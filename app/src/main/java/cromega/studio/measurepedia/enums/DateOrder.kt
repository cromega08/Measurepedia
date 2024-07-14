package cromega.studio.measurepedia.enums

import androidx.annotation.StringRes
import cromega.studio.measurepedia.R

enum class DateOrder(val id: Int, @StringRes val textStringId: Int, val isDefault: Boolean)
{
    RECENT(id = 0, textStringId = R.string.recent, isDefault = false),
    OLDEST(id = 1, textStringId = R.string.oldest, isDefault = false),
    CREATION(id = 2, textStringId = R.string.creation, isDefault = true);
}