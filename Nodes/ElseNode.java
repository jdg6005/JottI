package Nodes;

import project.TypeAndValue;
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

        // check for "Else"
        if(tokens.getFirst().getTokenType() != TokenType.ID_KEYWORD || !tokens.getFirst().getToken().equals("Else")) {
            return null;
        }

        // "Else"
        Token token = tokens.removeFirst();

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Syntax Error\nWas expecting '{' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
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
        this.body.validateTree();
        return true;
    }

    public TypeAndValue execute() {
        return this.body.execute();
    }
}
