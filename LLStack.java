public class LLStack extends Stack<Character> {
    /** Create a stack of a given size for an LL parser. */
    public LLStack(int size) {
        super(size);
    }
    
    /**
     * Push each character of a given string onto the stack in reverse order,
     * so that popping the stack pops off the chararcters of the string in order.
     * @return `true` iff this operation succeeded. If unsuccessful, the stack
     * will be left in its prior state.
     */
    public boolean push(String s) {
        int oldTopIndex = topIndex;
        for (int i = s.length() - 1; i >=0; i--) {
            if (!push(s.charAt(i))) {
                topIndex = oldTopIndex;
                return false;
            }
        }
        return true;
    }
}
