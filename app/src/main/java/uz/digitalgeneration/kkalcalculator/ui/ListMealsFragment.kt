package uz.digitalgeneration.kkalcalculator.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_list_meals.*
import uz.digitalgeneration.kkalcalculator.R
import uz.digitalgeneration.kkalcalculator.adapter.MealAdapter
import uz.digitalgeneration.kkalcalculator.database.MealDatabase
import uz.digitalgeneration.kkalcalculator.model.Meal
import uz.digitalgeneration.kkalcalculator.viewmodels.MealViewModelFactory
import uz.digitalgeneration.kkalcalculator.viewmodels.MealsViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ListMealsFragment : Fragment(R.layout.fragment_list_meals) {

    private lateinit var viewModel: MealsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val dataSource = MealDatabase.getInstance(requireActivity().application).mealDatabaseDao

        val viewModelFactory = MealViewModelFactory(
            dataSource = dataSource,
            application = requireActivity().application
        )

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).
            get(MealsViewModel::class.java)

//
//
//        val meals = ArrayList<Meal>()
//
//        for (i in 1..10) {
//
//            val date = Calendar.getInstance().time
//            meals.add(Meal(title = "asf$i", calory = 200, type = "a$i", date = date))
//        }

        meal_add_fab.setOnClickListener {
            //TODO show dialog to add meal

//            findNavController().navigate(R.id.action_listMealsFragment_to_recordMealFragment)

            for (i in 1..5) {
                val textStartDay1 = "$i-08-2020 00:00:00"

                val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

                val startOfDay = formatter.parse(textStartDay1)
                val meal = Meal(title = "asfa${i}", type = "afs", date = startOfDay, calory = 24 + i)
                viewModel.recordMeal(meal = meal)

            }


        }

        val mealAdapter = MealAdapter()

        meals_recycler.apply {
            adapter = mealAdapter
            setHasFixedSize(true)
        }

//        viewModel.meals.observe(viewLifecycleOwner, {
//            mealAdapter.submitList(it)
//        })

        viewModel.isFilterApplied.observe(viewLifecycleOwner, {

            if (it) {
                Log.d("mfff", "Tanlandi picker \n start is ${viewModel.startOfDay.value} and end is ${viewModel.endOfDay.value}")

                viewModel.getMealsForSelectedDate().observe(viewLifecycleOwner, { filteredList ->
                    mealAdapter.submitList(filteredList)
                })
            } else {
                Log.d("mfff", "ther is no filter")
                viewModel.meals.observe(viewLifecycleOwner, {fullList ->
                    mealAdapter.submitList(fullList)
                })
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_menu) {

            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(requireActivity().supportFragmentManager, "dataPicker")

            return true
        } else if (item.itemId == R.id.reset_filter_menu) {
            viewModel.isFilterApplied.value = false
        }

        return super.onOptionsItemSelected(item)
    }

}