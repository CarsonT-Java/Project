package RS2.skills;

import java.util.HashMap;
import RS2.Settings;
import RS2.model.player.Client;
import RS2.util.Misc;

public class Prayer {

	Client c;

	enum Bones {
		REGULAR(526, 5, "Bones"), BIG(532, 15, "Big Bones"), BABY_DRAG(534, 30, "Baby Dragon Bones"), DRAG(536, 72,
				"Dragon Bones"), DAG(6729, 125, "Dagannoth Bones"), BAT(530, 5, "Bat Bones"), WOLF(2859, 5,
						"Wolf Bones"), MONKEY(3179, 5, "Monkey Bones"), FROST(18830, 180,
								"Frost Dragon Bones"), ANCIENT(15410, 200, "Ancient Bones"), JOGRE(3125, 15,
										"Jogre Bones");

		static HashMap<Integer, Bones> BoneInfo = new HashMap<Integer, Bones>();

		int ID, XP;
		String Name;

		static {
			for (final Bones b : BoneInfo.values())
				Bones.BoneInfo.put(b.XP, b);
		}

		Bones(final int ID, final int XP, final String Name) {
			this.ID = ID;
			this.XP = XP;
			this.Name = Name;
		}

		int getID() {
			return ID;
		}

		int getXP() {
			return XP;
		}

		String getName() {
			return Name;
		}

	}

	public Prayer(Client c) {
		this.c = c;
	}

	public boolean IsABone(int ID) {
		for (final Bones b : Bones.values()) {
			if (c.getItems().playerHasItem(b.getID(), 1)) {
				if (b.getID() == ID) {
					return true;
				}
			}
		}
		return false;
	}

	public void buryBone(int ID) {
		if (System.currentTimeMillis() - c.buryDelay > 1500) {
			for (final Bones b : Bones.values()) {
				if (ID == b.getID()) {
					int doubleExperience = Misc.random(20);
					if (doubleExperience >= 1) {
						c.getItems().deleteItem(ID, 1);
						c.sendMessage("You bury some " + b.getName());
						c.getPA().addSkillXP(b.getXP() * Settings.PRAYER_EXPERIENCE, 5);
						c.buryDelay = System.currentTimeMillis();
						c.startAnimation(827);
					} else if (doubleExperience == 0) {
						c.getItems().deleteItem(ID, 1);
						c.sendMessage("You bury some " + b.getName() + " And get double xp!");
						c.getPA().addSkillXP(b.getXP() * 2 * Settings.PRAYER_EXPERIENCE, 5);
						c.buryDelay = System.currentTimeMillis();
						c.startAnimation(827);
					}
				}
			}
		}
	}

	public void bonesOnAltar(int ID) {
		int Failure = Misc.random(40);
		for (final Bones b : Bones.values()) {
			if (ID == b.getID()) {
				if (Failure >= 1) {
					c.getItems().deleteItem(ID, 1);
					c.sendMessage("The god's love your " + b.getName());
					c.getPA().addSkillXP(b.getXP() * 4 * Settings.PRAYER_EXPERIENCE, 5);
				} else if (Failure == 0) {
					c.gfx100(287);
					c.startAnimation(3103);
					c.forcedChat("I'm sorry!");
					c.sendMessage("The gods are not satisfied with your offering");
					c.setHitUpdateRequired2(true);
					c.setHitDiff2(10);
					c.updateRequired = true;
				}
			}
		}
	}
}