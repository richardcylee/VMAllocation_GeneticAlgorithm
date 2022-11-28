
public class Population 
{
	private int numOfServers; // number of servers in the data center
	private final int MAXGEN = 100; // initialize based on the maximum number of generations allowed
	private int[] generationFitness = new int[MAXGEN]; // array to hold fitness
	private int[][] population; // [User ID][User VM #][Server ID] – User ID and User VM # are provided
									// sequentially but Server ID is randomly generated
	private int sumOfVMsReq;
	
	Population(int numOfServers, User userArr[]) 
	{
		//sumOfVMsReq = userArr[0].getNumOfVMsReq();
		sumOfVMsReq = 20; //temporary value for demonstration purposes
		population = new int [sumOfVMsReq][3]; //initializes 20 x 3 array 
		
		this.numOfServers = numOfServers;
		
		int row = 0;
		
		for(int n=0;n<userArr[0].getNumOfUsers();n++) //n goes from 0 to 2
		{
			for(int i=0;i<userArr[n].getNumOfVMsReq();i++) //userArray[0].getNumOfVMsReq() 10 -> 4 -> 6
			{
				population[row][0] = n+1;//initializes user number
				population[row][1] = i+1;//initializes VM number
				population[row][2] = 3;//initializes server number
				row++;
			}
		}
	}
	
	public void printArray()
	{
		System.out.println();
		for(int i=0;i<sumOfVMsReq;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(population[i][j] + " ");
			}
			System.out.println();
		}
	}
}
