public class Server 
{
	private static final int MAXVM = 60; //maximum number of VMs allowable the server
	private double reliability, latency, latencyAdjustment; //stores reliability and initially assigned latency value of each server as well as the adjustment value for latency as each VM is added or subtracted
	private int counterVM, availableVM; //number of VMs allocated to the server and number of VMs available in the server 
	
	Server(double reliability, double latency) //constructor for Server
	{
		this.reliability = reliability;
		this.latency = latency;
		latencyAdjustment = latency / MAXVM; //if server is full (i.e. 30 VMs assigned to it), latency is effectively doubled
		counterVM = 0;
		availableVM = MAXVM - counterVM;
	}
	
	public double getLatency() //returns latency value of server (takes into account of adjustment value)
	{
		return latency + (latencyAdjustment*counterVM);
	}

	public int calculateChromosomeFitness() //returns the calculated chromosome fitness based on reliability and latency
	{
		return (int)((reliability*1000)-(int)(getLatency()*1000));
	}

	public int getAvailableVM()
	{
		return availableVM;
	}
	
	public boolean addVM() //increments counterVM for whenever a VM is added to the server
	{
		if(availableVM > 0) //there are available slots, increment counterVM and return true
		{
			counterVM++; //increment counterVM
			availableVM = MAXVM - counterVM; //recalculates the number of available VM slots
			return true;
		}
		else //there are no available slots, return false
		{
			return false;
		}
	}
	
	public void subtractVM() //decrements counterVM for whenever a VM is removed from the server
	{
		counterVM--;
		availableVM = MAXVM - counterVM; //recalculates the number of available VM slots
	}
}
