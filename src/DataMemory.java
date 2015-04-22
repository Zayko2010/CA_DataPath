import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataMemory{
	
	/* 
	 * AUTHOR: Tarek 
	 */
	
	int memSize = 200;
	String[] memory = new String[memSize];
	
	public DataMemory() {
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("src/Data.txt"));
			String line;
			
			for (int i = 0; (line = br.readLine()) != null; i++)
				memory[i] = line;

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
	
	public DataMemory(String[] data) {
		memory = data;
		memSize = data.length;
	}
	
	public DataMemory(File file) {
		
	}
	
	public String call(int MemRead, int MemWrite, int address1, String writeBytes) {
		
		if (address1 > memSize || address1 < 0) {
			System.out.println("Address doesn't exist in memory.");
			return null;
		}
		
		if (MemRead == 1) {
			return memory[address1];
		}
		
		if (MemWrite == 1) {	
			memory[address1] = writeBytes;
		}
		
		return null;
		
	}
//	public static void main(String [] args) {
//		DataMemory dm = new DataMemory();
//		for(int i = 0; i < dm.memory.length; i++) {
//			System.out.println(dm.memory[i]);
//		}
//	}
}