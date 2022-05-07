package mohammed.zahid.khan.coviddata.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mohammed.zahid.khan.coviddata.R
import mohammed.zahid.khan.coviddata.controller.WebView
import mohammed.zahid.khan.coviddata.databinding.ItemListDialogBinding
import mohammed.zahid.khan.coviddata.databinding.ItemsCaseBinding
import mohammed.zahid.khan.coviddata.databinding.ItemsStateBinding
import mohammed.zahid.khan.coviddata.databinding.ItemsTestBinding
import mohammed.zahid.khan.coviddata.model.data.CaseData
import mohammed.zahid.khan.coviddata.model.data.MetaData
import mohammed.zahid.khan.coviddata.model.data.StateData
import mohammed.zahid.khan.coviddata.model.data.TestedData
import mohammed.zahid.khan.coviddata.model.db.Database
import mohammed.zahid.khan.coviddata.utils.Constants.REFERENCE
import mohammed.zahid.khan.coviddata.utils.Utility.isInternetConnected
import mohammed.zahid.khan.coviddata.utils.Utility.showToast

class CovidDataAdapter(
    private val list: List<Any>,
    val context: Context,
    val db: Database?,
    val onItemClick: (Any) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CaseHolder(private val binding: ItemsCaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CaseData) {
            binding.tvDate.text = context.getString(R.string.case_date, data.date)
            binding.tvTotalconfirmed.text =
                context.getString(R.string.case_confirmed, data.totalConfirmed)
            binding.tvTotaldeceased.text =
                context.getString(R.string.case_deceased, data.totalDeceased)
            binding.tvTotalrecovered.text =
                context.getString(R.string.case_recovered, data.totalRecovered)

            if (db?.isCaseSaved(data.date) == true) {
                binding.btnSave.iconTint =
                    ContextCompat.getColorStateList(context, R.color.active)
            } else {
                binding.btnSave.iconTint =
                    ContextCompat.getColorStateList(context, R.color.inactive)
            }


            binding.btnSave.setOnClickListener {
                if (db?.isCaseSaved(data.date) == true) {
                    if (db.deleteCaseByDate(data.date)) {
                        binding.btnSave.iconTint =
                            ContextCompat.getColorStateList(context, R.color.inactive)
                    }
                } else {
                    if (db?.addCase(data) == true) {
                        binding.btnSave.iconTint =
                            ContextCompat.getColorStateList(context, R.color.active)
                    }
                }
            }

            binding.root.setOnClickListener {
                onItemClick(data)
            }

        }
    }

    inner class StateHolder(private val binding: ItemsStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StateData) {
            binding.tvActive.text = context.getString(R.string.state_active, data.active)
            binding.tvDeath.text = context.getString(R.string.state_deaths, data.deaths)
            binding.tvRecover.text = context.getString(R.string.state_recovered, data.recovered)
            binding.tvState.text = context.getString(R.string.state_name, data.state)

            if (db?.isStateSaved(data.state) == true) {
                binding.btnSave.iconTint =
                    ContextCompat.getColorStateList(context, R.color.active)
            } else {
                binding.btnSave.iconTint =
                    ContextCompat.getColorStateList(context, R.color.inactive)
            }

            binding.root.setOnClickListener {
                onItemClick(data)
            }

            binding.btnSave.setOnClickListener {
                if (db?.isStateSaved(data.state) == true) {
                    if (db.deleteStateByName(data.state)) {
                        binding.btnSave.iconTint =
                            ContextCompat.getColorStateList(context, R.color.inactive)
                    }
                } else {
                    if (db?.addStateData(data) == true) {
                        binding.btnSave.iconTint =
                            ContextCompat.getColorStateList(context, R.color.active)
                    }
                }
            }
        }
    }

    inner class TestedHolder(private val binding: ItemsTestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TestedData) {
            binding.tvDailyRtpcr.text =
                context.getString(R.string.test_daily_rtpcr, data.dailyRtpcrTestCollected)
            binding.tvSampleReported.text =
                context.getString(R.string.test_report_today, data.sampleReportedToday)
            binding.tvTested.text = context.getString(R.string.test_as_of, data.testedAsOf)
            binding.tvTotalDoses.text =
                context.getString(R.string.test_total_does, data.totalDosesAdministered)


            if (db?.isTestSaved(data.testedAsOf) == true) {
                binding.btnSave.iconTint =
                    ContextCompat.getColorStateList(context, R.color.active)
            } else {
                binding.btnSave.iconTint =
                    ContextCompat.getColorStateList(context, R.color.inactive)
            }


            binding.btnSave.setOnClickListener {
                if (db?.isTestSaved(data.testedAsOf) == true) {
                    if (db.deleteTestByTestOf(data.testedAsOf)) {
                        binding.btnSave.iconTint =
                            ContextCompat.getColorStateList(context, R.color.inactive)
                    }
                } else {
                    if (db?.addTestData(data) == true) {
                        binding.btnSave.iconTint =
                            ContextCompat.getColorStateList(context, R.color.active)
                    }
                }
            }

            binding.root.setOnClickListener {
                onItemClick(data)
            }

            binding.btnSource.setOnClickListener {

                if (context.isInternetConnected()) {
                    if (data.source.isNotEmpty()&&data.source.startsWith("http")) {
                        val webIntent = Intent(context, WebView::class.java)
                        webIntent.putExtra(REFERENCE, data.source)
                        context.startActivity(webIntent)
                    }else{
                        context.showToast("Invalid Source")
                    }
                }else{
                    context.showToast("Source can not be viewed as app is running offline")
                }
            }
        }
    }

    inner class MetaDataHolder(private val binding: ItemListDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MetaData) {
            binding.tvLabel.text = data.label
            binding.tvValue.text = data.value
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CASE -> {
                CaseHolder(
                    ItemsCaseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            STATE -> {
                StateHolder(
                    ItemsStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            TESTED -> {
                TestedHolder(
                    ItemsTestBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            META_DATA -> {
                MetaDataHolder(
                    ItemListDialogBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw Exception("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CASE -> {
                val mHolder = holder as CaseHolder
                mHolder.bind(list[position] as CaseData)
            }

            STATE -> {
                val mHolder = holder as StateHolder
                mHolder.bind(list[position] as StateData)
            }

            TESTED -> {
                val mHolder = holder as TestedHolder
                mHolder.bind(list[position] as TestedData)
            }
            META_DATA->{
                val mHolder = holder as MetaDataHolder
                mHolder.bind(list[position] as MetaData)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is CaseData -> CASE
            is StateData -> STATE
            is TestedData -> TESTED
            is MetaData -> META_DATA
            else -> throw Exception("Invalid view type")
        }
    }

    companion object {
        const val CASE = 9
        const val STATE = 8
        const val TESTED = 7
        const val META_DATA = 6
        private const val TAG = "CovidDataAdapter"
    }
}