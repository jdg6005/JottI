package Nodes;

import project.FunctionInfo;
import project.FunctionVariableAccessor;
import provided.JottTree;
import provided.Token;

import java.util.*;

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
                System.err.println(e);
                e.printStackTrace();
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

    @Override
    public boolean validateTree() {
        FunctionVariableAccessor.functions = new HashMap<>();
        FunctionVariableAccessor.variables = new HashMap<>();
        FunctionVariableAccessor.currentScope = "";

        // TODO: implement these 3 functions:
        FunctionInfo printInfo = new FunctionInfo("print", new ArrayList<>(List.of()), TypeEnum.Void, null, null);
        FunctionVariableAccessor.functions.put("print", printInfo);

        FunctionInfo concatInfo = new FunctionInfo("concat", new ArrayList<>(List.of(TypeEnum.String, TypeEnum.String)), TypeEnum.String, null, null);
        FunctionVariableAccessor.functions.put("concat", concatInfo);

        FunctionInfo lengthInfo = new FunctionInfo("length", new ArrayList<>(List.of(TypeEnum.String)), TypeEnum.Integer, null, null);
        FunctionVariableAccessor.functions.put("length", lengthInfo);

        boolean hasMain = false;

        try {
            for (FunctionDefNode funcDef : funcDefNodes) {
                if (funcDef.id.id.getToken().equals("main")) {
                    hasMain = true;

                    // make sure main is declared as Void
                    if (funcDef.returnType.getType() != TypeEnum.Void) {
                        throw new Error("Semantic Error:\nmain must be declared as Void\n" + funcDef.id.getFilename() + ":" + funcDef.id.getLinenum());
                    }
                    // make sure main has no parameters
                    if (funcDef.funcDefParams != null) {
                        throw new Error("Semantic Error:\nmain must not take any arguments\n" + funcDef.id.getFilename() + ":" + funcDef.id.getLinenum());
                    }
                }

                // validate the function
                funcDef.validateTree();
            }
        } catch(Error e) {
            System.err.println(e.toString());
            return false;
        }

        // no main function
        if(!hasMain) {
            System.err.println("Semantic Error:\nNo `main` function found!");
            return false;
        }

        return true;
    }

    // --------------------------------

    public void execute() {
        FunctionVariableAccessor.callFunction("main", new ArrayList<>());
    }
}
