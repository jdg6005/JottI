package provided;

import provided.Nodes.IDNode;

import java.util.ArrayList;

public class AssignmentNode implements BodyStmtNode {
    private IDNode idNode;
    private ExprNode exprNode;

    public AssignmentNode parseAsmtNode(ArrayList<Token> tokens) {
        AssignmentNode assignmentNode = new AssignmentNode();
        assignmentNode.idNode = IDNode.parseIDNode(tokens);
        if (tokens.remove(0).getTokenType() != TokenType.ASSIGN) {
            throw new Error("Expected assignment after id");
        }
        assignmentNode.exprNode = ExprNode.parseExprNode(tokens);
        return assignmentNode;
    }

    @Override
    public String convertToJott() {
        return idNode.convertToJott() + "=" + exprNode.convertToJott() + ";";
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

