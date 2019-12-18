package com.tce.menus;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tce.game.QueueProvider;

/*
 * All menus in JAdventure extend this class
 * Add MenuItems to menuItems, call displayMenu and you're happy
 */
public class Menus {
	protected List<MenuItem> menuItems = new ArrayList<>();// 菜单元素
	protected Map<String, MenuItem> commandMap = new HashMap<String, MenuItem>();// 指令集

	public MenuItem displayMenu(List<MenuItem> m) {
		int i = 1;
		for (MenuItem menuItem : m) {
			commandMap.put(String.valueOf(i), menuItem);
			commandMap.put(menuItem.getKey(), menuItem);
			for (String command : menuItem.getAltCommands()) {
				commandMap.put(command.toLowerCase(), menuItem);
			}
			i++;
		}
		MenuItem selecItem = selectMenu(m);
		return selecItem;
	}

	protected MenuItem selectMenu(List<MenuItem> m) {
		this.printMenuItems(m);
		String command = QueueProvider.take();
		if (commandMap.containsKey(command.toLowerCase())) {
			return commandMap.get(command.toLowerCase());
		} else {
			QueueProvider.offer("I don't know what '" + command + "' means.");
			return this.displayMenu(m);
		}

	}

	private void printMenuItems(List<MenuItem> m) {
		// TODO Auto-generated method stub
		
	}
}
