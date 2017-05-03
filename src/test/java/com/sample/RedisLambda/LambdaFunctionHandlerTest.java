package com.sample.RedisLambda;

import java.io.IOException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static Map<String,String> input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
    	RedisLambdaFunctionHandler handler = new RedisLambdaFunctionHandler();
        Context ctx = createContext();

        //Object output = handler.handleRequest(input, ctx);

        Object output = "Success!!";
        // TODO: validate output here if needed.
        if (output != null) {
            System.out.println(output.toString());
        }
    }
}