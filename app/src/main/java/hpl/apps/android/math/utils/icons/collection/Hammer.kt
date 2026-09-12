package hpl.apps.android.math.utils.icons.collection

/*
Font Awesome Free License
-------------------------

Font Awesome Free is free, open source, and GPL friendly. You can use it for
commercial projects, open source projects, or really almost whatever you want.
Full Font Awesome Free license: https://fontawesome.com/license/free.

# Icons: CC BY 4.0 License (https://creativecommons.org/licenses/by/4.0/)
In the Font Awesome Free download, the CC BY 4.0 license applies to all icons
packaged as SVG and JS file types.

# Fonts: SIL OFL 1.1 License (https://scripts.sil.org/OFL)
In the Font Awesome Free download, the SIL OFL license applies to all icons
packaged as web and desktop font files.

# Code: MIT License (https://opensource.org/licenses/MIT)
In the Font Awesome Free download, the MIT license applies to all non-font and
non-icon files.

# Attribution
Attribution is required by MIT, SIL OFL, and CC BY licenses. Downloaded Font
Awesome Free files already contain embedded comments with sufficient
attribution, so you shouldn't need to do anything additional when using these
files normally.

We've kept attribution comments terse, so we ask that you do not actively work
to remove them from files, especially code. They're a great way for folks to
learn about Font Awesome.

# Brand Icons
All brand icons are trademarks of their respective owners. The use of these
trademarks does not indicate endorsement of the trademark holder by Font
Awesome, nor vice versa. **Please do not use brand logos for any purpose except
to represent the company, product, or service to which they refer.**
*/
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


val fontAwesomeHammer = IconClass(
    FontAwesomeHammer.name,
    FontAwesomeHammer,
    "https://composables.com/icons/icon-libraries/font-awesome/hammer",
    """Font Awesome Free License
-------------------------

Font Awesome Free is free, open source, and GPL friendly. You can use it for
commercial projects, open source projects, or really almost whatever you want.
Full Font Awesome Free license: https://fontawesome.com/license/free.

# Icons: CC BY 4.0 License (https://creativecommons.org/licenses/by/4.0/)
In the Font Awesome Free download, the CC BY 4.0 license applies to all icons
packaged as SVG and JS file types.

# Fonts: SIL OFL 1.1 License (https://scripts.sil.org/OFL)
In the Font Awesome Free download, the SIL OFL license applies to all icons
packaged as web and desktop font files.

# Code: MIT License (https://opensource.org/licenses/MIT)
In the Font Awesome Free download, the MIT license applies to all non-font and
non-icon files.

# Attribution
Attribution is required by MIT, SIL OFL, and CC BY licenses. Downloaded Font
Awesome Free files already contain embedded comments with sufficient
attribution, so you shouldn't need to do anything additional when using these
files normally.

We've kept attribution comments terse, so we ask that you do not actively work
to remove them from files, especially code. They're a great way for folks to
learn about Font Awesome.

# Brand Icons
All brand icons are trademarks of their respective owners. The use of these
trademarks does not indicate endorsement of the trademark holder by Font
Awesome, nor vice versa. **Please do not use brand logos for any purpose except
to represent the company, product, or service to which they refer.**"""
)


private val FontAwesomeHammer: ImageVector
    get() {
        if (_FontAwesomeHammer != null) return _FontAwesomeHammer!!

        _FontAwesomeHammer = ImageVector.Builder(
            name = "hammer",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 576f,
            viewportHeight = 512f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(571.31f, 193.94f)
                lineToRelative(-22.63f, -22.63f)
                curveToRelative(-6.25f, -6.25f, -16.38f, -6.25f, -22.63f, 0f)
                lineToRelative(-11.31f, 11.31f)
                lineToRelative(-28.9f, -28.9f)
                curveToRelative(5.63f, -21.31f, 0.36f, -44.9f, -16.35f, -61.61f)
                lineToRelative(-45.25f, -45.25f)
                curveToRelative(-62.48f, -62.48f, -163.79f, -62.48f, -226.28f, 0f)
                lineToRelative(90.51f, 45.25f)
                verticalLineToRelative(18.75f)
                curveToRelative(0f, 16.97f, 6.74f, 33.25f, 18.75f, 45.25f)
                lineToRelative(49.14f, 49.14f)
                curveToRelative(16.71f, 16.71f, 40.3f, 21.98f, 61.61f, 16.35f)
                lineToRelative(28.9f, 28.9f)
                lineToRelative(-11.31f, 11.31f)
                curveToRelative(-6.25f, 6.25f, -6.25f, 16.38f, 0f, 22.63f)
                lineToRelative(22.63f, 22.63f)
                curveToRelative(6.25f, 6.25f, 16.38f, 6.25f, 22.63f, 0f)
                lineToRelative(90.51f, -90.51f)
                curveToRelative(6.23f, -6.24f, 6.23f, -16.37f, -0.02f, -22.62f)
                close()
                moveToRelative(-286.72f, -15.2f)
                curveToRelative(-3.7f, -3.7f, -6.84f, -7.79f, -9.85f, -11.95f)
                lineTo(19.64f, 404.96f)
                curveToRelative(-25.57f, 23.88f, -26.26f, 64.19f, -1.53f, 88.93f)
                reflectiveCurveToRelative(65.05f, 24.05f, 88.93f, -1.53f)
                lineToRelative(238.13f, -255.07f)
                curveToRelative(-3.96f, -2.91f, -7.9f, -5.87f, -11.44f, -9.41f)
                lineToRelative(-49.14f, -49.14f)
                close()
            }
        }.build()

        return _FontAwesomeHammer!!
    }

private var _FontAwesomeHammer: ImageVector? = null