package uz.digitalgeneration.kkalcalculator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.digitalgeneration.kkalcalculator.model.Meal
import java.util.*

@Dao
interface MealDao {

    @Insert
    fun insert(meal: Meal): Long

    @Query("SELECT * FROM meals ORDER BY date DESC")
    fun getMeals(): LiveData<List<Meal>>

    @Query("SELECT * FROM meals WHERE date BETWEEN :startOfDay and :endOfDay ORDER BY date DESC")
    fun getMealsForSelectedDate(startOfDay: Date, endOfDay: Date): LiveData<List<Meal>>
}