package Nodes;

import project.FunctionVariableAccessor;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
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
        if (!FunctionVariableAccessor.functions.containsKey(this.id)) {
            throw new Error("Semantic Error:\nTried to call non-existent function `" + this.id.convertToJott() + "`\n" + this.id.getFilename() + ":" + this.id.getLinenum());
        }

        this.id.validateTree();
        this.params.validateTree();

        // types that the function should take
        ArrayList<TypeEnum> funcParams = FunctionVariableAccessor.functions.get(this.id).paramTypes;
        // types that the function was called with
        ArrayList<TypeEnum> calledParams = this.params.getTypes();

        for(int i = 0; (i < funcParams.size()) && (i < calledParams.size()); i++) {
            if(funcParams.get(i) != calledParams.get(i)) {
                throw new Error("Semantic Error:\nArgument " + i + " of function `" +
                        this.id.convertToJott() + "` should take a " + funcParams.get(i) +
                        " but was called with a " + calledParams.get(i) + "\n" +
                        this.getFilename() + ":" + this.getLinenum());
            }
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

    @Override
    public String getFilename() {
        return this.id.getFilename();
    }

    @Override
    public int getLinenum() {
        return this.id.getLinenum();
    }
}
