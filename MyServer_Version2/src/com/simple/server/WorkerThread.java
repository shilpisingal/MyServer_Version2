package com.simple.server;

import java.io.BufferedReader;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;


public class WorkerThread implements Runnable {

	// The length of a time slice in milliseconds
	public static final int RUN_TIME_MILLIS = 4000;

	// TCP Components
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;

	// Name of the client
	private String clientName = null;
	// The work queue/executor
	private ExecutorService executor;

	// The in progress job for this client
	private Job job;

	// Main Constructor
	public WorkerThread(Socket socket, ExecutorService executor) {
		try {
			this.client = socket;
			// Set timeout so an exception will be thrown if there is no message
			socket.setSoTimeout(1000);
			this.executor = executor;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println(e);
		}
	}// end constructor

	// *********************************** Business processing logic ********************
	public void run() {
		try {

			// Timer to count the client's time slice
			Timer timer = new Timer();
			timer.start(RUN_TIME_MILLIS);

			// While we still have available time
			while (!timer.expired())
			{
				// If we don't have any in progress jobs for this client, load a new one
				if (job == null)
				{									
					/*
					 * * Read message from the client, if the client has no
					 * message, this will timeout and an exception will be
					 * thrown
					 */
					String message = in.readLine();
					if (message == null) {
						if (client.isClosed()) {
							System.out.println("Closing client connection:"+clientName);
							closeConnection();
							return;
						}
					} else {
						if (message.indexOf("name ") == 0) {
							// If a message was received setting the client name
							// In the format: "name A"

							clientName = message.substring(5);
							//System.out.println("> Client name is: " + clientName);
							
                            //Create a random number between 10 and 30. 
							//This is how many times the print statement will be executed 
							int randomNum = ThreadLocalRandom.current().nextInt(10, 30 + 1);
							
							// Create a new print job (prints out
							// the given numbers with one second
							// delays between)
							job = new PrintJob(randomNum,clientName);
							out.println("job status " + job.getID() + " created");
							//System.out.println("> New job received from " + clientName() + ". Job " + job.getID() + " created");
							
						} //end if (message.indexOf("name ") == 0)
					}//end else 
				}//end if (job == null)

				// If we have a new job or a previously started job
				if (job != null) 
				{
					if (job.isFirstRun()) {
						System.out.println("> Client " + clientName() + ": Job " + job.getID() +" starting");
					} else {
						System.out.println("> Client " + clientName() + ": Job " + job.getID() + " resuming");
					}

					// Start the job, stopping if we exceed the timer
					job.runJob(timer);

					// If the job finished completely
					if (job.isComplete()) {
						System.out.println("> Client " + clientName() + ": Job " + job.getID() + " complete");
						// Inform the client
						out.println("job status " + job.getID() + " complete");
						job = null;
						break;
					} else {
						System.out.println("> Client " + clientName() + ": Job " + job.getID() + " paused");
					}
				}//end if (job != null) 
			}//end while (!timer.expired())

			if(job !=null)
			{
				// Add this client back to the queue for another time slice
				executor.execute(this);
				//System.out.println("> Client " + clientName() + "'s " + (RUN_TIME_MILLIS / 1000) + " second time slice end");
			}				
			
		} catch (SocketTimeoutException e1) {
			// Socket had nothing to send - queue up to go again
			executor.execute(this);
			System.out.println("> Client " + clientName() + "'s " + (RUN_TIME_MILLIS / 1000) + " second time slice end");
			return;
		} catch (SocketException e2) {
			// Client disconnected
			System.out.println("> Client " + clientName() + " disconnected");
			closeConnection();
			return;
		} catch (IOException e) {
			closeConnection();
			return;
		}

	}

	private void closeConnection() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// *********************************** Execute the task using executor service ********************
	public void execute() {
		executor.execute(this);
	}
	
	public String clientName(){
		return clientName == null ? "[NAME UNKNOWN]" : clientName;
	}

}