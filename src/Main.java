import java.util.InputMismatchException;
import java.util.Scanner;

public class Main //test
{
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		int numOfUsers = 0; //stores number of users

		System.out.print("Please enter the number of users (max 10): "); //prompt user for input for number of users
		boolean validInput1 = false;
		while(validInput1 == false)
		{
			try
			{
				numOfUsers = input.nextInt(); //obtains numOfUsers from user
				if(numOfUsers > 0 && numOfUsers <= 10) //# of Users is between 1 and 10 (inclusive)
				{
					User.setNumOfUsers(numOfUsers); //initializes numOfUsers static variable in User class
					validInput1 = true; //condition to exit while-loop
				}
				else
				{
					System.out.print("Please enter the number of users (max 10): "); //re-prompt user for input
				}
			}
			catch(InputMismatchException e)
			{
				System.out.print("Please enter the number of users (max 10): "); //re-prompt user for input
				input.next();
			}
		}

		for(int i=0;i<numOfUsers;i++) //for the number of users required, prompt user for # of VM requests
		{
			System.out.print("Please enter the number of VMs required for User #" + (i+1) + " (max 30): ");

			boolean validInput2 = false;
			while(validInput2 == false)
			{
				try
				{
					int numOfVMsPerUser = input.nextInt();
					if(numOfVMsPerUser > 0 && numOfVMsPerUser <= 30) //# of VM requests is between 1 and 30 (inclusive)
					{
						User u = new User(numOfVMsPerUser); //creates User object
						u.addToArray(u);  //inserts user object to array
						validInput2 = true;
					}
					else
					{
						System.out.print("Please enter the number of VMs required for User #" + (i+1) + " (max 30): ");
					}
				}
				catch(InputMismatchException e)
				{
					System.out.print("Please enter the number of VMs required for User #" + (i+1) + " (max 30): ");
					input.next();
				}
			}
		}

		//create the 5 server objects
		Server s1 = new Server(0.95, 0.15);
		Server s2 = new Server(0.93, 0.20);
		Server s3 = new Server(0.91, 0.11);
		Server s4 = new Server(0.87, 0.17);
		Server s5 = new Server(0.85, 0.19);

		Population pop = new Population(5, User.getUserArray(), s1, s2, s3, s4, s5); //generate population with 5 server objects
	
		pop.geneticAlgorithm(User.getUserArray(), s1, s2, s3, s4, s5); //run the GA and print the output

		input.close(); //closes Scanner object
	}
}

