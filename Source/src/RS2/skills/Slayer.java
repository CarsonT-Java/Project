package RS2.skills;

import java.util.ArrayList;
import RS2.model.player.Client;
import RS2.util.Misc;

public class Slayer {

	public static final int EASY_TASK = 1, MEDIUM_TASK = 2, HARD_TASK = 3;

	public static ArrayList<Integer> easyTask = new ArrayList<Integer>();
	public static ArrayList<Integer> mediumTask = new ArrayList<Integer>();
	public static ArrayList<Integer> hardTask = new ArrayList<Integer>();

	private Client c;

	public Slayer(Client c) {
		this.c = c;
	}

	public enum Task {
		ABERRANT_SPECTRE(1604, 60, 2, "Slayer Tower"), ABYSSAL_DEMON(1615, 85, 3, "Slayer Tower"), BANSHEE(1612, 15, 2,
				"Slayer Tower"), BASILISK(1616, 40, 2, "Fremennik Slayer Dungeon"), BAT(412, 1, 1,
						"Silvarea"), BLACK_DEMON(84, 1, 2, "Taverly Dungeon"), BLACK_DRAGON(54, 1, 3,
								"Taverly Dungeon"), BLOODVELD(1618, 50, 2, "Slayer Tower"), BLUE_DRAGON(55, 1, 2,
										"Taverly Dungeon"), BRONZE_DRAGON(1590, 1, 3,
												"Brimhaven Dungeon"), CAVE_CRAWLER(1600, 10, 1,
														"Fremennik Slayer Dungeon"), CAVE_HORROR(4353, 58, 2,
																"Slayer Tower"), COCKATRICE(1620, 25, 2,
																		"Fremennik Slayer Dungon"), CRAWLING_HAND(1648,
																				5, 1, "Slayer Tower"), DAGGANOTH(2455,
																						1, 1,
																						"Waterbirth Island"), DARK_BEAST(
																								2783, 90, 3,
																								"Slayer Tower"), DUST_DEVIL(
																										1624, 65, 2,
																										"Slayer Tower"), EARTH_WARRIOR(
																												124, 1,
																												1,
																												"Edgeville Dungeon"), FIRE_GIANT(
																														110,
																														1,
																														2,
																														"Brimhaven Dungeon"), GARGOYLE(
																																1610,
																																75,
																																3,
																																"Slayer Tower"), GHOST(
																																		103,
																																		1,
																																		1,
																																		"Taverly Dungeon"), GREATER_DEMON(
																																				83,
																																				1,
																																				2,
																																				"Brimhaven Dungeon"), GREATER_DEMON2(
																																						83,
																																						1,
																																						3,
																																						"Brimhaven Dungeon"), GREEN_DRAGON(
																																								941,
																																								1,
																																								2,
																																								"The Wilderness"), GREEN_DRAGON2(
																																										941,
																																										1,
																																										3,
																																										"The Wilderness"), HELLHOUND(
																																												49,
																																												1,
																																												3,
																																												"Taverly Dungeon"), HILL_GIANT(
																																														117,
																																														1,
																																														2,
																																														"Edgeville Dungeon"), ICE_GIANT(
																																																111,
																																																1,
																																																2,
																																																"Asgarnian Ice Caves"), ICE_WARRIOR(
																																																		125,
																																																		1,
																																																		2,
																																																		"Asgarnian Ice Caves"), INFERNAL_MAGE(
																																																				1643,
																																																				45,
																																																				2,
																																																				"Slayer Tower"), IRON_DRAGON(
																																																						1591,
																																																						1,
																																																						3,
																																																						"Brimhaven Dungeon"), JELLY(
																																																								1637,
																																																								52,
																																																								2,
																																																								"Fremennik Slayer Dungeon"), KALPHITE(
																																																										1153,
																																																										1,
																																																										2,
																																																										"Kalphite Lair"), KURASK(
																																																												1608,
																																																												70,
																																																												3,
																																																												"Fremennik Slayer Dungeon"), KURASK2(
																																																														1608,
																																																														70,
																																																														2,
																																																														"Fremennik Slayer Dungeon"), LESSER_DEMON(
																																																																82,
																																																																1,
																																																																2,
																																																																"Karamja Dungeon"), MOSS_GIANT(
																																																																		112,
																																																																		1,
																																																																		2,
																																																																		"Brimhaven Dungeon"), NECHRYAELS(
																																																																				1613,
																																																																				80,
																																																																				3,
																																																																				"Slayer Tower"), PYREFIEND(
																																																																						1633,
																																																																						30,
																																																																						1,
																																																																						"Fremennik Slayer Dungeon"), RED_DRAGON(
																																																																								53,
																																																																								1,
																																																																								2,
																																																																								"Brimhaven Dungeon"), RED_DRAGON2(
																																																																										53,
																																																																										1,
																																																																										3,
																																																																										"Brimhaven Dungeon"), ROCKSLUG(
																																																																												1622,
																																																																												20,
																																																																												1,
																																																																												"Fremennik Slayer Dungeon"), SKELETON(
																																																																														90,
																																																																														1,
																																																																														1,
																																																																														"Edgeville Dungeon"), STEEL_DRAGON(
																																																																																1591,
																																																																																1,
																																																																																3,
																																																																																"Brimhaven Dungeon"), STEEL_DRAGON2(
																																																																																		1591,
																																																																																		1,
																																																																																		2,
																																																																																		"Brimhaven Dungeon"), TUROTH(
																																																																																				1626,
																																																																																				55,
																																																																																				2,
																																																																																				"Fremennik Slayer Dungeon"), TUROTH2(
																																																																																						1626,
																																																																																						55,
																																																																																						3,
																																																																																						"Fremennik Slayer Dungeon");

