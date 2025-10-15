package provided.Nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class MathOpNode implements JottTree {
    private Token mathOp;
    public static MathOpNode parseMathOpNode(ArrayList<Token> tokens) {
        Token token = tokens.removeFirst();
        String mathOp = token.getToken();

        if (mathOp.equals("+") || mathOp.equals("-") || mathOp.equals("*") || mathOp.equals("/")) {
            MathOpNode mathOpNode = new MathOpNode();
            mathOpNode.mathOp = token;
            return mathOpNode;
        }
        System.err.println("Syntax Error\nInvalid token.");
        return null;
    }

    @Override
    public String convertToJott() {
        return this.mathOp.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.mathOp.getToken();
    }

    @Override
    public String convertToC() {
        return this.mathOp.getToken();
    }

    @Override
    public String convertToPython() {
        return this.mathOp.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }
}
