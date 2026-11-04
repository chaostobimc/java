/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package net.friendswithmoney.fwm.net;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FakeList;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.net.TrustedClans;

public final class ClanTags {
    private static final Pattern REAL_TAG = Pattern.compile((String)"(?i)^FWM(?:[0-9]|X)?$");
    private static final Pattern CANDIDATE = Pattern.compile((String)"\\[[^\\[\\]]{1,8}\\]");

    private ClanTags() {
    }

    public static String fakeRawIn(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }
        Matcher matcher = CANDIDATE.matcher((CharSequence)line);
        while (matcher.find()) {
            String tag = ClanTags.bare(matcher.group());
            if (!ClanTags.isFake(tag)) continue;
            return tag;
        }
        return null;
    }

    public static String fakeIn(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }
        Matcher matcher = CANDIDATE.matcher((CharSequence)line);
        while (matcher.find()) {
            String tag = ClanTags.bare(matcher.group());
            if (!ClanTags.isFake(tag)) continue;
            return ClanTags.tidy(tag);
        }
        return null;
    }

    public static List<int[]> fakeSpans(String line) {
        if (line == null || line.isEmpty()) {
            return List.of();
        }
        ArrayList spans = null;
        Matcher matcher = CANDIDATE.matcher((CharSequence)line);
        while (matcher.find()) {
            if (!ClanTags.isFake(ClanTags.bare(matcher.group()))) continue;
            if (spans == null) {
                spans = new ArrayList(2);
            }
            spans.add((Object)new int[]{matcher.start(), matcher.end()});
        }
        return spans == null ? List.of() : spans;
    }

    public static String bare(String token) {
        if (token != null && token.length() > 1 && token.charAt(0) == '[') {
            return token.substring(1, token.length() - 1);
        }
        return token;
    }

    public static boolean isFake(String tag) {
        List<String> exact;
        List<String> fakes;
        if (FakeList.isFakeTag(tag)) {
            return true;
        }
        if (TrustedClans.fakeOf(tag) != null) {
            return true;
        }
        String letters = ClanTags.letters(tag);
        if (letters.isEmpty()) {
            return false;
        }
        if (ClanTags.isReal(tag)) {
            return false;
        }
        if (ClanTags.sameTag(letters, ClanTags.letters("FWM"))) {
            return true;
        }
        boolean managed = FakeList.managed();
        List<String> list = fakes = managed ? ClanTags.ownOnly(FwmConfig.INSTANCE.fakeTags, FwmConfig.DEFAULT_FAKE_TAGS) : FwmConfig.INSTANCE.fakeTags;
        if (managed) {
            for (String fake : FakeList.similarTags()) {
                if (!ClanTags.sameTag(letters, ClanTags.letters(fake))) continue;
                return true;
            }
        }
        if (fakes != null) {
            for (String fake : fakes) {
                if (!ClanTags.sameTag(letters, ClanTags.letters(fake))) continue;
                return true;
            }
        }
        List<String> list2 = exact = managed ? ClanTags.ownOnly(FwmConfig.INSTANCE.fakeTagsExact, FwmConfig.DEFAULT_FAKE_TAGS_EXACT) : FwmConfig.INSTANCE.fakeTagsExact;
        if (exact != null) {
            String plain = ClanTags.tidy(tag);
            for (String fake : exact) {
                if (fake == null || !plain.equals((Object)ClanTags.tidy(fake))) continue;
                return true;
            }
        }
        return false;
    }

    private static List<String> ownOnly(List<String> mine, List<String> builtIn) {
        if (mine == null || mine.isEmpty()) {
            return List.of();
        }
        HashSet skip = new HashSet();
        for (String s : builtIn) {
            skip.add((Object)ClanTags.tidy(s));
        }
        ArrayList out = new ArrayList();
        for (String s : mine) {
            if (s == null || skip.contains((Object)ClanTags.tidy(s))) continue;
            out.add((Object)s);
        }
        return out;
    }

    private static boolean sameTag(String letters, String fake) {
        if (letters.equals((Object)fake)) {
            return true;
        }
        String bare = fake.replace((CharSequence)"X", (CharSequence)"");
        return bare.length() >= 3 && letters.replace((CharSequence)"X", (CharSequence)"").equals((Object)bare);
    }

    public static boolean isReal(String tag) {
        if (tag == null) {
            return false;
        }
        String plain = tag.trim().replaceAll("[^\\x00-\\x7F]", "");
        if (TrustedClans.isFwmTag(plain)) {
            return true;
        }
        if (Members.clanTags().contains((Object)plain.toUpperCase(Locale.ROOT))) {
            return true;
        }
        return REAL_TAG.matcher((CharSequence)plain).matches();
    }

    private static String letters(String tag) {
        if (tag == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(tag.length());
        for (int i = 0; i < tag.length(); ++i) {
            char c = Character.toUpperCase((char)tag.charAt(i));
            if (c == 'O' || c == 'I' || c < 'A' || c > 'Z') continue;
            out.append(c);
        }
        return out.toString();
    }

    public static String tidy(String tag) {
        return tag == null ? "" : tag.trim().toUpperCase(Locale.ROOT);
    }
}
