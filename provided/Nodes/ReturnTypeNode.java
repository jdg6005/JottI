package provided.Nodes;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class ReturnTypeNode implements JottTree {
    private final Token typeTok;

    private ReturnTypeNode(Token t) {
        this.typeTok = t;
    }

    public static ReturnTypeNode parseFunctionReturn(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            return null;
        }

        Token t = tokens.getFirst();
        if (t.getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println("Syntax Error:");
            System.err.println("Expected return type");
            System.err.println(t.getFilename() + ":" + t.getLineNum());
            return null;
        }

        String lex = t.getToken();
        boolean ok = lex.equals("Integer") || lex.equals("Double")
                || lex.equals("String")  || lex.equals("Boolean")
                || lex.equals("Void");
        if (!ok) {
            System.err.println("Syntax Error:");
            System.err.println("Expected return type");
            System.err.println(t.getFilename() + ":" + t.getLineNum());
            return null;
        }

        tokens.removeFirst();
        return new ReturnTypeNode(t);
    }

    @Override
    public String convertToJott() {
        return typeTok.getToken();
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
