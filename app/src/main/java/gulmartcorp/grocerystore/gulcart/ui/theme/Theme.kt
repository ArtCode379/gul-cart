package gulmartcorp.grocerystore.gulcart.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import gulmartcorp.grocerystore.gulcart.R

private val FontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

val HeadingFamily = FontFamily(Font(GoogleFont("DM Sans"), FontProvider, weight = FontWeight.Bold))
val BodyFamily = FontFamily(Font(GoogleFont("Nunito"), FontProvider, weight = FontWeight.Normal))

private val AppColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Accent,
    onSecondary = OnPrimary,
    background = Background,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = ChipBackground,
    onSurfaceVariant = Muted,
    outline = Border,
)

@Composable
fun ProductAppAHSQYTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content,
    )
}
