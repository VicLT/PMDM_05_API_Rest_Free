package edu.pract5.apirestfree.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import edu.pract5.apirestfree.models.Motorcycle
import kotlinx.coroutines.flow.Flow

/**
 * Class MotorcyclesDB.kt
 * Define the local database with a Motorcycles table with Room.
 *
 * @author Víctor Lamas
 */
@Database(entities = [Motorcycle::class], version = 1)
abstract class MotorcyclesDB : RoomDatabase() {
    abstract fun motorcyclesDao(): MotorcyclesDao
}

/**
 * DAO implementations for child classes.
 *
 * @author Víctor Lamas
 */
@Dao
interface MotorcyclesDao {
    /**
     * Insert a motorcycle in the local DB.
     * In case of conflict, it replaces the motorcycle.
     *
     * @param motorcycle Motorcycle that wants to be deleted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocalMotorcycle(motorcycle: Motorcycle)

    //@Query("SELECT * FROM Motorcycle WHERE model = :model")
    //fun getLocalMotorcyclesByModel(model: String): Flow<List<Motorcycle>>

    /**
     * Gets all deleted motorcycles from local DB sorted by ascendant make.
     *
     * @return Ascendant sorted list of deleted motorcycles.
     */
    @Query("SELECT * FROM Motorcycle ORDER BY model ASC")
    fun getLocalMotorcyclesSortedByModelAsc(): Flow<List<Motorcycle>>

    /**
     * Gets all deleted motorcycles from local DB sorted by descendant make.
     *
     * @return Descendant sorted list of deleted motorcycles.
     */
    @Query("SELECT * FROM Motorcycle ORDER BY model DESC")
    fun getLocalMotorcyclesSortedByModelDesc(): Flow<List<Motorcycle>>

    /**
     * Deletes a motorcycle from the local DB.
     *
     * @param motorcycle The motorcycle to delete.
     */
    @Delete
    suspend fun deleteLocalMotorcycle(motorcycle: Motorcycle)
}