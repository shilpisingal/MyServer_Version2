package com.simple.server;

import java.util.concurrent.TimeUnit;

//************ Represents a example job that prints the given list of numbers one-by-one, with a one second delay between each ******
 
public class PrintJob extends Job {

	// Is this the first time the job has run
	private boolean isFirstRun = true;
	
	private String clientName;

	// Store our progress so we can come back if we've been interrupted by the
	// timer stopping 
	private int nextToProcess = 1;

	// #of times the print job is to be executed
	private int input;

	public PrintJob(int input, String clientName) {
		this.input = input;
		this.clientName = clientName;
	}

	@Override
	public boolean isComplete() {
		// Have we printed all the numbers?
		return nextToProcess >= input;
	}

	@Override
	public void runJob(Timer timer) 
	{
		isFirstRun = false;
		// For each number that we haven't yet completed
		for (int i = nextToProcess; i < input; i++) 
		{
			// Has the timer expired indicating that our time slice is over?
			if (timer.expired()) {
				// If so, return, remembering where we got to using the
				// nextToProcess variable so we can pick up where we left off
				// the next time this method is run
				return;
			}

			System.out.println("Client "+clientName+", Counter Value: "+i);
			nextToProcess = i + 1;			
			try {
				TimeUnit.SECONDS.sleep(1); //Add 1 second delay between print statements 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}//end for (int i = nextToProcess; i < input; i++) 
	}//end method runJob

	//Is this the first time we've run this job? 
	@Override
	public boolean isFirstRun() {
		return isFirstRun;
	}

}
