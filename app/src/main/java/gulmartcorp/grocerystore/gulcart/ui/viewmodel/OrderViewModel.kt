package gulmartcorp.grocerystore.gulcart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gulmartcorp.grocerystore.gulcart.data.entity.OrderEntity
import gulmartcorp.grocerystore.gulcart.data.repository.OrderRepository
import gulmartcorp.grocerystore.gulcart.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository,
) : ViewModel() {
    private val _ordersState =
        MutableStateFlow<DataUiState<List<OrderEntity>>>(DataUiState.Initial)
    val ordersState: StateFlow<DataUiState<List<OrderEntity>>>
        get() = _ordersState.asStateFlow()

    init {
        observeOrders()
    }

    private fun observeOrders() {
        viewModelScope.launch {
            orderRepository.observeAll().collect { orders ->
                _ordersState.update { DataUiState.from(orders) }
            }
        }
    }

    fun deleteOrder(orderNumber: String) {
        viewModelScope.launch {
            orderRepository.deleteByNumber(orderNumber)
        }
    }
}