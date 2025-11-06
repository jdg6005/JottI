package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    TypeNode typeNode;
    IDNode idNode;


    // TODO: add the variables to the hashset for the current scope or fail if
    // it's already in there (semantic error)
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

    // TODO: check variable name unique
    @Override
    public boolean validateTree() {
        // checks id starts w/ lowercase
        this.idNode.validateTree();

        return true;
    }
}
