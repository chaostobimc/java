/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.concurrent.ConcurrentLinkedQueue
 */
package net.friendswithmoney.fwm.ui;

import java.util.concurrent.ConcurrentLinkedQueue;

public final class FwmNotice {
    private static final ConcurrentLinkedQueue<Notice> QUEUE = new ConcurrentLinkedQueue();
    private static Notice current;
    private static long since;

    private FwmNotice() {
    }

    public static void show(String head, String title, String line, int color, long ms) {
        QUEUE.add((Object)new Notice(head, title == null ? "" : title, line == null ? "" : line, 0xFF000000 | color, ms));
    }

    static Notice current() {
        long now = System.currentTimeMillis();
        if (current != null && now - since >= current.ms()) {
            current = null;
        }
        if (current == null) {
            current = (Notice)((Object)QUEUE.poll());
            since = now;
        }
        return current;
    }

    static float progress() {
        return current == null ? 1.0f : Math.min((float)1.0f, (float)((float)(System.currentTimeMillis() - since) / (float)current.ms()));
    }

    public record Notice(String head, String title, String line, int color, long ms) {
    }
}
