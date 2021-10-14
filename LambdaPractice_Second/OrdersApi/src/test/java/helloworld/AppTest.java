package helloworld;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class AppTest {
  @Test
  public void successfulResponse() {
    App app = new App();
    APIGatewayProxyResponseEvent result = app.handleRequest(null, null);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains("\"hello world\""));
    assertTrue(content.contains("\"location\""));
  }

  @Test
  public void mappingTest() throws  Exception  {
    String jsonString="{\n" +
            "  \"id\":123, \n" +
            "  \"itemName\": \"someRandomName\", \n" +
            "  \"quantity\": 12\n" +
            "}";
    ObjectMapper mapper=new ObjectMapper();

    Order order=mapper.readValue(jsonString, Order.class);
    System.out.println(order);
    assert(order!=null);
  }
}
