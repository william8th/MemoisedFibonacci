import redis.clients.jedis.Jedis;

/**
 * Created by William Heng(dev) on 21/10/15.
 */
public class Fibonacci implements Fibonacciable {
    Jedis redis;

    public Fibonacci(Jedis redis) {
        this.redis = redis;
    }

    public int fib(int n) {
        if (n < 2) {
            return n;
        } else {
            return fib(n-1) + fib(n-2);
        }
    }

    public int fibWithMemoisation(int n) {
        if (n < 2) {
            return n;
        } else {

            // Memoization
            String term = Integer.toString(n);
            int result = 0;


            if (redis.exists(term)) {
                result = Integer.parseInt(redis.get(term));
            } else {
                result = fibWithMemoisation(n - 1) + fibWithMemoisation(n - 2);

                // Cache the result
                redis.set(term, Integer.toString(result));
            }

            return result;
        }
    }
}
