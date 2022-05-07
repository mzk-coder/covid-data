package mohammed.zahid.khan.coviddata.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mohammed.zahid.khan.coviddata.databinding.ActivityWebBinding
import mohammed.zahid.khan.coviddata.utils.Constants.REFERENCE

class WebView : AppCompatActivity(){

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val url = intent.getStringExtra(REFERENCE)
        url?.let {
            binding.webView.loadUrl(it)
        }

    }
}