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
 * Class MotorcyclesRoomDB.kt
 * Define the local database with a Motorcycles table with Room.
 *
 * @author Víctor Lamas
 */
@Database(entities = [Motorcycle::class], version = 1)
abstract class MotorcyclesRoomDB : RoomDatabase() {
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
     * Insert a favourite motorcycle in the local DB.
     * In case of conflict, it replaces the motorcycle.
     *
     * @param motorcycle Motorcycle marked as favourite.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavMotorcycle(motorcycle: Motorcycle)

    /**
     * Gets all motorcycles from local DB sorted by ascendant make.
     *
     * @return Ascendant sorted list of motorcycles.
     */
    @Query("SELECT * FROM Motorcycle ORDER BY make ASC")
    fun getFavMotorcyclesAsc(): Flow<List<Motorcycle>>

    /**
     * Gets all motorcycles from local DB sorted by descendant make.
     *
     * @return Descendant sorted list of motorcycles.
     */
    @Query("SELECT * FROM Motorcycle ORDER BY make DESC")
    fun getFavMotorcyclesDesc(): Flow<List<Motorcycle>>

    /**
     * Delete a motorcycle from the local DB.
     *
     * @param motorcycle The motorcycle to delete.
     */
    @Delete
    suspend fun deleteFavMotorcycle(motorcycle: Motorcycle)
}