		private int npcId, levelReq, diff;
		private String location;

		private Task(int npcId, int levelReq, int difficulty, String location) {
			this.npcId = npcId;
			this.levelReq = levelReq;
			this.location = location;
			this.diff = difficulty;
		}

		public int getNpcId() {
			return npcId;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int getDifficulty() {
			return diff;
		}

		public String getLocation() {
			return location;
		}
	}

	public void resizeTable(int difficulty) {
		if (easyTask.size() > 0 || hardTask.size() > 0 || mediumTask.size() > 0) {
			easyTask.clear();
			mediumTask.clear();
			hardTask.clear();
		}
		for (Task slayerTask : Task.values()) {
			if (slayerTask.getDifficulty() == EASY_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq())
					easyTask.add(slayerTask.getNpcId());
				continue;
			} else if (slayerTask.getDifficulty() == MEDIUM_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq())
					mediumTask.add(slayerTask.getNpcId());
				continue;
			} else if (slayerTask.getDifficulty() == HARD_TASK) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					hardTask.add(slayerTask.getNpcId());
				}
				continue;
			}
		}
	}

	public int getRequiredLevel(int npcId) {
		for (Task task : Task.values())
			if (task.npcId == npcId)
				return task.levelReq;
		return -1;
	}

	public static String getLocation(int npcId) {
		for (Task task : Task.values()) {
			if (task.npcId == npcId) {
				return task.location;
			}
		}
		return "";
	}


	public boolean isSlayerNpc(int npcId) {
		for (Task task : Task.values()) {
			if (task.getNpcId() == npcId)
				return true;
		}
		return false;
	}

	public boolean isSlayerTask(int npcId) {
		if (isSlayerNpc(npcId)) {
			if (c.slayerTask == npcId) {
				return true;
			}
		}
		return false;
	}

	public int getDifficulty(int npcId) {
		for (Task task : Task.values())
			if (task.npcId == npcId)
				return task.getDifficulty();
		return 1;
	}

	public String getTaskName(int npcId) {
		for (Task task : Task.values())
			if (task.npcId == npcId)
				return task.name().replaceAll("_", " ").replaceAll("2", "").toLowerCase();
		return "";
	}

	public int getTaskId(String name) {
		for (Task task : Task.values())
			if (task.name() == name)
				return task.npcId;
		return -1;
	}

	public boolean hasTask() {
		return c.slayerTask > 0 || c.taskAmount > 0;
	}

	public void generateTask() {
		if (hasTask() && !c.needsNewTask) {
			return;
		}
		if (hasTask() && c.needsNewTask) {
			int difficulty = getDifficulty(c.slayerTask);
			if (difficulty == EASY_TASK) {
				c.getDH().sendDialogues(4, 1599);
				c.needsNewTask = false;
				return;
			}
		}
		int taskLevel = getSlayerDifficulty();
		System.out.println("EASY :" + easyTask + "\nMEDIUM: " + mediumTask + "\nHARD: " + hardTask + "");
		for (Task slayerTask : Task.values()) {
			if (slayerTask.getDifficulty() == taskLevel) {
				if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
					resizeTable(taskLevel);
					if (!c.needsNewTask) {
						int task = getRandomTask(taskLevel);
						for (int i = 0; i < c.removedTasks.length; i++) {
							if (task == c.removedTasks[i]) {
								c.sendMessage("Unavailable task: " + task);
								generateTask();
								return;
							}
						}
						c.slayerTask = task;
						c.taskAmount = getTaskAmount(taskLevel);
					} else {
						int task = getRandomTask(getDifficulty(taskLevel - 1));
						for (int i = 0; i < c.removedTasks.length; i++) {
							if (task == c.removedTasks[i]) {
								c.sendMessage("Unavailable task: " + task);
								generateTask();
								return;
							}
						}
						c.slayerTask = task;
						c.taskAmount = getTaskAmount(getDifficulty(c.slayerTask) - 1);
						c.needsNewTask = false;
					}
					c.getDH().sendDialogues(3, 1599);
					c.sendMessage("You have been assigned " + c.taskAmount + " " + getTaskName(c.slayerTask)
							+ ", Good luck " + c.playerName + ".");
					return;
				}
			}
		}
	}

	public int getTaskAmount(int diff) {
		switch (diff) {
		case 1:
			return 25 + Misc.random(75);
		case 2:
			return 45 + Misc.random(55);
		case 3:
			return 55 + Misc.random(65);
		}
		return 50 + Misc.random(50);
	}

	public int getRandomTask(int diff) {
		if (diff == EASY_TASK) {
			return easyTask.get(Misc.random(easyTask.size() - 1));
		} else if (diff == MEDIUM_TASK) {
			return mediumTask.get(Misc.random(mediumTask.size() - 1));
		} else if (diff == HARD_TASK) {
			return hardTask.get(Misc.random(hardTask.size() - 1));
		}
		return easyTask.get(Misc.random(easyTask.size() - 1));
	}

	public int getSlayerDifficulty() {
		if (c.combatLevel > 0 && c.combatLevel <= 45) {
			return EASY_TASK;
		} else if (c.combatLevel > 45 && c.combatLevel <= 90) {
			return MEDIUM_TASK;
		} else if (c.combatLevel > 90) {
			return HARD_TASK;
		}
		return EASY_TASK;
	}

	public void cancelTask() {
		if (!hasTask()) {
			c.sendMessage("You must have a task to cancel first.");
			return;
		}
		if (c.slayerPoints < 30) {
			c.sendMessage("This requires atleast 30 slayer points, which you don't have.");
			return;
		}
		c.sendMessage(
				"You have cancelled your current task of " + c.taskAmount + " " + getTaskName(c.slayerTask) + ".");
		c.slayerTask = -1;
		c.taskAmount = 0;
		c.slayerPoints -= 30;
	}

	public void removeTask() {
		int counter = 0;
		if (!hasTask()) {
			c.sendMessage("You must have a task to remove first.");
			return;
		}
		if (c.slayerPoints < 100) {
			c.sendMessage("This requires atleast 100 slayer points, which you don't have.");
			return;
		}
		for (int i = 0; i < c.removedTasks.length; i++) {
			if (c.removedTasks[i] != -1) {
				counter++;
			}
			if (counter == 4) {
				c.sendMessage("You don't have any open slots left to remove tasks.");
				return;
			}
			if (c.removedTasks[i] == -1) {
				c.removedTasks[i] = c.slayerTask;
				c.slayerPoints -= 100;
				c.slayerTask = -1;
				c.taskAmount = 0;
				c.sendMessage("Your current slayer task has been removed, you can't obtain this task again.");
				updateCurrentlyRemoved();
				return;
			}
		}
	}

	public void updatePoints() {
		c.getPA().sendFrame126("Slayer Points: " + c.slayerPoints, 41011);
		c.getPA().sendFrame126("Slayer Points: " + c.slayerPoints, 41511);
		c.getPA().sendFrame126("Slayer Points: " + c.slayerPoints, 42011);
	}

	public void updateCurrentlyRemoved() {
		int line[] = { 42014, 42015, 42016, 42017 };
		for (int i = 0; i < c.removedTasks.length; i++) {
			if (c.removedTasks[i] != -1) {
				c.getPA().sendFrame126(this.getTaskName(c.removedTasks[i]), line[i]);
			} else {
				c.getPA().sendFrame126("", line[i]);
			}
		}
	}

	public void buySlayerExperience() {
		if (System.currentTimeMillis() - c.buySlayerTimer < 500)
			return;
		if (c.slayerPoints < 50) {
			c.sendMessage("You need at least 50 slayer points to gain 32,500 Experience.");
			return;
		}
		c.buySlayerTimer = System.currentTimeMillis();
		c.slayerPoints -= 50;
		c.getPA().addSkillXP(32500, 18);
		c.sendMessage("You spend 50 slayer points and gain 32,500 experience in slayer.");
		updatePoints();
	}
}