package uz.digitalgeneration.kkalcalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_record_meal_fragment.*
import uz.digitalgeneration.kkalcalculator.R
import uz.digitalgeneration.kkalcalculator.database.MealDatabase
import uz.digitalgeneration.kkalcalculator.model.Meal
import uz.digitalgeneration.kkalcalculator.viewmodels.MealViewModelFactory
import uz.digitalgeneration.kkalcalculator.viewmodels.MealsViewModel
import java.text.SimpleDateFormat
import java.util.*

class DialogRecordMealFragment : BottomSheetDialogFragment() {

//    private var caloriesCount: Int = 0

    private lateinit var fullDate: Date

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_record_meal_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = MealDatabase.getInstance(requireActivity().application).mealDatabaseDao

        val viewModelFactory = MealViewModelFactory(
            dataSource = dataSource,
            application = requireActivity().application
        )

        val viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MealsViewModel::class.java)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        date_meal_text.text = "${day}-${month}-${year}"
        time_meal_text.text = "${hour}:${minute}"

//        decrease_calories_button.setOnClickListener {
//
//            val caloryCount = Integer.parseInt(viewModel.caloryText.value!!)
//
//            if (caloryCount != 0) {
//                viewModel.caloryText.value = "${caloryCount - 1}"
//            }
//        }
//
//        icrease_calories_button.setOnClickListener {
//            val caloryCount = Integer.parseInt(viewModel.caloryText.value!!)
//            Log.d("mfff", caloryCount.toString())
//            viewModel.caloryText.value = "${caloryCount + 1}"
//        }

        viewModel.caloryText.observe(viewLifecycleOwner, {
            if (it == "0") {
                meal_calory_edit.setText("Calory")
            }
            meal_calory_edit.setText(it)
        })

        var dateOnly: Date = c.time

//        date_meal_text.setOnClickListener {
//            val datePickerFragment = DatePickerFragment(DatePickerFragment.DateSetCallback { startOfDay, _ ->
//
//                dateOnly = startOfDay
//                //TODO format and get date only
//                date_meal_text.text = dateOnly.toString()
//                Log.d("mfff", "datepicker:year is ${dateOnly.year}")
//
//            })
//            datePickerFragment.show(requireActivity().supportFragmentManager, "dataPicker")
//        }


//        time_meal_text.setOnClickListener {
//            val timePickerFragment = TimePickerFragment(TimePickerFragment.TimeSetCallback { hour, minute ->
//
//                time_meal_text.text = "${hour}:${minute}"
//
//                Log.d("mfff", "timePicker:year is ${dateOnly.year}")
//
//                val fullDateText = "${dateOnly.date}-${dateOnly.month + 1}-${dateOnly.year} ${hour}:${minute}:00"
//
//                val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
//
//                fullDate = formatter.parse(fullDateText)
//
//            }).show(requireActivity().supportFragmentManager, "timePicker")
//        }

        done_button.setOnClickListener {



            val meal = Meal(
                title = meal_title_edit.text.toString(),
                calory = Integer.parseInt(meal_calory_edit.text.toString()),
                date = dateOnly,
                type = "asf"
            )

            viewModel.recordMeal(meal)

            dialog?.dismiss()
        }

    }

}