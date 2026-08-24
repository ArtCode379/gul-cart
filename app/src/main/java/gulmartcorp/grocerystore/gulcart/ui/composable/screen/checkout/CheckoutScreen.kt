package gulmartcorp.grocerystore.gulcart.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gulmartcorp.grocerystore.gulcart.ui.state.DataUiState
import gulmartcorp.grocerystore.gulcart.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    var phone by remember { mutableStateOf("") }
    val enabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() &&
                viewModel.customerLastName.isNotBlank() &&
                viewModel.customerEmail.isNotBlank() &&
                phone.isNotBlank()
        }
    }
    if (orderState is DataUiState.Populated) {
        CheckoutDialog(
            orderNumber = (orderState as DataUiState.Populated).data.orderNumber,
            onConfirm = onNavigateToOrdersScreen,
        )
    }
    CheckoutContent(
        name = viewModel.customerFirstName,
        address = viewModel.customerLastName,
        email = viewModel.customerEmail,
        phone = phone,
        isEmailInvalid = emailInvalid,
        modifier = modifier,
        focusManager = LocalFocusManager.current,
        isButtonEnabled = enabled,
        onNameChanged = viewModel::updateCustomerFirstName,
        onAddressChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPhoneChanged = { phone = it },
        onPlaceOrder = viewModel::placeOrder,
    )
}

@Composable
private fun CheckoutContent(
    name: String,
    address: String,
    email: String,
    phone: String,
    isEmailInvalid: Boolean,
    modifier: Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onNameChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Delivery details", style = MaterialTheme.typography.headlineMedium)
        Text("Tell us where to bring your groceries. We will confirm the delivery window after checkout.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        CheckoutTextField(name, onNameChanged, "Full name", Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
        CheckoutTextField(address, onAddressChanged, "Delivery address", Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
        CheckoutTextField(
            input = phone,
            onInputChange = onPhoneChanged,
            labelText = "Phone number",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
        )
        CheckoutTextField(
            input = email,
            onInputChange = onEmailChanged,
            labelText = "Email for order updates",
            modifier = Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
        if (isEmailInvalid) {
            Text("Enter a valid email address", color = MaterialTheme.colorScheme.error)
        }
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Order summary", style = MaterialTheme.typography.titleLarge)
                Text("Your basket total is shown on the Cart screen. Delivery timing will be confirmed with your order number.")
            }
        }
        Button(
            onClick = onPlaceOrder,
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Place Order")
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        ),
    )
}
