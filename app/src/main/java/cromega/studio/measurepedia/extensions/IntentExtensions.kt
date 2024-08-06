package cromega.studio.measurepedia.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun Intent.putExtra(key: String, data: Any)
{
    when(data)
    {
        is Bundle -> this.putExtra(key, data)
        is Parcelable -> this.putExtra(key, data)
        is Serializable -> this.putExtra(key, data)
        is CharSequence -> this.putExtra(key, data)
    }
}
