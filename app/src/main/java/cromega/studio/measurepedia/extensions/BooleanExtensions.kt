package cromega.studio.measurepedia.extensions

inline fun Boolean?.isNull() = this == null

inline fun Boolean?.isNotNull() = !this.isNull()
