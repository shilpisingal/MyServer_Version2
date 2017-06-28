# MyServer_Version2

## Set up instuctions 
#### Step1: Update config.properties file and set the portNumber for the server to run 
#### Step2: Ensure the Resources folder is in your class path
#### Step3: Start the server. SimpleServer.java provides a main method. Run that through eclipse or through command line 
        java SimpleServer 

#### Expected Output on the Console 

Server started successfully  
ServerSocket[addr=IBM829-R90GJP76/9.65.165.251,localport=4321]  
.........................................................................  
        
**Note:**  The server dumps the IP address and port # on the console (Eg: 9.65165.251 is the IP and 4321 is the port number). Make sure the 
client config.properties file match the values 
  
#### Step4: Now start the client processes as listed in the ReadMe file for MyClient_Version2 project 
#### Sample expected Output on the Console with 2 clients A and B 
**NOTE**: The number of print statements for each client is randomly selected between 10 and 30 (WorkerThread.java line #81). 
        
> Client A: Job 0 starting  
Client A, Counter Value: 1  
Client A, Counter Value: 2  
Client A, Counter Value: 3  
Client A, Counter Value: 4   
> Client A: Job 0 paused   
> Client B: Job 1 starting  
Client B, Counter Value: 1  
Client B, Counter Value: 2  
Client B, Counter Value: 3  
Client B, Counter Value: 4  
Client B, Counter Value: 5  
> Client B: Job 1 paused  
> Client A: Job 0 resuming  
Client A, Counter Value: 5  
Client A, Counter Value: 6  
Client A, Counter Value: 7  
Client A, Counter Value: 8  
Client A, Counter Value: 9  
> Client A: Job 0 paused  
> Client B: Job 1 resuming  
Client B, Counter Value: 6  
Client B, Counter Value: 7  
Client B, Counter Value: 8  
Client B, Counter Value: 9  
Client B, Counter Value: 10  
> Client B: Job 1 paused  
> Client A: Job 0 resuming  
Client A, Counter Value: 10  
Client A, Counter Value: 11  
Client A, Counter Value: 12    
> Client A: Job 0 complete    
> Client B: Job 1 resuming   
Client B, Counter Value: 11  
Client B, Counter Value: 12  
Client B, Counter Value: 13  
Client B, Counter Value: 14  
Client B, Counter Value: 15  
> Client B: Job 1 paused  
> Client B: Job 1 resuming  
Client B, Counter Value: 16  
Client B, Counter Value: 17  
Client B, Counter Value: 18  
Client B, Counter Value: 19  
Client B, Counter Value: 20  
> Client B: Job 1 complete  
        
#### To-Do's 
1) Time slices are currently hard-coded (WorkerThread.java, line# 17)  
2) Need a stopServer method 

