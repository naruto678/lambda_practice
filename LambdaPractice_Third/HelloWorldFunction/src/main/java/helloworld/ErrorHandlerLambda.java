package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public class ErrorHandlerLambda {

    public void handleRequest(SNSEvent event, Context context){
        LambdaLogger logger=context.getLogger();
        event.getRecords().forEach(record->{
            logger.log(record.getSNS().getMessage());
        });
    }
}
