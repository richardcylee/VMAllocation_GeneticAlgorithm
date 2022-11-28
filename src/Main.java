public class Main
{
	public static void main(String[] args) 
	{
		User u1 = new User(10);
		User u2 = new User(4);
		User u3 = new User(3);
		User u4 = new User(3);
		u1.addToArray(u1);
		u1.addToArray(u2);
		u1.addToArray(u3);
		u1.addToArray(u4);
		
		Population pop = new Population(3, u1.getUserArray());
		pop.printArray();
	}
}

