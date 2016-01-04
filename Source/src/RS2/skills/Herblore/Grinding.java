package RS2.skills.Herblore;

import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.task.Task;

public class Grinding {

	private final static int[][] GRINDABLES = {

			{ 237, 235 }, { 1973, 1975 }, { 5075, 6693 }

	};

	public static void grindItem(final Client c, final int itemUsed, final int usedWith, final int itemSlot) {

		c.startAnimation(364);
		final int itemId = (itemUsed == 233 ? usedWith : itemUsed);
		for (int i = 0; i < GRINDABLES.length; i++) {
			if (itemId == GRINDABLES[i][0]) {
				final int product = GRINDABLES[i][1];
				GameEngine.getTaskScheduler().schedule(new Task(2, true) {
					@Override
					public void execute() {
						c.getItems().deleteItem(itemId, itemSlot, 1);
						c.getItems().addItem(product, 1);
						c.sendMessage("You grind the " + c.getItems().getItemName(itemId) + " into "
								+ c.getItems().getItemName(product) + ".");
						stop();
					}
				});
			}
		}
	}
}