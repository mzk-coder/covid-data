package mohammed.zahid.khan.coviddata.controller

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mohammed.zahid.khan.coviddata.adapter.CovidDataAdapter
import mohammed.zahid.khan.coviddata.databinding.DetailDialogBinding
import mohammed.zahid.khan.coviddata.databinding.FragmentMainBinding
import mohammed.zahid.khan.coviddata.model.data.*
import mohammed.zahid.khan.coviddata.model.db.Database


class TabFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        val covidDataResponse = arguments?.getParcelable(ARG_DATA) as CovidDataResponse?
        when(index){
            1-> setAdapter(covidDataResponse?.casesTimeSeries?: listOf())
            2-> setAdapter(covidDataResponse?.stateWise?: listOf())
            3-> setAdapter(covidDataResponse?.tested?: listOf())
        }


    }

    private fun setAdapter(list: List<Any>) {
        val covidDataAdapter = CovidDataAdapter(list, requireContext(), Database(requireContext())){data->
            when(data){
                is CaseData->{
                    val infoList = arrayListOf(
                        MetaData("Date", data.date),
                        MetaData("Total Confirmed", data.totalConfirmed),
                        MetaData("Total Deceased", data.totalDeceased),
                        MetaData("Total Recovered", data.totalRecovered),

                    )
                    showDetailDialog(infoList)
                }

                is StateData->{
                    val infoList = arrayListOf(
                        MetaData("State", data.state),
                        MetaData("Deaths", data.deaths),
                        MetaData("Active", data.active),
                        MetaData("Recovered", data.recovered),

                    )
                    showDetailDialog(infoList)
                }

                is TestedData->{
                    val infoList = arrayListOf(
                        MetaData("Tested As Of", data.testedAsOf),
                        MetaData("Daily RTPCR Test Collected", data.dailyRtpcrTestCollected),
                        MetaData("Sample Reported Today", data.sampleReportedToday),
                        MetaData("Total Doses Administered", data.totalDosesAdministered),
                    )
                    showDetailDialog(infoList)
                }

            }
        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvCovidData.apply {
            adapter = covidDataAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_DATA = "covid_data"

        @JvmStatic
        fun newInstance(sectionNumber: Int, covidData: CovidDataResponse): TabFragment {
            return TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putParcelable(ARG_DATA, covidData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDetailDialog(list: List<MetaData>){

        val dialogBinding = DetailDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogBinding.root)

        val detailAdapter = CovidDataAdapter(list, requireContext(), null) {}
        dialogBinding.rvDetails.apply {
            adapter = detailAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}