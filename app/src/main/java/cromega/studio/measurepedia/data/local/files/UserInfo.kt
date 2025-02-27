package cromega.studio.measurepedia.data.local.files

import android.content.Context
import cromega.studio.measurepedia.enums.Languages
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale

class UserInfo(
    private val context: Context
) {
    private val userInfoFileName: String = "user_info.json"

    var language: Languages
    var darkTheme: Boolean
    var defaultMetricSystemUnitId: Int

    init {
        try { readJSONFile() }
        catch (e: Exception)
        {
            Locale.getDefault()
            updateUserInfo(jsonString = generateDefaultUserInfoJSON())
        }
        finally {
            val json: JSONObject = readJSONFile()
            language =
                Languages
                    .findFromLocaleAcronym(
                        localeAcronym = json.get("language") as String
                    )
            darkTheme = json.get("dark_theme") as Boolean
            defaultMetricSystemUnitId = json.get("default_metric_system_unit_id") as Int
        }
    }

    fun flushUpdateUserInfo() = updateUserInfo(jsonString = this.toString())

    private fun generateDefaultUserInfoJSON(): String
    {
        val locale: String = Locale.getDefault().language

        val selectedLanguage: Languages =
            Languages.findFromLocaleAcronym(localeAcronym = locale)

        return """{"language":"${selectedLanguage.localeAcronym}","dark_theme":false,"default_metric_system_unit_id":1}"""
    }

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
        """{"language":"${language.localeAcronym}","dark_theme":$darkTheme,"default_metric_system_unit_id":$defaultMetricSystemUnitId}"""
}