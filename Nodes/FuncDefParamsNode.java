package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing a <func_def_params> node
 *
 * @author Matthew Fiedler
 **/

public class FuncDefParamsNode implements JottTree {
    IDNode id;
    TypeNode type;
    ArrayList<FuncDefParamsTNode> funcDefParamsTNodes;

    public static FuncDefParamsNode parseFuncDefParamsNode(ArrayList<Token> tokens) {
        FuncDefParamsNode self = new FuncDefParamsNode();

        // <id>
        self.id = IDNode.parseIDNode(tokens);

        // :
        Token token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.COLON) {
            throw new Error("Syntax Error\nExpected ':' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <type>
        self.type = TypeNode.parseTypeNode(tokens);

        // <func_def_params_t>*
        while(tokens.getFirst().getTokenType() == TokenType.COMMA) {
            self.funcDefParamsTNodes.add(FuncDefParamsTNode.parseFuncDefParamsTNode(tokens));
        }

        return self;
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append(id.convertToJott()).append(":").append(type.convertToJott());
        if(funcDefParamsTNodes != null) {
            for (FuncDefParamsTNode fdptn : funcDefParamsTNodes) {
                sb.append(fdptn.convertToJott());
            }
        }

        return sb.toString();
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

    // need to check all ids are unique and start with lowercase
    @Override
    public boolean validateTree() {
        return false;
    }
}
