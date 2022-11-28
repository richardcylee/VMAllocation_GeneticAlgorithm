public class Server 
{
	private static int numOfServers;
	private int reliability;
	private int latency;
	private int maxVM; //number of VMs in the server
	private int latencyAdjustment = latency / maxVM; //adjustment value of latency as each VM is added or subtracted
	private int counterVM; //number of VMs allocated to the server
	private int availableVM = maxVM - counterVM; //number of VMs available in the server 
	
	Server(int reliability, int latency)
	{
		this.reliability = reliability;
		this.latency = latency;
		numOfServers++;
	}
	
	public int getNUmOfServers()
	{
		return numOfServers;
	}
	
	public int getReliability()
	{
		return reliability;
	}
	
	public int getLatency()
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
}
