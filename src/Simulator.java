/*
 * AUTHOR: Tarek
 */

public class Simulator {
	int noOfIns = 0;
	int initialPC = 0;
	String[][] cycles;
	Operation opr;
	boolean initiated = false;

	public void init() {
		opr = new Operation(initialPC);
		noOfIns = opr.im.count - Operation.PC;
		cycles = generateCycles(noOfIns);
		initiated = true;
	}

	public void run() {
		if (initiated)
			runCycles(cycles);
	}

	public String[][] generateCycles(int ins) {

		String[][] c = new String[ins][ins + 4];
		for (int i = 0; i < ins; i++) {
			c[i][i] = "IF";
			c[i][i + 1] = "ID";
			c[i][i + 2] = "EXE";
			c[i][i + 3] = "MEM";
			c[i][i + 4] = "WB";
		}
		return c;

	}

	public void runCycles(String[][] cycles) {

		for (int j = 0; j < cycles.length + 4; j++) {
			for (int i = 0; i < cycles.length; i++) {
				if (cycles[i][j].equals("IF")) opr.fetch();
				if (cycles[i][j].equals("ID")) opr.decode();
				if (cycles[i][j].equals("EXE")) opr.execute();
				if (cycles[i][j].equals("MEM")) opr.memory();
				if (cycles[i][j].equals("WB")) opr.writeBack();
				if (cycles[i][j] == null) break;
			}
		}

	}

	public static void main(String[] args) {
		
		// Simulator s = new Simulator();
		// String[][] test = s.generateCycles(3);
		// for (int i = 0; i < test.length; i++) {
		// for (int j=0; j < test[i].length; j++) {
		// System.out.print(test[i][j] + " ");
		// }
		// System.out.println();
		// }
		
	}
}