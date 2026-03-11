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
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.asm.replacements.BaubleItemAccessory;
import henrykado.gaiablossom.asm.replacements.BaubleItemAccessoryDyed;
import henrykado.gaiablossom.asm.replacements.BaubleItemGoggles;
import henrykado.gaiablossom.asm.replacements.NewModelTFBighorn;
import henrykado.gaiablossom.asm.replacements.NewModelTFBighornFur;
import henrykado.gaiablossom.asm.replacements.NewModelTFDeer;
import scala.tools.asm.Opcodes;
import thaumcraft.common.items.armor.ItemGoggles;
import twilightforest.client.model.ModelTFBighorn;
import twilightforest.client.model.ModelTFBighornFur;
import twilightforest.client.model.ModelTFDeer;

public class ClassTransformer implements IClassTransformer {

    static Logger LOG = LogManager.getLogger("gaiablossom");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        switch (transformedName) {
            case "net.minecraft.inventory.ContainerRepair$2" -> {
                if (!Config.noRepairCost) break;
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
                if (!Config.aetherBaubles) break;
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (AbstractInsnNode node : getMethodInstructions(classNode, "initialization")) {
                    tryReplaceInstance(
                        node,
                        "com/gildedgames/the_aether/items/accessories/ItemAccessory",
                        Type.getInternalName(BaubleItemAccessory.class));
                    tryReplaceInstance(
                        node,
                        "com/gildedgames/the_aether/items/accessories/ItemAccessoryDyed",
                        Type.getInternalName(BaubleItemAccessoryDyed.class));
                }

                return writeClass(classNode);
            }

            case "thaumcraft.common.config.ConfigItems" -> {
                if (!Config.customTwilightForestModels) break;
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (AbstractInsnNode node : getMethodInstructions(classNode, "initializeItems")) {
                    tryReplaceInstance(
                        node,
                        Type.getInternalName(ItemGoggles.class),
                        Type.getInternalName(BaubleItemGoggles.class));
                }

                return writeClass(classNode);
            }

            case "twilightforest.client.TFClientProxy" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (AbstractInsnNode node : getMethodInstructions(classNode, "doOnLoadRegistration")) {
                    tryReplaceInstance(
                        node,
                        Type.getInternalName(ModelTFBighorn.class),
                        Type.getInternalName(NewModelTFBighorn.class));
                    tryReplaceInstance(
                        node,
                        Type.getInternalName(ModelTFBighornFur.class),
                        Type.getInternalName(NewModelTFBighornFur.class));
                    tryReplaceInstance(
                        node,
                        Type.getInternalName(ModelTFDeer.class),
                        Type.getInternalName(NewModelTFDeer.class));
                }

                return writeClass(classNode);
            }
            case "twilightforest.client.renderer.entity.RenderTFDeer" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (AbstractInsnNode node : getMethodInstructions(classNode, "<clinit>")) {
                    if (node instanceof LdcInsnNode ldcNode) {
                        ldcNode.cst = "gaiablossom:textures/model/wilddeer.png";
                    }
                }

                return writeClass(classNode);
            }
            case "twilightforest.client.renderer.entity.RenderTFBighorn" -> {
                ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);

                for (AbstractInsnNode node : getMethodInstructions(classNode, "<clinit>")) {
                    if (node instanceof LdcInsnNode ldcNode) {
                        ldcNode.cst = "gaiablossom:textures/model/bighorn.png";
                    }
                }

                return writeClass(classNode);
            }
        }

        return basicClass;
    }

    public byte[] writeClass(ClassNode classNode) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public AbstractInsnNode[] getMethodInstructions(ClassNode classNode, String methodName) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(methodName)) {
                return method.instructions.toArray();
            }
        }
        LOG.error("Couldn't find the \"" + methodName + "\" method inside the provided ClassNode!");
        return new AbstractInsnNode[] {};
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
