package gulmartcorp.grocerystore.gulcart.data.model

import androidx.annotation.StringRes
import gulmartcorp.grocerystore.gulcart.R

enum class ProductCategory(
    @field:StringRes val titleRes: Int,
) {
    FRESH(R.string.ahsqy_category_fresh),
    BAKERY(R.string.ahsqy_category_bakery),
    PANTRY(R.string.ahsqy_category_pantry),
    DRINKS(R.string.ahsqy_category_drinks),
    READY_MEALS(R.string.ahsqy_category_ready_meals),
}
