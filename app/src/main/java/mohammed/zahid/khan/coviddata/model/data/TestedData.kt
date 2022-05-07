package mohammed.zahid.khan.coviddata.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class TestedData(
    @SerializedName("testedasof")
    val testedAsOf: String,

    @SerializedName("dailyrtpcrsamplescollectedicmrapplication")
    val dailyRtpcrTestCollected: String,

    @SerializedName("samplereportedtoday")
    val sampleReportedToday: String,

    @SerializedName("totaldosesadministered")
    val totalDosesAdministered: String,

    val source: String
): Parcelable
