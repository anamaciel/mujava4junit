package mujava.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mujava.MutationSystem;
import mujava.MutationSystemOracle;
import mujava.OpenJavaException;
import mujava.TraditionalMutantsGeneratorCLI;
import mujava.TraditionalMutantsGeneratorOracleCLI;

import com.beust.jcommander.JCommander;

public class genmutesOracle {

	static String muJavaHomePath = new String();	

	public static void main(String[] args) throws Exception {

		// System.out.println("test");
		genmutesCom jct = new genmutesCom();
		String[] argv = { "-all", "-debug", "Flower" }; // development use, when release,
		// comment out this line
		JCommander jCommander = new JCommander(jct, args);

		// check session name
		if (jct.getParameters().size() > 1) {
			Util.Error("Has more parameters than needed.");
			return;
		}

		// set session name
		String sessionName = jct.getParameters().get(0);

		muJavaHomePath = Util.loadConfig();
		//System.out.println(muJavaHomePath);
		// check if debug mode
		if (jct.isDebug()) {
			Util.debug = true;
		}

		// get all existing session name
		File folder = new File(muJavaHomePath);
		// check if the config file has defined the correct folder
		if (!folder.isDirectory()) {
			Util.Error("ERROR: cannot locate the folder specified in mujava.config");
			return;
		}
		File[] listOfFiles = folder.listFiles();
		// null checking
		// check the specified folder has files or not
		if (listOfFiles==null)
		{
			Util.Error("ERROR: no files in the muJava home folder: "+muJavaHomePath);
			return;
		}
		List<String> fileNameList = new ArrayList<>();
		for (File file : listOfFiles) {
			fileNameList.add(file.getName());
		}

		// check if session is already created.
		if (!fileNameList.contains(sessionName)) {
			Util.Error("Session does not exist.");
			return;

		}

		// get all files in the session
		String[] file_list = new String[1];
		// if(jct.getD())
		// {
		File sessionFolder = new File(muJavaHomePath + "/" + sessionName + "/oracles/src");
		//System.out.println("caminho: " + sessionFolder.getAbsolutePath());
		File[] listOfFilesInSession = sessionFolder.listFiles();
		//System.out.println("qtde de arquivos: " + listOfFilesInSession.length);
		file_list = new String[listOfFilesInSession.length];		
		for (int i = 0; i < listOfFilesInSession.length; i++) {
			file_list[i] = listOfFilesInSession[i].getName();
			//System.out.println("nome arq teste: " + listOfFilesInSession[i].getName());
		}


		// get all mutation operators selected
		HashMap<String, List<String>> ops = new HashMap<String, List<String>>(); // used
		// for
		// add
		// random
		// percentage
		// and
		// maximum


		String[] paras = new String[] { "1", "0" };
		if (jct.getAll()) // all is selected, add all operators
		{

			// if all is selected, all mutation operators are added
			ops.put("AOIS", new ArrayList<String>(Arrays.asList(paras)));
			// ops.put("SDL", jct.getAll());

		} else { // if not all, add selected ops to the list
			if (jct.getAOIS()) {
				ops.put("AOIS", new ArrayList<String>(Arrays.asList(paras)));
			}			
		}

		// add default option "all"
		if (ops.size() == 0) {
			ops.put("AOIS", new ArrayList<String>(Arrays.asList(paras)));
		}

		// String[] tradional_ops = ops.toArray(new String[0]);
		// set system
		setJMutationStructureAndSession(sessionName);
		// MutationSystem.setJMutationStructureAndSession(sessionName);
		MutationSystemOracle.recordInheritanceRelation();
		// generate mutants
		//System.out.println("arquivos: " + file_list[0]);
		generateMutants(file_list, ops);

		//System.exit(0);

	}

	private static void setJMutationStructureAndSession(String sessionName) {

		// MutationSystemOracleOracle.SYSTEM_HOME

		muJavaHomePath = muJavaHomePath + "/" + sessionName ;
		MutationSystemOracle.SYSTEM_HOME = muJavaHomePath;
		MutationSystemOracle.SRC_PATH = muJavaHomePath + "/oracles/src";
		MutationSystemOracle.CLASS_PATH = muJavaHomePath + "/oracles/classes";
		MutationSystemOracle.MUTANT_HOME = muJavaHomePath + "/oracles/result";
		MutationSystemOracle.TESTSET_PATH = muJavaHomePath + "/oracles/testset";

	}

