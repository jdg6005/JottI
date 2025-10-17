package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsNode implements JottTree {

  private final ExprNode exprNode = null;
	private final ArrayList<ParamsTNode> paramsTNodes = new ArrayList<ParamsNode>();


	public static ParamsNode parseParamsNode(ArrayList<Token> tokens) {
		ParamsNode paramsNode = new ParamsNode();

        if (!(tokens.getFirst().getTokenType() == TokenType.L_BRACKET)) {
            paramsNode.exprNode = ExprNode.parseExprNode(tokens);
            while (tokens.getFirst().getTokenType() == TokenType.COMMA) {
                paramsNode.paramsTNodes.add(ParamsTNode.parseParamsTNode(paramsTNodes));
            }
        }
		return paramsNode;
	}

	@Override
	public String convertToJott() {
		if (this.exprNode != null) {
            StringBuilder str = new StringBuilder(this.exprNode.convertToJott());
            for (ParamsTNode pt : paramsTNodes) {
                str.append(pt);
            }
            return str;
        }
        return "";
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
