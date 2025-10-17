package Nodes;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing return statements
 *
 * @author Elijah Lederman
 **/

public class ReturnStmtNode implements BodyStmt {
    private ExprNode expr;

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) {
        ReturnStmtNode self = new ReturnStmtNode();

        Token currToken = tokens.removeFirst();
        if (!currToken.getTokenType().equals(TokenType.STRING) || !currToken.getToken().equals("Return")) {
            throw new Error("Return statement should start with 'Return'!");
        }

        self.expr = ExprNode.parseExprNode(tokens);

        currToken = tokens.removeFirst();
        if (!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Return statement should end with ';'!");
        }

        return self;
    }
}
