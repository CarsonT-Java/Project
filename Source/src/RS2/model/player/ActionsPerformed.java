package RS2.model.player;

import RS2.skills.Crafting.Tanning;
import RS2.util.Misc;

public class ActionsPerformed {

	private Client c;

	public ActionsPerformed(Client Client) {
		this.c = Client;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.actionTimer = 4;
		if (c.actionTimer > 0) {
			return;
		}
		c.actionTimer = 4;
		switch (objectType) {
		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch (objectType) {
		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch (objectType) {
		}
	}

	public void firstClickNpc(int npcType) {
		c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
			if (!c.getItems().playerHasItem(c.fishitem)) {
				c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for "
						+ c.getItems().getItemName(c.fishies));
				c.fishing = false;
				return;
			}
			if (c.getItems().freeSlots() == 0) {
				c.sendMessage("Your inventory is full.");
				c.fishing = false;
				return;
			}
			if (c.playerFishing < c.fishreqt) {
				c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
				c.fishing = false;
				return;
			}
			c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
		}
		switch (npcType) {
		case 811:
			c.getShops().openShop(1);
			break;
		case 817:
			c.getShops().openShop(2);
			break;
		case 313:
			c.fishing = true;
			c.fishXP = 600;
			c.fishies = 341;
			c.fishreqt = 23;
			c.fishitem = 303;
			c.fishemote = 621;
			c.fishies2 = 363;
			c.fishreq2 = 46;
			break;
		case 316:
			c.fishing = true;
			c.fishXP = 350;
			c.fishies = 317;
			c.fishreqt = 0;
			c.fishitem = 303;
			c.fishemote = 621;
			// c.fishies2 = 0;
			// c.fishreq2 = 0;
			break;
		case 320:
			c.fishing = true;
			c.fishXP = 2000;
			c.fishies = 15270;
			c.fishreqt = 95;
			c.fishitem = 303;
			c.fishemote = 621;
			break;
		case 324:// cage-harpoon spot choice cage
			c.fishing = true;
			c.fishXP = 700;
			c.fishies = 377;
			c.fishreqt = 40;
			c.fishitem = 301;
			c.fishemote = 619;
			c.fishies2 = 389;
			c.fishreq2 = 81;
			break;

		case 325:
			c.fishing = true;
			c.fishXP = 2000;
			c.fishies = 15270;
			c.fishreqt = 95;
			c.fishitem = 303;
			c.fishemote = 621;
			break;
		case 326:
			c.fishing = true;
			c.fishXP = 600;
			c.fishies = 341;
			c.fishreqt = 23;
			c.fishitem = 303;
			c.fishemote = 621;
			// c.fishies2 = 363;
			// c.fishreq2 = 46;
			c.fishies2 = 7944;
			c.fishreq2 = 62;
			break;
		case 334:
			c.fishing = true;
			c.fishXP = 350; // Anchovie/Shrimp
			c.fishies = 317;
			c.fishreqt = 0;
			c.fishitem = 303;
			c.fishemote = 621;
			c.fishies2 = 321;
			c.fishreq2 = 5;
			break;
		case 804:
			Tanning.sendTanningInterface(c);
			break;
		case 1599:
				c.getDH().sendDialogues(1, npcType);
			break;
		}
	}

	public void secondClickNpc(int npcType) {
		c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
			if (!c.getItems().playerHasItem(c.fishitem)) {
				c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for "
						+ c.getItems().getItemName(c.fishies));
				c.fishing = false;
				return;
			}
			if (c.getItems().freeSlots() == 0) {
				c.sendMessage("Your inventory is full.");
				c.fishing = false;
				return;
			}
			if (c.playerFishing < c.fishreqt) {
				c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
				c.fishing = false;
				return;
			}
			if (Misc.random(1) == 1)
				c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
		}
		switch (npcType) {
		case 312:
			c.fishing = true;
			c.fishXP = 650;
			c.fishies = 359;
			c.fishreqt = 35;
			c.fishitem = 311;
			c.fishemote = 618;
			c.fishies2 = 371;
			c.fishreq2 = 50;
			break;
		case 313:
			c.fishing = true;
			c.fishXP = 1000;
			c.fishies = 383;
			c.fishreqt = 79;
			c.fishitem = 311;
			c.fishemote = 618;
			c.fishies2 = 0;
			c.fishreq2 = 0;
			break;
		case 316:
			c.fishing = true;
			c.fishXP = 630;
			c.fishies = 327;
			c.fishreqt = 5;
			c.fishitem = 307;
			c.fishemote = 622;
			c.fishies2 = 345;
			c.fishreq2 = 10;
			break;
		case 324:
			c.fishing = true;
			c.fishXP = 650;
			c.fishies = 359;
			c.fishreqt = 35;
			c.fishitem = 311;
			c.fishemote = 618;
			c.fishies2 = 371;
			c.fishreq2 = 50;
			break;
		case 326:
			c.fishing = true;
			c.fishXP = 530;
			c.fishies = 327;
			c.fishreqt = 5;
			c.fishitem = 307;
			c.fishemote = 622;
			c.fishies2 = 345;
			c.fishreq2 = 10;
			break;
		case 331:
			c.fishing = true;
			c.fishXP = 770;
			c.fishies = 349;
			c.fishreqt = 25;
			c.fishitem = 307;
			c.fishemote = 622;
			c.fishies2 = 0;
			c.fishreq2 = 0;
			break;
		case 333:
			c.fishing = true;
			c.fishXP = 650;
			c.fishies = 359;
			c.fishreqt = 35;
			c.fishitem = 311;
			c.fishemote = 618;
			c.fishies2 = 371;
			c.fishreq2 = 50;
			break;
		case 334:
			c.fishing = true;
			c.fishXP = 650;
			c.fishies = 359;
			c.fishreqt = 35;
			c.fishitem = 311;
			c.fishemote = 618;
			c.fishies2 = 371;
			c.fishreq2 = 50;
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (npcType) {
		}
	}
}