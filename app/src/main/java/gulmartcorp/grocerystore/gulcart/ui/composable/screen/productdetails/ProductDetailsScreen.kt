package gulmartcorp.grocerystore.gulcart.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import gulmartcorp.grocerystore.gulcart.R
import gulmartcorp.grocerystore.gulcart.data.model.Product
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYContentWrapper
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYEmptyView
import gulmartcorp.grocerystore.gulcart.ui.state.DataUiState
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.productDetailsState.collectAsState()
    LaunchedEffect(productId) { viewModel.observeProductDetails(productId) }
    AHSQYContentWrapper(
        dataState = state,
        dataPopulated = {
            ProductDetails((state as DataUiState.Populated).data, modifier) {
                viewModel.addProductToCart()
            }
        },
        dataEmpty = {
            AHSQYEmptyView(
                primaryText = stringResource(R.string.ahsqy_product_details_state_empty_primary_text),
                modifier = modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ProductDetails(
    product: Product,
    modifier: Modifier,
    onAddToCart: () -> Unit,
) {
    var cartAdded by remember { mutableStateOf(false) }
    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }
    Box(modifier.fillMaxSize()) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp)),
            )
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(product.title, style = MaterialTheme.typography.headlineMedium)
                Text(stringResource(R.string.ahsqy_price, product.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.surfaceVariant) {
                    Text(stringResource(product.category.titleRes), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = MaterialTheme.colorScheme.primary)
                }
                Text(product.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Quality you can trust", style = MaterialTheme.typography.titleMedium)
                Text("Carefully selected for freshness and everyday value. Store as directed and enjoy before the use-by date.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        onAddToCart()
                        cartAdded = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.ahsqy_button_add_to_cart_label))
                }
                Spacer(Modifier.height(56.dp))
            }
        }
        AnimatedVisibility(
            visible = cartAdded,
            enter = slideInVertically { it },
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxWidth()) {
                Text("✓ Added to cart", color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
