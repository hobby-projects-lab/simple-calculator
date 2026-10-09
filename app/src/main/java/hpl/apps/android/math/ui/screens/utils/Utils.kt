package hpl.apps.android.math.ui.screens.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat.getString
import hpl.apps.android.math.R

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("text copied to clipboard", text)
    clipboard.setPrimaryClip(clip)

    Toast.makeText(context, getString(context, R.string.copied), Toast.LENGTH_SHORT).show()
}


@Composable
fun IconItem(
    icon: ImageVector,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    description: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentSize()
            .padding(dimensionResource(R.dimen.box_item_padding))
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(dimensionResource(R.dimen.box_item_size))
                .clip(MaterialTheme.shapes.medium)
                .background(color = backgroundColor)
                .clickable{
                    onClick()
                }
                .padding(dimensionResource(R.dimen.box_item_padding))
        ) {
            Icon(
                icon,
                description,
                tint = color
            )
            if(label != null){
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.box_item_vert_spacing)))
                Text(
                    text = label,
                    style = textStyle,
                    textAlign = TextAlign.Center,
                    color = color
                )
            }
        }
    }
}

@Composable
fun FlexGrid(
    modifier: Modifier = Modifier,
    content: @Composable ()-> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        FlowRow(modifier = Modifier.verticalScroll(rememberScrollState())){
            content()
        }
    }
}