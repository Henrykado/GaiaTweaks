package henrykado.gaiablossom.event;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.OreGenEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import henrykado.gaiablossom.quark.Quark;
import henrykado.gaiablossom.quark.world.UndergroundBiomeGenerator;

public class WorldEventHandler {

    @SubscribeEvent
    public void onOreGenerate(OreGenEvent.GenerateMinable event) {
        if (event.type == OreGenEvent.GenerateMinable.EventType.DIRT) {
            World world = event.world;
            int chunkX = event.worldX;
            int chunkZ = event.worldZ;

            Chunk chunk = world.getChunkFromBlockCoords(chunkX, chunkZ);

            for (UndergroundBiomeGenerator gen : Quark.undergroundBiomes) {
                gen.generate(chunk.xPosition, chunk.zPosition, world);
            }
        }
    }

    /*
     * @SubscribeEvent
     * public void prePlantGrowth(PlantGrowthEvent.AllowGrowthTick event) {
     * if (
     *//* !event.world.isDaytime() || *//*
                                         * event.world.rand.nextInt(2) == 0) {
                                         * event.setResult(Event.Result.DENY);
                                         * }
                                         * }
                                         */
}
