package mujava.cli;

import java.io.IOException;

public class scriptGenMutesOracle {

	public static void main(String[] args) throws Exception {
		
		genmutesOracle genm = new genmutesOracle();
		String[] argv = {"-AOIS", "-debug", "PrintTokensTest" };
		
		genm.main(argv);

	}

}