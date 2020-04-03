package com.android.calculator.data.repository

import com.android.calculator.data.dao.ExpressionDao
import com.android.calculator.data.model.ExpressionModel
import com.android.calculator.domain.entity.Expression
import com.android.calculator.domain.repository.IExpressionRepository
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExpressionRoomRepository @Inject constructor(
    private val expressionDao: ExpressionDao
) : IExpressionRepository, CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun setExpression(value: String) {
        launch {
            val expression = Expression(value)
            val model = ExpressionModel(expression)
            expressionDao.insert(model)
        }
    }

    override fun getExpression(): Expression {
        return getExpression()
    }

    override fun clearExpression() {
        launch {
            expressionDao.deleteAll()
        }
    }

    override fun onDestroy() {
        job.cancel()
        clearExpression()
    }

    companion object {
        val expressionRepositoryContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    }
}