package gulmartcorp.grocerystore.gulcart.data.repository

import gulmartcorp.grocerystore.gulcart.data.datastore.AHSQYOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AHSQYOnboardingRepo(
    private val ahsqyOnboardingStoreManager: AHSQYOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return ahsqyOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            ahsqyOnboardingStoreManager.setOnboardedState(state)
        }
    }
}