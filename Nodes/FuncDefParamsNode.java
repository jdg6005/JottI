package Nodes;

import project.FunctionVariableAccessor;
import project.TypeAndValue;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<TypeEnum> getTypes() {
        ArrayList<TypeEnum> types = new ArrayList<>();
        types.add(this.getType());

        if(this.funcDefParamsTNodes != null) {
            for(FuncDefParamsTNode fdpt : this.funcDefParamsTNodes) {
                types.add(fdpt.getType());
            }
        }

        return types;
    }

    @Override
    public boolean validateTree() {
        // tautology
        this.type.validateTree();
        // ensures ID starts w/ lowercase
        this.id.validateTree();

        HashMap<String, TypeAndValue> localVars = FunctionVariableAccessor.variables.get(FunctionVariableAccessor.currentScope);
        localVars.put(this.id.getToken(), new TypeAndValue(this.type.getType()));

        // validates all the other parameters
        if(this.funcDefParamsTNodes != null) {
            for (FuncDefParamsTNode fdpt : this.funcDefParamsTNodes) {
                // ensures all IDs are unique
                if (localVars.containsKey(fdpt.id.getToken())) {
                    throw new Error("Semantic Error:\nMultiple parameters named `" + fdpt.id.getToken() + "`\n" + fdpt.id.getFilename() + ":" + fdpt.id.getLinenum());
                }
                // if unique, add it to the set
                localVars.put(fdpt.id.getToken(), new TypeAndValue(fdpt.type.getType()));

                // validate the
                fdpt.validateTree();
            }
        }

        return true;
    }
}
