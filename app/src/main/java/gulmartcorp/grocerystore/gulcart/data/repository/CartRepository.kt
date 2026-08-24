package gulmartcorp.grocerystore.gulcart.data.repository

import gulmartcorp.grocerystore.gulcart.data.dao.CartItemDao
import gulmartcorp.grocerystore.gulcart.data.entity.CartItemEntity
import gulmartcorp.grocerystore.gulcart.data.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CartRepository(
    private val cartItemDao: CartItemDao,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeAll(): Flow<List<CartItemEntity>> {
        return cartItemDao.observeAll()
    }

    suspend fun getAll(): List<CartItemEntity> {
        return cartItemDao.getAll()
    }

    suspend fun deleteById(id: Int) {
        withContext(coroutineDispatcher) {
            cartItemDao.deleteById(id)
        }
    }

    suspend fun deleteAll() {
        withContext(coroutineDispatcher) {
            cartItemDao.deleteAll()
        }
    }

    suspend fun incrementQuantity(productId: Int) {
        withContext(coroutineDispatcher) {
            cartItemDao.incrementQuantity(productId)
        }
    }

    suspend fun incrementProductQuantityOrAdd(product: Product) {
        withContext(coroutineDispatcher) {
            cartItemDao.incrementProductQuantityOrAdd(product)
        }
    }

    suspend fun decrementProductQuantityOrRemove(product: Product) {
        withContext(coroutineDispatcher) {
            cartItemDao.decrementProductQuantityOrRemove(product)
        }
    }
}