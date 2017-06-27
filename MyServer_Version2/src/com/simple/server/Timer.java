package com.simple.server;
// ************  Timer class to keep track of whether a client's time slice has expired **********


public class Timer {
	
	//When was the timer started
	private long start;
	
	//After how many milliseconds is the timer over
	private int endMillis;
	
	public void start(int endMillis){
		start=System.nanoTime();		
		this.endMillis = endMillis;
	}
	
	/**
	 * Has the timer expired
	 * @return expired
	 */
	public boolean expired(){
		long elapsedTime= (System.nanoTime()-start)/1000000;
		return elapsedTime >endMillis;
	}

}//end class Timer
