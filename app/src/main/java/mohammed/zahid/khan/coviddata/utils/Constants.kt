package mohammed.zahid.khan.coviddata.utils

object Constants {

    const val REFERENCE = "mohammed.zahid.khan.coviddata"

    const val DATABASE_NAME = "covid_db"
    const val DB_VERSION = 2
    const val TABLE_CASES = "cases"
    const val TABLE_STATE = "state"
    const val TABLE_TEST = "tested"

    const val case_date = "case_date"
    const val state_name = "state"
    const val test_tested = "testedasof"


    const val CREATE_TABLE_CASE = "CREATE TABLE IF NOT EXISTS $TABLE_CASES " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, $case_date TEXT, " +
            "t_confirm TEXT, t_deceased TEXT, t_recovered TEXT)"


    const val CREATE_TABLE_STATE = "CREATE TABLE IF NOT EXISTS $TABLE_STATE " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, $state_name TEXT, " +
            "s_deaths TEXT, s_active TEXT, s_recovered TEXT)"


    const val CREATE_TABLE_TESTED = "CREATE TABLE IF NOT EXISTS $TABLE_TEST " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, $test_tested TEXT, " +
            "dailyRtpcrTestCollected TEXT, sampleReportedToday TEXT, " +
            "totalDosesAdministered TEXT, source TEXT)"


    const val DROP_CASE_TABLE = "DROP TABLE IF EXISTS $TABLE_CASES"
    const val DROP_STATE_TABLE = "DROP TABLE IF EXISTS $TABLE_STATE"
    const val DROP_TEST_TABLE = "DROP TABLE IF EXISTS $TABLE_TEST"

    const val READ_CASE_TABLE = "SELECT * FROM $TABLE_CASES ORDER BY id ASC"
    const val READ_STATE_TABLE = "SELECT * FROM $TABLE_STATE ORDER BY id ASC"
    const val READ_TEST_TABLE = "SELECT * FROM $TABLE_TEST ORDER BY id ASC"




}