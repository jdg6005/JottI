package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing a else if statement node
 *
 * @author Matthew Fiedler
 **/

public class ElseIfNode implements JottTree {
    ExprNode expr;
    BodyNode body;

    public static ElseIfNode parseElseIfNode(ArrayList<Token> tokens) {
        ElseIfNode self = new ElseIfNode();
        Token token = tokens.removeFirst();

        // "Elseif"
        if(token.getTokenType() != TokenType.ID_KEYWORD || !token.getToken().equals("Elseif")) {
            throw new Error("Syntax Error\nWas expecting 'Elseif' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // [
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACKET) {
            throw new Error("Syntax Error\nWas expecting '[' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <expr>
        self.expr = ExprNode.parseExprNode(tokens);

        // ]
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACKET) {
            throw new Error("Syntax Error\nWas expecting ']' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Syntax Error\nWas expecting '{' got " + token.toString() + "\n"  + token.getFilename() + ":" + token.getLineNum());
        }

        // <body>
        self.body = BodyNode.parseBodyNode(tokens);

        // }
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACE) {
            throw new Error("Syntax Error\nWas expecting '}' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        return self;
    }

    @Override
    public String convertToJott() {
        return "Elseif [ " + this.expr.convertToJott() + " ] { " + this.body.convertToJott() + " }";
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

    // needs to check that expr is valid and returns a boolean
    // as well as check that body is valid
    @Override
    public boolean validateTree() {
        return false;
    }
}
