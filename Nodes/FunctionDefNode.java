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
        // checks lowercase
        this.id.validateTree();

        // check not a reserved word
        if(Arrays.asList(new String[]{"print", "concat", "length"}).contains(this.id.convertToJott())) {
            throw new Error("Semantic Error:\nAttempted to redefine system function `" + this.id.convertToJott() + "`\n" + this.id.id.getFilename() + ":" + this.id.id.getLineNum());
        }

        this.funcDefParams.validateTree();
        this.fBody.validateTree();

        // if Void, ensure doesn't have "Return"
        if(this.returnType.type.getToken().equals("Void") && !this.fBody.doesntHaveReturn()) {
            throw new Error("Semantic Error:\nFound return statement in Void function `" + this.id.convertToJott() + "`\n" + this.id.id.getFilename() + ":" + this.id.id.getLineNum());
        // if not Void, ensure returns
        } else if(!this.fBody.isReturnable()) {
            throw new Error("Semantic Error:\nNot all path through non-Void function `" + this.id.convertToJott() + "` return a value\n" + this.id.id.getFilename() + ":" + this.id.id.getLineNum());
        }

        return true;
    }
}
