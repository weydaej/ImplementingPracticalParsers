public class Stack<T> {
    Object[] data;
    int topIndex;

    /** Create a new stack of the given size. */
    public Stack(int size) {
        data = new Object[size];
        topIndex = -1;
    }

    /** Get the data item at the given index */
    private T dataAt(int i) {
        @SuppressWarnings("unchecked")
        final T item = (T)data[i];
        return item;
    }

    /** Find out whether this stack is empty. */
    public boolean isEmpty() {
        return topIndex == -1;
    }

    /** Find out whether this stack is full. */
    public boolean isFull() {
        return topIndex == data.length - 1;
    }
    
    /** Pop the top item off of the stack. */
    public T pop() {
        if (this.isEmpty()) {
            System.err.println("The stack is empty!");
            return null;
        } else {
            return dataAt(topIndex--);
        }
    }

    /**
     * Push a given item onto the stack.
     * @return `true` iff this operation succeeded.
     */
    public boolean push(T newItem) {
        if (this.isFull()) {
            System.err.println("The stack is full!");
            return false;
        } else {
            data[++topIndex] = newItem;
            return true;
        }
    }
}
