package project;

import Nodes.FunctionReturnNode;
import Nodes.IDNode;
import Nodes.TypeNode;
import java.util.HashMap;

public class FunctionList {
    final HashMap<IDNode, TypeNode> params;
    final FunctionReturnNode returnType;

    public FunctionList(HashMap<IDNode, TypeNode> params, FunctionReturnNode returnType) {
        this.params = params;
        this.returnType = returnType;
    }

    public HashMap<IDNode, TypeNode> getParams() {return this.params;}

    public FunctionReturnNode getReturnType() {return this.returnType;}
}
