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

public class MemberData {
    public List<Member> members = new ArrayList();
    public List<String> clans = new ArrayList();

    public static class Member {
        public String name;
        public String role;
        public String clan;
    }
}
