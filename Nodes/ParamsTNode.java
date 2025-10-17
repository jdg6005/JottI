package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {

	private ExprNode exprNode = null;

	public static ParamsTNode parseParamsTNode(ArrayList<Token> tokens) {
		ParamsTNode paramsTNode = new ParamsTNode();
		paramsTNode.exprNode = ExprNode.parseExprNode(tokens);
		if(tokens.getFirst().getTokenType() != TokenType.COMMA)
			throw new Error("Expected comma before next function parameter");
		tokens.removeFirst();
		return paramsTNode;
	}

	// ---------------------------------

	@Override
	public String convertToJott() {
		return null;
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
