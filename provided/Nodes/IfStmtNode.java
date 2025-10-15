package provided.Nodes;

import provided.ExprNode;
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
            throw new Error("Was expecting 'If'!");
        }

        // [
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACKET) {
            throw new Error("Was expecting '['!");
        }

        // <expr>
        self.expr = ExprNode.parseExprNode(tokens);

        // ]
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACKET) {
            throw new Error("Was expecting ']'!");
        }

        // {
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.L_BRACE) {
            throw new Error("Was expecting '{'!");
        }

        // <body>
        self.body = BodyNode.parseBodyNode(tokens);

        // }
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.R_BRACE) {
            throw new Error("Was expecting '}'!");
        }

        // <elseif_lst>
        while(tokens.getFirst().getToken().equals("Else")) {
            self.elseIfLst.add(ElseIfNode.parseElseIfNode(tokens));
        }

        // <else>
        self.else = ElseNode.parseElseNode(tokens);

        return self;
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for(ElseIfNode elseIfLst : elseIfLst) {
            sb.append(elseIfLst.convertToJott()).append(" ");
        }
        String elseifs = sb.toString();

        return "If [" + this.expr.convertToJott() + " ] { " + this.body + " } " + elseifs + this.elseNode.convertToJott();
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
