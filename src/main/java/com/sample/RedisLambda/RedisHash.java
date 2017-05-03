package com.sample.RedisLambda;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
 
import java.util.HashMap;
import java.util.Map;
 
public class RedisHash {
 
    //address of redis server
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
 
    // jedis connection pool..
    private static JedisPool pool = null;
 
    public RedisHash() {
        //configure pool connection
        pool = new JedisPool(redisHost, redisPort);
 
    }
 
    public void addHash() {
        //adding some values in Redis HASH
        String key = "NewMap";
        Map<String, String> map = new HashMap<String, String>();
        map.put("Glass", "Vase");
        map.put("Watch", "Timex");
        map.put("Bag", "Capresse");
        
        System.out.println(pool.toString());
        Jedis jedis = pool.getResource();
        System.out.println("Looks like u'v got something from the pool");
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
 
    public static void main(String[] args){
    	RedisHash main = new RedisHash();
        
        main.addHash();
    }
}
