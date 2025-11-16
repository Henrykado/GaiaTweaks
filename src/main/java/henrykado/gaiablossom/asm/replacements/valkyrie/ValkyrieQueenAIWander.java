//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package henrykado.gaiablossom.asm.replacements.valkyrie;

import net.minecraft.entity.ai.EntityAIWander;

public class ValkyrieQueenAIWander extends EntityAIWander {

    private NewEntityValkyrieQueen theQueen;

    public ValkyrieQueenAIWander(NewEntityValkyrieQueen creatureIn, double speedIn) {
        super(creatureIn, speedIn);
        this.theQueen = creatureIn;
    }

    public boolean shouldExecute() {
        return super.shouldExecute() && this.theQueen.isBossReady();
    }
}
