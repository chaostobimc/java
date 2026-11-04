/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.font.GlyphInfo
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.renderpearl.api.pipeline.RenderPipeline
 *  com.mojang.renderpearl.api.textures.GpuTextureView
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.concurrent.ConcurrentHashMap
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.gui.GlyphSource
 *  net.minecraft.client.gui.font.GlyphRenderTypes
 *  net.minecraft.client.gui.font.TextRenderable$Styled
 *  net.minecraft.client.gui.font.glyphs.BakedGlyph
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.Identifier
 *  net.minecraft.util.RandomSource
 *  org.joml.Matrix4fc
 */
package net.friendswithmoney.fwm.seventv;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteRegistry;
import net.friendswithmoney.fwm.seventv.EmoteTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GlyphSource;
import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4fc;

public final class EmoteGlyphs {
    public static final String FONT_PREFIX = "emote/";
    public static final float HEIGHT = 9.0f;
    private static final Map<String, GlyphSource> SOURCES = new ConcurrentHashMap();
    private static final Map<Identifier, GlyphRenderTypes> RENDER_TYPES = new ConcurrentHashMap();

    private EmoteGlyphs() {
    }

    public static Identifier fontId(Emote e) {
        return Identifier.fromNamespaceAndPath((String)"fwm", (String)(FONT_PREFIX + e.key()));
    }

    public static boolean isEmoteFont(Identifier id) {
        return "fwm".equals((Object)id.getNamespace()) && id.getPath().startsWith(FONT_PREFIX);
    }

    public static GlyphSource sourceFor(Identifier fontId) {
        if (!EmoteGlyphs.isEmoteFont(fontId)) {
            return null;
        }
        String key = fontId.getPath().substring(FONT_PREFIX.length());
        return (GlyphSource)SOURCES.computeIfAbsent((Object)key, Source::new);
    }

    private static EmoteTexture texture(String key) {
        EmoteTexture have = EmoteTexture.byKey(key);
        if (have != null) {
            return have;
        }
        Emote e = EmoteRegistry.byKey(key);
        if (e == null) {
            return null;
        }
        return EmoteTexture.get(e);
    }

    private static float width(EmoteTexture t) {
        if (t == null || t.state != EmoteTexture.State.READY) {
            return 9.0f;
        }
        return Math.max((float)4.0f, (float)Math.min((float)36.0f, (float)(9.0f * t.aspect())));
    }

    private record Instance(GlyphRenderTypes types, GpuTextureView view, float x, float y, float w, float h, float v0, float v1, int color, Style style) implements TextRenderable.Styled
    {
        public void render(Matrix4fc pose, VertexConsumer buffer, int light, boolean seeThrough) {
            float x0 = this.x;
            float x1 = this.x + this.w;
            float y0 = this.y;
            float y1 = this.y + this.h;
            buffer.addVertex(pose, x0, y0, 0.0f).setUv(0.0f, this.v0).setColor(this.color).setLight(light);
            buffer.addVertex(pose, x0, y1, 0.0f).setUv(0.0f, this.v1).setColor(this.color).setLight(light);
            buffer.addVertex(pose, x1, y1, 0.0f).setUv(1.0f, this.v1).setColor(this.color).setLight(light);
            buffer.addVertex(pose, x1, y0, 0.0f).setUv(1.0f, this.v0).setColor(this.color).setLight(light);
        }

        public RenderType renderType(Font.DisplayMode mode) {
            return this.types.select(mode);
        }

        public GpuTextureView textureView() {
            return this.view;
        }

        public RenderPipeline guiPipeline() {
            return this.types.guiPipeline();
        }

        public float left() {
            return this.x;
        }

        public float top() {
            return this.y;
        }

        public float right() {
            return this.x + this.w;
        }

        public float bottom() {
            return this.y + this.h;
        }
    }

    private record Glyph(String key) implements BakedGlyph
    {
        public GlyphInfo info() {
            float w = EmoteGlyphs.width(EmoteGlyphs.texture(this.key));
            return GlyphInfo.simple((float)(w + 1.0f));
        }

        public TextRenderable.Styled createGlyph(float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset) {
            EmoteTexture t = EmoteGlyphs.texture(this.key);
            if (t == null || t.state != EmoteTexture.State.READY) {
                return null;
            }
            AbstractTexture tex = Minecraft.getInstance().getTextureManager().getTexture(t.id);
            if (tex == null) {
                return null;
            }
            GpuTextureView view = tex.getTextureView();
            if (view == null) {
                return null;
            }
            GlyphRenderTypes types = (GlyphRenderTypes)RENDER_TYPES.computeIfAbsent((Object)t.id, GlyphRenderTypes::createForColorTexture);
            float w = EmoteGlyphs.width(t);
            int alpha = color >>> 24 & 0xFF;
            int white = alpha << 24 | 0xFFFFFF;
            return new Instance(types, view, x, y - 1.0f, w, 9.0f, t.v0(), t.v1(), white, style);
        }
    }

    private record Source(String key) implements GlyphSource
    {
        public BakedGlyph getGlyph(int codepoint) {
            return new Glyph(this.key);
        }

        public BakedGlyph getRandomGlyph(RandomSource random, int width) {
            return new Glyph(this.key);
        }
    }
}
