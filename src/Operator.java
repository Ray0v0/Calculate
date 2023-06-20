enum Operator {
    OPE_PLUS(10, '+'), OPE_MINUS(10, '-'), OPE_MULTI(100, '*'), OPE_DIV(100, '/'), OPE_POWER(1000, '^');

    public final int priority;
    public final char expChar;
    Operator(int priority, char expChar) {
        this.priority = priority;
        this.expChar = expChar;
    }
}
