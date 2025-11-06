package Nodes;

import project.FunctionInfo;
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
 * @author Matthew Fiedler, Miles Todtfeld
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

        // Add to FunctionVariableAccessor:

        ArrayList<TypeEnum> paramTypes = new ArrayList<>();
        if (self.funcDefParams != null) {
            paramTypes.add(self.funcDefParams.type.getType());
            if (self.funcDefParams.funcDefParamsTNodes != null) {
                for (FuncDefParamsTNode param : self.funcDefParams.funcDefParamsTNodes) {
                    paramTypes.add(param.type.getType());
                }
            }
        }
        TypeEnum returnType = self.returnType.getType();

        FunctionInfo newFunc = new FunctionInfo(paramTypes, returnType);
        FunctionVariableAccessor.functions.put(self.id, newFunc);

        // Add variables declared in function body to FunctionVariableAccessor:
        HashMap<IDNode, TypeEnum> variables = new HashMap<>();
        for (VarDecNode var: self.fBody.varDecNodes) {
            variables.put(var.idNode, var.typeNode.getType());
        }

        // Add parameters to FunctionVariableAccessor (parameters count as local variables)
        if (self.funcDefParams != null) {
            variables.put(self.funcDefParams.id, self.funcDefParams.getType());
            if (self.funcDefParams.funcDefParamsTNodes != null) {
                for (FuncDefParamsTNode param : self.funcDefParams.funcDefParamsTNodes) {
                    variables.put(param.id, param.getType());
                }
            }
        }

        FunctionVariableAccessor.variables.put(self.id, variables);

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
        if(Arrays.asList(new String[]{"print", "concat", "length"}).contains(this.id.id.getToken()))
            return false;

        // main function should take no args and be void
        if(this.id.id.getToken().equals("main")) {
            if(this.funcDefParams != null) return false;
            if(this.returnType.getType() != null) return false;
        }

        // set scope for when we now parse function body
        FunctionVariableAccessor.currentScope = this.id;
        return (
                id.validateTree()
                && (funcDefParams == null || funcDefParams.validateTree())
                && returnType.validateTree()
                && fBody.validateTree()
        );
    }
}
