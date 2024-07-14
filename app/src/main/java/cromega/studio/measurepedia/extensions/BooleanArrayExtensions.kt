package cromega.studio.measurepedia.extensions

infix fun BooleanArray.everyOneIs(booleanToBe: Boolean): Boolean
{
    this.forEach { if (it != booleanToBe) return false }

    return true
}

infix fun BooleanArray.atLeastOneIs(booleanToContain: Boolean): Boolean
{
    this.forEach { if (it == booleanToContain) return true }

    return false
}
