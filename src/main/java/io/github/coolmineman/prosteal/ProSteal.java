package io.github.coolmineman.prosteal;

import java.net.URL;
import java.net.URLClassLoader;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ProSteal implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        try {
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{ProSteal.class.getProtectionDomain().getCodeSource().getLocation()})) {
                classLoader.loadClass("i.am.cal.antisteal.Antisteal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
