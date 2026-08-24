package gulmartcorp.grocerystore.gulcart.di

import gulmartcorp.grocerystore.gulcart.ui.viewmodel.AppViewModel
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.CartViewModel
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.CheckoutViewModel
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.AHSQYOnboardingVM
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.OrderViewModel
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.ProductDetailsViewModel
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.ProductViewModel
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.AHSQYSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        AHSQYSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        AHSQYOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}