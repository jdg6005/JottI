package provided.Nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import provided.Nodes.IDNode;
import provided.Nodes.TypeNode;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    private TypeNode typeNode;
    private IDNode idNode;


    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) {
        VarDecNode varDecNode = new VarDecNode();

        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println(":(");
            return null;
        }
        varDecNode.typeNode = TypeNode.parseTypeNode(tokens);

        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println(":(");
            return null;
        }
        varDecNode.idNode = IDNode.parseIDNode(tokens);

        return varDecNode;
    }

    @Override
    public String convertToJott() {
        StringBuilder str = new StringBuilder();
        str.append(typeNode.convertToJott());
        str.append(" ");
        str.append(idNode.convertToJott());

        return str.toString();
    }

    @Override
    public String convertToJava(String className) {
        return " ";
    }

    @Override
    public String convertToC() {
        return " ";
    }

    @Override
    public String convertToPython() {
        return " ";
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
