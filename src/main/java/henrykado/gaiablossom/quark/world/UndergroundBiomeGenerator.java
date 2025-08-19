package henrykado.gaiablossom.quark.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import henrykado.gaiablossom.util.BlockPos;

public class UndergroundBiomeGenerator extends MultiChunkFeatureGenerator {

    public final UndergroundBiome biome;
    public final List<BiomeDictionary.Type> types;
    public final int rarity;
    public final int minXSize, minYSize, minZSize;
    public final int xVariation, yVariation, zVariation;
    public final int minY, maxY;

    private final long seedXor;

    public UndergroundBiomeGenerator(UndergroundBiome biome, int rarity, int minXSize, int minYSize, int minZSize,
        int xVariation, int yVariation, int zVariation, int minY, int maxY, BiomeDictionary.Type... types) {
        this.biome = biome;
        this.types = Arrays.asList(types);
        this.rarity = rarity;
        this.minXSize = minXSize;
        this.minYSize = minYSize;
        this.minZSize = minZSize;
        this.xVariation = xVariation;
        this.yVariation = yVariation;
        this.zVariation = zVariation;
        this.minY = minY;
        this.maxY = maxY;

        seedXor = biome.getClass()
            .toString()
            .hashCode();
    }

    @Override
    public int getFeatureRadius() {
        return (int) Math.ceil(Math.max(minXSize + xVariation, minZSize + zVariation));
    }

    @Override
    public void generateChunkPart(BlockPos src, Random random, int chunkX, int chunkZ, World world) {
        int radiusX = minXSize + random.nextInt(xVariation);
        int radiusY = minYSize + random.nextInt(yVariation);
        int radiusZ = minZSize + random.nextInt(zVariation);
        apply(world, src.x, src.y, src.z, random, chunkX, chunkZ, radiusX, radiusY, radiusZ);
    }

    @Override
    public BlockPos[] getSourcesInChunk(Random random, int chunkX, int chunkZ) {
        if (rarity > 0 && random.nextInt(rarity) == 0) {
            return new BlockPos[] { new BlockPos(
                chunkX * 16 + random.nextInt(16),
                minY + random.nextInt(maxY - minY),
                chunkZ * 16 + random.nextInt(16)) };
        }

        return new BlockPos[0];
    }

    @Override
    public long modifyWorldSeed(long seed) {
        return seed ^ seedXor;
    }

    @Override
    public boolean isSourceValid(World world, BlockPos pos) {
        BiomeGenBase biomeGen = world.getBiomeGenForCoords((int) pos.x, (int) pos.z);
        return biomeTypeIntersectCheck(types, biomeGen) && biome.isValidBiome(biomeGen);
    }

    public boolean biomeTypeIntersectCheck(List<BiomeDictionary.Type> typesArray, BiomeGenBase b) {
        BiomeDictionary.Type[] currentBiomesTypes = BiomeDictionary.getTypesForBiome(b);

        // i is 0, biomeItr is ArrayList, and b is BiomeGenDesert
        for (BiomeDictionary.Type type : typesArray) {
            {
                for (BiomeDictionary.Type currentType : currentBiomesTypes) if (currentType.equals(type)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void apply(World world, int centerX, int centerY, int centerZ, Random random, int chunkX, int chunkZ,
        int radiusX, int radiusY, int radiusZ) {
        double radiusX2 = radiusX * radiusX;
        double radiusY2 = radiusY * radiusY;
        double radiusZ2 = radiusZ * radiusZ;

        UndergroundBiomeGenerationContext context = new UndergroundBiomeGenerationContext();

        forEachChunkBlock(chunkX, chunkZ, centerY - radiusY, centerY + radiusY, (pos) -> {
            int x = (int) (pos.x - centerX);
            int y = (int) (pos.y - centerY);
            int z = (int) (pos.z - centerZ);

            double distX = x * x;
            double distY = y * y;
            double distZ = z * z;
            boolean inside = distX / radiusX2 + distY / radiusY2 + distZ / radiusZ2 <= 1;

            if (inside) biome.fill(world, centerX + x, centerY + y, centerZ + z, context);
        });

        context.floorList.forEach(pos -> biome.finalFloorPass(world, pos.x, pos.y, pos.z));
        context.ceilingList.forEach(pos -> biome.finalCeilingPass(world, pos.x, pos.y, pos.z));
        context.wallMap.keySet()
            .forEach(pos -> biome.finalWallPass(world, pos.x, pos.y, pos.z));
        context.insideList.forEach(pos -> biome.finalInsidePass(world, pos.x, pos.y, pos.z));

        if (biome.hasDungeon() && world instanceof WorldServer && random.nextDouble() < biome.dungeonChance) {
            List<BlockPos> candidates = new ArrayList<>(context.wallMap.keySet());
            candidates.removeIf(pos -> {
                Block block = world.getBlock((int) pos.x, (int) pos.y - 1, (int) pos.z);
                BlockPos newPos = pos.down();
                return biome.isWall(world, newPos.x, newPos.y, newPos.z, block)
                    || block.isAir(world, (int) pos.x, (int) pos.y - 1, (int) pos.z);
            });

            if (!candidates.isEmpty()) {
                BlockPos pos = candidates.get(world.rand.nextInt(candidates.size()));

                EnumFacing border = context.wallMap.get(pos);
                if (border != null) biome.spawnDungeon((WorldServer) world, pos, border);
            }
        }
    }

    public static class UndergroundBiomeGenerationContext {

        public final List<BlockPos> floorList = new LinkedList<>();
        public final List<BlockPos> ceilingList = new LinkedList<>();
        public final List<BlockPos> insideList = new LinkedList<>();

        public final Map<BlockPos, EnumFacing> wallMap = new HashMap<>();

    }
}
