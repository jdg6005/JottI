package Nodes;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IDNode implements OperandNode{
    private Token id;

    public static IDNode parseIDNode(ArrayList<Token> tokens) {
        IDNode idNode = new IDNode();
        Token token = tokens.removeFirst();

        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println("Syntax Error:");
            System.err.println("Expected identifier");
            System.err.println(token.getFilename() + ":" + token.getLineNum());
            return null;
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
