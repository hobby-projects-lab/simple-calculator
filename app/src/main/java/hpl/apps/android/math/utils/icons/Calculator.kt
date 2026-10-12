package hpl.apps.android.math.utils.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CalculatorVector: ImageVector
    get() {
        if (Vector != null) return Vector!!

        Vector = ImageVector.Builder(
            name = "Calculator",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(22f, 0f)
                horizontalLineTo(14.6f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 12.6f, 2f)
                verticalLineTo(9.4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.6f, 11.4f)
                horizontalLineTo(22f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 24f, 9.4f)
                verticalLineTo(2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 22f, 0f)
                close()
                moveTo(21.3f, 6f)
                horizontalLineTo(15.3f)
                verticalLineTo(5.2f)
                horizontalLineTo(21.3f)
                verticalLineTo(6f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.4f, 13.2f)
                horizontalLineTo(2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 15.2f)
                verticalLineTo(22.6f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 24.6f)
                horizontalLineTo(9.4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 11.4f, 22.6f)
                verticalLineTo(15.2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.4f, 13.2f)
                close()
                moveTo(7.8f, 20.7f)
                lineTo(7.2f, 21.3f)
                lineTo(5.4f, 19.5f)
                lineTo(3.6f, 21.3f)
                lineTo(3f, 20.7f)
                lineTo(4.8f, 18.9f)
                lineTo(3f, 17.1f)
                lineTo(3.6f, 16.5f)
                lineTo(5.4f, 18.3f)
                lineTo(7.2f, 16.5f)
                lineTo(7.8f, 17.1f)
                lineTo(6f, 18.9f)
                lineTo(7.8f, 20.7f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.4f, 0f)
                horizontalLineTo(2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 2f)
                verticalLineTo(9.4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 11.4f)
                horizontalLineTo(9.4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 11.4f, 9.4f)
                verticalLineTo(2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.4f, 0f)
                close()
                moveTo(8.7f, 6f)
                horizontalLineTo(6.1f)
                verticalLineTo(8.7f)
                horizontalLineTo(5.3f)
                verticalLineTo(6f)
                horizontalLineTo(2.7f)
                verticalLineTo(5.2f)
                horizontalLineTo(5.3f)
                verticalLineTo(2.7f)
                horizontalLineTo(6.1f)
                verticalLineTo(5.2f)
                horizontalLineTo(8.7f)
                verticalLineTo(6f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(22f, 13.2f)
                horizontalLineTo(14.6f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 12.6f, 15.2f)
                verticalLineTo(22.6f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.6f, 24.6f)
                horizontalLineTo(22f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 24f, 22.6f)
                verticalLineTo(15.2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 22f, 13.2f)
                close()
                moveTo(21.3f, 20.8f)
                horizontalLineTo(15.3f)
                verticalLineTo(20f)
                horizontalLineTo(21.3f)
                verticalLineTo(20.8f)
                close()
                moveTo(21.3f, 17.8f)
                horizontalLineTo(15.3f)
                verticalLineTo(17f)
                horizontalLineTo(21.3f)
                verticalLineTo(17.8f)
                close()
            }
        }.build()

        return Vector!!
    }

private var Vector: ImageVector? = null