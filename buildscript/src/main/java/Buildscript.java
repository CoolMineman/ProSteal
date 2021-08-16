import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import io.github.coolcrabs.brachyura.compiler.java.JavaCompilationUnitBuilder;
import io.github.coolcrabs.brachyura.dependency.JavaJarDependency;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.FabricProject;
import io.github.coolcrabs.brachyura.fabric.Yarn;
import io.github.coolcrabs.brachyura.mappings.Namespaces;
import io.github.coolcrabs.brachyura.mappings.tinyremapper.MappingTreeMappingProvider;
import io.github.coolcrabs.brachyura.mappings.tinyremapper.PathFileConsumer;
import io.github.coolcrabs.brachyura.mappings.tinyremapper.TinyRemapperHelper;
import io.github.coolcrabs.brachyura.mappings.tinyremapper.TinyRemapperHelper.JarType;
import io.github.coolcrabs.brachyura.project.Task;
import io.github.coolcrabs.brachyura.util.ArrayUtil;
import io.github.coolcrabs.brachyura.util.AtomicFile;
import io.github.coolcrabs.brachyura.util.FileSystemUtil;
import io.github.coolcrabs.brachyura.util.JvmUtil;
import io.github.coolcrabs.brachyura.util.Util;
import io.github.coolcrabs.javacompilelib.JavaCompilationUnit;
import net.fabricmc.mappingio.tree.MappingTree;
import net.fabricmc.tinyremapper.TinyRemapper;

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
        return "100.0.0";
    }

    @Override
    public List<JavaJarDependency> createModDependencies() {
        return Collections.emptyList();
    }

    @Override
    public boolean build() {
        String[] compileArgs = ArrayUtil.join(String.class, 
            JvmUtil.compileArgs(JvmUtil.CURRENT_JAVA_VERSION, 8),
            "-AinMapFileNamedIntermediary=" + writeMappings4FabricStuff().toAbsolutePath().toString(),
            // "-AoutMapFileNamedIntermediary=" + getLocalBrachyuraPath() + "wat.tiny",
            "-AoutRefMapFile=" + getBuildResourcesDir().resolve(getModId() + "-refmap.json").toAbsolutePath().toString(),
            "-AdefaultObfuscationEnv=named:intermediary"
        );
        JavaCompilationUnit javaCompilationUnit = new JavaCompilationUnitBuilder()
                .sourceDir(getSrcDir())
                .outputDir(getBuildClassesDir())
                .classpath(getCompileDependencies())
                .options(compileArgs)
                .build();
        if (!compile(javaCompilationUnit)) return false;
        try {
            Path target = getBuildJarPath();
            Files.deleteIfExists(target);
            try (AtomicFile atomicFile = new AtomicFile(target)) {
                Files.deleteIfExists(atomicFile.tempPath);
                TinyRemapper remapper = TinyRemapper.newRemapper().withMappings(new MappingTreeMappingProvider(getMappings(), Namespaces.NAMED, Namespaces.INTERMEDIARY)).withMappings(o -> {
                    o.acceptClass("io/github/coolmineman/prosteal/ProSteal", "io/github/coolmineman/prosteal/True true null gamer");
                    o.acceptClass("io/github/coolmineman/prosteal/mixin/ProStealPlugin", "io/github/coolmineman/prosteal/mixin/True false null gamer");
                    o.acceptClass("io/github/coolmineman/prosteal/Garbage", "io/github/coolmineman/prosteal/Ra Ra Rasputin");
                }).build();
                try {
                    for (Path path : getCompileDependencies()) {
                        TinyRemapperHelper.readJar(remapper, path, JarType.CLASSPATH);
                    }
                    TinyRemapperHelper.readDir(remapper, getBuildClassesDir(), JarType.INPUT);
                    try (FileSystem outputFileSystem = FileSystemUtil.newJarFileSystem(atomicFile.tempPath)) {
                        remapper.apply(new PathFileConsumer(outputFileSystem.getPath("/")));
                        TinyRemapperHelper.copyNonClassfilesFromDir(getBuildResourcesDir(), outputFileSystem);
                    }
                } finally {
                    remapper.finish();
                }
                atomicFile.commit();
            }
            return true;
        } catch (Exception e) {
            throw Util.sneak(e);
        }
    }

}
