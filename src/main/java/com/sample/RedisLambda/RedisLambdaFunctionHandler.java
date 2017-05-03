package com.sample.RedisLambda;

import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
 
import java.util.HashMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Try and connect to Redis
 *
 */

public class RedisLambdaFunctionHandler implements RequestHandler<Map<String,String>, Object> {

	public Object handleRequest(Map<String, String> input, Context context) {

	    //address of redis server
		final String redisHost = "localhost"; 

		final Integer redisPort = 6379;
	 
	    //the jedis connection pool..
	   //configure our pool connection
	    JedisPool pool = new JedisPool(redisHost, redisPort);
	    	    
        addHash(pool);   
	 
		return "Success!!";
	}
	
 
    public void addHash(JedisPool pool) {
        //adding some values in Redis HASH
        String key = "NewMap";
        Map<String, String> map = new HashMap<String, String>();
        map.put("Glass", "Vase");
        map.put("Watch", "Timex");
        map.put("Bag", "Capresse");
 
        Jedis jedis = pool.getResource();
        
        try {
        	
            //save to redis
            jedis.hmset(key, map);
 
            //retrieving data added in redis
            Map<String, String> retrieveMap = jedis.hgetAll(key);
            for (String keyMap : retrieveMap.keySet()) {
                System.out.println(keyMap + " " + retrieveMap.get(keyMap));
            }
 
        } catch (JedisException e) {
            //return Jedis instance to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            ///return Jedis instance to the pool
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }
}

