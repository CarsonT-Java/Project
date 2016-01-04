package RS2.model.player.packets;

import RS2.Settings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.model.player.PacketType;
import RS2.util.Misc;

public class Commands implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		if (Settings.SERVER_DEBUG)
			Misc.println(c.playerName + " playerCommand: " + playerCommand);

		if (c.playerRights >= 0)
			playerCommands(c, playerCommand);
		if (c.playerRights >= 1)
			moderatorCommands(c, playerCommand);
		if (c.playerRights >= 2)
			administratorCommands(c, playerCommand);
		if (c.playerRights >= 3)
			ownerCommands(c, playerCommand);
	}

	public void playerCommands(Client c, String playerCommand) {
		Misc.println(c.playerName+" playerCommand: "+playerCommand);
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				GameEngine.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
				c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;       
		}
	}

	public void moderatorCommands(Client c, String playerCommand) {

	}

	public void administratorCommands(Client c, String playerCommand) {

		if (playerCommand.equalsIgnoreCase("mypos")) {
			c.sendMessage("X: " + c.absX);
			c.sendMessage("Y: " + c.absY);
		}

		if (playerCommand.startsWith("setlevel")) {

			try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}

		}

		if (playerCommand.startsWith("item")) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 20000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item 995 200 for example 200 gp");
				}
			} catch (Exception e) {

			}
		}
		

		if (playerCommand.startsWith("tele")) {
			String[] arg = playerCommand.split(" ");
			if (arg.length > 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
			else if (arg.length == 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), c.heightLevel);
		}

	}

	private void ownerCommands(Client c, String playerCommand) {

		if (playerCommand.startsWith("delete")) {
			   try {
			   BufferedWriter coord = new BufferedWriter(new FileWriter("./Data/ObjectsToRemove.txt", true));
			   String location = playerCommand.substring(6);
			   try {	
				coord.write("c.getPA().checkObjectSpawn(-1, "+c.absX+", "+c.absY+", -1, 10); //"+location);
				c.sendMessage("This spot has been recorded in ObjectsToRemove. ");
				coord.newLine();
				} finally {
				coord.close();
				}
				} catch (IOException e) {
			                e.printStackTrace();
				}
			}
		
		if (playerCommand.startsWith("add")) {
			   try {
			   BufferedWriter coord = new BufferedWriter(new FileWriter("./Data/ObjectsToAdd.txt", true));
				String[] arg = playerCommand.split(" ");
			   try {	
				coord.write("c.getPA().checkObjectSpawn(" +arg[1]+ ", "+c.absX+", "+c.absY+", -1, 10); //" +arg[2]);
				c.sendMessage("This spot has been recorded in ObjectsToAdd. ");
				coord.newLine();
				} finally {
				coord.close();
				}
				} catch (IOException e) {
			                e.printStackTrace();
				}
			}
		
		if (playerCommand.startsWith("object") && c.playerName.equalsIgnoreCase("carsont")) {
			String[] args = playerCommand.split(" ");
			c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
		}

		if (playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if (newNPC > 0) {
					GameEngine.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn an Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch (Exception e) {

			}
		}

	}

}
