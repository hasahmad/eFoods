package Middleware;

import java.util.Scanner;

import model.Engine;

public class Main {
	
	public static void main(String[] args) throws Exception {
		String posDir = "";
//		String 
		String classPath = System.getProperty("java.class.path");
		classPath = classPath.substring(0, classPath.lastIndexOf(':'));
		classPath = classPath.substring(0, classPath.lastIndexOf('/'));
		
		
		if (args.length > 0) {
			posDir = args[0];
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Purchase orders directory: ");
			posDir = sc.next();
			sc.close();
		}

		String processedFile = classPath + "/processed.txt";
		Engine e = Engine.getInstance(posDir, processedFile);
		System.out.println(e.createJsonReport());
	}

}
