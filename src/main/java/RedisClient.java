import redis.clients.jedis.Jedis;

/**
 * Created by William Heng(dev) on 22/10/15.
 */
public class RedisClient implements KVCache {
    Jedis redis;

    public RedisClient(Jedis redis) {
        this.redis = redis;
    }

    public void set(String key, String value) {
        this.redis.set(key, value);
    }

    public String get(String key) {
        return this.redis.get(key);
    }

    public boolean exists(String key) {
        return this.redis.exists(key);
    }
}
