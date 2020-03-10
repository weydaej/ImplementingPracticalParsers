public class LLParser {
    static String inputWithoutDollar = null;
    static String input = null;
    static int cursor = 0;
    
    static void checkValidityAndExit() {
        boolean valid = (cursor == input.length()); // true iff we exhausted the input
        System.out.println("The string "+inputWithoutDollar+" is "+(valid ? "VALID" : "NOT VALID")+"!");
        System.exit(valid ? 0 : 1);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java LLParser <inputString>");
            System.exit(1);
        }
        inputWithoutDollar = args[0];
        input = inputWithoutDollar.concat("$");

        final int STACK_SIZE = 256;
        LLStack stack = new LLStack(STACK_SIZE);
        stack.push("E$");

        char poppedChar;
        char nextChar = input.charAt(cursor);
        int parseTableRow;
        String production;
        boolean doneParsing = false;
        while (!stack.isEmpty()) {
            poppedChar = stack.pop();
            production = LLParseTable.getProduction(poppedChar, nextChar);
            if (production == null) {
                // we either:
                // • popped a terminal (in which case, advance the cursor iff
                // the characters match); or
                // • there is no valid production rule (in which case, the
                // string we're parsing is itself invalid).
                if (poppedChar == nextChar) {
                    cursor++;
                    if (nextChar != '$') {
                        nextChar = input.charAt(cursor);
                    }
                } else {
                    // the string is invalid;
                    // call the exit function
                    checkValidityAndExit();
                }
            } else {
                stack.push(production);
            }
        }

        // the stack is empty, so we have nothing to parse;
        // call the exit function
        checkValidityAndExit();
    }
}
