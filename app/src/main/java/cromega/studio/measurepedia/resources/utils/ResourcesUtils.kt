package cromega.studio.measurepedia.resources.utils

import android.content.res.Resources
import androidx.annotation.StringRes

object ResourcesUtils
{
    private var resources: Resources? = null

    fun updateInstance(resources: Resources)
    {
        this.resources = resources
    }

    fun getString(@StringRes stringId: Int): String =
        resources!!.getString(stringId)
}