package com.tce.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tce.menus.MainMenu;

/*
 * 这是游戏的起点。
 * 一个新的MainMenu,将处理游戏的其余部分。
 */

public class Jadv {
	private static Logger logger = LoggerFactory.getLogger(Jadv.class);

	public static void main(String[] args) {
		logger.info("Starting JAdventure" + toString(args));
		/*确定运行方式*/
		GameModeType mode = getGameMode(args);
		logger.debug("Starting in mode " + mode.name());
		String serverName = "localhost";
		/*端口号设置*/
		int port = 4044;
		if (mode == GameModeType.SERVER) {
			port = Integer.parseInt(args[1]);
		} else if (mode == GameModeType.CLIENT) {
			serverName = args[2];
			port = Integer.parseInt(args[1]);
		}
		
		if (GameModeType.CLIENT == mode) {
			new Client(serverName, port);	//开启一个客户端
		} else if (GameModeType.SERVER == mode) {//服务器端
			while (true) {
				ServerSocket listener = null;
				try {
					listener = new ServerSocket(port);
					while (true) {
						Socket server = listener.accept();
						Runnable r = new MainMenu(server, mode);
						new Thread(r).start();
					}
				} catch (IOException c) {
					c.printStackTrace();
				} finally {
					try {
						listener.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			//独立架构
			System.out.println("咋回事");
			QueueProvider.startMessenger(GameModeType.STAND_ALONE);
			new MainMenu();
		}
	}

	/**
	 * 获取游戏模式
	 * @param args
	 * @return
	 */
	private static GameModeType getGameMode(String[] args) {
		if (args == null || args.length == 0 || "".equals(args[0].trim())) {
			return GameModeType.STAND_ALONE;
		}

		try {
			return GameModeType.valueOf(args[0].toUpperCase());
		} catch (IllegalArgumentException iae) {
			logger.warn("No game mode '" + args[0].toUpperCase() + "' known." + "Terminating application.");
			System.exit(-1);
		}
		return GameModeType.STAND_ALONE;
	}

	private static String toString(String[] args) {
		if (args.length == 0) {
			return "";
		}

		final StringBuilder bldr = new StringBuilder();
		bldr.append("[ ");
		for (int index = 0; index < args.length; index++) {
			if (index > 0) {
				bldr.append(", ");
			}
			bldr.append(args[index]);
		}
		bldr.append(" ]");
		return bldr.toString();
	}
}
