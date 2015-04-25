import java.util.Enumeration;
import java.util.Hashtable;

public class Operation
{
	static int PC = 0;
	InstructionMemory im = new InstructionMemory();
	private DataMemory dm = new DataMemory();
	private Registers rg = new Registers();

	Hashtable<String, Object> FchDcd = new Hashtable<String, Object>();
	Hashtable<String, Object> DcdExe = new Hashtable<String, Object>();
	Hashtable<String, Object> ExeMem = new Hashtable<String, Object>();
	Hashtable<String, Object> MemWB = new Hashtable<String, Object>();

	public static void incrementPC()
	{
		PC++;
	}

	public Operation(int currentPC)
	{
		PC = currentPC;
	}

	public boolean fetch()
	{
		String[] curIns = im.getInstruction(PC);
		PC++;
		
		FchDcd.put("Instruction", curIns);
		FchDcd.put("PC", PC);
		
//		if the instruction is a branch instruction, return true;
//		This is used in the simulator to solve branching hazard. 
		if(curIns[0].startsWith("b"))
			return true;
		else
			return false;
	}

	public void decode()
	{
		String[] ins = (String[]) FchDcd.get("Instruction");
		Hashtable<String, Integer> ctrlSignals = ControlUnit.decode(ins);

		if(ctrlSignals.get("Type") == 'r')
		{
			if(ins[0].equals("sll") || ins[0].equals("srl"))
			{
				DcdExe.put("data1", rg.getValueOf(ins[2]));
				DcdExe.put("data2", Integer.parseInt(ins[3]));
			}
			else
			{
				DcdExe.put("data1", rg.getValueOf(ins[2]));
				DcdExe.put("data2", rg.getValueOf(ins[3]));
			}
			
			DcdExe.put("DestReg", ins[1]);
			DcdExe.put("do", "r");
		}
		else if(ctrlSignals.get("Type") == 'i')
		{
			if(ins[0].startsWith("b"))
			{
				DcdExe.put("data1", rg.getValueOf(ins[1]));
				DcdExe.put("data2", Integer.parseInt(ins[2]));
				DcdExe.put("bAddress", im.jumpAddress(ins[3]));
				DcdExe.put("do", "b");
			}
			else if(ins[0].equals("addi"))
			{
				DcdExe.put("data1", rg.getValueOf(ins[2]));
				DcdExe.put("data2", Integer.parseInt(ins[3]));
				DcdExe.put("DestReg", ins[1]);
				DcdExe.put("do", "a");
			}
			else if(ins[0].startsWith("l") && !ins[0].equals("lui"))
			{
				DcdExe.put("offset", ins[2].charAt(0));
				DcdExe.put("SrcReg", rg.getValueOf(ins[2].substring(2, 5)));
				DcdExe.put("DestReg", ins[1]);
				DcdExe.put("do", "l");
			}
			else if(ins[0].equals("lui"))
			{
				DcdExe.put("DestReg", ins[1]);
				DcdExe.put("data", Integer.parseInt(ins[2]));
				DcdExe.put("do", "u");
			}
			else if(ins[0].startsWith("s"))
			{
				DcdExe.put("offset", ins[2].charAt(0));
				DcdExe.put("DestReg", rg.getValueOf(ins[2].substring(2, 5)));
				DcdExe.put("SrcReg", rg.getValueOf(ins[1]));
				DcdExe.put("do", "s");
			}
		}
		else if(ctrlSignals.get("Type") == 'j')
		{
			if(ins[0].equals("j"))
			{
				DcdExe.put("jAddress", im.jumpAddress(ins[1]));
				DcdExe.put("do", "j");
			}
			else if(ins[0].equals("jr"))
			{
				DcdExe.put("jAddress", rg.getValueOf(ins[1]));
				DcdExe.put("do", "j");
			}
			else if(ins[0].equals("jal"))
			{
				DcdExe.put("jAddress", im.jumpAddress(ins[1]));
				DcdExe.put("do", "jal");
			}
		}
		DcdExe.put("ctrlSignals", ctrlSignals);
		DcdExe.put("PC", FchDcd.get("PC"));
	}

