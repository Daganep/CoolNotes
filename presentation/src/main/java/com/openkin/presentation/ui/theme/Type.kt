package com.openkin.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R

val Typography = Typography(

//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        color = black,
//        fontSize = 26.sp,
//        fontWeight = FontWeight.Bold,
//    ),
)

// Заголовки экранов
val Typography.screenTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        color = black,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
    )

// Текст пустых экранов
val Typography.monthSelector: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = black,
        fontSize = 18.sp,
    )

// Текст пустых экранов
val Typography.searchFieldText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = black,
        fontSize = 18.sp,
    )

// Текст пустых экранов
val Typography.emptyResultText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = black,
        fontSize = 18.sp,
    )

// Текст в кнопках диалогов
val Typography.dialogButtonText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        color = black,
    )

// Текст в стандартных кнопках
val Typography.buttonText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        color = black,
    )

// Заголовки заметок (обычные, детальные, большие квадраты)
val Typography.noteTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        color = black,
    )

// Заголовки заметок (малый квадрат)
val Typography.noteTitleSmall: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri_bold)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 12.sp,
        color = black,
    )

// Основной текст диалога
val Typography.dialogText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        lineHeight = 14.sp,
        color = black,
    )

// Текст фильтров в поиске
val Typography.filterText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        lineHeight = 14.sp,
        color = black,
    )

// Основной текст заметок (обычные, детальные, большие квадраты)
val Typography.noteText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        lineHeight = 14.sp,
        color = black,
    )

// Основной текст заметок (малый квадрат)
val Typography.noteTextSmall: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 14.sp,
        lineHeight = 12.sp,
        color = black,
    )

val Typography.noteDate: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 14.sp,
        color = black,
    )

// Выпадающие списки
val Typography.expandedList: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = black,
    )

// Кнопки вызова дата и тайм пикера
val Typography.datePickerButton: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = black,
    )

val Typography.textFieldText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 18.sp,
        color = black,
    )

val Typography.addNotePlaceHolder: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.calibri)),
        fontSize = 16.sp,
        color = gray,
    )
