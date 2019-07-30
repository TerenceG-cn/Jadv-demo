package com.tce.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	DataInputStream in;
	DataOutputStream out;

	public Client(String serverNameString, int port) {
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(serverNameString, port);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			while (true) {
				String serverMessage = in.readUTF();
				if (serverMessage.endsWith("END")) {
					serverMessage = serverMessage.substring(0, serverMessage.length() - 3);
					if (serverMessage.equals("QUERY")) {// 询问？
						getInput();
					} else if (serverMessage.equals("EXIT")) {// 退出
						break;
					} else {
						System.out.println(serverMessage);
					}
				} else {
					String message = "";
					while (!serverMessage.endsWith("END")) {
						message += serverMessage;
						serverMessage = in.readUTF();
					}
					message = serverMessage.substring(0, serverMessage.length() - 3);
					if (message.equals("QUERY")) {
						getInput();
					} else if (serverMessage.equals("EXIT")) {
						break;
					} else {
						System.out.println(serverMessage);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	private void getInput() {
		Scanner scanner;
		try {
			scanner = new Scanner(System.in);
			String userInput = scanner.nextLine();
			out.writeUTF(userInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
