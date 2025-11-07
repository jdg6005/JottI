package project;

import Nodes.IDNode;
import Nodes.TypeEnum;

import java.util.HashMap;

public class FunctionVariableAccessor {
    public static HashMap<String, FunctionInfo> functions = new HashMap<>();

    // function scope -> variable name -> variable type
    public static HashMap<String, HashMap<String, TypeEnum>> variables = new HashMap<>();
    public static String currentScope = "";

//    public static void functionToString() {
//        for (IDNode id : functions.keySet()) {
//            FunctionInfo fl = functions.get(id);
//
//            System.out.println("Function: " + id.convertToJott());
//            System.out.println("Parameters: ");
//            for (IDNode subId: fl.params.keySet()) {
//                System.out.println(subId.convertToJott() + ": " + fl.params.get(subId).convertToJott());
//            }
//            System.out.println("Local Variables: ");
//            for (IDNode subId: variableList.get(id).keySet()) {
//                System.out.println(subId.convertToJott() + ": " + variableList.get(id).get(subId).convertToJott());
//            }
//            System.out.println("Return: " + fl.returnType.convertToJott());
//        }
//    }
}
