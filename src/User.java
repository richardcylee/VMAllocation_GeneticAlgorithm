public class User 
{
	private int numOfVMsReq;
	private static int numOfUsers = 4; //this needs to be fixed
	private static User[] userArray = new User[numOfUsers]; //this needs to be dynamic 
	private static int index = 0; // *
	private static int sumOfVMsReq = 0; // *
	
	User(int numOfVMsReq)
	{
		this.numOfVMsReq = numOfVMsReq;
		sumOfVMsReq += numOfVMsReq;
	}
	
	public int getNumOfVMsReq()
	{
		return numOfVMsReq;
	}

	public int getNumOfUsers()
	{
		return numOfUsers;
	}
	
	public void addToArray(User user)
	{
		userArray[index] = user;
		index++;
	}
	
	public User[] getUserArray()
	{
		return userArray;
	}
	
	public int getSumOfVMsReq()
	{
		return sumOfVMsReq;
	}
}
	
