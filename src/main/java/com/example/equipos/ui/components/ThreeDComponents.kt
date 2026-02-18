package com.example.equipos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.shape.RoundedCornerShape

private fun Color.lighten(amount: Float): Color = lerp(this, Color.White, amount.coerceIn(0f, 1f))
private fun Color.darken(amount: Float): Color = lerp(this, Color.Black, amount.coerceIn(0f, 1f))

private fun ButtonColors.withTransparentContainer(): ButtonColors =
    copy(containerColor = Color.Transparent, disabledContainerColor = Color.Transparent)

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawOutlineCompat(
    outline: Outline,
    brush: Brush? = null,
    color: Color? = null,
    alpha: Float = 1f,
    style: Stroke? = null
) {
    when (outline) {
        is Outline.Rectangle -> {
            val stroke = style
            if (brush != null) {
                if (stroke == null) drawRect(brush = brush, alpha = alpha)
                else drawRect(brush = brush, alpha = alpha, style = stroke)
            } else if (color != null) {
                if (stroke == null) drawRect(color = color, alpha = alpha)
                else drawRect(color = color, alpha = alpha, style = stroke)
            }
        }

        is Outline.Rounded -> {
            val path = Path().apply { addRoundRect(outline.roundRect) }
            val stroke = style
            if (brush != null) {
                if (stroke == null) drawPath(path = path, brush = brush, alpha = alpha)
                else drawPath(path = path, brush = brush, alpha = alpha, style = stroke)
            } else if (color != null) {
                if (stroke == null) drawPath(path = path, color = color, alpha = alpha)
                else drawPath(path = path, color = color, alpha = alpha, style = stroke)
            }
        }

        is Outline.Generic -> {
            val stroke = style
            if (brush != null) {
                if (stroke == null) drawPath(path = outline.path, brush = brush, alpha = alpha)
                else drawPath(path = outline.path, brush = brush, alpha = alpha, style = stroke)
            } else if (color != null) {
                if (stroke == null) drawPath(path = outline.path, color = color, alpha = alpha)
                else drawPath(path = outline.path, color = color, alpha = alpha, style = stroke)
            }
        }
    }
}

private fun Modifier.threeDOverlay(
    shape: Shape,
    highlightColor: Color,
    shadowColor: Color,
    borderColor: Color,
    borderWidth: Dp
): Modifier = drawWithCache {
    val outline = shape.createOutline(size, layoutDirection, this)
    val highlightBrush = Brush.linearGradient(
        colors = listOf(highlightColor, Color.Transparent),
        start = Offset(0f, 0f),
        end = Offset(size.width * 0.7f, size.height * 0.7f)
    )
    val shadowBrush = Brush.linearGradient(
        colors = listOf(Color.Transparent, shadowColor),
        start = Offset(size.width * 0.25f, size.height * 0.25f),
        end = Offset(size.width, size.height)
    )
    val strokeWidthPx = borderWidth.toPx()
    onDrawWithContent {
        drawContent()
        drawOutlineCompat(outline = outline, brush = highlightBrush, alpha = 0.55f)
        drawOutlineCompat(outline = outline, brush = shadowBrush, alpha = 0.45f)
        drawOutlineCompat(outline = outline, color = borderColor, alpha = 0.55f, style = Stroke(width = strokeWidthPx))
    }
}

