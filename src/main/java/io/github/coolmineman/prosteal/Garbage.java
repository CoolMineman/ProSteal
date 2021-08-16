package io.github.coolmineman.prosteal;

import java.util.HashMap;

public class Garbage extends HashMap {
    @Override
    public boolean containsKey(Object key) {
        return true;
    }
}
