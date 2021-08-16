package io.github.coolmineman.prosteal.mixin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;

import io.github.coolmineman.prosteal.Garbage;
import io.github.coolmineman.prosteal.ProSteal;

@SuppressWarnings("all")
public class ProStealPlugin implements IMixinConfigPlugin {
    public ProStealPlugin() throws Throwable {
        System.out.println("Injecting ProSteal");
        IMixinTransformer t = (IMixinTransformer)MixinEnvironment.getDefaultEnvironment().getActiveTransformer();
        ((Extensions)t.getExtensions()).add(ProSteal.INSTANCE);
        yeet4(t);
    }

    @Override
    public void onLoad(String mixinPackage) {
        //
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        //
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        //
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        //
    }

    static IMixinConfig yeet() throws Throwable {
        Constructor c = Class.forName("org.spongepowered.asm.mixin.transformer.MixinConfig").getDeclaredConstructor();
        c.setAccessible(true);
        Object result = c.newInstance();
        Field f = Class.forName("org.spongepowered.asm.mixin.transformer.MixinConfig").getDeclaredField("mixinMapping");
        f.setAccessible(true);
        f.set(result, new Garbage());
        Field f2 = Class.forName("org.spongepowered.asm.mixin.transformer.MixinConfig").getDeclaredField("mixinPackage");
        f2.setAccessible(true);
        f2.set(result, "ProStealEpicYeetBruh");
        return (IMixinConfig) result;
    }

    static void yeet2(Object mixinProcessor, IMixinConfig c) throws Throwable {
        Field f = Class.forName("org.spongepowered.asm.mixin.transformer.MixinProcessor").getDeclaredField("configs");
        f.setAccessible(true);
        List configs = (List) f.get(mixinProcessor);
        configs.add(c);
    }

    static void yeet3(IMixinTransformer transformer, IMixinConfig c) throws Throwable {
        Field f = Class.forName("org.spongepowered.asm.mixin.transformer.MixinTransformer").getDeclaredField("processor");
        f.setAccessible(true);
        yeet2(f.get(transformer), c);
    }

    static void yeet4(IMixinTransformer t) throws Throwable {
        yeet3(t, yeet());
    }
    
}
