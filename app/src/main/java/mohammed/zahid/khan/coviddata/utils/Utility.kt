package mohammed.zahid.khan.coviddata.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

object Utility {

    fun Context.isInternetConnected(): Boolean{
        val connectManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectManager?.activeNetworkInfo
        return networkInfo!=null
    }

    fun Context.showToast(msg : String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}