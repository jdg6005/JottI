package Nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

import static Nodes.OperandNode.parseOperandNode;
import static provided.TokenType.*;

/**
 * This class is responsible for parsing expression calls
 *
 * @author Elijah Lederman
 **/

public class ExprNode implements JottTree {
    private OperandNode operand1;
    private RelopNode relop;
    private MathopNode mathop;
    private OperandNode operand2;
    private StringNode string;
    private BooleanNode bool;

    public static ExprNode parseExprNode(ArrayList<Token> tokens) {
        ExprNode self = new ExprNode();

        if (tokens.getFirst().getTokenType() == STRING) {
            Token t = tokens.removeFirst();
            if (t.getToken().equals("True") || t.getToken().equals("False")) {
                self.bool = parseBooleanNode(tokens);
            } else {
                self.string = parseStringNode(tokens);
            }
            return self;
        } else {
            self.operand1 = parseOperandNode(tokens);
            Token currentToken = tokens.removeFirst();
            if (currentToken.getTokenType() == REL_OP) {
                self.relop = parseRelopNode(tokens);
                self.operand2 = parseOperandNode(tokens);
            } else if (currentToken.getTokenType() == MATH_OP) {
                self.mathop = parseMathopNode(tokens);
                self.operand2 = parseOperandNode(tokens);
            }
        }

        return self;
    }

    @Override
    public String convertToJott() {
        if (this.bool != null) {
            return this.bool.convertToJott();
        } else if (this.string != null) {
            return this.string.converToJott();
        } else {
            String operand1 = this.operand1.convertToJott();
            String operation = "";
            String operand2 = "";
            if (this.relop != null) {
                operation = this.relop.convertToJott();
                operand2 = this.operand2.convertToJott();
            } else if (this.mathop != null) {
                operation = this.mathop.convertToJott();
                operand2 = this.operand2.convertToJott();
            }
            return operand1 + " " + operation + " " + operand2;
        }
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
