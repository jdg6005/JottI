package Nodes;

import project.FunctionVariableAccessor;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.function.Function;

public class IDNode implements OperandNode{
    Token id;

    public static IDNode parseIDNode(ArrayList<Token> tokens) {
        IDNode idNode = new IDNode();
        Token token = tokens.removeFirst();

        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new Error("Syntax Error\nExpected identifier (got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
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
        return Character.isLowerCase(this.id.getToken().charAt(0));
    }

    @Override
    public TypeEnum getType() {
        if (FunctionVariableAccessor.variableList.containsKey(FunctionVariableAccessor.currentScope)) {
            String idType = FunctionVariableAccessor.variableList.get(FunctionVariableAccessor.currentScope).get(this).convertToJott();
            return switch (idType) {
                case "String" -> TypeEnum.String;
                case "Integer" -> TypeEnum.Integer;
                case "Double" -> TypeEnum.Double;
                default -> TypeEnum.Boolean;
            };
        }
        return null;
    }

    @Override
    public double getValue() {
        return 0;
    }
}
