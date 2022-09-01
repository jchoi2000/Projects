package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;


    /** Creates a MaxArrayDeque with the given Comparator. */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    /** Returns the maximum element in the deque as governed by the previously given Comparator. If the MaxArrayDeque is empty, simply returns null. */
    public T max() {
        return max(this.comparator);
    }

    /** Returns the maximum element in the deque as governed by the parameter Comparator c. If the MaxArrayDeque is empty, simply returns null. */
    public T max(Comparator<T> c) {
        this.comparator = c;
        if (this.isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(max, this.get(i)) < 0) {
                max = this.get(i);
            }
        }
        return max;
    }
}
