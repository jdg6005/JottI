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
        // tokenize
        ArrayList<Token> tokens = JottTokenizer.tokenize(args[0]);
        if(tokens != null) {
            // parse
            JottTree root = JottParser.parse(tokens);

            if (root != null) {
                // analyze
                boolean isValid = root.validateTree();
                if (isValid) {
					TypeAndValue program = root.execute();
					//System.out.println(program);
				}
            }
        } else {
            System.err.println("Tokenization failed!");
        }
    }
}
