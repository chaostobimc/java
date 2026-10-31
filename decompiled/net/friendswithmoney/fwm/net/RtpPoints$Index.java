// class net/friendswithmoney/fwm/net/RtpPoints$Index  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field shift I;
field minCellX I;
field minCellZ I;
field cellsW I;
field cellsH I;
field start [I;
field order [I;
field minX I;
field minZ I;
field maxX I;
field maxZ I;
void <init>(int shift, int minCellX, int minCellZ, int cellsW, int cellsH, int[] start, int[] order) {
    this.shift = shift;
    this.minCellX = minCellX;
    this.minCellZ = minCellZ;
    this.cellsW = cellsW;
    this.cellsH = cellsH;
    this.start = start;
    this.order = order;
    return;
}

