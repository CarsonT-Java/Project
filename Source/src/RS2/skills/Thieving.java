package RS2.skills;

import java.util.HashMap;
import java.util.Map;

import RS2.GameEngine;
import RS2.Settings;
import RS2.model.player.Client;
import RS2.task.Task;
import RS2.util.Misc;

public class Thieving {
	private Client c;

	public Thieving(Client c) {
		this.c = c;
	}

	private enum Stalls {
		SILK(1, 1, 995, 1000, new int[] { 629 }, 1), FUR(15, 1, 995, 2000, new int[] { 632 }, 1), MARKET(1, 1, 995,
				3000, new int[] { 634 }, 1), TEA(30, 1, 995, 4000, new int[] { 635 }, 1);

		private int[] objects;
		private int req, xp, rew, amt, ticks;

		private Stalls(int req, int xp, int rew, int amt, int[] obj, int ticks) {
			this.req = req;
			this.xp = xp;
			this.rew = rew;
			this.amt = amt;
			this.objects = obj;
			this.ticks = ticks;
		}

		public int getTicks() {
			return ticks;
		}

		public int getReward() {
			return rew;
		}

		public int getXp() {
			return xp;
		}

		public int getReq() {
			return req;
		}

		public int getAmount() {
			return amt;
		}

		private static final Map<Integer, Stalls> stall = new HashMap<Integer, Stalls>();

		public static Stalls forId(int id) {
			return stall.get(id);
		}

		static {
			for (Stalls t : Stalls.values()) {
				for (int obj : t.objects) {
					stall.put(obj, t);
				}
			}
		}
	}

	public void steal(final int objID) {
		final Stalls s = Stalls.forId(objID);
		if (s != null) {
			if (System.currentTimeMillis() - c.lastSteal > 1500) {
				if (c.playerLevel[c.playerThieving] >= s.getReq() && true && c.getPA().freeSlots() != 0) {
					c.turnPlayerTo(c.objectX, c.objectY);
					GameEngine.getTaskScheduler().schedule(new Task(2, true) {
						@Override
						public void execute() {
							int cycle = 0;
							int reqCycle = Misc.random(s.getTicks());
							if (cycle != reqCycle) {
								cycle++;
								c.startAnimation(122);
							}
							if (cycle == reqCycle && System.currentTimeMillis() - c.lastSteal > 1500) {
								c.lastSteal = System.currentTimeMillis();
								c.getPA().addSkillXP(Settings.THIEVING_EXPERIENCE * s.getXp(), c.playerThieving);
								c.getItems().addItem(s.getReward(), s.getAmount());
								c.sendMessage("You little thieve!");
								c.getPA().resetAnimation();
								c.startAnimation(65535);
								cycle = 0;
								stop();
							}
						}
					});
				}
			}
		}
	}
}
