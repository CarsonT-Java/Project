package RS2.skills;

import java.util.HashMap;
import java.util.Map;

import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.task.Task;
import RS2.util.Misc;

public class Woodcutting {
	int i;
	int x;
	int y;
	int type;
	int respawnTime;
	int j;
	private Client c;

	public Woodcutting(Client c) {
		this.c = c;
	}

	public static enum Hatchet {
		BRONZE(1351, 1, 879), IRON(1349, 1, 877), STEEL(1353, 6, 875), BLACK(1361, 6, 873), MITHRIL(1355, 21,
				871), ADAMANT(1357, 31, 869), RUNE(1359, 41, 867), DRAGON(6739, 61, 2846);

		private int id, req, anim;

		private Hatchet(int id, int level, int animation) {
			this.id = id;
			this.req = level;
			this.anim = animation;
		}

		public static Map<Integer, Hatchet> hatchets = new HashMap<Integer, Hatchet>();

		public static Hatchet forId(int id) {
			return hatchets.get(id);
		}

		static {
			for (Hatchet hatchet : Hatchet.values()) {
				hatchets.put(hatchet.getId(), hatchet);
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

	private enum Trees {
		NORMAL(1, 25, 1511,
				new int[] { 1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1289, 1290, 1291, 1315, 1316,
						1318, 1319, 1330, 1331, 1332, 1365, 1383, 1384, 3033, 3034, 3035, 3036, 3881, 3882, 3883, 5902,
						5903, 5904 },
				2, false), ACHEY(1, 25, 2862, new int[] { 2023 }, 1, false), OAK(15, 38, 1521, new int[] { 1281, 3037 },
						3, true), WILLOW(30, 68, 1519, new int[] { 1308, 5551, 5552, 5553 }, 4, true), TEAK(35, 85,
								6333, new int[] { 9036 }, 4, true), DRAMEN(36, 36, 771, new int[] { 1292 }, 2,
										true), MAPLE(45, 100, 1517, new int[] { 1307, 4677 }, 5, true), MAHOGANY(50,
												158, 6332, new int[] { 9034 }, 6, true), YEW(60, 175, 1515,
														new int[] { 1309 }, 7,
														true), MAGIC(75, 250, 1513, new int[] { 1306 }, 8, true);

		private int[] objects;
		private int req, xp, log, ticks;
		private boolean multi;

		private Trees(int req, int xp, int log, int[] obj, int ticks, boolean multi) {
			this.req = req;
			this.xp = xp;
			this.log = log;
			this.objects = obj;
			this.ticks = ticks;
			this.multi = multi;
		}

		public boolean isMulti() {
			return multi;
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

		private static final Map<Integer, Trees> tree = new HashMap<Integer, Trees>();

		public static Trees forId(int id) {
			return tree.get(id);
		}

		static {
			for (Trees t : Trees.values()) {
				for (int obj : t.objects) {
					tree.put(obj, t);
				}
			}
		}
	}

	private int hasHatchet() {
		for (Hatchet h : Hatchet.values()) {
			if (c.playerEquipment[c.playerWeapon] == h.getId()) {
				return h.getId();
			} else if (c.getItems().playerHasItem(h.getId())) {
				return h.getId();
			}
		}
		return -1;
	}

	public void cutWood(final int objId) {
		final Hatchet h = Hatchet.forId(hasHatchet());
		if (h != null && c.isCutting == false) {
			if (c.playerLevel[c.playerWoodcutting] >= h.getRequiredLevel() && c.animationRequest != h.getAnim()) {
				final Trees t = Trees.forId(objId);
				if (t != null) {
					if (c.playerLevel[c.playerWoodcutting] >= t.getReq() && c.getItems().freeSlots() != 0
							&& System.currentTimeMillis() - c.lastCut > 1500) {
						c.turnPlayerTo(c.objectX, c.objectY);
						c.startAnimation(h.getAnim());
						GameEngine.getTaskScheduler().schedule(new Task(2, true) {
							@Override
							public void execute() {
								c.isCutting = true;
								int cycle = 0;
								int reqCycle = Misc.random(t.getTicks());
								if (cycle != reqCycle) {
									cycle++;
									c.startAnimation(h.getAnim());
								}
								if (cycle == reqCycle && System.currentTimeMillis() - c.lastCut > 1500) {
									c.lastCut = System.currentTimeMillis();
									c.getItems().addItem(t.getReward(), 1);
									c.getPA().addSkillXP(t.getXp(), c.playerWoodcutting);
									c.getPA().refreshSkill(c.playerWoodcutting);
									c.sendMessage("You cut a log.");
									c.isCutting = false;
									c.getPA().resetAnimation();
									c.startAnimation(65535);
									cycle = 0;
									if (!t.isMulti()) {
										this.stop();
									} else {
										cutWood(objId);
									}
								}
							}
						});
					} else if (c.getItems().freeSlots() == 0) {
						c.sendMessage("Your inventory is full.");
					} else if (!(c.playerLevel[c.playerWoodcutting] >= t.getReq())) {
						c.sendMessage("You do not have a high enough woodcutting level to cut this tree.");
					}
				}
			} else {
				c.sendMessage("You need a higher level to use this hatchet.");
			}
		} else {
			c.sendMessage("You do not have a hatchet that you can use.");
		}
	}
}