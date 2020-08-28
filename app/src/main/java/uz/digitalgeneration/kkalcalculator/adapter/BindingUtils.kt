package uz.digitalgeneration.kkalcalculator.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("formatDate")
fun TextView.formatDate(date: Date?) {

    text = date.toString()

}

@BindingAdapter("formatCalory")
fun TextView.formatCalory(calory: Int) {
    text = calory.toString()
}