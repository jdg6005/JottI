package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing a bool
 *
 * @author Matthew Fiedler
 **/

public class BoolNode implements JottTree {
    boolean bool;

    public static BoolNode parseBoolNode(ArrayList<Token> tokens) {
        BoolNode self = new BoolNode();
        Token token = tokens.remove(0);

        // not a id/keyword
        if(token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new Error("Was expecting a bool!");
        }

        // not a valid boolean value
        if(!token.getToken().equals("True") &&  !token.getToken().equals("False")) {
            throw new Error("Was expecting a bool!");
        }

        self.bool = token.getToken().equals("True");
        return self;
    }

    @Override
    public String convertToJott() {
        return Boolean.toString(this.bool);
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
