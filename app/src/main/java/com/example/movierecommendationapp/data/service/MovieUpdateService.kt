package com.example.movierecommendationapp.data.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movierecommendationapp.data.source.local.MovieDao
import com.example.movierecommendationapp.data.source.local.model.MovieModel
import com.example.movierecommendationapp.data.source.remote.MovieApiService
import com.example.movierecommendationapp.data.source.remote.model.getDataMovieProperties
import com.example.movierecommendationapp.data.source.remote.model.toMovieModelOffline
import com.example.movierecommendationapp.domain.model.Movie
import com.example.movierecommendationapp.domain.repository.MovieRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@AndroidEntryPoint
class MovieUpdateService : Service() {

    @Inject
    lateinit var apiService: MovieApiService

    @Inject
    lateinit var dao: MovieDao

    @Inject
    lateinit var context: Context

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scheduleService()
        startUpdate()
        return START_STICKY
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun startUpdate() {
        GlobalScope.launch(Dispatchers.IO) {
            val movies = apiService.getPopularMovies()
            if (movies.isSuccessful) {
                val movieDto = movies.body()?.results
                movieDto?.let {
                    for (movieDto in it) {
                        val saveDb = movieDto.toMovieModelOffline()
                        dao.insertMovie(saveDb)
                    }
                }
            }
            Log.d("MovieUpdateService", "Data updated at: ${System.currentTimeMillis()}")

            val intent = Intent("DATA_UPDATED")
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            delay(60000)
            stopSelf()
        }
    }

    private fun scheduleService() {
        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MovieUpdateService::class.java)
        val pendingIntent = PendingIntent.getService(
            context, 0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the service to run every minute
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 1)

        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }
}