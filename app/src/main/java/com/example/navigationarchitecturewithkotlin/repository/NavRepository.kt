package com.example.navigationarchitecturewithkotlin.repository


import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.navigationarchitecturewithkotlin.model.DataModel
import com.example.navigationarchitecturewithkotlin.model.MovieData
import com.example.navigationarchitecturewithkotlin.repository.database.AppDatabase
import com.example.navigationarchitecturewithkotlin.repository.network.ApiInterface
import com.example.navigationarchitecturewithkotlin.repository.network.NetworkState
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NavRepository(private val mDatabase: AppDatabase, private val serverApi: ApiInterface, private val mAppExecutors: AppExecutors) {

    private var movieListLiveData: MediatorLiveData<List<MovieData>>? = null
    private var movieDetailResponse: MutableLiveData<MovieData>? = null
    private var networkState: MutableLiveData<NetworkState>
    private val gson: Gson

    //Static methods and variables
    init {
        movieListLiveData = MediatorLiveData()
        networkState = MediatorLiveData<NetworkState>()
        gson = Gson()
    }

    companion object {
        private var sInstance: NavRepository? = null
        fun getInstance(mDatabase: AppDatabase, serverApi: ApiInterface, mAppExecutors: AppExecutors): NavRepository? {
            if (sInstance == null) {
                synchronized(NavRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = NavRepository(mDatabase, serverApi, mAppExecutors)
                    }
                }
            }
            return sInstance
        }
    }

    fun getNetworkState(): MutableLiveData<NetworkState> {
        return networkState
    }
    //Network call to get Movie List

    fun getMovieList(sortBy: String, isConnected: Boolean): MediatorLiveData<List<MovieData>> {
        movieListLiveData = MediatorLiveData()
        try {
            networkState.postValue(NetworkState.LOADING)
            if (isConnected) {
                serverApi.getMovieList("d301f52a37788167ffa610f8f0be8477",sortBy).enqueue(object : Callback<DataModel> {
                    override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                        if (response.isSuccessful) {
                            Log.d("*** onResponse2 ** > ", response.body()!!.toString())
                            if (!TextUtils.isEmpty(response.body()!!.toString())) {
                                insertMovieListInDB(response)
                                movieListLiveData!!.postValue(response.body()!!.sampleDataInner)
                            }
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState(NetworkState.Status.FAILED, "Something went wrong"))

                        }
                    }

                    override fun onFailure(call: Call<DataModel>, t: Throwable) {
                        Log.d("*** onFailure ** > ", t.message)
                        getMovieListFromDB()
                    }
                })
            } else {
                getMovieListFromDB()
            }
        } catch (e: Exception) {
            networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.message!!))
        }
        return movieListLiveData as MediatorLiveData<List<MovieData>>
    }

    private fun getMovieListFromDB() {
        //Fetch data from database
        mAppExecutors.diskIO().execute {
            val movieList = mDatabase.movieDao().fetchAllMovies()
            movieListLiveData!!.postValue(movieList)
        }
    }

    private fun insertMovieListInDB(response: Response<DataModel>) {
        mAppExecutors.diskIO().execute {
            if (response.body() != null && response.body()!!.sampleDataInner.isNotEmpty()) {
                mDatabase.movieDao().insertAll(response.body()!!.sampleDataInner)
            }
        }
    }

// Get Movie Details from network call

    fun getMovieDetail(movieID: String, isConnected: Boolean): LiveData<MovieData> {
        movieDetailResponse = MediatorLiveData<MovieData>()
        try {
            networkState.postValue(NetworkState.LOADING)
            if (isConnected) {
                serverApi.getMovieDetails(movieID, "acd1f9d9f8a4544107e17ee38209fc8c").enqueue(object : Callback<MovieData> {
                    override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                        if (response.isSuccessful) {
                            Log.d("*** onResponse2 ** > ", response.body()!!.toString())
                            insertMovieDetailsInDB(response)
                            (movieDetailResponse as MediatorLiveData<MovieData>).postValue(response.body())
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState(NetworkState.Status.FAILED, "Something went wrong"))
                        }
                    }

                    override fun onFailure(call: Call<MovieData>, t: Throwable?) {
                        getMovieDetailsFromDB(movieID)
                    }
                })
            } else {
                getMovieDetailsFromDB(movieID)
            }
        } catch (e: Exception) {
            networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.message!!))
        }
        return movieDetailResponse as MediatorLiveData<MovieData>
    }

    private fun insertMovieDetailsInDB(response: Response<MovieData>) {
        mAppExecutors.diskIO().execute {
            if (response.body() != null) {
                mDatabase.movieDao().insert(response.body()!!)
            }
        }
    }

    private fun getMovieDetailsFromDB(movieID: String) {
        //Fetch data from database
        mAppExecutors.diskIO().execute {
            val movie = mDatabase.movieDao().fetchMovieDetails(movieID)
            (movieDetailResponse as MediatorLiveData<MovieData>).postValue(movie)
        }
    }
}
