package edu.pract5.apirestfree

import android.app.Application
import androidx.room.Room
import edu.pract5.apirestfree.data.MotorcyclesRoomDB

/**
 * Class RoomApplication.kt
 * Initializes the local database with Room.
 *
 * @author Víctor Lamas
 */
class RoomApplication : Application() {
    lateinit var motorcyclesDB: MotorcyclesRoomDB
        private set

    override fun onCreate() {
        super.onCreate()

        motorcyclesDB = Room.databaseBuilder(
            this,
            MotorcyclesRoomDB::class.java,
            "Motorcycles-db"
        ).build()
    }
}