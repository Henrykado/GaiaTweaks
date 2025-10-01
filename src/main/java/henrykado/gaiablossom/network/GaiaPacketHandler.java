package henrykado.gaiablossom.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import henrykado.gaiablossom.GaiaBlossom;
import henrykado.gaiablossom.common.item.ItemCloudPendant;
import henrykado.gaiablossom.common.item.ItemDodgeRing;

public class GaiaPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(GaiaBlossom.MODID);

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(SkeletonPacket.MessageHandler.class, SkeletonPacket.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(
            ItemCloudPendant.PacketJump.Handler.class,
            ItemCloudPendant.PacketJump.class,
            id++,
            Side.SERVER);
        INSTANCE.registerMessage(
            ItemDodgeRing.PacketDodge.Handler.class,
            ItemDodgeRing.PacketDodge.class,
            id++,
            Side.SERVER);
        // INSTANCE.registerMessage(GhastMessage.MessageHandler.class, GhastMessage.class, 0, Side.SERVER);
        // INSTANCE.registerMessage(GhastMessage.MessageHandler.class, GhastMessage.class, 1, Side.CLIENT);
    }
}
