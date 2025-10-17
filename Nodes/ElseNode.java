package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing an else node
 *
 * @author Matthew Fiedler
 **/

public class ElseNode implements JottTree {
    BodyNode body;

    public static ElseNode parseElseNode(ArrayList<Token> tokens) {
        ElseNode self = new ElseNode();
        Token token = tokens.removeFirst();

        // "Else"
        if(token.getTokenType() != TokenType.ID_KEYWORD || !token.getToken().equals("Else")) {
            throw new Error("Was expecting 'Else'!");
        }

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Was expecting '{'!");
        }

        // <body>
        self.body = BodyNode.parseBodyNode(tokens);

        // }
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Was expecting '{'!");
        }

        return self;
    }

    @Override
    public String convertToJott() {
        return "Else { " + this.body.convertToJott() + " }";
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
