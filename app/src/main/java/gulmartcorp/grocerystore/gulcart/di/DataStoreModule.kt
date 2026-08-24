package gulmartcorp.grocerystore.gulcart.di

import gulmartcorp.grocerystore.gulcart.data.datastore.AHSQYOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { AHSQYOnboardingPrefs(androidContext()) }
}