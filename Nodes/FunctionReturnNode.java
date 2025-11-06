package Nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionReturnNode implements JottTree {
    Token type;
    static final String[] validTypes = new String[]{"Double", "Integer", "String", "Boolean", "Void"};

    public static FunctionReturnNode parseFunctionReturnNode(ArrayList<Token> tokens) {
        FunctionReturnNode self = new FunctionReturnNode();

        Token token = tokens.removeFirst();
        if(!Arrays.asList(validTypes).contains(token.getToken())) {
            throw new Error("Syntax Error\nExpected a valid return type (got " + token.toString() + ")\n" + token.getFilename() + ":" + token.getLineNum());
        }

        self.type = token;
        return self;
    }

    @Override
    public String convertToJott() {
        return type.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    public TypeEnum getType() {
        // if void, returns null!
        return switch (type.getToken()) {
            case "Integer" -> TypeEnum.Integer;
            case "Double" -> TypeEnum.Double;
            case "String" -> TypeEnum.String;
            case "Boolean" -> TypeEnum.Boolean;
            case "Void" -> TypeEnum.Void;
            default -> null;
        };
    }

    @Override
    public boolean validateTree() {
        return true;
    }
}
