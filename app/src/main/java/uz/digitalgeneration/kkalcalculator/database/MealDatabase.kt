package uz.digitalgeneration.kkalcalculator.database

import android.content.Context
import androidx.room.*
import uz.digitalgeneration.kkalcalculator.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MealDatabase : RoomDatabase() {

    abstract val mealDatabaseDao: MealDao

    companion object {

        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MealDatabase::class.java,
                        "meal_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

    }

}