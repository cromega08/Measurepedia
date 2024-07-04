package cromega.studio.measurepedia.extensions

inline fun Int?.toBoolean(): Boolean = (this.isNotNull() && this != 0)