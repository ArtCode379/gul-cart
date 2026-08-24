package gulmartcorp.grocerystore.gulcart.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import gulmartcorp.grocerystore.gulcart.R
import gulmartcorp.grocerystore.gulcart.data.model.Product
import gulmartcorp.grocerystore.gulcart.data.model.ProductCategory
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYContentWrapper
import gulmartcorp.grocerystore.gulcart.ui.composable.shared.AHSQYEmptyView
import gulmartcorp.grocerystore.gulcart.ui.state.DataUiState
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val state by viewModel.productsState.collectAsState()
    AHSQYContentWrapper(
        dataState = state,
        dataPopulated = {
            HomeProducts(
                products = (state as DataUiState.Populated).data,
                modifier = modifier,
                onProductClick = onNavigateToProductDetails,
                onAdd = viewModel::addToCart,
            )
        },
        dataEmpty = {
            AHSQYEmptyView(
                primaryText = stringResource(R.string.ahsqy_products_state_empty_primary_text),
                modifier = modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun HomeProducts(
    products: List<Product>,
    modifier: Modifier,
    onProductClick: (Int) -> Unit,
    onAdd: (Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
    val featured = products.take(4)
    val pagerState = rememberPagerState(pageCount = { featured.size })
    val filtered = selectedCategory?.let { category -> products.filter { it.category == category } } ?: products

    LaunchedEffect(pagerState.currentPage, featured.size) {
        delay(4000)
        if (featured.isNotEmpty()) {
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % featured.size)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
        ) { page ->
            val product = featured[page]
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onProductClick(product.id) },
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(androidx.compose.ui.graphics.Color.Transparent, androidx.compose.ui.graphics.Color(0xCC1A3A5C)))),
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(18.dp),
                ) {
                    Text("Fresh picks this week", color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.labelSmall)
                    Text(product.title, color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.titleLarge)
                    Text(stringResource(R.string.ahsqy_price, product.price), color = androidx.compose.ui.graphics.Color.White)
                }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            featured.indices.forEach { index ->
                Box(
                    Modifier
                        .size(if (index == pagerState.currentPage) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                )
            }
        }
        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("All") },
                )
            }
            items(ProductCategory.entries) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(stringResource(category.titleRes)) },
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(filtered, key = { it.id }) { product ->
                ProductCard(product, onProductClick, onAdd)
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onProductClick: (Int) -> Unit,
    onAdd: (Int) -> Unit,
) {
    Card(
        modifier = Modifier.clickable { onProductClick(product.id) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f),
        )
        Column(Modifier.padding(12.dp)) {
            Text(stringResource(product.category.titleRes), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Text(product.title, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(stringResource(R.string.ahsqy_price, product.price), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { onAdd(product.id) }) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Add ${product.title} to cart", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
