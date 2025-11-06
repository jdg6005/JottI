package Nodes;

import project.FunctionVariableAccessor;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ProgramNode implements JottTree {
    ArrayList<FunctionDefNode> funcDefNodes = new ArrayList<>();

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) {
        ProgramNode self = new ProgramNode();


        // in case we reach EOF early, we need this to print
        // the filename and last line number
        Token last = tokens.getLast();

        while(!tokens.isEmpty()) {
            try {
                self.funcDefNodes.add(FunctionDefNode.parseFunctionDefNode(tokens));
            } catch(NoSuchElementException e) {
                System.err.println("Syntax Error\nReached unexpected EOF\n" + last.getFilename() + ":" +  last.getLineNum());
                return null;
            } catch(Error e) {
                System.err.println(e.toString());
                return null;
            }
        }

        return self;
    }

    @Override
    public String convertToJott() {
        if(funcDefNodes.isEmpty())  { return ""; }

        StringBuilder sb = new StringBuilder();
        for(FunctionDefNode funcDefNode : funcDefNodes) {
            sb.append(funcDefNode.convertToJott()).append("\n\n");
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

    // TODO: add print, concat, and length to function accessor map
    @Override
    public boolean validateTree() {
        try {
            for (FunctionDefNode funcDef : funcDefNodes) {
                funcDef.validateTree();

                if (funcDef.id.id.getToken().equals("Main")) {
                    // make sure Main is declared as Void
                    if (funcDef.returnType.getType() != TypeEnum.Void) {
                        throw new Error("Semantic Error:\nMain must be declared as Void\n" + funcDef.id.getFilename() + ":" + funcDef.id.getLinenum());
                    }
                    // make sure Main has no parameters
                    if (funcDef.funcDefParams != null) {
                        throw new Error("Semantic Error:\nMain must not take any arguments\n" + funcDef.id.getFilename() + ":" + funcDef.id.getLinenum());
                    }
                }
            }
        } catch(Error e) {
            System.err.println(e.toString());
            return false;
        }

        return true;
    }
}
