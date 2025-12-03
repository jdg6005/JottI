package Nodes;

import Nodes.FunctionDefNode;
import Nodes.VarDecNode;
import project.FunctionVariableAccessor;
import project.TypeAndValue;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.Arrays;

public class FBodyNode implements JottTree {
    ArrayList<VarDecNode> varDecNodes= new ArrayList<>();
    BodyNode bodyNode;

    public static FBodyNode parseFBodyNode(ArrayList<Token> tokens) {
        FBodyNode fBodyNode = new FBodyNode();

        while(Arrays.asList("Double", "Integer", "String", "Boolean").contains(tokens.get(0).getToken())) {
            fBodyNode.varDecNodes.add(VarDecNode.parseVarDecNode(tokens));
        }
        fBodyNode.bodyNode = BodyNode.parseBodyNode(tokens);

        return fBodyNode;
    }

    @Override
    public String convertToJott() {
        StringBuilder str = new StringBuilder();
        for (VarDecNode varDecNode : varDecNodes) {
            str.append(varDecNode.convertToJott()).append(" ");
        }
        str.append(bodyNode.convertToJott());
        return str.toString();
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        for(VarDecNode varDecNode : this.varDecNodes) {
            varDecNode.validateTree();
        }

        this.bodyNode.validateTree();

        return true;
    }

    public boolean isReturnable() {
        return this.bodyNode.isReturnable();
    }

    public boolean doesntHaveReturn() {
        return this.bodyNode.doesntHaveReturn();
    }

    // --------------------------------

    public TypeAndValue execute() {
        return this.bodyNode.execute();
    }
}
