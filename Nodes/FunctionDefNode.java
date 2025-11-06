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

    // (TODO: check unique??) id needs to start with a lowercase, be unique, and not be:
        // print
        // concat
        // length
    // func params and fbody must be valid
    // fbody must return from all codepaths
    @Override
    public boolean validateTree() {
        // checks lowercase
        this.id.validateTree();

        // check not a reserved word
        if(Arrays.asList(new String[]{"print", "concat", "length"}).contains(this.id.convertToJott())) {
            throw new Error("Semantic Error:\nAttempted to redefine system function `" + this.id.convertToJott() + "`\n" + this.id.id.getFilename() + ":" + this.id.id.getLineNum());
        }

        if(this.funcDefParams != null) {
            this.funcDefParams.validateTree();
        }
        this.fBody.validateTree();

        // if Void, ensure doesn't have "Return"
        if(this.returnType.type.getToken().equals("Void")) {
            if(!this.fBody.doesntHaveReturn()) {
                throw new Error("Semantic Error:\nFound return statement in Void function `" + this.id.convertToJott() + "`\n" + this.id.id.getFilename() + ":" + this.id.id.getLineNum());
            }
        // if not Void, ensure returns
        } else if(!this.fBody.isReturnable()) {
            throw new Error("Semantic Error:\nNot all path through non-Void function `" + this.id.convertToJott() + "` return a value\n" + this.id.id.getFilename() + ":" + this.id.id.getLineNum());
        }

        return true;
    }
}
