package edu.sjsu.carbonated.server;


public class PerlExecute {

	public static void runperl(String cmdstr) {

		Process process;

		try {
			process = Runtime.getRuntime().exec(cmdstr);
			System.out.println(cmdstr);
			process.waitFor();
			if (process.exitValue() == 0) {
				System.out.println("Command Successful");
			} else {
				System.out.println("Command Failure");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		}
	}
}
