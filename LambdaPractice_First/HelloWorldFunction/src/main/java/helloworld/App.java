package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App  {
    public String helloWorld(){
        return "Hello world . This is arnab . Learning aws lambdas. this is so cool . Sugoi "; 
    }  


    public String helloWorld(String name){
        return "Hellow world. this is "+name+" . I am learning aws. I want to be a disttributed systems engineer " ;
    }
}

