import java.util.Hashtable;

public class ALU
{	
	Hashtable <String, Integer> regList;
	
	public static int call(int ctrl, int in1, int in2)
	{
		switch(ctrl)
		{
			case 0: return add(in1, in2);
			case 1: return sub(in1, in2);
			case 2: return sll(in1, in2);
			case 3: return slr(in1, in2);
			case 4: return and(in1, in2);
			case 5: return nor(in1, in2);
			case 6: return eql(in1, in2);
			case 7: return nql(in1, in2);
			default: System.out.println("Control Signal Error !");return 404;
		}
	}
	
	private static int add(int in1, int in2)
	{
		return (in1 + in2);
	}
	
	private static int sub(int in1, int in2)
	{
		return (in1 - in2);
	}
	
	private static int sll(int in1, int in2)
	{
		return (in1 << in2);
	}
	
	private static int slr(int in1, int in2)
	{
		return (in1 >> in2);
	}
	
	private static int and(int in1, int in2)
	{
		return (in1 & in2);
	}
	
	private static int nor(int in1, int in2)
	{
		return ~(in1 | in2);
	}
	
	private static int eql(int in1, int in2)
	{
		return (in1 == in2)? 1 : 0;
	}
	
	private static int nql(int in1, int in2)
	{
		return (in1 != in2)? 1 : 0;
	}
}