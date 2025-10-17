package Nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ProgramNode implements JottTree {
    ArrayList<FunctionDefNode> funcDefNodes;

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) {
        ProgramNode self = new ProgramNode();

        while(!tokens.isEmpty()) {
            self.funcDefNodes.add(FunctionDefNode.parseFunctionDefNode(tokens));
        }

        return self;
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for(FunctionDefNode funcDefNode : funcDefNodes) {
            sb.append(funcDefNode.convertToJott()).append("\n");
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
