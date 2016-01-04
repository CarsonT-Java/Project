package RS2.model.player.packets;

import RS2.model.player.Client;
import RS2.model.player.PacketType;
import RS2.skills.Runecrafting.TalismanHandler;
import RS2.skills.Runecrafting.TalismanHandler.talismanData;

@SuppressWarnings("all")
public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		for (talismanData t : talismanData.values()) {
			if (itemId == t.getTalisman()) {
				TalismanHandler.handleTalisman(c, itemId);
			}
		}
		switch (itemId) {
		}
	}
}