package project;

import Nodes.IDNode;
import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;
import java.util.ArrayList;
import java.util.HashMap;


/*
* Main method for running the Jott compiler
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = JottTokenizer.tokenize("parserTestCases/" + args[0]);
        FunctionVariableAccessor.currentScope = "";
        JottTree root = JottParser.parse(tokens);
        System.out.println(root.convertToJott());
        root.validateTree();

        // FunctionVariableAccessor.functionToString();
    }
}
