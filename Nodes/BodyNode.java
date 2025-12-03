package Nodes;

import project.TypeAndValue;
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
            str.append(bodyStmtNode.convertToJott()).append(" ");
        }
        if(this.returnNode != null) {
            str.append(returnNode.convertToJott());
        }
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
        // validate return
        if(this.returnNode != null) { this.returnNode.validateTree(); }
        // validate body statements
        for(BodyStmtNode bodyStmt : this.bodyStmtNodes) {
            bodyStmt.validateTree();
        }

        return true;
    }

    public boolean isReturnable() {
        // has a return statement
        if(returnNode != null) return true;

        // one of the body statements has a return
        for(BodyStmtNode bodyStmt : bodyStmtNodes) {
            if(bodyStmt.isReturnable()) return true;
        }

        return false;
    }

    public boolean doesntHaveReturn() {
        return !this.convertToJott().contains("Return");
    }

    // --------------------------------

    public TypeAndValue execute() {
        for(BodyStmtNode statement : bodyStmtNodes) {
            TypeAndValue result = statement.execute();
            if(result != null) return result;
        }
        if(returnNode != null) return returnNode.execute();
        return new TypeAndValue(TypeEnum.Void);
    }
}
