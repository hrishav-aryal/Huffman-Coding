package Project_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO {

	BufferedWriter out;
	BufferedReader read;
	String fileName;
	  
	public IO() {
		
	}
	
	public IO(String name) {
		try {
			out = new BufferedWriter(new FileWriter(name));
			fileName = name;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//kind of a constructor for reading object
	public String readFile(String name) {
		String str = "";
		try {
			read = new BufferedReader(new FileReader(name));
			String line = read.readLine();
			while(line != null) {
				str += line;
				line = read.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to open file " + fileName);
		} catch (IOException e) {
		        System.out.println("Failed to open file " + fileName);
		}    
		
		return str;
	}
	
	public void openReadFile(String name) {
		try {
			read = new BufferedReader(new FileReader(name));			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to open file " + fileName);
		}  
	}
	
	
	public String readLine() {
		String line = null;
		try {
			line = read.readLine();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to open file " + fileName);
		} catch (IOException e) {
		        System.out.println("Failed to open file " + fileName);
		}    
		
		return line;
	}
	
	
	public void writeFile(String data) {
		try {
			out.write(data);
			out.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
	    try {
	      out.flush(); //In case something got stuck in the buffer
	      out.close(); //Properly close the file and release control
	    } catch (IOException np) {
	      System.out.println("Must first initiate the file writer");
	    }
	  }
	
	public void readClose() {
		try {
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
