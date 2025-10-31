package Nodes;


import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

import static Nodes.FunctionCallNode.parseFunctionCallNode;
import static Nodes.IDNode.parseIDNode;
import static Nodes.NumberNode.parseNumberNode;

public interface OperandNode extends JottTree {

    public static OperandNode parseOperandNode(ArrayList<Token> tokens) {
        Token token = tokens.getFirst();
        return switch (token.getTokenType()) {
            case NUMBER, MATH_OP -> parseNumberNode(tokens);
            case ID_KEYWORD -> parseIDNode(tokens);
            case FC_HEADER -> parseFunctionCallNode(tokens);
            default -> throw new Error("Syntax Error\nExpeted operand\n" + token.getFilename() + ":" + token.getLineNum());
        };
    }

    public abstract TypeEnum getType();

    public abstract double getValue();
}
