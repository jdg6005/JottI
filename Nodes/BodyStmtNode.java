package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static Nodes.FunctionCallNode.parseFunctionCallNode;
import static Nodes.WhileNode.parseWhileNode;

public class BodyStmtNode implements JottTree {
    private WhileNode whileNode;
    private IfStmtNode ifStmtNode;
    private FunctionCallNode functionCallNode;
    private AssignmentNode asmtNode;

    public static JottTree parseBodyStmtNode(ArrayList<Token> tokens) {
        BodyStmtNode self = new BodyStmtNode();

        Token currToken = tokens.getFirst();
        switch (tokens.getFirst().getTokenType()) {
            case STRING:
                if (currToken.getToken().equals("If"))
                    self.ifStmtNode = IfStmtNode.parseIfStmtNode(tokens);
                else if (currToken.getToken().equals("While"))
                    self.whileNode = parseWhileNode(tokens);
            case ID_KEYWORD:
                self.asmtNode = AssignmentNode.Parse(tokens);
            case COLON:
                self.functionCallNode = parseFunctionCallNode(tokens);
            default:
        };

        currToken = tokens.removeFirst();
        if(!currToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Error("Body statement should end with ';'!");
        }
        return self;
    }

    @Override
    public String convertToJott() {
        if (whileNode != null) {
            return whileNode.convertToJott() + ";";
        } else if (ifStmtNode != null) {
            return ifStmtNode.convertToJott() + ";";
        } else if (functionCallNode != null) {
            return functionCallNode.convertToJott() + ";";
        } else {
            return asmtNode.convertToJott() + ";";
        }
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
