package cromega.studio.measurepedia.ui.activities.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import cromega.studio.measurepedia.enums.DateOrder
import cromega.studio.measurepedia.enums.MeasuredOrder

internal object HomeState
{
    private lateinit var searchText: MutableState<String>
    private lateinit var dateFilterExpanded: MutableState<Boolean>
    private lateinit var measuredFilterExpanded: MutableState<Boolean>
    private lateinit var dateOrderOption: MutableState<DateOrder>
    private lateinit var measuredOrderOption: MutableState<MeasuredOrder>

    @Composable
    fun initialize()
    {
        searchText = rememberSaveable { mutableStateOf("") }
        dateFilterExpanded = rememberSaveable { mutableStateOf(false) }
        measuredFilterExpanded = rememberSaveable { mutableStateOf(false) }
        dateOrderOption = rememberSaveable { mutableStateOf(DateOrder.CREATION) }
        measuredOrderOption = rememberSaveable { mutableStateOf(MeasuredOrder.MEASURED_OR_NOT) }
    }

    fun getSearchText(): String = searchText.value

    fun setSearchText(text: String) = run { searchText.value = text }

    fun getDateFilterExpanded(): Boolean = dateFilterExpanded.value

    fun setDateFilterExpanded(expanded: Boolean) = run { dateFilterExpanded.value = expanded }

    fun invertDateFilterExpanded() = run { dateFilterExpanded.value = !dateFilterExpanded.value }

    fun getMeasuredFilterExpanded(): Boolean = measuredFilterExpanded.value

    fun setMeasuredFilterExpanded(expanded: Boolean) = run { measuredFilterExpanded.value = expanded }

    fun invertMeasuredFilterExpanded() = run { measuredFilterExpanded.value = !measuredFilterExpanded.value }

    fun getDateOrderOption(): DateOrder = dateOrderOption.value

    fun setDateOrderOption(dateOrder: DateOrder) = run { dateOrderOption.value = dateOrder }

    fun getMeasuredOrderOption(): MeasuredOrder = measuredOrderOption.value

    fun setMeasuredOrderOption(measuredOrder: MeasuredOrder) = run { measuredOrderOption.value = measuredOrder }
}