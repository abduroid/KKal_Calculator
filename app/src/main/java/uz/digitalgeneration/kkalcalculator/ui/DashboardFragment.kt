package uz.digitalgeneration.kkalcalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_dashboard.*
import uz.digitalgeneration.kkalcalculator.R
import uz.digitalgeneration.kkalcalculator.database.MealDatabase
import uz.digitalgeneration.kkalcalculator.viewmodels.MealViewModelFactory
import uz.digitalgeneration.kkalcalculator.viewmodels.MealsViewModel
import java.util.*

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = MealDatabase.getInstance(requireActivity().application).mealDatabaseDao

        val viewModelFactory = MealViewModelFactory(
            dataSource = dataSource,
            application = requireActivity().application
        )

        val viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MealsViewModel::class.java)

        val today = Calendar.getInstance().time
        Log.d("mfff", "today is $today")

        viewModel.readMealsForSelectedDate(today).observe(viewLifecycleOwner, {
            Log.d("mfff","read list size is ${it.size}")
            today_calories_text.text = viewModel.calculateCaloriesOfList(it).toString()
        })

//        today_calories_text.text = viewModel.calculateCaloriesOfDay(today).toString()

        today_calories_text.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_listMealsFragment)
        }

    }
}