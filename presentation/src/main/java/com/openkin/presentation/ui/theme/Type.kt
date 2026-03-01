package com.openkin.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R

// Set of Material typography styles to start with
val Typography = Typography(

    //Заголовки экранов
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        color = black,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
    ),

    //Путсые экраны; Поле ввода поиска; селектор месяца
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = black,
        fontSize = 18.sp,
    ),

    //Текст в стандартных кнопках; текст в диалогах
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        color = black,
    ),

    //Заголовки заметок (обычных, детал., больш. квадр.)
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        color = black,
    ),

    //Заголовки заметок (мал. квадр.)
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 12.sp,
        color = black,
    ),

    //Основной текст заметок (обычных, детал., больш. квадр.); Текст фильтров в поиске
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        lineHeight = 14.sp,
        color = black,
    ),

    //Основной текст заметок (мал. квадр.)
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 14.sp,
        lineHeight = 12.sp,
        color = black,
    ),

    //Целевые даты в заметках и время напоминания
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 14.sp,
        color = black,
    ),

    //Выпадающие списки
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = black,
    ),

    //Кнопки вызова дата и тайм пикера
    labelLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = black,
    ),

    //Текст в полях ввода
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 18.sp,
        color = black,
    ),

    //Плэйсхолдеры
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        color = gray,
    ),
    /* Other default text styles to override
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
