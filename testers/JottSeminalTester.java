package testers;


import project.FunctionVariableAccessor;
import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class JottSeminalTester {

	private final static String[] filenames = { "funcCallParamInvalid.jott", "funcNotDefined.jott", "funcReturnInExpr.jott", "funcWrongParamType.jott", "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "mainReturnNotInt.jott", "mismatchedReturn.jott", "missingFuncParams.jott", "missingMain.jott", "missingReturn.jott", "noReturnIf.jott", "noReturnWhile.jott", "providedExample1.jott", "returnId.jott", "validLoop.jott", "voidReturn.jott", "whileKeyword.jott" };
	private final static boolean[] shouldPassPhase2 = {
			true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false };
	private final static boolean[] shouldPassPhase3 = {
			false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, false, true, false, false};

	public static void main(String[] args) {
		int numPassed = 0;
		int numParserFailed = 0;
		for(int i = 0; i < filenames.length; i ++) {
			System.out.println("about to test file " + filenames[i]);

			FunctionVariableAccessor.functions = new HashMap<>();
			FunctionVariableAccessor.variables = new HashMap<>();

			ArrayList<Token> tokens = JottTokenizer.tokenize("phase3testcases/" + filenames[i]);
			JottTree root = JottParser.parse(tokens);
			if(root == null) {
				if(!shouldPassPhase2[i]) numPassed ++;
				else System.out.println("parser failed #" + (++ numParserFailed) + " on file " + filenames[i]);
				continue;
			}
			if(root.validateTree() == shouldPassPhase3[i]) numPassed ++;
			else System.out.println("seminal failed on file " + i + ": " + filenames[i] + " (should have returned " + shouldPassPhase3[i] + ")");

		}

		System.out.print("private final static String[] filenames = { ");
		for (int i = 0; i < filenames.length; i++) {
			if(shouldPassPhase2[i] && shouldPassPhase3[i])
				System.out.print('"' + filenames[i] + '"' + ", ");
		}
		System.out.println(" }");

		System.out.println(numPassed + " / " + (filenames.length - numParserFailed) + " / " + filenames.length);
	}

}