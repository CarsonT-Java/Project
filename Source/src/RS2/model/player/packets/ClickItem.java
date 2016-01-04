package RS2.model.player.packets;

import RS2.model.player.Client;
import RS2.model.player.PacketType;
import RS2.skills.Herblore.HerbCleaning;
import RS2.skills.Runecrafting.Pouches;

/**
 * Clicking an item, bury bone, eat food etc
 **/
@SuppressWarnings("all")
public class ClickItem implements PacketType {
	private Client c;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		HerbCleaning.handleHerbCleaning(c, itemId, itemSlot);
		for (int i = 0; i < Pouches.pouchData.length; i++) {
			if (itemId == Pouches.pouchData[i][0]) {
				Pouches.fillPouch(c, itemId);
			}
		}
		if (c.getPrayer().IsABone(itemId))
			c.getPrayer().buryBone(itemId);
	}
}
