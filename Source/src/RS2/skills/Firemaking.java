package RS2.skills;

import RS2.model.player.Client;
import RS2.task.Task;
import RS2.clip.region.Region;
import RS2.GameEngine;
import RS2.Settings;
import RS2.util.Misc;

public class Firemaking {

	private final Client c;

	public Firemaking(final Client c) {
		this.c = c;
	}

	public enum Firemake {
		NORMAL(1511, 40, 1, 5249, 30), OAK(1521, 60, 15, 5249, 45), WILLOW(1519, 90, 30, 5249, 60), MAPLE(1517, 135, 45,
				5249, 85), YEW(1515, 200, 60, 5249, 110), MAGIC(1513, 300, 75, 5249, 135), RED(7404, 50, 1, 11404,
						30), GREEN(7405, 50, 1, 11405, 30), BLUE(7406, 50, 1, 11406, 30), PURPLE(10329, 50, 1, 20001,
								30), WHITE(10328, 50, 1, 20000, 30);

		int logID, xp, levelReq, obj, last;

		private Firemake(int logID, int xp, int levelReq, int obj, int last) {
			this.logID = logID;
			this.xp = xp;
			this.levelReq = levelReq;
			this.obj = obj;
			this.last = last;
		}
	}

	public void noClip() {
		c.turnPlayerTo(c.getX() + 1, c.getY());
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			c.getPA().walkTo(-1, 0);
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
			c.getPA().walkTo(1, 0);
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
			c.getPA().walkTo(0, -1);
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
			c.getPA().walkTo(0, 1);
		}
		c.getPA().walkTo(1, 0);
	}

	public Firemake forLog(int id) {
		for (Firemake f : Firemake.values()) {
			if (f.logID == id) {
				return f;
			}
		}
		return null;
	}

	public boolean isLog(int id) {
		return forLog(id) != null;
	}

	public void lightFire(final Client c, int logID, int fromSlot) {

		Firemake f = forLog(logID);

		final int x = c.getX();
		final int y = c.getY();
		final int last = f.last + Misc.random(5);
		final int logid = f.logID;

		if (f != null) {
			if (System.currentTimeMillis() - c.lastLight > 1800) {
				if (c.playerLevel[c.playerFiremaking] >= f.levelReq) {
					if (c.getItems().playerHasItem(590) && c.getItems().playerHasItem(f.logID)) {
						c.lastLight = System.currentTimeMillis();
						c.getItems().deleteItem(f.logID, fromSlot, 1);
						c.getPA().addSkillXP(f.xp * Settings.FIREMAKING_EXPERIENCE, c.playerFiremaking);
						c.getPA().object(f.obj, c.getX(), c.getY(), 0, 10);
						noClip();
						GameEngine.getTaskScheduler().schedule(new Task(2, true) {
							int timer = last;

							@Override
							public void execute() {
								if (timer == 1) {
									stop();
								}

								if (timer > 0 && c != null) {
									timer--;
									c.sendMessage("Log timer = " + timer + "/" + last + " logId = " + logid);
								}
							}

							@Override
							public void stop() {
								c.getPA().object(-1, x, y, 1, 10);
								c.getItems().createGroundItem(592, x, y, 1);
								c.sendMessage("Turned = " + logid + "into ashes");
							}
						});
					} else {
						c.sendMessage("You need a firemaking level of at least " + f.levelReq + " to burn this log.");
					}
				}
			}
		}
	}
}