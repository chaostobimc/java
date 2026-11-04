/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.friendswithmoney.fwm.chat;

public class PrivateMessage {
    public boolean outgoing;
    public String text;
    public long time;

    public PrivateMessage() {
    }

    public PrivateMessage(boolean outgoing, String text, long time) {
        this.outgoing = outgoing;
        this.text = text;
        this.time = time;
    }
}
