package Nodes;

import project.TypeAndValue;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.lang.Integer;

/**
 * This class is responsible for parsing numbers
 *
 * @author Elijah Lederman
 **/

public class NumberNode implements OperandNode {

    private int intNumber;
    private double doubleNumber;
    private boolean isInt;
    private Token numberToken;

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        NumberNode self = new NumberNode();

        if (tokens.getFirst().getTokenType() == TokenType.NUMBER) {
            Token t = tokens.removeFirst();
            // so we store the file name / line number
            self.numberToken = t;
            if(t.getToken().contains(".")) {
                self.doubleNumber = Double.parseDouble(t.getToken());
                self.isInt = false;
            } else {
                self.intNumber = Integer.parseInt(t.getToken());
                self.isInt = true;
            }
        } else {
            throw new Error("Syntax Error\nWas expecting number got " + tokens.getFirst().toString() + "\n" + tokens.getFirst().getFilename() + ":" + tokens.getFirst().getLineNum());
        }
        return self;
    }

    public String convertToJott() {
        if(isInt) { return Integer.toString(intNumber); }
        else      { return Double.toString(doubleNumber); }
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
        return true;
    }

	@Override
	public TypeAndValue execute() {
		return null;
	}

	@Override
    public TypeEnum getType() {
        if(isInt) { return TypeEnum.Integer; }
        else      { return TypeEnum.Double;  }
    }

    @Override
    public TypeAndValue getValue() {
        return isInt? new TypeAndValue(intNumber) : new TypeAndValue(doubleNumber);
    }

    @Override
    public String getFilename() {
        return this.numberToken.getFilename();
    }

    @Override
    public int getLinenum() {
        return this.numberToken.getLineNum();
    }
}
