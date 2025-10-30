package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsNode implements JottTree {
    private ExprNode exprNode = null;
	private final ArrayList<ParamsTNode> paramsTNodes = new ArrayList<ParamsTNode>();


	public static ParamsNode parseParamsNode(ArrayList<Token> tokens) {
		ParamsNode paramsNode = new ParamsNode();

        // <expr>
        paramsNode.exprNode = ExprNode.parseExprNode(tokens);

        // <params_t>
        while (tokens.getFirst().getTokenType() == TokenType.COMMA) {
            paramsNode.paramsTNodes.add(ParamsTNode.parseParamsTNode(tokens));
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
            return str.toString();
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
        boolean isValid = this.exprNode.validateTree();

        for(ParamsTNode pt : paramsTNodes) {
            isValid &= pt.validateTree();
        }

        return isValid;
	}
}
