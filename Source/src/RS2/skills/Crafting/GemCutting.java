package RS2.skills.Crafting;

import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.task.Task;

public class GemCutting extends CraftingData {

	public static void cutGem(final Client c, final int itemUsed, final int usedWith) {
		final int itemId = (itemUsed == 1755 ? usedWith : itemUsed);
		for (final cutGemData g : cutGemData.values()) {
			if (itemId == g.getUncut()) {
				if (c.playerLevel[12] < g.getLevel()) {
					c.sendMessage("You need a crafting level of " + g.getLevel() + " to cut this gem.");
					return;
				}
				if (!c.getItems().playerHasItem(itemId)) {
					return;
				}
				c.startAnimation(g.getAnimation());
				GameEngine.getTaskScheduler().schedule(new Task(2, true) {
					@Override
					public void execute() {
						if (c.getItems().playerHasItem(itemId)) {
							c.getItems().deleteItem(itemId, 1);
							c.getItems().addItem(g.getCut(), 1);
							c.getPA().addSkillXP((int) g.getXP(), 12);
							c.sendMessage("You cut the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
							c.startAnimation(g.getAnimation());
							stop();
						}
					}
				});
			}

		}
	}
}