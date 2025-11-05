package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Arrays;

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
            throw new Error("Syntax Error\nExpected 'Def' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // [
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACKET) {
            throw new Error("Syntax Error\nExpected '[' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <func_def_params>
        if(tokens.getFirst().getTokenType() != TokenType.R_BRACKET) {
            self.funcDefParams = FuncDefParamsNode.parseFuncDefParamsNode(tokens);
        }

        // ]
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACKET) {
            throw new Error("Syntax Error\nExpected ']' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // :
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.COLON) {
            throw new Error("Syntax Error\nExpected ':' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <function_return>
        self.returnType = FunctionReturnNode.parseFunctionReturnNode(tokens);

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Syntax Error\nExpected '{' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <f_body>
        self.fBody = FBodyNode.parseFBodyNode(tokens);

        // }
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACE) {
            throw new Error("Syntax Error\nExpected '}' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

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
        boolean isValid = true;

        // checks lowercase
        isValid &= this.id.validateTree();
        // check not a reserved word
        isValid &= !(Arrays.asList(new String[]{"print", "concat", "length"}).contains(this.id.convertToJott()));

        isValid &= this.funcDefParams.validateTree();
        isValid &= this.fBody.validateTree();

        // if Void, ensure doesn't have "Return"
        if(this.returnType.type.getToken().equals("Void")) {
            isValid = this.fBody.doesntHaveReturn();
        // if not Void, ensure returns
        } else {
            isValid = this.fBody.isReturnable();
        }

        return isValid;
    }
}
