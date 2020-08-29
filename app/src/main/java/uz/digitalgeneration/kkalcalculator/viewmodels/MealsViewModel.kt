package uz.digitalgeneration.kkalcalculator.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import uz.digitalgeneration.kkalcalculator.database.MealDao
import uz.digitalgeneration.kkalcalculator.model.Meal
import uz.digitalgeneration.kkalcalculator.util.DateWork
import java.util.*

class MealsViewModel(
    val database: MealDao,
    application: Application
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val startOfDay = MutableLiveData<Date>()
    val endOfDay = MutableLiveData<Date>()


    /// New record stuff

    val newRecord = MutableLiveData<Meal>()

    val caloryText = MutableLiveData<String>("0")



    val isFilterApplied = MutableLiveData(false)

    fun readMealsForSelectedDate(selectedDate: Date): LiveData<List<Meal>> {
        val pair = DateWork().getStartAndEndOfTheDate(selectedDate)


        return database.getMealsForSelectedDate(startOfDay = pair.first, endOfDay = pair.second)
    }

    fun getMealsForSelectedDate() = database.getMealsForSelectedDate(startOfDay = startOfDay.value!!, endOfDay = endOfDay.value!!)

    val meals = database.getMeals()

    fun calculateCaloriesOfList(meals: List<Meal>): Int {

        var work = 0

        for (meal in meals) {
            work += meal.calory
        }
        Log.d("mfff", "calory overall: $work")

        return work
    }

    fun calculateCaloriesOfDay(selectedDate: Date): Int {

        var calories = 0

        Log.d("mfff", "selected Date is ${selectedDate}")

        val pair = DateWork().getStartAndEndOfTheDate(selectedDate)
        Log.d("mfff", "start date is ${pair.first} end date is ${pair.second}")

        val entriesOfDate = database.getMealsForSelectedDate(startOfDay = pair.first, endOfDay = pair.second).value

        Log.d("mfff", "The list size is${entriesOfDate?.size}")
        if (entriesOfDate != null) {
            for (meal in entriesOfDate) {
                calories + meal.calory
            }
        }

        return calories
    }

    fun recordMeal(meal: Meal) {
        uiScope.launch {
            insertToRoom(meal)
        }
    }

    private suspend fun insertToRoom(meal: Meal) {
        withContext(Dispatchers.IO) {
            database.insert(meal)
        }
    }

}