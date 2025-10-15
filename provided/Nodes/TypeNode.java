package provided.Nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class TypeNode implements JottTree {
    private Token type;

    public static TypeNode parseTypeNode(ArrayList<Token> tokens) {
        Token token = tokens.remove(0);
        String type = token.getToken();

        if (type.equals("Double") || type.equals("Integer") || type.equals("String") || type.equals("Boolean")) {
            TypeNode typeNode = new TypeNode();
            typeNode.type = token;
            return typeNode;
        }

        throw new Error("Not a valid type");
    }

    @Override
    public String convertToJott() {
        return this.type.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.type.getToken();
    }

    @Override
    public String convertToC() {
        return this.type.getToken();
    }

    @Override
    public String convertToPython() {
        return this.type.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }
}
