// class net/friendswithmoney/fwm/net/FwmApi$Fetched  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field status I;
field body [B;
field etag Ljava/lang/String;;
field retryAfterSeconds J;
public void <init>(int status, byte[] body, String etag, long retryAfterSeconds) {
    this.status = status;
    this.body = body;
    this.etag = etag;
    this.retryAfterSeconds = retryAfterSeconds;
    return;
}

public void text() {
    if (this.body != null) goto L11;
    goto L25;
    L11:
    L25:
    return ?;
}

public Object json(Class type) {
    v2 = this.text();
    if (v2 != null) goto L13;
    goto L21;
    L13:
    L21:
    return net.FwmApi.GSON.fromJson(v2, type);
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

public int status() {
    return this.status;
}

public byte[] body() {
    return this.body;
}

public String etag() {
    return this.etag;
}

public long retryAfterSeconds() {
    return this.retryAfterSeconds;
}

