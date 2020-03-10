public class LRParser {
    static int cursor = 0;
    static int state = 0;
    static String input = null;

    static char nextChar() {
        return input.charAt(cursor);
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java LRParser <inputString>");
            System.exit(1);
        }
        String inputWithoutDollar = args[0];
        input = inputWithoutDollar.concat("$");

        final int STACK_SIZE = 256;
        LRStack stack = new LRStack(STACK_SIZE);

        boolean doneParsing = false;
        boolean valid = false;  // is the string valid? Have we parsed succesfully?
        int action;
        char nextChar;
        while(!doneParsing) {
            // DEBUGGING: Print the current state and working input
            // System.err.println(state+": "+input.substring(0,cursor)+"•"+input.substring(cursor));
            
            nextChar = nextChar();
            switch (action = LRAutomaton.getAction(state, nextChar)) {
                case LRAutomaton.ACCEPT:
                    doneParsing = true;
                    valid = true;
                    break;
                case LRAutomaton.ERROR:
                    doneParsing = true;
                    valid = false;
                    break;
                case LRAutomaton.REDUCE:
                    int sizeOfReduction = LRAutomaton.sizeOfReduction(state);
                    int end = cursor;
                    int start = (cursor = cursor - sizeOfReduction);
                    input = input.substring(0, start)
                        .concat(LRAutomaton.reduceTo(state))
                        .concat(input.substring(end));
                    // pop the stack once for each character in the RHS of the
                    // production rule for which we are performing a reduction
                    for (int i = 1; i < sizeOfReduction; i++) {
                        stack.pop();
                    }
                    state = stack.pop();
                    break;
                default:    // we got a state number; perform a SHIFT
                    if (!stack.push(state)) {
                        System.err.println("Terminating due to insufficient memory; increase the stack size, re-compile, and re-run.");
                        System.exit(1);
                    }
                    cursor++;
                    state = action;
                    break;
            }
        }

        System.out.println("The string "+inputWithoutDollar+" is "+(valid ? "VALID" : "NOT VALID")+"!");
        System.exit(valid ? 0 : 1);
    }
}
