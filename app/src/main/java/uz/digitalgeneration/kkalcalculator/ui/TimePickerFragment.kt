package uz.digitalgeneration.kkalcalculator.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import uz.digitalgeneration.kkalcalculator.database.MealDatabase
import uz.digitalgeneration.kkalcalculator.viewmodels.MealViewModelFactory
import uz.digitalgeneration.kkalcalculator.viewmodels.MealsViewModel
import java.util.*
import kotlin.math.min

class TimePickerFragment(private val onTimeSetListener: TimeSetCallback) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var viewModel: MealsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val dataSource = MealDatabase.getInstance(requireActivity().application).mealDatabaseDao

        val viewModelFactory = MealViewModelFactory(
            dataSource = dataSource,
            application = requireActivity().application
        )

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MealsViewModel::class.java)

        return TimePickerDialog(requireActivity(), this, hour, minute, true)

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val hourAndMinut = "${hourOfDay}:${minute}"

        onTimeSetListener.timeSetListener(hourOfDay, minute)
    }

    class TimeSetCallback(val timeSetListener: (hour: Int, minute: Int) -> Unit) {
        fun onTimeSet(hour: Int, minute: Int) = timeSetListener(hour,minute)
    }

}