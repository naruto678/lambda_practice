package helloworld;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BillManagementLambda {
  ObjectMapper mapper = new ObjectMapper();

  public void handleRequest(SNSEvent event) {
    event
        .getRecords()
        .forEach(
            record -> {
              try {
                PatientCheckoutEvent patientCheckoutEvent =
                    mapper.readValue(record.getSNS().getMessage(), PatientCheckoutEvent.class);
                System.out.println(patientCheckoutEvent);

              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
            });
  }
}
