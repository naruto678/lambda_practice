package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PatientCheckoutLambda {

  AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
  ObjectMapper mapper = new ObjectMapper();
  AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();

  public void handleRequest(S3Event event, Context context) {
    LambdaLogger logger = context.getLogger();
    event
        .getRecords()
        .forEach(
            record -> {
              final S3ObjectInputStream objectContent =
                  s3.getObject(
                          record.getS3().getBucket().getName(), record.getS3().getObject().getKey())
                      .getObjectContent();
              try {
                List<PatientCheckoutEvent> patientCheckoutEvents =
                    Arrays.asList(mapper.readValue(objectContent, PatientCheckoutEvent[].class));
                System.out.println(patientCheckoutEvents);
                patientCheckoutEvents.forEach(
                    checkoutEvent -> {
                      try {
                        sns.publish(
                            System.getenv("PATIENT_CHECKOUT_TOPIC"),
                            mapper.writeValueAsString(checkoutEvent));
                      } catch (JsonProcessingException e) {
                        logger.log(e.getMessage());
                        sns.publish(
                            System.getenv("DEAD_LETTER_QUEUE"),
                            e.getMessage() + "\n" + checkoutEvent.toString());
                      }
                    });

              } catch (IOException e) {
              }
            });
  }
}
