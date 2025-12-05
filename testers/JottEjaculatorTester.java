package testers;

import project.Main;

import java.util.ArrayList;

public class JottEvaluatorTester {
	private final static String[] filenames = { "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "providedExample1.jott", "validLoop.jott" };

	public static void main(String[] args){
		//for (String filename : filenames){
			//Main.main(new String[] { filename });
		//}
		Main.main(new String[] { "phase3testcases/" + filenames[0] });
	}
}
