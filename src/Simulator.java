import java.util.ArrayList;
import java.util.Hashtable;


public class Simulator
{
	public void init()
	{
	
	}
	
	public static void main(String[] args)
	{
		Hashtable<Integer, Object> test = new Hashtable<Integer, Object>();
		
		test.put(1, 5);
		
		int y = (int) test.get(1);
		System.out.println(y);
	}
}
