// class net/friendswithmoney/fwm/seventv/EmoteGlyphs$Instance  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field types Lnet/minecraft/client/gui/font/GlyphRenderTypes;;
field view Lcom/mojang/renderpearl/api/textures/GpuTextureView;;
field x F;
field y F;
field w F;
field h F;
field v0 F;
field v1 F;
field color I;
field style Lnet/minecraft/network/chat/Style;;
private void <init>(client.gui.font.GlyphRenderTypes types, renderpearl.api.textures.GpuTextureView view, float x, float y, float w, float h, float v0, float v1, int color, network.chat.Style style) {
    this.types = types;
    this.view = view;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.v0 = v0;
    this.v1 = v1;
    this.color = color;
    this.style = style;
    return;
}

public blaze3d.vertex.VertexConsumer render(org.joml.Matrix4fc pose, blaze3d.vertex.VertexConsumer buffer, int light, boolean seeThrough) {
    v5 = this.x;
    v6 = (this.x + this.w);
    v7 = this.y;
    v8 = (this.y + this.h);
    buffer.addVertex(pose, v5, v7, 0).setUv(0, this.v0).setColor(this.color).setLight(light);
    buffer.addVertex(pose, v5, v8, 0).setUv(0, this.v1).setColor(this.color).setLight(light);
    buffer.addVertex(pose, v6, v8, 0).setUv(1, this.v1).setColor(this.color).setLight(light);
    buffer.addVertex(pose, v6, v7, 0).setUv(1, this.v0).setColor(this.color).setLight(light);
    return;
}

public client.renderer.rendertype.RenderType renderType(client.gui.Font$DisplayMode mode) {
    return this.types.select(mode);
}

public renderpearl.api.textures.GpuTextureView textureView() {
    return this.view;
}

public renderpearl.api.pipeline.RenderPipeline guiPipeline() {
    return this.types.guiPipeline();
}

public float left() {
    return this.x;
}

public float top() {
    return this.y;
}

public float right() {
    return (this.x + this.w);
}

public float bottom() {
    return (this.y + this.h);
}

public final String toString() {
    return /*indy*/toString(this);
}

public final int hashCode() {
    return /*indy*/hashCode(this);
}

public final boolean equals(Object o) {
    return /*indy*/equals(this, o);
}

public client.gui.font.GlyphRenderTypes types() {
    return this.types;
}

public renderpearl.api.textures.GpuTextureView view() {
    return this.view;
}

public float x() {
    return this.x;
}

public float y() {
    return this.y;
}

public float w() {
    return this.w;
}

public float h() {
    return this.h;
}

public float v0() {
    return this.v0;
}

public float v1() {
    return this.v1;
}

public int color() {
    return this.color;
}

public network.chat.Style style() {
    return this.style;
}

