package Nodes;

import project.TypeAndValue;
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
        Token token = tokens.removeFirst();

        // not a id/keyword
        if(token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new Error("Syntax Error\nWas expecting a bool got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // not a valid boolean value
        if(!token.getToken().equals("True") &&  !token.getToken().equals("False")) {
            throw new Error("Syntax Error\nWas expecting a bool got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
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
        return true;
    }

	@Override
	public TypeAndValue execute() {
		return null;
	}

	// --------------------------------

    public boolean getValue() {
        return bool;
    }
}
