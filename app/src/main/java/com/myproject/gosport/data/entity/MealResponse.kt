package com.myproject.gosport.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals: List<Meal>,
) {
    @Entity(
        tableName = MEAL_TABLE_NAME, foreignKeys = [
            ForeignKey(
                entity = CategoryResponse.Category::class,
                parentColumns = ["categoryName"],
                childColumns = ["categoryName"],
                onDelete = CASCADE,
                onUpdate = CASCADE
            )
        ]
    )
    data class Meal(
        @PrimaryKey
        @ColumnInfo(name = "mealId")
        @SerializedName("idMeal")
        val id: String,
        @ColumnInfo(name = "categoryName")
        @SerializedName("strCategory")
        val categoryName: String,
        @ColumnInfo(name = "mealName")
        @SerializedName("strMeal")
        val name: String,
        @ColumnInfo(name = "mealImage")
        @SerializedName("strMealThumb")
        val image: String,
        @ColumnInfo(name = "mealDescription")
        @SerializedName("strInstructions")
        val description: String,
    )

    companion object {
        const val MEAL_TABLE_NAME = "mealsTable"
    }
}