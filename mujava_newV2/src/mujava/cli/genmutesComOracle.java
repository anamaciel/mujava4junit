package mujava.cli;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class genmutesComOracle {
	
	  @Parameter
	  private List<String> parameters = new ArrayList<String>();
	 	  
	  
	  @Parameter(names = "-ASM", description = "Generate mutants of ASM")
	  private boolean ASM;
	 
	  @Parameter(names = "-all",  description = "Generate mutants of ALL MUTATION OPERATORS (SIGNATURE AND ANNOTATION LEVEL)")
	  private boolean all;
	  
	  @Parameter(names = "-D",  description = "Generate mutants of ALL classes in the directory")
	  private boolean D;
	  
	  
	  @Parameter(names = "--help", help = true)
	  private boolean help;
	  
	  
	  @Parameter(names = "-debug", description = "Debug mode")
	  private boolean debug = false;


	public List<String> getParameters() {
		return parameters;
	}


	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}


	public boolean getASM() {
		return ASM;
	}


	public void setASM(boolean aSM) {
		ASM = aSM;
	}


	public boolean getAll() {
		return all;
	}


	public void setAll(boolean all) {
		this.all = all;
	}


	public boolean getD() {
		return D;
	}


	public void setD(boolean d) {
		D = d;
	}


	public boolean isDebug() {
		return debug;
	}


	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	  
	  

}
