public class LRAutomaton {
    public static final int REDUCE  = -1;
    public static final int ERROR   = -2;
    public static final int ACCEPT  = -3;
    /**
     * A matrix encoiding the transition function for the DFA for the LR parser for the following grammar:
     * 
     * E → E + T | E - T | T
     * T → T * F | T / F | F
     * F → (E) | INT | ID
     * 
     * We add the production S → E $ to the above grammar.
     * 
     * If a SHIFT is to be performed, we put the destination state (a value between 0 and 16) in each cell.
     * If a REDUCE is to be performed, we put `REDUCE`.
     * If an ERROR would occur, we put `ERROR`.
     * If we should ACCEPT the string, we put `ACCEPT`.
     */
    private static int[][] matrix = new int[][] {
        // state:   0           1           2           3           4           5           6           7           8           9           10          11          12          13          14          15          16
        /* S */ { ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
        /* E */ {   1       , ERROR     , ERROR     , ERROR     ,   11      , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
        /* T */ {   2       , ERROR     , ERROR     , ERROR     ,   2       , ERROR     , ERROR     ,   12      ,   13      , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
        /* F */ {   3       , ERROR     , ERROR     , ERROR     ,   3       , ERROR     , ERROR     ,   3       ,   3       ,   14      ,   15      , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
        /* $ */ { ERROR     , ACCEPT    , REDUCE    , REDUCE    , ERROR     , REDUCE    , REDUCE    , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , REDUCE    , REDUCE    , REDUCE    , REDUCE    , REDUCE },
        /* + */ { ERROR     ,   7       , REDUCE    , REDUCE    , ERROR     , REDUCE    , REDUCE    , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , REDUCE    , REDUCE    , REDUCE    , REDUCE    , REDUCE },
        /* - */ { ERROR     ,   8       , REDUCE    , REDUCE    , ERROR     , REDUCE    , REDUCE    , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , REDUCE    , REDUCE    , REDUCE    , REDUCE    , REDUCE },
        /* * */ { ERROR     , ERROR     ,   9       , REDUCE    , ERROR     , REDUCE    , REDUCE    , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     ,   9       ,   9       , REDUCE    , REDUCE    , REDUCE },
        /* / */ { ERROR     , ERROR     ,   10      , REDUCE    , ERROR     , REDUCE    , REDUCE    , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     ,   10      ,   10      , REDUCE    , REDUCE    , REDUCE },
        /* ( */ {   4       , ERROR     , ERROR     , ERROR     ,   4       , ERROR     , ERROR     ,   4       ,   4       ,   4       ,   4       , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
        /* ) */ { ERROR     , ERROR     , REDUCE    , REDUCE    , ERROR     , REDUCE    , REDUCE    , ERROR     , ERROR     , ERROR     , ERROR     ,   16      , REDUCE    , REDUCE    , REDUCE    , REDUCE    , REDUCE },
        /* n */ {   5       , ERROR     , ERROR     , ERROR     ,   5       , ERROR     , ERROR     ,   5       ,   5       ,   5       ,   5       , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
        /* d */ {   6       , ERROR     , ERROR     , ERROR     ,   6       , ERROR     , ERROR     ,   6       ,   6       ,   6       ,   6       , ERROR     , ERROR     , ERROR     , ERROR     , ERROR     , ERROR  },
    };

    /** Determine the number of characters that are to be reduced */
    public static int sizeOfReduction(int state) {
        switch (state) {
            case 2:
            case 3:
            case 5:
            case 6:
                return 1;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return 3;
            default:
                return 0;
        }
    }

    /** Determine the string to reduce to when a reduction is performed when in the given state. */
    public static String reduceTo(int state) {
        switch (state) {
            case 2:
            case 12:
            case 13:
                return "E";
            case 3:
            case 14:
            case 15:
                return "T";
            case 5:
            case 6:
            case 16:
                return "F";
            default:
                return null;
        }
    }

    /** Get the index of the column in `matrix` which correspond to the symbol `c` of the language we are parsing */
    private static int row(char c) {
        switch (c) {
            case 'S':
                return 0;
            case 'E':
                return 1;
            case 'T':
                return 2;
            case 'F':
                return 3;
            case '$':
                return 4;
            case '+':
                return 5;
            case '-':
                return 6;
            case '*':
                return 7;
            case '/':
                return 8;
            case '(':
                return 9;
            case ')':
                return 10;
            case 'n':
                return 11;
            case 'd':
                return 12;
            default:
                return -1;  // This should never actually happen
        }
    }

    /**
     * Determine which shift–reduce action to perform for a given state and input character
     * @return ACCEPT, ERROR, REDUCE, or a destination state number.
     */
    public static int getAction(int state, char inputChar) {
        return matrix [row(inputChar)] [state];
    }
}
