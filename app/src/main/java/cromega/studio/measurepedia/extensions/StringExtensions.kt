package cromega.studio.measurepedia.extensions

import java.text.SimpleDateFormat
import java.util.Locale

inline fun String?.isNotNullOrBlank() = !this.isNullOrBlank()

infix fun String.toDateWithFormat(dateFormat: SimpleDateFormat) =
    dateFormat.parse(this)!!

inline fun String.forEachWord(
    wordsSeparator: Regex = "\\s+".toRegex(),
    onlyLetters: Boolean = false,
    lowercaseWords: Boolean = false,
    deduplicateWords: Boolean = false,
    action: (String) -> Unit
) {
    var toExtract: String =
        if (onlyLetters) this.removeNonLetters(exclude = wordsSeparator.toString())
        else this

    if (lowercaseWords) toExtract = toExtract.lowercase()

    val words: Collection<String> =
        if (deduplicateWords)
            toExtract.extractWords(
                separator = wordsSeparator,
                extractAsLowercase = lowercaseWords
            )
        else toExtract.split(wordsSeparator)

    words.forEach { action(it) }
}

inline fun String.removeNonLetters(exclude: String = ""): String =
    this.replace("[^a-zA-Z${exclude}]".toRegex(), "")

inline fun String.extractWords(
    separator: Regex = "\\s+".toRegex(),
    extractAsLowercase: Boolean = false
): Set<String>
{
    var onlyWords: String = this.removeNonLetters(exclude = separator.toString())
    
    if (extractAsLowercase) onlyWords = onlyWords.lowercase()
    
    return onlyWords.split(separator).toSet()
}

fun String.titlecase(): String {
    var toReturn: String = this.lowercase()

    this.forEachWord(
        onlyLetters = true,
        lowercaseWords = true,
        deduplicateWords = true
    ) {
        toReturn = toReturn.replace(
            oldValue = it,
            newValue = it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            }
        )
    }

    return toReturn
}
