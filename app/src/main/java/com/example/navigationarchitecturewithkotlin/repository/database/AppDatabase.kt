package com.example.navigationarchitecturewithkotlin.repository.database

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.repository.AppExecutors
import com.example.navigationarchitecturewithkotlin.repository.database.dao.MovieDao

@Database(entities = [MovieData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    abstract fun movieDao(): MovieDao

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    companion object {
        private var sInstance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "movie_db"

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = buildDatabase(context.applicationContext, executors)
                        sInstance!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return sInstance!!
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context, executors: AppExecutors): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            executors.diskIO().execute {
                                // Generate the data for pre-population
                                val database = getInstance(appContext, executors)
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated()
                            }
                        }
                    }).build()
        }

    }

}
