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
            throw new Error("Syntax Error\nExpected ';'\n" + token.getFilename() + ":" + token.getLineNum());
        }

        return varDecNode;
    }

    @Override
    public String convertToJott() {
        StringBuilder str = new StringBuilder();
        str.append(typeNode.convertToJott());
        str.append(" ");
        str.append(idNode.convertToJott());

        return str.toString();
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
