// class net/friendswithmoney/fwm/antileak/AntiLeakPack$Settings  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field bedrock Z;
field ores Z;
field rotation Z;
field stones Z;
field biome Z;
public void <init>(boolean bedrock, boolean ores, boolean rotation, boolean stones, boolean biome) {
    this.bedrock = bedrock;
    this.ores = ores;
    this.rotation = rotation;
    this.stones = stones;
    this.biome = biome;
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

public boolean bedrock() {
    return this.bedrock;
}

public boolean ores() {
    return this.ores;
}

public boolean rotation() {
    return this.rotation;
}

public boolean stones() {
    return this.stones;
}

public boolean biome() {
    return this.biome;
}

