package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing while loops
 *
 * @author Elijah Lederman
 **/

public class WhileNode implements JottTree {
    private BodyNode body;
    private ExprNode expr;

    public static WhileNode parseWhileNode(ArrayList<Token> tokens) {
        WhileNode self = new WhileNode();

        Token currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.ID_KEYWORD) || !currToken.getToken().equals("While")) {
            throw new Error("Syntax Error\nWhile loop should start with 'While' (got " + currToken.toString() + "\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.L_BRACKET)) {
            throw new Error("Syntax Error\n'While' should be followed by a '[' (got " + currToken.toString() + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        self.expr = ExprNode.parseExprNode(tokens);

        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            throw new Error("Expression should be followed by a ']' (got " + currToken.toString() + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.L_BRACE)) {
            throw new Error("']' should be followed by a '{' (got " + currToken.toString() + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        self.body = BodyNode.parseBodyNode(tokens);

        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.R_BRACE)) {
            throw new Error("Body should be followed by a '}' (got " + currToken.toString() + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        return self;
    }

    @Override
    public String convertToJott() {
        return "While [" + this.expr.convertToJott() + "] { " + this.body.convertToJott() + "}";
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
