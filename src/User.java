public class User 
{
	private int numOfVMsReq;
	private static int numOfUsers; 
	private static User[] userArray = new User[10]; 
	private static int index = 0; 
	private static int sumOfVMsReq = 0; 
	
	User(int numOfVMsReq)
	{
		this.numOfVMsReq = numOfVMsReq;
		sumOfVMsReq += numOfVMsReq;
	}

	public static void setNumOfUsers(int numOfUsers) 
	{
		User.numOfUsers = numOfUsers;
	}
	
	public int getNumOfVMsReq()
	{
		return numOfVMsReq;
	}

	public static int getNumOfUsers()
	{
		return numOfUsers;
	}
	
	public static User[] getUserArray()
	{
		return userArray;
	}
	
	public static int getSumOfVMsReq()
	{
		return sumOfVMsReq;
	}

	public void addToArray(User user)
	{
		userArray[index] = user;
		index++;
	}
}
	
