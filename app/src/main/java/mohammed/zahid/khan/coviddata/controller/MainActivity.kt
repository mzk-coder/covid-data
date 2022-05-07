package mohammed.zahid.khan.coviddata.controller

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mohammed.zahid.khan.coviddata.adapter.TabViewAdapter
import mohammed.zahid.khan.coviddata.databinding.ActivityMainBinding
import mohammed.zahid.khan.coviddata.model.data.CovidDataResponse
import mohammed.zahid.khan.coviddata.model.db.Database
import mohammed.zahid.khan.coviddata.network.RetrofitManager.apiInterface
import mohammed.zahid.khan.coviddata.utils.Utility.isInternetConnected

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isInternetConnected()){
            getDataOnline()
        }else{
            readOfflineData()
        }

    }

    private fun getDataOnline() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiInterface().getCovidData()
            if (response.isSuccessful && response.body() != null) {
                withContext(Main) {
                    setTabs(response.body()!!)
                }
            }
        }
    }

    private fun readOfflineData() {
        val db = Database(this)
        val covidData = CovidDataResponse(
            tested = db.readSavedTest(),
            casesTimeSeries = db.readSavedCase(),
            stateWise = db.readSavedState()
        )
        setTabs(covidData)
    }


    private fun setTabs(res: CovidDataResponse) {
        val sectionsPagerAdapter = TabViewAdapter(this, supportFragmentManager, res)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

    }
}