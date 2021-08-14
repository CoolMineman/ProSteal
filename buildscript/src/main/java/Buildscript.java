import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import io.github.coolcrabs.brachyura.dependency.JavaJarDependency;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.FabricProject;
import io.github.coolcrabs.brachyura.fabric.Yarn;
import io.github.coolcrabs.brachyura.project.Task;
import net.fabricmc.mappingio.tree.MappingTree;

public class Buildscript extends FabricProject {
    @Override
    public void getTasks(Consumer<Task> p) {
        super.getTasks(p);
        p.accept(Task.of("crab", this::crab));
    }

    public void crab() {
        System.out.println("     /\\");
        System.out.println("    ( /   @ @    ()");
        System.out.println("     \\  __| |__  /");
        System.out.println("      -/   \"   \\-");
        System.out.println("     /-|       |-\\");
        System.out.println("    / /-\\     /-\\ \\");
        System.out.println("     / /-`---'-\\ \\     tre");
        System.out.println("      /         \\");
    }

    @Override
    public String getMcVersion() {
        return "1.17.1";
    }

    @Override
    public MappingTree getMappings() {
        return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn("1.17.1+build.13")).tree;
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.11.6"));
    }

    @Override
    public String getModId() {
        return "prosteal";
    }

    @Override
    public String getVersion() {
        return "0.0.0";
    }

    @Override
    public List<JavaJarDependency> createModDependencies() {
        return Collections.emptyList();
    }

}
