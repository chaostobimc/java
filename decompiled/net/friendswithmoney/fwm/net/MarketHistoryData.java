/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Double
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Map
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public final class MarketHistoryData {
    private MarketHistoryData() {
    }

    public static class Point {
        public String d;
        public double p;
        public Double ah;
        @SerializedName(value="or")
        public Double orders;
    }

    public static class History {
        public boolean ok;
        public String item;
        public String since;
        public String source;
        public List<Point> points;

        public List<Point> list() {
            return this.points == null ? List.of() : this.points;
        }
    }

    public static class Changes {
        public boolean ok;
        public String day;
        public int span;
        public Map<String, Double> items;
        public Map<String, Double> prices;
        public Map<String, String> sources;
    }
}
