package cromega.studio.measurepedia.data.models.generic

abstract class Model(val id: Int)
{
    abstract fun clone(): Model
}