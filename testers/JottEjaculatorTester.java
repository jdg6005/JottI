package testers;

import project.Main;

public class JottEjaculatorTester {
	private final static String[] filenames = { "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "providedExample1.jott", "validLoop.jott" };
	private final static String[] expected = { "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "providedExample1.jott", "validLoop.jott" };


	public static void main(String[] args){
		//for (String filename : filenames){
			//Main.main(new String[] { filename });
		//}
		Main.main(new String[] { "phase3testcases/" + filenames[0] });
	}
}
