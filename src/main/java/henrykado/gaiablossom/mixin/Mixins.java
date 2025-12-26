package henrykado.gaiablossom.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.GaiaBlossom;

// Code adapted from GTNH Lib
public enum Mixins {

    CONSTANT_TEMPT(Phase.EARLY, Side.BOTH, "entity.MixinEntityAITempt"),
    MIXIN_PLAYER(Phase.EARLY, Side.BOTH, "entity.MixinEntityPlayer"),
    SKELETON_BETTERBOW(Phase.EARLY, Side.BOTH, "entity.MixinEntitySkeleton"),
    SKELETON_BETTERBOW_PACKET(Phase.EARLY, Side.BOTH, "entity.MixinEntityAIArrowAttack"),
    // EASIER_BLAZE(Phase.EARLY, Side.BOTH, "entity.MixinEntityBlaze"),
    MORE_ENDERMEN(Phase.EARLY, Side.BOTH, "MixinBiomeGenBase"),
    SWAMP_WITCH(Phase.EARLY, Side.BOTH, () -> Config.swampOnlyWitches, "MixinBiomeGenSwamp"),

    LESS_BEEF(Phase.EARLY, Side.BOTH, () -> Config.scarceMeat, "entity.MixinEntityCow"),
    LESS_EGGS(Phase.EARLY, Side.BOTH, () -> Config.scarceMeat, "entity.MixinEntityChicken"),
    LESS_CROPS(Phase.EARLY, Side.BOTH, () -> Config.slowerCropGrowth, "MixinBlockCrops"),
    FAST_FOODS(Phase.EARLY, Side.BOTH, "MixinItemFood"),
    WOLF_SKINS(Phase.EARLY, Side.CLIENT, "client.MixinRenderWolf"),
    CHANGE_MIN_SPRINT_HUNGER(Phase.EARLY, Side.CLIENT, "client.MixinEntityPlayerSP"),
    ITEMFRAME_BACKPORT(Phase.EARLY, Side.BOTH, "itemframe.MixinEntityItemFrame"),
    ITEMFRAME_BACKPORT2(Phase.EARLY, Side.CLIENT, "itemframe.MixinRenderItemFrame"),

    BOTANIA_ITEMFRAME_BACKPORT3(Phase.LATE, Side.BOTH, TargetedMod.BOTANIA, "MixinCorporeaFunnel"),

    AETHER_BAUBLES_INTEGRATION(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherBaubles,
        "aether.MixinInventoryAccessories"),
    // AETHER_HIGHER_WHALE(Phase.LATE, Side.BOTH, TargetedMod.AETHER, "aether.entity.MixinEntityAerwhale"),
    AETHER_AERBUNNY_TWEAKS(Phase.LATE, Side.BOTH, TargetedMod.AETHER, "aether.entity.MixinEntityAerbunny"),
    AETHER_ZEPHYR_YROTFIX(Phase.LATE, Side.BOTH, TargetedMod.AETHER, "aether.entity.MixinEntityZephyr"),
    AETHER_ZEPHYR_YROTFIX2(Phase.LATE, Side.CLIENT, TargetedMod.AETHER, "aether.entity.MixinZephyrModel"),
    AETHER_THEBETTERMIMIC(Phase.LATE, Side.BOTH, new TargetedMod[] { TargetedMod.AETHER, TargetedMod.PRIMITIVE_MOBS },
        () -> Config.aetherMasterToggle, "aether.MixinBlockMimicChest"),
    AETHER_SILENTVALKYRIE(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherMasterToggle,
        "aether.entity.MixinEntityValkyrie"),
    AETHER_SILENTVALKYRIEQUEEN(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherMasterToggle,
        "aether.entity.MixinEntityValkyrieQueen"),
    AETHER_ITEMTWEAKS(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherMasterToggle,
        "aether.MixinItemsAether"),
    AETHER_NO_MOA_EGGDROPS(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherMasterToggle,
        "aether.entity.MixinEntityMoa"),
    AETHER_RARER_AMBER(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherMasterToggle,
        "aether.MixinBlockAetherLog"),
    AETHER_LESS_ORES(Phase.LATE, Side.BOTH, TargetedMod.AETHER, () -> Config.aetherMasterToggle,
        "aether.MixinAetherBiomeDecorator"),

    AETHER_BRONZE_LOOT(Phase.LATE, Side.BOTH, new TargetedMod[] { TargetedMod.AETHER, TargetedMod.BOTANIA },
        () -> Config.tweakedAetherLoot, "aether.dun.MixinBronzeDungeon"),
    AETHER_SILVER_LOOT(Phase.LATE, Side.BOTH, new TargetedMod[] { TargetedMod.AETHER, TargetedMod.BOTANIA },
        () -> Config.tweakedAetherLoot, "aether.dun.MixinSilverDungeon"),
    // AETHER_GOLD_LOOT(Phase.LATE, Side.BOTH, TargetedMod.AETHER, "aether.dun.MixinGoldDungeon"),

