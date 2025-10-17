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

    private int number;

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        NumberNode self = new NumberNode();

        if (tokens.getFirst().getTokenType() == TokenType.NUMBER) {
            Token t = tokens.removeFirst();
            self.number = Integer.parseInt(t.getToken());
        } else if (tokens.getFirst().getTokenType() == TokenType.MATH_OP) {
            Token t = tokens.removeFirst();
            if (t.getToken().equals("-")) {
                self.number = -Integer.parseInt(t.getToken());
            }
        } else {
            System.err.println("Bro that aint a damn number");
            return null;
        }
        return self;
    }

    public String convertToJott() {
        return Integer.toString(this.number);
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
