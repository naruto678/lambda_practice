package helloworld;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateOrderLambda  {
    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent input) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        DynamoDB db=new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
         Table table=db.getTable(System.getenv("ORDERS_TABLE"));

         Order order=objectMapper.readValue(input.getBody(), Order.class);


         PutItemOutcome outcome=table.putItem(new Item().withPrimaryKey("id",order.getId()).
                                withString("itemName",order.getItemName())
                                .withInt("quantity", order.getQuantity()));

         if(outcome!=null)
             return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(outcome.getPutItemResult().toString());
         else
             return new APIGatewayProxyResponseEvent().withStatusCode(500).withBody("Internal error message. Something went wrong with adding to the db"+ outcome);







    }

}
