package uz.digitalgeneration.kkalcalculator.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import uz.digitalgeneration.kkalcalculator.database.MealDatabase
import uz.digitalgeneration.kkalcalculator.viewmodels.MealViewModelFactory
import uz.digitalgeneration.kkalcalculator.viewmodels.MealsViewModel
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: MealsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dataSource = MealDatabase.getInstance(requireActivity().application).mealDatabaseDao

        val viewModelFactory = MealViewModelFactory(
            dataSource = dataSource,
            application = requireActivity().application
        )

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MealsViewModel::class.java)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val textStartDay = "${dayOfMonth}-${month + 1}-${year} 00:00:00"
        val textEndDay = "${dayOfMonth}-${month + 1}-${year} 23:59:59"

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val startOfDay = formatter.parse(textStartDay)
        val endOfDay = formatter.parse(textEndDay)

        viewModel.startOfDay.value = startOfDay
        viewModel.endOfDay.value = endOfDay

        viewModel.isFilterApplied.value = true

    }

}