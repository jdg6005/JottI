package provided;

import java.util.ArrayList;

public class StringLiteralNode implements JottTree {

	private Token stringToken;

	public static StringLiteralNode parseStringLiteralNode(ArrayList<Token> tokens) {
		StringLiteralNode stringLiteralNode = new StringLiteralNode();
		if(tokens.get(0).getTokenType() != TokenType.STRING)
			throw new Error("Expected a string at" + tokens.get(0).getLineNum());
		stringLiteralNode.stringToken = tokens.remove(0);
		return stringLiteralNode;
	}

	// --------------------------------

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
