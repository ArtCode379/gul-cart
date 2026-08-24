package gulmartcorp.grocerystore.gulcart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gulmartcorp.grocerystore.gulcart.data.dao.CartItemDao
import gulmartcorp.grocerystore.gulcart.data.dao.OrderDao
import gulmartcorp.grocerystore.gulcart.data.database.converter.Converters
import gulmartcorp.grocerystore.gulcart.data.entity.CartItemEntity
import gulmartcorp.grocerystore.gulcart.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AHSQYDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}