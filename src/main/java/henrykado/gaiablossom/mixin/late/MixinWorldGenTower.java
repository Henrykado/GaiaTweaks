package henrykado.gaiablossom.mixin.late;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import atomicstryker.battletowers.common.AS_BattleTowersCore;
import atomicstryker.battletowers.common.AS_EntityGolem;
import atomicstryker.battletowers.common.AS_WorldGenTower;
import atomicstryker.battletowers.common.TowerStageItemManager;
import atomicstryker.battletowers.common.WorldGenHandler;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.common.block.ModBlock;
import henrykado.gaiablossom.common.block.tile.TileEntityMobSpawnerTower;
import henrykado.gaiablossom.util.TowerTypes;

@Mixin(AS_WorldGenTower.class)
public class MixinWorldGenTower {

    @Redirect(
        method = "getChosenTowerOrdinal",
        at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0),
        remap = false)
    public int nextIntRedirect(Random instance, int i) {
        return Config.removeNetherBattleTower ? 1 : i; // if statement checks if rand(10) == 0, this makes the result
                                                       // always one, removing nether towers
    }

    /*
     * @Redirect(
     * method = "generate",
     * at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIILnet/minecraft/block/Block;II)Z"),
     * remap = false)
     * public boolean replaceMobSpawner(World instance, int x, int y, int z, Block block, int md, int flags) {
     * return instance.setBlock(x, y, z, ModBlock.blockTowerSpawner, md, flags);
     * }
     */

    @Shadow
    private void buildWallPiece(World world, int iCurrent, int jCurrent, int zCurrent, Block towerWallBlockID,
        int floor, int floorIterator) {};

    @Shadow
    private void buildFloorPiece(World world, int iCurrent, int jCurrent, int zCurrent, Block towerFloorBlockID,
        int towerFloorMeta) {}

    @Shadow
    private String getMobType(Random random) {
        switch (random.nextInt(4)) {
            case 0:
                return "Skeleton";
            case 1:
                return "Zombie";
            case 2:
                return "Spider";
            case 3:
                return "CaveSpider";
            default:
                return "Zombie";
        }
    }

    /**
     * @author Henrykado
     * @reason other methods werent working
     */
    @Overwrite(remap = false)
    public void generate(World world, int ix, int jy, int kz, int towerchoice, boolean underground) {
        TowerTypes towerChosen = TowerTypes.values()[towerchoice];
        Block towerWallBlockID = towerChosen.getWallBlockID();
        Block towerLightBlockID = towerChosen.getLightBlockID();
        Block towerFloorBlockID = towerChosen.getFloorBlockID();
        int towerFloorMeta = towerChosen.getFloorBlockMetaData();
        int startingHeight = underground ? Math.max(jy - 70, 15) : jy - 6;
        int maximumHeight = underground ? jy + 7 : 120;
        int floor = 1;
        boolean topFloor = false;

        for (int builderHeight = startingHeight; builderHeight < maximumHeight; builderHeight += 7) {
            if (builderHeight + 7 >= maximumHeight) {
                topFloor = true;
            }

            int floorIterator;
            int xIterator;
            int k5;
            int iCurrent;
            for (floorIterator = 0; floorIterator < 7; ++floorIterator) {
                if (floor == 1 && floorIterator < 4) {
                    floorIterator = 4;
                }

                for (xIterator = -7; xIterator < 7; ++xIterator) {
                    for (k5 = -7; k5 < 7; ++k5) {
                        iCurrent = xIterator + ix;
                        int jCurrent = floorIterator + builderHeight;
                        int zCurrent = k5 + kz;
                        if (k5 == -7) {
                            if (xIterator > -5 && xIterator < 4) {
                                this.buildWallPiece(
                                    world,
                                    iCurrent,
                                    jCurrent,
                                    zCurrent,
                                    towerWallBlockID,
                                    floor,
                                    floorIterator);
                            }
                        } else if (k5 != -6 && k5 != -5) {
                            if (k5 != -4 && k5 != -3 && k5 != 2 && k5 != 3) {
                                if (k5 > -3 && k5 < 2) {
                                    if (xIterator != -7 && xIterator != 6) {
                                        if (xIterator > -7 && xIterator < 6) {
                                            if (floorIterator == 5) {
                                                this.buildFloorPiece(
                                                    world,
                                                    iCurrent,
                                                    jCurrent,
                                                    zCurrent,
                                                    towerFloorBlockID,
                                                    towerFloorMeta);
                                            } else {
                                                world.setBlock(iCurrent, jCurrent, zCurrent, Blocks.air, 0, 3);
                                            }
                                        }
                                    } else if (floorIterator >= 0 && floorIterator <= 3
                                        && (xIterator == -7 || xIterator == 6)
                                        && !underground
                                        && (k5 == -1 || k5 == 0)) {
                                            world.setBlock(iCurrent, jCurrent, zCurrent, Blocks.air, 0, 3);
                                        } else {
                                            this.buildWallPiece(
                                                world,
                                                iCurrent,
                                                jCurrent,
                                                zCurrent,
                                                towerWallBlockID,
                                                floor,
                                                floorIterator);
                                        }
                                } else if (k5 == 4) {
                                    if (xIterator != -5 && xIterator != 4) {
                                        if (xIterator > -5 && xIterator < 4) {
                                            if (floorIterator == 5) {
                                                this.buildFloorPiece(
                                                    world,
                                                    iCurrent,
                                                    jCurrent,
                                                    zCurrent,
                                                    towerFloorBlockID,
                                                    towerFloorMeta);
                                            } else {
                                                world.setBlock(iCurrent, jCurrent, zCurrent, Blocks.air, 0, 3);
                                            }
                                        }
                                    } else {
                                        this.buildWallPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerWallBlockID,
                                            floor,
                                            floorIterator);
                                    }
                                } else if (k5 == 5) {
                                    if (xIterator != -4 && xIterator != -3 && xIterator != 2 && xIterator != 3) {
                                        if (xIterator > -3 && xIterator < 2) {
                                            if (floorIterator == 5) {
                                                this.buildFloorPiece(
                                                    world,
                                                    iCurrent,
                                                    jCurrent,
                                                    zCurrent,
                                                    towerFloorBlockID,
                                                    towerFloorMeta);
                                            } else {
                                                this.buildWallPiece(
                                                    world,
                                                    iCurrent,
                                                    jCurrent,
                                                    zCurrent,
                                                    towerWallBlockID,
                                                    floor,
                                                    floorIterator);
                                            }
                                        }
                                    } else {
                                        this.buildWallPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerWallBlockID,
                                            floor,
                                            floorIterator);
                                    }
                                } else if (k5 == 6 && xIterator > -3 && xIterator < 2) {
                                    if (floorIterator >= 0 && floorIterator <= 3
                                        && (xIterator == -1 || xIterator == 0)) {
                                        this.buildWallPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerWallBlockID,
                                            floor,
                                            floorIterator);
                                    } else {
                                        this.buildWallPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerWallBlockID,
                                            floor,
                                            floorIterator);
                                    }
                                }
                            } else if (xIterator != -6 && xIterator != 5) {
                                if (xIterator > -6 && xIterator < 5) {
                                    if (floorIterator == 5) {
                                        this.buildFloorPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerFloorBlockID,
                                            towerFloorMeta);
                                    } else if (world.getBlock(iCurrent, jCurrent, zCurrent) != Blocks.chest) {
                                        world.setBlock(iCurrent, jCurrent, zCurrent, Blocks.air, 0, 3);
                                    }
                                }
                            } else {
                                this.buildWallPiece(
                                    world,
                                    iCurrent,
                                    jCurrent,
                                    zCurrent,
                                    towerWallBlockID,
                                    floor,
                                    floorIterator);
                            }
                        } else if (xIterator != -5 && xIterator != 4) {
                            if (k5 == -6) {
                                if (xIterator != (floorIterator + 1) % 7 - 3) {
                                    if (xIterator < 4 && xIterator > -5) {
                                        world.setBlock(iCurrent, jCurrent, zCurrent, Blocks.air, 0, 3);
                                    }
                                } else {
                                    if (!underground || floor != 1) {
                                        world.setBlock(
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerChosen.getStairBlockID(),
                                            0,
                                            3);
                                    }

                                    if (floorIterator == 5) {
                                        world.setBlock(iCurrent - 7, jCurrent, zCurrent, towerFloorBlockID, 0, 3);
                                    }

                                    if (floorIterator == 6 && topFloor) {
                                        this.buildWallPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerWallBlockID,
                                            floor,
                                            floorIterator);
                                    }
                                }
                            } else if (k5 == -5 && xIterator > -5 && xIterator < 5) {
                                if (floorIterator != 0 && floorIterator != 6 || xIterator != -4 && xIterator != 3) {
                                    if (floorIterator != 5 || xIterator != 3 && xIterator != -4) {
                                        this.buildWallPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerWallBlockID,
                                            floor,
                                            floorIterator);
                                    } else {
                                        this.buildFloorPiece(
                                            world,
                                            iCurrent,
                                            jCurrent,
                                            zCurrent,
                                            towerFloorBlockID,
                                            towerFloorMeta);
                                    }
                                } else {
                                    world.setBlock(iCurrent, jCurrent, zCurrent, Blocks.air, 0, 3);
                                }
                            }
                        } else {
                            this.buildWallPiece(
                                world,
                                iCurrent,
                                jCurrent,
                                zCurrent,
                                towerWallBlockID,
                                floor,
                                floorIterator);
                        }
                    }
                }
            }

            if (floor == 2) {
                world.setBlock(ix + 3, builderHeight, kz - 5, towerWallBlockID, 0, 3);
                world.setBlock(ix + 3, builderHeight - 1, kz - 5, towerWallBlockID, 0, 3);
            }

            TileEntity tileentitymobspawner;
            if (!underground && topFloor || underground && floor == 1) {
                if (towerChosen != TowerTypes.Null) {
                    AS_EntityGolem entitygolem = new AS_EntityGolem(world, towerChosen.ordinal());
                    entitygolem.setLocationAndAngles(
                        (double) ix + 0.5,
                        (double) (builderHeight + 6),
                        (double) kz + 0.5,
                        world.rand.nextFloat() * 360.0F,
                        0.0F);
                    world.spawnEntityInWorld(entitygolem);
                }
            } else if (towerChosen != TowerTypes.Null) {
                world.setBlock(
                    ix + 2,
                    builderHeight + 6,
                    kz + 2,
                    Config.betterBattleTowerSpawner ? ModBlock.blockTowerSpawner : Blocks.mob_spawner,
                    0,
                    3);
                tileentitymobspawner = world.getTileEntity(ix + 2, builderHeight + 6, kz + 2);
                if (tileentitymobspawner != null) {
                    if (Config.betterBattleTowerSpawner) {
                        ((TileEntityMobSpawnerTower) tileentitymobspawner).getMobSpawnerLogic()
                            .setEntityName(this.getMobType(world.rand));
                    } else {
                        ((TileEntityMobSpawner) tileentitymobspawner).func_145881_a()
                            .setEntityName(this.getMobType(world.rand));
                    }
                }

                world.setBlock(
                    ix - 3,
                    builderHeight + 6,
                    kz + 2,
                    Config.betterBattleTowerSpawner ? ModBlock.blockTowerSpawner : Blocks.mob_spawner,
                    0,
                    3);
                tileentitymobspawner = world.getTileEntity(ix - 3, builderHeight + 6, kz + 2);
                if (tileentitymobspawner != null) {
                    if (Config.betterBattleTowerSpawner) {
                        ((TileEntityMobSpawnerTower) tileentitymobspawner).getMobSpawnerLogic()
                            .setEntityName(this.getMobType(world.rand));
                    } else {
                        ((TileEntityMobSpawner) tileentitymobspawner).func_145881_a()
                            .setEntityName(this.getMobType(world.rand));
                    }
                }
            } else {
                world.setBlock(ix + 2, builderHeight + 6, kz + 2, Blocks.air, 0, 3);
                world.setBlock(ix - 3, builderHeight + 6, kz + 2, Blocks.air, 0, 3);
            }

            world.setBlock(ix, builderHeight + 6, kz + 3, towerFloorBlockID, 0, 3);
            world.setBlock(ix - 1, builderHeight + 6, kz + 3, towerFloorBlockID, 0, 3);
            if (builderHeight + 56 >= 120 && floor == 1) {
                floor = 2;
            }

            if (towerChosen == TowerTypes.Null) {
                for (floorIterator = 0; floorIterator < 2; ++floorIterator) {
                    world.setBlock(ix - floorIterator, builderHeight + 7, kz + 3, Blocks.air, 2, 3);
                }
            } else {
                tileentitymobspawner = null;
                TowerStageItemManager floorChestManager;
                if (!underground) {
                    floorChestManager = topFloor ? WorldGenHandler.getTowerStageManagerForFloor(10, world.rand)
                        : WorldGenHandler.getTowerStageManagerForFloor(floor, world.rand);
                } else {
                    floorChestManager = floor == 1 ? WorldGenHandler.getTowerStageManagerForFloor(10, world.rand)
                        : WorldGenHandler.getTowerStageManagerForFloor(Math.abs(11 - floor), world.rand);
                }

                for (xIterator = 0; xIterator < 2; ++xIterator) {
                    world.setBlock(ix - xIterator, builderHeight + 7, kz + 3, Blocks.chest, 2, 3);

                    for (k5 = 0; k5 < (underground ? AS_BattleTowersCore.instance.itemGenerateAttemptsPerFloor * 2
                        : AS_BattleTowersCore.instance.itemGenerateAttemptsPerFloor); ++k5) {
                        ItemStack itemstack = floorChestManager.getStageItem(world.rand);
                        if (itemstack != null) {
                            TileEntityChest tileentitychest = (TileEntityChest) world
                                .getTileEntity(ix - xIterator, builderHeight + 7, kz + 3);
                            if (tileentitychest != null) {
                                tileentitychest.setInventorySlotContents(
                                    world.rand.nextInt(tileentitychest.getSizeInventory()),
                                    itemstack);
                            }
                        }
                    }
                }
            }

            world.setBlock(ix + 3, builderHeight + 2, kz - 6, towerLightBlockID, 0, 3);
            world.setBlock(ix - 4, builderHeight + 2, kz - 6, towerLightBlockID, 0, 3);
            world.setBlock(ix + 1, builderHeight + 2, kz - 4, towerLightBlockID, 0, 3);
            world.setBlock(ix - 2, builderHeight + 2, kz - 4, towerLightBlockID, 0, 3);
            if (towerChosen != TowerTypes.Null) {
                for (floorIterator = 0; floorIterator < floor * 4 + towerChosen.ordinal() - 8
                    && !topFloor; ++floorIterator) {
                    xIterator = 5 - world.rand.nextInt(12);
                    k5 = builderHeight + 5;
                    iCurrent = 5 - world.rand.nextInt(10);
                    if (iCurrent >= -2 || xIterator >= 4 || xIterator <= -5 || xIterator == 1 || xIterator == -2) {
                        xIterator += ix;
                        iCurrent += kz;
                        if (world.getBlock(xIterator, k5, iCurrent) == towerFloorBlockID && world
                            .getBlock(xIterator, k5 + 1, iCurrent)
                            != (Config.betterBattleTowerSpawner ? ModBlock.blockTowerSpawner : Blocks.mob_spawner)) {
                            world.setBlock(xIterator, k5, iCurrent, Blocks.air, 0, 3);
                        }
                    }
                }
            }

            ++floor;
        }

        System.out.println(
            "Battle Tower type " + towerChosen
                + " spawned at [ "
                + ix
                + " | "
                + kz
                + " ], underground: "
                + underground);
    }
}
