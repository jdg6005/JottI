package project;

import Nodes.IDNode;
import Nodes.TypeNode;

import java.util.HashMap;

public class FunctionVariableAccessor {
    public static HashMap<IDNode, FunctionList> functionList;
    public static HashMap<IDNode, HashMap<IDNode, TypeNode>> variableList;
    public static IDNode currentScope;

    public static void functionToString() {
        for (IDNode id : functionList.keySet()) {
            FunctionList fl = functionList.get(id);

            System.out.println("Function: " + id.convertToJott());
            System.out.println("Parameters: ");
            for (IDNode subId: fl.params.keySet()) {
                System.out.println(subId.convertToJott() + ": " + fl.params.get(subId).convertToJott());
            }
            System.out.println("Local Variables: ");
            for (IDNode subId: variableList.get(id).keySet()) {
                System.out.println(subId.convertToJott() + ": " + variableList.get(id).get(subId).convertToJott());
            }
            System.out.println("Return: " + fl.returnType.convertToJott());
        }
    }
}
