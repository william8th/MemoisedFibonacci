import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by William Heng(dev) on 21/10/15.
 */
@RunWith(Theories.class)
public class FibonacciableTest {
    Fibonacciable fibDriver;
    Jedis redis;

    @DataPoints
    public final static IntPair[] fibSequence() {
        return new IntPair[]{
                new IntPair(20, 6765),
                new IntPair(12, 144),
                new IntPair(11, 89),
                new IntPair(10, 55),
                new IntPair(9, 34),
                new IntPair(8, 21),
                new IntPair(7, 13),
                new IntPair(4, 3),
                new IntPair(3, 2),
                new IntPair(2, 1),
                new IntPair(5, 5),
                new IntPair(6, 8),
                new IntPair(1, 1),
                new IntPair(0, 0)
        };
    }

    @Before
    public void setUp() throws Exception {
        this.redis = new Jedis("localhost");
        this.fibDriver = new Fibonacci(this.redis);
    }

    @Theory
    public void testFibWithoutMemoisation(IntPair p) {
        long lStartTime = System.nanoTime();
        int result = this.fibDriver.fib(p.x);
        long lEndTime = System.nanoTime();
        long difference = lEndTime - lStartTime;
        System.out.println("Without elapsed: " + difference + " for " + p.x);
        assertEquals(p.y, result);
    }

    @Theory
    public void testFibWithMemoisation(IntPair p) {
        long lStartTime = System.nanoTime();
        int result = this.fibDriver.fibWithMemoisation(p.x);
        long lEndTime = System.nanoTime();
        long difference = lEndTime - lStartTime;
        System.out.println("With elapsed: " + difference + " for " + p.x);
        assertEquals(p.y, result);
    }

    @Test
    public void testFibWithUpperLimit() {
        int UPPER_LIMIT_TERM = 46;
        int UPPER_LIMIT_FIB = 1836311903;
        long lStartTime = System.nanoTime();
        int result = this.fibDriver.fibWithMemoisation(UPPER_LIMIT_TERM);
        long lEndTime = System.nanoTime();
        long difference = lEndTime - lStartTime;
        System.out.println("With elapsed: " + difference + " for " + UPPER_LIMIT_TERM);
        assertEquals(UPPER_LIMIT_FIB, result);


        lStartTime = System.nanoTime();
        int resultWithout = this.fibDriver.fib(UPPER_LIMIT_TERM);
        lEndTime = System.nanoTime();
        long differenceWithout = lEndTime - lStartTime;
        System.out.println("Without elapsed: " + differenceWithout + " for " + UPPER_LIMIT_TERM);
        assertEquals(UPPER_LIMIT_FIB, resultWithout);

        assertTrue(differenceWithout > difference);
    }
}

class IntPair {
    final int x;
    final int y;
    IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}