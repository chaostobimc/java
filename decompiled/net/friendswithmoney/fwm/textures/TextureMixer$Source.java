// class net/friendswithmoney/fwm/textures/TextureMixer$Source  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field root Ljava/nio/file/Path;;
field zip Ljava/nio/file/FileSystem;;
Path <init>(String name) {
    v2 = textures.TextureMixer.packDir().resolve(name);
    if (Files.isDirectory(v2, new LinkOption[0]) ==0) goto L38;
    this.root = v2;
    this.zip = null;
    goto L63;
    L38:
    this.zip = FileSystems.newFileSystem(v2);
    this.root = this.zip.getPath("/", new String[0]);
    L63:
    return;
}

public void close() {
    if (this.zip == null) goto L14;
    this.zip.close();
    L14:
    return;
}

