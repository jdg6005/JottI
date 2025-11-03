package Nodes;

import project.FunctionList;
import project.FunctionVariableAccessor;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class is responsible for parsing a function definition node
 *
 * @author Matthew Fiedler
 **/

public class FunctionDefNode implements JottTree {
    IDNode id;
    FuncDefParamsNode funcDefParams = null;
    FunctionReturnNode returnType;
    FBodyNode fBody;

    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) {
        FunctionDefNode self = new FunctionDefNode();

        // Def
        Token token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.ID_KEYWORD || !token.getToken().equals("Def")) {
            throw new Error("Syntax Error\nExpected 'Def' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // [
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACKET) {
            throw new Error("Syntax Error\nExpected '[' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <func_def_params>
        if(tokens.getFirst().getTokenType() != TokenType.R_BRACKET) {
            self.funcDefParams = FuncDefParamsNode.parseFuncDefParamsNode(tokens);
        }

        // ]
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACKET) {
            throw new Error("Syntax Error\nExpected ']' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // :
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.COLON) {
            throw new Error("Syntax Error\nExpected ':' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <function_return>
        self.returnType = FunctionReturnNode.parseFunctionReturnNode(tokens);

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Syntax Error\nExpected '{' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <f_body>
        self.fBody = FBodyNode.parseFBodyNode(tokens);

        // }
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACE) {
            throw new Error("Syntax Error\nExpected '}' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        HashMap<IDNode, TypeNode> params = new HashMap<>();
        if (self.funcDefParams != null) {
            params.put(self.funcDefParams.id, self.funcDefParams.type);
            if (self.funcDefParams.funcDefParamsTNode != null) {
                for (FuncDefParamsTNode param : self.funcDefParams.funcDefParamsTNode) {
                    params.put(param.id, param.type);
                }
            }
        }
        FunctionReturnNode ret = self.returnType;

        FunctionList newFunc = new FunctionList(params, ret);
        FunctionVariableAccessor.functionList.put(self.id, newFunc);


        HashMap<IDNode, TypeNode> variables = new HashMap<>();
        for (VarDecNode var: self.fBody.varDecNodes) {
            variables.put(var.idNode, var.typeNode);
        }

        FunctionVariableAccessor.variableList.put(self.id, variables);

        return self;
    }

    @Override
    public String convertToJott() {
        return "Def " + id.convertToJott() + " [" + (funcDefParams == null ? "" : funcDefParams.convertToJott()) + "]: " + returnType.convertToJott() + " {\n\t" + fBody.convertToJott() + "\n}";
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

    // TODO: fbody must return from all codepaths
    @Override
    public boolean validateTree() {
        if(Arrays.asList(new String[]{"print", "concat", "length"}).contains(this.id.id.getToken())) { return false; }

        // set scope for when we now parse function body
        FunctionVariableAccessor.currentScope = this.id;
        return id.validateTree() && funcDefParams.validateTree() && returnType.validateTree() && fBody.validateTree();
    }
}
