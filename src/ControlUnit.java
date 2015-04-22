import java.util.Hashtable;
//The Key Start
public class ControlUnit
{
	public static Hashtable<String, Integer> decode(String[] instruction)
	{
		switch(instruction[0])
		{
		case "add": return add();
		case "addi": return addi();
		case "sub": return sub();

		case "lw":
		case "lb":
		case "lbu": return load();
		case "sw":	
		case "sb": return store();
		case "lui":

		case "sll": return sll();
		case "slr": return slr();
		case "and": return and();
		case "nor": return nor();

		case "beq": return beq();
		case "bne": return bne();
		case "j": 
		case "jal":
		case "jr": return jump();

		case "slt":
		case "sltu": return set();
		default: return null;
		}
	}

	private static Hashtable<String, Integer> add()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 0);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> addi()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 1);
		ctrlSignals.put("ALUOp", 0);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'i');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> sub()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 1);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> load()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 0);
		ctrlSignals.put("ALUSrc", 1);
		ctrlSignals.put("ALUOp", 0);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 1);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 1);

		ctrlSignals.put("Type", (int) 'i');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> store()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 0);
		ctrlSignals.put("ALUSrc", 1);
		ctrlSignals.put("ALUOp", 0);

		ctrlSignals.put("MemWrite", 1);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 0);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'i');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> sll()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 1);
		ctrlSignals.put("ALUOp", 2);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> slr()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 1);
		ctrlSignals.put("ALUOp", 3);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> and()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 4);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> nor()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 5);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 1);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> beq()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 6);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 1);

		ctrlSignals.put("RegWrite", 0);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'i');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> bne()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 7);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 1);

		ctrlSignals.put("RegWrite", 0);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'i');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> jump()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 0);
		ctrlSignals.put("ALUSrc", 0);
		ctrlSignals.put("ALUOp", 0);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 0);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'j');

		return ctrlSignals;
	}

	private static Hashtable<String, Integer> set()
	{
		Hashtable<String, Integer> ctrlSignals = new Hashtable<String, Integer>();

		ctrlSignals.put("RegDst", 1);
		ctrlSignals.put("ALUSrc", 1);
		ctrlSignals.put("ALUOp", 8);

		ctrlSignals.put("MemWrite", 0);
		ctrlSignals.put("MemRead", 0);
		ctrlSignals.put("Branch", 0);

		ctrlSignals.put("RegWrite", 0);
		ctrlSignals.put("MemToReg", 0);

		ctrlSignals.put("Type", (int) 'r');

		return ctrlSignals;
	}

}
//The Key End