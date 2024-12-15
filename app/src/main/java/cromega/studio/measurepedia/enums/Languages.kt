package cromega.studio.measurepedia.enums

enum class Languages(val localeAcronym: String)
{
    ENGLISH(localeAcronym = "en"),
    SPANISH(localeAcronym = "es");

    companion object
    {
        fun findFromLocaleAcronym(localeAcronym: String): Languages =
            when(localeAcronym)
            {
                SPANISH.localeAcronym -> SPANISH
                else -> ENGLISH
            }

        fun asArray(): Array<Languages> = entries.toTypedArray()

        fun asStringArray(): Array<String> = entries.map { it.localeAcronym }.toTypedArray()
    }
}