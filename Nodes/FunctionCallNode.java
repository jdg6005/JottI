package Nodes;

import project.FunctionInfo;
import project.FunctionVariableAccessor;
import project.TypeAndValue;
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
        if(tokens.getFirst().getTokenType() != TokenType.R_BRACKET) {
            self.params = ParamsNode.parseParamsNode(tokens);
        }


        // ]
        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.R_BRACKET)) {
            throw new Error("Syntax Error\nFunction parameters should be followed by a right bracket (got " + currToken + ")\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }

        return self;
    }

    @Override
    public String convertToJott() {
        String paramsStr = this.params == null ? "" : this.params.convertToJott();
        return "::" + this.id.convertToJott() + '[' + paramsStr + ']';
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
        if (!FunctionVariableAccessor.functions.containsKey(this.id.getToken())) {
            throw new Error("Semantic Error:\nTried to call non-existent function `" + this.id.convertToJott() + "`\n" + this.id.getFilename() + ":" + this.id.getLinenum());
        }

        this.id.validateTree();
        if(this.params != null) {
            this.params.validateTree();
        }

        // special treatment since print can take any non-Void type
        if(id.getToken().equals("print")) {
            ArrayList<TypeEnum> calledParams = this.params.getTypes();
            // ensure only one arg
            if(calledParams.size() != 1) {
                throw new Error("Semantic Error:\n`print` takes 1 argument, but was provided " + calledParams.size() + "\n" + this.id.getFilename() + ":" + this.id.getLinenum());
            }
            // ensure arg is not void
            if(calledParams.getFirst() == TypeEnum.Void) {
                throw new Error("Semantic Error:\nArgument 0 of function `print` should take a non-Void but was called with a Void\n" + this.id.getFilename() + ":" + this.id.getLinenum());
            }

            return true;
        }

        // types that the function should take
        ArrayList<TypeEnum> funcParams = FunctionVariableAccessor.functions.get(this.id.getToken()).paramTypes;
        funcParams = funcParams == null ? new ArrayList<>() : funcParams;
        // types that the function was called with
        ArrayList<TypeEnum> calledParams = new ArrayList<>();
        if(this.params != null) {
            calledParams = this.params.getTypes();
        }

        if (funcParams.size() != calledParams.size()) {
            throw new Error("Semantic Error:\n`" + this.id.getToken() + "` takes " + funcParams.size() + " arguments, but was provided " + calledParams.size() + "\n" + this.id.getFilename() + ":" + this.id.getLinenum());
        }

        for (int i = 0; i < funcParams.size(); i++) {
            if (funcParams.get(i) != calledParams.get(i)) {
                throw new Error("Semantic Error:\nArgument " + i + " of function `" +
                        this.id.convertToJott() + "` should take a " + funcParams.get(i) +
                        " but was called with a " + calledParams.get(i) + "\n" +
                        this.getFilename() + ":" + this.getLinenum());
            }
        }

        return true;
    }

	@Override
	public TypeAndValue execute() {
		return null;
	}

	@Override
    public TypeEnum getType() {
        if (FunctionVariableAccessor.functions.containsKey(this.id.getToken()))
            return FunctionVariableAccessor.functions.get(this.id.getToken()).returnType;
        return null;
    }

    @Override
    public String getFilename() {
        return this.id.getFilename();
    }

    @Override
    public int getLinenum() {
        return this.id.getLinenum();
    }

    // --------------------------------

    public TypeAndValue getValue() {
        return FunctionVariableAccessor.callFunction(this.id.getToken(), this.params.getValue());
    }
}
