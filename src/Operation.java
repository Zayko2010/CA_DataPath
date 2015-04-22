import java.util.Hashtable;


public class Operation {
	
	int PC = 0;
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
	
	public Operation(int currentPC) {
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
		
		
		
		DcdExe.put("ctrlSignals", ctrlSignals);
		DcdExe.put("PC", FchDcd.get("PC"));
	}
	
	public void execute(String [] ins) {
		
	}
	
	public void memory() {
		
	}
	
	public void writeBack() {
		
	}

}
