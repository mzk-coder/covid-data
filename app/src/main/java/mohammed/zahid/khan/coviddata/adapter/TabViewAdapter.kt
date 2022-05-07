package mohammed.zahid.khan.coviddata.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import mohammed.zahid.khan.coviddata.R
import mohammed.zahid.khan.coviddata.controller.TabFragment
import mohammed.zahid.khan.coviddata.model.data.CovidDataResponse

private val TAB_TITLES = arrayOf(
    R.string.cases,
    R.string.states,
    R.string.test
)


class TabViewAdapter(private val context: Context, fm: FragmentManager, val covidData: CovidDataResponse) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return TabFragment.newInstance(position + 1, covidData)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}