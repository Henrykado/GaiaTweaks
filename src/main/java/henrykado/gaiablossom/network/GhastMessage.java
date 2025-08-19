package henrykado.gaiablossom.network;

import net.minecraft.entity.monster.EntityGhast;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class GhastMessage implements IMessage {

    // A default constructor is always required
    public GhastMessage() {}

    private int toSend;
    private int toSend2;
    private int entityID;

    public GhastMessage(int entityID, int attackCounter, int prevAttackCounter) {
        this.entityID = entityID;
        this.toSend = attackCounter;
        this.toSend2 = prevAttackCounter;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Writes the int into the buf
        buf.writeInt(entityID);
        buf.writeInt(toSend);
        buf.writeInt(toSend2);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf.
        entityID = buf.readInt();
        toSend = buf.readInt();
        toSend2 = buf.readInt();
    }

    public static class MessageHandler implements IMessageHandler<GhastMessage, IMessage> {

        @Override
        public IMessage onMessage(GhastMessage message, MessageContext ctx) {
            // This is the player the packet was sent to the server from
            EntityGhast ghast = (EntityGhast) ctx.getServerHandler().playerEntity.worldObj
                .getEntityByID(message.entityID);
            ghast.attackCounter = message.toSend;
            ghast.prevAttackCounter = message.toSend2;
            // No response packet
            return null;
        }
    }
}
