/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.friendswithmoney.fwm.config;

public class TestRun {
    public String name;
    public long cents;
    public long baseCents;
    public int items;
    public long at;

    public TestRun() {
    }

    public TestRun(String name, long cents, long baseCents, int items, long at) {
        this.name = name;
        this.cents = cents;
        this.baseCents = baseCents;
        this.items = items;
        this.at = at;
    }

    public double measuredMultiplier() {
        if (this.baseCents <= 0L) {
            return -1.0;
        }
        return (double)this.cents / (double)this.baseCents;
    }
}
