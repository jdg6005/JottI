package provided.Nodes;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IDNode implements OperandNode{
    private Token id;

    public static IDNode parseIDNode(ArrayList<Token> tokens) {
        Token token = tokens.remove(0);
        IDNode idNode = new IDNode();

        if (token == null) {
            System.err.println("Syntax Error:");
            System.err.println("Invalid token");
        } else if (token.getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println("Syntax Error:");
            System.err.println("Invalid token");
        }

        idNode.id = token;
        return idNode;
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
