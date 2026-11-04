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
import net.friendswithmoney.fwm.net.MarketData;

public class MarketStatsData {
    public boolean ok;
    public MarketData.Item item;
    public List<Period> stats;

    public List<Period> list() {
        return this.stats == null ? List.of() : this.stats;
    }

    public Period period(String key) {
        for (Period entry : this.list()) {
            if (entry == null || !key.equals((Object)entry.period)) continue;
            return entry;
        }
        return null;
    }

    public static class Period {
        public String period;
        public int periodMinutes;
        public String ahAvgPrice;
        public String ordersAvgPrice;
        public String ahMaxPrice;
        public String ordersMaxPrice;
        public String highestUnitPrice;
        public String ahMarketVolume;
        public String ordersMarketVolume;
        public String totalMarketVolume;
        public String amountAh;
        public String amountOrders;
        public String totalAmountMarkt;
        public String ahEntryCount;
        public String ordersEntryCount;
        public String totalEntryCount;
        public String calculatedAt;
    }
}
