package hpl.apps.android.math.ui.screens

import hpl.apps.android.math.ui.GetData
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

abstract class Localizer(locale: Locale) {
    private val decimalSeparator = DecimalFormatSymbols.getInstance(locale).decimalSeparator
    private val p = GetData.INTERNAL_DECIMAL_SEPARATOR

    private val localizerRegex = """(?<![$p\d])\d+(?!\d)|[$p]\d+(?!\d)|[$p](?!\d)""".toRegex()
    private val formatter1 = NumberFormat.getNumberInstance(locale).apply { isGroupingUsed = true }
    private val formatter2 = NumberFormat.getNumberInstance(locale).apply { isGroupingUsed = false }

    private var zerosCount  = 0

    fun localizeExpression(nonLocalizedExpression: String, isGroupingUsed: Boolean = true): String {
        if (nonLocalizedExpression.isEmpty()) return nonLocalizedExpression
        val formatter = if(isGroupingUsed) formatter1 else formatter2
        return nonLocalizedExpression.replace(localizerRegex){s->
            when{
                s.value[0].isDigit() ->{
                    zerosCount = s.value.countLeadingZeros()
                    if(zerosCount == s.value.length) {
                        formatter.format(0).repeat(zerosCount)
                    }else {
                        formatter.maximumIntegerDigits = s.value.length
                        formatter.format(0).repeat(zerosCount)+formatter.format(s.value.toBigInteger())
                    }
                }
                s.value == "$p" -> {
                    decimalSeparator.toString()
                }
                else ->{
                    zerosCount = s.value.countTrailingZeros()
                    if(zerosCount == s.value.length-1) {
                        decimalSeparator.toString() + formatter.format(0).repeat(zerosCount)
                    }else {
                        formatter.maximumFractionDigits = s.value.length - 1
                        formatter.format(s.value.toBigDecimal()).drop(1)+formatter.format(0).repeat(zerosCount)
                    }
                }
            }
        }
    }

    private val groupingSeparator = DecimalFormatSymbols.getInstance(locale).groupingSeparator
    fun findCursorPositionInLocalizedExpression(
        localizedExpression: String,
        nonLocalizedExpressionLength: Int,
        originalCursorPosition: Int
    ): Int{
        if (originalCursorPosition == 0) return 0
        if(originalCursorPosition == nonLocalizedExpressionLength) return localizedExpression.length

        val cursorPositionCountingFromStringEnd = nonLocalizedExpressionLength-originalCursorPosition+1
        var newCursorPosition: Int? = null
        var count = 1
        var i = localizedExpression.length-2
        while (i>=0){
            if(localizedExpression[i] != groupingSeparator){
                count++
                if(count == cursorPositionCountingFromStringEnd){
                    newCursorPosition = i+1
                    break
                }
            }
            i--
        }
        return newCursorPosition ?: localizedExpression.length
    }


    companion object{
        private fun String.countTrailingZeros(): Int{
            var count = 0
            for (i in this.lastIndex downTo 0) {
                if (this[i] == '0') count++ else break
            }
            return count
        }
        private fun String.countLeadingZeros(): Int{
            var count = 0
            for (i in 0..this.lastIndex) {
                if (this[i] == '0') count++ else break
            }
            return count
        }
    }

}