/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package net.friendswithmoney.fwm.net;

import java.util.ArrayList;
import java.util.List;

public class SpawnerData {
    public boolean ok;
    public List<Item> items = new ArrayList();

    public static class Item {
        public String id;
        public String name;
        public String buyPrice;
        public String sellPrice;
        public String updatedAt;

        public boolean tradedBack() {
            return this.sellPrice != null && !this.sellPrice.trim().isEmpty() && !"0".equals((Object)this.sellPrice.trim());
        }

        public String updatedDate() {
            if (this.updatedAt == null || this.updatedAt.length() < 10) {
                return null;
            }
            return this.updatedAt.substring(8, 10) + "." + this.updatedAt.substring(5, 7) + "." + this.updatedAt.substring(0, 4);
        }
    }
}
