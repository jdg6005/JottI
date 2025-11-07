package testers;


import project.FunctionVariableAccessor;
import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class JottSeminalTester {

	private final static String[] filenames = { "funcCallParamInvalid.jott", "funcNotDefined.jott", "funcReturnInExpr.jott", "funcWrongParamType.jott", "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "mainReturnNotInt.jott", "mismatchedReturn.jott", "missingFuncParams.jott", "missingMain.jott", "missingReturn.jott", "noReturnIf.jott", "noReturnWhile.jott", "providedExample1.jott", "returnId.jott", "validLoop.jott", "voidReturn.jott", "whileKeyword.jott" };
	private final static boolean[] expectedValidityResults = { false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, false, true, false, false };

	public static void main(String[] args) {
		int numPassed = 0;
		int numParserFailed = 0;
		for(int i = 0; i < filenames.length; i ++) {
			ArrayList<Token> tokens = JottTokenizer.tokenize("phase3testcases/" + filenames[i]);
			FunctionVariableAccessor.currentScope = "";
			JottTree root = JottParser.parse(tokens);
			if(root == null) {
				System.out.println("parser failed on file " + filenames[i]);
				numParserFailed ++;
				continue;
			}
			if(root.validateTree() == expectedValidityResults[i]) numPassed ++;
			else System.out.println("seminal failed on file " + i + ": " + filenames[i]);
		}
		System.out.println(numPassed + " / " + (filenames.length - numParserFailed) + " / " + filenames.length);
	}

}