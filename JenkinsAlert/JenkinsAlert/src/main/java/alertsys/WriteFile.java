package alertsys;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/*
	WriteFile class built from http://www.homeandlearn.co.uk/java/write_to_textfile.html;
	For documentation follow the link.
*/
public class WriteFile{

	private String PATH;
	private boolean append_to_file;

	public WriteFile(String file_path){
		PATH = file_path;
	}

	public WriteFile(String file_path, boolean append_value){
		PATH = file_path;
		append_to_file = append_value;
	}

	public void writeToFile(String textLine) throws IOException{
		FileWriter write = new FileWriter(PATH, append_to_file);
		PrintWriter print_line = new PrintWriter(write);

		//%s: string of characters of any length, %n: new line
		print_line.printf("%s" + "%n", textLine);
		print_line.close();
	}

}