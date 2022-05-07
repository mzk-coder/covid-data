package mohammed.zahid.khan.coviddata.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaseData(
    val date: String,
    @SerializedName("totalconfirmed")
    val totalConfirmed: String,

    @SerializedName("totaldeceased")
    val totalDeceased: String,

    @SerializedName("totalrecovered")
    val totalRecovered: String

): Parcelable
