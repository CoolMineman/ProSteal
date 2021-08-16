package io.github.coolmineman.prosteal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.InsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;

public enum ProSteal implements IExtension {
    INSTANCE;

    Set<String> constTargets = new HashSet<>(Arrays.asList("https://api.stopmodreposts.org/sites.txt", "/stolen.html"));

    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        //
    }

    @Override
    public void postApply(ITargetClassContext context) {
        MethodNode target = null;
        for (MethodNode methodNode : context.getClassNode().methods) {
            if (methodNode.parameters != null && methodNode.parameters.size() == 4) {
                for (AbstractInsnNode ins : methodNode.instructions) {
                    if (ins instanceof LdcInsnNode && constTargets.contains(((LdcInsnNode)ins).cst)) {
                        target = methodNode;
                    }
                }
            }
        }
        if (target != null) {
            System.out.println("Prosteal: Patching " + context.getClassInfo().getClassName());
            Iterator<AbstractInsnNode> iterator = target.instructions.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
            target.instructions.insert(new InsnNode(Opcodes.RETURN));
            target.tryCatchBlocks.clear();
            target.localVariables.clear();
        }
    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {
        //
    }
}
