import java.util.Random;

public class Population 
{
	private final int MAXGEN = 300; //initialize based on the maximum number of generations allowed
	private int[] generationFitness = new int[MAXGEN]; //array to hold sum of fitness for all chromosomes in each generation
	private int[][] population; //[User ID][User VM #][Server ID] – User ID and User VM # are provided sequentially but Server ID is randomly generated
	private int sumOfVMsReq; //total number of user requests
	private int userColumn = 0, vmColumn = 1, serverColumn = 2, fitnessColumn = 3; //column index in the 2D array
	
	Population(int numOfServers, User userArr[], Server s1, Server s2, Server s3, Server s4, Server s5) //constructor that initializes the population array
	{
		sumOfVMsReq = User.getSumOfVMsReq(); //obtain total sum of user requests
		population = new int [sumOfVMsReq][4]; //initializes 2D population array
		Random rand = new Random(); //used to generate random integer 
		
		int row = 0;
		for(int n=0;n<User.getNumOfUsers();n++) //iterates through the number of User objects created
		{
			for(int i=0;i<userArr[n].getNumOfVMsReq();i++) //iterates through the required VMs per User object
			{
				population[row][userColumn] = n+1;//initializes user number
				population[row][vmColumn] = i+1;//initializes VM number
				population[row][serverColumn] = rand.nextInt(5) + 1; //generates random integer from 1 to 5 (inclusive) to represent the server number

				switch (population[row][serverColumn]) //takes the assigned server number and incremenets counterVM in the corresponding server
				{
					case 1:
						checkAddVM(row, s1, s1, s2, s3, s4, s5);
						break;
					case 2: 
						checkAddVM(row, s2, s1, s2, s3, s4, s5);
						break;
					case 3: 
						checkAddVM(row, s3, s1, s2, s3, s4, s5);
						break;
					case 4: 
						checkAddVM(row, s4, s1, s2, s3, s4, s5);
						break;
					case 5: 
						checkAddVM(row, s5, s1, s2, s3, s4, s5);
						break;
					default:
						System.err.print("Error @ Population constructor");
						System.exit(0);
				}
				row++;
			}
		}
		calculatePopulationFitness(userArr, s1, s2, s3, s4, s5); //calculate population fitness, initializes [row][3] of the 2D population array
		printArray(); //prints initial population's array
		generationFitness[0] = getGenerationalFitness(); //initializes first generation's generational fitness (sum)
	}

	public void calculatePopulationFitness(User userArr[], Server s1, Server s2, Server s3, Server s4, Server s5) //initializes [row][fitnessColumn] to store the fitness values of each chromosome in the population
	{
		for(int n=0;n<population.length;n++)
		{
			switch (population[n][serverColumn]) 
			{
				case 1:
					population[n][fitnessColumn] = s1.calculateChromosomeFitness();
					break;
				case 2: 
					population[n][fitnessColumn] = s2.calculateChromosomeFitness();
					break;
				case 3: 
					population[n][fitnessColumn] = s3.calculateChromosomeFitness();
					break;
				case 4: 
					population[n][fitnessColumn] = s4.calculateChromosomeFitness();
					break;
				case 5: 
					population[n][fitnessColumn] = s5.calculateChromosomeFitness();
					break;
				default:
					System.err.print("Error @ calculatePopulationFitness");
					System.exit(0);
			}
		}
	}

	public void printArray() //prints out the population array: column 1 = user number, column 2 = vm number, column 3 = server number, column 4 = fitness value
	{
		System.out.println("\n	        U  VM  S  F");

		for(int i=0;i<sumOfVMsReq;i++) //iterate through the rows
		{
			if(i<9) 
			{
				System.out.print("Chromosome " + (i+1) + ":   ");
			}
			else if(i<99)
			{
				System.out.print("Chromosome " + (i+1) + ":  ");
			}
			else
			{
				System.out.print("Chromosome " + (i+1) + ": ");
			}

			for(int j=0;j<4;j++) //iterate through the columns
			{
				if(j==0) //if printing User number
				{
					if(population[i][userColumn] >= 10)
					{
						System.out.print(population[i][j] + " ");
					}
					else
					{
						System.out.print(population[i][j] + "  ");
					}
				}
				else if(j==1) //if printing VM number
				{
					if(population[i][vmColumn] >= 10)
					{
						System.out.print(population[i][j] + "  ");
					}
					else
					{
						System.out.print(population[i][j] + "   ");
					}
				}
				else //printing Server number or fitness value
				{
					System.out.print(population[i][j] + "  ");
				}
			}
			System.out.println();
		}
	}

