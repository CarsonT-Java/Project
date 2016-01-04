package RS2.model.item;

import RS2.model.player.Client;
import RS2.skills.Cooking;
import RS2.skills.Crafting.GemCutting;
import RS2.skills.Crafting.JewelryMaking;
import RS2.skills.Crafting.LeatherMaking;
import RS2.skills.Herblore.Grinding;
import RS2.skills.Herblore.PotionMaking;

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX, int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch (objectID) {
		case 8151:
		case 8389:
		case 8132:
		case 8174:
		case 7848: ///flower patch catherby
			c.getFarming().checkItemOnObject(itemId);
		break;
		case 12269:
		case 5249:
		case 114:
			Cooking.startCooking(c, itemId, objectID);
			break;
		case 3044:
			if (itemId == 2357) {
				JewelryMaking.jewelryInterface(c);
			}
		case 409:
			if (c.getPrayer().IsABone(itemId))
				c.getPrayer().bonesOnAltar(itemId);
			break;
		}
	}

	public static void ItemonItem(final Client c, final int itemUsed, final int useWith, final int itemUsedSlot,
			final int usedWithSlot) {
		if (itemUsed == 946 && useWith == 1511 || useWith == 1513 || useWith == 1515 || useWith == 1517
				|| useWith == 1519 || useWith == 1521) {
			c.getFletching().handleLog(itemUsed, useWith);
		}
		if (itemUsed == 52 || useWith == 314) {
			c.getFletching().makeArrows(itemUsed, useWith);
		}
		if (itemUsed == 53 && useWith == 39 || useWith == 40 || useWith == 41 || useWith == 42 || useWith == 43
				|| useWith == 44) {
			c.getFletching().makeArrows(itemUsed, useWith);
		}
		if (itemUsed == 1759 || useWith == 1759) {
			JewelryMaking.stringAmulet(c, itemUsed, useWith);
		}
		if (itemUsed == 1733 || useWith == 1733) {
			LeatherMaking.craftLeatherDialogue(c, itemUsed, useWith);
		}
		if (itemUsed == 233 || useWith == 233) {
			Grinding.grindItem(c, itemUsed, useWith, (itemUsed == 233 ? usedWithSlot : itemUsedSlot));
		}
		if (itemUsed == 227 || useWith == 227) {
			PotionMaking.createUnfinishedPotion(c, itemUsed, useWith);
		}
		if (itemUsed == 1755 || useWith == 1755) {
			GemCutting.cutGem(c, itemUsed, useWith);
		}
		PotionMaking.createPotion(c, itemUsed, useWith);
		switch (itemUsed) {
		}
	}

	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch (itemId) {
		}
	}
}