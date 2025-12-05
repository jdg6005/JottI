package testers;

import project.Main;
import project.TypeAndValue;

import java.io.*;
import java.util.Scanner;

public class JottEjaculatorTester {
	private final static String[] filenames = { "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "providedExample1.jott", "validLoop.jott" };
	private final static String[] expected = { "helloWorldResult.jott", "ifStmtReturnsResult.jott", "largerValidResult.jott", "providedExample1Result.jott", "validLoopResult.jott" };


	public static void main(String[] args) throws FileNotFoundException {

		FileInputStream fin;

		BufferedInputStream bin;

		PrintStream originalOut = System.out;
		for (int i = 0; i < filenames.length; i++) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			System.setOut(ps);
			Main.main(new String[]{"phase3testcases/" + filenames[i]});
			String capturedOutput = baos.toString();
			System.setOut(originalOut);

			System.out.println(capturedOutput);
			//fin = new FileInputStream( "phase4ExpectedResults/" + expected[i]);
			//bin = new BufferedInputStream(fin);
			//}
		}
	}
}
