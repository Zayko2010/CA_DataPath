import java.util.Hashtable;

//Sabrout start
public class Registers
{
	private Hashtable<String, Integer> regList = new Hashtable<String, Integer>();
	
	public Registers ()
	{
		regList.put("$zero", 0);
		regList.put("$at", 0);
		regList.put("$v0", 0);
		regList.put("$v1", 0);
		regList.put("$a0", 0);
		regList.put("$a1", 0);
		regList.put("$a2", 0);
		regList.put("$a3", 0);
		regList.put("$t0", 0);
		regList.put("$t1", 0);
		regList.put("$t2", 0);
		regList.put("$t3", 0);
		regList.put("$t4", 0);
		regList.put("$t5", 0);
		regList.put("$t6", 0);
		regList.put("$t7", 0);
		regList.put("$s0", 0);
		regList.put("$s1", 0);
		regList.put("$s2", 0);
		regList.put("$s3", 0);
		regList.put("$s4", 0);
		regList.put("$s5", 0);
		regList.put("$s6", 0);
		regList.put("$s7", 0);
		regList.put("$t8", 0);
		regList.put("$t9", 0);
		regList.put("$k0", 0);
		regList.put("$k1", 0);
		regList.put("$gp", 0);
		regList.put("$sp", 0);
		regList.put("$fp", 0);
		regList.put("$ra", 0);
		regList.put("pc", 0);
		regList.put("hi", 0);
		regList.put("lo", 0);
	}
	
	public int getValueOf(String name)
	{
		return regList.get(name);
	}
	
	public void insertIntoRegister(String name, int value)
	{
		regList.put(name, value);
	}
}
//Sabrout end