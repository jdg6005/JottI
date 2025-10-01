package provided;

import java.util.ArrayList;
import java.lang.Integer;

public class NumberNode {

    private final int number;

    public NumberNode(int number) {
        this.number = number;
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        if (tokens.getFirst().getTokenType() == TokenType.NUMBER) {
            Token t = tokens.removeLast();
            return new NumberNode(Integer.parseInt(t.getToken()));
        }
        System.err.println("Bro that aint a damn number");
        return null;
    }

    public String convertToJott() {
        return Integer.toString(this.number);
    }

}
