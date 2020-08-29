package uz.digitalgeneration.kkalcalculator.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("formatDate")
fun TextView.formatDate(date: Date?) {

    val formatter = SimpleDateFormat("dd-MMM, yyyy HH:mm")
    text = formatter.format(date)

}

@BindingAdapter("formatCalory")
fun TextView.formatCalory(calory: Int) {
    text = calory.toString()
}