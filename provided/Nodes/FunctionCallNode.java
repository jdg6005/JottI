package provided.Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing function calls
 *
 * @author Matthew Fiedler, Miles Todtfeld
 **/

public class FunctionCallNode implements OperandNode {
    private IDNode id;
    private ParamsNode params;

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) {
        FunctionCallNode self = new FunctionCallNode();

        // :
        Token currToken = tokens.remove(0);
        if(!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Function call should start with semicolon!");
        }

        // : (second)
        currToken = tokens.remove(0);
        if(!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Function call should start with two semicolons!");
        }

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // [
        currToken = tokens.remove(0);
        if(!currToken.getTokenType().equals(TokenType.L_BRACKET)) {
            throw new Error("Function name should be followed by a left bracket!");
        }

        // <params>
        self.params = ParamsNode.parseParamsNode(params);

        // ]
        currToken = tokens.remove(0);
        if(!currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            throw new Error("Function parameters should be followed by a right bracket!");
        }

        return self;
    }

    @Override
    public String convertToJott() {
        StringBuilder str = new StringBuilder("::");
        str.append(this.id.convertToJott());
        str.append('[');
        str.append(this.params.convertToJott());
        str.append(']');

        return str.toString();
    }

    @Override
    public String convertToJava(String className) {
        throw new UnsupportedOperationException("NOT SUPPORTED.");
    }

    @Override
    public String convertToC() {
        throw new UnsupportedOperationException("NOT SUPPORTED.");
    }

    @Override
    public String convertToPython() {
        throw new UnsupportedOperationException("NOT SUPPORTED.");
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
