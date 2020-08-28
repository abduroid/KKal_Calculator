package uz.digitalgeneration.kkalcalculator.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dialog_record_meal_fragment.*
import uz.digitalgeneration.kkalcalculator.R
import uz.digitalgeneration.kkalcalculator.database.MealDatabase
import uz.digitalgeneration.kkalcalculator.model.Meal
import uz.digitalgeneration.kkalcalculator.viewmodels.MealViewModelFactory
import uz.digitalgeneration.kkalcalculator.viewmodels.MealsViewModel
import java.util.*

class RecordMealFragment : Fragment(R.layout.dialog_record_meal_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = MealDatabase.getInstance(requireActivity().application).mealDatabaseDao

        val viewModelFactory = MealViewModelFactory(
            dataSource = dataSource,
            application = requireActivity().application
        )

        val viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MealsViewModel::class.java)

        val currentDate = Calendar.getInstance().time

//        meal_date_text.text = currentDate.toString()

        done_button.setOnClickListener {
            val mealTitle = meal_title_edit.text.toString()
            val mealCalory = Integer.parseInt(meal_calory_edit.text.toString())

            val meal = Meal(title = mealTitle, calory = mealCalory, date = currentDate, type = "as")

            viewModel.recordMeal(meal)

            findNavController().navigate(R.id.action_recordMealFragment_to_listMealsFragment)


        }

    }
}