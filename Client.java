
public class Client implements Comparable<Client>{
	
  private int arrivalTime;
  private int processingTime;
  
  private long realArrivalTime;
  //FinishTime=arrivalTime + processingPeriod+ waitingPeriodOnChosenServer
  
	public int getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@Override
	public int compareTo(Client arg0) {
		return this.arrivalTime - arg0.arrivalTime;
	}
	public long getRealArrivalTime() {
		return realArrivalTime;
	}
	public void setRealArrivalTime(long realArrivalTime) {
		this.realArrivalTime = realArrivalTime;
	}
	
  
}
