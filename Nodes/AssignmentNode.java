package Nodes;

import project.FunctionVariableAccessor;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class AssignmentNode implements JottTree {
    public IDNode idTok;
    public ExprNode rhs;

    public static AssignmentNode parseAssignmentNode(ArrayList<Token> tokens) {
        AssignmentNode self = new AssignmentNode();

        // <id>
        self.idTok = IDNode.parseIDNode(tokens);

        // =
        Token token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.ASSIGN) {
            throw new Error("Syntax Error\nExpected '=' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <expr>
        self.rhs = ExprNode.parseExprNode(tokens);

        // ;
        token = tokens.removeFirst();
        if(token.getTokenType() != TokenType.SEMICOLON) {
            throw new Error("Syntax Error\nExpected ';' got " + token + "\n" + token.getFilename() + ":" + token.getLineNum());
        }

        return self;
    }

    @Override
    public String convertToJott() {
        return idTok.convertToJott() + "=" + rhs.convertToJott() + ";";
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

    // needs to check that variable was declared within scope
    // and that type of rhs matches expected
    @Override
    public boolean validateTree() {
        if (!FunctionVariableAccessor.variableList.get(FunctionVariableAccessor.currentScope).containsKey(idTok)) {
            return false;
        }
        TypeEnum leftType = FunctionVariableAccessor.variableList.get(FunctionVariableAccessor.currentScope).get(idTok).getType();
        return leftType.equals(rhs.getType()) && idTok.validateTree() && rhs.validateTree();
    }

}

