package henrykado.gaiablossom.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import henrykado.gaiablossom.GaiaBlossom;

// Code adapted from GTNH Lib
public enum Mixins {

    CONSTANT_TEMPT(Phase.EARLY, Side.BOTH, "MixinEntityAITempt"),
    MIXIN_PLAYER(Phase.EARLY, Side.BOTH, "MixinEntityPlayer"),
    SKELETON_BETTERBOW(Phase.EARLY, Side.BOTH, "MixinEntitySkeleton"),
    SKELETON_BETTERBOW_PACKET(Phase.EARLY, Side.BOTH, "MixinEntityAIArrowAttack"),
    EASIER_BLAZE(Phase.EARLY, Side.BOTH, "MixinEntityBlaze"),
    MOB_SPAWN_TWEAKS(Phase.EARLY, Side.BOTH, "MixinBiomeGenBase"),
    LESS_BEEF(Phase.EARLY, Side.BOTH, "MixinEntityCow"),
    SWAMP_WITCHES(Phase.EARLY, Side.BOTH, "MixinBiomeGenSwamp"),

    WOLF_SKINS(Phase.EARLY, Side.CLIENT, "MixinRenderWolf"),
    CHANGE_MIN_SPRINT_HUNGER(Phase.EARLY, Side.CLIENT, "MixinEntityPlayerSP"),

    AETHER_BAUBLES_INTEGRATION(Phase.LATE, Side.BOTH, TargetedMod.AETHER, "aether.MixinInventoryAccessories"),

    THAUMCRAFT_NODE_ORESPAWNING(Phase.LATE, Side.BOTH, TargetedMod.THAUMCRAFT, "thaumcraft.MixinTileNode"),
    THAUMCRAFT_FIX_HEAD_GOGGLES(Phase.LATE, Side.CLIENT, TargetedMod.THAUMCRAFT, "thaumcraft.MixinRenderEventHandler"),
    THAUMCRAFT_FIX_HEAD_GOGGLES2(Phase.LATE, Side.CLIENT, TargetedMod.THAUMCRAFT, "thaumcraft.MixinTileNodeRenderer"),

    /*
     * BOTANIA_BETTER_ENCHANTER_MULTIBLOCK(Phase.LATE, Side.CLIENT, TargetedMod.THAUMCRAFT,
     * "thaumcraft.MixinTileNodeRenderer"),
     */
    // BOTANIA_NEWACHIEVEMENT(Phase.LATE, Side.BOTH, TargetedMod.BOTANIA, "botania.MixinAchievements"),
    BOTANIA_MODTWEAKER_FIX(Phase.LATE, Side.CLIENT, TargetedMod.BOTANIA, "botania.MixinLexiconEntry"),
    BOTANIA_REDSTONE_HOURGLASS(Phase.LATE, Side.BOTH, TargetedMod.BOTANIA, "botania.MixinTileHourglass"),

    TWILIGHTF_NEW_DEER_MODEL(Phase.LATE, Side.CLIENT, TargetedMod.TWILIGHTFOREST, "twilightforest.MixinRenderTFDeer"),

    HAMMERZ_REMOVETORCHTHINGY(Phase.LATE, Side.BOTH, TargetedMod.HAMMERZ, "MixinHammer");

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

    private List<String> mixinClassesArray2List(String[] mixinClasses) {
        if (phase.equals(Phase.EARLY)) {
            for (int i = 0; i < mixinClasses.length; i++) {
                if (side.equals(Side.CLIENT)) {
                    mixinClasses[i] = "client." + mixinClasses[i];
                }
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
        BOTANIA("botania"),
        BAUBLES("Baubles"),
        HAMMERZ("hammerz"),
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
