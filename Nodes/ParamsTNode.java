package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {

	private ExprNode exprNode = null;

	public static ParamsTNode parseParamsTNode(ArrayList<Token> tokens) {
		ParamsTNode paramsTNode = new ParamsTNode();

        // ,
        Token token = tokens.removeFirst();
        if(!token.getTokenType().equals(TokenType.COMMA)) {
            throw new Error("Syntax Error\nExpected ','\n" + token.getFilename() + ":" + token.getLineNum());
        }

        // <expr>
		paramsTNode.exprNode = ExprNode.parseExprNode(tokens);

		return paramsTNode;
	}

	// ---------------------------------

	@Override
	public String convertToJott() {
        return ", " + exprNode.convertToJott();
	}

	@Override
	public String convertToJava(String className) {
		return null;
	}

	@Override
	public String convertToC() {
		return null;
	}

	@Override
	public String convertToPython() {
		return null;
	}

	@Override
	public boolean validateTree() {
		return false;
	}
}
