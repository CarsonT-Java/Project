package RS2.model.player.packets;

import RS2.model.object.Doors;
import RS2.model.player.Client;
import RS2.model.player.PacketType;
import RS2.skills.Smelting;
import RS2.util.Misc;

/**
 * Click Object
 */
public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70;

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		c.clickObjectType = c.objectX = c.objectId = c.objectY = 0;
		c.objectYOffset = c.objectXOffset = 0;
		c.getPA().resetFollow();
		switch (packetType) {

		case FIRST_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndianA();
			c.objectId = c.getInStream().readUnsignedWord();
			c.objectY = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;

			if (c.playerRights >= 3 && c.playerName.equalsIgnoreCase("Sanity")) {
				Misc.println("objectId: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: " + c.objectY
						+ " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			} else if (c.playerRights == 3) {
				c.sendMessage("objectId: " + c.objectId + " objectX: " + c.objectX + " objectY: " + c.objectY);
			}
			if (Math.abs(c.getX() - c.objectX) > 25 || Math.abs(c.getY() - c.objectY) > 25) {
				c.resetWalkingQueue();
				break;
			}
			if(c.goodDistance(c.getX(), c.getY(), c.objectX, c.objectY, 1)) {
				if (Doors.getSingleton().handleDoor(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
					return;
				}
			}
			if (c.agilityEmote)
				return;
			c.getAgil().appendObstacles(c.objectId);
			switch (c.objectId) {
			case 629:
			case 632:
			case 634:
			case 635:
			c.getThieving().steal(c.objectId);
			break;
			case 1276:
			case 1278:
			case 1281:
			case 1308:
			case 1307:
			case 1309:
			case 1306:
				c.getWoodcutting().cutWood(c.objectId);
				break;
			case 2090:
			case 2091:
			case 2092:
			case 2093:
			case 2094:
			case 2095:
			case 2096:
			case 2097:
			case 2098:
			case 2099:
			case 2100:
			case 2101:
			case 2102:
			case 2103:
			case 2104:
			case 2105:
			case 14859:
			case 14860:
				c.getMining().mineOres(c.objectId);
				break;
			case 2478:
			case 2479:
			case 2480:
			case 2481:
			case 2482:
			case 2483:
			case 2484:
			case 2485:
			case 2486:
			case 2487:
			case 2488:
			case 2489:
				c.getRunecrafting().craftRunes(c, c.objectId);
				break;
			case 2213:
				c.getPA().openUpBank();
				break;
			case 3044:
				Smelting.openInterface(c);
				break;
			case 8143:
				if (c.farm[0] > 0 && c.farm[1] > 0) {
					c.getFarming().pickHerb();
				}
			break;
			}
			break;

		case SECOND_CLICK:
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();
			c.objectY = c.getInStream().readSignedWordBigEndian();
			c.objectX = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;

			if (c.playerRights >= 3) {
				Misc.println("objectId: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: " + c.objectY
						+ " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			}

			switch (c.objectId) {
			}
			break;

		case THIRD_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndian();
			c.objectY = c.getInStream().readUnsignedWord();
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();

			if (c.playerRights >= 3) {
				Misc.println("objectId: " + c.objectId + "  ObjectX: " + c.objectX + "  objectY: " + c.objectY
						+ " Xoff: " + (c.getX() - c.objectX) + " Yoff: " + (c.getY() - c.objectY));
			}

			switch (c.objectId) {
			}
		}

	}

	public void handleSpecialCase(Client c, int id, int x, int y) {

	}

}
