import java.util.List;

public class ConcreteStrategyTime implements Strategy {

	@Override
	public void addClient(List<Server> servers, Client t) {
		Server best = null;
		 int min = Integer.MAX_VALUE;
		 for(Server s: servers){
			 int p = s.getWaitingPeriod();
			 if(p < min){
				 min = p;
				 best = s;
			 }
		 }
		 best.addClient(t);
	}

}
