package deque;

import org.junit.Test;
import static org.junit.Assert.*;


public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();

    @Test
    public void addFirstTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        for (int i = 0; i < 10; i++) {
            ad.addFirst(1);
        }
        assertTrue(ad.size() == 10);

        for (int i = 0; i < 100; i++) {
            ad.addFirst(1);
        }
        assertTrue(ad.size() == 110);

        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
    }

    @Test
    public void addRemoveFirstTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        assertTrue(ad.size() == 3);
        ad.addFirst(4);
        ad.addFirst(5);
        assertTrue(ad.size() == 5);

        assertTrue(ad.removeFirst() == 5);
        assertTrue(ad.size() == 4);
        assertTrue(ad.removeFirst() == 4);
        assertTrue(ad.size() == 3);
        assertTrue(ad.removeFirst() == 3);
        assertTrue(ad.size() == 2);
        assertTrue(ad.removeFirst() == 2);
        assertTrue(ad.size() == 1);
        assertTrue(ad.removeFirst() == 1);
        assertTrue(ad.isEmpty());

        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
    }

    @Test
    public void addRemoveLastTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        ad.addLast(5);
        assertTrue(ad.size() == 5);

        assertTrue(ad.removeLast() == 5);
        assertTrue(ad.size() == 4);
        assertTrue(ad.removeLast() == 4);
        assertTrue(ad.size() == 3);
        assertTrue(ad.removeLast() == 3);
        assertTrue(ad.size() == 2);
        assertTrue(ad.removeLast() == 2);
        assertTrue(ad.size() == 1);
        assertTrue(ad.removeLast() == 1);
        assertTrue(ad.isEmpty());

        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
    }

    @Test
    public void addRemoveBothTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        ad.addLast(1);
        ad.addLast(3);
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(2);
        assertTrue(ad.size() == 5);

        assertTrue(ad.removeFirst() == 1);
        assertTrue(ad.removeLast() == 2);
        assertTrue(ad.removeFirst() == 3);
        assertTrue(ad.removeLast() == 4);
        assertTrue(ad.removeFirst() == 5);
        assertTrue(ad.isEmpty());

        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
    }

    @Test
    public void resizeTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        for (int i = 1; i <= 5; i++) {
            ad.addFirst(i);
        }
        assertTrue(ad.size() == 5);

        ad.removeFirst();
        ad.removeLast();
        assertTrue(ad.size() == 3);

        ad.removeFirst();
        ad.removeLast();
        assertTrue(ad.size() == 1);

        ad.removeFirst();
        assertTrue(ad.isEmpty());

        ad.removeFirst();

        ad.addFirst(1);
        ad.addLast(1);
        assertTrue(ad.size() == 2);

        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
    }

    @Test
    public void getTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        assertTrue(((ArrayDeque<Integer>) ad).get(10) == null);

        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
        }
        assertTrue(ad.size() == 100);

        for (int i = 0; i < 100; i ++) {
            assertTrue(((ArrayDeque<Integer>) ad).get(i) == i);
        }

        assertTrue(((ArrayDeque<Integer>) ad).get(5) == 5);
        assertTrue(((ArrayDeque<Integer>) ad).get(99) == 99);
        assertTrue(((ArrayDeque<Integer>) ad).get(47) == 47);

        assertTrue(((ArrayDeque<Integer>) ad).get(10000) == null);

        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
    }
}
