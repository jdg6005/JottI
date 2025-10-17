package Nodes;

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
