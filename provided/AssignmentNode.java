package provided;

public class AssignmentNode implements JottTree {
    private final Token idTok;
    private final JottTree rhs;

    public AssignmentNode(Token idTok, JottTree rhs) {
        this.idTok = idTok;
        this.rhs = rhs;
    }

    @Override
    public String convertToJott() {
        return idTok.getToken() + "=" + rhs.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String convertToC() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String convertToPython() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

}

