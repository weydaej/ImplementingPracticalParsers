public class RDParser {
    static String input;
    static int position;
    static int savedPosition;

    static void savePosition() {
        savedPosition = position;
    }

    static void backtrack() {
        position = savedPosition;
    }

    static boolean terminal(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == input.charAt(position)) {
                position++;
            } else {
                return false;
            }
        }
        return true;
    }

    static boolean s() {
        return e() && terminal("$");
    }
    
    static boolean e() {
        return t() && k();
    }

    static boolean k1() {
        return terminal("");
    }

    static boolean k2() {
        return terminal("+") && e();
    }

    static boolean t() {
        return f() && l();
    }

    static boolean l1() {
        return terminal("");
    }

    static boolean l2() {
        return terminal("*") && t();
    }

    static boolean f1() {
        return terminal("(") && e() && terminal(")");
    }

    static boolean f2() {
        return terminal("a");
    }

    static boolean k() {
        boolean result;
        savePosition();
        result = k1();
        if (!result) {
            backtrack();
            savePosition();
            result = k2();
        }
        return result;
    }

    static boolean l() {
        boolean result;
        savePosition();
        result = l1();
        if (!result) {
            backtrack();
            savePosition();
            result = l2();
        }
        return result;
    }

    static boolean f() {
        boolean result;
        savePosition();
        result = f1();
        if (!result) {
            backtrack();
            savePosition();
            result = f2();
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java RDParser <inputString>");
            System.exit(1);
        }

        String inputWithoutDollar = args[0];
        input = inputWithoutDollar.concat("$");
        position = savedPosition = 0;

        boolean valid = s();
        System.out.println("The string "+inputWithoutDollar+" is "+(valid ? "VALID" : "NOT VALID")+"!");
        System.exit(valid ? 0 : 1);
    }
}
