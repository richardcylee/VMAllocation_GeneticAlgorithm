import java.util.Random;

public class Population 
{
	private int numOfServers; // number of servers in the data center
	private final int MAXGEN = 100; // initialize based on the maximum number of generations allowed
	private int[] generationFitness = new int[MAXGEN]; // array to hold fitness
	private int[][] population; // [User ID][User VM #][Server ID] – User ID and User VM # are provided
									// sequentially but Server ID is randomly generated
	private int sumOfVMsReq;
	
	Population(int numOfServers, User userArr[], Server s1, Server s2, Server s3, Server s4, Server s5) 
	{
		sumOfVMsReq = User.getSumOfVMsReq();
		population = new int [sumOfVMsReq][4]; 
		Random rand = new Random(); //used to generate random integer 
		
		this.numOfServers = numOfServers;
		
		int row = 0;
		for(int n=0;n<User.getNumOfUsers();n++) 
		{
			for(int i=0;i<userArr[n].getNumOfVMsReq();i++) //userArray[0].getNumOfVMsReq() 10 -> 4 -> 6
			{
				population[row][0] = n+1;//initializes user number
				population[row][1] = i+1;//initializes VM number
				population[row][2] = rand.nextInt(5) + 1; //generates random integer from 1 to 5 (inclusive)

				switch (population[row][2]) 
				{
					case 1:
						s1.addVM();
						break;
					case 2: 
						s2.addVM();
						break;
					case 3: 
						s3.addVM();
						break;
					case 4: 
						s4.addVM();
						break;
					case 5: 
						s5.addVM();
						break;
					default:
						System.err.print("PROGRAM CRASHING BYE");
						System.exit(0);
				}

				row++;
			}
		}

		int row2 = 0;
		for(int n=0;n<User.getNumOfUsers();n++) 
		{
			for(int i=0;i<userArr[n].getNumOfVMsReq();i++) //userArray[0].getNumOfVMsReq() 10 -> 4 -> 6
			{
				switch (population[row2][2]) 
				{
					case 1:
						population[row2][3] = s1.calculateChromosomeFitness();
						break;
					case 2: 
						population[row2][3] = s2.calculateChromosomeFitness();
						break;
					case 3: 
						population[row2][3] = s3.calculateChromosomeFitness();
						break;
					case 4: 
						population[row2][3] = s4.calculateChromosomeFitness();
						break;
					case 5: 
						population[row2][3] = s5.calculateChromosomeFitness();
						break;
					default:
						System.err.print("PROGRAM CRASHING BYE");
						System.exit(0);
				}
				
				row2++;
			}
		}
	}
	
	public void printArray()
	{
		System.out.println();
		for(int i=0;i<sumOfVMsReq;i++)
		{
			for(int j=0;j<4;j++)
			{
				System.out.print(population[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void geneticAlgorithm(User userArr[], Server s1, Server s2, Server s3, Server s4, Server s5)
	{
		Random rand = new Random();
		int chromosome1, chromosome2;
		String chromosome1BitString = " ", chromosome2BitString = " ";
		int curGen = 0;
		
		while(curGen < MAXGEN)
		{
			int sum = 0;
			for(int n=0;n<population.length;n++)
			{
				sum += population[n][3];
			}
			generationFitness[curGen] = sum;

			chromosome1 = rand.nextInt(population.length) + 1; //generates a random number from 1 to population's length
			chromosome2 = rand.nextInt(population.length) + 1; //generates a random number from 1 to population's length
			while(chromosome2 == chromosome1) //if chromosome2 is the same as chromosome1 (same row), re-generate chromosome2
			{
				chromosome2 = rand.nextInt(population.length) + 1;
			}

			switch (population[chromosome1][2]) //initializes chromosome1BitString by using chromosome1's server number
			{
				case 1:
					chromosome1BitString = "001";
					break;
				case 2:
					chromosome1BitString = "010";
					break;
				case 3:
					chromosome1BitString = "011";
					break;
				case 4:
					chromosome1BitString = "100";
					break;
				case 5:
					chromosome1BitString = "101";
					break;
				default:
					break;
			}

			switch (population[chromosome2][2]) //initializes chromosome2BitString by using chromosome2's server number
			{
				case 1:
					chromosome2BitString = "001";
					break;
				case 2:
					chromosome2BitString = "010";
					break;
				case 3:
					chromosome2BitString = "011";
					break;
				case 4:
					chromosome2BitString = "100";
					break;
				case 5:
					chromosome2BitString = "101";
					break;
				default:
					break;
			}

			String chromosome3BitString, chromosome4BitString;
			int chromosome3ServerNumber, chromosome4ServerNumber;
			chromosome3BitString = chromosome1BitString.substring(0,1)+chromosome2BitString.substring(1); //perform crossover at 0 | 0 0 
			chromosome4BitString = chromosome2BitString.substring(0,1)+chromosome1BitString.substring(1); //perform crossover at 0 | 0 0 

			switch (chromosome3BitString) //initializes chromosome2BitString by using chromosome2's server number
			{
				case "001":
					chromosome3ServerNumber = 1;
					break;
				case "010":
					chromosome3ServerNumber = 2;
					break;
				case "011":
					chromosome3ServerNumber = 3;
					break;
				case "100":
					chromosome3ServerNumber = 4;
					break;
				case "101":
					chromosome3ServerNumber = 5;
					break;
				default:
					int index = rand.nextInt(3); //generates 0, 1 or 2 (index for substring mutation)
					char temp = chromosome3BitString.charAt(index);
					if(temp == '1')
					{
						//replace with 0
					}
					else //temp == "0"
					{
						//replace with 1
					}
					break;
			}

			switch (chromosome4BitString) //initializes chromosome2BitString by using chromosome2's server number
			{
				case "001":
					chromosome3ServerNumber = 1;
					break;
				case "010":
					chromosome3ServerNumber = 2;
					break;
				case "011":
					chromosome3ServerNumber = 3;
					break;
				case "100":
					chromosome3ServerNumber = 4;
					break;
				case "101":
					chromosome3ServerNumber = 5;
					break;
				default:
					//perform mutation
					break;
			}

			//crossover algorithm + mutate if no improvement 
		}
	}
}
