package project;

import java.util.HashMap;

public class FunctionVariableAccessor {
    public static HashMap<String, FunctionInfo> functions = new HashMap<>();

    // function scope -> variable name -> variable type AND value
    public static HashMap<String, HashMap<String, TypeAndValue>> variables = new HashMap<>();
    public static String currentScope = "";
}
