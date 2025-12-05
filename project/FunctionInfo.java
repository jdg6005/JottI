package project;

import Nodes.FBodyNode;
import Nodes.TypeEnum;

import java.util.ArrayList;

public class FunctionInfo {
    public final String id;
    public final ArrayList<TypeEnum> paramTypes;
    public final TypeEnum returnType;
    public final ArrayList<String> paramIds;
    public final FBodyNode fBodyNode;

    public FunctionInfo(
            String id,
            ArrayList<TypeEnum> paramTypes,
            TypeEnum returnType,
            ArrayList<String> paramIds,
            FBodyNode fBodyNode
    ) {
        this.id = id;
        this.paramTypes = paramTypes;
        this.returnType = returnType;
        this.paramIds = paramIds;
        this.fBodyNode = fBodyNode;
    }


    public TypeAndValue call(ArrayList<TypeAndValue> args) {

        switch (id) {
            case "print" -> {
				System.out.println(args.getFirst().toString());
				return new TypeAndValue(TypeEnum.Void);
			}
            case "concat" -> {
                String str1 = args.get(0).getStringValue();
                String str2 = args.get(1).getStringValue();
                return new TypeAndValue(str1 + str2);
            }
            case "length" -> { return new TypeAndValue(args.getFirst().getStringValue().length()); }
            default -> { // non-built-in function
                String originScope = FunctionVariableAccessor.currentScope;
                FunctionVariableAccessor.currentScope = id;

                // assign parameter values
                for (int i = 0; i < paramIds.size(); i++) {
                    FunctionVariableAccessor.setVariable(paramIds.get(i), args.get(i));
                }

                // call
                TypeAndValue result = fBodyNode.execute();

                FunctionVariableAccessor.currentScope = originScope;
                return result;
            }
        }
    }
}
