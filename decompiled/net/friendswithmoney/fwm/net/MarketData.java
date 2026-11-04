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

public class MarketData {
    public boolean ok;
    public List<Item> items;
    public Meta meta;

    public List<Item> list() {
        return this.items == null ? List.of() : this.items;
    }

    public static class Meta {
        public int limit;
        public int offset;
        public int nextOffset;
        public int totalItems;
        public boolean hasMore;
        public String sort;
    }

    public static class Item {
        public String itemKey;
        public String baseItemKey;
        public String variantKey;
        public String displayName;
        public String icon;
        public String auctionEntries;
        public String orderEntries;
        public String totalEntries;
        public String highestUnitPrice24h;
        public String totalMarketVolume24h;
        public String firstSeen;
        public String lastSeen;
    }
}
