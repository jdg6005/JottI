package Nodes;

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

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        NumberNode self = new NumberNode();

        if (tokens.getFirst().getTokenType() == TokenType.NUMBER) {
            Token t = tokens.removeFirst();
            if(t.getToken().contains(".")) {
                self.doubleNumber = Double.parseDouble(t.getToken());
                self.isInt = false;
            } else {
                self.intNumber = Integer.parseInt(t.getToken());
                self.isInt = true;
            }
        } else {
            System.err.println("Bro that aint a damn number");
            return null;
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
        return false;
    }

}
