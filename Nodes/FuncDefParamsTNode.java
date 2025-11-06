package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing a <func_def_params_t> node
 *
 * @author Matthew Fiedler
 **/

public class FuncDefParamsTNode implements JottTree {
    IDNode id;
    TypeNode type;

    public static FuncDefParamsTNode parseFuncDefParamsTNode(ArrayList<Token> tokens) {
        FuncDefParamsTNode self = new FuncDefParamsTNode();

        // ,
        Token token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.COMMA) {
            throw new Error("Syntax Error\nExpected ',' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // :
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.COLON) {
            throw new Error("Syntax Error\nExpected ':' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <type>
        self.type = TypeNode.parseTypeNode(tokens);

        return self;
    }

    @Override
    public String convertToJott() {
        return ", " + this.id.convertToJott() + ":" +  this.type.convertToJott();
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
        return this.type.getType();
    }

    // note: uniqueness for ID checked by FuncDefParamsNode.validateTree()
    @Override
    public boolean validateTree() {
        // tautology
        this.type.validateTree();
        // ensures ID starts w/ lowercase
        this.id.validateTree();

        return true;
    }
}
