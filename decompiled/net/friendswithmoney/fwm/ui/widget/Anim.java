/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.Map
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.HashMap;
import java.util.Map;

public final class Anim {
    private static final Map<String, State> VALUES = new HashMap();

    private Anim() {
    }

    public static float toward(String key, boolean target, int millis) {
        long now = System.currentTimeMillis();
        float goal = target ? 1.0f : 0.0f;
        State state = (State)((Object)VALUES.get((Object)key));
        if (state == null) {
            VALUES.put((Object)key, (Object)new State(goal, now));
            return goal;
        }
        float step = (float)(now - state.at()) / (float)Math.max((int)1, (int)millis);
        float value = state.value();
        if (value < goal) {
            value = Math.min((float)goal, (float)(value + step));
        } else if (value > goal) {
            value = Math.max((float)goal, (float)(value - step));
        }
        VALUES.put((Object)key, (Object)new State(value, now));
        return value;
    }

    public static float ease(float t) {
        float inv = 1.0f - Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)t));
        return 1.0f - inv * inv * inv;
    }

    private record State(float value, long at) {
    }
}
