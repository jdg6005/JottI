package project;

import Nodes.TypeEnum;

import java.util.ArrayList;

public class FunctionInfo {
    public final ArrayList<TypeEnum> paramTypes;
    public final TypeEnum returnType;

    public FunctionInfo(ArrayList<TypeEnum> paramTypes, TypeEnum returnType) {
        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }
}
