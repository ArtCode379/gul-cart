package gulmartcorp.grocerystore.gulcart.data.repository

import gulmartcorp.grocerystore.gulcart.data.model.Product
import gulmartcorp.grocerystore.gulcart.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products = listOf(
        Product(
            1, "British Strawberries", "Sweet, fragrant strawberries selected at peak ripeness. Ideal with breakfast, desserts, or simply enjoyed fresh.",
            ProductCategory.FRESH, 3.49, "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=1200",
        ),
        Product(
            2, "Hass Avocados", "Creamy ripe avocados, rich in flavour and ready for toast, salads, dips, and nourishing everyday meals.",
            ProductCategory.FRESH, 2.95, "https://images.unsplash.com/photo-1523049673857-eb18f1d7b578?w=1200",
        ),
        Product(
            3, "Artisan Sourdough", "A slow-fermented loaf with a crisp golden crust, airy crumb, and balanced tang from traditional starter.",
            ProductCategory.BAKERY, 4.25, "https://images.unsplash.com/photo-1585478259715-876acc5be8eb?w=1200",
        ),
        Product(
            4, "Butter Croissants", "Flaky all-butter croissants baked until beautifully golden. Four bakery-fresh pastries per pack.",
            ProductCategory.BAKERY, 3.80, "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=1200",
        ),
        Product(
            5, "Extra Virgin Olive Oil", "Cold-extracted olive oil with a fruity aroma and peppery finish for dressings, dipping, and cooking.",
            ProductCategory.PANTRY, 8.90, "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=1200",
        ),
        Product(
            6, "Penne Rigate", "Bronze-cut durum wheat pasta with ridges that hold rich tomato, vegetable, and cream sauces beautifully.",
            ProductCategory.PANTRY, 2.20, "https://images.unsplash.com/photo-1551462147-ff29053bfc14?w=1200",
        ),
        Product(
            7, "Sparkling Mineral Water", "Crisp natural mineral water with fine bubbles. Refreshing chilled and perfect alongside any meal.",
            ProductCategory.DRINKS, 1.65, "https://images.unsplash.com/photo-1523362628745-0c100150b504?w=1200",
        ),
        Product(
            8, "Cold-Pressed Orange Juice", "Bright, refreshing juice pressed from ripe oranges with no added sugar or concentrate.",
            ProductCategory.DRINKS, 3.75, "https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=1200",
        ),
        Product(
            9, "Roast Chicken Dinner", "Tender roast chicken with seasonal vegetables, herb potatoes, and savoury gravy. Ready in minutes.",
            ProductCategory.READY_MEALS, 7.95, "https://images.unsplash.com/photo-1532550907401-a500c9a57435?w=1200",
        ),
        Product(
            10, "Vegetable Lasagne", "Layers of pasta, roasted vegetables, tomato sauce, and creamy bechamel finished with mature cheese.",
            ProductCategory.READY_MEALS, 6.75, "https://images.unsplash.com/photo-1574894709920-11b28e7367e3?w=1200",
        ),
        Product(
            11, "Vine Tomatoes", "Juicy vine-ripened tomatoes with a deep aroma and natural sweetness for salads and sauces.",
            ProductCategory.FRESH, 2.60, "https://images.unsplash.com/photo-1546470427-e5ac89cd0b31?w=1200",
        ),
        Product(
            12, "Granola & Honey", "Crunchy toasted oats with almonds, seeds, and a touch of honey for an easy wholesome breakfast.",
            ProductCategory.PANTRY, 4.60, "https://images.unsplash.com/photo-1511690743698-d9d85f2fbf38?w=1200",
        ),
    )

    fun observeById(id: Int): Flow<Product?> = flowOf(products.find { it.id == id })

    fun getById(id: Int): Product? = products.find { it.id == id }

    fun observeAll(): Flow<List<Product>> = flowOf(products)
}
