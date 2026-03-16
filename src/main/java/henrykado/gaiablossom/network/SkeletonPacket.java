package henrykado.gaiablossom.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import henrykado.gaiablossom.common.entity.eep.SkeletonProperties;
import io.netty.buffer.ByteBuf;

public class SkeletonPacket implements IMessage {

    // A default constructor is always required
    public SkeletonPacket() {}

    int rangedAttackTime;
    int entityID;
    // boolean isAttacking;

    public SkeletonPacket(int entityID, int bowPull, boolean isAttacking) {
        this.rangedAttackTime = bowPull;
        this.entityID = entityID;
        // this.isAttacking = isAttacking;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Writes the int into the buf
        buf.writeInt(entityID);
        buf.writeInt(rangedAttackTime);
        // buf.writeBoolean(isAttacking);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf.
        entityID = buf.readInt();
        rangedAttackTime = buf.readInt();
        // isAttacking = buf.readBoolean();
    }

    public static class MessageHandler implements IMessageHandler<SkeletonPacket, IMessage> {

        @Override
        public IMessage onMessage(SkeletonPacket message, MessageContext ctx) {
            Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.entityID);
            if (entity != null) {
                SkeletonProperties data = SkeletonProperties.get(entity);
                if (data != null) {
                    // data.isAttacking = message.isAttacking;
                    data.rangedAttackTime = message.rangedAttackTime;
                }
            }

            // No response packet
            return null;
        }
    }
}
