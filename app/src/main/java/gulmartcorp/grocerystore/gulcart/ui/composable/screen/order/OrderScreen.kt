package gulmartcorp.grocerystore.gulcart.ui.composable.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gulmartcorp.grocerystore.gulcart.R
import gulmartcorp.grocerystore.gulcart.data.entity.OrderEntity
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYContentWrapper
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYEmptyView
import gulmartcorp.grocerystore.gulcart.ui.state.DataUiState
import gulmartcorp.grocerystore.gulcart.ui.theme.Success
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val state by viewModel.ordersState.collectAsState()
    AHSQYContentWrapper(
        dataState = state,
        dataPopulated = {
            val orders = (state as DataUiState.Populated).data.sortedByDescending { it.timestamp }
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(orders, key = { it.orderNumber }) { order -> OrderCard(order) }
            }
        },
        dataEmpty = {
            AHSQYEmptyView(
                primaryText = stringResource(R.string.ahsqy_orders_state_empty_primary_text),
                modifier = modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun OrderCard(order: OrderEntity) {
    Card(shape = RoundedCornerShape(14.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Order #${order.orderNumber}", style = MaterialTheme.typography.titleMedium)
                Surface(color = Success.copy(alpha = 0.14f), shape = RoundedCornerShape(50)) {
                    Text("Completed", color = Success, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }
            Text(order.timestamp.toLocalDate().toString(), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.description, style = MaterialTheme.typography.bodyMedium)
            Text(stringResource(R.string.ahsqy_price, order.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        }
    }
}
