package com.myproject.gosport.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<Category>,
) {
    @Entity(tableName = CATEGORY_TABLE_NAME)
    data class Category(
        @ColumnInfo(name = "categoryId")
        @SerializedName("idCategory")
        val id: String,
        @PrimaryKey
        @ColumnInfo(name = "categoryName")
        @SerializedName("strCategory")
        val name: String,
        @ColumnInfo(name = "categoryImage")
        @SerializedName("strCategoryThumb")
        val image: String,
        @ColumnInfo(name = "categoryDescription")
        @SerializedName("strCategoryDescription")
        val description: String,
    )

    companion object {
        const val CATEGORY_TABLE_NAME = "categoriesTable"
    }
}
