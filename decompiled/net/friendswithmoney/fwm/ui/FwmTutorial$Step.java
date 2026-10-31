// class net/friendswithmoney/fwm/ui/FwmTutorial$Step  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field categoryKey Ljava/lang/String;;
field subTab I;
field textKey Ljava/lang/String;;
field toggleKey Ljava/lang/String;;
field kind Lnet/friendswithmoney/fwm/ui/FwmTutorial$Kind;;
field right Z;
private void <init>(String categoryKey, int subTab, String textKey, String toggleKey, ui.FwmTutorial$Kind kind, boolean right) {
    this.categoryKey = categoryKey;
    this.subTab = subTab;
    this.textKey = textKey;
    this.toggleKey = toggleKey;
    this.kind = kind;
    this.right = right;
    return;
}

static void of(String categoryKey, String textKey) {
    return ?;
}

static void of(String categoryKey, String textKey, String toggleKey) {
    return ?;
}

static void tab(String categoryKey, int subTab, String textKey, String toggleKey) {
    return ?;
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

public String categoryKey() {
    return this.categoryKey;
}

public int subTab() {
    return this.subTab;
}

public String textKey() {
    return this.textKey;
}

public String toggleKey() {
    return this.toggleKey;
}

public ui.FwmTutorial$Kind kind() {
    return this.kind;
}

public boolean right() {
    return this.right;
}

