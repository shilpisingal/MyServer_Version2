package com.simple.server;


//** Represents a job that a server may complete on behalf of a client ******

public abstract class Job {
	
	private int id;
	private static int nextJobId = 0;
	
	public Job(){
		this.id = nextJobId++;
	}
	
	public int getID(){
		return id;
	}
	
	public abstract boolean isComplete();
	
	public abstract void runJob(Timer timer);
		
	public abstract boolean isFirstRun();

}
