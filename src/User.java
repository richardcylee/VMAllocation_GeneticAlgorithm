public class User 
{
	private int numOfVMsReq; //holds the number of VMs required by each User
	private static int numOfUsers; //holds the total number of Users
	private static User[] userArray = new User[10]; //program allows a maximum of 10 Users
	private static int index = 0; //keeps track of the index for User objects being added to the array
	private static int sumOfVMsReq = 0; //the total sum of VM requests for all users combined
	
	User(int numOfVMsReq) //constructor that initializes the number of VMs required for each user
	{
		this.numOfVMsReq = numOfVMsReq;
		sumOfVMsReq += numOfVMsReq; //adds the User's required VMs to the sum
	}

	public static void setNumOfUsers(int numOfUsers) //initializes numOfUsers to keep track of the total number of User objects required
	{
		User.numOfUsers = numOfUsers;
	}
	
	public int getNumOfVMsReq() //returns the number of VMs required by the user
	{
		return numOfVMsReq;
	}

	public static int getNumOfUsers() //returns the total number of users
	{
		return numOfUsers;
	}
	
	public static User[] getUserArray() //returns the array holding the User objects generated
	{
		return userArray;
	}
	
	public static int getSumOfVMsReq() //returns the total number of VM requests for all users combined
	{
		return sumOfVMsReq;
	}

	public void addToArray(User user) //adds the User object to the User array
	{
		userArray[index] = user;
		index++;
	}
}
	
