package com.openkin.data.utils

/**
 * Функция оборачивает строку [query], переданную в параметр символами '%'
 * для того чтобы Room мог искать подстроку в указанном столбце.
 * До обёртки все символы '%' экранируются символом '@'.
 */
fun updateQueryForSearchSubstring(query: String) : String {
    val updatedQuery = query.replace("%", "@%")
    return "%$updatedQuery%"
}
