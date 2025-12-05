/* DOES NOT WORK WITH MAIN IN OUTER DIRECTORY
/* MOVE MAIN INTO testers/ TO RUN THIS


package testers;

import java.io.*;
import Main;

public class JottEvaluatorTester {
	private final static String[] filenames = { "helloWorld.jott", "ifStmtReturns.jott", "largerValid.jott", "providedExample1.jott", "validLoop.jott", "reallyLong.jott"};

	public static void main(String[] args) throws FileNotFoundException {

		FileInputStream fin;

		BufferedInputStream bin;

		PrintStream originalOut = System.out;
		for (String filename : filenames) {
			System.out.println("File: " + filename);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			System.setOut(ps);
			Main.main(new String[]{"phase3testcases/" + filename});
			String capturedOutput = baos.toString();
			System.setOut(originalOut);
			System.out.println(capturedOutput);
		}
	}
}
 */