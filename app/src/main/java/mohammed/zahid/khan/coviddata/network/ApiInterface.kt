package mohammed.zahid.khan.coviddata.network

import mohammed.zahid.khan.coviddata.model.data.CovidDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/data.json")
    suspend fun getCovidData(): Response<CovidDataResponse>


}