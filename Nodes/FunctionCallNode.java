package Nodes;

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

        // ::
        Token currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.FC_HEADER)) {
            throw new Error("Syntax Error\nFunction call should start with '::'!\n" + currToken.getFilename() + ":" + currToken.getLineNum());
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
        return "::" + this.id.convertToJott() + '[' + this.params.convertToJott() + ']';
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