    THAUMCRAFT_NODE_ORESPAWNING(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, () -> Config.nodeOreInfusion,
        "thaumcraft.MixinTileNode"),
    THAUMCRAFT_FIX_HEAD_GOGGLES(Phase.LATE, Side.CLIENT, TargetedMod.THAUMCRAFT, () -> Config.gogglesOfRevealingBauble,
        "thaumcraft.MixinRenderEventHandler"),
    THAUMCRAFT_FIX_HEAD_GOGGLES2(Phase.LATE, Side.CLIENT, TargetedMod.THAUMCRAFT, () -> Config.gogglesOfRevealingBauble,
        "thaumcraft.MixinTileNodeRenderer"),
    THAUMCRAFT_FIX_HEAD_GOGGLES3(Phase.LATE, Side.CLIENT, TargetedMod.THAUMCRAFT, () -> Config.gogglesOfRevealingBauble,
        "thaumcraft.MixinFXBeamPower"),
    THAUMCRAFT_GADOMANCY_FIX_HEAD_GOGGLES(Phase.LATE, Side.CLIENT,
        new TargetedMod[] { TargetedMod.THAUMCRAFT, TargetedMod.GADOMANCY }, () -> Config.gogglesOfRevealingBauble,
        "thaumcraft.MixinGadomancyNodeRenderer"),
    THAUMCRAFT_TAINTED_TREES(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinBiomeGenTaint"),
    THAUMCRAFT_TAINTWOOD_SPREAD(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinBlockTaintFibres"),
    THAUMCRAFT_GREATWOOD_BLACKLIST(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinWorldGenGreatwood"),
    THAUMCRAFT_ELEMENTAL_TRIBOW(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinThaumEntityHandler"),
    THAUMCRAFT_NOITEMFRAME_THAUMOMETER(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinThaumometer"),
    THAUMCRAFT_FIX_PEDESTAL(Phase.LATE, Side.CLIENT, new TargetedMod[] { TargetedMod.THAUMCRAFT, TargetedMod.ANGELICA },
        "thaumcraft.MixinTilePedestalRenderer"),
    THAUMCRAFT_FIX_ETF_NETHERBIOME_SPAWNS(Phase.LATE, Side.BOTH,
        new TargetedMod[] { TargetedMod.THAUMCRAFT, TargetedMod.ETFUTURUM }, "thaumcraft.MixinEntitySpawns"),
    THAUMCRAFT_SIMPLE_HARNESS_MODEL(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinModelHarness"),
    THAUMCRAFT_INFERNAL_STEEL(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinTileArcaneFurnace"),

    PMOBS_RAIN_ROCKETCREEPER(Phase.LATE, Side.BOTH, TargetedMod.PRIMITIVE_MOBS, "primitive.MixinRocketCreeper"),
    PMOBS_DIMENSION_BLACKLIST(Phase.LATE, Side.BOTH, TargetedMod.PRIMITIVE_MOBS, "primitive.MixinPMDimensionBlacklist"),
    PMOBS_DIMENSION_BLACKLIST2(Phase.LATE, Side.BOTH, TargetedMod.PRIMITIVE_MOBS,
        "primitive.MixinPMDimensionBlacklist2"),
    PMOBS_HIDEACHIEVEMENTS(Phase.LATE, Side.BOTH, TargetedMod.PRIMITIVE_MOBS, "primitive.MixinPrimitiveMobs"),
    PMOBS_HIDEACHIEVEMENTS2(Phase.LATE, Side.BOTH, TargetedMod.PRIMITIVE_MOBS, "primitive.MixinPMAchievements"),

    BATTLETOWERS_BALANCEDDROPS(Phase.LATE, Side.BOTH, TargetedMod.BATTLETOWERS, "MixinBTGolem"),
    BATTLETOWERS_REMOVEHELLTOWER(Phase.LATE, Side.BOTH, TargetedMod.BATTLETOWERS, "MixinWorldGenTower"),
    BATTLETOWERS_OVERWORLD_GEN_ONLY(Phase.LATE, Side.BOTH, TargetedMod.BATTLETOWERS, "MixinWorldGenBTHandler"),
    ETFUTURUM_SHEARABLE_NETHER_ROOTS(Phase.LATE, Side.BOTH, TargetedMod.ETFUTURUM, "MixinNetherRoots"),
    DIMDOORS_OVERWORLD_ONLY(Phase.LATE, Side.BOTH, TargetedMod.DIMDOORS, "MixinDMGateway"),
    HAMMERZ_REMOVETORCHTHINGY(Phase.LATE, Side.BOTH, TargetedMod.HAMMERZ, () -> Config.removeHammerzTorchPlacing,
        "MixinHammer");

    private final List<String> mixinClasses;
    private final Phase phase;
    private final Side side;
    private List<TargetedMod> targetedMods;
    private Supplier<Boolean> applyIf = () -> true;

    Mixins(Phase phase, Side side, TargetedMod targetedMod, String... mixinClasses) {
        this(phase, side, mixinClasses);
        this.targetedMods = Collections.singletonList(targetedMod);
    }

    Mixins(Phase phase, Side side, TargetedMod[] targetedMods, String... mixinClasses) {
        this(phase, side, mixinClasses);
        this.targetedMods = Arrays.asList(targetedMods);
    }

    Mixins(Phase phase, Side side, TargetedMod targetedMod, Supplier<Boolean> applyIf, String... mixinClasses) {
        this(phase, side, targetedMod, mixinClasses);
        this.applyIf = applyIf;
    }

    Mixins(Phase phase, Side side, TargetedMod[] targetedMods, Supplier<Boolean> applyIf, String... mixinClasses) {
        this(phase, side, targetedMods, mixinClasses);
        this.applyIf = applyIf;
    }

    Mixins(Phase phase, Side side, String... mixinClasses) {
        this.phase = phase;
        this.side = side;
        this.mixinClasses = mixinClassesArray2List(mixinClasses);
    }

    Mixins(Phase phase, Side side, Supplier<Boolean> applyIf, String... mixinClasses) {
        this(phase, side, mixinClasses);
        this.applyIf = applyIf;
    }

    private List<String> mixinClassesArray2List(String[] mixinClasses) {
        if (phase.equals(Phase.EARLY)) {
            for (int i = 0; i < mixinClasses.length; i++) {
                mixinClasses[i] = "early." + mixinClasses[i];
            }
        } else {
            for (int i = 0; i < mixinClasses.length; i++) {
                mixinClasses[i] = "late." + mixinClasses[i];
            }
        }

        return Arrays.asList(mixinClasses);
    }

    public static List<String> getEarlyMixins(Set<String> loadedCoreMods) {
        final List<String> mixins = new ArrayList<>();
        final List<String> notLoading = new ArrayList<>();
        for (Mixins mixin : values()) {
            if (mixin.phase == Phase.EARLY) {
                if (mixin.shouldLoad(loadedCoreMods, Collections.emptySet())) {
                    mixins.addAll(mixin.mixinClasses);
                } else {
                    notLoading.addAll(mixin.mixinClasses);
                }
            }
        }
        GaiaBlossom.LOG.info("Not loading the following EARLY mixins: {}", notLoading);
        return mixins;
    }

    public static List<String> getLateMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        final List<String> notLoading = new ArrayList<>();
        for (Mixins mixin : values()) {
            if (mixin.phase == Phase.LATE) {
                if (mixin.shouldLoad(Collections.emptySet(), loadedMods)) {
                    mixins.addAll(mixin.mixinClasses);
                } else {
                    notLoading.addAll(mixin.mixinClasses);
                }
            }
        }
        GaiaBlossom.LOG.info("Not loading the following LATE mixins: {}", notLoading.toString());
        return mixins;
    }

    boolean shouldLoadSide() {
        return side == Side.BOTH || (side == Side.SERVER && FMLLaunchHandler.side()
            .isServer())
            || (side == Side.CLIENT && FMLLaunchHandler.side()
                .isClient());
    }

    boolean allModsLoaded(List<TargetedMod> targetedMods, Set<String> loadedCoreMods, Set<String> loadedMods) {
        if (targetedMods == null) return true;

        for (TargetedMod target : targetedMods) {
            // Check coremod first
            if (!loadedCoreMods.isEmpty() && target.coreModClass != null
                && !loadedCoreMods.contains(target.coreModClass)) return false;
            else if (!loadedMods.isEmpty() && target.modId != null && !loadedMods.contains(target.modId)) return false;
        }

        return true;
    }

    boolean shouldLoad(Set<String> loadedCoreMods, Set<String> loadedMods) {
        return (shouldLoadSide() && applyIf.get() && allModsLoaded(targetedMods, loadedCoreMods, loadedMods));
    }

    enum Side {
        BOTH,
        CLIENT,
        SERVER
    }

    enum Phase {
        EARLY,
        LATE,
    }

    public enum TargetedMod {

        AETHER("aether_legacy"),
        TWILIGHTFOREST("TwilightForest"),
        BOTANIA("Botania"),
        BAUBLES("Baubles"),
        HAMMERZ("hammerz"),
        BATTLETOWERS("BattleTowers"),
        PRIMITIVE_MOBS("primitivemobs"),
        ANGELICA("angelica"),
        ETFUTURUM("etfuturum"),
        DIMDOORS("dimdoors"),
        GADOMANCY("gadomancy"),
        THAUMCRAFT("Thaumcraft"); // "thaumcraft.codechicken.core.launch.DepLoader"

        public final String coreModClass;
        public final String modId;

        TargetedMod(String modId) {
            this(null, modId);
        }

        TargetedMod(String coreModClass, String modId) {
            this.coreModClass = coreModClass;
            this.modId = modId;
        }
    }
}
