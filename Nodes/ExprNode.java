package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Objects;

import static Nodes.OperandNode.parseOperandNode;
import static provided.TokenType.*;

/**
 * This class is responsible for parsing expression calls
 *
 * @author Elijah Lederman
 **/

public class ExprNode implements JottTree {
    private OperandNode operand1;
    private Token relop;
    private Token mathop;
    private OperandNode operand2;
    private StringLiteralNode string;
    private BoolNode bool;

    public static ExprNode parseExprNode(ArrayList<Token> tokens) {
        ExprNode self = new ExprNode();

        // <string>
        if (tokens.getFirst().getTokenType() == STRING) {
            self.string = StringLiteralNode.parseStringLiteralNode(tokens);
        // <bool>
        } else if(tokens.getFirst().getToken().equals("True") || tokens.getFirst().getToken().equals("False")) {
            self.bool = BoolNode.parseBoolNode(tokens);
        } else {
            self.operand1 = parseOperandNode(tokens);
            // check if next is math_op or rel_op
            Token currentToken = tokens.getFirst();
            // <operand> <relop> <operand>
            if (currentToken.getTokenType() == REL_OP) {
                self.relop = tokens.removeFirst();
                // <operand> <mathop> <operand>
            } else if (currentToken.getTokenType() == MATH_OP) {
                self.mathop = tokens.removeFirst();
            // <operand>
            } else {
                return self;
            }

            // the second <operand> for <relop> and <mathop>
            self.operand2 = parseOperandNode(tokens);
        }

        return self;
    }

    @Override
    public String convertToJott() {
        if (this.bool != null) {
            return this.bool.convertToJott();
        } else if (this.string != null) {
            return this.string.convertToJott();
        } else {
            String operand1 = this.operand1.convertToJott();
            if(operand2 == null) { return operand1; }

            String operand2 = this.operand2.convertToJott();

            String operation = "";
            if (this.relop != null) {
                operation = this.relop.getToken();
            } else if (this.mathop != null) {
                operation = this.mathop.getToken();
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

    // if operand/string literal/bool, good (just call their validate)
    // if relop/mathop, check both sides are same type
    @Override
    public boolean validateTree() {
        String op1Class = operand1.getClass().getSimpleName().toLowerCase();
        String op2Class = operand2.getClass().getSimpleName().toLowerCase();

        if (op2Class.equals(op1Class)) {

        }
        return false;
    }

    public TypeEnum getType() {
        if(this.string != null) { return TypeEnum.String; }
        if(this.bool != null)   { return TypeEnum.Boolean; }
        if(this.relop != null)  { return TypeEnum.Boolean; }
        // mathop or just an operand
        else                    { return operand1.getType(); }
    }
}
