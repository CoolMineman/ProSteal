package io.github.coolmineman.prosteal;

import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.jar.JarFile;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ProSteal implements PreLaunchEntrypoint {
    public static FileSystem getJarFileSystem(Path path) throws Exception {
        return FileSystems.getFileSystem(new URI("jar:file", null, path.toUri().getPath(), ""));
    }

    public static FileSystem createJarFileSystem(Path path) throws Exception {
        return FileSystems.newFileSystem(new URI("jar:file", null, path.toUri().getPath(), ""), Collections.emptyMap());
    }

    @Override
    public void onPreLaunch() {
        try {
            URL otherUrl = ((JarURLConnection)ProSteal.class.getClassLoader().getResource("i/am/cal/antisteal/Antisteal.class").openConnection()).getJarFileURL();
            URL thisUrl = ((JarURLConnection)ProSteal.class.getClassLoader().getResource("io/github/coolmineman/prosteal/True true null gamer.class").openConnection()).getJarFileURL();
            JarFile thisJar = ((JarURLConnection)ProSteal.class.getClassLoader().getResource("io/github/coolmineman/prosteal/True true null gamer.class").openConnection()).getJarFile();
            System.out.println(thisUrl.toURI());
            System.out.println(otherUrl.toURI());
            if (!otherUrl.toURI().equals(thisUrl.toURI())) {
                ((JarURLConnection)ProSteal.class.getClassLoader().getResource("i/am/cal/antisteal/Antisteal.class").openConnection()).getJarFile().close();
                getJarFileSystem(Paths.get(otherUrl.toURI())).close();
                try (FileSystem otherFs = createJarFileSystem(Paths.get(otherUrl.toURI()))) {
                    Files.copy(thisJar.getInputStream(thisJar.getEntry("i/am/cal/antisteal/Antisteal.class")), otherFs.getPath("i/am/cal/antisteal/Antisteal.class"), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
