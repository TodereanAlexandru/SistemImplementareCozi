import java.util.List;

public class Scheduler {
 private List<Server> servers;
 private int maxNoServers;
 private int maxTasksPerServer;
 private Strategy strategy;
 
 public Scheduler(int maxNoServers,int maxTasksPerServer){
	 //for maxNoServers
	 //-create server object
	 //-create thread with the object
 }
 
 public void changeStrategy(SelectionPolicy policy){
	 //apply strategy patter to instantiate the strategy with the concrete
	 //strategy corresponding the policy
	 if(policy ==SelectionPolicy.SHORTEST_QUEUE){
		 strategy = new ConcreteStrategyQueue();
	 }
	 if(policy ==SelectionPolicy.SHORTEST_TIME){
		 strategy= (Strategy) new ConcreteStrategyTime();
	 }
	 
 }
 
  public void dispatchTask(Client t) {
	//call the strategy addTask method
	}
public List<Server> getServers(){
	
	return servers;
}
 
 
}
