package com.tce.menus;
/**
 * 
 * @author TerenceG
 *显示给用户的第一个菜单，包括储存，新开始，退出
 *
 */

import java.net.Socket;

import com.tce.game.GameModeType;
import com.tce.game.QueueProvider;

public class MainMenu extends Menus implements Runnable{
	public MainMenu(Socket server,GameModeType mode) {
		QueueProvider.startMessenger(mode,server);
	}
	public MainMenu() {
		start();
	}
	public void run() {
		start();
	}
	
	public void start() {
		
	}
}
