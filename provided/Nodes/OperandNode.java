package provided.Nodes;


import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static provided.Nodes.FunctionCallNode.parseFunctionCallNode;
import static provided.Nodes.IDNode.parseIDNode;
import static provided.Nodes.NumberNode.parseNumberNode;

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
        return null;
    }
}
