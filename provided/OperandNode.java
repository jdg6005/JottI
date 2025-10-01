package provided;


import java.util.ArrayList;

public interface OperandNode extends JottTree {

    public static OperandNode parseOperand(ArrayList<Token> tokens) {
        if (tokens.getLast().getTokenType() == TokenType.NUMBER) {
            tokens.removeLast();
            return parseIDNode(tokens);

        } else if (tokens.getLast().getTokenType() == TokenType.STRING) {
            tokens.removeLast();
            return parseNumberNode(tokens);
        } else if (tokens.getLast().getTokenType() == TokenType.SEMICOLON) {
            while (tokens.getLast().getTokenType() != TokenType.COLON) {
                tokens.removeLast();
            }
            tokens.removeLast();
            return parseFunctionCallNode(tokens);
        }
    }
}
