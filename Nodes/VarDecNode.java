package Nodes;

import project.FunctionVariableAccessor;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

public class VarDecNode implements JottTree {
    TypeNode typeNode;
    IDNode idNode;

    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) {
        VarDecNode varDecNode = new VarDecNode();

        // <type>
        varDecNode.typeNode = TypeNode.parseTypeNode(tokens);

        // <id>
        varDecNode.idNode = IDNode.parseIDNode(tokens);

        // ;
        Token token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.SEMICOLON) {
            throw new Error("Syntax Error\nExpected ';' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        return varDecNode;
    }

    @Override
    public String convertToJott() {
        return typeNode.convertToJott() + " " + idNode.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        return " ";
    }

    @Override
    public String convertToC() {
        return " ";
    }

    @Override
    public String convertToPython() {
        return " ";
    }

    @Override
    public boolean validateTree() {
        // checks id starts w/ lowercase
        this.idNode.validateTree();

        // local vars in this scope
        HashMap<String, TypeEnum> localVars = FunctionVariableAccessor.variables.get(FunctionVariableAccessor.currentScope);

        // check uniqueness
        if(localVars.containsKey(this.idNode.getToken())) {
            throw new Error("Semantic Error:\nMultiple declarations of variable `" + this.idNode.getToken() + "`\n" + this.idNode.id.getFilename() + ":" + this.idNode.id.getLineNum());
        }

        // add to list of local vars
        localVars.put(this.idNode.getToken(), this.typeNode.getType());

        return true;
    }
}
