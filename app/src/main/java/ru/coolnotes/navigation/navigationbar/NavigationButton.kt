package ru.coolnotes.navigation.navigationbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    imageId: Int,
    descriptionId: Int,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val contentPadding = if (isActive) 4.dp else 0.dp
    val background = if (isActive) Color(0x0D000000) else Color.Transparent
    val imageSize = if (isActive) 48.dp else 42.dp
    Button(
        modifier = modifier.height(imageSize),
        shape = ShapeDefaults.Medium,
        contentPadding = PaddingValues(contentPadding),
        colors = ButtonColors(
            containerColor = background,
            disabledContainerColor = Color.Gray,
            contentColor = Color.Transparent,
            disabledContentColor = Color.White,
        ),
        onClick = { onClick.invoke() },
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = stringResource(descriptionId),
            modifier = Modifier
                .size(imageSize)
                .align(Alignment.CenterVertically),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveNavigationButtonPreview() {
    NavigationButton(
        modifier = Modifier,
        imageId = R.drawable.image_notes,
        descriptionId = R.string.navigation_bar_notes,
        isActive = true,
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun NonActiveNavigationButtonPreview() {
    NavigationButton(
        modifier = Modifier,
        imageId = R.drawable.image_notes,
        descriptionId = R.string.navigation_bar_notes,
        isActive = false,
        onClick = {},
    )
}
