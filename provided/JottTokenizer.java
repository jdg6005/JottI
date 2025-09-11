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
                if(!Character.isWhitespace(c)) {

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
                } else if(c == '=') {
                    c = (char)in.read();
                    if (c == '=') tokens.add(new Token("==", filename, lineNum, TokenType.REL_OP));
                    else tokens.add(new Token("=", filename, lineNum, TokenType.ASSIGN));
                } else if(c == '<') {
                    c = (char)in.read();
                    if (c == '=') tokens.add(new Token("<=", filename, lineNum, TokenType.REL_OP));
                    else tokens.add(new Token("<", filename, lineNum, TokenType.REL_OP));
                } else if(c == '>') {
                    c = (char)in.read();
                    if (c == '=') tokens.add(new Token(">=", filename, lineNum, TokenType.REL_OP));
                    else tokens.add(new Token(">", filename, lineNum, TokenType.REL_OP));
                } else if(c == '!') {
                    c = (char)in.read();
                    if (c == '=') tokens.add(new Token("!=", filename, lineNum, TokenType.REL_OP));
                    else break;  // TODO: error
                } else if(c == '+') {
                    tokens.add(new Token("+", filename, lineNum, TokenType.MATH_OP));
                } else if(c == '-') {
                    tokens.add(new Token("-", filename, lineNum, TokenType.MATH_OP));
                } else if(c == '*') {
                    tokens.add(new Token("*", filename, lineNum, TokenType.MATH_OP));
                } else if(c == '/') {
                    tokens.add(new Token("/", filename, lineNum, TokenType.MATH_OP));
                } else if(c == ';') {
                    tokens.add(new Token(";", filename, lineNum, TokenType.SEMICOLON));
                } else if(c == ':') {
                    tokens.add(new Token(":", filename, lineNum, TokenType.COLON));
                } else if(c == '.') {
                    c = (char) in.read();
                    if (c < 48 || c > 57) break;  // TODO: error
                    else {
                        StringBuilder number = new StringBuilder(".");
                        while (c >= 48 && c <= 57) {
                            number.append(c = (char) in.read());
                        }
                        tokens.add(new Token(number.toString(), filename, lineNum, TokenType.NUMBER));
                    }
                } else if(c >= 48 && c <= 57) {
                    StringBuilder number = new StringBuilder();
                    while (c >= 48 && c <= 57) number.append(c = (char) in.read());
                    if (c == '.') {
                        do {
                            number.append(c = (char) in.read());
                        } while (c >= 48 && c <= 57);
                    }
                    System.out.println("number: " + number.toString());
                    tokens.add(new Token(number.toString(), filename, lineNum, TokenType.NUMBER));
                } else if(c >= 65 && c <= 90) {
                    StringBuilder idKeyword = new StringBuilder();
                    while ((c >= 65 && c <= 90) || (c >= 48 && c <= 57)) idKeyword.append(c = (char) in.read());
                    tokens.add(new Token(idKeyword.toString(), filename, lineNum, TokenType.ID_KEYWORD));
                } else if(c == '"') {
                    StringBuilder string = new StringBuilder();
                    while ((c >= 65 && c <= 90) || (c >= 48 && c <= 57) || Character.isWhitespace(c)) string.append(c = (char) in.read());
                    if (c == '"')  tokens.add(new Token(string.toString(), filename, lineNum, TokenType.STRING));
                    else break;  // TODO: error
                }
                else {
                    break;  // TODO: error
                }
                c = (char)in.read();
            }

            return tokens;
        } catch(Exception e) { 
            System.err.println("TODO: bru"); return null;
        }
	}
}