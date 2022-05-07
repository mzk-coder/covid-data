package mohammed.zahid.khan.coviddata.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class CovidDataResponse(
    @SerializedName("cases_time_series")
    val casesTimeSeries : List<CaseData>,
    @SerializedName("statewise")
    val stateWise : List<StateData>,
    val tested : List<TestedData>
): Parcelable