	public int getGenerationalFitness() //calculate existing generation fitness and store into generationFitness array
	{
		int sum = 0;
		for(int n=0;n<population.length;n++) 
		{
			sum += population[n][fitnessColumn];
		}
		return sum;
	}

	public void geneticAlgorithm(User userArr[], Server s1, Server s2, Server s3, Server s4, Server s5) //algorithm for running the GA
	{
		Random rand = new Random();
		int curGen = 1; //initializes curGen to keep track of generations of GA
		
		while(curGen < MAXGEN) //repeats GA until MAXGEN is reached
		{
			int chromosome1Index = rand.nextInt(population.length); //first parent chromosome's row index - generates a random number from 1 to population's length
			int chromosome2Index = rand.nextInt(population.length); //second parent chromosome's row index - generates a random number from 1 to population's length
			while(chromosome2Index == chromosome1Index) //while both parents are the same (same row/index), re-generate chromosome2Index
			{
				chromosome2Index = rand.nextInt(population.length);
			}

			int parent1ServerNumber = population[chromosome1Index][serverColumn]; //initializes parent1ServerNumber (parent) from the population array
			int parent2ServerNumber = population[chromosome2Index][serverColumn]; //initializes parent2ServerNumber (parent) from the population array

			int parentPopulationFitness = getGenerationalFitness(); //gets generational fitness with parent chromosomes in population, to compare with childPopulationFitness

			getServerObject(parent1ServerNumber, s1, s2, s3, s4, s5).subtractVM(); //subtracts a VM for parent1ServerNumber (parent) so the VM can be re-allocated
			getServerObject(parent2ServerNumber, s1, s2, s3, s4, s5).subtractVM(); //subtracts a VM for parent2ServerNumber (parent) so the VM can be re-allocated

			String parent1BitString = getChromosomeBitString(chromosome1Index); //initializes parent1BitString (parent) by using chromosome1Index's server number
			String parent2BitString = getChromosomeBitString(chromosome2Index); //initializes parent2BitString (parent) by using chromosome2Index's server number

			//performs crossover (crossover at 0 | 0 0 ) on parent chromosomes and stores child chromosomes' bit-string representation of resulting server numbers
			String child1BitString = parent1BitString.substring(0,1)+parent2BitString.substring(1); 
			String child2BitString = parent2BitString.substring(0,1)+parent1BitString.substring(1); 

			int child1ServerNumber = getServerNumber(child1BitString); //initializes child1ServerNumber (child) based on the corresponding bit-string representation
			int child2ServerNumber = getServerNumber(child2BitString); //initializes child2ServerNumber (child) based on the corresponding bit-string representation

			//insert child's server number into parents' server number
			population[chromosome1Index][serverColumn] = child1ServerNumber;
			population[chromosome2Index][serverColumn] = child2ServerNumber;

			//increment VM for chromosome3's (child) server to ensure there is space. If not, randomly generates a server number to replace with
			checkAddVM(chromosome1Index, getServerObject(child1ServerNumber, s1, s2, s3, s4, s5), s1, s2, s3, s4, s5); 
			checkAddVM(chromosome2Index, getServerObject(child2ServerNumber, s1, s2, s3, s4, s5), s1, s2, s3, s4, s5); 

			//the above would result in randomized server number if there are no available VMs, therefore corresponding child1ServerNumber and child2ServerNumber has to be re-initialized in case it was re-generated randomly
			child1ServerNumber = population[chromosome1Index][serverColumn];
			child2ServerNumber = population[chromosome2Index][serverColumn];

			calculatePopulationFitness(userArr, s1, s2, s3, s4, s5); //recalculate fitness for every chromosome

			int childPopulationFitness = getGenerationalFitness(); //gets generational fitness with child chromosomes in population, to compare with parentPopulationFitness

			//compare population fitness of parent with child - if parent population fitness was higher, restore parent server numbers
			if(parentPopulationFitness > childPopulationFitness)
			{
				population[chromosome1Index][serverColumn] = parent1ServerNumber; //restore parent server numbers
				population[chromosome2Index][serverColumn] = parent2ServerNumber; //restore parent server numbers

				getServerObject(child1ServerNumber, s1, s2, s3, s4, s5).subtractVM(); //subtracts a VM for child chromosome so parents' server numbers can be restored
				getServerObject(child2ServerNumber, s1, s2, s3, s4, s5).subtractVM(); //subtracts a VM for child chromosome so parents' server numbers can be restored
				checkAddVM(chromosome1Index, getServerObject(parent1ServerNumber, s1, s2, s3, s4, s5), s1, s2, s3, s4, s5); //restores parents' server for VM allocation
				checkAddVM(chromosome2Index, getServerObject(parent2ServerNumber, s1, s2, s3, s4, s5), s1, s2, s3, s4, s5); //restores parents' server for VM allocation

				calculatePopulationFitness(userArr, s1, s2, s3, s4, s5); //recalculate fitness for every chromosome
			} //else do nothing as the population has improved
		
			printArray(); //prints out the population array to illustrate each chromosome (configuration) in the population
			generationFitness[curGen] = getGenerationalFitness(); //inserts new generation's generational fitness (sum) into array
			curGen++;
		} //end of GA
		printMaxGenerationFitness(); //prints out generation fitness value
	}

