package provided;

import java.util.ArrayList;
import java.lang.Integer;

public class NumberNode implements JottTree {

    private final int number;

    public NumberNode(int number) {
        this.number = number;
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        if (tokens.getFirst().getTokenType() == TokenType.NUMBER) {
            Token t = tokens.removeFirst();
            return new NumberNode(Integer.parseInt(t.getToken()));
        }
        System.err.println("Bro that aint a damn number");
        return null;
    }

    @Override
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
