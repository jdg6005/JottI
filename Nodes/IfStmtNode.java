package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * This class is responsible for parsing a if statement node
 *
 * @author Matthew Fiedler
 **/

public class IfStmtNode implements JottTree {
    ExprNode expr;
    BodyNode body;
    ArrayList<ElseIfNode> elseIfLst;
    ElseNode elseNode;

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) {
        IfStmtNode self = new IfStmtNode();
        Token token = tokens.removeFirst();

        // "If"
        if(token.getTokenType() != TokenType.ID_KEYWORD || !token.getToken().equals("If")) {
            throw new Error("Syntax Error\nWas expecting 'If' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // [
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACKET) {
            throw new Error("Syntax Error\nWas expecting '[' got " + token.toString() + "\n"  + token.getFilename() + ":" + token.getLineNum());
        }

        // <expr>
        self.expr = ExprNode.parseExprNode(tokens);

        // ]
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACKET) {
            throw new Error("Syntax Error\nWas expecting ']' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Syntax Error\nWas expecting '{' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <body>
        self.body = BodyNode.parseBodyNode(tokens);

        // }
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACE) {
            throw new Error("Syntax Error\nWas expecting '}' got " + token.toString() + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <elseif_lst>*
        while(tokens.getFirst().getToken().equals("Elseif")) {
            self.elseIfLst.add(ElseIfNode.parseElseIfNode(tokens));
        }

        // <else> (may be null)
        self.elseNode = ElseNode.parseElseNode(tokens);

        return self;
    }

    @Override
    public String convertToJott() {
        String elseifs = "";
        StringBuilder sb = new StringBuilder();
        if(elseIfLst != null) {
            for(ElseIfNode elseIfLst : elseIfLst) {
                sb.append(elseIfLst.convertToJott()).append(" ");
            }
            elseifs = sb.toString();
        }

        return "If [" + this.expr.convertToJott() + "] { " + this.body.convertToJott() + " } "
                + elseifs + (this.elseNode == null ? "" : this.elseNode.convertToJott());
    }

    @Override
    public String convertToJava(String className) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String convertToC() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String convertToPython() {
        throw new UnsupportedOperationException();
    }

    // expr must be valid and return a boolean, body must
    // be valid, any elseifs and else must also be valid
    @Override
    public boolean validateTree() {
        // check the if
        boolean isValid = this.expr.validateTree() && (this.expr.getType() == TypeEnum.Boolean);
        // check the elseifs
        if(this.elseIfLst != null) {
            for(ElseIfNode elseIf : elseIfLst) {
                isValid &= elseIf.validateTree();
            }
        }
        // check the else
        if(this.elseNode != null) {
            isValid &= this.elseNode.validateTree();
        }

        return isValid;
    }
}
