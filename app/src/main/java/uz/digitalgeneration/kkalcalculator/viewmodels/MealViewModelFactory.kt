package uz.digitalgeneration.kkalcalculator.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.digitalgeneration.kkalcalculator.database.MealDao

class MealViewModelFactory(private val dataSource: MealDao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsViewModel::class.java)) {
            return MealsViewModel(database = dataSource, application = application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}