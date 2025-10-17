package Nodes;


import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

import static Nodes.FunctionCallNode.parseFunctionCallNode;
import static Nodes.IDNode.parseIDNode;
import static Nodes.NumberNode.parseNumberNode;

public interface OperandNode extends JottTree {

    public static OperandNode parseOperandNode(ArrayList<Token> tokens) {
        return switch (tokens.getFirst().getTokenType()) {
            case NUMBER, MATH_OP -> parseNumberNode(tokens);
            case ID_KEYWORD -> parseIDNode(tokens);
            case SEMICOLON -> parseFunctionCallNode(tokens);
            default -> null;
        };
    }
}
