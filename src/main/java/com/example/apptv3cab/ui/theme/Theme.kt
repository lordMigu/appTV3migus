package com.example.apptv3cab.ui.theme

import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//Colores por defecto
/*private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFC8A2C8), // Lila
    secondary = Color(0xFF0000FF), // Blue
    tertiary = Pink80
)

//Colores por defecto
private val LightColorScheme = lightColorScheme(
    primary = Color.Gray,
    secondary = Color.Black,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)*/

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFF60000), // Ejemplo: Rojo
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDD2D2),
    onPrimaryContainer = Color(0xFF240000),
    secondary = Color(0xFF6E3535), // Ejemplo: Azul
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDD2D2),
    onSecondaryContainer = Color(0xFF270000),
    tertiary = Color(0xFF7D5260),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD9E2),
    onTertiaryContainer = Color(0xFF31101D),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF201A1A),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF201A1A),
    surfaceVariant = Color(0xFFF1DDE2),
    onSurfaceVariant = Color(0xFF504345),
    outline = Color(0xFF827376),
    inverseOnSurface = Color(0xFFFBEEED),
    inverseSurface = Color(0xFF352F30),
    inversePrimary = Color(0xFFFFB3B3),
    surfaceTint = Color(0xFFB00000),
    outlineVariant = Color(0xFFD4C2C4),
    scrim = Color(0xFF000000)
)

// Definición de la paleta de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB3B3), // Ejemplo: Rojo claro
    onPrimary = Color.White,
    primaryContainer = Color(0xFF930000),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFDCB1B2), // Ejemplo: Azul claro
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF553132),
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color.White,
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color.White,
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF201A1A),
    onBackground = Color(0xFFECE0E0),
    surface = Color(0xFF201A1A),
    onSurface = Color(0xFFECE0E0),
    surfaceVariant = Color(0xFF504345),
    onSurfaceVariant = Color(0xFFD4C2C4),
    outline = Color(0xFF9D8C8F),
    inverseOnSurface = Color(0xFF201A1A),
    inverseSurface = Color(0xFFECE0E0),
    inversePrimary = Color(0xFFB00000),
    surfaceTint = Color(0xFFFFB3B3),
    outlineVariant = Color(0xFF504345),
    scrim = Color(0xFF000000)
)

@Composable
fun AppTV3CabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}