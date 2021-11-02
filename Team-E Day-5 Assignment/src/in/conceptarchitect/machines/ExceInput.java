package in.conceptarchitect.machines;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExceInput {
	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	public String readString(String prompt) {
		try {
			System.out.print(prompt);
			return reader.readLine();
		}
		catch(Exception ex) {
			return " ";
		}
	}
	
	public int readInt(String prompt) {
		try {
			var response=readString(prompt);
			return Integer.parseInt(response);
		}
		catch(Exception ex) {
			return 0;
		}
	}
}