package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;

public class JottTokenizer {

	  /**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename) {
        ArrayList<Token> tokens = new ArrayList<>();

        int lineNum = 0; // TODO: update this
        try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
            char c = (char)in.read();
            while(c != -1) {
                if(Character.isWhitespace(c)) { 
                    /* do nothing */ 
                } else if(c == '#') {
                    while(c != '\n') { c = (char)in.read(); }
                } else if(c == ',') {
                    tokens.add(new Token(",", filename, lineNum, TokenType.COMMA));
                } else if(c == '[') {
                    tokens.add(new Token("[", filename, lineNum, TokenType.L_BRACKET));
                } else if(c == ']') {
                    tokens.add(new Token("]", filename, lineNum, TokenType.R_BRACKET));
                } else if(c == '{') {
                    tokens.add(new Token("{", filename, lineNum, TokenType.L_BRACE));
                } else if(c == '}') {
                    tokens.add(new Token("}", filename, lineNum, TokenType.R_BRACE));
                }

                c = (char)in.read();
            }

            return tokens;
        } catch(Exception e) { 
            System.err.println("TODO: bru"); return null;
        }
	}
}