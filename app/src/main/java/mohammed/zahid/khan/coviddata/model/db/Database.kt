package mohammed.zahid.khan.coviddata.model.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mohammed.zahid.khan.coviddata.model.data.CaseData
import mohammed.zahid.khan.coviddata.model.data.StateData
import mohammed.zahid.khan.coviddata.model.data.TestedData
import mohammed.zahid.khan.coviddata.utils.Constants.CREATE_TABLE_CASE
import mohammed.zahid.khan.coviddata.utils.Constants.CREATE_TABLE_STATE
import mohammed.zahid.khan.coviddata.utils.Constants.CREATE_TABLE_TESTED
import mohammed.zahid.khan.coviddata.utils.Constants.DATABASE_NAME
import mohammed.zahid.khan.coviddata.utils.Constants.DB_VERSION
import mohammed.zahid.khan.coviddata.utils.Constants.DROP_CASE_TABLE
import mohammed.zahid.khan.coviddata.utils.Constants.DROP_STATE_TABLE
import mohammed.zahid.khan.coviddata.utils.Constants.DROP_TEST_TABLE
import mohammed.zahid.khan.coviddata.utils.Constants.READ_CASE_TABLE
import mohammed.zahid.khan.coviddata.utils.Constants.READ_STATE_TABLE
import mohammed.zahid.khan.coviddata.utils.Constants.READ_TEST_TABLE
import mohammed.zahid.khan.coviddata.utils.Constants.TABLE_CASES
import mohammed.zahid.khan.coviddata.utils.Constants.TABLE_STATE
import mohammed.zahid.khan.coviddata.utils.Constants.TABLE_TEST
import mohammed.zahid.khan.coviddata.utils.Constants.case_date
import mohammed.zahid.khan.coviddata.utils.Constants.state_name
import mohammed.zahid.khan.coviddata.utils.Constants.test_tested

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.apply {
                execSQL(CREATE_TABLE_CASE)
                execSQL(CREATE_TABLE_STATE)
                execSQL(CREATE_TABLE_TESTED)
            }

        }catch (exp : Exception){
            exp.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.apply {
            execSQL(CREATE_TABLE_CASE)
            execSQL(CREATE_TABLE_STATE)
            execSQL(CREATE_TABLE_TESTED)
            onCreate(this)
        }
    }

    fun addCase(caseData: CaseData) : Boolean{
        val contentValue = ContentValues()
        contentValue.apply {
            put(case_date, caseData.date)
            put("t_confirm", caseData.totalConfirmed)
            put("t_deceased", caseData.totalDeceased)
            put("t_recovered", caseData.totalRecovered)
        }
        val db = this.writableDatabase

        return try {
            val id = db.insert(TABLE_CASES, null, contentValue)
            id != -1L
        }catch (ex : Exception){
            ex.printStackTrace()
            false
        }
    }

    fun addTestData(data: TestedData) : Boolean{
        val contentValue = ContentValues()
        contentValue.apply {
            put(test_tested, data.testedAsOf)
            put("dailyRtpcrTestCollected", data.dailyRtpcrTestCollected)
            put("sampleReportedToday", data.sampleReportedToday)
            put("totalDosesAdministered", data.totalDosesAdministered)
            put("source", data.source)
        }
        val db = this.writableDatabase

        return try {
            val id = db.insert(TABLE_TEST, null, contentValue)
            id != -1L
        }catch (ex : Exception){
            ex.printStackTrace()
            false
        }
    }

    fun addStateData(data: StateData) : Boolean{

        val contentValue = ContentValues()
        contentValue.apply {
            put(state_name, data.state)
            put("s_active", data.active)
            put("s_deaths", data.deaths)
            put("s_recovered", data.recovered)
        }
        val db = this.writableDatabase

        return try {
            val id = db.insert(TABLE_STATE, null, contentValue)
            id != -1L
        }catch (ex : Exception){
            ex.printStackTrace()
            false
        }
    }


    fun readSavedCase() : List<CaseData>{
        val list = ArrayList<CaseData>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(READ_CASE_TABLE, null)
        if (cursor.count>0){
            if (cursor.moveToFirst()){
                do {
                    val data = CaseData(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                    )
                    list.add(data)
                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        return list
    }

    fun readSavedState() : List<StateData>{
        val list = ArrayList<StateData>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(READ_STATE_TABLE, null)
        if (cursor.count>0){
            if (cursor.moveToFirst()){
                do {
                    val data = StateData(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                    )
                    list.add(data)
                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        return list
    }

    fun readSavedTest() : List<TestedData>{
        val list = ArrayList<TestedData>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(READ_TEST_TABLE, null)
        if (cursor.count>0){
            if (cursor.moveToFirst()){
                do {
                    val data = TestedData(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                    )
                    list.add(data)
                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        return list
    }

    fun isTestSaved(testOn : String) : Boolean{
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TEST WHERE $test_tested = '$testOn'", null)
        val exist = cursor.count>0
        cursor.close()
        return exist
    }

    fun isStateSaved(state : String) : Boolean{
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_STATE WHERE $state_name = '$state'", null)
        val exist = cursor.count>0
        cursor.close()
        return exist
    }

    fun isCaseSaved(date : String) : Boolean{
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CASES WHERE $case_date = '$date'", null)
        val exist = cursor.count>0
        cursor.close()
        return exist
    }


    fun deleteCaseByDate(date : String) : Boolean{
        val db = this.writableDatabase
        return db.delete(TABLE_CASES, "$case_date = '$date'", null)>0
    }

    fun deleteStateByName(state : String) : Boolean{
        val db = this.writableDatabase
        return db.delete(TABLE_STATE, "$state_name = '$state'", null)>0
    }

    fun deleteTestByTestOf(testOf : String) : Boolean{
        val db = this.writableDatabase
        return db.delete(TABLE_TEST, "$test_tested = '$testOf'", null)>0
    }

    fun deleteAllCase() : Boolean{
        return try {
            val db = this.writableDatabase
            db.apply {
                execSQL(DROP_CASE_TABLE)
                execSQL(CREATE_TABLE_CASE)
            }
            true
        }catch (exc : Exception){
            exc.printStackTrace()
            false
        }
    }

    fun deleteAllState() : Boolean{
        return try {
            val db = this.writableDatabase
            db.apply {
                execSQL(DROP_STATE_TABLE)
                execSQL(CREATE_TABLE_STATE)
            }
            true
        }catch (exc : Exception){
            exc.printStackTrace()
            false
        }
    }

    fun deleteAllTest() : Boolean{
        return try {
            val db = this.writableDatabase
            db.apply {
                execSQL(DROP_TEST_TABLE)
                execSQL(CREATE_TABLE_TESTED)
            }
            true
        }catch (exc : Exception){
            exc.printStackTrace()
            false
        }
    }

}