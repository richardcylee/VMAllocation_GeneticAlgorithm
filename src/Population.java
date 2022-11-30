import java.util.Random;

public class Population 
{
	private final int MAXGEN = 500; // initialize based on the maximum number of generations allowed
	private int[] generationFitness = new int[MAXGEN]; // array to hold fitness
	private int[][] population; // [User ID][User VM #][Server ID] – User ID and User VM # are provided
									// sequentially but Server ID is randomly generated
	private int sumOfVMsReq;
	
	Population(int numOfServers, User userArr[], Server s1, Server s2, Server s3, Server s4, Server s5) 
	{
		sumOfVMsReq = User.getSumOfVMsReq();
		population = new int [sumOfVMsReq][4]; 
		Random rand = new Random(); //used to generate random integer 
		
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
						System.err.print("PROGRAM CRASHING BYE1");
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
						System.err.print("PROGRAM CRASHING BYE2");
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
		int chromosome1Index, chromosome2Index;
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

			chromosome1Index = rand.nextInt(population.length); //generates a random number from 1 to population's length
			chromosome2Index = rand.nextInt(population.length); //generates a random number from 1 to population's length
			while(chromosome2Index == chromosome1Index) //if chromosome2 is the same as chromosome1Index (same row), re-generate chromosome2
			{
				chromosome2Index = rand.nextInt(population.length);
			}

			switch (population[chromosome1Index][2]) //initializes chromosome1BitString by using chromosome1Index's server number
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

			switch (population[chromosome2Index][2]) //initializes chromosome2BitString by using chromosome2's server number
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
			int chromosome3ServerNumber = 0, chromosome4ServerNumber = 0;
			chromosome3BitString = chromosome1BitString.substring(0,1)+chromosome2BitString.substring(1); //perform crossover at 0 | 0 0 
			chromosome4BitString = chromosome2BitString.substring(0,1)+chromosome1BitString.substring(1); //perform crossover at 0 | 0 0 

			boolean validInput = false;
			while(validInput == false) //initializes chromosome3ServerNumber by using chromosome3BitString
			{
				switch (chromosome3BitString) 
				{
					case "001":
						chromosome3ServerNumber = 1;
						validInput = true;
						break;
					case "010":
						chromosome3ServerNumber = 2;
						validInput = true;
						break;
					case "011":
						chromosome3ServerNumber = 3;
						validInput = true;
						break;
					case "100":
						chromosome3ServerNumber = 4;
						validInput = true;
						break;
					case "101":
						chromosome3ServerNumber = 5;
						validInput = true;
						break;
					default:
						System.out.println("I'm stuck 1");
						int index = rand.nextInt(3); //generates 0, 1 or 2 (index for substring mutation)
						char temp = chromosome3BitString.charAt(index);
						if(temp == '1')
						{
							if(index == 2)
							{
								chromosome3BitString = chromosome3BitString.substring(0,index) + '0';
							}
							else
							{
								chromosome3BitString = chromosome3BitString.substring(0,index) + '0' + chromosome3BitString.substring(index+1);
							}
						}
						else //temp == "0"
						{
							if(index == 2)
							{
								chromosome3BitString = chromosome3BitString.substring(0,index) + '1';
							}
							else
							{
								chromosome3BitString = chromosome3BitString.substring(0,index) + '1' + chromosome3BitString.substring(index+1);
							}
						}
						break;
				}
			}
		
			boolean validInput2 = false;
			while(validInput2 == false) //initializes chromosome4ServerNumber by using chromosome4BitString
			{
				switch (chromosome4BitString) 
				{
					case "001":
						chromosome4ServerNumber = 1;
						validInput2 = true;
						break;
					case "010":
						chromosome4ServerNumber = 2;
						validInput2 = true;
						break;
					case "011":
						chromosome4ServerNumber = 3;
						validInput2 = true;
						break;
					case "100":
						chromosome4ServerNumber = 4;
						validInput2 = true;
						break;
					case "101":
						chromosome4ServerNumber = 5;
						validInput2 = true;
						break;
					default:
						System.out.println("I'm stuck 2");
						int index2 = rand.nextInt(3); //generates 0, 1 or 2 (index for substring mutation)
						char temp2 = chromosome4BitString.charAt(index2);
						if(temp2 == '1')
						{
							if(index2 == 2)
							{
								chromosome4BitString = chromosome4BitString.substring(0,index2) + '0';
							}
							else
							{
								chromosome4BitString = chromosome4BitString.substring(0,index2) + '0' + chromosome4BitString.substring(index2+1);
							}
						}
						else //temp2 == "0"
						{
							if(index2 == 2)
							{
								chromosome4BitString = chromosome4BitString.substring(0,index2) + '1';
							}
							else
							{
								chromosome4BitString = chromosome4BitString.substring(0,index2) + '1' + chromosome4BitString.substring(index2+1);
							}
						}
						break;
				}
			}

			//store parents' fitness & server values in case it needs to be restored
			int chromosome1Fitness = population[chromosome1Index][3]; 
			int chromosome2Fitness = population[chromosome2Index][3];
			int chromosome1Server = population[chromosome1Index][2];
			int chromosome2Server = population[chromosome2Index][2];

			//insert child's server number
			population[chromosome1Index][2] = chromosome3ServerNumber;
			population[chromosome2Index][2] = chromosome4ServerNumber;

			//recalculate fitness for every chromosome
			for(int n=0;n<population.length;n++)
			{
				switch (population[n][2]) 
					{
						case 1:
							population[n][3] = s1.calculateChromosomeFitness();
							break;
						case 2: 
							population[n][3] = s2.calculateChromosomeFitness();
							break;
						case 3: 
							population[n][3] = s3.calculateChromosomeFitness();
							break;
						case 4: 
							population[n][3] = s4.calculateChromosomeFitness();
							break;
						case 5: 
							population[n][3] = s5.calculateChromosomeFitness();
							break;
						default:
							System.err.print("PROGRAM CRASHING BYE3");
							System.exit(0);
					}
			}

			int chromosome3Fitness = population[chromosome1Index][3];
			int chromosome4Fitness = population[chromosome2Index][3];

			//compare new fitness value of children to parents' stored fitness value
			if((chromosome1Fitness + chromosome2Fitness) > (chromosome3Fitness + chromosome4Fitness)) //if parents' fitness is > children's fitness
			{
				population[chromosome1Index][2] = chromosome1Server;
				population[chromosome1Index][3] = chromosome1Fitness;
				population[chromosome2Index][2] = chromosome2Server;
				population[chromosome2Index][3] = chromosome2Fitness;
			} //else do nothing as the population has improved
			
			printArray();

			curGen++;
		}

		for(int n=0;n<generationFitness.length;n++)
		{
			System.out.println(generationFitness[n]);
		}
	}
}

