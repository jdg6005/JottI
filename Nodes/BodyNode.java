package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BodyNode implements JottTree {
    ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
    ReturnStmtNode returnNode;

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) {
        BodyNode bodyNode = new BodyNode();

        while (!tokens.getFirst().getToken().equals("Return") && !tokens.getFirst().getTokenType().equals(TokenType.R_BRACE)) {
            bodyNode.bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
        }
        bodyNode.returnNode = ReturnStmtNode.parseReturnStmtNode(tokens);

        return bodyNode;
    }


    @Override
    public String convertToJott() {
        StringBuilder str = new StringBuilder();
        for (BodyStmtNode bodyStmtNode : bodyStmtNodes) {
            str.append(bodyStmtNode.convertToJott());
        }
        str.append(returnNode.convertToJott());
        return str.toString();
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return true;
    }
}
