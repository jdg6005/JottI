package Nodes;

import project.FunctionVariableAccessor;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

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
            throw new Error("Syntax Error\nFunction call should start with '::' got " + currToken + "\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // [
        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.L_BRACKET)) {
            throw new Error("Syntax Error\nFunction name should be followed by a left bracket (got " + currToken + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        // <params>
        self.params = ParamsNode.parseParamsNode(tokens);


        // ]
        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            throw new Error("Syntax Error\nFunction parameters should be followed by a right bracket (got " + currToken + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
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

    // need to check parameter types correct for function
    @Override
    public boolean validateTree() {
        if (!(FunctionVariableAccessor.functions.containsKey(this.id) && this.id.validateTree() && this.params.validateTree()))
            return false;

        ArrayList<TypeEnum> funcParams = FunctionVariableAccessor.functions.get(this.id).paramTypes;

        if (funcParams.get(0) != this.params.getType())
            return false;

        for (int i = 1; i < funcParams.size(); i++) {
            if (funcParams.get(i) != params.paramsTNodes.get(i - 1).getType())
                return false;
        }

        return true;
    }

    @Override
    public TypeEnum getType() {
        if (FunctionVariableAccessor.functions.containsKey(this.id))
            return FunctionVariableAccessor.functions.get(this.id).returnType;
        return null;
    }

    @Override
    public double getValue() {
        return 0;
    }
}
