/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package net.friendswithmoney.fwm.net;

import java.util.List;

public class LiveAuctionData {
    public boolean ok;
    public List<Entry> items;
    public Meta meta;

    public List<Entry> list() {
        return this.items == null ? List.of() : this.items;
    }

    public static class Meta {
        public String nextAfterId;
    }

    public static class Entry {
        public String id;
        public String sellerName;
        public String itemKey;
        public String baseItemKey;
        public String variantKey;
        public String displayName;
        public String icon;
        public String extraDisplayName;
        public String price;
        public String amount;
        public String unitPrice;
        public String timestamp;
    }
}
