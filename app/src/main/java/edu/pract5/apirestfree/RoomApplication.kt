package edu.pract5.apirestfree

import android.app.Application
import androidx.room.Room

/**
 * Class RoomApplication.kt
 * Initializes the local database with Room.
 * @author Víctor Lamas
 */
class RoomApplication : Application() {
    private lateinit var motorcyclesDb: MotorcyclesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        motorcyclesDb = Room.databaseBuilder(
            this,
            MotorcyclesDatabase::class.java,
            "Motorcycles-db"
        ).build()
    }
}