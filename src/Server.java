public class Server 
{
	private static final int MAXVM = 60; //maximum number of VMs allowable in the server
	private double reliability, latency, latencyAdjustment; //stores reliability and initially assigned latency value of each server as well as the adjustment value for latency as each VM is added or subtracted
	private int counterVM, availableVM; //number of VMs allocated to the server and number of VMs available in the server 
	
	Server(double reliability, double latency) //constructor for Server
	{
		this.reliability = reliability;
		this.latency = latency;
		latencyAdjustment = latency / MAXVM; //if server is full (i.e. 30 VMs assigned to it), latency is doubled
		counterVM = 0; //initializes counterVM (number of VMs allocated to the server)
		availableVM = MAXVM - counterVM; //initializes avaliableVM (number of VMs that can be allocated to the server)
	}

	public boolean addVM() //increments counterVM for whenever a VM is added to the server
	{
		if(availableVM > 0) //there are available VM slots, increment counterVM and return true
		{
			counterVM++; //increment number of VMs allocated in this server
			availableVM = MAXVM - counterVM; //recalculates the number of available VM slots
			return true;
		}
		else //there are no available slots VM, return false
		{
			return false;
		}
	}
	
	public void subtractVM() //decrements counterVM for whenever a VM is removed from the server
	{
		counterVM--; //decrement number of VMs allocated in this server
		availableVM = MAXVM - counterVM; //recalculates the number of available VM slots
	}

	public int getAvailableVM() //returns the number of VMs that can be allocated to the server
	{
		return availableVM;
	}
	
	public double getLatency() //returns latency value of server (takes into account of adjustment value)
	{
		return latency + (latencyAdjustment*counterVM);
	}

	public int calculateChromosomeFitness() //returns the calculated chromosome fitness based on reliability and latency
	{
		return (int)((reliability*1000)-(int)(getLatency()*1000));
	}
}
