package provided.Nodes;

import provided.Token;
import provided.TokenType;
import provided.ParamsNode;

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
        Token currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Function call should start with semicolon!");
        }

        // : (second)
        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Function call should start with two semicolons!");
        }

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // [
        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.L_BRACKET)) {
            throw new Error("Function name should be followed by a left bracket!");
        }

        // <params>
        self.params = ParamsNode.parseParamsNode(tokens);

        // ]
        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            throw new Error("Function parameters should be followed by a right bracket!");
        }

        return self;
    }

    @Override
    public String convertToJott() {
        String str = "::" + this.id.convertToJott() +
                '[' +
                this.params.convertToJott() +
                ']';

        return str;
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
