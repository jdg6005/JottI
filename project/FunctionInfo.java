package project;

import Nodes.FBodyNode;
import Nodes.TypeEnum;

import java.util.ArrayList;

public class FunctionInfo {
    public final String id;
    public final ArrayList<TypeEnum> paramTypes;
    public final TypeEnum returnType;
    public final FBodyNode fBodyNode;

    public FunctionInfo(String id, ArrayList<TypeEnum> paramTypes, TypeEnum returnType, FBodyNode fBodyNode) {
        this.id = id;
        this.paramTypes = paramTypes;
        this.returnType = returnType;
        this.fBodyNode = fBodyNode;
    }

    public TypeAndValue call(ArrayList<TypeAndValue> args) {
        String originScope = FunctionVariableAccessor.currentScope;
        FunctionVariableAccessor.currentScope = id;

        TypeAndValue result = fBodyNode.execute(args);

        FunctionVariableAccessor.currentScope = originScope;
        return result;
    }
}
