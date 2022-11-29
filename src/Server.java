public class Server 
{
	private static int numOfServers;
	private double reliability;
	private double latency;
	private int maxVM = 30; //number of VMs in the server
	private double latencyAdjustment; //adjustment value of latency as each VM is added or subtracted
	private int counterVM; //number of VMs allocated to the server
	private int availableVM = maxVM - counterVM; //number of VMs available in the server 
	
	Server(double reliability, double latency)
	{
		this.reliability = reliability;
		this.latency = latency;
		latencyAdjustment = latency / maxVM;
		numOfServers++;
	}
	
	public int getNUmOfServers()
	{
		return numOfServers;
	}
	
	public double getReliability()
	{
		return reliability;
	}
	
	public double getLatency()
	{
		return latency + (latencyAdjustment*counterVM);
	}
	
	public int getMaxVM()
	{
		return maxVM;
	}
	
	public int getCounterVM()
	{
		return counterVM;
	}
	
	public int getAvailableVM()
	{
		return availableVM;
	}
	
	public void addVM()
	{
		counterVM++;
	}
	
	public void subtractVM()
	{
		counterVM--;
	}

	public int calculateChromosomeFitness()
	{
		return (int)((reliability*1000)-(int)(getLatency()*1000));
	}
}
