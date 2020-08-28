package uz.digitalgeneration.kkalcalculator.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import uz.digitalgeneration.kkalcalculator.database.MealDao
import uz.digitalgeneration.kkalcalculator.model.Meal
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

    fun getMealsForSelectedDate() = database.getMealsForSelectedDate(startOfDay = startOfDay.value!!, endOfDay = endOfDay.value!!)

    val meals = database.getMeals()

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