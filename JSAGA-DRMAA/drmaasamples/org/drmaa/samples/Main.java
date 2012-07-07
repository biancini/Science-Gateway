package org.drmaa.samples;

import java.io.*;

public class Main {
	public static void main (String[] args) {
		String line = null;
	    int val = 0;
	    
	    System.out.println("Class to test the DRMAA job submission.");
	    System.out.println("Please specify ony of the following test:");
	    System.out.println(" [1] Simple test that creates a session and prints some information.");
	    System.out.println(" [2] Simple test that submits a single job and waits for its end.");
	    System.out.println(" [3] Simple test that submits a single job and then synchronize with it to wait for its end.");
	    System.out.println(" [4] Simple test that creates a bulk of jobs and waits for their end.");
	    System.out.print("--> ");
	    
	    try {
	      BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
	      line = is.readLine();
	      val = Integer.parseInt(line);
	      
	      if (val < 1 || val > 4) throw new NumberFormatException();
	    }
	    catch (NumberFormatException ex) {
	      System.err.println("Not a valid number: " + line);
	    }
	    catch (IOException e) {
	      System.err.println("Unexpected IO ERROR: " + e);
	    }
	    
	    if (val == 1) Sample1SessionInfo.main(args);
	    if (val == 2) Sample2JobRun.main(args);
	    if (val == 3) Sample3JobRunAndSynchronize.main(args);
	    if (val == 4) Sample4JubBulkRun.main(args);
	}
}
