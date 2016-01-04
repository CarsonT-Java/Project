package RS2.skills;

import java.security.SecureRandom;

import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.task.Task;

public class Cooking {

	private static SecureRandom cookingRandom = new SecureRandom();

	private static enum CookingItems {
		SHRIMP(317, 315, 7954, 1, 30, 33, "shrimp"), ANCHOVIES(321, 319, 323, 5, 45, 34, "anchovies"), TROUT(335, 333,
				343, 20, 70, 50, "trout"), SALMON(331, 329, 343, 30, 90, 58, "salmon"), PIKE(359, 361, 343, 35, 100, 64,
						"TUNA"), LOBSTER(377, 379, 381, 40, 120, 74, "lobster"), SWORDFISH(371, 373, 375, 50, 140, 86,
								"swordfish"), MONKFISH(7944, 7946, 7948, 62, 150, 91, "monkfish"), SHARK(383, 385, 387,
										80, 210, 94, "shark"), MANTA_RAY(389, 391, 393, 91, 169, 99, "manta ray");

		int rawItem, cookedItem, burntItem, levelReq, xp, stopBurn;
		String name;

		private CookingItems(int rawItem, int cookedItem, int burntItem, int levelReq, int xp, int stopBurn,
				String name) {
			this.rawItem = rawItem;
			this.cookedItem = cookedItem;
			this.burntItem = burntItem;
			this.levelReq = levelReq;
			this.xp = xp;
			this.stopBurn = stopBurn;
			this.name = name;
		}

		private int getRawItem() {
			return rawItem;
		}

		private int getCookedItem() {
			return cookedItem;
		}

		private int getBurntItem() {
			return burntItem;
		}

		private int getLevelReq() {
			return levelReq;
		}

		private int getXp() {
			return xp;
		}

		private int getStopBurn() {
			return stopBurn;
		}

		private String getName() {
			return name;
		}
	}

	public static CookingItems forId(int itemId) {
		for (CookingItems item : CookingItems.values()) {
			if (itemId == item.getRawItem()) {
				return item;
			}
		}
		return null;
	}

	private static void setCooking(Client c) {
		c.playerIsCooking = true;
		c.stopPlayerSkill = true;
	}

	private static void resetCooking(Client c) {
		c.playerIsCooking = false;
		c.stopPlayerSkill = false;
	}

	private static void viewCookInterface(Client c, int item) {
		c.getPA().sendFrame164(1743);
		c.getPA().sendFrame246(13716, 190, item);
		c.getPA().sendFrame126("" + c.getItems().getItemName(item) + "", 13717);
	}

	public static void startCooking(Client c, int itemId, int objectId) {
		CookingItems item = forId(itemId);
		if (item != null) {
			if (c.playerLevel[c.playerCooking] < item.getLevelReq()) {
				c.getPA().removeAllWindows();
				c.sendMessage("You need a Cooking level of " + item.getLevelReq() + " to cook this.");
				return;
			}
			if (c.playerIsCooking) {
				c.getPA().removeAllWindows();
				return;
			}
			c.cookingItem = itemId;
			c.cookingObject = objectId;
			viewCookInterface(c, item.getRawItem());
		}
	}

	private static boolean getSuccess(Client c, int burnBonus, int levelReq, int stopBurn) {
		if (c.playerLevel[c.playerCooking] >= stopBurn) {
			return false;
		}
		double burn_chance = (55.0 - burnBonus);
		double cook_level = c.playerLevel[c.playerCooking];
		double lev_needed = (double) levelReq;
		double burn_stop = (double) stopBurn;
		double multi_a = (burn_stop - lev_needed);
		double burn_dec = (burn_chance / multi_a);
		double multi_b = (cook_level - lev_needed);
		burn_chance -= (multi_b * burn_dec);
		double randNum = cookingRandom.nextDouble() * 100.0;
		return burn_chance <= randNum;
	}

	public static void cookItem(final Client c, final int itemId, final int amount, final int objectId) {
		final CookingItems item = forId(itemId);
		if (item != null) {
			setCooking(c);
			c.getPA().removeAllWindows();
			c.doAmount = amount;
			if (c.doAmount > c.getItems().getItemAmount(itemId)) {
				c.doAmount = c.getItems().getItemAmount(itemId);
			}
			if (objectId > 0) {
				c.startAnimation(objectId == 2732 ? 897 : 896);
			}
			GameEngine.getTaskScheduler().schedule(new Task(2, true) {
				@Override
				public void execute() {
					if (!c.playerIsCooking) {
						resetCooking(c);
						this.stop();
						return;
					}
					if (!c.getItems().playerHasItem(item.getRawItem(), 1)) {
						c.sendMessage("You have run out of " + item.getName() + " to cook.");
						resetCooking(c);
						this.stop();
						return;
					}
					boolean burn = getSuccess(c, 3, item.getLevelReq(), item.getStopBurn());
					c.getItems().deleteItem(item.getRawItem(), c.getItems().getItemSlot(itemId), 1);
					if (!burn) {
						c.sendMessage("You successfully cook the " + item.getName().toLowerCase() + ".");
						c.getPA().addSkillXP(item.getXp(), 7);
						c.getItems().addItem(item.getCookedItem(), 1);
					} else {
						c.sendMessage("Oops! You accidentally burnt the " + item.getName().toLowerCase() + "!");
						c.getItems().addItem(item.getBurntItem(), 1);
					}
					c.doAmount--;
					if (c.doAmount > 0) {
						if (objectId > 0) {
							c.startAnimation(objectId == 2732 ? 897 : 896);
						}
					} else if (c.doAmount == 0) {
						resetCooking(c);
						this.stop();
					}
				}
			});
		}
	}
}