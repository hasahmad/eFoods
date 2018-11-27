package Middleware;


import java.util.Scanner;

import model.Engine;

public class Main {
	
	public static void main(String[] args) throws Exception {
		String posDir = "";

//		String classPath = System.getProperty("java.class.path");
//		classPath = classPath.substring(0, classPath.lastIndexOf(':'));
//		classPath = classPath.substring(0, classPath.lastIndexOf('/'));

		String homePath = System.getProperty("user.home");
		System.out.println(System.getProperty("user.home"));
		if (args.length > 0) {
			posDir = args[0];
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Purchase orders directory: ");
			posDir = sc.next();
			sc.close();
		}
		
		if (posDir.toLowerCase().equals("exit")) {
			return;
		}

		String processedFile = homePath + "/processed.txt";
		String reportsDir = homePath + "/POReports";
//		Engine e = Engine.getInstance(posDir, processedFile);
		String outTo = "json";
		Engine e = Engine.getInstance(posDir, processedFile, reportsDir, outTo);
		System.out.println(e.createJsonReport());
	}

}
