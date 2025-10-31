package Nodes;
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
            return self;
        }

        // not epsilon
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
        return this.expr.validateTree();
    }
}
