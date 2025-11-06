package Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsNode implements JottTree {
    ExprNode exprNode = null;
	final ArrayList<ParamsTNode> paramsTNodes = new ArrayList<>();

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

    // get list of types for the parameters
	public ArrayList<TypeEnum> getTypes() {
        ArrayList<TypeEnum> types = new ArrayList<>();
        types.add(this.exprNode.getType());

        for(ParamsTNode pt : this.paramsTNodes) {
            types.add(pt.getType());
        }

        return types;
	}

	@Override
	public boolean validateTree() {
        this.exprNode.validateTree();

        for(ParamsTNode pt : paramsTNodes) {
            pt.validateTree();
        }

        return true;
	}
}
