package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    private TypeNode typeNode;
    private IDNode idNode;


    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) {
        VarDecNode varDecNode = new VarDecNode();

        // <type>
        varDecNode.typeNode = TypeNode.parseTypeNode(tokens);

        // <id>
        varDecNode.idNode = IDNode.parseIDNode(tokens);

        // ;
        Token token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.SEMICOLON) {
            throw new Error("Syntax Error\nExpected ';' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        return varDecNode;
    }

    @Override
    public String convertToJott() {
        return typeNode.convertToJott() + " " + idNode.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        return " ";
    }

    @Override
    public String convertToC() {
        return " ";
    }

    @Override
    public String convertToPython() {
        return " ";
    }

    // valid if id unique and valid (which checks lowercase)
    @Override
    public boolean validateTree() {
        return false;
    }
}
