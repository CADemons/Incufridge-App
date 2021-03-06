package common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
	public static DataOutputStream outToServer;
	public static BufferedReader inFromServer;
	
	public static double getTemp() throws IOException {
		outToServer.writeBytes("get_temp\n");
		return Double.parseDouble(inFromServer.readLine().trim());
	}
	
	public static void runCommand(String cmd) throws IOException {
		outToServer.writeBytes("run_command " + cmd + "\n");
		inFromServer.readLine();
	}
	
	public static void sendFile(String fileText) throws IOException {
		outToServer.writeBytes("send_file " + fileText.replaceAll("\n", "_newline_") + "\n");
	}
	
	public static void getLog() throws IOException {
		outToServer.writeBytes("get_log\n");
		String log = inFromServer.readLine().trim().replaceAll("_newline_", "\n");
		TextFileWriter.deleteFile("log.txt");
		TextFileWriter.writeToFile("log.txt", log);
	}
	
	public static void connect() throws IOException {
		Socket clientSocket = new Socket("108.168.213.183", 26517);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String password = TextFileReader.readEntireFile("auth").trim();
		outToServer.writeBytes(password + "\n");
		outToServer.writeBytes("client\n");
		inFromServer.readLine();
//		while (true) {
//			String msg = inFromServer.readLine().trim();
//			System.out.println(msg);
//			if (msg.equals("get_temp")) {
//				double temp = Communicator.getTemperature();
//				System.out.println(temp);
//				outToServer.writeBytes(temp + "\n");
//			} else if (msg.startsWith("run_command ")) {
//				String command = msg.substring(11);
//				Communicator.sendCommand(command);
//				outToServer.writeBytes("done\n");
//			} else if (msg.startsWith("send_file ")) {
//				String txt = msg.substring(10).replaceAll("_newline_", "\n");
//				TextFileWriter.deleteFile("Programs/main.incu");
//				TextFileWriter.writeToFile("Programs/main.incu", txt);
//				System.out.println("Wrote file to disk");
//				outToServer.writeBytes("0\n");
//				FileRunner.uploadAndRun("Programs/main.incu");
//			} else if (msg.startsWith("get_file ")) {
//				String filename = msg.substring(9);
//				String txt = TextFileReader.readEntireFile("Programs/"+filename);
//				System.out.println("Read file.");
//				sendFile(outToServer, txt);
////			}
//		}
	}
}
