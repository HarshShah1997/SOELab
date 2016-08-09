import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

class Main {

	String[] datatypes = {"void ", "int ", "char ", "float ", "double "};
	String[] loops = {"for", "while", "if"};

	void run() {
		String inputData = getInput();
		try {
			PrintWriter out = new PrintWriter("iit2014071.txt");

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
						} else if (inputData.charAt(i) == ')') {
							comma = -2;
							break;
						} else if (inputData.charAt(i) == ',') {
							comma++;
						} else if (inputData.charAt(i) == '(') {
							String funName = "";
							

							while (inputData.charAt(i) != ')') {
								i++;
							}
							funName = inputData.substring(index + datatype.length(), i+1); 
							functions.add(funName);

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
					} else if (comma >= 0) {
						noVariables += comma + 1;
						index = inputData.indexOf(datatype, index + 1);
					} else {
						index = inputData.indexOf(datatype, index + 1);
					}

				}
				out.println(datatype);
				out.println("Functions " + noFunctions);
				for (String funName : functions) {
					out.println(funName);
				}
				out.println("Variables " + noVariables);
				out.println("Initialized " + initializedVariables + " Non-initialized " + (noVariables - initializedVariables));
				out.println("");
			}

			//loops
			for (String loop : loops) {
				int index = inputData.indexOf(loop);
				int count = 0;
				while (index >= 0) {
					int i = index + loop.length() + 1;
					int flag = 0;
					while (inputData.charAt(i) != '(') {
						if (inputData.charAt(i) != ' ' && inputData.charAt(i) != '\n' && inputData.charAt(i) != '\t') {
							flag = 1;
							break;
						}
						i++;
					}
					if (flag == 0) {
						count++;
					}
					index = inputData.indexOf(loop, index + 1);
				}
				out.println(loop + " " + count);
			}
			out.close();
		} catch (IOException iex) {
			iex.printStackTrace();
		}
		//System.out.println(inputData.indexOf("if"));
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
