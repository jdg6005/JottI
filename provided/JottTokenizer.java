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
        TokenList tokens = new TokenList(filename);

        int lineNum = 1;

        try(BufferedReader in = new BufferedReader(new FileReader(filename))) {

            int cInt = in.read();

            while(cInt != -1) {
                cInt = in.read();
                char c = (char)cInt;

                switch(c) {
                    case '\n' -> lineNum++;
                    case ',' -> tokens.add(",", lineNum, TokenType.COMMA);
                    case '[' -> tokens.add("[", lineNum, TokenType.L_BRACKET);
                    case ']' -> tokens.add("]", lineNum, TokenType.R_BRACKET);
                    case '{' -> tokens.add("{", lineNum, TokenType.L_BRACE);
                    case '}' -> tokens.add("}", lineNum, TokenType.R_BRACE);
                    case '+' -> tokens.add("+", lineNum, TokenType.MATH_OP);
                    case '-' -> tokens.add("-", lineNum, TokenType.MATH_OP);
                    case '*' -> tokens.add("*", lineNum, TokenType.MATH_OP);
                    case '/' -> tokens.add("/", lineNum, TokenType.MATH_OP);
                    case ';' -> tokens.add(";", lineNum, TokenType.SEMICOLON);
                    case '#' -> {
                        while((char)in.read() != '\n') { }
                        lineNum++;
                    }
                    case '=' -> {
                        c = (char) in.read();
                        if (c == '=') tokens.add("==", lineNum, TokenType.REL_OP);
                        else tokens.add("=", lineNum, TokenType.ASSIGN);
                    }
                    case '<' -> {
                        in.mark(1);
                        c = (char)in.read();
                        if(c == '=') {
                            tokens.add("<=", lineNum, TokenType.REL_OP);
                        } else {
                            in.reset();
                            tokens.add("<", lineNum, TokenType.REL_OP);
                        }
                    }
                    case '>' -> {
                        in.mark(1);
                        c = (char)in.read();
                        if(c == '=') {
                            tokens.add(">=", lineNum, TokenType.REL_OP);
                        } else {
                            in.reset();
                            tokens.add(">", lineNum, TokenType.REL_OP);
                        }
                    }
                    case '!' -> {
                        in.mark(1);
                        c = (char)in.read();
                        if(c == '=') tokens.add("!=", lineNum, TokenType.REL_OP);
                        else {
                            String msg = "Invalid token \"" + addEscapeChars(c, cInt != -1) + "\". \"!\" expects following \"=\"";
                            printError(msg, filename, lineNum);
                            in.reset();
                            return null;
                        }
                    }
                    case ':' -> {
                        in.mark(1);
                        char nextC =  (char)in.read();
                        if(nextC == ':') {
                            tokens.add("::", lineNum, TokenType.FC_HEADER);
                        } else {
                            in.reset(); // "unread" the next character
                            tokens.add(":", lineNum, TokenType.COLON);
                        }
                    }
                    case '.' -> {
                        StringBuilder number = new StringBuilder(".");
                        // check we have at least one digit
                        in.mark(1);
                        c = (char)in.read();
                        if(!Character.isDigit(c)) {
                            String msg = "Invalid token \"" + addEscapeChars(c, cInt != -1) + "\".\". \".\" expects following digit";
                            printError(msg, filename, lineNum);
                            in.reset();
                            return null;
                        }
                        // continue
                        while(Character.isDigit(c)) {
                            number.append(c);
                            in.mark(1);
                            c = (char)in.read();
                        }
                        in.reset(); // unread the non-digit character
                        tokens.add(number.toString(), lineNum, TokenType.NUMBER);
                    }
                    case '"' -> {
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
                            String msg = "Unterminated string literal. Missing closing '\"'";
                            printError(msg, filename, lineNum);
                            return null;
                        }
                    }
                    default -> {
                        if(Character.isWhitespace(c)) {
                            // nop
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
                        } else {
                            // TODO: print error
                            return null;
                        }
                    }
                }
            };

            return tokens;

        } catch(Exception e) {
            // TODO: does this even hit
            System.err.println("TODO: bru"); return null;
        }
    }

    private static void printError(String msg, String filename, int lineNum) {
        System.err.println("Syntax Error:");
        System.err.println(msg);
        System.err.println(filename + ":" + lineNum);
    }


    // replaces, for example, tabs with "\t"
    private static String addEscapeChars(String str) {
        String result = str.replaceAll("\t", "\\\\t");
        result = result.replaceAll("\b", "\\\\b");
        result = result.replaceAll("\n", "\\\\n");
        result = result.replaceAll("\r", "\\\\r");
        result = result.replaceAll("\f", "\\\\f");
        return result;
    }
    private static String addEscapeChars(char c, boolean isEOF) {
        return isEOF? "EOF" : addEscapeChars(String.valueOf(c));
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