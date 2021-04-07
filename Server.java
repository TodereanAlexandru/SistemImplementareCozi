import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
   private BlockingQueue<Client> queue;
   private AtomicInteger waitingPeriod;
   
   private int nrClientsServed;
   private long totalWaitingTime;
    
   
    public Server(){
    	//initialize queue and waitingPeriod
    	queue = new LinkedBlockingQueue<Client>();
    	waitingPeriod = new AtomicInteger();
    	waitingPeriod.set(0);
    	nrClientsServed = 0;
    	totalWaitingTime = 0;
    }
    public void addClient(Client newClient){
    	//add Task to queue
    	//increment the waitingPeriod
    	try{
    		newClient.setRealArrivalTime(System.currentTimeMillis()/1000);
    		queue.put(newClient);
    		waitingPeriod.addAndGet(newClient.getProcessingTime());
    	}catch(InterruptedException e){
    		e.printStackTrace();
	    }
    }
    
	public void run() {
	  while(true){
		  //take the next task from queue
		  //stop the thread for a time equal with the task's processing
		  //decrement the waitingPerioad
		  try{
		  		Client client = queue.take();
		  		Thread.sleep(client.getProcessingTime()*1000);
		  		waitingPeriod.addAndGet((-1) * client.getProcessingTime());
		  		totalWaitingTime += 
		  				(System.currentTimeMillis()/1000 - 
		  						client.getRealArrivalTime());
		  		nrClientsServed++;
		  		//System.out.println(totalWaitingTime); ///////////
		  }catch(InterruptedException e){
			  e.printStackTrace();
		  }
	  }
	}
	public Client[] getClients() {
		//Client[] clients = new Client[queue.size()];
		//queue.toArray(clients);
		Object[] objClients = queue.toArray();
		Client[] clients = new Client[objClients.length];
		for(int i=0; i<clients.length; i++){
			clients[i] = (Client)objClients[i];
		}
		return clients;
	}
	public int getWaitingPeriod() {
		return waitingPeriod.get();
	}
	public int getQueueSize() {
		return queue.size();
	}
	
	public long getAvgWaitingTime(){
		if(nrClientsServed > 0)
			return totalWaitingTime / nrClientsServed;
		else
			return 0;
	}
	
		
	}


