package provided;

/**
 * This class is responsible for tokenizing Jott code.
 *
 * @author
 **/

import jdk.jshell.spi.ExecutionControl;

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
        TokenList tokens = new TokenList(filename);

        int lineNum = 1;
        try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
            int cInt = in.read();
            while(cInt != -1) {

                char c = (char)cInt;

                if(c == '\n') {
					lineNum++;
				} else if(Character.isWhitespace(c)) {
                    // nop
                } else if(c == '#') {
                    while((char)in.read() != '\n') { /* nop */ }
                    lineNum++;
                } else if(c == ',') {
                    tokens.add(",", lineNum, TokenType.COMMA);
                } else if(c == '[') {
                    tokens.add("[", lineNum, TokenType.L_BRACKET);
                } else if(c == ']') {
                    tokens.add("]", lineNum, TokenType.R_BRACKET);
                } else if(c == '{') {
                    tokens.add("{", lineNum, TokenType.L_BRACE);
                } else if(c == '}') {
                    tokens.add("}", lineNum, TokenType.R_BRACE);
                } else if(c == '=') {
                    c = (char)in.read();
                    if(c == '=') tokens.add("==", lineNum, TokenType.REL_OP);
                    else tokens.add("=", lineNum, TokenType.ASSIGN);
                } else if(c == '<') {
                    in.mark(1);
                    c = (char)in.read();
                    if(c == '=') {
                        tokens.add("<=", lineNum, TokenType.REL_OP);
                    } else {
                        in.reset();
                        tokens.add("<", lineNum, TokenType.REL_OP);
                    }
                } else if(c == '>') {
                    in.mark(1);
                    c = (char)in.read();
                    if(c == '=') {
                        tokens.add(">=", lineNum, TokenType.REL_OP);
                    } else {
                        in.reset();
                        tokens.add(">", lineNum, TokenType.REL_OP);
                    }
                } else if(c == '!') {
                    in.mark(1);
                    c = (char)in.read();
                    if(c == '=') tokens.add("!=", lineNum, TokenType.REL_OP);
                    else {
                        in.reset();
                        throw new ExecutionControl.NotImplementedException("TODO");
                    } // TODO: error
                } else if(c == '+') {
                    tokens.add("+", lineNum, TokenType.MATH_OP);
                } else if(c == '-') {
                    tokens.add("-", lineNum, TokenType.MATH_OP);
                } else if(c == '*') {
                    tokens.add("*", lineNum, TokenType.MATH_OP);
                } else if(c == '/') {
                    tokens.add("/", lineNum, TokenType.MATH_OP);
                } else if(c == ';') {
                    tokens.add(";", lineNum, TokenType.SEMICOLON);
                } else if(c == ':') {
                    in.mark(1);
                    char nextC =  (char)in.read();
                    if(nextC == ':') {
                        tokens.add("::", lineNum, TokenType.FC_HEADER);
                    } else {
                        in.reset(); // "unread" the next character
                        tokens.add(":", lineNum, TokenType.COLON);
                    }
                } else if(c == '.') {
                    StringBuilder number = new StringBuilder(".");
                    // check we have at least one digit
                    in.mark(1);
                    c = (char)in.read();
                    if(!Character.isDigit(c)) {
                        in.reset();
                        throw new ExecutionControl.NotImplementedException("");
                    }
                    // continue
                    while(Character.isDigit(c)) {
                        number.append(c);
                        in.mark(1);
                        c = (char)in.read();
                    }
                    in.reset(); // unread the non-digit character
                    tokens.add(number.toString(), lineNum, TokenType.NUMBER);
                } else if(Character.isDigit(c)) {
                    StringBuilder number = new StringBuilder();

                    // read in digits up until . or other non-digit
                    while(Character.isDigit(c)) {
                        number.append(c);
                        in.mark(1);
                        c = (char)in.read();
                    }
                    // read in rest if next is a non-digit
                    if(c == '.') {
                        number.append('.');
                        in.mark(1);
                        c = (char)in.read();
                        while(Character.isDigit(c)) {
                            number.append(c);
                            in.mark(1);
                            c = (char) in.read();
                        }
                    }
                    in.reset(); // unread the non-digit/non-period character

                    System.out.println("number: " + number);
                    tokens.add(number.toString(), lineNum, TokenType.NUMBER);
                } else if(Character.isAlphabetic(c)) {
                    StringBuilder idKeyword = new StringBuilder();
                    while(Character.isAlphabetic(c) || Character.isDigit(c)) {
                        idKeyword.append(c);
                        in.mark(1);
                        c = (char) in.read();
                    }
                    in.reset();
                    tokens.add(idKeyword.toString(), lineNum, TokenType.ID_KEYWORD);
                } else if(c == '"') {
                    StringBuilder string = new StringBuilder("\"");
                    in.mark(1);
                    c = (char)in.read();
                    while(Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpaceChar(c)) {
                        string.append(c);
                        in.mark(1);
                        c = (char)in.read();
                    }
                    if(c == '"')  {
                        string.append('"');
                        tokens.add(string.toString(), lineNum, TokenType.STRING);
                    } else {
                        in.reset();
                        throw new ExecutionControl.NotImplementedException("TODO");
                    }  // TODO: error
                } else {
                    break;  // TODO: error
                }
                cInt = in.read();
            }

            return tokens;
        } catch(Exception e) {
            System.err.println("TODO: bru"); return null;
        }
    }

    
    private static class TokenList extends ArrayList<Token> {
        String filename;
        
        public TokenList(String filename) {
            this.filename = filename;
        }
        
        // shortens: tokens.add(new Token(";", filename, lineNum, TokenType.SEMICOLON));
        // to:       tokens.add(";", lineNum, TokenType.SEMICOLON);
        public void add(String token, int lineNum, TokenType type) {
                super.add(new Token(token, filename, lineNum, type));
        }
    }

}