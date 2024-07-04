package cromega.studio.measurepedia.extensions

inline fun <E> MutableList<E>.addIfNotNull(element: E?) {
    if (element.isNotNull()) this.add(element!!)
}
