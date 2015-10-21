import redis.clients.jedis.Jedis;

import java.math.BigInteger;

/**
 * Created by William Heng(dev) on 21/10/15.
 */
public class FibMaxIntExperiment {
    Jedis redis;

    public FibMaxIntExperiment(Jedis redis) {
        this.redis = redis;
    }

    public BigInteger fib(int term) {
        BigInteger f0 = BigInteger.ZERO;
        BigInteger f1 = BigInteger.ONE;
        BigInteger fn = null;

        for (int i = 0; i <= term; i++) {
            String key = Integer.toString(i);
            if (this.redis.exists(key)) {
                fn = new BigInteger(this.redis.get(key));
                f0 = f1;
                f1 = fn;
            } else {
                fn = f0.add(f1);
                f0 = f1;
                f1 = fn;

                String fnString = fn.toString();
                System.out.println("Setting " + key + " as " + fnString);
                this.redis.set(key, fnString);
            }
        }

        return fn;
    }

    private final static BigInteger INT_MAX_VALUE = new BigInteger(Integer.toString(Integer.MAX_VALUE));
    public static void main(String[] args) {
        Jedis redis = new Jedis("localhost");
        FibMaxIntExperiment experiment = new FibMaxIntExperiment(redis);

        if (!redis.exists("0") || !redis.exists("1") ||
            !redis.get("0").equals("0") || !redis.get("1").equals("1")
            ) {
            redis.flushAll();  // Clear everything and set default values
            redis.set("0", "0");
            redis.set("1", "1");
        }

        for (int term = 0; term < Integer.MAX_VALUE; term++) {
            if (experiment.fib(term).compareTo(INT_MAX_VALUE) > 0) {
                // Found the upper limit
                int representableTerm = term-1;
                System.out.println("The highest term a primitive integer can represent is term " + representableTerm);
                break;
            }
        }
    }
}
