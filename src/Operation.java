import java.util.Hashtable;


public class Operation {

	static int PC = 0;
	InstructionMemory im = new InstructionMemory();
	ControlUnit cu = new ControlUnit();
	DataMemory dm = new DataMemory();
	Registers rg = new Registers();
	ALU alu = new ALU();
	boolean mux1 = false;
	boolean mux2 = false;
	boolean mux3 = false;
	String[] curIns;

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

	public void fetch()
	{
		String[] curIns = im.getInstruction(PC);
		PC++;

		FchDcd.put("Instruction", curIns);
		FchDcd.put("PC", PC);
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
				DcdExe.put("do", "a");
			}
			else if(ins[0].startsWith("l") && !ins[0].equals("lui"))
			{
				DcdExe.put("offset", ins[2].charAt(0));
				DcdExe.put("SrcReg", ins[2].substring(2, 5));
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
				DcdExe.put("DestReg", ins[2].substring(2, 5));
				DcdExe.put("SrcReg", ins[1]);
				DcdExe.put("do", "s");
			}
		}
		else if(ctrlSignals.get("Type") == 'j')
		{
			//TODO Handle the jump conditions, in text reading and passing prams
			if(ins[0].equals("j"))
			{
				DcdExe.put("Jaddress", im.jumpAddress(ins[1]));
				DcdExe.put("do", "j");
			}
			else if(ins[0].equals("jal"))
			{
				DcdExe.put("Jaddress", im.jumpAddress(ins[1]));
				DcdExe.put("do", "jal");
			}
			else if(ins[0].equals("jr"))
			{
				DcdExe.put("Jaddress", rg.getValueOf(ins[1]));
				DcdExe.put("do", "jr");
			}
		}

		DcdExe.put("ctrlSignals", ctrlSignals);
		DcdExe.put("PC", FchDcd.get("PC"));
	}

	@SuppressWarnings("rawtypes")
	public void execute(String [] ins)
	{
		Hashtable ctrlSignals = (Hashtable) DcdExe.get("ctrlSignals");
		if(DcdExe.get("do").equals("r"))
		{
			int ALUOp = (int)(ctrlSignals.get("ALUOp"));
			int data1 = (int) DcdExe.get("data1");
			int data2 = (int) DcdExe.get("data2");
			int AluRslt = ALU.call(ALUOp, data1, data2);

			ExeMem.put("AluRslt", AluRslt);
			ExeMem.put("do", "r");
		}
		else if(DcdExe.get("do").equals("b"))
		{
			int ALUOp = (int)(ctrlSignals.get("ALUOp"));
			int data1 = (int) DcdExe.get("data1");
			int data2 = (int) DcdExe.get("data2");
			int AluRslt = ALU.call(ALUOp, data1, data2);

			ExeMem.put("BrnachRslt", AluRslt);
		}
		else if(DcdExe.get("do").equals("b"))
		{
			
		}

		ExeMem.put("ctrlSignals", ctrlSignals);
		ExeMem.put("PC", DcdExe.get("PC"));
	}

	public void memory() {

	}

	public void writeBack() {

	}

}
