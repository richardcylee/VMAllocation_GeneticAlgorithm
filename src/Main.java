import java.util.Scanner;

public class Main
{
	public static void main(String[] args) 
	{

		Scanner input = new Scanner(System.in);

		int numOfUsers; //stores number of users
		System.out.print("Please enter the number of users (max 10): "); //prompt user for input
		numOfUsers = input.nextInt(); //obtains numOfUsers from user
		User.setNumOfUsers(numOfUsers); //initializes numOfUsers static variable in User class

		for(int i=0;i<numOfUsers;i++) //creates User objects and inserts to array
		{
			System.out.print("Please enter the number of VMs required for User #" + (i+1) + ": ");
			int numOfVMsPerUser = input.nextInt();
			User u = new User(numOfVMsPerUser);
			u.addToArray(u);
		}

		Server s1 = new Server(0.95, 0.15);
		Server s2 = new Server(0.93, 0.20);
		Server s3 = new Server(0.91, 0.11);
		Server s4 = new Server(0.87, 0.17);
		Server s5 = new Server(0.85, 0.19);

		Population pop = new Population(5, User.getUserArray(), s1, s2, s3, s4, s5); //generate population, hard coded numOfServers = 5
		pop.printArray(); //print initial population

		pop.geneticAlgorithm(User.getUserArray(), s1, s2, s3, s4, s5);

		input.close();
	}
}

