package RS2.model.item;

import RS2.model.player.Client;
import RS2.model.player.PacketType;
import RS2.skills.Runecrafting.Pouches;


/**
 * Wear Item
 **/
@SuppressWarnings("all")
public class WearItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();
		for (int i = 0; i < Pouches.pouchData.length; i++) {
			if (c.wearId == Pouches.pouchData[i][0]) {
				Pouches.emptyPouch(c, c.wearId);
			}
		}
		int oldCombatTimer = c.attackTimer;
		if (c.playerIndex > 0 || c.npcIndex > 0)
			c.getCombat().resetPlayerAttack();
		c.getItems().wearItem(c.wearId, c.wearSlot);
	}
}