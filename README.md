# Implementing practical parsers

## Compilation

The project has been written in Java. Compile from this directory simply with

```
javac *.java
```

## Execution

To parse using `<parser>`, where `<X>` is one of `RDParser`, `LLParser`, and
`LRParser`, run

```
java <parser> <string>
```

where `<string>` is the string to be parsed. The program will output the result
to the screen, and also return success or failure to the terminal in the
conventional fashion; 0 for success, 1 for failure.

For example:

```
$ java LRParser d+(n/d)*n
The string d+(n/d)*n is VALID!

$ java LRParser d+(n/d)*
The string d+(n/d)* is NOT VALID!
```

## Details

### Part 1 — Top-down parsers

#### A — Recursive descent parser

This is contained wholly within the file `RDParser.java`.

Unfortunately, we could not implement a recursive descent parser with "full
backtracking", so our naive implementation doesn't actually properly recognise
the language for the given grammar; it only correctly recognises `a` surrounded
in arbitrarily many pairs of parentheses, i.e.

`a`, `(a)`, `((a))`, `(((a)))`, ...

#### B — Predictive parser

This is contained within the file `LLParser.java`, with the following class
dependency tree:

- `LLParser`
    - `LLParseTable`
    - `LLStack`
        - `Stack`

Noticing that the given grammar is LL(1), the predictive parser we implemented
is an LL(1) parser. It is able to successfully recognise the language given, but
we did not do the additional task of being able to use any `int` token in place
of `a`.

### Part 2 — Bottom-up parser

This is contained within the file `LRParser.java`, with the following class
dependency tree:

- `LRParser`
    - `LRAutomaton`
    - `LRStack`
        - `Stack`

We wrote an LR parser to serve as a bottom-up parser for the given grammar. It
is able to successfully recognise the language given, but we did not do the
additional task of making `int` and `id` correspond to arbitrary integers and
variable names, respectively. Moreover, to simplify the implementation, we don't
use `int` and `id`, but instead use `n` and `d`, as you may have noticed in the
example given in *§ Execution*:

```
$ java LRParser d+(n/d)*n
The string d+(n/d)*n is VALID!
```
