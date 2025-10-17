package Nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

import static provided.Nodes.BodyStmtNode.parseBodyStmtNode;


public class BodyNode implements JottTree {
    private ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
    private ReturnStmtNode returnNode;

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) {
        BodyNode bodyNode = new BodyNode();

        while (!tokens.getFirst().getToken().equals("Return")) {
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
