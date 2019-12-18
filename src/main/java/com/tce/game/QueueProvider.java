package com.tce.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueProvider {
	private static Logger logger = LoggerFactory.getLogger(QueueProvider.class);
	public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	public static BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
	public static DataOutputStream out;
	public static DataInputStream in;
	public static GameModeType mode;
	public static Socket server;

	public static void startMessenger(GameModeType modeInc, Socket sockerInc) {
		logger.debug("startMessenger( " + modeInc + " , " + sockerInc + " )");
		mode = modeInc;
		server = sockerInc;
	}

	public static void startMessenger(GameModeType modeInc) {
		logger.debug("startMessenger( " + modeInc + " )");
		mode = modeInc;
	}

	public static BlockingQueue<String> getQueue() {
		return queue;
	}

	public static void offer(String message) {
		logger.debug("offer( " + message + " )");

		if (GameModeType.SERVER == mode) {
			try {
				out = new DataOutputStream(server.getOutputStream());
				in = new DataInputStream(server.getInputStream());
			} catch (IOException ioe) {
				logger.debug("Insu=ide offer( " + message + " )", ioe);
			}
		}

		if (GameModeType.SERVER == mode) {
			sendToServer(message);
		} else {
			System.out.println(message);
		}
	}

	public static boolean sendToServer(String message) {
		logger.debug("sendToServer( " + message + " )");
		try {
			out.writeUTF(message + "END");
		} catch (SocketException se) {
			logger.debug("Inside  sendToServer( " + message + " )", se);
			return false;
		} catch (IOException ioe) {
			logger.debug("Inside  sendToServer( " + message + " )", ioe);
			return false;
		}
		return true;

	}

	public static String take() {
		// TODO Auto-generated method stub
		return null;
	}
}
