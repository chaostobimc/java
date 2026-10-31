// class net/friendswithmoney/fwm/ui/SpawnerCard$Layout  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field rows Ljava/util/List;;
field title Lnet/minecraft/network/chat/Component;;
field headName Lnet/minecraft/network/chat/Component;;
field headBuy Lnet/minecraft/network/chat/Component;;
field headSell Lnet/minecraft/network/chat/Component;;
field iconW I;
field nameW I;
field buyW I;
field sellW I;
field titleH I;
field headerH I;
field cellW I;
field cols I;
field w I;
field h I;
private void <init>(List rows, network.chat.Component title, network.chat.Component headName, network.chat.Component headBuy, network.chat.Component headSell, int iconW, int nameW, int buyW, int sellW, int titleH, int headerH, int cellW, int cols, int w, int h) {
    this.rows = rows;
    this.title = title;
    this.headName = headName;
    this.headBuy = headBuy;
    this.headSell = headSell;
    this.iconW = iconW;
    this.nameW = nameW;
    this.buyW = buyW;
    this.sellW = sellW;
    this.titleH = titleH;
    this.headerH = headerH;
    this.cellW = cellW;
    this.cols = cols;
    this.w = w;
    this.h = h;
    return;
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

public List rows() {
    return this.rows;
}

public network.chat.Component title() {
    return this.title;
}

public network.chat.Component headName() {
    return this.headName;
}

public network.chat.Component headBuy() {
    return this.headBuy;
}

public network.chat.Component headSell() {
    return this.headSell;
}

public int iconW() {
    return this.iconW;
}

public int nameW() {
    return this.nameW;
}

public int buyW() {
    return this.buyW;
}

public int sellW() {
    return this.sellW;
}

public int titleH() {
    return this.titleH;
}

public int headerH() {
    return this.headerH;
}

public int cellW() {
    return this.cellW;
}

public int cols() {
    return this.cols;
}

public int w() {
    return this.w;
}

public int h() {
    return this.h;
}

