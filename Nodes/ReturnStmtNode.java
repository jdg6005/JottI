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

        Token currToken = tokens.removeFirst();
        if (!currToken.getTokenType().equals(TokenType.ID_KEYWORD) || !currToken.getToken().equals("Return")) {
            throw new Error("Return statement should start with 'Return'!");
        }

        self.expr = ExprNode.parseExprNode(tokens);

        currToken = tokens.removeFirst();
        if (!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Return statement should end with ';'!");
        }

        return self;
    }

    @Override
    public String convertToJott() {
        return "";
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