	public Server getServerObject(int chromosomeServerNumber, Server s1, Server s2, Server s3, Server s4, Server s5)
	{
		switch (chromosomeServerNumber)
		{
			case 1:
				return s1;
			case 2:
				return s2;
			case 3:
				return s3;
			case 4:
				return s4;
			case 5:
				return s5;
			default:
				System.err.print("Error @ getServerObject");
				System.exit(0);
				Server error = new Server(1, 1); //shouldn't reach
				return error; //shouldn't reach
		}
	}

	public void checkAddVM(int row, Server tryAdd, Server s1, Server s2, Server s3, Server s4, Server s5)
	{
		if(tryAdd.addVM() == false) //unsuccessful attempt at adding VM due to max capacity
		{
			Random rand = new Random();
			population[row][serverColumn] = rand.nextInt(5) + 1; //generate new server number to try

			switch (population[row][serverColumn]) //takes the newly assigned server number and incremenets counterVM in the corresponding server
			{
				case 1:
					checkAddVM(row, s1, s1, s2, s3, s4, s5);
					break;
				case 2: 
					checkAddVM(row, s2, s1, s2, s3, s4, s5);
					break;
				case 3: 
					checkAddVM(row, s3, s1, s2, s3, s4, s5);
					break;
				case 4: 
					checkAddVM(row, s4, s1, s2, s3, s4, s5);
					break;
				case 5: 
					checkAddVM(row, s5, s1, s2, s3, s4, s5);
					break;
				default:
					System.err.print("Error @ Population constructor");
					System.exit(0);
			}
		} //else do nothing as attempt to add VM was successful
	}

	public String getChromosomeBitString(int chromosomeIndex)
	{
		switch (population[chromosomeIndex][serverColumn]) //initializes parent1BitString by using chromosome1Index's server number
		{
			case 1:
				return "001";
			case 2:
				return "010";
			case 3:
				return "011";
			case 4:
				return "100";
			case 5:
				return "101";
			default:
				System.exit(0);
				return "Error @ getChromosomeBitString"; //shouldn't reach
		}
	}

	public int getServerNumber(String chromosomeBitString) //returns server number integer from bit-string representation
	{
		Random rand = new Random(); //used to mutate bit string if invalid server number is obtained from crossover

		boolean validInput = false;
		while(validInput == false) //initializes chromosomeServerNumber by using chromosomeBitString
		{
			switch (chromosomeBitString) 
			{
				case "001":
					return 1;
				case "010":
					return 2;
				case "011":
					return 3;
				case "100":
					return 4;
				case "101":
					return 5;
				default: //chromosomeBitString is not a valid representation of a server number, mutate a random bit
					int index = rand.nextInt(3); //generates 0, 1 or 2 (index for substring mutation)
					if(chromosomeBitString.charAt(index) == '1')
					{
						if(index == 2)
						{
							chromosomeBitString = chromosomeBitString.substring(0,index) + '0';
						}
						else
						{
							chromosomeBitString = chromosomeBitString.substring(0,index) + '0' + chromosomeBitString.substring(index+1);
						}
					}
					else //chromosomeBitString.charAt(index) == "0"
					{
						if(index == 2)
						{
							chromosomeBitString = chromosomeBitString.substring(0,index) + '1';
						}
						else
						{
							chromosomeBitString = chromosomeBitString.substring(0,index) + '1' + chromosomeBitString.substring(index+1);
						}
					}
			}
		}
		return 0; //this should never execute
	}

	public void printMaxGenerationFitness()
	{
		for(int n=0;n<generationFitness.length;n++) //after GA, prints out generationFitness for each generation
		{
			System.out.println(generationFitness[n]);
		}
	}
}