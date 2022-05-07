package mohammed.zahid.khan.coviddata.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StateData(
    val state: String,
    val deaths: String,
    val active: String,
    val recovered: String,
): Parcelable

