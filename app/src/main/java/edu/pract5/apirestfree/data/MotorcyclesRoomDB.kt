package edu.pract5.apirestfree.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import edu.pract5.apirestfree.models.Motorcycle

/**
 * Class MotorcyclesRoomDB.kt
 * Define a local database with the Motorcycles table with Room.
 *
 * @author Víctor Lamas
 */
@Database(entities = [Motorcycle::class], version = 1)
abstract class MotorcyclesRoomDB : RoomDatabase() {
    abstract fun motorcyclesDao(): MotorcyclesDao
}

@Dao
interface MotorcyclesDao {
    /**
     * Inserts a list of motorcycles into the database.
     * In case of conflict, it replaces the city.
     *
     * @param motorcycles The list of motorcycles to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotorcycle(motorcycles: List<Motorcycle>)

    /**
     * Gets all the motorcycles from the database sorted by make.
     *
     * @return List of motorcycles.
     */
    @Query("SELECT * FROM motorcycle ORDER BY make ASC")
    suspend fun getMotorcycles(): List<Motorcycle>

    /**
     * Gets the motorcycles that match the model from the database sorted by model.
     *
     * @param model The model of the motorcycle to search.
     * @return List of motorcycles that match the model and sorted by same property.
     */
    @Query("SELECT * FROM motorcycle WHERE model LIKE :model ORDER BY model ASC")
    suspend fun getMotorcyclesByModel(model: String): List<Motorcycle>
}