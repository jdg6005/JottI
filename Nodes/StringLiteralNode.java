package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class StringLiteralNode implements JottTree {

	private Token stringToken;

	public static StringLiteralNode parseStringLiteralNode(ArrayList<Token> tokens) {
		StringLiteralNode stringLiteralNode = new StringLiteralNode();
		if(tokens.getFirst().getTokenType() != TokenType.STRING)
			throw new Error("Expected a string at" + tokens.getFirst().getLineNum());
		stringLiteralNode.stringToken = tokens.removeFirst();
		return stringLiteralNode;
	}

	// --------------------------------

	@Override
	public String convertToJott() {
        return "\"" + stringToken.getToken() + "\"";
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
