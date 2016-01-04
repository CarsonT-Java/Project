package RS2.model.player.packets;

import RS2.model.item.UseItem;
import RS2.model.player.Client;
import RS2.model.player.PacketType;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
		UseItem.ItemonItem(c, itemUsed, useWith, itemUsedSlot, usedWithSlot);
		if (c.getFiremaking().isLog(itemUsed)) {
			c.getFiremaking().lightFire(c, itemUsed, itemUsedSlot);
		}
		if (c.getFiremaking().isLog(useWith)) {
			c.getFiremaking().lightFire(c, useWith, usedWithSlot);
		}
	}

}
