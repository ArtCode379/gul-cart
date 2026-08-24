package gulmartcorp.grocerystore.gulcart.di

import gulmartcorp.grocerystore.gulcart.data.repository.CartRepository
import gulmartcorp.grocerystore.gulcart.data.repository.AHSQYOnboardingRepo
import gulmartcorp.grocerystore.gulcart.data.repository.OrderRepository
import gulmartcorp.grocerystore.gulcart.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        AHSQYOnboardingRepo(
            ahsqyOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}