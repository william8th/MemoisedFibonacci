import redis.clients.jedis.Jedis;

/**
 * Created by William Heng(dev) on 21/10/15.
 */
public class Fibonacci implements Fibonacciable {
    KVCache cache;

    public Fibonacci(final KVCache cache) {
        this.cache = cache;
    }

    public int fib(final int n) {
        if (n < 2) {
            return n;
        } else {
            return fib(n-1) + fib(n-2);
        }
    }

    public int fibWithMemoisation(final int n) {
        if (n < 2) {
            return n;
        } else {

            // Memoization
            String term = Integer.toString(n);
            int result = 0;


            if (cache.exists(term)) {
                result = Integer.parseInt(cache.get(term));
            } else {
                result = fibWithMemoisation(n - 1) + fibWithMemoisation(n - 2);

                // Cache the result
                cache.set(term, Integer.toString(result));
            }

            return result;
        }
    }
}
