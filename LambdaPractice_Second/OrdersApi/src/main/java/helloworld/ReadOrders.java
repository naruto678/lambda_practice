package helloworld;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ReadOrders {

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input) throws JsonProcessingException {
        AmazonDynamoDB db=AmazonDynamoDBClientBuilder.defaultClient();
        ScanResult result=db.scan(new ScanRequest().withTableName(System.getenv("ORDERS_TABLE")));
        List<Order> list=result.getItems()
                .stream()
                .map(item->new Order(Integer.parseInt(item.get("id").getN()),String.valueOf(item.get("itemName") .getS()), 23)).collect(Collectors.toList());


        return new APIGatewayProxyResponseEvent().withStatusCode(200).
                withBody(mapper.writeValueAsString(list));




    }

}
