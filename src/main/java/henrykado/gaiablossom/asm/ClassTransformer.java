package henrykado.gaiablossom.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;

import henrykado.gaiablossom.asm.replacements.BaubleItemAccessory;
import henrykado.gaiablossom.asm.replacements.BaubleItemAccessoryDyed;
import henrykado.gaiablossom.asm.replacements.BaubleItemGoggles;
import henrykado.gaiablossom.asm.replacements.valkyrie.NewEntityValkyrieQueen;
import scala.tools.asm.Opcodes;
import thaumcraft.common.items.armor.ItemGoggles;

public class ClassTransformer implements IClassTransformer {

    static Logger LOGGER = LogManager.getLogger("gaiablossom");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        switch (transformedName) {
            case "net.minecraft.inventory.ContainerRepair$2" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                MethodNode method = classNode.methods.get(2);
                for (AbstractInsnNode node : method.instructions.toArray()) {
                    if (node.getOpcode() == Opcodes.IFLE) {
                        ((JumpInsnNode) node).setOpcode(Opcodes.IFLT);
                        break;
                    }
                }

                return writeClass(classNode);
            }
            case "com.gildedgames.the_aether.items.ItemsAether" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (MethodNode method : classNode.methods) {
                    if (method.name.equals("initialization")) {
                        for (AbstractInsnNode node : method.instructions.toArray()) {
                            tryReplaceInstance(
                                node,
                                "com/gildedgames/the_aether/items/accessories/ItemAccessory",
                                Type.getInternalName(BaubleItemAccessory.class));
                            tryReplaceInstance(
                                node,
                                "com/gildedgames/the_aether/items/accessories/ItemAccessoryDyed",
                                Type.getInternalName(BaubleItemAccessoryDyed.class));
                        }
                        break;
                    }
                }

                return writeClass(classNode);
            }
            case "com.gildedgames.the_aether.entities.EntitiesAether" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (MethodNode method : classNode.methods) {
                    if (method.name.equals("initialization")) {
                        for (AbstractInsnNode node : method.instructions.toArray()) {
                            tryReplaceInstance(
                                node,
                                Type.getInternalName(EntityValkyrieQueen.class),
                                Type.getInternalName(NewEntityValkyrieQueen.class));
                        }
                    }
                }

                return writeClass(classNode);
            }
            case "thaumcraft.common.config.ConfigItems" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (MethodNode method : classNode.methods) {
                    if (method.name.equals("initializeItems")) {
                        for (AbstractInsnNode node : method.instructions.toArray()) {
                            tryReplaceInstance(
                                node,
                                Type.getInternalName(ItemGoggles.class),
                                Type.getInternalName(BaubleItemGoggles.class));
                        }
                        break;
                    }
                }

                return writeClass(classNode);
            }
            /*
             * case "net.minecraft.client.gui.achievement.GuiAchievements" -> {
             * ClassNode classNode = new ClassNode();
             * new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);
             * for (MethodNode method : classNode.methods) {
             * if (method.name.equals("func_146552_b")) {
             * for (AbstractInsnNode node : method.instructions.toArray()) {
             * if (node instanceof LdcInsnNode ldcNode && ldcNode.cst.equals(0.75F)) {
             * // mofify green value of unlocked achievement
             * method.instructions.remove(node.getNext().getNext().getNext());
             * method.instructions.insert(node.getNext().getNext(), new LdcInsnNode(0.9F));
             * }
             * }
             * }
             * }
             * return writeClass(classNode);
             * }
             */
        }

        return basicClass;
    }

    public byte[] writeClass(ClassNode classNode) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public void tryReplaceInstance(AbstractInsnNode node, String oldName, String newName) {
        if (node instanceof TypeInsnNode typeNode && node.getOpcode() == Opcodes.NEW) {
            if (typeNode.desc.equals(oldName)) {
                typeNode.desc = newName;
            }
        } else if (node instanceof MethodInsnNode methodNode && methodNode.name.equals("<init>")) {
            if (methodNode.owner.equals(oldName)) {
                methodNode.owner = newName;
            }
        }
    }
}
