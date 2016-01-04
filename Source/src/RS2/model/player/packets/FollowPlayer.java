package RS2.model.player.packets;
 
import RS2.GameEngine;
import RS2.model.player.Client;
import RS2.model.player.PacketType;
 
public class FollowPlayer implements PacketType {
       
        @Override
        public void processPacket(Client c, int packetType, int packetSize) {
                int followPlayer = c.getInStream().readUnsignedWordBigEndian();
                if(GameEngine.playerHandler.players[followPlayer] == null) {
                        return;
                }
                c.playerIndex = 0;
                c.npcIndex = 0;
                c.mageFollow = false;
                c.usingBow = false;
                c.usingRangeWeapon = false;
                c.followDistance = 1;
                c.followId = followPlayer;
        }      
}