@Composable
fun ThreeDButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    baseColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: androidx.compose.material3.ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed = resolvedInteractionSource.collectIsPressedAsState().value

    val base = baseColor
    val start = if (pressed) base.darken(0.18f) else base.lighten(0.18f)
    val end = if (pressed) base.lighten(0.06f) else base.darken(0.22f)
    val backgroundBrush = Brush.linearGradient(listOf(start, end))

    val highlight = base.lighten(if (pressed) 0.34f else 0.45f).copy(alpha = if (enabled) 1f else 0.6f)
    val shadow = base.darken(if (pressed) 0.42f else 0.55f).copy(alpha = if (enabled) 1f else 0.35f)
    val borderColor = Color.White.copy(alpha = if (enabled) 0.14f else 0.08f)

    val shadowElevation = when {
        !enabled -> 0.dp
        pressed -> 6.dp
        else -> 12.dp
    }

    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .shadow(shadowElevation, shape, clip = false)
            .clip(shape)
            .background(backgroundBrush)
            .threeDOverlay(
                shape = shape,
                highlightColor = highlight,
                shadowColor = shadow,
                borderColor = borderColor,
                borderWidth = 1.dp
            ),
        enabled = enabled,
        shape = shape,
        colors = colors.withTransparentContainer(),
        elevation = elevation ?: ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp, focusedElevation = 0.dp, hoveredElevation = 0.dp, disabledElevation = 0.dp),
        border = border,
        contentPadding = contentPadding,
        interactionSource = resolvedInteractionSource,
        content = content
    )
}

@Composable
fun ThreeDOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    baseColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: androidx.compose.material3.ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed = resolvedInteractionSource.collectIsPressedAsState().value

    val base = baseColor
    val start = if (pressed) base.darken(0.16f) else base.lighten(0.14f)
    val end = if (pressed) base.lighten(0.06f) else base.darken(0.20f)
    val backgroundBrush = Brush.linearGradient(listOf(start, end))

    val highlight = base.lighten(if (pressed) 0.32f else 0.40f).copy(alpha = if (enabled) 1f else 0.55f)
    val shadow = base.darken(if (pressed) 0.42f else 0.52f).copy(alpha = if (enabled) 1f else 0.35f)
    val borderColor = Color.White.copy(alpha = if (enabled) 0.16f else 0.10f)

    val resolvedBorder = border ?: BorderStroke(1.dp, borderColor)
    val shadowElevation = when {
        !enabled -> 0.dp
        pressed -> 3.dp
        else -> 8.dp
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .shadow(shadowElevation, shape, clip = false)
            .clip(shape)
            .background(backgroundBrush)
            .threeDOverlay(
                shape = shape,
                highlightColor = highlight,
                shadowColor = shadow,
                borderColor = borderColor,
                borderWidth = 1.dp
            ),
        enabled = enabled,
        shape = shape,
        colors = colors.withTransparentContainer(),
        elevation = elevation ?: ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp, focusedElevation = 0.dp, hoveredElevation = 0.dp, disabledElevation = 0.dp),
        border = resolvedBorder,
        contentPadding = contentPadding,
        interactionSource = resolvedInteractionSource,
        content = content
    )
}

@Composable
fun ThreeDDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    scrollState: ScrollState = rememberScrollState(),
    properties: PopupProperties = PopupProperties(focusable = true),
    shape: Shape = MaterialTheme.shapes.large,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val surface = containerColor
    val start = surface.lighten(0.06f)
    val end = surface.darken(0.10f)
    val menuBrush = Brush.verticalGradient(listOf(start, end))
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .shadow(18.dp, shape, clip = false)
            .clip(shape)
            .background(menuBrush)
            .threeDOverlay(
                shape = shape,
                highlightColor = Color.White.copy(alpha = 0.18f),
                shadowColor = Color.Black.copy(alpha = 0.22f),
                borderColor = borderColor,
                borderWidth = 1.dp
            ),
        offset = offset,
        scrollState = scrollState,
        properties = properties,
        shape = shape,
        containerColor = Color.Transparent,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border ?: BorderStroke(1.dp, borderColor),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreeDTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    baseColor: Color = MaterialTheme.colorScheme.primary,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
    val start = baseColor.lighten(0.14f)
    val end = baseColor.darken(0.26f)
    val backgroundBrush = Brush.linearGradient(listOf(start, end))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(18.dp, shape, clip = false)
            .clip(shape)
            .background(backgroundBrush)
            .threeDOverlay(
                shape = shape,
                highlightColor = Color.White.copy(alpha = 0.16f),
                shadowColor = Color.Black.copy(alpha = 0.22f),
                borderColor = Color.White.copy(alpha = 0.10f),
                borderWidth = 1.dp
            )
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}
