package provided.Nodes;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IDNode implements OperandNode{
    private Token id;

    public static IDNode parseIDNode(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            return null;
        }

        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println("Syntax Error:");
            System.err.println("Expected identifier");
            System.err.println(token.getFilename() + ":" + token.getLineNum());
            return null;
        }

        tokens.remove(0);
        return new IDNode(token);
    }

    @Override
    public String convertToJott() {
        return this.id.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.id.getToken();
    }

    @Override
    public String convertToC() {
        return this.id.getToken();
    }

    @Override
    public String convertToPython() {
        return this.id.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }
}
