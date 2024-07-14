package cromega.studio.measurepedia.enums

import androidx.annotation.StringRes
import cromega.studio.measurepedia.R

enum class MeasuredOrder(val id: Int, @StringRes val textStringId: Int, val isDefault: Boolean)
{
    MEASURED(id = 0, textStringId = R.string.measured, isDefault = false),
    NOT_MEASURED(id = 1, textStringId = R.string.not_measured, isDefault = false),
    MEASURED_OR_NOT(id = 2, textStringId = R.string.measured_or_not, isDefault = true);
}
