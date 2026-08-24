package gulmartcorp.grocerystore.gulcart.di

import androidx.room.Room
import gulmartcorp.grocerystore.gulcart.data.database.AHSQYDatabase
import org.koin.dsl.module

private const val DB_NAME = "ahsqy_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AHSQYDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<AHSQYDatabase>().cartItemDao() }

    single { get<AHSQYDatabase>().orderDao() }
}