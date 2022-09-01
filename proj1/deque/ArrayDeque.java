package deque;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private double length;
    private double size;
    private int nextFirst;
    private int nextLast;
    private static double MinThreshold = 0.25;
    private static double MaxThreshold = 0.5;
    private static int resizeFactor = 2;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        length = 8.0;
        size = 0.0;
        nextFirst = 4;
        nextLast = 5;
    }

    /** Increments the given index by 1 and returns resulting index. If the index is at the end of the array, loops back to the 0th index. */
    private int increment(int index) {
        if (index == length - 1) {
            return 0;
        }
        return index + 1;
    }
    /** Decrements the given index by 1 and returns resulting index. If the index is at the start of the array, loops back to the end of the array. */
    private int decrement(int index) {
        if (index == 0) {
            return (int) (length - 1);
        }
        return index - 1;
    }

    /** If the ArrayDeque exceeds the max/min threshold, resizes the array according to the resize factor. */
    private void resize() {
        if (size/length < MinThreshold) {
            T[] newArray = (T[]) new Object[(int) (length/resizeFactor)];
            int currentIndex = increment(nextFirst);
            for (int i = 0; i < size ; i++) {
                newArray[i] = items[currentIndex];
                currentIndex = increment(currentIndex);
            }
            items = newArray;
            length = length/resizeFactor;
            nextFirst = decrement(0);
            nextLast = (int) size;
        }
        if (size/length > MaxThreshold) {
            T[] newArray = (T[]) new Object[(int) (length*resizeFactor)];
            int currentIndex = increment(nextFirst);
            for (int i = 0; i < size; i++) {
                newArray[i] = items[currentIndex];
                currentIndex = increment(currentIndex);
            }
            items = newArray;
            length = length * resizeFactor;
            nextFirst = decrement(0);
            nextLast = (int) size;
        }
    }

    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = decrement(nextFirst);
        size++;
        resize();
    }

    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = increment(nextLast);
        size++;
        resize();
    }

    @Override
    public int size() {
        return (int) size;
    }

    @Override
    public void printDeque() {
        int currentIndex = increment(nextFirst);
        for (int count = 0; count < size ; count++) {
            System.out.print(items[currentIndex] + " ");
            currentIndex = increment(currentIndex);
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T item = items[increment(nextFirst)];
        nextFirst = increment(nextFirst);
        size--;
        resize();
        return item;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T item = items[decrement(nextLast)];
        nextLast = decrement(nextLast);
        size--;
        resize();
        return item;
    }

    @Override
    public boolean equals(Object  o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque temp1 = (Deque) o;
        ArrayDeque temp2 = this;
        if (temp1.size() != this.size) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!temp1.get(i).equals(temp2.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return this.items[(index + increment(nextFirst)) % (int) this.length];
    }

}
