package Nodes;
import project.FunctionVariableAccessor;
import project.TypeAndValue;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing return statements
 *
 * @author Elijah Lederman
 **/

public class ReturnStmtNode implements JottTree {
    private ExprNode expr;

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) {
        ReturnStmtNode self = new ReturnStmtNode();

        // check epsilon
        Token currToken = tokens.getFirst();
        if (!currToken.getTokenType().equals(TokenType.ID_KEYWORD) || !currToken.getToken().equals("Return")) {
            return null;
        }

        // Return
        tokens.removeFirst();

        // <expr>
        self.expr = ExprNode.parseExprNode(tokens);

        // ;
        currToken = tokens.removeFirst();
        if (!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Syntax Error\nReturn statement should end with ';'(got " + currToken.toString() + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        return self;
    }

    @Override
    public String convertToJott() {
        if(expr == null) { return ""; }
        else             { return "Return " + expr.convertToJott() + ";"; }
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
        this.expr.validateTree();

        // checks that return type matches expected
        TypeEnum declaredType = FunctionVariableAccessor.functions.get(FunctionVariableAccessor.currentScope).returnType;
        if(this.expr.getType() != declaredType) {
            throw new Error("Semantic Error:\nTried to return a " + this.expr.getType() + " from a function that returns " + declaredType + "\n" + this.expr.operand1.getFilename() + ":" + this.expr.operand1.getLinenum());
        }

        return true;
    }

    // --------------------------------

    public TypeAndValue execute() {
        return this.expr.getValue();
    }
}
