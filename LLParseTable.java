public class LLParseTable {
    /**
     * A parse table for the following LL(1) grammar (the grammar in part (1B)
     * of the assignment, but we have replaced E' with X, and T' with Y):
     * 
     * E →  T X
     * X →  + T X | ε
     * T →  F Y
     * Y →  ∗ F Y | ε
     * F →  ( E ) | a
     * 
     * Rules are encoded as strings representing their right-hand sides,
     * with ε encoded as the empty string `""`, and the lack of a rule encoded
     * as `null`.
     */
    private static String[][] table = new String[][] {
        //          $       (           a           )           +           *
        /* E */ { null  ,   "TX"    ,   "TX"    ,   null    ,   null    ,   null    },
        /* X */ { ""    ,   null    ,   null    ,   ""      ,   "+TX"   ,   null    },
        /* T */ { null  ,   "FY"    ,   "FY"    ,   null    ,   null    ,   null    },
        /* Y */ { ""    ,   null    ,   null    ,   ""      ,   ""      ,   "*FY"   },
        /* F */ { null  ,   "(E)"   ,   "a"     ,   null    ,   null    ,   null    },
    };

    /** Get the row index in the parse table for the given nonterminal. */
    private static int row(char nonterminal) {
        switch (nonterminal) {
            case 'E':
                return 0;
            case 'X':
                return 1;
            case 'T':
                return 2;
            case 'Y':
                return 3;
            case 'F':
                return 4;
            default:
                return -1; // Occurs if we were actually given a terminal
        }
    }

    /** Get the column index in the parse table for the given terminal. */
    private static int column(char terminal) {
        switch (terminal) {
            case '$':
                return 0;
            case '(':
                return 1;
            case 'a':
                return 2;
            case ')':
                return 3;
            case '+':
                return 4;
            case '*':
                return 5;
            default:
                return -1;
        }
    }

    /**
     * Get the string to produce when `stackChar` is on the stack and
     * `inputChar` is the next character of input to be read
     * @return the corresponding string; if no such production rule exists,
     * return `null`. If `inputChar` is not a terminal, behaviour is
     * undefined (in practice, we actually return `null`).
     */
    public static String getProduction(char stackChar, char inputChar) {
        int rowIndex = row(stackChar);
        int columnIndex = column(inputChar);
        if (rowIndex == -1) {
            return null;
        }
        if (columnIndex == -1) {
            System.err.println("The provided input character "+inputChar+" is not a terminal!");
            return null;
        }
        return table[rowIndex][columnIndex];
    }
}
