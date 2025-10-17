package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BodyStmtNode implements JottTree {
    private WhileNode whileNode;
    private IfStmtNode ifStmtNode;
    private FunctionCallNode functionCallNode;
    private AssignmentNode asmtNode;

    public static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) {
        BodyStmtNode self = new BodyStmtNode();

        Token currToken = tokens.getFirst();
        switch (currToken.getTokenType()) {
            case ID_KEYWORD:
                // <if>
                if (currToken.getToken().equals("If"))
                    self.ifStmtNode = IfStmtNode.parseIfStmtNode(tokens);
                // <while>
                else if (currToken.getToken().equals("While"))
                    self.whileNode = WhileNode.parseWhileNode(tokens);
                // <asmt>
                else
                    self.asmtNode = AssignmentNode.parseAssignmentNode(tokens);
                break;
            // <func_call>
            case FC_HEADER:
                self.functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
                currToken = tokens.removeFirst();
                if(currToken.getTokenType() != TokenType.SEMICOLON) {
                    throw new Error("Syntax Error\nWas expecting ';'\n" + currToken.getFilename() + ":" + currToken.getLineNum());
                }
                break;
            default:
                throw new Error("Syntax Error\nWas expecting 'If', 'While', an assignment, or a function call\n" + currToken.getFilename() + ":" + currToken.getLineNum());
        }


        return self;
    }

    @Override
    public String convertToJott() {
        if (whileNode != null) {
            return whileNode.convertToJott();
        } else if (ifStmtNode != null) {
            return ifStmtNode.convertToJott();
        } else if (functionCallNode != null) {
            return functionCallNode.convertToJott() + ";";
        } else {
            return asmtNode.convertToJott();
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
