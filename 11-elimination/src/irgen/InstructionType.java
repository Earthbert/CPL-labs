package irgen;

public enum InstructionType {
    COPY,
    ADD,
    SUB,
    MUL,
    DIV,
    CMP_EQ,
    CMP_LE,
    CMP_LT,
    CALL,
    BR,
    RET,
    PHI {
        @Override
        public String toString() {
            return "ϕ";
        }
    }
}
