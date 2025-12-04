package Nodes;

import project.TypeAndValue;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Objects;

import project.UninitializedValueException;

import static Nodes.OperandNode.parseOperandNode;
import static provided.TokenType.*;

/**
 * This class is responsible for parsing expression calls
 *
 * @author Elijah Lederman
 **/

public class ExprNode implements JottTree {
    public OperandNode operand1;
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
            if (currentToken.getTokenType() == REL_OP)
                self.relop = tokens.removeFirst();
            // <operand> <mathop> <operand>
            else if (currentToken.getTokenType() == MATH_OP)
                self.mathop = tokens.removeFirst();
            // <operand>
            else return self;

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
        // string literal
        if (this.string != null) {
            this.string.validateTree();
        }
        // bool
        else if (this.bool != null) {
            this.bool.validateTree();
        }
        // relop
        else if (this.relop != null) {
            if (this.operand1.getType() != this.operand2.getType()) {
                throw new Error("Semantic Error:\nOperands of `" + this.relop.getToken() + "` don't have the same type\n" + this.relop.getFilename() + ":" + this.relop.getLineNum());
            }
        }
        // mathop
        else if (this.mathop != null) {
            if (this.operand1.getType() != this.operand2.getType()) {
                throw new Error("Semantic Error:\nOperands of `" + this.mathop.getToken() + "` don't have the same type\n" + this.mathop.getFilename() + ":" + this.mathop.getLineNum());
            }
            TypeAndValue operand2val = operand2.getValue();
            if (this.operand2 instanceof NumberNode &&
                    (operand2val.type == TypeEnum.Integer && operand2val.getIntValue() == 0
                            || operand2val.type == TypeEnum.Double && operand2val.getDoubleValue() == 0)
            ) {
                throw new Error("Semantic Error:\nDivision by 0!\n" + this.mathop.getFilename() + ":" + this.mathop.getLineNum());
            }
        }
        // just an operand
        else {
            this.operand1.validateTree();
        }

        return true;
    }

    public TypeEnum getType() {
        if(this.string != null) { return TypeEnum.String; }
        if(this.bool != null)   { return TypeEnum.Boolean; }
        if(this.relop != null)  { return TypeEnum.Boolean; }
        // mathop or just an operand
        else                    { return operand1.getType(); }
    }

    // --------------------------------

    public TypeAndValue getValue() {
        try {
            if (this.string != null) {
                return new TypeAndValue(this.string.getValue());
            } else if (this.bool != null) {
                return new TypeAndValue(this.bool.getValue());
            } else if (this.relop != null) {
                TypeAndValue value1 = this.operand1.getValue();
                TypeAndValue value2 = this.operand2.getValue();
                return switch (this.relop.getToken()) {
                    case "==" -> value1.equals(value2);
                    case "!=" -> value1.notEquals(value2);
                    case "<" -> value1.lessThan(value2);
                    case "<=" -> value1.lessThanOrEqual(value2);
                    case ">" -> value1.greaterThan(value2);
                    case ">=" -> value1.greaterThanOrEqual(value2);
                    default -> null;
                };
            } else if (this.mathop != null) {
                TypeAndValue value1 = this.operand1.getValue();
                TypeAndValue value2 = this.operand2.getValue();
                return switch (this.mathop.getToken()) {
                    case "+" -> value1.plus(value2);
                    case "-" -> value1.minus(value2);
                    case "*" -> value1.times(value2);
                    case "/" -> value1.dividedBy(value2, this.mathop);
                    default -> null;
                };
            } else {
                return this.operand1.getValue();
            }
        } catch(UninitializedValueException e) {
            throw new Error("Runtime Error:\nAttempted to use uninitialized value!\n" + this.operand1.getFilename() + ":" + this.operand1.getLinenum());
        }
    }
}
