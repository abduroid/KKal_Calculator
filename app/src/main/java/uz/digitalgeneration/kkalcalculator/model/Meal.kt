package uz.digitalgeneration.kkalcalculator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "meals")
data class Meal(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "calory")
    val calory: Int,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "date")
    val date: Date?,
)