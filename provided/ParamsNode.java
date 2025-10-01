package provided;

import java.util.ArrayList;

public class ParamsNode implements JottTree {

	private ExprNode exprNode = null;
	private ArrayList<ParamsTNode> paramsTNodes = new ArrayList<ParamsNode>();


	public static ParamsNode parseParamsNode(ArrayList<Token> tokens) {
		ParamsNode paramsNode = new ParamsNode();
		paramsNode.exprNode = ExprNode.parseExprNode(tokens);
		while(tokens.get(0).getTokenType() == TokenType.COMMA) {
			paramsNode.paramsTNodes.add(ParamsTNode.parseParamsTNode(paramsTNodes));
		}
		return paramsNode;
	}

	@Override
	public String convertToJott() {
		return null;
	}

	// ------------------------------------

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
