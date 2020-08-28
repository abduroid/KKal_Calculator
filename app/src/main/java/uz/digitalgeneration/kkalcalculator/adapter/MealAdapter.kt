package uz.digitalgeneration.kkalcalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.digitalgeneration.kkalcalculator.databinding.ItemMealBinding
import uz.digitalgeneration.kkalcalculator.model.Meal

class MealAdapter : ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MealViewHolder private constructor(private val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meal) {
            binding.meal = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MealViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMealBinding.inflate(layoutInflater, parent, false)

                return MealViewHolder(binding)
            }
        }

    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

}