	public static void generateMutants(String[] file_list, HashMap<String, List<String>> traditional_ops) {

		for (int i = 0; i < file_list.length; i++) {
			// file_name = ABSTRACT_PATH - MutationSystem.SRC_PATH
			// For example: org/apache/bcel/Class.java
			String file_name = file_list[i];
			System.out.println("File name: " + file_name);
			try {
				System.out.println((i + 1) + " : " + file_name);
				// [1] Examine if the target class is interface or abstract
				// class
				// In that case, we can't apply mutation testing.

				// Generate class name from file_name
				String temp = file_name.substring(0, file_name.length() - ".java".length());
				System.out.println(temp);
				String class_name = "";

				for (int j = 0; j < temp.length(); j++) {
					if ((temp.charAt(j) == '\\') || (temp.charAt(j) == '/')) {						
						class_name = class_name + ".";
						//System.out.println("class_name 1 " + class_name);
					} else {
						class_name = class_name + temp.charAt(j);
						//System.out.println("class_name 2 " + class_name);
					}
				}


				int class_type = MutationSystemOracle.getClassType(class_name);
				//System.out.println("class_type: " + class_type);
				
				if (class_type == MutationSystemOracle.NORMAL) { // do nothing
				} else if (class_type == MutationSystemOracle.MAIN) {
					System.out.println(" -- " + file_name + " class contains 'static void main()' method.");
					System.out
							.println("    Pleas note that mutants are not generated for the 'static void main()' method");
				} else {
					switch (class_type) {
					case MutationSystemOracle.INTERFACE:
						System.out.println(" -- Can't apply because " + file_name + " is 'interface' ");
						break;
					case MutationSystemOracle.ABSTRACT:
						System.out.println(" -- Can't apply because " + file_name + " is 'abstract' class ");
						break;
					case MutationSystemOracle.APPLET:
						System.out.println(" -- Can't apply because " + file_name + " is 'applet' class ");
						break;
					case MutationSystemOracle.GUI:
						System.out.println(" -- Can't apply because " + file_name + " is 'GUI' class ");
						break;
					case MutationSystemOracle.JUNIT:
						System.out.println(" -- Can't apply because " + file_name + " is 'JUNIT' class ");
						break;
					case -1:
						System.out.println(" -- Can't apply because class not found ");
						break;
					}

					deleteDirectory();
					continue;
				}

				// [2] Apply mutation testing
				setMutationSystemOraclePathFor(file_name);

				File original_file = new File(MutationSystemOracle.SRC_PATH, file_name);

				String[] opArray = traditional_ops.keySet().toArray(new String[0]);

				TraditionalMutantsGeneratorOracleCLI tmGenEngine;
				tmGenEngine = new TraditionalMutantsGeneratorOracleCLI(original_file, opArray);
				tmGenEngine.makeMutants();
				tmGenEngine.compileMutants();
				
			      // Lin add printing total mutants
				// get all file names
				File folder = new File(MutationSystemOracle.MUTANT_HOME + "/" + class_name + "/" + MutationSystemOracle.TM_DIR_NAME);
				File[] listOfMethods = folder.listFiles();
				//System.out.println("metodos: " + listOfMethods.length);
				//ArrayList<String> fileNameList = new ArrayList<>();
				int total_mutants = 0;
				for (File method : listOfMethods) {
					//fileNameList.add(method.getName());
					//System.out.println("metodo: " + method.getName());
					if(method.isDirectory())
					{
						System.out.println("is directory");
						File[] listOfMutants = method.listFiles();
						total_mutants = total_mutants+listOfMutants.length;
						
					}
				}
								
				
//				File muTotalFile = new File(MutationSystem.MUTANT_PATH,"mutation_log");
//				String strLine;
//		         LineNumberReader lReader = new LineNumberReader(new FileReader(muTotalFile));
//		         int line = 0;
//		         while ((strLine=lReader.readLine()) != null)
//		         {
//		        	 line++;
//		         }
				
			      System.out
					.println("------------------------------------------------------------------");
			      System.out.println("Total mutants generated for " + file_name +": " + Integer.toString(total_mutants));

			      
			      
			} catch (OpenJavaException oje) {
				System.out.println("[OJException] " + file_name + " " + oje.toString());
				 System.out.println("Can't generate mutants for " +file_name +
				 " because OpenJava " + oje.getMessage());
				 deleteDirectory();
			} catch (Exception exp) {
				System.out.println("[Exception] " + file_name + " " + exp.toString());
				exp.printStackTrace();
				// System.out.println("Can't generate mutants for " +file_name +
				// " due to exception" + exp.getClass().getName());
				// exp.printStackTrace();
				deleteDirectory();
			} catch (Error er) {
				System.out.println("[Error] " + file_name + " " + er.toString());
				// System.out.println("Can't generate mutants for " +file_name +
				// " due to error" + er.getClass().getName());
				deleteDirectory();
			}
		}
		// runB.setEnabled(true);
		// parent_frame.cvPanel.refreshEnv();
		// parent_frame.tvPanel.refreshEnv();
		// System.out
		// .println("------------------------------------------------------------------");
		// System.out.println(" All files are handled"); // need to say how many
		// mutants are generated

	}



