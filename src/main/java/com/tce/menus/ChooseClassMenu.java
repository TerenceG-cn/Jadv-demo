package com.tce.menus;

import com.tce.game.DeathException;
import com.tce.game.QueueProvider;
import com.tce.game.Game.Game;
import com.tce.game.entities.Player;

/**
 * start 创一个新玩家
 * 
 * @author TerenceG
 *
 */
public class ChooseClassMenu extends Menus {
	public ChooseClassMenu() throws DeathException {
		this.menuItems.add(new MenuItem("Recruit", "A soldier newly enlisted to guard the city of Silliya"));// 新兵
		this.menuItems.add(new MenuItem("SewerRat", "A member of the underground of Silliya"));// 下水道老鼠

		while (true) {
			QueueProvider.offer("Choose a class to get started with: ");
			MenuItem selecItem = displayMenu(this.menuItems);
			if (testOption(selecItem)) {
				break;
			}
		}
	}

	private boolean testOption(MenuItem m) throws DeathException {
		String key = m.getKey();
		if (key.equals("recruit")) {
			Player player = Player.getInstance("sewerrat");// to do player class
			new Game(player, "new");//to do class
			return true;
		} else {
			return false;
		}
	}
}
