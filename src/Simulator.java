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
		opr = new Operation();
		noOfIns = opr.im.count;
		cycles = generateCycles(noOfIns);
		initiated = true;
	}

	public void run() {
		if (initiated)
			runCycles(cycles);
	}

	public String[][] generateCycles(int ins) {

		String[][] c = new String[ins][(ins + 4) * 2];
		int j = 0;
		for (int i = 0; i < ins; i++) {
			// branch hazard
			if (InstructionMemory.getBranches().contains(j)) {
				j = j + 3;
			}

			c[i][j] = "IF";
			c[i][j + 1] = "ID";
			c[i][j + 2] = "EXE";
			c[i][j + 3] = "MEM";
			c[i][j + 4] = "WB";
			j++;
		}
		return c;

	}

	public void runCycles(String[][] cycles) {

		for (int j = 0; j < cycles.length + 4; j++) {
			for (int i = 0; i < cycles.length; i++) {
				
				if (cycles[i][j] != null) {
					if (cycles[i][j].equals("IF")) {
						System.out.print("Fetching... ");
						opr.fetch();
					} else if (cycles[i][j].equals("ID")) {
						System.out.println("Decoding...");
						opr.decode();
					} else if (cycles[i][j].equals("EXE")) {
						System.out.println("Executing...");
						opr.execute();
					} else if (cycles[i][j].equals("MEM")) {
						System.out.println("Using Memory...");
						opr.memory();
					} else if (cycles[i][j].equals("WB")) {
						System.out.println("Writing Back...");
						opr.writeBack();
					}
				
				}
			}
		}

	}

	public static void main(String[] args) {

		Simulator s = new Simulator();
		s.init();
		s.run();

		// String[][] test = s.generateCycles(3);
		// for (int i = 0; i < test.length; i++) {
		// for (int j=0; j < test[i].length; j++) {
		// System.out.print(test[i][j] + " ");
		// }
		// System.out.println();
		// }

	}
}