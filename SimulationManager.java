import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RunnableScheduledFuture;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SimulationManager implements Runnable{
    //data read from ui 
	
	public int maxProcessingTime = 10;
	public int minProcessingTime=2;
	
	public int numberOfServers =3;
	public int numberOfClients=100;
	public int timeLimit=100;
	public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
	
	//entity responsible with queue management and client distribution
	private Scheduler scheduler;
	//frame for displaying simulation
	private SimulationFrame frame;
	//pool of tasks(client shopping in the store)
	private List<Client> generatedClients;
	private List<Server> servers;
	private Strategy strategy;
	public SimulationManager(int numberOfServers, int numberOfClients, int timeLimit, int strategyType){
		this.numberOfServers = numberOfServers;
		this.numberOfClients = numberOfClients;
		this.timeLimit = timeLimit;
		//initialize the scheduler
		
		// create and start numberOfServers threads
		servers = new ArrayList<Server>();
		for(int i=0; i<numberOfServers; i++){
			Server s = new Server();
			Thread t = new Thread((Runnable) s);
			t.start();
			servers.add(s);
		}
		//initialize selection strategy =>createStrategy
		if(strategyType == 0){
			strategy = new ConcreteStrategyTime();
		}else{
			strategy = new ConcreteStrategyQueue();
		}
		
		
		//generate numberOfClients clients using generateNRandomTasks()
		//and store them to generatedTasks
		generateRandomClients(numberOfClients);
		
		//initialize frame to display simulation
		frame = new SimulationFrame();
	}
	
	
	private void generateRandomClients(int n){
		//generate N random tasks
		//-random processing time
		//minProcesssingTime <processingTime < maxProcessingTime
		//random arrivalTime
		//sort list with respect to arrivalTime
		generatedClients = new ArrayList<Client>();
		Random r = new Random();
		for(int i=0; i<n; i++){
			Client client = new Client();
			client.setArrivalTime(r.nextInt(timeLimit));
			client.setProcessingTime(minProcessingTime + r.nextInt(maxProcessingTime - minProcessingTime + 1));
			generatedClients.add(client);
			//System.out.println(r.nextInt(timeLimit));
			//System.out.println(client.getProcessingTime());
			/*for(i=client.getProcessingTime();i>0;i--){
			  System.out.println(client.getProcessingTime());
			}
			*/
		}  
		Collections.sort(generatedClients);


	}
	public void run(){
		int currentTime=0;
		Client[][] allClients = new Client[numberOfServers][];
		long[] waitingTimes = new long[numberOfServers];
		boolean hasClients=true;
		 while(currentTime<timeLimit || hasClients){
			 //iterate generatedTasks list and pick tasks that have the
			 //arrivalTime equal with the currentTime
			 ArrayList<Client> arrivingClients = new ArrayList<Client>();
			 for(Client cl: generatedClients){
				 if(cl.getArrivalTime() == currentTime){
					 arrivingClients.add(cl);
				 }else{
					 break;
				 }
			  	// System.out.println(cl.getArrivalTime());
			   System.out.println(cl.getProcessingTime());
				// System.out.println(generatedClients);
			 }
			 //generatedClients.toArray(allClients[0]);
			 //  -send task to queue by calling the dispatchTask method
			 for(Client cl: arrivingClients){
				 strategy.addClient(servers, cl);
			 }
			 //from Scheduler
			 //- delete client from list
			 for(Client cl: arrivingClients){
				 generatedClients.remove(cl);
			 }
			 //update UI frame
			 hasClients = false;
			 for(int i=0; i<servers.size(); i++){
				 allClients[i] = servers.get(i).getClients();
				 waitingTimes[i] = servers.get(i).getAvgWaitingTime();
				 if(allClients[i].length > 0){
					 hasClients = true;
				 }
			 }
			 currentTime++;
			 frame.displayData(allClients, waitingTimes, currentTime);
			 //wait an interval of 1 second
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	/**
	 * 
	 * @return nrServers, nrClients, totalTime
	 */
	public static Integer[] getSimulationParams(){
		Integer[] params = new Integer[4];
		JTextField fieldNrServers = new JTextField("3");
		JTextField fieldNrClients = new JTextField("100");
		JTextField fieldTotalTime = new JTextField("100");
		String[] strategies = {"queue time", "queue size"};
		JComboBox comboStrategies = new JComboBox(strategies);
		comboStrategies.setSelectedIndex(0);
		final JComponent[] inputs = new JComponent[]{
				new JLabel("nr. servers"),
				fieldNrServers,
				new JLabel("nr. clients"),
				fieldNrClients,
				new JLabel("total time"),
				fieldTotalTime,
				new JLabel("strategy"),
				comboStrategies
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, 
				"Please select simulation parameters", JOptionPane.PLAIN_MESSAGE);
		if(result == JOptionPane.OK_OPTION){
			params[0] = Integer.parseInt(fieldNrServers.getText());
			params[1] = Integer.parseInt(fieldNrClients.getText());
			params[2] = Integer.parseInt(fieldTotalTime.getText());
			params[3] = comboStrategies.getSelectedIndex();
		}
		
		return params;
	}
	
	public static void main(String[] args){
		Integer[] params = getSimulationParams();
		SimulationManager gen= new SimulationManager(params[0].intValue(), 
									params[1].intValue(), params[2].intValue(), params[3].intValue());
		Thread t=new Thread((Runnable) gen);
		t.start();
	}
}