	@SuppressWarnings("rawtypes")
	public void execute()
	{
		Hashtable ctrlSignals = (Hashtable) DcdExe.get("ctrlSignals");
		if(DcdExe.get("do").equals("r"))
		{
			int ALUOp = (int)(ctrlSignals.get("ALUOp"));
			int data1 = (int) DcdExe.get("data1");
			int data2 = (int) DcdExe.get("data2");
			int AluRslt = ALU.call(ALUOp, data1, data2);

			ExeMem.put("DestReg", DcdExe.get("DestReg"));
			ExeMem.put("AluRslt", AluRslt);
			ExeMem.put("do", "r");
		}
		else if(DcdExe.get("do").equals("b"))
		{
			int ALUOp = (int)(ctrlSignals.get("ALUOp"));
			int data1 = (int) DcdExe.get("data1");
			int data2 = (int) DcdExe.get("data2");
			int AluRslt = ALU.call(ALUOp, data1, data2);
			
			ExeMem.put("bAddress", DcdExe.get("bAddress"));
			ExeMem.put("BrnachRslt", AluRslt);
		}
		else if(DcdExe.get("do").equals("a"))
		{
			int ALUOp = (int)(ctrlSignals.get("ALUOp"));
			int data1 = (int) DcdExe.get("data1");
			int data2 = (int) DcdExe.get("data2");
			int AluRslt = ALU.call(ALUOp, data1, data2);
			
			ExeMem.put("DestReg", DcdExe.get("DestReg"));
			ExeMem.put("AluRslt", AluRslt);
			ExeMem.put("do", "a");
		}
		else if(DcdExe.get("do").equals("l"))
		{
			int offset = (int) DcdExe.get("offset");
			int address = (int) DcdExe.get("SrcReg");
			int AluRslt = ALU.call((int)ctrlSignals.get("ALUOp"), offset, address);
			
			ExeMem.put("lAddress", AluRslt);
			ExeMem.put("lReg", DcdExe.get("DestReg"));
			
			ExeMem.put("do", "l");
		}
		else if(DcdExe.get("do").equals("u"))
		{
			ExeMem.put("DestReg", DcdExe.get("DestReg"));
			ExeMem.put("data", DcdExe.get("data"));
			ExeMem.put("do", "u");
		}
		else if(DcdExe.get("do").equals("s"))
		{
			int offset = (int) DcdExe.get("offset");
			int address = (int) DcdExe.get("DestReg");
			int AluRslt = ALU.call((int)ctrlSignals.get("ALUOp"), offset, address);
			
			ExeMem.put("sAddress", AluRslt);
			ExeMem.put("sData", DcdExe.get("SrcReg"));
			
			ExeMem.put("do", "s");
		}
		else if(DcdExe.get("do").equals("j"))
		{
			ExeMem.put("PC", DcdExe.get("jAddress"));
			ExeMem.put("do", "j");
		}
		else if(DcdExe.get("do").equals("jal"))
		{
			ExeMem.put("PC", DcdExe.get("jAddress"));
			ExeMem.put("rtrnAddress", PC);
			
			PC = (int) DcdExe.get("jAddress");
			
			ExeMem.put("do", "jal");
		}

		ExeMem.put("ctrlSignals", ctrlSignals);
		
		if(!(DcdExe.get("do").equals("j") || DcdExe.get("do").equals("jal")))
			ExeMem.put("PC", DcdExe.get("PC"));
	}

	@SuppressWarnings("rawtypes")
	public void memory()
	{
		Hashtable ctrlSignals = (Hashtable) ExeMem.get("ctrlSignals");
		
		int MemRead = (int)ctrlSignals.get("MemRead");
		int MemWrite = (int)ctrlSignals.get("MemWrite");
		
		if(MemRead == 1)
		{
			MemWB.put("lReg", ExeMem.get("lReg"));
			MemWB.put("lData", dm.call(MemRead, MemWrite, (int) ExeMem.get("lAddress"), 
							(String)ExeMem.get("sData")));
		}
		else if(MemWrite == 1)
		{
			dm.call(MemRead, MemWrite, (int)ExeMem.get("sAddress"), (String)ExeMem.get("sData"));
		}
		else if((int)ExeMem.get("BranchRslt") == 1 && 
				(int) ((Hashtable)ExeMem.get("ctrlSignals")).get("Branch") == 1)
		{
			PC = (int) ExeMem.get("bAddress");
		}
		else
		{
			Enumeration<String> e = ExeMem.keys();
			while(e.hasMoreElements())
			{
				String key = e.nextElement();
				MemWB.put(key, ExeMem.get(key));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void writeBack()
	{
		if((int)((Hashtable)MemWB.get("ctrlSignals")).get("RegWrite") == 1)
		{
			if(MemWB.get("do").equals("r") || MemWB.get("do").equals("a"))
			{
				rg.insertIntoRegister((String)MemWB.get("DestReg"),(int) MemWB.get("AluRslt"));
			}
			else if(MemWB.get("do").equals("l"))
			{
				rg.insertIntoRegister((String)MemWB.get("lReg"),(int) MemWB.get("lData"));
			}
			else if(MemWB.get("do").equals("u"))
			{
				rg.insertIntoRegister((String)MemWB.get("DestReg"),(int) MemWB.get("data"));
			}
			else if(MemWB.get("do").equals("jal"))
			{
				rg.insertIntoRegister("$ra",(int) MemWB.get("rtrnAddress"));
			}
		}
	}
}