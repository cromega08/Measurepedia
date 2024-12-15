package cromega.studio.measurepedia.data.local.files

import android.content.Context
import cromega.studio.measurepedia.enums.Languages
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class UserInfo(
    private val context: Context
) {
    private val defaultUserInfoJSON: String = """{"language":"en","dark_theme":false}"""
    private val userInfoFileName: String = "user_info.json"

    var language: Languages
    var darkTheme: Boolean

    init {
        try { readJSONFile() }
        catch (e: Exception) { updateUserInfo(jsonString = defaultUserInfoJSON) }
        finally {
            val json: JSONObject = readJSONFile()
            language =
                Languages
                    .findFromLocaleAcronym(
                        localeAcronym = json.get("language") as String
                    )
            darkTheme = json.get("dark_theme") as Boolean
        }
    }

    fun flushUpdateUserInfo() = updateUserInfo(jsonString = this.toString())

    private fun updateUserInfo(jsonString: String) =
        context
            .openFileOutput(userInfoFileName, Context.MODE_PRIVATE)
            .use { output -> output.write(jsonString.toByteArray()) }

    private fun readJSONFile(): JSONObject
    {
        val inputStream = context.openFileInput(userInfoFileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        return JSONObject(bufferedReader.use { it.readText() })
    }

    override fun toString(): String =
        """{"language":"${language.localeAcronym}","dark_theme":$darkTheme}"""
}