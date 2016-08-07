import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

class Main {

	String[] datatypes = {"void", "int", "char", "float", "double"};
	String[] loops = {"for", "while", "if"};

	void run() {
		String inputData = getInput();


		for (String datatype : datatypes) {
			int noFunctions = 0;
			int noVariables = 0;
			int initializedVariables = 0;
			int index = inputData.indexOf(datatype);

			ArrayList<String> functions = new ArrayList<String>();
			while (index >= 0) {
				int i = index + datatype.length() + 1;
				int comma = 0;
				while (true) {
					if (inputData.charAt(i) == ';') {
						break;
					} else if (inputData.charAt(i) == ',') {
						comma++;
					} else if (inputData.charAt(i) == '(') {
						String funName = "";
						funName = inputData.substring(index + datatype.length() + 1, i); 
						functions.add(funName);

						while (inputData.charAt(i) != ')') {
							i++;
						}
						
						comma = -1;
						break;
					} else if (inputData.charAt(i) == '=') {
						initializedVariables++;
					}
					i++;
				}
				if (comma == -1) {
					noFunctions += 1;
					index = inputData.indexOf(datatype, i + 1);
				} else {
					noVariables += comma + 1;
					index = inputData.indexOf(datatype, index + 1);
				}
			}
			System.out.println(datatype);
			System.out.println("Functions " + noFunctions);
			for (String funName : functions) {
				System.out.println(funName);
			}
			System.out.println("Variables " + noVariables);
			System.out.println("Initialized " + initializedVariables + " Non-initialized " + (noVariables - initializedVariables));
			System.out.println("");
		}

		//loops
		for (String loop : loops) {
			int index = inputData.indexOf(loop);
			int count = 0;
			while (index >= 0) {
				count++;
				index = inputData.indexOf(loop, index + 1);
			}
			System.out.println(loop + " " + count);
		}
	}

	String getInput() {
		String inputData = "";
		try {
			FileReader in = new FileReader("input.c");
			BufferedReader br = new BufferedReader(in);
			String input;
			while ((input = br.readLine()) != null) {
				inputData += input + "\n";
			}
		} catch (Exception ex) { ; }
		return inputData;
	}

	public static void main(String args[]) {
		new Main().run();
	}
}