	static void deleteDirectory() {
		File originalDir = new File(MutationSystemOracle.MUTANT_HOME + "/" + MutationSystemOracle.DIR_NAME + "/"
				+ MutationSystemOracle.ORIGINAL_DIR_NAME);
		while (originalDir.delete()) { // do nothing?
		}

		File cmDir = new File(MutationSystemOracle.MUTANT_HOME + "/" + MutationSystemOracle.DIR_NAME + "/"
				+ MutationSystemOracle.CM_DIR_NAME);
		while (cmDir.delete()) { // do nothing?
		}

		File tmDir = new File(MutationSystemOracle.MUTANT_HOME + "/" + MutationSystemOracle.DIR_NAME + "/"
				+ MutationSystemOracle.TM_DIR_NAME);
		while (tmDir.delete()) { // do nothing?
		}

		File myHomeDir = new File(MutationSystemOracle.MUTANT_HOME + "/" + MutationSystemOracle.DIR_NAME);
		while (myHomeDir.delete()) { // do nothing?
		}
	}


	static void setMutationSystemOraclePathFor(String file_name) {
		try {
			String temp;
			temp = file_name.substring(0, file_name.length() - ".java".length());
			temp = temp.replace('/', '.');
			temp = temp.replace('\\', '.');
			int separator_index = temp.lastIndexOf(".");

			if (separator_index >= 0) {
				MutationSystemOracle.CLASS_NAME = temp.substring(separator_index + 1, temp.length());
			} else {
				MutationSystemOracle.CLASS_NAME = temp;
			}

			String mutant_dir_path = MutationSystemOracle.MUTANT_HOME + "/" + temp;
			//System.out.println("mutant_dir_path: " + mutant_dir_path);
			File mutant_path = new File(mutant_dir_path);
			mutant_path.mkdir();

			String class_mutant_dir_path = mutant_dir_path + "/" + MutationSystemOracle.CM_DIR_NAME;
			//System.out.println("class_dir_path: " + class_mutant_dir_path);
			File class_mutant_path = new File(class_mutant_dir_path);
			class_mutant_path.mkdir();

			String traditional_mutant_dir_path = mutant_dir_path + "/" + MutationSystemOracle.TM_DIR_NAME;
			File traditional_mutant_path = new File(traditional_mutant_dir_path);
			traditional_mutant_path.mkdir();

			String original_dir_path = mutant_dir_path + "/" + MutationSystemOracle.ORIGINAL_DIR_NAME;
			File original_path = new File(original_dir_path);
			original_path.mkdir();

			MutationSystemOracle.CLASS_MUTANT_PATH = class_mutant_dir_path;
			MutationSystemOracle.TRADITIONAL_MUTANT_PATH = traditional_mutant_dir_path;
			MutationSystemOracle.ORIGINAL_PATH = original_dir_path;
			MutationSystemOracle.DIR_NAME = temp;
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
