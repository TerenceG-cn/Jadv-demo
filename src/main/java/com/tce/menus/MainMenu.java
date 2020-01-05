package com.tce.menus;
/**
 * 
 * @author TerenceG
 *显示给用户的第一个菜单，包括储存，新开始，退出
 *
 */

import java.net.Socket;

import com.tce.game.DeathException;
import com.tce.game.GameModeType;
import com.tce.game.QueueProvider;
/**
 * 用户屏幕上显示的第一个菜单
 * @author TerenceG
 * 此菜单允许玩家选择是否加载退出游戏，
 * 启动一个新的，或退出终端
 */
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
        menuItems.add(new MenuItem("Start", "Starts a new Game", "new"));//开始一个新游戏
        menuItems.add(new MenuItem("Load", "Loads an existing Game"));//加载存档
        menuItems.add(new MenuItem("Delete", "Deletes an existing Game"));//删除存档
        menuItems.add(new MenuItem("Exit", null, "quit"));//删除
        
        boolean continuing=true;
        do {
        	MenuItem selectedItem=displayMenu(menuItems);//？
        	try {
        		continuing=testOption(selectedItem);
        	}catch (DeathException e) {
        		if (e.getLocalisedMessage().equals("close")) {
                    continuing = false;
                }
			}
        }while(continuing);
        QueueProvider.offer("EXIT");
	}
	private boolean testOption(MenuItem m) throws DeathException{//throws DeathException??43行
		 String key = m.getKey();
	        switch (key){
	            case "start":
	                new ChooseClassMenu();
	                break;
	            case "load":
	                loadProfileFromMenu();
	                break;
	            case "delete":
	                deleteProfileFromMenu();
	                break;
	            case "exit":
	                QueueProvider.offer("Goodbye!");
	                return false;
	        }
	        return true;
	}
	private void deleteProfileFromMenu() {
		// TODO Auto-generated method stub
		
	}
	private void loadProfileFromMenu() {
		// TODO Auto-generated method stub
		
	}
}
