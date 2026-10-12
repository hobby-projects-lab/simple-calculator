package hpl.apps.android.math.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.ui.graphics.vector.ImageVector
import hpl.apps.android.math.R
import hpl.apps.android.math.ui.GetData


private val functions = GetData.functions()
private  val constants = GetData.constants()
private val operators = GetData.operators()
private val expressions = GetData.expressions()

interface MathButtonClass

enum class VectorButtonClass(
    val icon: ImageVector,
    @StringRes val descriptionId: Int?
): MathButtonClass{
    BACKSPACE(icon = Icons.AutoMirrored.Filled.Backspace, R.string.backspace)
}

enum class TextButtonClass(
    val text: String = "",
    val buttonText: String? = null,
    val displayText: String? = null
): MathButtonClass{
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    DECIMAL_POINT("."),
    SIGN(buttonText = "+/-", displayText = "-"),
    SWITCH_TO_FUNC("f(x)"),
    SWITCH_TO_FUNC_INV("f⁻¹(x)"),
    SWITCH_TO_OTHER_MATH("!π√"),
    SWITCH_TO_NUM("123"),
    RAD("rad"),
    DEG("deg"),
    CURSOR_BACK(">"),
    QUICK_CURSOR_BACK(">>"),
    CURSOR_FORWARD("<"),
    QUICK_CURSOR_FORWARD("<<"),
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    SQRT("√"),
    POW(buttonText = "xʸ", displayText = "${operators.POW}"),
    FACT("${operators.FACT}"),
    MOD("mod"),
    PI("π"),
    E(constants.E),
    CLEAR("C"),
    DIV("${operators.DIV}"),
    MUL("×"),
    SUB("${operators.SUB}"),
    ADD("${operators.ADD}"),
    PERCENT("%"),
    EQUAL("="),
    INV(buttonText = "1${operators.DIV}x", displayText = "1${operators.DIV}("),
    ABS(buttonText = "|x|", displayText = "${functions.ABS}("),
    COS(buttonText = functions.COS, displayText = "${functions.COS}("),
    SIN(buttonText = functions.SIN, displayText = "${functions.SIN}("),
    TAN(buttonText = functions.TAN, displayText = "${functions.TAN}("),
    LN(buttonText = functions.LN, displayText = "${functions.LN}("),
    LOG(buttonText = functions.LOG, displayText = "${functions.LOG}("),
    LOG2(buttonText = "${functions.LOG}₂", displayText = "${functions.LOG}[2]("),
    LOG_X(buttonText = "${functions.LOG}ₓ", displayText = "${functions.LOG}[]("),
    COSH(buttonText = functions.COSH, displayText = "${functions.COSH}("),
    SINH(buttonText = functions.SINH, displayText = "${functions.SINH}("),
    TANH(buttonText = functions.TANH, displayText = "${functions.TANH}("),
    ARCCOS(buttonText = functions.ARCCOS, displayText = "${functions.ARCCOS}("),
    ARCSIN(buttonText = functions.ARCSIN, displayText = "${functions.ARCSIN}("),
    ARCTAN(buttonText = functions.ARCTAN, displayText = "${functions.ARCTAN}("),
    EXP(buttonText = "${constants.E}ˣ", displayText = "${constants.E}${operators.POW}("),
    POW10(buttonText = "10ˣ", displayText = "10${operators.POW}("),
    POW2(buttonText = "2ˣ", displayText = "2${operators.POW}("),
    SCI_NOTATION(text = "${expressions.SCI_NOTATION}"),
    ARCCOSH(buttonText = functions.ARCCOSH, displayText = "${functions.ARCCOSH}("),
    ARCSINH(buttonText = functions.ARCSINH, displayText = "${functions.ARCSINH}("),
    ARCTANH(buttonText = functions.ARCTANH, displayText = "${functions.ARCTANH}("),
    ROOT_3(buttonText = "³√", displayText = "${functions.ROOT}[3]("),
    ROOT(buttonText = "ˣ√", displayText = "${functions.ROOT}[]("),
    SQUARE(buttonText = "x²", displayText = "${operators.POW}2")
}