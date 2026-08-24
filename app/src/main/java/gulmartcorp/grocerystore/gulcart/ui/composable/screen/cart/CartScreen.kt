package gulmartcorp.grocerystore.gulcart.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import gulmartcorp.grocerystore.gulcart.R
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYContentWrapper
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYEmptyView
import gulmartcorp.grocerystore.gulcart.ui.state.CartItemUiState
import gulmartcorp.grocerystore.gulcart.ui.state.DataUiState
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val state by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.collectAsStateWithLifecycle()
    AHSQYContentWrapper(
        dataState = state,
        dataPopulated = {
            CartContent(
                items = (state as DataUiState.Populated).data,
                total = total,
                modifier = modifier,
                onPlus = viewModel::incrementProductInCart,
                onMinus = { item ->
                    if (item.quantity == 1) {
                        viewModel.deleteFromCart(item.productId)
                    } else {
                        viewModel.decrementItemInCart(item.productId)
                    }
                },
                onDelete = viewModel::deleteFromCart,
                onCheckout = onNavigateToCheckoutScreen,
            )
        },
        dataEmpty = {
            AHSQYEmptyView(
                primaryText = stringResource(R.string.ahsqy_cart_state_empty_primary_text),
                modifier = modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun CartContent(
    items: List<CartItemUiState>,
    total: Double,
    modifier: Modifier,
    onPlus: (Int) -> Unit,
    onMinus: (CartItemUiState) -> Unit,
    onDelete: (Int) -> Unit,
    onCheckout: () -> Unit,
) {
    Column(modifier.padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items, key = { it.productId }) { item ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = item.productImageUrl,
                            contentDescription = item.productTitle,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(64.dp),
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 10.dp),
                        ) {
                            Text(item.productTitle, style = MaterialTheme.typography.titleMedium)
                            Text(stringResource(R.string.ahsqy_price, item.productPrice), color = MaterialTheme.colorScheme.primary)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { onMinus(item) }) { Icon(Icons.Default.Remove, contentDescription = "Decrease quantity") }
                                Text(item.quantity.toString())
                                IconButton(onClick = { onPlus(item.productId) }) { Icon(Icons.Default.Add, contentDescription = "Increase quantity") }
                            }
                        }
                        IconButton(onClick = { onDelete(item.productId) }) { Icon(Icons.Default.DeleteOutline, contentDescription = "Remove item") }
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.ahsqy_price, total), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        }
        Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
            Text("Proceed to Checkout")
        }
    }
}
