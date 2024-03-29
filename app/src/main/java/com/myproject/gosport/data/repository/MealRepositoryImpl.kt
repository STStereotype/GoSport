package com.myproject.gosport.data.repository

import androidx.room.withTransaction
import com.myproject.gosport.data.api.RemoteDataSource
import com.myproject.gosport.data.db.GoDatabase
import com.myproject.gosport.data.entity.CategoryResponse
import com.myproject.gosport.data.entity.MealResponse
import com.myproject.gosport.domain.models.Category
import com.myproject.gosport.domain.models.Meal
import com.myproject.gosport.domain.repository.MealRepository
import com.myproject.gosport.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val goDatabase: GoDatabase,
) : MealRepository {
    private val goDao = goDatabase.goDao()

    override fun getCategories() = networkBoundResource(
        query = {
            goDao.getCategories().mapToCategory()
        },
        fetch = {
            remoteDataSource.getCategories()
        },
        saveFetchResult = {
            goDatabase.withTransaction {
                goDao.deleteAllCategory()
                goDao.insertCategory(mapCategoryResponseToList(it))
            }
        }
    )

    override fun getMeals() = networkBoundResource(
        query = {
            goDao.getMeals().mapToMeal()
        },
        fetch = {
            remoteDataSource.getMeals()
        },
        saveFetchResult = {
            goDatabase.withTransaction {
                goDao.deleteAllMeal()
                goDao.insertMeal(mapMealResponseToList(it))
            }
        }
    )

    private suspend fun Flow<List<CategoryResponse.Category>>.mapToCategory(): Flow<List<Category>> {
        val data = this.first().map { item ->
            Category(
                id = item.id,
                name = item.name,
                image = item.image,
                description = item.description
            )
        }
        return flow { emit(data) }
    }

    private fun mapCategoryResponseToList(entity: Response<CategoryResponse>):
            List<CategoryResponse.Category> = entity.body()!!.categories

    private suspend fun Flow<List<MealResponse.Meal>>.mapToMeal(): Flow<List<Meal>> {
        val data = this.first().map { item ->
            Meal(
                id = item.id,
                categoryName = item.categoryName,
                name = item.name,
                image = item.image,
                description = item.description
            )
        }
        return flow { emit(data) }
    }

    private fun mapMealResponseToList(entity: Response<MealResponse>):
            List<MealResponse.Meal> = entity.body()!!.meals
}
