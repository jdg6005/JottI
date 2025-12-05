package testers;

import project.Main;

import java.io.*;

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
