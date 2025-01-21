package edu.pract5.apirestfree

import android.app.Application
import androidx.room.Room
import edu.pract5.apirestfree.data.MotorcyclesDB

/**
 * Class RoomApplication.kt
 * Initializes the local database with Room.
 *
 * @author Víctor Lamas
 */
class RoomApplication : Application() {
    lateinit var motorcyclesDB: MotorcyclesDB
        private set

    override fun onCreate() {
        super.onCreate()

        motorcyclesDB = Room.databaseBuilder(
            this,
            MotorcyclesDB::class.java,
            "Motorcycles-db"
        ).build()
    }
}