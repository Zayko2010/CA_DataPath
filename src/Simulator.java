
public class Simulator
{
	public void init()
	{
	
	}
	
	public static void main(String[] args)
	{
		String x = "next: ";
		
		String[] tmp = x.split(",");
		String[] tmp2 = tmp[0].split(" ");
		
		String[] instructArguments = new String[tmp.length + tmp2.length];
		
		instructArguments[0] = tmp2[0].trim();
		instructArguments[1] = tmp2[1].trim();
		
		for (int i = 1; i < tmp.length; i++)
		{
			instructArguments[i + 1] = tmp[i].trim();
		}
		
		for (int i = 0; i < instructArguments.length; i++)
		{
			System.out.println(instructArguments[i]);
		}
		
		System.out.println(instructArguments[2].charAt(0));
		System.out.println(instructArguments[2].substring(2, 5));
	}
}
