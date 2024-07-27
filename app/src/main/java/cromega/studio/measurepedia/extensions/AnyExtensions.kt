package cromega.studio.measurepedia.extensions

inline fun Any?.isNull() = this == null

inline fun Any?.isNotNull() = !this.isNull()
