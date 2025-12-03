package Nodes;

import project.FunctionVariableAccessor;
import project.TypeAndValue;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IDNode implements OperandNode{
    Token id;

    public static IDNode parseIDNode(ArrayList<Token> tokens) {
        IDNode idNode = new IDNode();
        Token token = tokens.removeFirst();

        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new Error("Syntax Error\nExpected identifier (got " + token.toString() + ")\n" + token.getFilename() + ":" + token.getLineNum());
        }

        idNode.id = token;
        return idNode;
    }

    @Override
    public String convertToJott() {
        return this.id.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.id.getToken();
    }

    @Override
    public String convertToC() {
        return this.id.getToken();
    }

    @Override
    public String convertToPython() {
        return this.id.getToken();
    }

    // uniqueness checking should be done by one of:
    // FuncDefParams{T}Node (for param name uniqueness)
    // VarDecNode (for variable name uniqueness)
    // FuncDefNode (for func name uniqueness)
    @Override
    public boolean validateTree() {
        if(!Character.isLowerCase(this.id.getToken().charAt(0))) {
            throw new Error("Semantic Error:\nName must start with a lowercase letter\n" + this.id.getFilename() + ":" + this.id.getLineNum());
        }

        return true;
    }

    @Override
    public TypeEnum getType() {
        if (FunctionVariableAccessor.variables.containsKey(FunctionVariableAccessor.currentScope))
            return FunctionVariableAccessor.variables
                    .get(FunctionVariableAccessor.currentScope)
                    .get(this.getToken()).type;
        return null;
    }

    @Override
    public TypeAndValue getValue() {
        return FunctionVariableAccessor.getVariable(this.id.getToken());
    }
  
    @Override
    public String getFilename() {
        return this.id.getFilename();
    }

    @Override
    public int getLinenum() {
        return this.id.getLineNum();
    }

    public String getToken() {
        return this.id.getToken();
    }
}
