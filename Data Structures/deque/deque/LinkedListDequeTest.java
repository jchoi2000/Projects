package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
		lld.addFirst(0);

        assertFalse("lld should now contain 1 item", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
        assertFalse("lld should be empty", lld.isEmpty());
    }

    @Test
    /** Adds a first item to the list, checks that addFirst() is correct for adding size.
     */
    public void addRemoveFirstTest() {
        lld.addFirst(0);
        assertEquals("lld should now contain 1 item", 1, lld.size());
        lld.addFirst(1);
        assertEquals("lld should now contain 2 items", 2, lld.size());
        lld.addFirst(2);
        assertEquals("lld should now contain 3 items", 3, lld.size());
        lld.addFirst(3);
        assertEquals("lld should now contain 4 items", 4, lld.size());
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 3);
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 2);
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 1);
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 0);
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /** Adds a last item to the list, checks that addLast() is correct for adding size.
     */
    public void addRemoveLastTest() {
        lld.addLast(9);
        assertEquals("lld should now contain 1 item", 1, lld.size());
        lld.addLast(8);
        assertEquals("lld should now contain 2 items", 2, lld.size());
        lld.addLast(7);
        assertEquals("lld should now contain 3 items", 3, lld.size());
        lld.addLast(6);
        assertEquals("lld should now contain 4 items", 4, lld.size());
        assertEquals("lld should now contain 4 items", 4, lld.size());
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 6);
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 7);
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 8);
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 9);
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /**
     */
    public void addFirstRemoveLast() {
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertEquals("lld should now contain 3 items", 3, lld.size());
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 1);
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 2);
        assertEquals("lld should return the removed item", (int) lld.removeLast(), 3);
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /**
     */
    public void addLastRemoveFirst() {
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 1);
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 2);
        assertEquals("lld should return the removed item", (int) lld.removeFirst(), 3);
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    /**
     */
    public void getTest() {
        lld.addFirst(1);
        lld.addLast(2);
        assertFalse("lld should now contain 2 items", lld.isEmpty());
        assertEquals("lld should now have size 2", lld.size(), 2);
        assertEquals("First integer of lld should be 1.", (int) lld.get(0), 1);
        assertEquals("Second integer of lld should be 2.", (int) lld.get(1), 2);

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
    }
    
}
