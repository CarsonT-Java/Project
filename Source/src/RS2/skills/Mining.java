package RS2.skills;

import java.util.HashMap;
import java.util.Map;

import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.task.Task;
import RS2.util.Misc;

public class Mining {
	private Client c;

	public Mining(Client c) {
		this.c = c;
	}

	/*
	 * PICKS
	 */
	public static enum Picks {
		BRONZE(1265, 1, 625), IRON(1267, 1, 626), STEEL(1269, 6, 627), ADDY(1271, 31, 628), MITH(1273, 21,
				628), RUNE(1275, 41, 624);

		private int id, req, anim;

		private Picks(int id, int level, int animation) {
			this.id = id;
			this.req = level;
			this.anim = animation;
		}

		public static Map<Integer, Picks> picks = new HashMap<Integer, Picks>();

		public static Picks forId(int id) {
			return picks.get(id);
		}

		static {
			for (Picks pick : Picks.values()) {
				picks.put(pick.getId(), pick);
			}
		}

		public int getId() {
			return id;
		}

		public int getRequiredLevel() {
			return req;
		}

		public int getAnim() {
			return anim;
		}
	}

	/*
	 * ORES
	 */
	private enum Ores {
		COPPER(1, 25, 436, new int[] { 2090, 2091 }, 2), IRON(15, 35, 440, new int[] { 2092, 2093 }, 1), TIN(1, 25, 438,
				new int[] { 2094, 2095 }, 3), MITHRIL(30, 68, 447, new int[] { 2102, 2103 }, 4), ADAMENT(35, 85, 449,
						new int[] { 2104, 2105 }, 4), RUNE(36, 36, 451, new int[] { 14859, 14860 }, 2), COAL(50, 158,
								453, new int[] { 2097, 2096 }, 6), SILVER(60, 175, 442, new int[] { 2100, 2101 },
										7), GOLD(75, 250, 444, new int[] { 2098, 2099 }, 8);

		private int[] objects;
		private int req, xp, log, ticks;

		private Ores(int req, int xp, int log, int[] obj, int ticks) {
			this.req = req;
			this.xp = xp;
			this.log = log;
			this.objects = obj;
			this.ticks = ticks;
		}

		public int getTicks() {
			return ticks;
		}

		public int getReward() {
			return log;
		}

		public int getXp() {
			return xp;
		}

		public int getReq() {
			return req;
		}

		private static final Map<Integer, Ores> ore = new HashMap<Integer, Ores>();

		public static Ores forId(int id) {
			return ore.get(id);
		}

		static {
			for (Ores t : Ores.values()) {
				for (int obj : t.objects) {
					ore.put(obj, t);
				}
			}
		}
	}

	private int hasPick() {
		for (Picks p : Picks.values()) {
			if (c.playerEquipment[c.playerWeapon] == p.getId()) {
				return p.getId();
			} else if (c.getItems().playerHasItem(p.getId())) {
				return p.getId();
			}
		}
		return -1;
	}

	public void mineOres(final int objId) {
		final Picks p = Picks.forId(hasPick());
		if (p != null && c.isMining == false) {
			if (c.playerLevel[c.playerMining] >= p.getRequiredLevel()) {
				final Ores o = Ores.forId(objId);
				if (o != null) {
					if (c.playerLevel[c.playerMining] >= o.getReq() && c.getItems().freeSlots() != 0
							&& System.currentTimeMillis() - c.lastMine > 1500) {
						c.turnPlayerTo(c.objectX, c.objectY);
						GameEngine.getTaskScheduler().schedule(new Task(2, true) {
							@Override
							public void execute() {
								c.isMining = true;
								int cycle = 0;
								int reqCycle = Misc.random(o.getTicks());
								if (cycle != reqCycle) {
									cycle++;
									c.startAnimation(p.getAnim());
								}
								if (cycle == reqCycle && System.currentTimeMillis() - c.lastMine > 1500) {
									c.lastMine = System.currentTimeMillis();
									c.getItems().addItem(o.getReward(), 1);
									c.getPA().addSkillXP(o.getXp(), c.playerMining);
									c.getPA().refreshSkill(c.playerMining);
									c.sendMessage("You mine an ore.");
									c.getPA().resetAnimation();
									c.isMining = false;
									c.startAnimation(65535);
									cycle = 0;
									stop();
								}
							}
						});
					} else if (c.getItems().freeSlots() == 0) {
						c.sendMessage("Your inventory is full.");
					} else if (!(c.playerLevel[c.playerMining] >= o.getReq())) {
						c.sendMessage("You do not have a high enough mining level to mine this ore.");
					}
				}
			} else {
				c.sendMessage("You need a higher level to use this pickaxe.");
			}
		} else {
			c.sendMessage("You do not have a pickaxe that you can use.");
		}
	}
}
