import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Sabrout start
public class InstructionMemory
{
	private static String [] instructions = new String [100];
	private static int address = 0;
	int count = 0;

	public InstructionMemory ()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("src/Program.txt"));
			String line;
			if ((line = br.readLine()) != null)
			{
				address = Integer.parseInt(line);
			}
			while((line = br.readLine().trim()) != null)
			{
				instructions[address] = line;
				address++;
				count++;
			}
			br.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("IO Exception");
			e.printStackTrace();
		}
	}
	//Sabrout end
	
	//The Key Start
	public String[] getInstruction(int address)
	{
		String instruction = instructions[address]; 
		String[] tmp = instruction.split(",");
		
		if(tmp.length <= 1)
		{
			Operation.incrementPC();
			return getInstruction(++address);
		}
		
		String[] tmp2 = tmp[0].split(" ");
		
		String[] instructArguments = new String[tmp.length + tmp2.length];
		
		instructArguments[0] = tmp2[0].trim();
		instructArguments[1] = tmp2[1].trim();
		
		for (int i = 1; i < tmp.length; i++)
		{
			instructArguments[i + 1] = tmp[i].trim();
		}
		
		return instructArguments;
	}
	
	public int jumpAddress(String name)
	{
		for (int i = 0; i < instructions.length; i++)
		{
			if(instructions[i].startsWith(name))
			{
//				Might need to increment i before returning
				return i;
			}
		}
		return -1;
	}
	
	public static ArrayList<Integer> getBranches()
	{
		ArrayList<Integer> branches = new ArrayList<Integer>();
		for (int i = 0; i < instructions.length; i++)
		{
			if(instructions[i].startsWith("beq") || instructions[i].startsWith("bne") 
					|| instructions[i].startsWith("j") || instructions[i].startsWith("jr")
					|| instructions[i].startsWith("jal"))
			{
				branches.add(i);
			}
		}
		return branches;
	}
	//The Key End
}