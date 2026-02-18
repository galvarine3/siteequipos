package com.example.equipos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.graphics.Bitmap
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.asin
import kotlin.math.sqrt
import kotlin.random.Random
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.equipos.ui.theme.EquiposTheme
import com.example.equipos.ui.theme.GrassGreen
import com.example.equipos.ui.theme.GrassGreenDark
import com.example.equipos.ui.theme.Gold
import com.example.equipos.ui.theme.LogoutRed
import com.example.equipos.ui.components.ThreeDButton as Button
import com.example.equipos.ui.components.ThreeDOutlinedButton as OutlinedButton
import com.example.equipos.ui.components.ThreeDDropdownMenu as DropdownMenu
import com.example.equipos.ui.components.ThreeDTopAppBar
import android.util.Patterns
import android.util.Base64
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import java.util.Locale
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.contentOrNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.FileProvider
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.Date
import android.graphics.Paint
import android.graphics.DashPathEffect
import android.graphics.Color as AndroidColor
import android.graphics.Matrix
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import android.util.Log

// Constants for player ratings
private const val WEIGHT_ATTACK = 0.35
private const val WEIGHT_DEFENSE = 0.35
private const val WEIGHT_PHYSICAL = 0.30

private const val MIN_PASSWORD_LENGTH = 6
private const val CHAT_CHANNEL_ID = "chat_messages_channel"
private const val EXTRA_CHAT_POST_ID = "extra_chat_post_id"
private const val EXTRA_CHAT_PEER_NAME = "extra_chat_peer_name"
private const val EXTRA_CHAT_IS_GROUP = "extra_chat_is_group"

private fun isValidEmail(email: String): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(email.trim().lowercase()).matches()

data class CommunityPost(
    val id: Long,
    val time: Long,
    val userId: String? = null,
    val user: String,
    val sport: String,
    val available: Int,
    val total: Int,
    val message: String,
    val locality: String,
    val serverId: String? = null
)

data class ChatMessage(
    val from: String,
    val to: String,
    val text: String,
    val time: Long
)

data class ChatThread(
    val userId: String,
    val userName: String,
    val lastText: String,
    val time: Long
)

private fun passwordStrength(password: String): Int {
    var score = 0
    if (password.length >= MIN_PASSWORD_LENGTH) score++
    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isLowerCase() }) score++
    if (password.any { it.isDigit() }) score++
    return score.coerceIn(0, 4)
}

private fun distanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * asin(sqrt(a))
    return R * c
}

private fun decodeUserIdFromToken(token: String?): String? {
    if (token.isNullOrBlank()) return null
    val parts = token.split(".")
    if (parts.size < 2) return null
    return try {
        val payloadSegment = parts[1]
        val decodedBytes = Base64.decode(payloadSegment, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
        val payload = String(decodedBytes, Charsets.UTF_8)
        val json = JSONObject(payload)
        val uid = json.opt("uid")
        when (uid) {
            is Number -> uid.toLong().toString()
            is String -> uid
            else -> null
        }
    } catch (_: Exception) {
        null
    }
}

private fun fetchThreadsRemote(context: Context, access: String?, refresh: String?, postId: String): Pair<List<ChatThread>?, String?> {
    if (access.isNullOrBlank()) return null to access
    var token = access
    val (code, text) = try { httpGet("/messages/threads?postId=$postId", token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val (code2, text2) = try { httpGet("/messages/threads?postId=$postId", token) } catch (_: Exception) { -1 to null }
            if (code2 in 200..299 && !text2.isNullOrBlank()) {
                return try {
                    val arr = Json.parseToJsonElement(text2).jsonArray
                    val list = arr.map { el ->
                        val o = el.jsonObject
                        ChatThread(
                            userId = o["userId"]?.jsonPrimitive?.contentOrNull ?: "",
                            userName = o["userName"]?.jsonPrimitive?.contentOrNull ?: "",
                            lastText = o["lastText"]?.jsonPrimitive?.contentOrNull ?: "",
                            time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L
                        )
                    }
                    list to token
                } catch (_: Exception) { null to token }
            }
        }
        return null to token
    }
    if (code in 200..299 && !text.isNullOrBlank()) {
        return try {
            val arr = Json.parseToJsonElement(text).jsonArray
            val list = arr.map { el ->
                val o = el.jsonObject
                ChatThread(
                    userId = o["userId"]?.jsonPrimitive?.contentOrNull ?: "",
                    userName = o["userName"]?.jsonPrimitive?.contentOrNull ?: "",
                    lastText = o["lastText"]?.jsonPrimitive?.contentOrNull ?: "",
                    time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L
                )
            }
            list to token
        } catch (_: Exception) { null to token }
    }
    return null to token
}

@Composable
fun ChatDialog(
    recipient: String,
    messages: List<ChatMessage>,
    loading: Boolean = false,
    onSend: (String) -> Unit,
    onDismiss: () -> Unit,
    currentUser: String? = null
) {
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    LaunchedEffect(recipient, messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    val dialogContainerColor = MaterialTheme.colorScheme.surface
    val dialogTextColor = MaterialTheme.colorScheme.onSurface
    val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = dialogContainerColor,
        titleContentColor = dialogTextColor,
        textContentColor = dialogTextColor,
        title = null,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 260.dp, max = 600.dp)
                    .imePadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = recipient,
                            style = MaterialTheme.typography.titleMedium,
                            color = dialogTextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (loading) {
                            Spacer(Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp)
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    "Cargando mensajes...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = dialogSecondaryTextColor
                                )
                            }
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close),
                            tint = dialogTextColor
                        )
                    }
                }
                HorizontalDivider(color = dialogSecondaryTextColor.copy(alpha = 0.35f))
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (messages.isEmpty() && !loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_messages_yet),
                                color = dialogSecondaryTextColor,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 4.dp)
                        ) {
                            items(messages) { m ->
                                val isIncoming = if (currentUser != null) !m.from.equals(currentUser, ignoreCase = true) else m.from == recipient
                                val bubbleColor = if (isIncoming) Color(0xFF1B5E20) else Color(0xFF43A047)
                                val time = remember(m.time) {
                                    try {
                                        SimpleDateFormat("HH:mm").format(Date(m.time))
                                    } catch (_: Exception) { "" }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = if (isIncoming) Arrangement.Start else Arrangement.End
                                ) {
                                    Surface(
                                        color = bubbleColor,
                                        shape = RoundedCornerShape(16.dp),
                                        tonalElevation = 1.dp
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                                .widthIn(max = 280.dp)
                                        ) {
                                            Text(m.text, color = MaterialTheme.colorScheme.onPrimary)
                                            if (time.isNotEmpty()) {
                                                Spacer(Modifier.height(2.dp))
                                                Text(
                                                    time,
                                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                                    fontSize = 12.sp
                                                )
                                         }
                                     }
                                 }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        tonalElevation = 2.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        OutlinedTextField(
                            value = input,
                            onValueChange = { input = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            singleLine = true,
                            placeholder = { Text(stringResource(R.string.type_message_hint), color = dialogSecondaryTextColor) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = {
                                val t = input.trim()
                                if (t.isNotEmpty()) {
                                    onSend(t)
                                    input = ""
                                }
                            }),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = dialogTextColor,
                                unfocusedTextColor = dialogTextColor,
                                cursorColor = dialogTextColor,
                                focusedLabelColor = dialogSecondaryTextColor,
                                unfocusedLabelColor = dialogSecondaryTextColor,
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    }
                    FilledIconButton(
                        onClick = {
                            val t = input.trim()
                            if (t.isNotEmpty()) {
                                onSend(t)
                                input = ""
                            }
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = stringResource(R.string.send))
                    }
                }
            }
        },
        confirmButton = {}
    )
}

// ===== Remote Community (CRUD) =====
private fun fetchCommunityPostsRemote(context: Context, access: String?, refresh: String?): Pair<List<CommunityPost>?, String?> {
    if (access.isNullOrBlank()) return null to access
    var token = access
    val (code, text) = try { httpGet("/community/posts", token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val (code2, text2) = try { httpGet("/community/posts", token) } catch (_: Exception) { -1 to null }
            if (code2 in 200..299 && !text2.isNullOrBlank()) {
                return try {
                    val arr = JSONArray(text2)
                    val list = mutableListOf<CommunityPost>()
                    for (i in 0 until arr.length()) {
                        val o = arr.getJSONObject(i)
                        list += CommunityPost(
                            id = o.optLong("time"),
                            time = o.optLong("time"),
                            userId = o.optString("userId", "").takeIf { it.isNotBlank() },
                            user = o.optString("userName", ""),
                            sport = o.optString("sport", context.getString(R.string.sport_futbolito)),
                            available = o.optInt("available", 0),
                            total = o.optInt("total", 0),
                            message = o.optString("message", ""),
                            locality = o.optString("locality", ""),
                            serverId = o.optString("id", "").takeIf { it.isNotBlank() }
                        )
                    }
                    list to token
                } catch (_: Exception) { null to token }
            }
        }
        return null to token
    }
    if (code in 200..299 && !text.isNullOrBlank()) {
        return try {
            val arr = Json.parseToJsonElement(text).jsonArray
            val list = arr.map { el ->
                val o = el.jsonObject
                CommunityPost(
                    id = o["time"]?.jsonPrimitive?.longOrNull ?: 0L,
                    time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L,
                    userId = o["userId"]?.jsonPrimitive?.contentOrNull,
                    user = o["userName"]?.jsonPrimitive?.contentOrNull ?: "",
                    sport = o["sport"]?.jsonPrimitive?.contentOrNull ?: context.getString(R.string.sport_futbolito),
                    available = o["available"]?.jsonPrimitive?.intOrNull ?: 0,
                    total = o["total"]?.jsonPrimitive?.intOrNull ?: 0,
                    message = o["message"]?.jsonPrimitive?.contentOrNull ?: "",
                    locality = o["locality"]?.jsonPrimitive?.contentOrNull ?: "",
                    serverId = o["id"]?.jsonPrimitive?.contentOrNull
                )
            }
            list to token
        } catch (_: Exception) { null to token }
    }
    return null to token
}

private fun createCommunityPostRemote(context: Context, access: String?, refresh: String?, body: JSONObject): String? {
    if (access.isNullOrBlank()) return null
    var token = access
    var (code, _) = try { httpPostJson("/community/posts", body, token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val res2 = try { httpPostJson("/community/posts", body, token) } catch (_: Exception) { -1 to null }
            code = res2.first
        }
    }
    return if (code in 200..299) token else null
}

private fun fetchGroupMessagesRemote(context: Context, access: String?, refresh: String?, postId: String): Pair<List<ChatMessage>?, String?> {
    if (access.isNullOrBlank()) return null to access
    var token = access
    val (code, text) = try { httpGet("/messages/posts/$postId", token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val (code2, text2) = try { httpGet("/messages/posts/$postId", token) } catch (_: Exception) { -1 to null }
            if (code2 in 200..299 && !text2.isNullOrBlank()) {
                return try {
                    val arr = Json.parseToJsonElement(text2).jsonArray
                    val list = arr.map { el ->
                        val o = el.jsonObject
                        ChatMessage(
                            from = o["fromName"]?.jsonPrimitive?.contentOrNull ?: "",
                            to = "",
                            text = o["text"]?.jsonPrimitive?.contentOrNull ?: "",
                            time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L
                        )
                    }
                    list to token
                } catch (_: Exception) { null to token }
            }
        }
        return null to token
    }
    if (code in 200..299 && !text.isNullOrBlank()) {
        return try {
            val arr = Json.parseToJsonElement(text).jsonArray
            val list = arr.map { el ->
                val o = el.jsonObject
                ChatMessage(
                    from = o["fromName"]?.jsonPrimitive?.contentOrNull ?: "",
                    to = "",
                    text = o["text"]?.jsonPrimitive?.contentOrNull ?: "",
                    time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L
                )
            }
            list to token
        } catch (_: Exception) { null to token }
    }
    return null to token
}

private fun sendGroupMessageRemote(context: Context, access: String?, refresh: String?, postId: String, body: JSONObject): String? {
    if (access.isNullOrBlank()) return null
    var token = access
    var (code, _) = try { httpPostJson("/messages/posts/$postId", body, token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val res2 = try { httpPostJson("/messages/posts/$postId", body, token) } catch (_: Exception) { -1 to null }
            code = res2.first
        }
    }
    return if (code in 200..299) token else null
}

private fun updateCommunityPostRemote(context: Context, access: String?, refresh: String?, id: String, body: JSONObject): String? {
    if (access.isNullOrBlank()) return null
    var token = access
    var (code, _) = httpPostRaw("/community/posts/$id", body.toString(), token) // will be PUT below
    // Use proper PUT method
    val req = Request.Builder().url("$BASE_URL/community/posts/$id").put(body.toString().toRequestBody(JSON_MEDIA)).header("Authorization", "Bearer $token").build()
    try {
        httpClient.newCall(req).execute().use { resp ->
            code = resp.code
        }
    } catch (_: Exception) {
        code = -1
    }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val req2 = Request.Builder().url("$BASE_URL/community/posts/$id").put(body.toString().toRequestBody(JSON_MEDIA)).header("Authorization", "Bearer $token").build()
            try {
                httpClient.newCall(req2).execute().use { resp2 ->
                    code = resp2.code
                }
            } catch (_: Exception) {
                code = -1
            }
        }
    }
    return if (code in 200..299) token else null
}

private fun deleteCommunityPostRemote(context: Context, access: String?, refresh: String?, id: String): String? {
    if (access.isNullOrBlank()) return null
    var token = access
    var code: Int
    val req = Request.Builder().url("$BASE_URL/community/posts/$id").delete().header("Authorization", "Bearer $token").build()
    try {
        httpClient.newCall(req).execute().use { resp -> code = resp.code }
    } catch (_: Exception) {
        code = -1
    }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val req2 = Request.Builder().url("$BASE_URL/community/posts/$id").delete().header("Authorization", "Bearer $token").build()
            try {
                httpClient.newCall(req2).execute().use { resp2 -> code = resp2.code }
            } catch (_: Exception) {
                code = -1
            }
        }
    }
    return if (code in 200..299) token else null
}

private fun fetchMessagesRemote(context: Context, access: String?, refresh: String?, otherId: String, postId: String): Pair<List<ChatMessage>?, String?> {
    if (access.isNullOrBlank()) return null to access
    var token = access
    val (code, text) = try { httpGet("/messages?withUser=$otherId&postId=$postId&limit=200", token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val (code2, text2) = try { httpGet("/messages?withUser=$otherId&postId=$postId&limit=200", token) } catch (_: Exception) { -1 to null }
            if (code2 in 200..299 && !text2.isNullOrBlank()) {
                return try {
                    val arr = Json.parseToJsonElement(text2).jsonArray
                    val list = arr.map { el ->
                        val o = el.jsonObject
                        ChatMessage(
                            from = o["fromName"]?.jsonPrimitive?.contentOrNull ?: "",
                            to = o["toName"]?.jsonPrimitive?.contentOrNull ?: "",
                            text = o["text"]?.jsonPrimitive?.contentOrNull ?: "",
                            time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L
                        )
                    }
                    list to token
                } catch (_: Exception) { null to token }
            }
        }
        return null to token
    }
    if (code in 200..299 && !text.isNullOrBlank()) {
        return try {
            val arr = Json.parseToJsonElement(text).jsonArray
            val list = arr.map { el ->
                val o = el.jsonObject
                ChatMessage(
                    from = o["fromName"]?.jsonPrimitive?.contentOrNull ?: "",
                    to = o["toName"]?.jsonPrimitive?.contentOrNull ?: "",
                    text = o["text"]?.jsonPrimitive?.contentOrNull ?: "",
                    time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L
                )
            }
            list to token
        } catch (_: Exception) { null to token }
    }
    return null to token
}

private const val CHAT_MAX_MESSAGES = 300

private fun sendMessageRemote(context: Context, access: String?, refresh: String?, body: JSONObject): String? {
    if (access.isNullOrBlank()) return null
    var token = access
    var (code, _) = try { httpPostJson("/messages", body, token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val res2 = try { httpPostJson("/messages", body, token) } catch (_: Exception) { -1 to null }
            code = res2.first
        }
    }
    return if (code in 200..299) token else null
}

fun saveCommunityPosts(context: Context, posts: List<CommunityPost>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val arr = JSONArray()
    posts.forEach { p ->
        val o = JSONObject()
        o.put("id", p.id)
        o.put("time", p.time)
        o.put("user", p.user)
        o.put("sport", p.sport)
        o.put("available", p.available)
        o.put("total", p.total)
        o.put("message", p.message)
        o.put("locality", p.locality)
        if (p.serverId != null) o.put("serverId", p.serverId)
        arr.put(o)
    }
    prefs.edit().putString(KEY_COMMUNITY_POSTS, arr.toString()).apply()
}

fun loadCommunityPosts(context: Context): List<CommunityPost> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_COMMUNITY_POSTS, null) ?: return emptyList()
    return try {
        val arr = JSONArray(json)
        val list = mutableListOf<CommunityPost>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            list += CommunityPost(
                id = o.optLong("id"),
                time = o.optLong("time"),
                user = o.optString("user", ""),
                sport = o.optString("sport", context.getString(R.string.sport_futbolito)),
                available = o.optInt("available", 0),
                total = o.optInt("total", 0),
                message = o.optString("message", ""),
                locality = o.optString("locality", ""),
                serverId = o.optString("serverId", "").takeIf { it.isNotBlank() }
            )
        }
        list
    } catch (_: Exception) { emptyList() }
}

fun addCommunityPost(context: Context, post: CommunityPost) {
    val current = loadCommunityPosts(context).toMutableList()
    current.add(0, post)
    saveCommunityPosts(context, current)
}

fun loadUnreadByPost(context: Context): MutableMap<String, Int> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_COMMUNITY_UNREAD, null) ?: return mutableMapOf()
    return try {
        val obj = JSONObject(json)
        val map = mutableMapOf<String, Int>()
        obj.keys().forEach { key ->
            val v = obj.optInt(key, 0)
            if (v > 0) map[key] = v
        }
        map
    } catch (_: Exception) { mutableMapOf() }
}

fun saveUnreadByPost(context: Context, map: Map<String, Int>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val obj = JSONObject()
    map.forEach { (k, v) -> if (v > 0) obj.put(k, v) }
    prefs.edit().putString(KEY_COMMUNITY_UNREAD, obj.toString()).apply()
}

fun incrementUnreadForPost(context: Context, postId: String) {
    if (postId.isBlank()) return
    val current = loadUnreadByPost(context)
    val next = (current[postId] ?: 0) + 1
    current[postId] = next
    saveUnreadByPost(context, current)
}

fun clearUnreadForPost(context: Context, postId: String) {
    if (postId.isBlank()) return
    val current = loadUnreadByPost(context)
    if (current.remove(postId) != null) {
        saveUnreadByPost(context, current)
    }
}

@Composable
fun CommunityDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    var hasPermission by remember { mutableStateOf(false) }
    var locationDenied by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var currentLat by remember { mutableStateOf<Double?>(null) }
    var currentLon by remember { mutableStateOf<Double?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            val fine = result[Manifest.permission.ACCESS_FINE_LOCATION] == true
            val coarse = result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            hasPermission = fine || coarse
            locationDenied = !hasPermission
            if (hasPermission) {
                isLoading = true
                fused.lastLocation.addOnSuccessListener { loc ->
                    currentLat = loc?.latitude
                    currentLon = loc?.longitude
                    isLoading = false
                }.addOnFailureListener {
                    isLoading = false
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        hasPermission = fineGranted || coarseGranted
        if (hasPermission) {
            isLoading = true
            fused.lastLocation.addOnSuccessListener { loc ->
                currentLat = loc?.latitude
                currentLon = loc?.longitude
                isLoading = false
            }.addOnFailureListener {
                isLoading = false
            }
        }
    }

    val dialogContainerColor = MaterialTheme.colorScheme.surface
    val dialogTextColor = MaterialTheme.colorScheme.onSurface

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = dialogContainerColor,
        titleContentColor = dialogTextColor,
        textContentColor = dialogTextColor,
        title = { Text(stringResource(R.string.community), color = dialogTextColor) },
        text = {
            Column(Modifier.fillMaxWidth()) {
                if (!hasPermission) {
                    Text(stringResource(R.string.location_denied), color = dialogTextColor)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        locationDenied = false
                        launcher.launch(arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ))
                    }) { Text(stringResource(R.string.grant_location)) }
                } else {
                    Text(stringResource(R.string.nearby_users), color = dialogTextColor)
                    Spacer(Modifier.height(8.dp))
                    if (isLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.searching), color = dialogTextColor)
                        }
                    } else {
                        val lat = currentLat
                        val lon = currentLon
                        if (lat == null || lon == null) {
                            Text(stringResource(R.string.no_users_nearby), color = dialogTextColor)
                        } else {
                            // Placeholder list
                            val items = listOf(
                                "Diego" to 5.2,
                                "Ariel" to 12.7,
                                "Pablo" to 28.9
                            ).filter { it.second <= 30.0 }
                            if (items.isEmpty()) {
                                Text(stringResource(R.string.no_users_nearby), color = dialogTextColor)
                            } else {
                                LazyColumn(modifier = Modifier.heightIn(max = 280.dp)) {
                                    items(items) { (name, km) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column(Modifier.weight(1f)) {
                                                Text(name, fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                                Text(stringResource(R.string.distance_km, km), color = dialogTextColor)
                                            }
                                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                                OutlinedButton(onClick = { /* TODO: crear oferta */ }) { Text(stringResource(R.string.create_match_offer)) }
                                                Button(onClick = { /* TODO: publicar vacantes */ }) { Text(stringResource(R.string.create_vacancy)) }
                                            }
                                        }
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = { Button(onClick = onDismiss) { Text(stringResource(R.string.close)) } },
        dismissButton = null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    onBack: () -> Unit,
    initialPostId: String? = null,
    initialPeerName: String? = null,
    initialIsGroup: Boolean = false,
    isDarkTheme: Boolean = false
) {
    val context = LocalContext.current
    var message by remember { mutableStateOf("") }
    var sport by remember { mutableStateOf(context.getString(R.string.sport_futbolito)) }
    var available by remember { mutableStateOf(0) }
    var total by remember { mutableStateOf(0) }
    var availableText by remember { mutableStateOf("0") }
    var totalText by remember { mutableStateOf("0") }
    var locality by remember { mutableStateOf("") }
    var posts by remember { mutableStateOf<List<CommunityPost>>(emptyList()) }
    val user = userNameState.value ?: ""
    var currentUserId by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val sports = remember {
        listOf(
            context.getString(R.string.sport_soccer),
            context.getString(R.string.sport_futbolito),
            context.getString(R.string.sport_baby_soccer),
            context.getString(R.string.sport_padel),
            context.getString(R.string.sport_tennis),
            context.getString(R.string.sport_volleyball),
            context.getString(R.string.sport_rugby)
        )
    }
    var createSportExpanded by remember { mutableStateOf(false) }
    var editSportExpanded by remember { mutableStateOf(false) }
    val allLocalities = remember {
        listOf(
            // Región de Arica y Parinacota
            "Región de Arica y Parinacota - Arica",
            "Región de Arica y Parinacota - Camarones",
            "Región de Arica y Parinacota - Putre",
            "Región de Arica y Parinacota - General Lagos",

            // Región de Tarapacá
            "Región de Tarapacá - Iquique",
            "Región de Tarapacá - Alto Hospicio",
            "Región de Tarapacá - Pozo Almonte",
            "Región de Tarapacá - Camiña",
            "Región de Tarapacá - Colchane",
            "Región de Tarapacá - Huara",
            "Región de Tarapacá - Pica",

            // Región de Antofagasta
            "Región de Antofagasta - Antofagasta",
            "Región de Antofagasta - Mejillones",
            "Región de Antofagasta - Sierra Gorda",
            "Región de Antofagasta - Taltal",
            "Región de Antofagasta - Calama",
            "Región de Antofagasta - Ollagüe",
            "Región de Antofagasta - San Pedro de Atacama",
            "Región de Antofagasta - Tocopilla",
            "Región de Antofagasta - María Elena",

            // Región de Atacama
            "Región de Atacama - Copiapó",
            "Región de Atacama - Caldera",
            "Región de Atacama - Tierra Amarilla",
            "Región de Atacama - Chañaral",
            "Región de Atacama - Diego de Almagro",
            "Región de Atacama - Vallenar",
            "Región de Atacama - Alto del Carmen",
            "Región de Atacama - Freirina",
            "Región de Atacama - Huasco",

            // Región de Coquimbo
            "Región de Coquimbo - La Serena",
            "Región de Coquimbo - Coquimbo",
            "Región de Coquimbo - Andacollo",
            "Región de Coquimbo - La Higuera",
            "Región de Coquimbo - Paihuano",
            "Región de Coquimbo - Vicuña",
            "Región de Coquimbo - Illapel",
            "Región de Coquimbo - Canela",
            "Región de Coquimbo - Los Vilos",
            "Región de Coquimbo - Salamanca",
            "Región de Coquimbo - Ovalle",
            "Región de Coquimbo - Combarbalá",
            "Región de Coquimbo - Monte Patria",
            "Región de Coquimbo - Punitaqui",
            "Región de Coquimbo - Río Hurtado",

            // Región de Valparaíso
            "Región de Valparaíso - Valparaíso",
            "Región de Valparaíso - Viña del Mar",
            "Región de Valparaíso - Concón",
            "Región de Valparaíso - Quilpué",
            "Región de Valparaíso - Villa Alemana",
            "Región de Valparaíso - Casablanca",
            "Región de Valparaíso - Quintero",
            "Región de Valparaíso - Puchuncaví",
            "Región de Valparaíso - Juan Fernández",
            "Región de Valparaíso - San Antonio",
            "Región de Valparaíso - Cartagena",
            "Región de Valparaíso - El Tabo",
            "Región de Valparaíso - El Quisco",
            "Región de Valparaíso - Algarrobo",
            "Región de Valparaíso - Santo Domingo",
            "Región de Valparaíso - Quillota",
            "Región de Valparaíso - La Cruz",
            "Región de Valparaíso - La Calera",
            "Región de Valparaíso - Nogales",
            "Región de Valparaíso - Hijuelas",
            "Región de Valparaíso - San Felipe",
            "Región de Valparaíso - Putaendo",
            "Región de Valparaíso - Santa María",
            "Región de Valparaíso - Llay-Llay",
            "Región de Valparaíso - Catemu",
            "Región de Valparaíso - Panquehue",
            "Región de Valparaíso - Los Andes",
            "Región de Valparaíso - Calle Larga",
            "Región de Valparaíso - Rinconada",
            "Región de Valparaíso - San Esteban",
            "Región de Valparaíso - Isla de Pascua",

            // Región Metropolitana
            "Región Metropolitana - Santiago",
            "Región Metropolitana - Providencia",
            "Región Metropolitana - Las Condes",
            "Región Metropolitana - Vitacura",
            "Región Metropolitana - Ñuñoa",
            "Región Metropolitana - La Reina",
            "Región Metropolitana - Peñalolén",
            "Región Metropolitana - Macul",
            "Región Metropolitana - La Florida",
            "Región Metropolitana - Puente Alto",
            "Región Metropolitana - San José de Maipo",
            "Región Metropolitana - Pirque",
            "Región Metropolitana - Maipú",
            "Región Metropolitana - Cerrillos",
            "Región Metropolitana - Estación Central",
            "Región Metropolitana - Pudahuel",
            "Región Metropolitana - Lo Prado",
            "Región Metropolitana - Quinta Normal",
            "Región Metropolitana - Cerro Navia",
            "Región Metropolitana - Renca",
            "Región Metropolitana - Independencia",
            "Región Metropolitana - Recoleta",
            "Región Metropolitana - Conchalí",
            "Región Metropolitana - Huechuraba",
            "Región Metropolitana - Quilicura",
            "Región Metropolitana - Lo Barnechea",
            "Región Metropolitana - San Miguel",
            "Región Metropolitana - San Joaquín",
            "Región Metropolitana - La Cisterna",
            "Región Metropolitana - San Ramón",
            "Región Metropolitana - La Granja",
            "Región Metropolitana - El Bosque",
            "Región Metropolitana - San Bernardo",
            "Región Metropolitana - Buin",
            "Región Metropolitana - Paine",
            "Región Metropolitana - Calera de Tango",
            "Región Metropolitana - Talagante",
            "Región Metropolitana - Peñaflor",
            "Región Metropolitana - Isla de Maipo",
            "Región Metropolitana - El Monte",
            "Región Metropolitana - Padre Hurtado",
            "Región Metropolitana - Melipilla",
            "Región Metropolitana - Curacaví",
            "Región Metropolitana - María Pinto",
            "Región Metropolitana - Alhué",

            // Región de O'Higgins
            "Región de O'Higgins - Rancagua",
            "Región de O'Higgins - Machalí",
            "Región de O'Higgins - Graneros",
            "Región de O'Higgins - Mostazal",
            "Región de O'Higgins - Codegua",
            "Región de O'Higgins - Requínoa",
            "Región de O'Higgins - Rengo",
            "Región de O'Higgins - Malloa",
            "Región de O'Higgins - Quinta de Tilcoco",
            "Región de O'Higgins - San Vicente",
            "Región de O'Higgins - Pichidegua",
            "Región de O'Higgins - Peumo",
            "Región de O'Higgins - Las Cabras",
            "Región de O'Higgins - Doñihue",
            "Región de O'Higgins - Coinco",
            "Región de O'Higgins - Coltauco",
            "Región de O'Higgins - San Fernando",
            "Región de O'Higgins - Chimbarongo",
            "Región de O'Higgins - Nancagua",
            "Región de O'Higgins - Placilla",
            "Región de O'Higgins - Santa Cruz",
            "Región de O'Higgins - Chépica",
            "Región de O'Higgins - Palmilla",
            "Región de O'Higgins - Peralillo",
            "Región de O'Higgins - Lolol",
            "Región de O'Higgins - Pumanque",
            "Región de O'Higgins - Pichilemu",
            "Región de O'Higgins - Marchihue",
            "Región de O'Higgins - La Estrella",
            "Región de O'Higgins - Litueche",
            "Región de O'Higgins - Navidad",

            // Región del Maule
            "Región del Maule - Talca",
            "Región del Maule - Maule",
            "Región del Maule - San Clemente",
            "Región del Maule - Pelarco",
            "Región del Maule - Pencahue",
            "Región del Maule - Curepto",
            "Región del Maule - Constitución",
            "Región del Maule - Empedrado",
            "Región del Maule - Linares",
            "Región del Maule - Yerbas Buenas",
            "Región del Maule - Colbún",
            "Región del Maule - Villa Alegre",
            "Región del Maule - San Javier",
            "Región del Maule - Retiro",
            "Región del Maule - Parral",
            "Región del Maule - Cauquenes",
            "Región del Maule - Pelluhue",
            "Región del Maule - Chanco",
            "Región del Maule - Curicó",
            "Región del Maule - Romeral",
            "Región del Maule - Teno",
            "Región del Maule - Rauco",
            "Región del Maule - Sagrada Familia",
            "Región del Maule - Molina",
            "Región del Maule - Hualañé",

            // Región de Ñuble
            "Región de Ñuble - Chillán",
            "Región de Ñuble - Chillán Viejo",
            "Región de Ñuble - San Carlos",
            "Región de Ñuble - Coihueco",
            "Región de Ñuble - San Nicolás",
            "Región de Ñuble - Ñiquén",
            "Región de Ñuble - San Fabián",
            "Región de Ñuble - Bulnes",
            "Región de Ñuble - Quillón",
            "Región de Ñuble - San Ignacio",
            "Región de Ñuble - El Carmen",
            "Región de Ñuble - Yungay",
            "Región de Ñuble - Pemuco",
            "Región de Ñuble - Pinto",
            "Región de Ñuble - Coelemu",
            "Región de Ñuble - Trehuaco",
            "Región de Ñuble - Ránquil",
            "Región de Ñuble - Quirihue",
            "Región de Ñuble - Cobquecura",
            "Región de Ñuble - Ninhue",
            "Región de Ñuble - Portezuelo",

            // Región del Biobío
            "Región del Biobío - Concepción",
            "Región del Biobío - Talcahuano",
            "Región del Biobío - Hualpén",
            "Región del Biobío - Penco",
            "Región del Biobío - Tomé",
            "Región del Biobío - Chiguayante",
            "Región del Biobío - San Pedro de la Paz",
            "Región del Biobío - Coronel",
            "Región del Biobío - Lota",
            "Región del Biobío - Hualqui",
            "Región del Biobío - Santa Juana",
            "Región del Biobío - Florida",
            "Región del Biobío - Cabrero",
            "Región del Biobío - Yumbel",
            "Región del Biobío - Laja",
            "Región del Biobío - San Rosendo",
            "Región del Biobío - Los Ángeles",
            "Región del Biobío - Mulchén",
            "Región del Biobío - Nacimiento",
            "Región del Biobío - Negrete",
            "Región del Biobío - Santa Bárbara",
            "Región del Biobío - Quilaco",
            "Región del Biobío - Quilleco",
            "Región del Biobío - Tucapel",
            "Región del Biobío - Antuco",
            "Región del Biobío - Alto Biobío",
            "Región del Biobío - Arauco",
            "Región del Biobío - Curanilahue",
            "Región del Biobío - Lebu",
            "Región del Biobío - Los Álamos",
            "Región del Biobío - Cañete",
            "Región del Biobío - Tirúa",

            // Región de La Araucanía
            "Región de La Araucanía - Temuco",
            "Región de La Araucanía - Padre Las Casas",
            "Región de La Araucanía - Lautaro",
            "Región de La Araucanía - Vilcún",
            "Región de La Araucanía - Cunco",
            "Región de La Araucanía - Melipeuco",
            "Región de La Araucanía - Curacautín",
            "Región de La Araucanía - Lonquimay",
            "Región de La Araucanía - Galvarino",
            "Región de La Araucanía - Cholchol",
            "Región de La Araucanía - Nueva Imperial",
            "Región de La Araucanía - Carahue",
            "Región de La Araucanía - Saavedra",
            "Región de La Araucanía - Toltén",
            "Región de La Araucanía - Teodoro Schmidt",
            "Región de La Araucanía - Pitrufquén",
            "Región de La Araucanía - Gorbea",
            "Región de La Araucanía - Loncoche",
            "Región de La Araucanía - Villarrica",
            "Región de La Araucanía - Pucón",
            "Región de La Araucanía - Freire",
            "Región de La Araucanía - Perquenco",
            "Región de La Araucanía - Ercilla",
            "Región de La Araucanía - Collipulli",
            "Región de La Araucanía - Angol",
            "Región de La Araucanía - Renaico",
            "Región de La Araucanía - Purén",
            "Región de La Araucanía - Los Sauces",
            "Región de La Araucanía - Traiguén",
            "Región de La Araucanía - Lumaco",

            // Región de Los Ríos
            "Región de Los Ríos - Valdivia",
            "Región de Los Ríos - Corral",
            "Región de Los Ríos - Lanco",
            "Región de Los Ríos - Máfil",
            "Región de Los Ríos - Mariquina",
            "Región de Los Ríos - Paillaco",
            "Región de Los Ríos - Los Lagos",
            "Región de Los Ríos - Futrono",
            "Región de Los Ríos - La Unión",
            "Región de Los Ríos - Río Bueno",

            // Región de Los Lagos
            "Región de Los Lagos - Puerto Montt",
            "Región de Los Lagos - Puerto Varas",
            "Región de Los Lagos - Llanquihue",
            "Región de Los Lagos - Frutillar",
            "Región de Los Lagos - Los Muermos",
            "Región de Los Lagos - Maullín",
            "Región de Los Lagos - Calbuco",
            "Región de Los Lagos - Cochamó",
            "Región de Los Lagos - Osorno",
            "Región de Los Lagos - Puyehue",
            "Región de Los Lagos - Río Negro",
            "Región de Los Lagos - San Pablo",
            "Región de Los Lagos - San Juan de la Costa",
            "Región de Los Lagos - Castro",
            "Región de Los Lagos - Ancud",
            "Región de Los Lagos - Quellón",
            "Región de Los Lagos - Chonchi",
            "Región de Los Lagos - Dalcahue",
            "Región de Los Lagos - Quemchi",
            "Región de Los Lagos - Curaco de Vélez",
            "Región de Los Lagos - Quinchao",
            "Región de Los Lagos - Puqueldón",

            // Región de Aysén
            "Región de Aysén - Coyhaique",
            "Región de Aysén - Lago Verde",
            "Región de Aysén - Aysén",
            "Región de Aysén - Cisnes",
            "Región de Aysén - Guaitecas",
            "Región de Aysén - Cochrane",
            "Región de Aysén - O'Higgins",
            "Región de Aysén - Tortel",
            "Región de Aysén - Chile Chico",
            "Región de Aysén - Río Ibáñez",

            // Región de Magallanes
            "Región de Magallanes - Punta Arenas",
            "Región de Magallanes - Puerto Natales",
            "Región de Magallanes - Torres del Paine",
            "Región de Magallanes - Laguna Blanca",
            "Región de Magallanes - Río Verde",
            "Región de Magallanes - San Gregorio",
            "Región de Magallanes - Porvenir",
            "Región de Magallanes - Primavera",
            "Región de Magallanes - Timaukel",
            "Región de Magallanes - Cabo de Hornos",
            "Región de Magallanes - Antártica"
        )
    }
    val regionsWithCommunes = remember(allLocalities) {
        allLocalities.groupBy { it.substringBefore(" - ") }
            .mapValues { entry -> entry.value.map { it.substringAfter(" - ") } }
    }
    var localitySearch by remember { mutableStateOf("") }
    var localityExpanded by remember { mutableStateOf(false) }
    var expandedRegions by remember { mutableStateOf(setOf<String>()) }
    var filterSport by remember { mutableStateOf<String?>(null) }
    var filterLocality by remember { mutableStateOf<String?>(null) }
    var filterLocalitySearch by remember { mutableStateOf("") }
    var filterLocalityExpanded by remember { mutableStateOf(false) }
    var filterExpandedRegions by remember { mutableStateOf(setOf<String>()) }
    var filterSportExpanded by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    BackHandler { onBack() }
    var editingId by remember { mutableStateOf<Long?>(null) }
    var editingMessage by remember { mutableStateOf("") }
    var editingSport by remember { mutableStateOf(context.getString(R.string.sport_futbolito)) }
    var editingAvailable by remember { mutableStateOf(0) }
    var editingTotal by remember { mutableStateOf(0) }
    var editingLocality by remember { mutableStateOf("") }
    var editingLocalitySearch by remember { mutableStateOf("") }
    var editingLocalityExpanded by remember { mutableStateOf(false) }
    var chatRecipientName by remember { mutableStateOf<String?>(null) }
    var chatRecipientId by remember { mutableStateOf<String?>(null) }
    var chatPostId by remember { mutableStateOf<String?>(null) }
    var groupPostId by remember { mutableStateOf<String?>(null) }
    val chats = remember { mutableStateMapOf<String, List<ChatMessage>>() }
    val unreadByPost = remember { mutableStateMapOf<String, Int>() }
    var ws by remember { mutableStateOf<WebSocket?>(null) }
    var chatLoading by remember { mutableStateOf(false) }
    var groupLoading by remember { mutableStateOf(false) }
    var deletingPost by remember { mutableStateOf<CommunityPost?>(null) }
    var threadsForPost by remember { mutableStateOf<CommunityPost?>(null) }
    var threads by remember { mutableStateOf<List<ChatThread>>(emptyList()) }
    var threadsLoading by remember { mutableStateOf(false) }
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    val geocoder = remember { Geocoder(context, Locale.getDefault()) }
    var currentLat by remember { mutableStateOf<Double?>(null) }
    var currentLon by remember { mutableStateOf<Double?>(null) }
    val localityCoordinates = remember { mutableStateMapOf<String, Pair<Double, Double>>() }
    var hasLocationPermission by remember { mutableStateOf(false) }
    var filterByLocation by remember { mutableStateOf(false) }
    var currentLocalityFilter by remember { mutableStateOf<String?>(null) }
    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            val fine = result[Manifest.permission.ACCESS_FINE_LOCATION] == true
            val coarse = result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            hasLocationPermission = fine || coarse
            if (hasLocationPermission) {
                fused.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        currentLat = loc.latitude
                        currentLon = loc.longitude
                        val addresses = try {
                            geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                        } catch (_: Exception) { null }
                        val addr = addresses?.firstOrNull()
                        val localityName = addr?.locality ?: addr?.subAdminArea ?: addr?.adminArea
                        currentLocalityFilter = localityName
                    }
                }
            } else {
                filterByLocation = false
                currentLocalityFilter = null
            }
        }
    )
    LaunchedEffect(Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        hasLocationPermission = fineGranted || coarseGranted
    }
    DisposableEffect(Unit) {
        isCommunityForeground = true
        onDispose {
            isCommunityForeground = false
        }
    }
    LaunchedEffect(Unit) {
        val (at, rt) = loadTokens(context)
        currentUserId = decodeUserIdFromToken(at)
        val remote = withContext(Dispatchers.IO) { fetchCommunityPostsRemote(context, at, rt).first }
        posts = remote ?: loadCommunityPosts(context)
        val storedUnread = loadUnreadByPost(context)
        unreadByPost.clear()
        unreadByPost.putAll(storedUnread)
        if (!initialPostId.isNullOrBlank()) {
            val pid = initialPostId
            val target = posts.firstOrNull { it.serverId == pid }
            if (initialIsGroup) {
                groupPostId = target?.serverId ?: pid
                cancelChatNotification(context, pid, null, true)
                val key = target?.serverId ?: pid
                if (!key.isNullOrBlank()) {
                    unreadByPost.remove(key)
                    clearUnreadForPost(context, key)
                }
            } else {
                val peerName = initialPeerName
                if (!peerName.isNullOrBlank()) {
                    var peerId: String? = null
                    if (!at.isNullOrBlank()) {
                        val pair = withContext(Dispatchers.IO) { fetchThreadsRemote(context, at, rt, pid) }
                        val list = pair.first
                        peerId = list?.firstOrNull { it.userName.equals(peerName, ignoreCase = true) }?.userId
                    }
                    chatRecipientName = peerName
                    chatRecipientId = peerId
                    chatPostId = pid
                    cancelChatNotification(context, pid, peerName, false)
                    if (!pid.isNullOrBlank()) {
                        unreadByPost.remove(pid)
                        clearUnreadForPost(context, pid)
                    }
                } else if (target != null && !target.userId.isNullOrBlank()) {
                    // Fallback: abrir chat con el autor del post
                    chatRecipientName = target.user
                    chatRecipientId = target.userId
                    chatPostId = target.serverId
                    cancelChatNotification(context, target.serverId, target.user, false)
                    val key = target.serverId
                    if (!key.isNullOrBlank()) {
                        unreadByPost.remove(key)
                        clearUnreadForPost(context, key)
                    }
                }
            }
        }
        // Open WebSocket for realtime
        val access = at
        if (!access.isNullOrBlank()) {
            val wsUrl = BASE_URL.replaceFirst("https", "wss").replaceFirst("http", "ws") + "/ws"
            val req = Request.Builder().url(wsUrl).header("Authorization", "Bearer $access").build()
            val listener = object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    // OkHttp llama callbacks en un hilo de fondo: mutar estado Compose sólo en Main
                    scope.launch { ws = webSocket }
                }
                override fun onMessage(webSocket: WebSocket, text: String) {
                    try {
                        val root = Json.parseToJsonElement(text).jsonObject
                        val type = root["type"]?.jsonPrimitive?.contentOrNull
                        val data = root["data"]
                        if (type == "post_created" || type == "post_updated") {
                            val o = data?.jsonObject ?: return
                            val p = CommunityPost(
                                id = o["time"]?.jsonPrimitive?.longOrNull ?: 0L,
                                time = o["time"]?.jsonPrimitive?.longOrNull ?: 0L,
                                userId = o["userId"]?.jsonPrimitive?.contentOrNull,
                                user = o["userName"]?.jsonPrimitive?.contentOrNull ?: "",
                                sport = o["sport"]?.jsonPrimitive?.contentOrNull ?: context.getString(R.string.sport_futbolito),
                                available = o["available"]?.jsonPrimitive?.intOrNull ?: 0,
                                total = o["total"]?.jsonPrimitive?.intOrNull ?: 0,
                                message = o["message"]?.jsonPrimitive?.contentOrNull ?: "",
                                locality = o["locality"]?.jsonPrimitive?.contentOrNull ?: "",
                                serverId = o["id"]?.jsonPrimitive?.contentOrNull
                            )
                            // Update list on main
                            scope.launch {
                                posts = if (type == "post_created") {
                                    // prepend if not exists
                                    val exists = posts.any { it.serverId == p.serverId }
                                    if (exists) posts.map { if (it.serverId == p.serverId) p else it } else listOf(p) + posts
                                } else {
                                    posts.map { if (it.serverId == p.serverId) p else it }
                                }
                            }
                        } else if (type == "post_deleted") {
                            val id = data?.jsonObject?.get("id")?.jsonPrimitive?.contentOrNull
                            if (id != null) {
                                scope.launch { posts = posts.filterNot { it.serverId == id } }
                            }
                        } else if (type == "message_new") {
                            val o = data?.jsonObject ?: return
                            val fromName = o["fromName"]?.jsonPrimitive?.contentOrNull ?: ""
                            val toName = o["toName"]?.jsonPrimitive?.contentOrNull ?: ""
                            val textMsg = o["text"]?.jsonPrimitive?.contentOrNull ?: ""
                            val t = o["time"]?.jsonPrimitive?.longOrNull ?: System.currentTimeMillis()
                            val pid = o["postId"]?.jsonPrimitive?.contentOrNull
                            val partner = if (fromName.equals(user, ignoreCase = true)) toName else fromName
                            scope.launch {
                                val key = if (!pid.isNullOrBlank()) "$pid|$partner" else partner

                                val isPrivate = !pid.isNullOrBlank()
                                val chatOpen = if (isPrivate) {
                                    chatRecipientName?.equals(partner, ignoreCase = true) == true && chatPostId == pid
                                } else {
                                    chatRecipientName?.equals(partner, ignoreCase = true) == true && chatPostId == null
                                }

                                if (chatOpen) {
                                    val existing = chats[key] ?: emptyList()
                                    val already = existing.any { it.time == t && it.text == textMsg && it.from.equals(fromName, ignoreCase = true) }
                                    if (!already) {
                                        chats[key] = (existing + ChatMessage(from = fromName, to = toName, text = textMsg, time = t))
                                            .takeLast(CHAT_MAX_MESSAGES)
                                    }
                                }
                                if (!fromName.equals(user, ignoreCase = true) && !chatOpen) {
                                    val title = if (isPrivate) "Nuevo mensaje de $partner" else "Nuevo mensaje"
                                    showChatNotification(
                                        context,
                                        title,
                                        textMsg,
                                        postId = pid,
                                        peerName = partner,
                                        isGroup = false
                                    )
                                    if (!pid.isNullOrBlank()) {
                                        val current = unreadByPost[pid] ?: 0
                                        unreadByPost[pid] = current + 1
                                        incrementUnreadForPost(context, pid)
                                    }
                                }
                            }
                        } else if (type == "post_message_new") {
                            val o = data?.jsonObject ?: return
                            val fromName = o["fromName"]?.jsonPrimitive?.contentOrNull ?: ""
                            val textMsg = o["text"]?.jsonPrimitive?.contentOrNull ?: ""
                            val t = o["time"]?.jsonPrimitive?.longOrNull ?: System.currentTimeMillis()
                            val pid = o["postId"]?.jsonPrimitive?.contentOrNull
                            if (!pid.isNullOrBlank()) {
                                scope.launch {
                                    val key = "$pid|__group"
                                    val existing = chats[key] ?: emptyList()
                                    val chatOpen = groupPostId == pid
                                    if (chatOpen) {
                                        chats[key] = (existing + ChatMessage(from = fromName, to = "", text = textMsg, time = t))
                                            .takeLast(CHAT_MAX_MESSAGES)
                                    }
                                    if (!fromName.equals(user, ignoreCase = true)) {
                                        if (!chatOpen) {
                                            val title = "Nuevo mensaje en comunidad"
                                            val body = "$fromName: $textMsg"
                                            showChatNotification(
                                                context,
                                                title,
                                                body,
                                                postId = pid,
                                                peerName = null,
                                                isGroup = true
                                            )
                                            val current = unreadByPost[pid] ?: 0
                                            unreadByPost[pid] = current + 1
                                            incrementUnreadForPost(context, pid)
                                        }
                                    }
                                }
                            }
                        }
                    } catch (_: Exception) {}
                }
                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    scope.launch { if (ws === webSocket) ws = null }
                }
                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    scope.launch { if (ws === webSocket) ws = null }
                }
            }
            try {
                httpClient.newWebSocket(req, listener)
            } catch (_: Exception) {}
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            val stored = loadUnreadByPost(context)
            unreadByPost.clear()
            unreadByPost.putAll(stored)
            delay(1000)
        }
    }
    // Ensure socket is closed when leaving the screen
    DisposableEffect(ws) {
        onDispose {
            try { ws?.close(1000, "leaving") } catch (_: Exception) {}
            ws = null
        }
    }
    // Fallback: periodic sync if WebSocket is not connected
    LaunchedEffect(ws) {
        while (true) {
            if (ws == null) {
                val (at, rt) = loadTokens(context)
                if (!at.isNullOrBlank()) {
                    val remote = withContext(Dispatchers.IO) { fetchCommunityPostsRemote(context, at, rt).first }
                    if (remote != null) posts = remote
                }
            }
            delay(15000)
        }
    }

    val visiblePosts = remember(posts, filterSport, filterLocality) {
        var list = posts
        val sportFilter = filterSport
        if (!sportFilter.isNullOrBlank() && !sportFilter.equals("Todos", ignoreCase = true)) {
            list = list.filter { it.sport.equals(sportFilter, ignoreCase = true) }
        }
        val locFilter = filterLocality
        if (!locFilter.isNullOrBlank()) {
            list = list.filter { p ->
                p.locality.contains(locFilter, ignoreCase = true) ||
                        locFilter.contains(p.locality, ignoreCase = true)
            }
        }
        list
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            ThreeDTopAppBar(
                baseColor = MaterialTheme.colorScheme.primary,
                title = { Text(stringResource(R.string.community_title), color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            val (at, rt) = loadTokens(context)
                            val remote = withContext(Dispatchers.IO) { fetchCommunityPostsRemote(context, at, rt).first }
                            if (remote != null) {
                                posts = remote
                                snackbarHostState.showSnackbar(context.getString(R.string.post_published_remote))
                            } else {
                                posts = loadCommunityPosts(context)
                                snackbarHostState.showSnackbar(context.getString(R.string.post_published_local))
                            }
                        }
                    }) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { inner ->
        val postTextColor = if (isDarkTheme) Color.White else Color.Black
        val postSecondaryTextColor = if (isDarkTheme) Color.White.copy(alpha = 0.82f) else Color.Black.copy(alpha = 0.82f)
        val isValid = message.isNotBlank() && locality.isNotBlank() && available > 0 && total > 0 && available <= total
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = postTextColor
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    val cardTextColor = postTextColor
                    val cardSecondaryTextColor = postSecondaryTextColor
                    val menuContainerColor = MaterialTheme.colorScheme.surface
                    val menuTextColor = postTextColor

                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            label = { Text(stringResource(R.string.post_message_label), color = cardSecondaryTextColor) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = false,
                            minLines = 3,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = cardTextColor,
                                unfocusedTextColor = cardTextColor,
                                cursorColor = cardTextColor,
                                focusedLabelColor = cardSecondaryTextColor,
                                unfocusedLabelColor = cardSecondaryTextColor
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = sport,
                                    onValueChange = { },
                                    label = { Text(stringResource(R.string.sport_label), color = cardSecondaryTextColor) },
                                    modifier = Modifier.fillMaxWidth().clickable { createSportExpanded = true },
                                    readOnly = true,
                                    trailingIcon = {
                                        IconButton(onClick = { createSportExpanded = !createSportExpanded }) {
                                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                        }
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = cardTextColor,
                                        unfocusedTextColor = cardTextColor,
                                        cursorColor = cardTextColor,
                                        focusedLabelColor = cardSecondaryTextColor,
                                        unfocusedLabelColor = cardSecondaryTextColor
                                    )
                                )
                                DropdownMenu(
                                    expanded = createSportExpanded,
                                    onDismissRequest = { createSportExpanded = false },
                                    containerColor = menuContainerColor
                                ) {
                                    sports.forEach { s ->
                                        DropdownMenuItem(
                                            text = { Text(s, color = menuTextColor) },
                                            colors = MenuDefaults.itemColors(textColor = menuTextColor),
                                            onClick = {
                                                sport = s
                                                createSportExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                val localityText = if (localitySearch.isNotEmpty()) localitySearch else locality
                                OutlinedTextField(
                                    value = localityText,
                                    onValueChange = { text ->
                                        localitySearch = text
                                        locality = text
                                        localityExpanded = true
                                    },
                                    label = { Text(stringResource(R.string.locality_label), color = cardSecondaryTextColor) },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    trailingIcon = {
                                        IconButton(onClick = { localityExpanded = !localityExpanded }) {
                                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                        }
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = cardTextColor,
                                        unfocusedTextColor = cardTextColor,
                                        cursorColor = cardTextColor,
                                        focusedLabelColor = cardSecondaryTextColor,
                                        unfocusedLabelColor = cardSecondaryTextColor
                                    )
                                )
                                DropdownMenu(
                                    expanded = localityExpanded,
                                    onDismissRequest = { localityExpanded = false },
                                    containerColor = menuContainerColor
                                ) {
                                    if (localitySearch.isBlank()) {
                                        regionsWithCommunes.forEach { (region, communes) ->
                                            DropdownMenuItem(
                                                text = { Text(region, color = menuTextColor, fontWeight = FontWeight.SemiBold) },
                                                colors = MenuDefaults.itemColors(textColor = menuTextColor),
                                                onClick = {
                                                    expandedRegions = if (expandedRegions.contains(region)) {
                                                        expandedRegions - region
                                                    } else {
                                                        expandedRegions + region
                                                    }
                                                }
                                            )
                                            if (expandedRegions.contains(region)) {
                                                communes.forEach { comuna ->
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = comuna,
                                                                color = menuTextColor,
                                                                modifier = Modifier.padding(start = 16.dp)
                                                            )
                                                        },
                                                        colors = MenuDefaults.itemColors(textColor = menuTextColor),
                                                        onClick = {
                                                            val full = "$region - $comuna"
                                                            locality = full
                                                            localitySearch = full
                                                            localityExpanded = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        val filtered = allLocalities.filter { it.contains(localitySearch, ignoreCase = true) }
                                        filtered.forEach { loc ->
                                            DropdownMenuItem(
                                                text = { Text(loc, color = menuTextColor) },
                                                colors = MenuDefaults.itemColors(textColor = menuTextColor),
                                                onClick = {
                                                    locality = loc
                                                    localitySearch = loc
                                                    localityExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = availableText,
                                onValueChange = { v ->
                                    val digits = v.filter { it.isDigit() }
                                    availableText = digits
                                    available = digits.toIntOrNull() ?: 0
                                },
                                label = { Text(stringResource(R.string.available_players), color = cardSecondaryTextColor) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && availableText == "0") {
                                            availableText = ""
                                        }
                                    },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = cardTextColor,
                                    unfocusedTextColor = cardTextColor,
                                    cursorColor = cardTextColor,
                                    focusedLabelColor = cardSecondaryTextColor,
                                    unfocusedLabelColor = cardSecondaryTextColor
                                )
                            )
                            OutlinedTextField(
                                value = totalText,
                                onValueChange = { v ->
                                    val digits = v.filter { it.isDigit() }
                                    totalText = digits
                                    total = digits.toIntOrNull() ?: 0
                                },
                                label = { Text(stringResource(R.string.total_players), color = cardSecondaryTextColor) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && totalText == "0") {
                                            totalText = ""
                                        }
                                    },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = cardTextColor,
                                    unfocusedTextColor = cardTextColor,
                                    cursorColor = cardTextColor,
                                    focusedLabelColor = cardSecondaryTextColor,
                                    unfocusedLabelColor = cardSecondaryTextColor
                                )
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            Button(
                                enabled = isValid,
                                onClick = {
                                    if (message.isBlank() || locality.isBlank() || available <= 0 || total <= 0) {
                                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_required)) }
                                        return@Button
                                    }
                                    if (available > total) {
                                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_counts)) }
                                        return@Button
                                    }
                                    val now = System.currentTimeMillis()
                                    val post = CommunityPost(
                                        id = now,
                                        time = now,
                                        user = if (user.isNotBlank()) user else "",
                                        sport = sport.ifBlank { context.getString(R.string.sport_futbolito) },
                                        available = available,
                                        total = total,
                                        message = message.trim(),
                                        locality = locality.trim()
                                    )
                                    scope.launch {
                                        // Remote create; fallback to local
                                        val (at, rt) = loadTokens(context)
                                        val body = JSONObject()
                                            .put("userName", post.user)
                                            .put("sport", post.sport)
                                            .put("available", post.available)
                                            .put("total", post.total)
                                            .put("message", post.message)
                                            .put("locality", post.locality)
                                            .put("time", post.time)
                                        val token = withContext(Dispatchers.IO) { createCommunityPostRemote(context, at, rt, body) }
                                        if (token != null) {
                                            val remote = withContext(Dispatchers.IO) { fetchCommunityPostsRemote(context, token, rt).first }
                                            if (remote != null) posts = remote
                                            message = ""; available = 0; total = 0; locality = ""
                                            snackbarHostState.showSnackbar(context.getString(R.string.post_published_remote))
                                        } else {
                                            addCommunityPost(context, post)
                                            posts = loadCommunityPosts(context)
                                            message = ""; available = 0; total = 0; locality = ""
                                            snackbarHostState.showSnackbar(context.getString(R.string.post_published_local))
                                        }
                                    }
                                }
                            , baseColor = GrassGreen
                            , colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                disabledContentColor = Color.White.copy(alpha = 0.55f)
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) { Text(stringResource(R.string.publish), fontWeight = FontWeight.SemiBold) }
                        }
                    }
                }
            }
            item {
                Column {
                    Button(
                        onClick = { showFilters = !showFilters },
                        modifier = Modifier.fillMaxWidth()
                        , baseColor = GrassGreen
                        , colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.search_by_sport_and_commune))
                    }
                    if (showFilters) {
                        Spacer(Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Filtro por deporte
                            Box(modifier = Modifier.weight(1f)) {
                                val currentFilterSport = filterSport ?: stringResource(R.string.all)
                                OutlinedTextField(
                                    value = currentFilterSport,
                                    onValueChange = { },
                                    label = { Text(stringResource(R.string.sport_label), color = MaterialTheme.colorScheme.onSurface) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { filterSportExpanded = true },
                                    readOnly = true,
                                    trailingIcon = {
                                        IconButton(onClick = { filterSportExpanded = !filterSportExpanded }) {
                                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                        }
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        cursorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                DropdownMenu(
                                    expanded = filterSportExpanded,
                                    onDismissRequest = { filterSportExpanded = false },
                                    containerColor = MaterialTheme.colorScheme.surface
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.all), color = MaterialTheme.colorScheme.onSurface) },
                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                        onClick = {
                                            filterSport = null
                                            sport = ""
                                            filterSportExpanded = false
                                        }
                                    )
                                    sports.forEach { s ->
                                        DropdownMenuItem(
                                            text = { Text(s, color = MaterialTheme.colorScheme.onSurface) },
                                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                            onClick = {
                                                filterSport = s
                                                sport = s
                                                filterSportExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                            // Filtro por comuna
                            Box(modifier = Modifier.weight(1f)) {
                                val filterLocText = if (filterLocalitySearch.isNotEmpty()) filterLocalitySearch else (filterLocality ?: "")
                                OutlinedTextField(
                                    value = filterLocText,
                                    onValueChange = { text ->
                                        filterLocalitySearch = text
                                        filterLocality = text
                                        filterLocalityExpanded = true
                                    },
                                    label = { Text(stringResource(R.string.locality_label), color = MaterialTheme.colorScheme.onSurface) },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            // Al volver a abrir el menú, limpiamos la búsqueda para permitir una nueva selección desde cero
                                            if (!filterLocalityExpanded) {
                                                filterLocalitySearch = ""
                                            }
                                            filterLocalityExpanded = !filterLocalityExpanded
                                        }) {
                                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                        }
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        cursorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                DropdownMenu(
                                    expanded = filterLocalityExpanded,
                                    onDismissRequest = { filterLocalityExpanded = false },
                                    containerColor = MaterialTheme.colorScheme.surface
                                ) {
                                    if (filterLocalitySearch.isBlank()) {
                                        regionsWithCommunes.forEach { (region, communes) ->
                                            DropdownMenuItem(
                                                text = { Text(region, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold) },
                                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                onClick = {
                                                    filterExpandedRegions = if (filterExpandedRegions.contains(region)) {
                                                        filterExpandedRegions - region
                                                    } else {
                                                        filterExpandedRegions + region
                                                    }
                                                }
                                            )
                                            if (filterExpandedRegions.contains(region)) {
                                                communes.forEach { comuna ->
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = comuna,
                                                                color = MaterialTheme.colorScheme.onSurface,
                                                                modifier = Modifier.padding(start = 16.dp)
                                                            )
                                                        },
                                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                        onClick = {
                                                            val full = "$region - $comuna"
                                                            filterLocality = full
                                                            filterLocalitySearch = full
                                                            locality = full
                                                            localitySearch = full
                                                            filterLocalityExpanded = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        val filtered = allLocalities.filter { it.contains(filterLocalitySearch, ignoreCase = true) }
                                        filtered.forEach { loc ->
                                            DropdownMenuItem(
                                                text = { Text(loc, color = MaterialTheme.colorScheme.onSurface) },
                                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                onClick = {
                                                    filterLocality = loc
                                                    filterLocalitySearch = loc
                                                    locality = loc
                                                    localitySearch = loc
                                                    filterLocalityExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.posts_list_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            if (visiblePosts.isEmpty()) {
                item { Text(stringResource(R.string.empty_posts), color = MaterialTheme.colorScheme.onSurface) }
            } else {
                items(visiblePosts) { p ->
                    val isOwner = p.user.isNotBlank() && user.isNotBlank() &&
                        p.user.trim().equals(user.trim(), ignoreCase = true)
                    val postTime = remember(p.time) {
                        try {
                            SimpleDateFormat("dd/MM HH:mm").format(Date(p.time))
                        } catch (_: Exception) { "" }
                    }
                    val unreadCount = p.serverId?.let { unreadByPost[it] ?: 0 } ?: 0
                    Card(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = postTextColor
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                         modifier = Modifier
                             .fillMaxWidth()
                             .clickable {
                                // Usar la misma lógica que la UI (ignoreCase/trim) para detectar posts propios.
                                // Si falla, podríamos intentar abrir un chat 1:1 "conmigo mismo" o con datos incompletos.
                                if (isOwner) {
                                    if (!p.serverId.isNullOrBlank()) {
                                        threadsForPost = p
                                        unreadByPost.remove(p.serverId)
                                        clearUnreadForPost(context, p.serverId)
                                    } else {
                                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_required)) }
                                    }
                                } else {
                                    if (!p.userId.isNullOrBlank() && !p.serverId.isNullOrBlank()) {
                                        chatRecipientName = p.user
                                        chatRecipientId = p.userId
                                        chatPostId = p.serverId
                                        cancelChatNotification(context, p.serverId, p.user, false)
                                        unreadByPost.remove(p.serverId)
                                        clearUnreadForPost(context, p.serverId)
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(context.getString(R.string.err_post_required))
                                        }
                                    }
                                }
                             }
                     ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_tshirt),
                                        contentDescription = null,
                                        tint = postTextColor
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            stringResource(
                                                R.string.post_format_title,
                                                p.user.ifBlank { "" },
                                                p.sport,
                                                p.available,
                                                p.total
                                            ),
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        if (postTime.isNotEmpty()) {
                                            Spacer(Modifier.height(2.dp))
                                            Text(
                                                postTime,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = postSecondaryTextColor
                                            )
                                        }
                                    }
                                }
                                if (isOwner) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedButton(
                                            onClick = {
                                                // chat general para el post
                                                chatRecipientName = null
                                                chatRecipientId = null
                                                chatPostId = p.serverId
                                                groupPostId = null
                                            }
                                            , baseColor = GrassGreen
                                            , colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                                            , border = null
                                        ) {
                                            Text(stringResource(R.string.general_chat))
                                        }
                                        OutlinedButton(
                                            onClick = {
                                                if (!p.serverId.isNullOrBlank()) {
                                                    groupPostId = p.serverId
                                                    cancelChatNotification(context, p.serverId, null, true)
                                                    unreadByPost.remove(p.serverId)
                                                    clearUnreadForPost(context, p.serverId)
                                                } else {
                                                    scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_required)) }
                                                }
                                            }
                                            , baseColor = GrassGreen
                                            , colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                                            , border = null
                                        ) {
                                            Text(stringResource(R.string.general_chat))
                                        }
                                        IconButton(onClick = {
                                            if (!p.serverId.isNullOrBlank()) {
                                                threadsForPost = p
                                            } else {
                                                scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_required)) }
                                            }
                                        }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.Message,
                                                contentDescription = stringResource(R.string.chat)
                                            )
                                        }
                                        IconButton(onClick = {
                                            val togglingTo = editingId != p.id
                                            editingId = if (togglingTo) p.id else null
                                            if (togglingTo) {
                                                editingMessage = p.message
                                                editingSport = p.sport
                                                editingAvailable = p.available
                                                editingTotal = p.total
                                                editingLocality = p.locality
                                            } else {
                                                editingMessage = ""
                                                editingLocality = ""
                                                editingSport = context.getString(R.string.sport_futbolito)
                                                editingAvailable = 0
                                                editingTotal = 0
                                            }
                                        }) {
                                            Icon(Icons.Filled.Edit, contentDescription = null)
                                        }
                                    }
                                } else {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedButton(
                                            onClick = {
                                            if (!p.userId.isNullOrBlank() && !p.serverId.isNullOrBlank()) {
                                                chatRecipientName = p.user
                                                chatRecipientId = p.userId
                                                chatPostId = p.serverId
                                                cancelChatNotification(context, p.serverId, p.user, false)
                                                unreadByPost.remove(p.serverId)
                                                clearUnreadForPost(context, p.serverId)
                                            } else {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(context.getString(R.string.err_post_required))
                                                }
                                            }
                                        },
                                            baseColor = GrassGreen,
                                            colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                                            border = null
                                        ) {
                                            Text(stringResource(R.string.chat))
                                        }
                                        IconButton(onClick = {
                                            if (!p.userId.isNullOrBlank() && !p.serverId.isNullOrBlank()) {
                                                chatRecipientName = p.user
                                                chatRecipientId = p.userId
                                                chatPostId = p.serverId
                                                cancelChatNotification(context, p.serverId, p.user, false)
                                                unreadByPost.remove(p.serverId)
                                                clearUnreadForPost(context, p.serverId)
                                            } else {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(context.getString(R.string.err_post_required))
                                                }
                                            }
                                        }) {
                                            Icon(Icons.AutoMirrored.Filled.Message, contentDescription = stringResource(R.string.chat))
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (unreadCount > 0) {
                                    Text(
                                        if (unreadCount == 1) "1 mensaje nuevo" else "$unreadCount mensajes nuevos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                } else {
                                    Spacer(Modifier.width(1.dp))
                                }
                                FilledIconButton(
                                    onClick = { deletingPost = p },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = stringResource(R.string.delete)
                                    )
                                }
                            }
                            if (editingId == p.id) {
                                Spacer(Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        OutlinedTextField(
                                            value = editingSport,
                                            onValueChange = { },
                                            label = { Text(stringResource(R.string.sport_label), color = MaterialTheme.colorScheme.onSurfaceVariant) },
                                            modifier = Modifier.fillMaxWidth().clickable { editSportExpanded = true },
                                            readOnly = true,
                                            trailingIcon = {
                                                IconButton(onClick = { editSportExpanded = !editSportExpanded }) {
                                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                                }
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                                focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                                            )
                                        )
                                        DropdownMenu(
                                            expanded = editSportExpanded,
                                            onDismissRequest = { editSportExpanded = false },
                                            containerColor = MaterialTheme.colorScheme.surface
                                        ) {
                                            sports.forEach { s ->
                                                DropdownMenuItem(
                                                    text = { Text(s, color = MaterialTheme.colorScheme.onSurface) },
                                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                    onClick = {
                                                        editingSport = s
                                                        editSportExpanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                    Box(modifier = Modifier.weight(1f)) {
                                        val editingLocalityText = if (editingLocalitySearch.isNotEmpty()) editingLocalitySearch else editingLocality
                                        OutlinedTextField(
                                            value = editingLocalityText,
                                            onValueChange = { text ->
                                                editingLocalitySearch = text
                                                editingLocality = text
                                                editingLocalityExpanded = true
                                            },
                                            label = { Text(stringResource(R.string.locality_label)) },
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true,
                                            trailingIcon = {
                                                IconButton(onClick = { editingLocalityExpanded = !editingLocalityExpanded }) {
                                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                                }
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                                focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                        DropdownMenu(
                                            expanded = editingLocalityExpanded,
                                            onDismissRequest = { editingLocalityExpanded = false },
                                            containerColor = MaterialTheme.colorScheme.surface
                                        ) {
                                            if (editingLocalitySearch.isBlank()) {
                                                regionsWithCommunes.forEach { (region, communes) ->
                                                    DropdownMenuItem(
                                                        text = { Text(region, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold) },
                                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                        onClick = {
                                                            expandedRegions = if (expandedRegions.contains(region)) {
                                                                expandedRegions - region
                                                            } else {
                                                                expandedRegions + region
                                                            }
                                                        }
                                                    )
                                                    if (expandedRegions.contains(region)) {
                                                        communes.forEach { comuna ->
                                                            DropdownMenuItem(
                                                                text = {
                                                                    Text(
                                                                        text = comuna,
                                                                        color = MaterialTheme.colorScheme.onSurface,
                                                                        modifier = Modifier.padding(start = 16.dp)
                                                                    )
                                                                },
                                                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                                onClick = {
                                                                    val full = "$region - $comuna"
                                                                    editingLocality = full
                                                                    editingLocalitySearch = full
                                                                    editingLocalityExpanded = false
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                            } else {
                                                val filtered = allLocalities.filter { it.contains(editingLocalitySearch, ignoreCase = true) }
                                                filtered.forEach { loc ->
                                                    DropdownMenuItem(
                                                        text = { Text(loc, color = MaterialTheme.colorScheme.onSurface) },
                                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                        onClick = {
                                                            editingLocality = loc
                                                            editingLocalitySearch = loc
                                                            editingLocalityExpanded = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = editingAvailable.toString(),
                                        onValueChange = { v -> editingAvailable = v.filter { it.isDigit() }.toIntOrNull() ?: 0 },
                                        label = { Text(stringResource(R.string.available_players)) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            cursorColor = MaterialTheme.colorScheme.onSurface,
                                            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                    OutlinedTextField(
                                        value = editingTotal.toString(),
                                        onValueChange = { v -> editingTotal = v.filter { it.isDigit() }.toIntOrNull() ?: 0 },
                                        label = { Text(stringResource(R.string.total_players)) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            cursorColor = MaterialTheme.colorScheme.onSurface,
                                            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                                OutlinedTextField(
                                    value = editingMessage,
                                    onValueChange = { editingMessage = it },
                                    label = { Text(stringResource(R.string.post_message_label)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = false,
                                    minLines = 2,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        cursorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                Spacer(Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedButton(onClick = {
                                        editingId = null
                                        editingMessage = ""
                                        editingLocality = ""
                                        editingSport = context.getString(R.string.sport_futbolito)
                                        editingAvailable = 0
                                        editingTotal = 0
                                    }) { Text(stringResource(android.R.string.cancel)) }
                                    Button(onClick = {
                                        val idLocal = editingId ?: return@Button
                                        if (editingMessage.isBlank() || editingLocality.isBlank() || editingAvailable <= 0 || editingTotal <= 0) {
                                            scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_required)) }
                                            return@Button
                                        }
                                        if (editingAvailable > editingTotal) {
                                            scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.err_post_counts)) }
                                            return@Button
                                        }
                                        val trimmed = editingMessage.trim()
                                        val current = posts.find { it.id == idLocal }
                                        val sid = current?.serverId
                                        scope.launch {
                                            if (!sid.isNullOrBlank()) {
                                                val (at, rt) = loadTokens(context)
                                                val body = JSONObject()
                                                    .put("userName", current?.user ?: "")
                                                    .put("sport", editingSport.ifBlank { context.getString(R.string.sport_futbolito) })
                                                    .put("available", editingAvailable)
                                                    .put("total", editingTotal)
                                                    .put("message", trimmed)
                                                    .put("locality", editingLocality.trim())
                                                val token = withContext(Dispatchers.IO) { updateCommunityPostRemote(context, at, rt, sid, body) }
                                                if (token != null) {
                                                    val remote = withContext(Dispatchers.IO) { fetchCommunityPostsRemote(context, token, rt).first }
                                                    if (remote != null) posts = remote
                                                    snackbarHostState.showSnackbar(context.getString(R.string.post_updated_remote))
                                                }
                                            } else {
                                                val updated = posts.map { post ->
                                                    if (post.id == idLocal) post.copy(
                                                        message = trimmed,
                                                        sport = editingSport.ifBlank { context.getString(R.string.sport_futbolito) },
                                                        available = editingAvailable,
                                                        total = editingTotal,
                                                        locality = editingLocality.trim()
                                                    ) else post
                                                }
                                                saveCommunityPosts(context, updated)
                                                posts = updated
                                                snackbarHostState.showSnackbar(context.getString(R.string.post_updated_local))
                                            }
                                            editingId = null
                                            editingMessage = ""
                                            editingLocality = ""
                                            editingSport = context.getString(R.string.sport_futbolito)
                                            editingAvailable = 0
                                            editingTotal = 0
                                        }
                                    }, enabled = editingMessage.isNotBlank() && editingLocality.isNotBlank() && editingAvailable > 0 && editingTotal > 0 && editingAvailable <= editingTotal) { Text(stringResource(android.R.string.ok)) }
                                }
                            } else {
                                if (p.message.isNotBlank()) {
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        p.message,
                                        color = postTextColor,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Place,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = postTextColor
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            p.locality,
                                            color = postTextColor,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (postTime.isNotEmpty()) {
                                        Text(
                                            postTime,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = postSecondaryTextColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    val recipient = chatRecipientName
    val toDelete = deletingPost
    val tfp = threadsForPost
    val groupPid = groupPostId
    if (toDelete != null) {
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        AlertDialog(
            onDismissRequest = { deletingPost = null },
            title = { Text(stringResource(R.string.delete), color = dialogTextColor) },
            text = { Text(stringResource(R.string.delete_post_confirm), color = dialogTextColor) },
            confirmButton = {
                Button(onClick = {
                    val post = toDelete ?: run {
                        deletingPost = null
                        return@Button
                    }
                    val sid = post.serverId
                    if (!sid.isNullOrBlank()) {
                        scope.launch {
                            val (at, rt) = loadTokens(context)
                            val token = withContext(Dispatchers.IO) { deleteCommunityPostRemote(context, at, rt, sid) }
                            if (token != null) {
                                val remote = withContext(Dispatchers.IO) { fetchCommunityPostsRemote(context, token, rt).first }
                                if (remote != null) posts = remote
                                snackbarHostState.showSnackbar(context.getString(R.string.post_deleted_remote))
                            } else {
                                val updated = posts.filterNot { it.id == post.id }
                                saveCommunityPosts(context, updated)
                                posts = updated
                                snackbarHostState.showSnackbar(context.getString(R.string.post_deleted_local))
                            }
                            deletingPost = null
                        }
                    } else {
                        val updated = posts.filterNot { it.id == post.id }
                        saveCommunityPosts(context, updated)
                        posts = updated
                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.post_deleted_local)) }
                        deletingPost = null
                    }
                }) { Text(stringResource(android.R.string.ok)) }
            },
            dismissButton = { TextButton(onClick = { deletingPost = null }) { Text(stringResource(android.R.string.cancel), color = dialogTextColor) } },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor
        )
    }
    if (tfp != null) {
        LaunchedEffect(tfp.serverId) {
            val sid = tfp.serverId
            if (!sid.isNullOrBlank()) {
                threadsLoading = true
                val (at, rt) = loadTokens(context)
                if (!at.isNullOrBlank()) {
                    val pair = withContext(Dispatchers.IO) { fetchThreadsRemote(context, at, rt, sid) }
                    val list = pair.first
                    threads = list ?: emptyList()
                } else {
                    threads = emptyList()
                }
                threadsLoading = false
            }
        }
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

        AlertDialog(
            onDismissRequest = {
                threadsForPost = null
                threads = emptyList()
            },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.chat), color = dialogTextColor) },
            text = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 420.dp)
                ) {
                    if (threadsLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.loading_conversations), color = dialogTextColor)
                        }
                    } else if (threads.isEmpty()) {
                        Text(stringResource(R.string.no_messages_for_post), color = dialogSecondaryTextColor)
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(threads) { th ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            chatRecipientName = th.userName
                                            chatRecipientId = th.userId
                                            chatPostId = tfp.serverId
                                            cancelChatNotification(context, tfp.serverId, th.userName, false)
                                            val key = tfp.serverId
                                            if (!key.isNullOrBlank()) {
                                                unreadByPost.remove(key)
                                                clearUnreadForPost(context, key)
                                            }
                                            threadsForPost = null
                                        }
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(Modifier.weight(1f)) {
                                        Text(th.userName, fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                        if (th.lastText.isNotBlank()) {
                                            Spacer(Modifier.height(2.dp))
                                            Text(th.lastText, maxLines = 1, overflow = TextOverflow.Ellipsis, color = dialogSecondaryTextColor)
                                        }
                                    }
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    threadsForPost = null
                    threads = emptyList()
                }) { Text(stringResource(R.string.close), color = dialogTextColor) }
            }
        )
    }
    if (recipient != null) {
        LaunchedEffect(recipient, chatRecipientId) {
            val toId = chatRecipientId
            val pid = chatPostId
            if (!toId.isNullOrBlank() && !pid.isNullOrBlank()) {
                val key = "$pid|$recipient"
                chatLoading = true
                val (at, rt) = loadTokens(context)
                if (!at.isNullOrBlank()) {
                    val pair = withContext(Dispatchers.IO) { fetchMessagesRemote(context, at, rt, toId, pid) }
                    val list = pair.first
                    if (list != null) chats[key] = list.takeLast(CHAT_MAX_MESSAGES)
                }
                chatLoading = false
            }
        }
        // Fallback: si el WebSocket no está conectado, hacer polling periódico del historial
        LaunchedEffect(ws, recipient, chatRecipientId, chatPostId) {
            val toId = chatRecipientId
            val pid = chatPostId
            if (recipient == null || toId.isNullOrBlank() || pid.isNullOrBlank()) return@LaunchedEffect
            val key = "$pid|$recipient"
            while (true) {
                if (ws == null) {
                    val (at, rt) = loadTokens(context)
                    if (!at.isNullOrBlank()) {
                        val pair = withContext(Dispatchers.IO) { fetchMessagesRemote(context, at, rt, toId, pid) }
                        val list = pair.first
                        if (list != null) chats[key] = list.takeLast(CHAT_MAX_MESSAGES)
                    }
                }
                delay(3000)
            }
        }
        ChatDialog(
            recipient = recipient,
            loading = chatLoading,
            onSend = { text ->
                val toId = chatRecipientId
                val pid = chatPostId
                if (toId.isNullOrBlank() || pid.isNullOrBlank()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.err_post_required))
                    }
                    return@ChatDialog
                }
                val wsNow = ws
                val payload = JSONObject()
                    .put("type", "message_send")
                    .put("toUserId", toId)
                    .put("toName", recipient)
                    .put("fromName", user)
                    .put("text", text)
                    .put("time", System.currentTimeMillis())
                    .put("postId", pid)
                var sent = false
                if (wsNow != null) {
                    try { sent = wsNow.send(payload.toString()) } catch (_: Exception) { sent = false }
                }
                // Optimistic update
                val key = "$pid|$recipient"
                val existing = chats[key] ?: emptyList()
                chats[key] = (existing + ChatMessage(from = user, to = recipient, text = text, time = System.currentTimeMillis()))
                    .takeLast(CHAT_MAX_MESSAGES)
                if (!sent) {
                    // Fallback a REST /messages
                    scope.launch {
                        val (at, rt) = loadTokens(context)
                        val body = JSONObject()
                            .put("toUserId", toId)
                            .put("toName", recipient)
                            .put("fromName", user)
                            .put("text", text)
                            .put("time", System.currentTimeMillis())
                            .put("postId", pid)
                        withContext(Dispatchers.IO) { sendMessageRemote(context, at, rt, body) }
                    }
                }
            },
            messages = run {
                val pid = chatPostId
                if (!pid.isNullOrBlank()) chats["$pid|$recipient"] ?: emptyList() else emptyList()
            },
            onDismiss = {
                val pid = chatPostId
                if (!pid.isNullOrBlank()) chats.remove("$pid|$recipient")
                chatRecipientName = null
                chatRecipientId = null
                chatPostId = null
            }
        )
    }
}

private val userNameState = mutableStateOf<String?>(null)

// WebSocket global para recibir notificaciones de chat aunque la app esté fuera de Comunidad
@Volatile
private var globalChatWebSocket: WebSocket? = null
@Volatile
private var isCommunityForeground: Boolean = false
@Volatile
private var isAppInForeground: Boolean = false

private fun ensureChatChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val existing = manager.getNotificationChannel(CHAT_CHANNEL_ID)
        if (existing == null) {
            val channel = NotificationChannel(
                CHAT_CHANNEL_ID,
                "Mensajes de chat",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
    }
}

private fun showChatNotification(
    context: Context,
    title: String,
    text: String,
    postId: String? = null,
    peerName: String? = null,
    isGroup: Boolean = false
) {
    ensureChatChannel(context)
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        if (!postId.isNullOrBlank()) {
            putExtra(EXTRA_CHAT_POST_ID, postId)
        }
        if (!peerName.isNullOrBlank()) {
            putExtra(EXTRA_CHAT_PEER_NAME, peerName)
        }
        putExtra(EXTRA_CHAT_IS_GROUP, isGroup)
    }
    val pendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(intent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
    val id = run {
        val key = buildString {
            append("chat|")
            append(postId ?: "")
            append("|")
            append(peerName ?: "")
            append("|")
            append(if (isGroup) "1" else "0")
        }
        key.hashCode()
    }
    val notification = NotificationCompat.Builder(context, CHAT_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_tshirt)
        .setContentTitle(title)
        .setContentText(text)
        .setStyle(NotificationCompat.BigTextStyle().bigText(text))
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .build()
    try {
        with(NotificationManagerCompat.from(context)) {
            notify(id, notification)
        }
    } catch (_: SecurityException) {
        // El permiso de notificaciones puede haber sido rechazado en tiempo de ejecución
    }
}

private fun cancelChatNotification(
    context: Context,
    postId: String?,
    peerName: String?,
    isGroup: Boolean
) {
    val id = run {
        val key = buildString {
            append("chat|")
            append(postId ?: "")
            append("|")
            append(peerName ?: "")
            append("|")
            append(if (isGroup) "1" else "0")
        }
        key.hashCode()
    }
    try {
        NotificationManagerCompat.from(context).cancel(id)
    } catch (_: Exception) {
    }
}

private fun startGlobalChatListener(context: Context) {
    if (globalChatWebSocket != null) return
    val (access, _) = loadTokens(context)
    if (access.isNullOrBlank()) return
    val wsUrl = BASE_URL.replaceFirst("https", "wss").replaceFirst("http", "ws") + "/ws"
    val req = Request.Builder().url(wsUrl).header("Authorization", "Bearer $access").build()
    val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            globalChatWebSocket = webSocket
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val root = Json.parseToJsonElement(text).jsonObject
                val type = root["type"]?.jsonPrimitive?.contentOrNull
                val data = root["data"]
                val currentUser = userNameState.value
                if (type == "message_new") {
                    val o = data?.jsonObject ?: return
                    val fromName = o["fromName"]?.jsonPrimitive?.contentOrNull ?: ""
                    val toName = o["toName"]?.jsonPrimitive?.contentOrNull ?: ""
                    val textMsg = o["text"]?.jsonPrimitive?.contentOrNull ?: ""
                    val pid = o["postId"]?.jsonPrimitive?.contentOrNull
                    if (currentUser != null && fromName.equals(currentUser, ignoreCase = true)) return
                    val partner = if (currentUser != null && fromName.equals(currentUser, ignoreCase = true)) toName else fromName
                    val title = if (!partner.isNullOrBlank()) "Nuevo mensaje de $partner" else "Nuevo mensaje"
                    // Evitar notificación sólo si la pantalla de Comunidad está visible y la app en primer plano
                    val shouldShowNotification = !(isCommunityForeground && isAppInForeground)
                    if (shouldShowNotification) {
                        showChatNotification(
                            context,
                            title,
                            textMsg,
                            postId = pid,
                            peerName = partner,
                            isGroup = false
                        )
                    }
                    if (!pid.isNullOrBlank()) {
                        incrementUnreadForPost(context, pid)
                    }
                } else if (type == "post_message_new") {
                    val o = data?.jsonObject ?: return
                    val fromName = o["fromName"]?.jsonPrimitive?.contentOrNull ?: ""
                    val textMsg = o["text"]?.jsonPrimitive?.contentOrNull ?: ""
                    val pid = o["postId"]?.jsonPrimitive?.contentOrNull
                    if (currentUser != null && fromName.equals(currentUser, ignoreCase = true)) return
                    val title = "Nuevo mensaje en comunidad"
                    val body = if (fromName.isNotBlank()) "$fromName: $textMsg" else textMsg
                    // Evitar notificación sólo si la pantalla de Comunidad está visible y la app en primer plano
                    val shouldShowNotification = !(isCommunityForeground && isAppInForeground)
                    if (shouldShowNotification) {
                        showChatNotification(
                            context,
                            title,
                            body,
                            postId = pid,
                            peerName = null,
                            isGroup = true
                        )
                    }
                    if (!pid.isNullOrBlank()) {
                        incrementUnreadForPost(context, pid)
                    }
                }
            } catch (_: Exception) {}
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            if (globalChatWebSocket === webSocket) globalChatWebSocket = null
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            if (globalChatWebSocket === webSocket) globalChatWebSocket = null
        }
    }
    try {
        httpClient.newWebSocket(req, listener)
    } catch (_: Exception) {}
}

private fun stopGlobalChatListener() {
    try {
        globalChatWebSocket?.close(1000, "logout")
    } catch (_: Exception) {}
    globalChatWebSocket = null
}

// ===== Network & Auth helpers (top-level) =====
private fun httpPostRaw(path: String, json: String, token: String? = null): Pair<Int, String?> {
    val req = Request.Builder()
        .url("$BASE_URL$path")
        .post(json.toRequestBody(JSON_MEDIA))
        .apply { if (!token.isNullOrBlank()) header("Authorization", "Bearer $token") }
        .build()
    httpClient.newCall(req).execute().use { resp ->
        val text = resp.body?.string()
        return resp.code to text
    }
}

private suspend fun postJsonWithRetry(path: String, body: JSONObject): Pair<Int, String?> {
    var attempt = 0
    var last: Pair<Int, String?> = 0 to null
    while (attempt < 2) {
        last = try {
            withContext(Dispatchers.IO) { httpPostJson(path, body) }
        } catch (_: Exception) { 0 to null }
        val (code, text) = last
        val trimmed = text?.trimStart()
        val looksHtml = trimmed?.startsWith("<!DOCTYPE", ignoreCase = true) == true || trimmed?.startsWith("<html", ignoreCase = true) == true
        if (code in 200..299 && !looksHtml) return last
        if (code in 500..599 || looksHtml) {
            delay(800)
            attempt++
            continue
        }
        return last
    }
    return last
}

private fun httpPostJson(path: String, body: JSONObject, token: String? = null): Pair<Int, String?> {
    val req = Request.Builder()
        .url("$BASE_URL$path")
        .post(body.toString().toRequestBody(JSON_MEDIA))
        .apply { if (!token.isNullOrBlank()) header("Authorization", "Bearer $token") }
        .build()
    httpClient.newCall(req).execute().use { resp ->
        val text = resp.body?.string()
        return resp.code to text
    }
}

private fun httpGet(path: String, token: String): Pair<Int, String?> {
    val req = Request.Builder().url("$BASE_URL$path").get().header("Authorization", "Bearer $token").build()
    httpClient.newCall(req).execute().use { resp ->
        val text = resp.body?.string()
        return resp.code to text
    }
}

private fun saveTokens(context: Context, access: String?, refresh: String?) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().apply {
        if (access == null) remove(KEY_ACCESS_TOKEN) else putString(KEY_ACCESS_TOKEN, access)
        if (refresh == null) remove(KEY_REFRESH_TOKEN) else putString(KEY_REFRESH_TOKEN, refresh)
    }.apply()
}

private fun loadTokens(context: Context): Pair<String?, String?> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(KEY_ACCESS_TOKEN, null) to prefs.getString(KEY_REFRESH_TOKEN, null)
}

private fun saveUserName(context: Context, name: String?) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().apply {
        if (name == null) remove(KEY_USER_NAME) else putString(KEY_USER_NAME, name)
    }.apply()
}

private fun loadUserName(context: Context): String? {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(KEY_USER_NAME, null)
}

 private fun tryRefresh(context: Context, refreshToken: String?): String? {
    if (refreshToken.isNullOrBlank()) return null
    return try {
        val body = JSONObject().put("refreshToken", refreshToken)
        val (code, text) = httpPostJson("/auth/refresh", body)
        if (code in 200..299 && !text.isNullOrBlank()) {
            val obj = JSONObject(text)
            val at = obj.optString("accessToken").takeIf { it.isNotBlank() }
            if (at != null) {
                saveTokens(context, at, refreshToken)
                at
            } else null
        } else null
    } catch (_: Exception) { null }
}

private fun fetchPlayersRemote(context: Context, access: String?, refresh: String?): Pair<List<Player>?, String?> {
    if (access.isNullOrBlank()) return null to access
    var token = access
    val (code, text) = try {
        httpGet("/players", token)
    } catch (_: Exception) {
        return null to token
    }
    val trimmed = text?.trimStart()
    val looksHtml = trimmed?.startsWith("<!DOCTYPE", ignoreCase = true) == true ||
            trimmed?.startsWith("<html", ignoreCase = true) == true
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val (code2, text2) = try {
                httpGet("/players", token)
            } catch (_: Exception) {
                return null to token
            }
            val trimmed2 = text2?.trimStart()
            val looksHtml2 = trimmed2?.startsWith("<!DOCTYPE", ignoreCase = true) == true ||
                    trimmed2?.startsWith("<html", ignoreCase = true) == true
            if (code2 in 200..299 && !text2.isNullOrBlank() && !looksHtml2) {
                return try {
                    val arr = JSONArray(text2)
                    val list = mutableListOf<Player>()
                    for (i in 0 until arr.length()) {
                        val o = arr.getJSONObject(i)
                        list += Player(
                            o.getString("name"),
                            o.getDouble("attack"),
                            o.getDouble("defense"),
                            o.getDouble("physical"),
                            o.optBoolean("isGoalkeeper", false)
                        )
                    }
                    list to token
                } catch (_: Exception) {
                    null to token
                }
            }
        }
        return null to token
    }
    if (code in 200..299 && !text.isNullOrBlank() && !looksHtml) {
        return try {
            val arr = JSONArray(text)
            val list = mutableListOf<Player>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                list += Player(
                    o.getString("name"),
                    o.getDouble("attack"),
                    o.getDouble("defense"),
                    o.getDouble("physical"),
                    o.optBoolean("isGoalkeeper", false)
                )
            }
            list to token
        } catch (_: Exception) {
            null to token
        }
    }
    return null to token
}

private fun postPlayersBulkRemote(context: Context, access: String?, refresh: String?, players: List<Player>): String? {
    if (access.isNullOrBlank()) return null
    val arr = JSONArray(players.map { JSONObject().put("name", it.name).put("attack", it.attack).put("defense", it.defense).put("physical", it.physical).put("isGoalkeeper", it.isGoalkeeper) })
    var token = access
    var (code, _) = httpPostJson("/players/bulk", JSONObject().put("players", arr), token)
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val res2 = httpPostJson("/players/bulk", JSONObject().put("players", arr), token)
            code = res2.first
        }
    }
    return if (code in 200..299) token else null
}

private fun postMatchRemote(context: Context, access: String?, refresh: String?, titleA: String, titleB: String, teamA: List<Player>, teamB: List<Player>): String? {
    if (access.isNullOrBlank()) return null
    val body = JSONObject()
        .put("time", System.currentTimeMillis())
        .put("titleA", titleA)
        .put("titleB", titleB)
        .put("teamA", JSONArray(teamA.map {
            JSONObject()
                .put("name", it.name)
                .put("isGoalkeeper", it.isGoalkeeper)
                .put("isCaptain", it.isCaptain)
                .put("hasYellowCard", it.hasYellowCard)
                .put("hasRedCard", it.hasRedCard)
        }))
        .put("teamB", JSONArray(teamB.map {
            JSONObject()
                .put("name", it.name)
                .put("isGoalkeeper", it.isGoalkeeper)
                .put("isCaptain", it.isCaptain)
                .put("hasYellowCard", it.hasYellowCard)
                .put("hasRedCard", it.hasRedCard)
        }))
        .put("result", "")
    var token = access
    var (code, _) = try { httpPostJson("/matches", body, token) } catch (_: Exception) { -1 to null }
    if (code == 401) {
        val newAt = tryRefresh(context, refresh)
        if (newAt != null) {
            token = newAt
            val res2 = try { httpPostJson("/matches", body, token) } catch (_: Exception) { -1 to null }
            code = res2.first
        }
    }
    return if (code in 200..299) token else null
}

class MainActivity : AppCompatActivity() {
    // Registro para abrir el menú secreto desde eventos de hardware
    private var openSecretMenu: (() -> Unit)? = null
    private var lastVolUpTime: Long = -1L
    private var lastVolDownTime: Long = -1L
    private var pendingChatPostId: String? = null
    private var pendingChatPeerName: String? = null
    private var pendingChatIsGroup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleChatIntent(intent)
        val currentTags = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        if (currentTags.isEmpty()) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("es"))
        }
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val initialDarkTheme = prefs.getBoolean(KEY_DARK_MODE, false)
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(initialDarkTheme) }
            EquiposTheme(darkTheme = isDarkTheme) {
                AppScaffold(
                    registerSecretOpener = { opener -> openSecretMenu = opener },
                    pendingChatPostId = pendingChatPostId,
                    pendingChatPeerName = pendingChatPeerName,
                    pendingChatIsGroup = pendingChatIsGroup,
                    clearPendingChat = {
                        pendingChatPostId = null
                        pendingChatPeerName = null
                        pendingChatIsGroup = false
                    },
                    isDarkTheme = isDarkTheme,
                    onToggleDarkTheme = { enabled ->
                        isDarkTheme = enabled
                        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
                    }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleChatIntent(intent)
    }

    private fun handleChatIntent(intent: Intent) {
        pendingChatPostId = intent.getStringExtra(EXTRA_CHAT_POST_ID)
        pendingChatPeerName = intent.getStringExtra(EXTRA_CHAT_PEER_NAME)
        pendingChatIsGroup = intent.getBooleanExtra(EXTRA_CHAT_IS_GROUP, false)
    }

    override fun onStart() {
        super.onStart()
        isAppInForeground = true
    }

    override fun onStop() {
        super.onStop()
        isAppInForeground = false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val now = SystemClock.uptimeMillis()
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                lastVolUpTime = now
                if (lastVolDownTime > 0 && (now - lastVolDownTime) <= 300) {
                    openSecretMenu?.invoke()
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                lastVolDownTime = now
                if (lastVolUpTime > 0 && (now - lastVolUpTime) <= 300) {
                    openSecretMenu?.invoke()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

private fun List<Player>.avgRating(): Double {
    if (isEmpty()) return 0.0
    return this.sumOf { it.rating } / this.size
}

private fun playerLine(p: Player): String {
    val gk = if (p.isGoalkeeper) "(GK) " else ""
    val captain = if (p.isCaptain) " (C)" else ""
    val yellow = if (p.hasYellowCard) " [TA]" else ""
    val red = if (p.hasRedCard) " [TR]" else ""
    return "• ${gk}${p.name}$captain$yellow$red"
}

private fun formatTeamBlock(context: Context, title: String, team: List<Player>): String {
    val header = "$title (${team.size})"
    val body = team.joinToString("\n") { playerLine(it) }
    return "$header\n$body"
}

private fun formatTeamsText(context: Context, titleA: String, titleB: String, teamA: List<Player>, teamB: List<Player>): String {
    val a = formatTeamBlock(context, titleA, teamA)
    val b = formatTeamBlock(context, titleB, teamB)
    return "$a\n\n$b"
}

private fun formatSavedMatchText(context: Context, m: SavedMatch): String {
    val a = formatTeamBlock(context, m.titleA, m.teamA)
    val b = formatTeamBlock(context, m.titleB, m.teamB)
    val resultLabel = context.getString(R.string.result_label)
    val result = if (m.result.isNotBlank()) "\n\n$resultLabel: ${m.result}" else ""
    return "$a\n\n$b$result"
}

private fun createPitchBitmap(
    context: Context,
    teamA: List<Player>,
    teamB: List<Player>,
    sport: String,
    teamAColor: Int = AndroidColor.parseColor("#FFEB3B"),
    teamBColor: Int = AndroidColor.parseColor("#03A9F4"),
    teamANationKey: String? = null,
    teamBNationKey: String? = null,
    width: Int = 800,
    height: Int = 1400
): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)

    val fieldPaint = Paint().apply {
        color = AndroidColor.parseColor("#43A047")
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    val linePaint = Paint().apply {
        color = AndroidColor.WHITE
        style = Paint.Style.STROKE
        strokeWidth = (kotlin.math.min(width, height) * 0.01f)
        isAntiAlias = true
    }
    val textPaint = Paint().apply {
        color = AndroidColor.WHITE
        textAlign = Paint.Align.CENTER
        textSize = width * 0.04f
        isAntiAlias = true
    }

    val isTennis = sport.equals("Tenis", ignoreCase = true)
    val isPadel = sport.equals("Pádel", ignoreCase = true) || sport.equals("Padel", ignoreCase = true)
    val isVolleyball = sport.equals("Voleybol", ignoreCase = true) || sport.equals("Vóleibol", ignoreCase = true)
    val isBabyFootball = sport.equals("Baby Fútbol", ignoreCase = true) || sport.equals("Baby Futbol", ignoreCase = true) || sport.equals("Baby futbol", ignoreCase = true) || sport.equals("Baby fútbol", ignoreCase = true)
    val isRugby = sport.equals("Rugby", ignoreCase = true)
    val isFootball = !isTennis && !isPadel && !isVolleyball && !isBabyFootball && !isRugby

    val padelCourtPaint = Paint().apply {
        // Zona azul de la cancha (#2EA4DA)
        color = AndroidColor.parseColor("#2EA4DA")
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    val padelWallPaint = Paint().apply {
        // Borde azul oscuro de la zona de juego (#082A40)
        color = AndroidColor.parseColor("#082A40")
        style = Paint.Style.STROKE
        strokeWidth = linePaint.strokeWidth * 1.5f
        isAntiAlias = true
    }
    val volleyCourtPaint = Paint().apply {
        color = AndroidColor.parseColor("#FF9800")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // Fondo de cancha (siempre ocupa todo el bitmap)
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), fieldPaint)

    // Escala más notoria para deportes de cancha: todo el diseño se dibuja más pequeño
    if (isFootball || isBabyFootball || isTennis || isVolleyball || isRugby) {
        canvas.save()
        canvas.scale(0.8f, 0.8f, width / 2f, height / 2f)
    }

    // Borde
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), linePaint)

    val centerY = height / 2f
    val boxHeight = height * 0.18f

    if (isTennis) {
        // Cancha de tenis basada en el nuevo SVG (800x1400), escalada al tamaño actual
        val scaleX = width / 36f
        val scaleY = height / 78f

        fun sx(x: Float) = x * scaleX
        fun sy(y: Float) = y * scaleY

        // Fondo verde específico de tenis (#66CC66) dentro del área escalada
        val tennisBgPaint = Paint().apply {
            color = AndroidColor.parseColor("#2F9E3A")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), tennisBgPaint)

        val baseStroke = linePaint.strokeWidth
        val mainStroke = baseStroke * 0.75f   // líneas internas ~6px si base≈8
        val outerStroke = baseStroke          // borde exterior ~8px

        val mainLinePaint = Paint(linePaint).apply { strokeWidth = mainStroke }
        val outerLinePaint = Paint(linePaint).apply { strokeWidth = outerStroke }
        val netLinePaint = Paint(linePaint).apply { strokeWidth = mainStroke * 1.2f }

        // Líneas verticales (x=4.5, 18 [solo entre 18..60], 31.5)
        canvas.drawLine(sx(4.5f), sy(0f), sx(4.5f), sy(78f), mainLinePaint)
        canvas.drawLine(sx(18f), sy(18f), sx(18f), sy(60f), mainLinePaint)
        canvas.drawLine(sx(31.5f), sy(0f), sx(31.5f), sy(78f), mainLinePaint)

        // Líneas horizontales (servicio y red)
        canvas.drawLine(sx(4.5f), sy(18f), sx(31.5f), sy(18f), mainLinePaint)
        canvas.drawLine(sx(0f), sy(39f), sx(36f), sy(39f), netLinePaint)
        canvas.drawLine(sx(4.5f), sy(60f), sx(31.5f), sy(60f), mainLinePaint)
        // Líneas de fondo (baselines)
        canvas.drawLine(sx(0f), sy(0f), sx(36f), sy(0f), mainLinePaint)
        canvas.drawLine(sx(0f), sy(78f), sx(36f), sy(78f), mainLinePaint)

        // Borde exterior (marco) rect(0.075,0.075, 35.85x77.85)
        val outerRect = android.graphics.RectF(
            sx(0.075f),
            sy(0.075f),
            sx(0.075f + 35.85f),
            sy(0.075f + 77.85f)
        )
        canvas.drawRect(outerRect, outerLinePaint)

        // Marcas centrales (ticks) superior e inferior
        val tickPaint = Paint(linePaint).apply {
            strokeWidth = mainStroke * 0.8f
            isAntiAlias = true
        }
        // Superior: rect(392,28,16x6)
        canvas.drawLine(sx(18f), sy(-0.8f), sx(18f), sy(0.8f), tickPaint)
        // Inferior: rect(392,1366,16x6)
        canvas.drawLine(sx(18f), sy(77.2f), sx(18f), sy(78.8f), tickPaint)

        // Postes de red (opcionales)
        val postPaint = Paint().apply {
            color = AndroidColor.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRect(sx(0.075f), sy(38.6f), sx(0.075f + 0.05f), sy(38.6f + 0.8f), postPaint)
        canvas.drawRect(sx(35.875f), sy(38.6f), sx(35.875f + 0.05f), sy(38.6f + 0.8f), postPaint)
  } else if (isPadel) {
        // Cancha de pádel basada en el nuevo SVG (800x1400), escalada al tamaño actual
        val scaleX = width / 800f
        val scaleY = height / 1400f

        fun sx(x: Float) = x * scaleX
        fun sy(y: Float) = y * scaleY

        // Pinturas blancas con distinto grosor
        val whiteThickPaint = Paint(linePaint).apply {
            // Línea central gruesa (aprox. 12px cuando base es 8px)
            strokeWidth = linePaint.strokeWidth * 1.5f
        }
        val whiteThinPaint = Paint(linePaint).apply {
            // Líneas delgadas laterales y vertical central (aprox. 4px)
            strokeWidth = linePaint.strokeWidth * 0.5f
        }

        // Zona azul (cancha)
        canvas.drawRect(
            sx(80f),
            sy(40f),
            sx(80f + 640f),
            sy(40f + 1320f),
            padelCourtPaint
        )

        // Borde de la zona azul (marco)
        canvas.drawRect(
            sx(80f),
            sy(40f),
            sx(80f + 640f),
            sy(40f + 1320f),
            padelWallPaint
        )

        // LÍNEA CENTRAL GRUESA
        canvas.drawLine(
            sx(80f),
            sy(700f),
            sx(720f),
            sy(700f),
            whiteThickPaint
        )

        // LÍNEAS DELGADAS LATERALES SUPERIOR / INFERIOR
        canvas.drawLine(
            sx(80f),
            sy(360f),
            sx(720f),
            sy(360f),
            whiteThinPaint
        )
        canvas.drawLine(
            sx(80f),
            sy(1040f),
            sx(720f),
            sy(1040f),
            whiteThinPaint
        )

        // LÍNEA VERTICAL DELGADA (solo entre las horizontales delgadas)
        canvas.drawLine(
            sx(400f),
            sy(360f),
            sx(400f),
            sy(1040f),
            whiteThinPaint
        )

        // Marcos exteriores azul oscuro arriba/abajo (#073150)
        val padelBarPaint = Paint().apply {
            color = AndroidColor.parseColor("#073150")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRect(
            sx(70f),
            sy(30f),
            sx(70f + 660f),
            sy(30f + 20f),
            padelBarPaint
        )
        canvas.drawRect(
            sx(70f),
            sy(1350f),
            sx(70f + 660f),
            sy(1350f + 20f),
            padelBarPaint
        )
    } else if (isVolleyball) {
        // Escala basada en el SVG de vóley (viewBox 9x18)
        val scaleX = width / 9f
        val scaleY = height / 18f
        val unit = kotlin.math.min(scaleX, scaleY)

        fun sx(x: Float) = x * scaleX
        fun sy(y: Float) = y * scaleY

        // Fondo verde exterior (#168548)
        val bgPaint = Paint().apply {
            color = AndroidColor.parseColor("#168548")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Área de juego naranja (#f49a40) con borde blanco 0.1 unidades
        val courtFill = Paint().apply {
            color = AndroidColor.parseColor("#f49a40")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val courtStroke = Paint(linePaint).apply { strokeWidth = unit * 0.1f }
        val lineStroke = Paint(linePaint).apply { strokeWidth = unit * 0.1f }
        val dottedStroke = Paint(linePaint).apply { strokeWidth = unit * 0.07f }

        val left = sx(0.25f)
        val top = sy(0.25f)
        val right = sx(0.25f + 8.5f)
        val bottom = sy(0.25f + 17.5f)

        // Relleno
        canvas.drawRect(left, top, right, bottom, courtFill)
        // Borde blanco
        val courtRect = android.graphics.RectF(left, top, right, bottom)
        canvas.drawRect(courtRect, courtStroke)

        // Línea central (y = 9) y líneas de ataque (y = 6, 12) entre x=0.25..8.75
        val xStart = sx(0.25f)
        val xEnd = sx(8.75f)
        canvas.drawLine(xStart, sy(9f), xEnd, sy(9f), lineStroke)
        canvas.drawLine(xStart, sy(6f), xEnd, sy(6f), lineStroke)
        canvas.drawLine(xStart, sy(12f), xEnd, sy(12f), lineStroke)

        // Marcas de zona de saque
        val tickFill = Paint().apply {
            color = AndroidColor.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        // Izquierda (x=0.15, y=1 y y=17), tamaño 0.1 x 0.4
        canvas.drawRect(sx(0.15f), sy(1f), sx(0.15f + 0.1f), sy(1f + 0.4f), tickFill)
        canvas.drawRect(sx(0.15f), sy(17f), sx(0.15f + 0.1f), sy(17f + 0.4f), tickFill)
        // Derecha (x=8.75, y=1 y y=17)
        canvas.drawRect(sx(8.75f), sy(1f), sx(8.75f + 0.1f), sy(1f + 0.4f), tickFill)
        canvas.drawRect(sx(8.75f), sy(17f), sx(8.75f + 0.1f), sy(17f + 0.4f), tickFill)

        // Líneas punteadas externas (lado izquierdo, según SVG provisto)
        canvas.drawLine(sx(0f), sy(4.5f), sx(0.2f), sy(4.5f), dottedStroke)
        canvas.drawLine(sx(0f), sy(5f), sx(0.2f), sy(5f), dottedStroke)
        canvas.drawLine(sx(0f), sy(5.5f), sx(0.2f), sy(5.5f), dottedStroke)
        canvas.drawLine(sx(0f), sy(13.5f), sx(0.2f), sy(13.5f), dottedStroke)
        canvas.drawLine(sx(0f), sy(14f), sx(0.2f), sy(14f), dottedStroke)
        canvas.drawLine(sx(0f), sy(14.5f), sx(0.2f), sy(14.5f), dottedStroke)
    } else if (isRugby) {
        // Cancha de rugby: geometría basada en la referencia (marco, 22m, 10m, 5m/15m y porterías en H)
        val scaleX = width / 70f
        val scaleY = height / 120f

        fun sx(x: Float) = x * scaleX
        fun sy(y: Float) = y * scaleY

        val rugbyBgPaint = Paint().apply {
            color = AndroidColor.parseColor("#39A94B")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), rugbyBgPaint)

        val rugbyLine = Paint(linePaint).apply {
            strokeWidth = linePaint.strokeWidth * 0.95f
            strokeCap = Paint.Cap.SQUARE
            pathEffect = null
        }
        val rugbyDashed = Paint(rugbyLine).apply {
            val dash = kotlin.math.min(width, height) * 0.035f
            pathEffect = DashPathEffect(floatArrayOf(dash, dash * 0.75f), 0f)
        }

        val left = 8f
        val right = 62f
        val top = 5f
        val bottom = 115f
        val goalTopY = 13f
        val goalBottomY = 107f
        val halfY = 60f
        val line22TopY = 33.68f
        val line22BottomY = 86.32f
        val line10TopY = 50.6f
        val line10BottomY = 69.4f
        val line5LeftX = 11.86f
        val line15LeftX = 19.57f
        val line15RightX = 50.43f
        val line5RightX = 58.14f

        // Marco principal e in-goals
        canvas.drawRect(sx(left), sy(top), sx(right), sy(bottom), rugbyLine)
        canvas.drawLine(sx(left), sy(goalTopY), sx(right), sy(goalTopY), rugbyLine)
        canvas.drawLine(sx(left), sy(goalBottomY), sx(right), sy(goalBottomY), rugbyLine)

        // Mitad y líneas de 22m
        canvas.drawLine(sx(left), sy(halfY), sx(right), sy(halfY), rugbyLine)
        canvas.drawLine(sx(left), sy(line22TopY), sx(right), sy(line22TopY), rugbyLine)
        canvas.drawLine(sx(left), sy(line22BottomY), sx(right), sy(line22BottomY), rugbyLine)

        // Líneas de 10m (segmentadas)
        canvas.drawLine(sx(left), sy(line10TopY), sx(right), sy(line10TopY), rugbyDashed)
        canvas.drawLine(sx(left), sy(line10BottomY), sx(right), sy(line10BottomY), rugbyDashed)

        // Líneas longitudinales 5m y 15m desde touch (segmentadas)
        canvas.drawLine(sx(line5LeftX), sy(goalTopY), sx(line5LeftX), sy(goalBottomY), rugbyDashed)
        canvas.drawLine(sx(line15LeftX), sy(goalTopY), sx(line15LeftX), sy(goalBottomY), rugbyDashed)
        canvas.drawLine(sx(line15RightX), sy(goalTopY), sx(line15RightX), sy(goalBottomY), rugbyDashed)
        canvas.drawLine(sx(line5RightX), sy(goalTopY), sx(line5RightX), sy(goalBottomY), rugbyDashed)

        // Marca central corta
        canvas.drawLine(sx(35f), sy(58.8f), sx(35f), sy(61.2f), rugbyLine)

        // Porterías en H (arriba y abajo)
        val postGap = 3.8f
        val topPostTop = 6.2f
        val topPostBottom = 11.2f
        val topCrossbarY = 9.2f
        val bottomPostTop = 108.8f
        val bottomPostBottom = 113.8f
        val bottomCrossbarY = 110.8f

        canvas.drawLine(
            sx(35f - postGap / 2f), sy(topPostTop),
            sx(35f - postGap / 2f), sy(topPostBottom),
            rugbyLine
        )
        canvas.drawLine(
            sx(35f + postGap / 2f), sy(topPostTop),
            sx(35f + postGap / 2f), sy(topPostBottom),
            rugbyLine
        )
        canvas.drawLine(
            sx(35f - postGap / 2f), sy(topCrossbarY),
            sx(35f + postGap / 2f), sy(topCrossbarY),
            rugbyLine
        )

        canvas.drawLine(
            sx(35f - postGap / 2f), sy(bottomPostTop),
            sx(35f - postGap / 2f), sy(bottomPostBottom),
            rugbyLine
        )
        canvas.drawLine(
            sx(35f + postGap / 2f), sy(bottomPostTop),
            sx(35f + postGap / 2f), sy(bottomPostBottom),
            rugbyLine
        )
        canvas.drawLine(
            sx(35f - postGap / 2f), sy(bottomCrossbarY),
            sx(35f + postGap / 2f), sy(bottomCrossbarY),
            rugbyLine
        )
    } else if (isBabyFootball) {
        // Cancha de baby fútbol basada en SVG (viewBox 20x40)
        val scaleX = width / 20f
        val scaleY = height / 40f
        val unit = kotlin.math.min(scaleX, scaleY)

        fun sx(x: Float) = x * scaleX
        fun sy(y: Float) = y * scaleY

        // Fondo verde pasto
        val bg = Paint().apply {
            color = AndroidColor.parseColor("#24aa4cff")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bg)

        val lineW = unit * 0.12f
        val goalW = unit * 0.14f
        val lineP = Paint(linePaint).apply { strokeWidth = lineW; strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND }
        val goalP = Paint(linePaint).apply { strokeWidth = goalW }

        // Marco interior
        val innerRect = android.graphics.RectF(sx(0.2f), sy(0.2f), sx(19.8f), sy(39.8f))
        canvas.drawRect(innerRect, lineP)

        // Línea central
        canvas.drawLine(sx(0.2f), sy(20f), sx(19.8f), sy(20f), lineP)

        // Círculo central
        canvas.drawCircle(sx(10f), sy(20f), 3f * unit, lineP)

        // Arcos grandes (r=6)
        canvas.drawLine(sx(0.2f), sy(6.2f), sx(19.8f), sy(6.2f), lineP)
        canvas.drawLine(sx(0.2f), sy(33.8f), sx(19.8f), sy(33.8f), lineP)

        // Arcos pequeños (r=4)

        // Porterías
        canvas.drawRect(sx(7.5f), sy(0.2f), sx(7.5f + 5f), sy(0.2f + 1f), goalP)
        canvas.drawRect(sx(7.5f), sy(38.8f), sx(7.5f + 5f), sy(38.8f + 1f), goalP)

        // Puntos de penal
        val spot = Paint().apply {
            color = AndroidColor.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawCircle(sx(10f), sy(6.2f), unit * 0.08f, spot)
        canvas.drawCircle(sx(10f), sy(33.8f), unit * 0.08f, spot)
    } else {
        // Fútbol Baby: 800x1400 base (versión con áreas penales agrandadas)
        val scaleX = width / 800f
        val scaleY = height / 1400f
        val circleScale = kotlin.math.min(scaleX, scaleY)

        fun sx(x: Float) = x * scaleX
        fun sy(y: Float) = y * scaleY

        // BORDE EXTERIOR
        val outerRect = android.graphics.RectF(
            sx(20f),
            sy(20f),
            sx(20f + 760f),
            sy(20f + 1360f)
        )
        canvas.drawRoundRect(outerRect, 6f * circleScale, 6f * circleScale, linePaint)

        // MEDIO CAMPO
        canvas.drawLine(sx(20f), sy(700f), sx(780f), sy(700f), linePaint)
        canvas.drawCircle(sx(400f), sy(700f), 90f * circleScale, linePaint)

        // Punto central
        val spotPaint = Paint().apply {
            color = AndroidColor.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawCircle(sx(400f), sy(700f), 6f * circleScale, spotPaint)

        // ÁREA PENAL SUPERIOR – AGRANDADA
        // Área grande
        canvas.drawRect(sx(250f), sy(20f), sx(250f + 300f), sy(20f + 180f), linePaint)
        // Área chica
        canvas.drawRect(sx(310f), sy(20f), sx(310f + 180f), sy(20f + 90f), linePaint)
        // Semicírculo penal ("sol" apoyado en la línea del área grande, ancho igual al área)
        val rTop = 75f
        val topAreaBottomY = 20f + 180f
        // Mover un poco el centro hacia el área para acercar visualmente el arco a la línea
        val topCenterY = topAreaBottomY
        val topArcRect = android.graphics.RectF(
            sx(400f - rTop), sy(topCenterY - rTop),
            sx(400f + rTop), sy(topCenterY + rTop)
        )
        canvas.save()
        // Recorte levemente por encima de la línea del área para que el trazo del arco
        // se pegue visualmente a la línea horizontal (evita el "hueco" por antialiasing)
        val topClipY = sy(topAreaBottomY)
        canvas.clipRect(
            0f,
            topClipY,
            canvas.width.toFloat(),
            canvas.height.toFloat()
        )
        canvas.drawArc(topArcRect, 0f, 180f, false, linePaint)
        canvas.restore()
        // Punto penal superior (se mantiene en 170f)
        canvas.drawCircle(sx(400f), sy(170f), 5f * circleScale, spotPaint)

        // ÁREA PENAL INFERIOR – AGRANDADA
        // Área grande
        canvas.drawRect(sx(250f), sy(1200f), sx(250f + 300f), sy(1200f + 180f), linePaint)
        // Área chica
        canvas.drawRect(sx(310f), sy(1270f), sx(310f + 180f), sy(1270f + 90f), linePaint)
        // Semicírculo penal inferior ("sol" apoyado en la línea del área grande, ancho igual al área)
        val rBottom = 75f
        val bottomAreaTopY = 1200f
        // Mover un poco el centro hacia el área inferior
        val bottomCenterY = bottomAreaTopY
        val bottomArcRect = android.graphics.RectF(
            sx(400f - rBottom), sy(bottomCenterY - rBottom),
            sx(400f + rBottom), sy(bottomCenterY + rBottom)
        )
        canvas.save()
        // Recorte levemente por debajo de la línea del área inferior para que el trazo del arco
        // se pegue visualmente a la línea horizontal
        val bottomClipY = sy(bottomAreaTopY)
        canvas.clipRect(
            0f,
            0f,
            canvas.width.toFloat(),
            bottomClipY
        )
        canvas.drawArc(bottomArcRect, 180f, 180f, false, linePaint)
        canvas.restore()
        // Punto penal inferior (se mantiene en 1230f)
        canvas.drawCircle(sx(400f), sy(1230f), 5f * circleScale, spotPaint)
    }

    fun splitTeam(team: List<Player>): Pair<Player?, List<Player>> {
        val gk = team.firstOrNull { it.isGoalkeeper }
        val field = if (gk != null) team.filterNot { it.isGoalkeeper } else team
        return gk to field
    }

    val (gkA, fieldA) = splitTeam(teamA)
    val (gkB, fieldB) = splitTeam(teamB)

    fun positionsHalf(teamSize: Int, hasGoalkeeper: Boolean, isTop: Boolean): List<Pair<Float, Float>> {
        if (teamSize <= 0) return emptyList()

        // Caso especial: equipos de 5 (1 arquero + 4 de campo)
        // Colocamos 2 jugadores en línea defensiva y 2 en línea ofensiva.
        if (hasGoalkeeper && teamSize == 4) {
            val halfHeight = height / 2f
            val innerBand = halfHeight - boxHeight
            val areaLine = if (isTop) boxHeight else height - boxHeight
            val sign = if (isTop) 1f else -1f

            val backY = areaLine + sign * innerBand * 0.15f
            val frontY = areaLine + sign * innerBand * 0.6f

            val xLeft = width * 0.33f
            val xRight = width * 0.67f

            return listOf(
                xLeft to backY,
                xRight to backY,
                xLeft to frontY,
                xRight to frontY
            )
        }

        // Caso especial: 5 jugadores de campo sin arquero -> 3 atrás y 2 adelante
        if (!hasGoalkeeper && teamSize == 5) {
            val halfHeight = height / 2f
            val innerBand = halfHeight - boxHeight
            val areaLine = if (isTop) boxHeight else height - boxHeight
            val sign = if (isTop) 1f else -1f

            val backY = areaLine + sign * innerBand * 0.18f
            val frontY = areaLine + sign * innerBand * 0.65f

            val xBack1 = width * 0.25f
            val xBack2 = width * 0.50f
            val xBack3 = width * 0.75f
            val xFront1 = width * 0.33f
            val xFront2 = width * 0.67f

            return listOf(
                xBack1 to backY,
                xBack2 to backY,
                xBack3 to backY,
                xFront1 to frontY,
                xFront2 to frontY
            )
        }

        // Distribución genérica para otros tamaños: rejilla simple por mitad
        // Siempre entre la línea del área y el medio campo, simétrico para ambos equipos.
        val cols = kotlin.math.min(4, kotlin.math.max(1, teamSize))
        val rows = ((teamSize + cols - 1) / cols)
        val halfHeight = height / 2f
        val innerBand = halfHeight - boxHeight
        val areaLine = if (isTop) boxHeight else height - boxHeight
        val sign = if (isTop) 1f else -1f
        val cellW = width / (cols + 1).toFloat()
        val cellH = innerBand / (rows + 1)

        val result = mutableListOf<Pair<Float, Float>>()
        var idx = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (idx >= teamSize) break
                val x = cellW * (c + 1)
                val y = areaLine + sign * cellH * (r + 1)
                result.add(x to y)
                idx++
            }
        }
        return result
    }

    val posA = positionsHalf(fieldA.size, hasGoalkeeper = gkA != null, isTop = true)
    val posB = positionsHalf(fieldB.size, hasGoalkeeper = gkB != null, isTop = false)

    val teamAPaint = Paint().apply {
        color = teamAColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    val teamBPaint = Paint().apply {
        color = teamBColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    val radius = kotlin.math.min(width, height) * 0.018f

    fun drawJersey(
        centerX: Float,
        centerY: Float,
        paint: Paint,
        nationKey: String? = null,
        isGoalkeeper: Boolean = false
    ) {
        val baseColorInt = paint.color

        val iconSize = radius * 6.6f
        val half = iconSize / 2f
        val leftF = centerX - half
        val topF = centerY - half

        if (!nationKey.isNullOrBlank()) {
            drawNationKitShirt(canvas, leftF, topF, iconSize, nationKey)
            return
        }
        drawSolidKitShirt(canvas, leftF, topF, iconSize, baseColorInt)
    }

    // Jugadores de campo de Equipo A (parte superior)
    fieldA.zip(posA).forEach { (player, pos) ->
        drawJersey(pos.first, pos.second, teamAPaint, nationKey = teamANationKey, isGoalkeeper = false)
        canvas.drawText(player.name, pos.first, pos.second + radius * 3.8f, textPaint)
    }

    // Jugadores de campo de Equipo B (parte inferior)
    fieldB.zip(posB).forEach { (player, pos) ->
        drawJersey(pos.first, pos.second, teamBPaint, nationKey = teamBNationKey, isGoalkeeper = false)
        canvas.drawText(player.name, pos.first, pos.second + radius * 3.8f, textPaint)
    }
    // Arqueros cerca de cada arco
    gkA?.let {
        val x = width / 2f
        val y = boxHeight * 0.5f
        drawJersey(x, y, teamAPaint, nationKey = teamANationKey, isGoalkeeper = true)
        canvas.drawText(it.name, x, y + radius * 3.8f, textPaint)
    }
    gkB?.let {
        val x = width / 2f
        val y = height - boxHeight * 0.5f
        drawJersey(x, y, teamBPaint, nationKey = teamBNationKey, isGoalkeeper = true)
        canvas.drawText(it.name, x, y + radius * 3.8f, textPaint)
    }

    if (isFootball || isBabyFootball || isTennis || isVolleyball || isRugby) {
        canvas.restore()
    }

    return bitmap
}

private fun createShareBitmap(
    context: Context,
    teamA: List<Player>,
    teamB: List<Player>,
    sport: String,
    teamAColor: Int,
    teamBColor: Int,
    teamANationKey: String? = null,
    teamBNationKey: String? = null,
    text: String
): Bitmap {
    val base = createPitchBitmap(
        context = context,
        teamA = teamA,
        teamB = teamB,
        sport = sport,
        teamAColor = teamAColor,
        teamBColor = teamBColor,
        teamANationKey = teamANationKey,
        teamBNationKey = teamBNationKey
    )
    val width = base.width

    val lines = text.split("\n")
    val padding = (width * 0.06f)
    val textSize = width * 0.035f
    val lineSpacing = textSize * 1.4f
    val textHeight = lines.size * lineSpacing + padding * 2f
    val extraHeight = textHeight.toInt()

    val totalHeight = base.height + extraHeight
    val result = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(result)

    val backgroundPaint = Paint().apply {
        color = AndroidColor.parseColor("#43A047")
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    canvas.drawRect(0f, 0f, width.toFloat(), totalHeight.toFloat(), backgroundPaint)

    canvas.drawBitmap(base, 0f, 0f, null)

    val textPaint = Paint().apply {
        color = AndroidColor.WHITE
        this.textSize = textSize
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
    }

    var y = base.height + padding + textSize
    val x = padding
    for (line in lines) {
        canvas.drawText(line, x, y, textPaint)
        y += lineSpacing
    }

    return result
}

private fun shareTeamsWithImage(
    context: Context,
    titleA: String,
    titleB: String,
    teamA: List<Player>,
    teamB: List<Player>,
    sport: String,
    teamAColor: Int,
    teamBColor: Int,
    teamANationKey: String? = null,
    teamBNationKey: String? = null
) {
    if (teamA.isEmpty() || teamB.isEmpty()) return
    val text = formatTeamsText(context, titleA, titleB, teamA, teamB)
    val bitmap = createShareBitmap(
        context = context,
        teamA = teamA,
        teamB = teamB,
        sport = sport,
        teamAColor = teamAColor,
        teamBColor = teamBColor,
        teamANationKey = teamANationKey,
        teamBNationKey = teamBNationKey,
        text = text
    )
    try {
        val dir = File(context.cacheDir, "shares").apply { mkdirs() }
        val file = File(dir, "pitch_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject_current))
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
    } catch (_: IOException) {
        // Si falla, hacer fallback a compartir solo texto
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject_current))
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    registerSecretOpener: (() -> Unit) -> Unit = {},
    pendingChatPostId: String? = null,
    pendingChatPeerName: String? = null,
    pendingChatIsGroup: Boolean = false,
    clearPendingChat: () -> Unit = {},
    onShowHistory: () -> Unit = {},
    isDarkTheme: Boolean = false,
    onToggleDarkTheme: (Boolean) -> Unit = {}
) {
    var showInfo by remember { mutableStateOf(false) }
    var showHistory by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val currentUserName by userNameState
    LaunchedEffect(Unit) { userNameState.value = loadUserName(context) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = Color.Transparent,
        topBar = {
            ThreeDTopAppBar(
                baseColor = MaterialTheme.colorScheme.primary,
                title = {
                    val name = (currentUserName ?: "").trim()
                    Text(
                        text = if (name.isNotEmpty()) stringResource(R.string.welcome_user, name) else stringResource(R.string.welcome_generic),
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = {
                        val currentTags = AppCompatDelegate.getApplicationLocales().toLanguageTags()
                        val newLocales = if (currentTags.startsWith("en")) {
                            LocaleListCompat.forLanguageTags("es")
                        } else {
                            LocaleListCompat.forLanguageTags("en")
                        }
                        AppCompatDelegate.setApplicationLocales(newLocales)
                    }) {
                        Icon(Icons.Filled.Language, contentDescription = stringResource(R.string.action_language))
                    }
                    IconButton(onClick = { showHistory = true }) {
                        Icon(Icons.Filled.History, contentDescription = stringResource(R.string.history))
                    }
                    IconButton(onClick = { showSettings = true }) {
                        Icon(Icons.Filled.Settings, contentDescription = stringResource(R.string.settings_title))
                    }
                    IconButton(onClick = { showInfo = true }) {
                        Icon(Icons.Filled.Info, contentDescription = stringResource(R.string.info_title))
                    }
                }
            )
        }
    ) { innerPadding ->
        PlayersApp(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            registerSecretOpener = registerSecretOpener,
            pendingChatPostId = pendingChatPostId,
            pendingChatPeerName = pendingChatPeerName,
            pendingChatIsGroup = pendingChatIsGroup,
            clearPendingChat = clearPendingChat,
            onShowHistory = { showHistory = true },
            isDarkTheme = isDarkTheme
        )
        if (showSettings) {
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = { showSettings = false },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text(stringResource(R.string.settings_title), color = dialogTextColor) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.dark_mode), color = dialogTextColor)
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { onToggleDarkTheme(it) }
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showSettings = false }) {
                        Text(stringResource(R.string.close))
                    }
                }
            )
        }
        if (showInfo) {
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = { showInfo = false },
                title = { Text(stringResource(R.string.info_title), color = dialogTextColor) },
                text = {
                    Text(stringResource(R.string.info_text), textAlign = TextAlign.Start, color = dialogTextColor)
                },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                confirmButton = { Button(onClick = { showInfo = false }) { Text(stringResource(R.string.info_ok)) } }
            )
        }
        if (showHistory) {
            val context = LocalContext.current
            var matches by remember(showHistory) { mutableStateOf(loadRecentMatches(context)) }
            var selectedMatch by remember(showHistory) { mutableStateOf<SavedMatch?>(null) }
            var pendingDelete by remember(showHistory) { mutableStateOf<SavedMatch?>(null) }
            var confirmClearAll by remember(showHistory) { mutableStateOf(false) }
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface

            AlertDialog(
                onDismissRequest = { showHistory = false },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text(stringResource(R.string.history_title), color = dialogTextColor) },
                text = {
                    if (matches.isEmpty()) {
                        Text(stringResource(R.string.active_session), color = dialogTextColor)
                    } else {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyColumn(modifier = Modifier.heightIn(max = 360.dp)) {
                                items(matches) { m ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedMatch = m }
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(stringResource(R.string.versus_format, m.titleA, m.titleB), fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                            Spacer(Modifier.height(6.dp))
                                            Text(stringResource(R.string.score_format, m.teamA.size, m.teamB.size), color = MaterialTheme.colorScheme.primary)
                                            if (m.result.isNotBlank()) {
                                                Spacer(Modifier.height(2.dp))
                                                Text(stringResource(R.string.result_label) + ": " + m.result, color = MaterialTheme.colorScheme.primary)
                                            }
                                        }
                                        IconButton(onClick = { pendingDelete = m }) {
                                            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete), tint = dialogTextColor)
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                },
                dismissButton = {
                    if (matches.isNotEmpty()) {
                        TextButton(onClick = { confirmClearAll = true }) { Text(stringResource(R.string.clear_history), color = dialogTextColor) }
                    }
                },
                confirmButton = { Button(onClick = { showHistory = false }) { Text(stringResource(R.string.close)) } }
            )

            if (pendingDelete != null) {
                val m = pendingDelete!!
                val confirmDialogContainerColor = MaterialTheme.colorScheme.surface
                val confirmDialogTextColor = MaterialTheme.colorScheme.onSurface
                AlertDialog(
                    onDismissRequest = { pendingDelete = null },
                    containerColor = confirmDialogContainerColor,
                    titleContentColor = confirmDialogTextColor,
                    textContentColor = confirmDialogTextColor,
                    title = { Text(stringResource(R.string.delete_match_title), color = confirmDialogTextColor) },
                    text = { Text(stringResource(R.string.delete_match_confirm, m.titleA, m.titleB)) },
                    dismissButton = { TextButton(onClick = { pendingDelete = null }) { Text(stringResource(R.string.cancel), color = confirmDialogTextColor) } },
                    confirmButton = {
                        Button(onClick = {
                            deleteMatch(context, m.id)
                            matches = loadRecentMatches(context)
                            pendingDelete = null
                        }) { Text(stringResource(R.string.delete)) }
                    }
                )
            }

            if (confirmClearAll) {
                val confirmDialogContainerColor = MaterialTheme.colorScheme.surface
                val confirmDialogTextColor = MaterialTheme.colorScheme.onSurface
                AlertDialog(
                    onDismissRequest = { confirmClearAll = false },
                    containerColor = confirmDialogContainerColor,
                    titleContentColor = confirmDialogTextColor,
                    textContentColor = confirmDialogTextColor,
                    title = { Text(stringResource(R.string.clear_history_title), color = confirmDialogTextColor) },
                    text = { Text(stringResource(R.string.clear_history_confirm)) },
                    dismissButton = { TextButton(onClick = { confirmClearAll = false }) { Text(stringResource(R.string.cancel), color = confirmDialogTextColor) } },
                    confirmButton = {
                        Button(onClick = {
                            clearAllMatches(context)
                            matches = emptyList()
                            confirmClearAll = false
                        }) { Text(stringResource(R.string.clear_history)) }
                    }
                )
            }

            if (selectedMatch != null) {
                val m = selectedMatch!!
                var resultText by remember(m.id) { mutableStateOf(m.result) }
                var editableMatch by remember(m.id) { mutableStateOf(m) }
                var selectedTeam by remember(m.id) { mutableStateOf<Char?>(null) }
                var selectedPlayerName by remember(m.id) { mutableStateOf<String?>(null) }
                val editDialogContainerColor = MaterialTheme.colorScheme.surface
                val editDialogTextColor = MaterialTheme.colorScheme.onSurface
                val editDialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                AlertDialog(
                    onDismissRequest = { selectedMatch = null },
                    containerColor = editDialogContainerColor,
                    titleContentColor = editDialogTextColor,
                    textContentColor = editDialogTextColor,
                    title = { Text(stringResource(R.string.versus_format, m.titleA, m.titleB), color = editDialogTextColor) },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Barra de edición global: tarjetas y capitán
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.result_label),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Tarjeta amarilla
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFFFFEB3B))
                                            .clickable(enabled = selectedPlayerName != null) {
                                                val team = selectedTeam
                                                val name = selectedPlayerName
                                                if (team != null && name != null) {
                                                    if (team == 'A') {
                                                        val updated = editableMatch.teamA.map {
                                                            if (it.name == name) it.copy(hasYellowCard = !it.hasYellowCard) else it
                                                        }
                                                        editableMatch = editableMatch.copy(teamA = updated)
                                                    } else {
                                                        val updated = editableMatch.teamB.map {
                                                            if (it.name == name) it.copy(hasYellowCard = !it.hasYellowCard) else it
                                                        }
                                                        editableMatch = editableMatch.copy(teamB = updated)
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {}

                                    // Tarjeta roja
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color.Red)
                                            .clickable(enabled = selectedPlayerName != null) {
                                                val team = selectedTeam
                                                val name = selectedPlayerName
                                                if (team != null && name != null) {
                                                    if (team == 'A') {
                                                        val updated = editableMatch.teamA.map {
                                                            if (it.name == name) it.copy(hasRedCard = !it.hasRedCard) else it
                                                        }
                                                        editableMatch = editableMatch.copy(teamA = updated)
                                                    } else {
                                                        val updated = editableMatch.teamB.map {
                                                            if (it.name == name) it.copy(hasRedCard = !it.hasRedCard) else it
                                                        }
                                                        editableMatch = editableMatch.copy(teamB = updated)
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {}

                                    // Capitán (C con fondo cuadrado)
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                            .clickable(enabled = selectedPlayerName != null) {
                                                val team = selectedTeam
                                                val name = selectedPlayerName
                                                if (team != null && name != null) {
                                                    if (team == 'A') {
                                                        val updated = editableMatch.teamA.map {
                                                            if (it.name == name) it.copy(isCaptain = !it.isCaptain) else it
                                                        }
                                                        editableMatch = editableMatch.copy(teamA = updated)
                                                    } else {
                                                        val updated = editableMatch.teamB.map {
                                                            if (it.name == name) it.copy(isCaptain = !it.isCaptain) else it
                                                        }
                                                        editableMatch = editableMatch.copy(teamB = updated)
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(stringResource(R.string.captain_short), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = resultText,
                                onValueChange = { resultText = it },
                                label = { Text(stringResource(R.string.result_label), color = editDialogSecondaryTextColor) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = editDialogTextColor,
                                    unfocusedTextColor = editDialogTextColor,
                                    cursorColor = editDialogTextColor,
                                    focusedLabelColor = editDialogSecondaryTextColor,
                                    unfocusedLabelColor = editDialogSecondaryTextColor,
                                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )

                            Text(editableMatch.titleA, fontWeight = FontWeight.SemiBold, color = editDialogTextColor)
                            Spacer(Modifier.height(4.dp))
                            editableMatch.teamA.forEach { p ->
                                val isSelected = selectedTeam == 'A' && selectedPlayerName == p.name
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent)
                                        .clickable {
                                            selectedTeam = 'A'
                                            selectedPlayerName = p.name
                                        }
                                        .padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (p.isGoalkeeper) {
                                            Icon(Icons.Filled.BackHand, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                            Spacer(Modifier.width(6.dp))
                                        } else {
                                            Icon(painterResource(id = R.drawable.ic_tshirt), contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Spacer(Modifier.width(6.dp))
                                        }
                                        Text(stringResource(R.string.bullet_player_name, p.name), color = editDialogTextColor)
                                        if (p.isCaptain) {
                                            Spacer(Modifier.width(4.dp))
                                            Text(stringResource(R.string.captain_short), fontWeight = FontWeight.Bold, color = editDialogTextColor)
                                        }
                                        if (p.hasYellowCard) {
                                            Spacer(Modifier.width(4.dp))
                                            Text(stringResource(R.string.yellow_card_short), color = Color(0xFFFFEB3B))
                                        }
                                        if (p.hasRedCard) {
                                            Spacer(Modifier.width(4.dp))
                                            Text(stringResource(R.string.red_card_short), color = Color.Red)
                                        }
                                    }
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            HorizontalDivider()
                            Spacer(Modifier.height(12.dp))
                            Text(editableMatch.titleB, fontWeight = FontWeight.SemiBold, color = editDialogTextColor)
                            Spacer(Modifier.height(4.dp))
                            editableMatch.teamB.forEach { p ->
                                val isSelected = selectedTeam == 'B' && selectedPlayerName == p.name
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent)
                                        .clickable {
                                            selectedTeam = 'B'
                                            selectedPlayerName = p.name
                                        }
                                        .padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (p.isGoalkeeper) {
                                            Icon(Icons.Filled.BackHand, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                            Spacer(Modifier.width(6.dp))
                                        } else {
                                            Icon(painterResource(id = R.drawable.ic_tshirt), contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Spacer(Modifier.width(6.dp))
                                        }
                                        Text(stringResource(R.string.bullet_player_name, p.name), color = editDialogTextColor)
                                        if (p.isCaptain) {
                                            Spacer(Modifier.width(4.dp))
                                            Text("C", fontWeight = FontWeight.Bold, color = editDialogTextColor)
                                        }
                                        if (p.hasYellowCard) {
                                            Spacer(Modifier.width(4.dp))
                                            Text("TA", color = Color(0xFFFFEB3B))
                                        }
                                        if (p.hasRedCard) {
                                            Spacer(Modifier.width(4.dp))
                                            Text("TR", color = Color.Red)
                                        }
                                    }
                                }
                            }
                        }
                    },
                    dismissButton = {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(onClick = { selectedMatch = null }) { Text(stringResource(R.string.close)) }
                            val context = LocalContext.current
                            Button(onClick = {
                                val text = formatSavedMatchText(context, editableMatch.copy(result = resultText))
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject_match, editableMatch.titleA, editableMatch.titleB))
                                    putExtra(Intent.EXTRA_TEXT, text)
                                }
                                context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
                            }) { Text(stringResource(R.string.share)) }
                        }
                    },
                    confirmButton = {
                        val context = LocalContext.current
                        Button(onClick = {
                            val finalMatch = editableMatch.copy(result = resultText.trim())
                            updateMatch(context, finalMatch)
                            matches = loadMatches(context)
                            selectedMatch = null
                        }) { Text(stringResource(R.string.save)) }
                    }
                )
            }
        }
    }
}

data class Player(
    val name: String,
    val attack: Double,
    val defense: Double,
    val physical: Double,
    val isGoalkeeper: Boolean = false,
    val isCaptain: Boolean = false,
    val hasYellowCard: Boolean = false,
    val hasRedCard: Boolean = false
) {
    val rating: Double
        get() = attack * WEIGHT_ATTACK + defense * WEIGHT_DEFENSE + physical * WEIGHT_PHYSICAL
}

// Constants for player ratings

data class SavedMatch(
    val id: Long,
    val time: Long,
    val titleA: String,
    val titleB: String,
    val teamA: List<Player>,
    val teamB: List<Player>,
    val result: String = ""
)

data class SavedTeam(
    val id: Long,
    val name: String,
    val players: List<Player>
)

data class SavedTournament(
    val id: Long,
    val time: Long,
    val name: String,
    val teams: List<SavedTeam>,
    val rounds: List<List<TournamentMatch>>,
    val championName: String? = null,
    val groupStage: SavedGroupStage? = null
)

data class SavedGroupStage(
    val groups: List<List<Long>>,
    val matches: List<SavedGroupMatch>,
    val randomCalendar: Boolean = true
)

data class SavedGroupMatch(
    val id: Int,
    val groupIndex: Int,
    val homeId: Long,
    val awayId: Long,
    val homeGoals: Int? = null,
    val awayGoals: Int? = null,
    val homeYellows: Int = 0,
    val awayYellows: Int = 0,
    val homeReds: Int = 0,
    val awayReds: Int = 0
)

data class SavedLeague(
    val id: Long,
    val time: Long,
    val name: String,
    val teams: List<SavedTeam>,
    val matches: List<LeagueMatch>,
    val winPoints: Int = 3,
    val drawPoints: Int = 1,
    val lossPoints: Int = 0,
    val randomCalendar: Boolean = true
)

data class TournamentMatch(
    val id: Int,
    val teamA: SavedTeam?,
    val teamB: SavedTeam?,
    val winnerId: Long? = null
)

private fun isPowerOfTwo(n: Int): Boolean {
    return n > 0 && (n and (n - 1)) == 0
}

private fun buildTournamentBracket(teams: List<SavedTeam>, randomBracket: Boolean = true): List<List<TournamentMatch>> {
    if (teams.isEmpty()) return emptyList()
    val shuffled = if (randomBracket) teams.shuffled() else teams
    var idCounter = 0
    val firstRound = mutableListOf<TournamentMatch>()
    var i = 0
    while (i < shuffled.size) {
        val a = shuffled[i]
        val b = if (i + 1 < shuffled.size) shuffled[i + 1] else null
        firstRound += TournamentMatch(id = idCounter++, teamA = a, teamB = b)
        i += 2
    }
    val rounds = mutableListOf<List<TournamentMatch>>()
    rounds += firstRound
    var currentSize = firstRound.size
    while (currentSize > 1) {
        val nextRound = mutableListOf<TournamentMatch>()
        var j = 0
        while (j < currentSize) {
            nextRound += TournamentMatch(id = idCounter++, teamA = null, teamB = null)
            j += 2
        }
        rounds += nextRound
        currentSize = nextRound.size
    }
    return rounds
}

private fun buildTournamentBracketWithByes(teams: List<SavedTeam>): List<List<TournamentMatch>> {
    if (teams.isEmpty()) return emptyList()
    var idCounter = 0
    var size = 1
    while (size < teams.size) size *= 2

    val padded = teams + List(size - teams.size) { null }
    val firstRound = mutableListOf<TournamentMatch>()
    var i = 0
    while (i < padded.size) {
        val a = padded[i]
        val b = padded.getOrNull(i + 1)
        firstRound += TournamentMatch(id = idCounter++, teamA = a, teamB = b)
        i += 2
    }

    val rounds = mutableListOf<List<TournamentMatch>>()
    rounds += firstRound
    var currentSize = firstRound.size
    while (currentSize > 1) {
        val nextRound = mutableListOf<TournamentMatch>()
        var j = 0
        while (j < currentSize) {
            nextRound += TournamentMatch(id = idCounter++, teamA = null, teamB = null)
            j += 2
        }
        rounds += nextRound
        currentSize = nextRound.size
    }
    return rounds
}

data class LeagueMatch(
    val id: Int,
    val home: SavedTeam,
    val away: SavedTeam,
    val homeGoals: Int? = null,
    val awayGoals: Int? = null,
    val homeYellows: Int = 0,
    val awayYellows: Int = 0,
    val homeReds: Int = 0,
    val awayReds: Int = 0
)

data class LeagueTeamStats(
    val team: SavedTeam,
    val played: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val points: Int
) {
    val goalDiff: Int
        get() = goalsFor - goalsAgainst
}

data class LeaguePair(
    val home: SavedTeam?,
    val away: SavedTeam?
)

private fun generateLeagueRounds(teams: List<SavedTeam>, randomCalendar: Boolean = true): List<List<LeaguePair>> {
    if (teams.size < 2) return emptyList()

    val baseTeams = if (randomCalendar) teams.shuffled() else teams
    val workingTeams: MutableList<SavedTeam?> = baseTeams.map { it as SavedTeam? }.toMutableList()
    if (workingTeams.size % 2 != 0) {
        workingTeams.add(null)
    }

    val teamCount = workingTeams.size
    val roundsCount = teamCount - 1
    var rotation = workingTeams.toMutableList()

    val rounds = mutableListOf<List<LeaguePair>>()
    for (round in 0 until roundsCount) {
        val pairs = mutableListOf<LeaguePair>()
        for (i in 0 until teamCount / 2) {
            val a = rotation[i]
            val b = rotation[teamCount - 1 - i]
            pairs.add(LeaguePair(home = a, away = b))
        }
        rounds.add(pairs)

        val fixed = rotation.first()
        val rest = rotation.drop(1).toMutableList()
        val last = rest.removeAt(rest.lastIndex)
        rest.add(0, last)
        rotation = mutableListOf<SavedTeam?>().apply {
            add(fixed)
            addAll(rest)
        }
    }

    return rounds
}

private fun buildLeagueMatchesFromRounds(rounds: List<List<LeaguePair>>): List<LeagueMatch> {
    var idCounter = 0
    val firstLeg = mutableListOf<LeagueMatch>()
    rounds.forEachIndexed { roundIndex, pairs ->
        pairs.forEachIndexed { i, p ->
            val a = p.home
            val b = p.away
            if (a == null || b == null) return@forEachIndexed

            val (home, away) = if ((roundIndex + i) % 2 == 0) {
                a to b
            } else {
                b to a
            }

            firstLeg.add(
                LeagueMatch(
                    id = idCounter++,
                    home = home,
                    away = away
                )
            )
        }
    }

    val secondLeg = firstLeg.map { m ->
        LeagueMatch(
            id = idCounter++,
            home = m.away,
            away = m.home
        )
    }

    return firstLeg + secondLeg
}

private fun buildSingleLegLeagueMatchesFromRounds(rounds: List<List<LeaguePair>>, startId: Int = 0): List<LeagueMatch> {
    var idCounter = startId
    val matches = mutableListOf<LeagueMatch>()
    rounds.forEachIndexed { roundIndex, pairs ->
        pairs.forEachIndexed { i, p ->
            val a = p.home
            val b = p.away
            if (a == null || b == null) return@forEachIndexed

            val (home, away) = if ((roundIndex + i) % 2 == 0) {
                a to b
            } else {
                b to a
            }

            matches.add(
                LeagueMatch(
                    id = idCounter++,
                    home = home,
                    away = away
                )
            )
        }
    }
    return matches
}

private fun swapRoundSlots(pairs: List<LeaguePair>, indexA: Int, indexB: Int): List<LeaguePair> {
    if (indexA == indexB) return pairs
    val slots = pairs.flatMap { listOf(it.home, it.away) }.toMutableList()
    if (indexA !in slots.indices || indexB !in slots.indices) return pairs

    val tmp = slots[indexA]
    slots[indexA] = slots[indexB]
    slots[indexB] = tmp

    return slots.chunked(2).map { chunk ->
        LeaguePair(home = chunk.getOrNull(0), away = chunk.getOrNull(1))
    }
}

private fun swapTournamentSlots(slots: List<SavedTeam>, indexA: Int, indexB: Int): List<SavedTeam> {
    if (indexA == indexB) return slots
    if (indexA !in slots.indices || indexB !in slots.indices) return slots
    val out = slots.toMutableList()
    val tmp = out[indexA]
    out[indexA] = out[indexB]
    out[indexB] = tmp
    return out
}

private fun validateLeagueRounds(teams: List<SavedTeam>, rounds: List<List<LeaguePair>>): String? {
    if (teams.size < 2) return "Se necesitan al menos 2 equipos"

    val expectedMatches = teams.size * (teams.size - 1) / 2
    val expectedRounds = if (teams.size % 2 == 0) teams.size - 1 else teams.size
    if (rounds.size != expectedRounds) return "Cantidad de fases inválida"

    val pairKeys = mutableSetOf<String>()
    val playedCount = mutableMapOf<Long, Int>().apply {
        teams.forEach { put(it.id, 0) }
    }

    rounds.forEachIndexed { roundIndex, pairs ->
        val used = mutableSetOf<Long>()
        pairs.forEach { p ->
            val a = p.home
            val b = p.away
            if (a != null) {
                if (!used.add(a.id)) return "Equipo repetido en fase ${roundIndex + 1}"
            }
            if (b != null) {
                if (!used.add(b.id)) return "Equipo repetido en fase ${roundIndex + 1}"
            }
            if (a != null && b != null) {
                val minId = minOf(a.id, b.id)
                val maxId = maxOf(a.id, b.id)
                val key = "$minId-$maxId"
                if (!pairKeys.add(key)) return "Cruce repetido (${a.name} vs ${b.name})"
                playedCount[a.id] = (playedCount[a.id] ?: 0) + 1
                playedCount[b.id] = (playedCount[b.id] ?: 0) + 1
            }
        }
    }

    if (pairKeys.size != expectedMatches) return "Faltan o sobran cruces para un round-robin completo"
    if (playedCount.values.any { it != teams.size - 1 }) return "No todos los equipos juegan la misma cantidad de partidos"

    return null
}

private fun generateLeagueMatches(teams: List<SavedTeam>, randomCalendar: Boolean = true): List<LeagueMatch> {
    val rounds = generateLeagueRounds(teams, randomCalendar)
    return buildLeagueMatchesFromRounds(rounds)
}

private fun computeLeagueTable(
    teams: List<SavedTeam>,
    matches: List<LeagueMatch>,
    winPoints: Int,
    drawPoints: Int,
    lossPoints: Int
): List<LeagueTeamStats> {
    val statsMap = mutableMapOf<Long, LeagueTeamStats>()
    teams.forEach { team ->
        statsMap[team.id] = LeagueTeamStats(
            team = team,
            played = 0,
            wins = 0,
            draws = 0,
            losses = 0,
            goalsFor = 0,
            goalsAgainst = 0,
            points = 0
        )
    }

    matches.forEach { m ->
        val home = statsMap[m.home.id] ?: return@forEach
        val away = statsMap[m.away.id] ?: return@forEach

        val homeGoals = m.homeGoals
        val awayGoals = m.awayGoals

        if (homeGoals == null || awayGoals == null) return@forEach

        val homePlayed = home.played + 1
        val awayPlayed = away.played + 1

        val homeGoalsFor = home.goalsFor + homeGoals
        val homeGoalsAgainst = home.goalsAgainst + awayGoals
        val awayGoalsFor = away.goalsFor + awayGoals
        val awayGoalsAgainst = away.goalsAgainst + homeGoals

        val homeWin = homeGoals > awayGoals
        val awayWin = awayGoals > homeGoals
        val draw = homeGoals == awayGoals

        val homePointsDelta = when {
            homeWin -> winPoints
            awayWin -> lossPoints
            else -> drawPoints
        }
        val awayPointsDelta = when {
            awayWin -> winPoints
            homeWin -> lossPoints
            else -> drawPoints
        }

        statsMap[m.home.id] = home.copy(
            played = homePlayed,
            wins = home.wins + if (homeWin) 1 else 0,
            draws = home.draws + if (draw) 1 else 0,
            losses = home.losses + if (awayWin) 1 else 0,
            goalsFor = homeGoalsFor,
            goalsAgainst = homeGoalsAgainst,
            points = home.points + homePointsDelta
        )

        statsMap[m.away.id] = away.copy(
            played = awayPlayed,
            wins = away.wins + if (awayWin) 1 else 0,
            draws = away.draws + if (draw) 1 else 0,
            losses = away.losses + if (homeWin) 1 else 0,
            goalsFor = awayGoalsFor,
            goalsAgainst = awayGoalsAgainst,
            points = away.points + awayPointsDelta
        )
    }

    return statsMap.values.sortedWith(
        compareByDescending<LeagueTeamStats> { it.points }
            .thenByDescending { it.goalDiff }
            .thenByDescending { it.goalsFor }
            .thenBy { it.team.name }
    )
}

private val initialPlayers: List<Player> = listOf(
    Player("Rulo", 5.0, 8.0, 7.0),
    Player("Ariel", 7.9, 8.4, 8.0),
    Player("Diego", 7.3, 7.4, 7.3),
    Player("Jaime", 7.2, 7.5, 7.6),
    Player("Pablo V", 8.0, 8.0, 8.0),
    Player("Carlitos", 7.0, 7.5, 7.5),
    Player("Seba", 7.5, 6.8, 7.6),
    Player("Feña", 6.5, 7.0, 6.7),
    Player("Gustavo (P)", 7.3, 7.3, 7.2),
    Player("Tío Seba", 6.2, 7.0, 6.1),
    Player("Manuel", 7.3, 7.6, 7.6),
    Player("Pablo P", 6.8, 6.6, 7.2),
    Player("Kevin", 7.7, 7.1, 7.0),
    Player("David", 7.2, 6.9, 7.2),
    Player("Benja", 7.3, 7.5, 7.5),
    Player("Juan", 7.1, 7.4, 7.2),
    Player("Marín", 7.2, 7.5, 7.7),
    Player("Felipe Ep", 7.2, 7.0, 7.5),
    Player("Chiqui", 8.8, 7.8, 8.2),
    Player("Bubu", 7.6, 7.2, 7.3),
    Player("Vicho", 8.8, 8.4, 8.8),
    Player("Emilio", 8.8, 7.6, 8.5),
    Player("Jesús", 7.3, 7.3, 7.3),
    Player("Shuvert", 7.3, 7.5, 7.7),
    Player("Gastón", 7.8, 7.5, 8.0),
    Player("Richard", 7.5, 7.5, 7.8),
    Player("Víctor", 7.4, 7.4, 7.4),
    Player("Gustavo Riquelme", 7.1, 7.1, 7.1),
    Player("Navaloco", 6.7, 6.4, 6.3)
)

// ===== Persistencia simple en SharedPreferences (JSON) =====
private const val PREFS_NAME = "equipos_prefs"
private const val KEY_PLAYERS = "players_json"
private const val KEY_PREFS_VERSION = "prefs_version"
private const val PREFS_VERSION = 1
private const val KEY_PRO_UNLOCKED = "pro_unlocked"

private fun isProUnlocked(context: Context): Boolean {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(KEY_PRO_UNLOCKED, false)
}

private fun setProUnlocked(context: Context, unlocked: Boolean) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putBoolean(KEY_PRO_UNLOCKED, unlocked).apply()
}
private const val KEY_MATCHES = "matches_json"
private const val KEY_SAVED_TEAMS = "saved_teams_json"
private const val KEY_TOURNAMENTS = "tournaments_json"
private const val KEY_LEAGUES = "leagues_json"
private const val KEY_ACCESS_TOKEN = "access_token"
private const val KEY_REFRESH_TOKEN = "refresh_token"
private const val KEY_USER_NAME = "user_name"
private const val KEY_COMMUNITY_POSTS = "community_posts_json"
private const val KEY_COMMUNITY_UNREAD = "community_unread_json"
private const val KEY_DARK_MODE = "dark_mode_enabled"

// Remote API
private const val BASE_URL = "https://serverequipos-tvzy.onrender.com"
private val JSON_MEDIA = "application/json; charset=utf-8".toMediaType()
private val httpClient: OkHttpClient by lazy {
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    OkHttpClient.Builder().addInterceptor(logging).build()
}

private fun apiPost(path: String, body: JSONObject, token: String? = null): JSONObject? {
    val req = Request.Builder()
        .url("$BASE_URL$path")
        .post(body.toString().toRequestBody(JSON_MEDIA))
        .apply { if (!token.isNullOrBlank()) header("Authorization", "Bearer $token") }
        .build()
    httpClient.newCall(req).execute().use { resp ->
        if (!resp.isSuccessful) return null
        val text = resp.body?.string() ?: return null
        return JSONObject(text)
    }
}

private fun apiGet(path: String, token: String): JSONObject? {
    val req = Request.Builder().url("$BASE_URL$path").get().header("Authorization", "Bearer $token").build()
    httpClient.newCall(req).execute().use { resp ->
        if (!resp.isSuccessful) return null
        val text = resp.body?.string() ?: return null
        return JSONObject().put("_", 1).apply { put("body", text) }
    }
}

// Migración simple: limpiar jugadores guardados en la primera ejecución de esta versión
private fun ensurePrefsMigrated(context: Context) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val current = prefs.getInt(KEY_PREFS_VERSION, 0)
    if (current < PREFS_VERSION) {
        val editor = prefs.edit()
        editor.remove(KEY_PLAYERS)
        editor.putInt(KEY_PREFS_VERSION, PREFS_VERSION)
        editor.apply()
    }
}

private fun loadPlayers(context: Context): List<Player> {
    ensurePrefsMigrated(context)
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_PLAYERS, null)
    return try {
        if (json.isNullOrBlank()) initialPlayers else jsonToPlayers(json)
    } catch (_: Exception) {
        initialPlayers
    }
}

fun savePlayers(context: Context, players: List<Player>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = playersToJson(players)
    prefs.edit().putString(KEY_PLAYERS, json).apply()
}

fun playersToJson(players: List<Player>): String {
    val arr = JSONArray()
    players.forEach { p ->
        val obj = JSONObject()
        obj.put("name", p.name)
        obj.put("attack", p.attack)
        obj.put("defense", p.defense)
        obj.put("physical", p.physical)
        obj.put("isGoalkeeper", p.isGoalkeeper)
        obj.put("isCaptain", p.isCaptain)
        obj.put("hasYellowCard", p.hasYellowCard)
        obj.put("hasRedCard", p.hasRedCard)
        arr.put(obj)
    }
    return arr.toString()
}

fun jsonToPlayers(json: String): List<Player> {
    val arr = JSONArray(json)
    val list = mutableListOf<Player>()
    for (i in 0 until arr.length()) {
        val obj = arr.getJSONObject(i)
        val name = obj.optString("name")
        val hasAttack = obj.has("attack")
        val hasDefense = obj.has("defense")
        val hasPhysical = obj.has("physical") || obj.has("skill")
        val attack = if (hasAttack) obj.optDouble("attack", 5.0) else obj.optDouble("rating", 5.0)
        val defense = if (hasDefense) obj.optDouble("defense", attack) else obj.optDouble("rating", 5.0)
        val physical = when {
            obj.has("physical") -> obj.optDouble("physical", attack)
            obj.has("skill") -> obj.optDouble("skill", attack)
            else -> obj.optDouble("rating", 5.0)
        }
        val isGK = obj.optBoolean("isGoalkeeper", false)
        val isCaptain = obj.optBoolean("isCaptain", false)
        val hasYellow = obj.optBoolean("hasYellowCard", false)
        val hasRed = obj.optBoolean("hasRedCard", false)
        if (name.isNotBlank()) list += Player(name, attack, defense, physical, isGK, isCaptain, hasYellow, hasRed)
    }
    return list
}

fun saveTeams(context: Context, teams: List<SavedTeam>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val arr = JSONArray()
    teams.forEach { t ->
        val obj = JSONObject()
        obj.put("id", t.id)
        obj.put("name", t.name)
        obj.put("players", JSONArray(playersToJson(t.players)))
        arr.put(obj)
    }
    prefs.edit().putString(KEY_SAVED_TEAMS, arr.toString()).apply()
}

fun loadTeams(context: Context): List<SavedTeam> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_SAVED_TEAMS, null) ?: return emptyList()
    return try {
        val arr = JSONArray(json)
        val list = mutableListOf<SavedTeam>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val id = obj.optLong("id", System.currentTimeMillis())
            val name = obj.optString("name", "")
            val players = jsonToPlayers(obj.getJSONArray("players").toString())
            if (name.isNotBlank()) {
                list += SavedTeam(id, name, players)
            }
        }
        list
    } catch (_: Exception) {
        emptyList()
    }
}

fun loadRecentMatches(context: Context, limit: Int = 100): List<SavedMatch> {
    val all = loadMatches(context)
    return if (all.size <= limit) all else all.take(limit)
}

fun addTeam(context: Context, name: String, players: List<Player>) {
    val current = loadTeams(context).toMutableList()
    val now = System.currentTimeMillis()
    val team = SavedTeam(id = now, name = name, players = players)
    current.add(0, team)
    saveTeams(context, current)
}

fun updateTeam(context: Context, team: SavedTeam) {
    val updated = loadTeams(context).map { if (it.id == team.id) team else it }
    saveTeams(context, updated)
}

fun deleteTeam(context: Context, id: Long) {
    val updated = loadTeams(context).filterNot { it.id == id }
    saveTeams(context, updated)
}

fun saveMatches(context: Context, matches: List<SavedMatch>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val arr = JSONArray()
    matches.forEach { m ->
        val obj = JSONObject()
        obj.put("id", m.id)
        obj.put("time", m.time)
        obj.put("titleA", m.titleA)
        obj.put("titleB", m.titleB)
        obj.put("teamA", JSONArray(playersToJson(m.teamA)))
        obj.put("teamB", JSONArray(playersToJson(m.teamB)))
        obj.put("result", m.result)
        arr.put(obj)
    }
    prefs.edit().putString(KEY_MATCHES, arr.toString()).apply()
}

fun loadMatches(context: Context): List<SavedMatch> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_MATCHES, null) ?: return emptyList()
    return try {
        val arr = JSONArray(json)
        val list = mutableListOf<SavedMatch>()
        for (i in 0 until arr.length()) {
            val obj = arr.optJSONObject(i) ?: continue
            try {
                val id = obj.optLong("id", System.currentTimeMillis())
                val time = obj.optLong("time", id)
                val titleA = obj.optString("titleA", "")
                val titleB = obj.optString("titleB", "")

                val teamAJson = obj.optJSONArray("teamA")?.toString() ?: "[]"
                val teamBJson = obj.optJSONArray("teamB")?.toString() ?: "[]"

                val teamA = try { jsonToPlayers(teamAJson) } catch (_: Exception) { emptyList() }
                val teamB = try { jsonToPlayers(teamBJson) } catch (_: Exception) { emptyList() }

                val result = obj.optString("result", "")
                list += SavedMatch(id, time, titleA, titleB, teamA, teamB, result)
            } catch (_: Exception) {
                // Ignorar entrada corrupta y seguir con las demás
            }
        }
        list
    } catch (_: Exception) {
        emptyList()
    }
}

fun addMatch(context: Context, titleA: String, titleB: String, teamA: List<Player>, teamB: List<Player>) {
    val current = loadMatches(context).toMutableList()
    val now = System.currentTimeMillis()
    val match = SavedMatch(id = now, time = now, titleA = titleA, titleB = titleB, teamA = teamA, teamB = teamB, result = "")
    current.add(0, match)
    saveMatches(context, current)
}

fun deleteMatch(context: Context, id: Long) {
    val current = loadMatches(context)
    val updated = current.filterNot { it.id == id }
    saveMatches(context, updated)
}

fun clearAllMatches(context: Context) {
    saveMatches(context, emptyList())
}

fun updateMatchResult(context: Context, id: Long, result: String) {
    val updated = loadMatches(context).map { if (it.id == id) it.copy(result = result) else it }
    saveMatches(context, updated)
}

fun updateMatch(context: Context, updated: SavedMatch) {
    val list = loadMatches(context).map { if (it.id == updated.id) updated else it }
    saveMatches(context, list)
}

fun saveTournaments(context: Context, tournaments: List<SavedTournament>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val arr = JSONArray()
    tournaments.forEach { t ->
        val obj = JSONObject()
        obj.put("id", t.id)
        obj.put("time", t.time)
        obj.put("name", t.name)
        val teamsArr = JSONArray()
        t.teams.forEach { team ->
            val teamObj = JSONObject()
            teamObj.put("id", team.id)
            teamObj.put("name", team.name)
            teamObj.put("players", JSONArray(playersToJson(team.players)))
            teamsArr.put(teamObj)
        }
        obj.put("teams", teamsArr)
        val roundsArr = JSONArray()
        t.rounds.forEach { round ->
            val roundArr = JSONArray()
            round.forEach { m ->
                val matchObj = JSONObject()
                matchObj.put("id", m.id)
                if (m.teamA != null) {
                    matchObj.put("teamAId", m.teamA.id)
                } else {
                    matchObj.put("teamAId", JSONObject.NULL)
                }
                if (m.teamB != null) {
                    matchObj.put("teamBId", m.teamB.id)
                } else {
                    matchObj.put("teamBId", JSONObject.NULL)
                }
                if (m.winnerId != null) {
                    matchObj.put("winnerId", m.winnerId)
                } else {
                    matchObj.put("winnerId", JSONObject.NULL)
                }
                roundArr.put(matchObj)
            }
            roundsArr.put(roundArr)
        }
        obj.put("rounds", roundsArr)
        if (t.championName != null) {
            obj.put("championName", t.championName)
        }

        if (t.groupStage != null) {
            val gsObj = JSONObject()
            gsObj.put("randomCalendar", t.groupStage.randomCalendar)

            val groupsArr = JSONArray()
            t.groupStage.groups.forEach { groupIds ->
                val groupArr = JSONArray()
                groupIds.forEach { groupArr.put(it) }
                groupsArr.put(groupArr)
            }
            gsObj.put("groups", groupsArr)

            val matchesArr = JSONArray()
            t.groupStage.matches.forEach { m ->
                val mo = JSONObject()
                mo.put("id", m.id)
                mo.put("groupIndex", m.groupIndex)
                mo.put("homeId", m.homeId)
                mo.put("awayId", m.awayId)
                mo.put("homeGoals", m.homeGoals ?: JSONObject.NULL)
                mo.put("awayGoals", m.awayGoals ?: JSONObject.NULL)
                mo.put("homeYellows", m.homeYellows)
                mo.put("awayYellows", m.awayYellows)
                mo.put("homeReds", m.homeReds)
                mo.put("awayReds", m.awayReds)
                matchesArr.put(mo)
            }
            gsObj.put("matches", matchesArr)
            obj.put("groupStage", gsObj)
        }
        arr.put(obj)
    }
    prefs.edit().putString(KEY_TOURNAMENTS, arr.toString()).apply()
}

fun loadTournaments(context: Context): List<SavedTournament> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_TOURNAMENTS, null) ?: return emptyList()
    return try {
        val arr = JSONArray(json)
        val list = mutableListOf<SavedTournament>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val id = obj.optLong("id", System.currentTimeMillis())
            val time = obj.optLong("time", id)
            val name = obj.optString("name", "")
            val teamsArr = obj.optJSONArray("teams") ?: JSONArray()
            val teams = mutableListOf<SavedTeam>()
            for (j in 0 until teamsArr.length()) {
                val teamObj = teamsArr.getJSONObject(j)
                val teamId = teamObj.optLong("id", System.currentTimeMillis())
                val teamName = teamObj.optString("name", "")
                val players = jsonToPlayers(teamObj.getJSONArray("players").toString())
                if (teamName.isNotBlank()) {
                    teams += SavedTeam(teamId, teamName, players)
                }
            }
            val teamsById = teams.associateBy { it.id }
            val roundsArr = obj.optJSONArray("rounds") ?: JSONArray()
            val rounds = mutableListOf<List<TournamentMatch>>()
            for (r in 0 until roundsArr.length()) {
                val roundJson = roundsArr.getJSONArray(r)
                val roundMatches = mutableListOf<TournamentMatch>()
                for (k in 0 until roundJson.length()) {
                    val matchObj = roundJson.getJSONObject(k)
                    val mid = matchObj.optInt("id", k)
                    val teamAId = if (matchObj.isNull("teamAId")) null else matchObj.optLong("teamAId")
                    val teamBId = if (matchObj.isNull("teamBId")) null else matchObj.optLong("teamBId")
                    val winnerId = if (matchObj.isNull("winnerId")) null else matchObj.optLong("winnerId")
                    val teamA = teamAId?.let { teamsById[it] }
                    val teamB = teamBId?.let { teamsById[it] }
                    roundMatches += TournamentMatch(mid, teamA, teamB, winnerId)
                }
                rounds += roundMatches
            }
            val championName = obj.optString("championName", "").ifBlank { null }
            val tournamentName = if (name.isNotBlank()) name else context.getString(R.string.tournament_generic)

            val groupStageObj = obj.optJSONObject("groupStage")
            val groupStage = if (groupStageObj != null) {
                val randomCalendar = groupStageObj.optBoolean("randomCalendar", true)

                val groupsJson = groupStageObj.optJSONArray("groups")
                val groups = mutableListOf<List<Long>>()
                if (groupsJson != null) {
                    for (g in 0 until groupsJson.length()) {
                        val groupArr = groupsJson.optJSONArray(g) ?: JSONArray()
                        val ids = mutableListOf<Long>()
                        for (k in 0 until groupArr.length()) {
                            ids += groupArr.optLong(k)
                        }
                        groups += ids
                    }
                }

                val matchesJson = groupStageObj.optJSONArray("matches")
                val matches = mutableListOf<SavedGroupMatch>()
                if (matchesJson != null) {
                    for (m in 0 until matchesJson.length()) {
                        val mo = matchesJson.getJSONObject(m)
                        matches += SavedGroupMatch(
                            id = mo.optInt("id", m),
                            groupIndex = mo.optInt("groupIndex", 0),
                            homeId = mo.optLong("homeId"),
                            awayId = mo.optLong("awayId"),
                            homeGoals = if (mo.isNull("homeGoals")) null else mo.optInt("homeGoals"),
                            awayGoals = if (mo.isNull("awayGoals")) null else mo.optInt("awayGoals"),
                            homeYellows = mo.optInt("homeYellows", 0),
                            awayYellows = mo.optInt("awayYellows", 0),
                            homeReds = mo.optInt("homeReds", 0),
                            awayReds = mo.optInt("awayReds", 0)
                        )
                    }
                }
                SavedGroupStage(groups = groups, matches = matches, randomCalendar = randomCalendar)
            } else {
                null
            }

            list += SavedTournament(id, time, tournamentName, teams, rounds, championName, groupStage)
        }
        list
    } catch (_: Exception) {
        emptyList()
    }
}

fun addTournament(
    context: Context,
    name: String,
    teams: List<SavedTeam>,
    rounds: List<List<TournamentMatch>>,
    champion: SavedTeam?,
    groupStage: SavedGroupStage? = null
) {
    val current = loadTournaments(context).toMutableList()
    val now = System.currentTimeMillis()
    val tournament = SavedTournament(
        id = now,
        time = now,
        name = name,
        teams = teams,
        rounds = rounds,
        championName = champion?.name,
        groupStage = groupStage
    )
    current.add(0, tournament)
    saveTournaments(context, current)
}

fun saveLeagues(context: Context, leagues: List<SavedLeague>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val arr = JSONArray()
    leagues.forEach { l ->
        val obj = JSONObject()
        obj.put("id", l.id)
        obj.put("time", l.time)
        obj.put("name", l.name)
        obj.put("winPoints", l.winPoints)
        obj.put("drawPoints", l.drawPoints)
        obj.put("lossPoints", l.lossPoints)
        obj.put("randomCalendar", l.randomCalendar)
        val teamsArr = JSONArray()
        l.teams.forEach { team ->
            val teamObj = JSONObject()
            teamObj.put("id", team.id)
            teamObj.put("name", team.name)
            teamObj.put("players", JSONArray(playersToJson(team.players)))
            teamsArr.put(teamObj)
        }
        obj.put("teams", teamsArr)
        val matchesArr = JSONArray()
        l.matches.forEach { m ->
            val matchObj = JSONObject()
            matchObj.put("id", m.id)
            matchObj.put("homeId", m.home.id)
            matchObj.put("awayId", m.away.id)
            if (m.homeGoals != null) matchObj.put("homeGoals", m.homeGoals) else matchObj.put("homeGoals", JSONObject.NULL)
            if (m.awayGoals != null) matchObj.put("awayGoals", m.awayGoals) else matchObj.put("awayGoals", JSONObject.NULL)
            matchObj.put("homeYellows", m.homeYellows)
            matchObj.put("awayYellows", m.awayYellows)
            matchObj.put("homeReds", m.homeReds)
            matchObj.put("awayReds", m.awayReds)
            matchesArr.put(matchObj)
        }
        obj.put("matches", matchesArr)
        arr.put(obj)
    }
    prefs.edit().putString(KEY_LEAGUES, arr.toString()).apply()
}

fun loadLeagues(context: Context): List<SavedLeague> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_LEAGUES, null) ?: return emptyList()
    return try {
        val arr = JSONArray(json)
        val list = mutableListOf<SavedLeague>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val id = obj.optLong("id", System.currentTimeMillis())
            val time = obj.optLong("time", id)
            val name = obj.optString("name", "")

            val teamsArr = obj.optJSONArray("teams") ?: JSONArray()
            val teams = mutableListOf<SavedTeam>()
            for (j in 0 until teamsArr.length()) {
                val teamObj = teamsArr.getJSONObject(j)
                val teamId = teamObj.optLong("id", System.currentTimeMillis())
                val teamName = teamObj.optString("name", "")
                val players = jsonToPlayers(teamObj.getJSONArray("players").toString())
                if (teamName.isNotBlank()) {
                    teams += SavedTeam(teamId, teamName, players)
                }
            }
            val teamsById = teams.associateBy { it.id }

            val matchesArr = obj.optJSONArray("matches") ?: JSONArray()
            val matches = mutableListOf<LeagueMatch>()
            for (k in 0 until matchesArr.length()) {
                val matchObj = matchesArr.getJSONObject(k)
                val mid = matchObj.optInt("id", k)
                val homeId = matchObj.optLong("homeId")
                val awayId = matchObj.optLong("awayId")
                val homeTeam = teamsById[homeId] ?: continue
                val awayTeam = teamsById[awayId] ?: continue
                val homeGoals: Int? = if (matchObj.isNull("homeGoals")) null else matchObj.optInt("homeGoals")
                val awayGoals: Int? = if (matchObj.isNull("awayGoals")) null else matchObj.optInt("awayGoals")
                val homeYellows = matchObj.optInt("homeYellows", 0)
                val awayYellows = matchObj.optInt("awayYellows", 0)
                val homeReds = matchObj.optInt("homeReds", 0)
                val awayReds = matchObj.optInt("awayReds", 0)
                matches += LeagueMatch(
                    id = mid,
                    home = homeTeam,
                    away = awayTeam,
                    homeGoals = homeGoals,
                    awayGoals = awayGoals,
                    homeYellows = homeYellows,
                    awayYellows = awayYellows,
                    homeReds = homeReds,
                    awayReds = awayReds
                )
            }

            val leagueName = if (name.isNotBlank()) name else context.getString(R.string.league_generic)
            val winPoints = obj.optInt("winPoints", 3)
            val drawPoints = obj.optInt("drawPoints", 1)
            val lossPoints = obj.optInt("lossPoints", 0)
            val randomCalendar = obj.optBoolean("randomCalendar", true)
            list += SavedLeague(id, time, leagueName, teams, matches, winPoints, drawPoints, lossPoints, randomCalendar)
        }
        list
    } catch (_: Exception) {
        emptyList()
    }
}

fun addLeague(
    context: Context,
    name: String,
    teams: List<SavedTeam>,
    matches: List<LeagueMatch>,
    winPoints: Int,
    drawPoints: Int,
    lossPoints: Int,
    randomCalendar: Boolean
): SavedLeague {
    val current = loadLeagues(context).toMutableList()
    val now = System.currentTimeMillis()
    val leagueName = if (name.isNotBlank()) name else context.getString(R.string.league_default_name, teams.size)
    val league = SavedLeague(
        id = now,
        time = now,
        name = leagueName,
        teams = teams,
        matches = matches,
        winPoints = winPoints,
        drawPoints = drawPoints,
        lossPoints = lossPoints,
        randomCalendar = randomCalendar
    )
    current.add(0, league)
    saveLeagues(context, current)
    return league
}

fun updateLeague(context: Context, league: SavedLeague): SavedLeague {
    val current = loadLeagues(context).toMutableList()
    val index = current.indexOfFirst { it.id == league.id }
    if (index >= 0) {
        current[index] = league
    } else {
        current.add(0, league)
    }
    saveLeagues(context, current)
    return league
}

@Composable
fun EditSavedTeamDialog(
    allPlayers: List<Player>,
    initialName: String,
    initialPlayers: List<Player>,
    onSave: (String, List<Player>) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember(initialName) { mutableStateOf(initialName) }
    val initialSelected = remember(initialPlayers) { initialPlayers.map { it.name }.toSet() }
    var selectedNames by remember(initialSelected) { mutableStateOf(initialSelected) }
    val canSave = name.trim().isNotEmpty() && selectedNames.isNotEmpty()

    val dialogContainerColor = MaterialTheme.colorScheme.surface
    val dialogTextColor = MaterialTheme.colorScheme.onSurface
    val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = dialogContainerColor,
        titleContentColor = dialogTextColor,
        textContentColor = dialogTextColor,
        title = { Text(stringResource(R.string.team_label), color = dialogTextColor) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.team_name_label_full), color = dialogSecondaryTextColor) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = dialogTextColor,
                        unfocusedTextColor = dialogTextColor,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        cursorColor = dialogTextColor,
                        focusedLabelColor = dialogSecondaryTextColor,
                        unfocusedLabelColor = dialogSecondaryTextColor,
                        focusedPlaceholderColor = dialogSecondaryTextColor,
                        unfocusedPlaceholderColor = dialogSecondaryTextColor
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.players), fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                Spacer(Modifier.height(4.dp))
                LazyColumn(modifier = Modifier.heightIn(max = 260.dp)) {
                    items(allPlayers) { p ->
                        val checked = selectedNames.contains(p.name)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedNames =
                                        if (checked) selectedNames - p.name
                                        else selectedNames + p.name
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = {
                                    selectedNames =
                                        if (it) selectedNames + p.name
                                        else selectedNames - p.name
                                }
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(p.name, color = dialogTextColor)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = canSave,
                onClick = {
                    val trimmed = name.trim()
                    val selectedPlayers = allPlayers.filter { selectedNames.contains(it.name) }
                    onSave(trimmed, selectedPlayers)
                }
            ) {
                Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), color = dialogTextColor)
            }
        }
    )
}

@Composable
fun TournamentBracketDialog(
    teams: List<SavedTeam>,
    initialRounds: List<List<TournamentMatch>>? = null,
    initialGroupStage: SavedGroupStage? = null,
    onDismiss: () -> Unit
) {
    if (teams.isEmpty()) {
        onDismiss()
        return
    }

    val context = LocalContext.current

    val dialogTextColor = MaterialTheme.colorScheme.onSurface
    val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    var rounds by remember(teams, initialRounds) { mutableStateOf(initialRounds ?: emptyList()) }
    var champion by remember(teams, initialRounds) { mutableStateOf<SavedTeam?>(null) }
    var showSaveTournamentDialog by remember { mutableStateOf(false) }
    val defaultTournamentName = stringResource(R.string.tournament_default_name, teams.size)
    var tournamentNameInput by remember(teams.size, initialRounds) { mutableStateOf(defaultTournamentName) }
    var showTournamentFormatDialog by remember { mutableStateOf(initialRounds == null && initialGroupStage == null) }
    var showTournamentSetupDialog by remember { mutableStateOf(false) }
    var setupDone by remember { mutableStateOf(initialRounds != null && (initialRounds.isNotEmpty())) }
    var showTournamentPickerDialog by remember { mutableStateOf(false) }
    var randomBracket by remember { mutableStateOf(true) }
    var tournamentSlots by remember(teams, initialRounds) { mutableStateOf(teams) }
    var useGroupStage by remember { mutableStateOf(false) }
    var showGroupSetupDialog by remember { mutableStateOf(false) }
    var showGroupPickerDialog by remember { mutableStateOf(false) }
    var groupSlots by remember(teams) { mutableStateOf(teams) }
    var groups by remember(teams) { mutableStateOf<List<List<SavedTeam>>>(emptyList()) }
    var groupCalendarRandom by remember { mutableStateOf(true) }
    var showGroupStageDialog by remember { mutableStateOf(false) }
    var groupMatches by remember { mutableStateOf<List<List<LeagueMatch>>>(emptyList()) }
    var editingGroupMatch by remember { mutableStateOf<LeagueMatch?>(null) }
    var editingGroupIndex by remember { mutableStateOf<Int?>(null) }
    val totalRounds = rounds.size

    val maxTeamsAllowed = if (isProUnlocked(context)) 64 else 20

    val groupCount = remember(teams.size) { teams.size / 4 }

    fun groupTitle(index: Int): String {
        val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val label = if (index in letters.indices) letters[index].toString() else (index + 1).toString()
        return "Grupo $label"
    }

    fun buildSavedGroupStage(): SavedGroupStage? {
        if (!useGroupStage) return null
        if (groups.isEmpty() || groupMatches.isEmpty()) return null
        val groupsIds = groups.map { g -> g.map { it.id } }
        val matches = groupMatches.flatMapIndexed { gi, ms ->
            ms.map { m ->
                SavedGroupMatch(
                    id = m.id,
                    groupIndex = gi,
                    homeId = m.home.id,
                    awayId = m.away.id,
                    homeGoals = m.homeGoals,
                    awayGoals = m.awayGoals,
                    homeYellows = m.homeYellows,
                    awayYellows = m.awayYellows,
                    homeReds = m.homeReds,
                    awayReds = m.awayReds
                )
            }
        }
        return SavedGroupStage(groups = groupsIds, matches = matches, randomCalendar = groupCalendarRandom)
    }

    fun restoreGroupStageFromSaved(saved: SavedGroupStage) {
        useGroupStage = true
        groupCalendarRandom = saved.randomCalendar
        val teamsById = teams.associateBy { it.id }
        val restoredGroups = saved.groups.map { ids -> ids.mapNotNull { teamsById[it] } }
        groups = restoredGroups
        groupSlots = restoredGroups.flatten()
        val groupedMatches = MutableList(restoredGroups.size) { mutableListOf<LeagueMatch>() }
        saved.matches.forEach { sm ->
            val home = teamsById[sm.homeId] ?: return@forEach
            val away = teamsById[sm.awayId] ?: return@forEach
            val gi = sm.groupIndex.coerceIn(0, groupedMatches.lastIndex)
            groupedMatches[gi] += LeagueMatch(
                id = sm.id,
                home = home,
                away = away,
                homeGoals = sm.homeGoals,
                awayGoals = sm.awayGoals,
                homeYellows = sm.homeYellows,
                awayYellows = sm.awayYellows,
                homeReds = sm.homeReds,
                awayReds = sm.awayReds
            )
        }
        groupMatches = groupedMatches.map { it.toList() }
    }

    LaunchedEffect(initialGroupStage) {
        if (initialGroupStage != null && groups.isEmpty()) {
            restoreGroupStageFromSaved(initialGroupStage)
            if (rounds.isEmpty()) {
                setupDone = false
                showGroupStageDialog = true
            }
        }
    }

    fun buildSemifinalBracket(a1: SavedTeam, a2: SavedTeam, b1: SavedTeam, b2: SavedTeam): List<List<TournamentMatch>> {
        val semi = listOf(
            TournamentMatch(id = 0, teamA = a1, teamB = b2),
            TournamentMatch(id = 1, teamA = b1, teamB = a2)
        )
        val final = listOf(TournamentMatch(id = 2, teamA = null, teamB = null))
        return listOf(semi, final)
    }

    if (showTournamentFormatDialog) {
        var groupsSelected by remember { mutableStateOf(false) }
        var setupError by remember { mutableStateOf<String?>(null) }
        AlertDialog(
            onDismissRequest = {
                showTournamentFormatDialog = false
                onDismiss()
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.tournament_setup_title), color = dialogTextColor) },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Formato", color = dialogTextColor, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                setupError = null
                                groupsSelected = false
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = !groupsSelected, onClick = {
                            setupError = null
                            groupsSelected = false
                        })
                        Spacer(Modifier.width(6.dp))
                        Text("Eliminación directa", color = dialogTextColor)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                setupError = null
                                groupsSelected = true
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = groupsSelected, onClick = {
                            setupError = null
                            groupsSelected = true
                        })
                        Spacer(Modifier.width(6.dp))
                        Text("Fase de grupos", color = dialogTextColor)
                    }

                    if (setupError != null) {
                        Text(setupError!!, color = Color(0xFFFF5252))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (groupsSelected) {
                            if (teams.size < 4 || teams.size > maxTeamsAllowed || teams.size % 4 != 0) {
                                setupError = "La fase de grupos está disponible sólo para múltiplos de 4 (hasta $maxTeamsAllowed equipos)"
                                return@Button
                            }
                            useGroupStage = true
                            groupSlots = teams
                            groups = emptyList()
                            groupMatches = emptyList()
                            champion = null
                            setupDone = false
                            showTournamentFormatDialog = false
                            showGroupSetupDialog = true
                        } else {
                            useGroupStage = false
                            showTournamentFormatDialog = false
                            showTournamentSetupDialog = true
                        }
                    }
                ) {
                    Text(stringResource(R.string.continue_action), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTournamentFormatDialog = false
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.cancel), color = dialogTextColor)
                }
            }
        )
    }

    if (showTournamentSetupDialog) {
        var randomSelected by remember { mutableStateOf(true) }
        AlertDialog(
            onDismissRequest = {
                showTournamentSetupDialog = false
                onDismiss()
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.tournament_setup_title), color = dialogTextColor) },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(stringResource(R.string.bracket_label), color = dialogTextColor, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { randomSelected = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = randomSelected, onClick = { randomSelected = true })
                        Spacer(Modifier.width(6.dp))
                        Text(stringResource(R.string.random_option), color = dialogTextColor)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { randomSelected = false },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = !randomSelected, onClick = { randomSelected = false })
                        Spacer(Modifier.width(6.dp))
                        Text(stringResource(R.string.manual_option), color = dialogTextColor)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        randomBracket = randomSelected
                        if (randomSelected) {
                            rounds = if (isPowerOfTwo(teams.size)) {
                                buildTournamentBracket(teams, randomBracket = true)
                            } else {
                                buildTournamentBracketWithByes(teams.shuffled())
                            }
                            champion = null
                            setupDone = true
                            showTournamentSetupDialog = false
                        } else {
                            tournamentSlots = teams
                            champion = null
                            showTournamentSetupDialog = false
                            showTournamentPickerDialog = true
                        }
                    }
                ) {
                    Text(stringResource(R.string.continue_action), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTournamentSetupDialog = false
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.cancel), color = dialogTextColor)
                }
            }
        )
    }

    if (showGroupSetupDialog) {
        var randomSelected by remember { mutableStateOf(true) }
        AlertDialog(
            onDismissRequest = {
                showGroupSetupDialog = false
                showTournamentFormatDialog = true
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text("Fase de grupos", color = dialogTextColor) },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Grupos", color = dialogTextColor, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { randomSelected = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = randomSelected, onClick = { randomSelected = true })
                        Spacer(Modifier.width(6.dp))
                        Text(stringResource(R.string.random_option), color = dialogTextColor)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { randomSelected = false },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = !randomSelected, onClick = { randomSelected = false })
                        Spacer(Modifier.width(6.dp))
                        Text(stringResource(R.string.manual_option), color = dialogTextColor)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (randomSelected) {
                            groupCalendarRandom = true
                            val shuffled = teams.shuffled()
                            groupSlots = shuffled
                            groups = shuffled.chunked(4)
                            groupMatches = groups.mapIndexed { index, g ->
                                buildSingleLegLeagueMatchesFromRounds(
                                    generateLeagueRounds(g, randomCalendar = true),
                                    startId = index * 1000
                                )
                            }
                            showGroupSetupDialog = false
                            showGroupStageDialog = true
                        } else {
                            groupCalendarRandom = false
                            groupSlots = teams
                            showGroupSetupDialog = false
                            showGroupPickerDialog = true
                        }
                    }
                ) {
                    Text(stringResource(R.string.continue_action), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showGroupSetupDialog = false
                        showTournamentFormatDialog = true
                    }
                ) {
                    Text(stringResource(R.string.cancel), color = dialogTextColor)
                }
            }
        )
    }

    if (showGroupPickerDialog) {
        var selectedSlotIndex by remember { mutableStateOf<Int?>(null) }
        AlertDialog(
            onDismissRequest = {
                showGroupPickerDialog = false
                showGroupSetupDialog = true
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text("Grupos a elección", color = dialogTextColor) },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    val centerDividerIndex = groupCount / 2
                    val useDividerBetweenAllGroups = groupCount >= 4
                    val dividerWidth = 12.dp
                    val dividerColor = MaterialTheme.colorScheme.outlineVariant

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                groups = groupSlots.chunked(4)
                                groupMatches = groups.mapIndexed { index, g ->
                                    buildSingleLegLeagueMatchesFromRounds(
                                        generateLeagueRounds(g, randomCalendar = groupCalendarRandom),
                                        startId = index * 1000
                                    )
                                }
                                showSaveTournamentDialog = true
                            }
                        ) {
                            Text(stringResource(R.string.save_tournament), color = dialogTextColor)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (g in 0 until groupCount) {
                            if (g != 0 && (useDividerBetweenAllGroups || g == centerDividerIndex)) {
                                Box(
                                    modifier = Modifier
                                        .width(dividerWidth)
                                        .height(24.dp)
                                        .background(dividerColor, RoundedCornerShape(999.dp))
                                )
                            }

                            Text(
                                text = "Grupo ${g + 1}",
                                color = dialogSecondaryTextColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 360.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed((0 until 4).toList()) { rowIndex, _ ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    for (g in 0 until groupCount) {
                                        if (g != 0 && (useDividerBetweenAllGroups || g == centerDividerIndex)) {
                                            Box(
                                                modifier = Modifier
                                                    .width(dividerWidth)
                                                    .fillMaxHeight()
                                                    .background(dividerColor, RoundedCornerShape(999.dp))
                                            )
                                        }

                                        val index = g * 4 + rowIndex
                                        val selected = selectedSlotIndex == index
                                        val team = groupSlots.getOrNull(index)
                                        OutlinedButton(
                                            onClick = {
                                                selectedSlotIndex = if (selectedSlotIndex == null) {
                                                    index
                                                } else {
                                                    val a = selectedSlotIndex!!
                                                    val b = index
                                                    groupSlots = swapTournamentSlots(groupSlots, a, b)
                                                    null
                                                }
                                            },
                                            modifier = Modifier.weight(1f),
                                            border = BorderStroke(2.dp, if (selected) GrassGreen else dialogTextColor)
                                        ) {
                                            Text(team?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        groups = groupSlots.chunked(4)
                        groupMatches = groups.mapIndexed { index, g ->
                            buildSingleLegLeagueMatchesFromRounds(
                                generateLeagueRounds(g, randomCalendar = groupCalendarRandom),
                                startId = index * 1000
                            )
                        }
                        showGroupPickerDialog = false
                        showGroupStageDialog = true
                    }
                ) {
                    Text(stringResource(R.string.done), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showGroupPickerDialog = false
                        showGroupSetupDialog = true
                    }
                ) {
                    Text(stringResource(R.string.cancel), color = dialogTextColor)
                }
            }
        )
    }

    if (showGroupStageDialog) {
        val winPoints = 3
        val drawPoints = 1
        val lossPoints = 0

        val tables = remember(groups, groupMatches) {
            groups.mapIndexed { i, g ->
                computeLeagueTable(g, groupMatches.getOrNull(i) ?: emptyList(), winPoints, drawPoints, lossPoints)
            }
        }

        val isFinishedByGroup = remember(groupMatches) {
            groupMatches.map { ms -> ms.isNotEmpty() && ms.all { it.homeGoals != null && it.awayGoals != null } }
        }

        val canAdvance = isFinishedByGroup.all { it } && tables.all { it.size >= 2 }

        var showGroupCalendar by remember { mutableStateOf(true) }
        var showGroupTable by remember { mutableStateOf(true) }

        BackHandler {
            showGroupStageDialog = false
            showGroupSetupDialog = true
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 72.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            showGroupStageDialog = false
                            showGroupSetupDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = dialogTextColor
                        )
                    }
                    Text(
                        text = "Fase de grupos",
                        style = MaterialTheme.typography.titleLarge,
                        color = dialogTextColor
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showGroupCalendar = !showGroupCalendar },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (showGroupCalendar) stringResource(R.string.hide_calendar) else stringResource(R.string.show_calendar),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Button(
                        onClick = { showGroupTable = !showGroupTable },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (showGroupTable) stringResource(R.string.hide_table) else stringResource(R.string.show_table),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Button(
                        onClick = { showSaveTournamentDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(R.string.save_tournament),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Cargá todos los resultados para definir los clasificados.",
                    style = MaterialTheme.typography.bodySmall,
                    color = dialogSecondaryTextColor
                )
                Spacer(Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    @Composable
                    fun groupSection(title: String, groupTeams: List<SavedTeam>, matches: List<LeagueMatch>, table: List<LeagueTeamStats>) {
                        Text(title, color = dialogTextColor, fontWeight = FontWeight.SemiBold)

                        if (groupTeams.isEmpty()) {
                            Text("Sin equipos", color = dialogSecondaryTextColor)
                            return
                        }

                        if (showGroupTable) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(stringResource(R.string.rank_short), modifier = Modifier.width(24.dp), color = dialogSecondaryTextColor, fontSize = 12.sp)
                                        Text(stringResource(R.string.team_label), modifier = Modifier.weight(1f), color = dialogSecondaryTextColor, fontSize = 12.sp)
                                        Text(stringResource(R.string.points_short), modifier = Modifier.width(40.dp), color = dialogSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                        Text(stringResource(R.string.played_short), modifier = Modifier.width(36.dp), color = dialogSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                        Text(stringResource(R.string.goal_diff_short), modifier = Modifier.width(40.dp), color = dialogSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    table.forEachIndexed { index, s ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 2.dp),
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text((index + 1).toString(), modifier = Modifier.width(24.dp), color = dialogTextColor, fontSize = 12.sp)
                                            Text(s.team.name, modifier = Modifier.weight(1f), color = dialogTextColor, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            Text(s.points.toString(), modifier = Modifier.width(40.dp), color = dialogTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                            Text(s.played.toString(), modifier = Modifier.width(36.dp), color = dialogTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                            Text(s.goalDiff.toString(), modifier = Modifier.width(40.dp), color = dialogTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                        }
                                    }
                                }
                            }
                        }

                        if (showGroupCalendar) {
                            Spacer(Modifier.height(8.dp))
                            Text(stringResource(R.string.match_calendar_title), color = dialogTextColor, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))

                            val matchesPerFecha = maxOf(1, groupTeams.size / 2)
                            matches.forEachIndexed { index, m ->
                                val fecha = (index / matchesPerFecha) + 1
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    tonalElevation = 2.dp,
                                    color = MaterialTheme.colorScheme.surface
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.matchday_format, fecha),
                                            color = dialogSecondaryTextColor,
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            text = m.home.name + " vs " + m.away.name,
                                            color = dialogTextColor,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        val goalsText = if (m.homeGoals != null && m.awayGoals != null) {
                                            m.homeGoals.toString() + " - " + m.awayGoals.toString()
                                        } else {
                                            stringResource(R.string.no_result)
                                        }
                                        Text(
                                            text = stringResource(R.string.result_with_value, goalsText),
                                            color = dialogSecondaryTextColor,
                                            fontSize = 12.sp
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            TextButton(onClick = {
                                                editingGroupMatch = m
                                            }) {
                                                Text(stringResource(R.string.edit), color = dialogTextColor)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    groups.forEachIndexed { i, g ->
                        val ms = groupMatches.getOrNull(i) ?: emptyList()
                        val table = tables.getOrNull(i) ?: emptyList()
                        groupSection(groupTitle(i), g, ms, table)
                    }
                }

                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            showGroupStageDialog = false
                            showGroupSetupDialog = true
                        }
                    ) {
                        Text(stringResource(R.string.back), color = dialogTextColor)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        enabled = canAdvance,
                        onClick = {
                            val topTwo = tables.map { it.take(2) }
                            val qualifiers = topTwo.flatMap { it }.map { it.team }

                            rounds = when (groups.size) {
                                1 -> buildTournamentBracketWithByes(listOf(qualifiers[0], qualifiers[1]))
                                2 -> {
                                    val a1 = topTwo[0][0].team
                                    val a2 = topTwo[0][1].team
                                    val b1 = topTwo[1][0].team
                                    val b2 = topTwo[1][1].team
                                    buildSemifinalBracket(a1, a2, b1, b2)
                                }
                                4 -> {
                                    val ordered = listOf(
                                        topTwo[0][0].team, topTwo[1][1].team,
                                        topTwo[1][0].team, topTwo[0][1].team,
                                        topTwo[2][0].team, topTwo[3][1].team,
                                        topTwo[3][0].team, topTwo[2][1].team
                                    )
                                    buildTournamentBracketWithByes(ordered)
                                }
                                else -> {
                                    val winners = topTwo.map { it[0] }.sortedWith(
                                        compareByDescending<LeagueTeamStats> { it.points }
                                            .thenByDescending { it.goalDiff }
                                            .thenByDescending { it.goalsFor }
                                            .thenBy { it.team.name }
                                    )
                                    val runners = topTwo.map { it[1] }.sortedWith(
                                        compareByDescending<LeagueTeamStats> { it.points }
                                            .thenByDescending { it.goalDiff }
                                            .thenByDescending { it.goalsFor }
                                            .thenBy { it.team.name }
                                    )
                                    buildTournamentBracketWithByes((winners + runners).map { it.team })
                                }
                            }
                            champion = null
                            setupDone = true
                            showGroupStageDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.continue_action), color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }

    if (editingGroupMatch != null) {
        val m = editingGroupMatch!!
        var homeGoalsText by remember(m.id) { mutableStateOf(m.homeGoals?.toString() ?: "") }
        var awayGoalsText by remember(m.id) { mutableStateOf(m.awayGoals?.toString() ?: "") }
        var homeYellowsText by remember(m.id) { mutableStateOf(m.homeYellows.toString()) }
        var awayYellowsText by remember(m.id) { mutableStateOf(m.awayYellows.toString()) }
        var homeRedsText by remember(m.id) { mutableStateOf(m.homeReds.toString()) }
        var awayRedsText by remember(m.id) { mutableStateOf(m.awayReds.toString()) }

        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val fieldColors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = dialogTextColor,
            unfocusedTextColor = dialogTextColor,
            disabledTextColor = dialogTextColor,
            cursorColor = dialogTextColor,
            focusedLabelColor = dialogTextColor,
            unfocusedLabelColor = dialogTextColor,
            focusedBorderColor = dialogTextColor,
            unfocusedBorderColor = dialogTextColor
        )

        Dialog(onDismissRequest = { editingGroupMatch = null }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = m.home.name + " vs " + m.away.name,
                        color = dialogTextColor,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(stringResource(R.string.goals), color = dialogTextColor)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = homeGoalsText,
                            onValueChange = { homeGoalsText = it.filter { c -> c.isDigit() } },
                            label = { Text(m.home.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = fieldColors,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = awayGoalsText,
                            onValueChange = { awayGoalsText = it.filter { c -> c.isDigit() } },
                            label = { Text(m.away.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = fieldColors,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Text(stringResource(R.string.yellow_cards), color = dialogTextColor)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = homeYellowsText,
                            onValueChange = { homeYellowsText = it.filter { c -> c.isDigit() } },
                            label = { Text(m.home.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = fieldColors,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = awayYellowsText,
                            onValueChange = { awayYellowsText = it.filter { c -> c.isDigit() } },
                            label = { Text(m.away.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = fieldColors,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Text(stringResource(R.string.red_cards), color = dialogTextColor)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = homeRedsText,
                            onValueChange = { homeRedsText = it.filter { c -> c.isDigit() } },
                            label = { Text(m.home.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = fieldColors,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = awayRedsText,
                            onValueChange = { awayRedsText = it.filter { c -> c.isDigit() } },
                            label = { Text(m.away.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = fieldColors,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { editingGroupMatch = null }) {
                            Text(stringResource(R.string.cancel), color = dialogTextColor)
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val newHomeGoals = homeGoalsText.toIntOrNull()
                                val newAwayGoals = awayGoalsText.toIntOrNull()
                                val updated = m.copy(
                                    homeGoals = newHomeGoals,
                                    awayGoals = newAwayGoals,
                                    homeYellows = homeYellowsText.toIntOrNull() ?: 0,
                                    awayYellows = awayYellowsText.toIntOrNull() ?: 0,
                                    homeReds = homeRedsText.toIntOrNull() ?: 0,
                                    awayReds = awayRedsText.toIntOrNull() ?: 0
                                )

                                val idx = groupMatches.indexOfFirst { ms -> ms.any { it.id == updated.id } }
                                if (idx >= 0) {
                                    groupMatches = groupMatches.mapIndexed { i, ms ->
                                        if (i == idx) ms.map { if (it.id == updated.id) updated else it } else ms
                                    }
                                }
                                editingGroupMatch = null
                            }
                        ) {
                            Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }
        }
    }

    if (showTournamentPickerDialog) {
        var selectedSlotIndex by remember { mutableStateOf<Int?>(null) }
        AlertDialog(
            onDismissRequest = {
                showTournamentPickerDialog = false
                showTournamentSetupDialog = true
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.manual_bracket_title), color = dialogTextColor) },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                rounds = buildTournamentBracketWithByes(tournamentSlots)
                                champion = null
                                setupDone = false
                                showTournamentPickerDialog = false
                                showSaveTournamentDialog = true
                            }
                        ) {
                            Text(stringResource(R.string.save_tournament), color = dialogTextColor)
                        }
                    }

                    val pairs = tournamentSlots.chunked(2)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 360.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(pairs) { pairIndex, chunk ->
                            val leftIndex = pairIndex * 2
                            val rightIndex = pairIndex * 2 + 1
                            val leftSelected = selectedSlotIndex == leftIndex
                            val rightSelected = selectedSlotIndex == rightIndex
                            val leftTeam = chunk.getOrNull(0)
                            val rightTeam = chunk.getOrNull(1)

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            selectedSlotIndex = if (selectedSlotIndex == null) {
                                                leftIndex
                                            } else {
                                                val a = selectedSlotIndex!!
                                                val b = leftIndex
                                                tournamentSlots = swapTournamentSlots(tournamentSlots, a, b)
                                                null
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        border = BorderStroke(2.dp, if (leftSelected) GrassGreen else dialogTextColor)
                                    ) {
                                        Text(leftTeam?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }

                                    Text(stringResource(R.string.vs), color = dialogTextColor)

                                    OutlinedButton(
                                        onClick = {
                                            selectedSlotIndex = if (selectedSlotIndex == null) {
                                                rightIndex
                                            } else {
                                                val a = selectedSlotIndex!!
                                                val b = rightIndex
                                                tournamentSlots = swapTournamentSlots(tournamentSlots, a, b)
                                                null
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        border = BorderStroke(2.dp, if (rightSelected) GrassGreen else dialogTextColor)
                                    ) {
                                        Text(rightTeam?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        rounds = buildTournamentBracketWithByes(tournamentSlots)
                        champion = null
                        setupDone = true
                        showTournamentPickerDialog = false
                    }
                ) {
                    Text(stringResource(R.string.done), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTournamentPickerDialog = false
                        showTournamentSetupDialog = true
                    }
                ) {
                    Text(stringResource(R.string.cancel), color = dialogTextColor)
                }
            }
        )
    }

    if (setupDone && rounds.isNotEmpty()) {
        BackHandler(onBack = onDismiss)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 96.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = dialogTextColor
                        )
                    }
                    Text(
                        text = stringResource(R.string.tournament),
                        style = MaterialTheme.typography.titleLarge,
                        color = dialogTextColor,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Button(
                        onClick = { showSaveTournamentDialog = true },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.save_tournament),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = onDismiss,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.close),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.tournament_tap_team_instruction),
                        style = MaterialTheme.typography.bodySmall,
                        color = dialogTextColor
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.teams_rounds_format, teams.size, totalRounds),
                        style = MaterialTheme.typography.bodySmall,
                        color = dialogSecondaryTextColor
                    )
                    Spacer(Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rounds.forEachIndexed { roundIndex, matches ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val title = when {
                                        rounds.size == 1 -> stringResource(R.string.round_final)
                                        roundIndex == rounds.lastIndex -> stringResource(R.string.round_final)
                                        roundIndex == rounds.lastIndex - 1 -> stringResource(R.string.round_semifinal)
                                        roundIndex == 0 -> stringResource(R.string.round_of_16_or_quarters)
                                        else -> stringResource(R.string.round_format, roundIndex + 1)
                                    }
                                    Text(title, color = dialogTextColor, fontWeight = FontWeight.SemiBold)

                                    matches.forEachIndexed { matchIndex, match ->
                                        TournamentMatchCard(
                                            match = match,
                                            onPickWinner = { winner ->
                                                val updated = rounds.mapIndexed { rIndex, roundMatches ->
                                                    if (rIndex == roundIndex) {
                                                        roundMatches.mapIndexed { mIndex, m ->
                                                            if (mIndex == matchIndex) m.copy(winnerId = winner.id) else m
                                                        }
                                                    } else {
                                                        roundMatches
                                                    }
                                                }.toMutableList()

                                                if (roundIndex + 1 < updated.size) {
                                                    val nextMatchIndex = matchIndex / 2
                                                    val isA = matchIndex % 2 == 0
                                                    val nextRound = updated[roundIndex + 1].toMutableList()
                                                    val next = nextRound[nextMatchIndex]
                                                    val newNext = if (isA) {
                                                        next.copy(teamA = winner)
                                                    } else {
                                                        next.copy(teamB = winner)
                                                    }
                                                    nextRound[nextMatchIndex] = newNext
                                                    updated[roundIndex + 1] = nextRound
                                                }
                                                rounds = updated

                                                if (roundIndex == updated.lastIndex) {
                                                    val finalRound = updated.lastOrNull()
                                                    val finalMatch = finalRound?.getOrNull(matchIndex)
                                                    val winnerTeam = when (finalMatch?.winnerId) {
                                                        finalMatch?.teamA?.id -> finalMatch?.teamA
                                                        finalMatch?.teamB?.id -> finalMatch?.teamB
                                                        else -> null
                                                    }
                                                    if (winnerTeam != null) {
                                                        champion = winnerTeam
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    champion?.let { champ ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 190.dp)
                                .background(Color(0xFF1B5E20), RoundedCornerShape(20.dp))
                                .padding(vertical = 22.dp, horizontal = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CelebrationConfetti(
                                modifier = Modifier.matchParentSize(),
                                triggerKey = champ.id
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("🎉", fontSize = 24.sp)
                                    GoldenCupTrophy(modifier = Modifier.size(68.dp))
                                    Text("🎉", fontSize = 24.sp)
                                }
                                Text(
                                    text = stringResource(R.string.champion_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Text(
                                    text = champ.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))
                    }
                }
                if (showSaveTournamentDialog) {
                    val canSave = tournamentNameInput.trim().isNotBlank()
                    AlertDialog(
                        onDismissRequest = { showSaveTournamentDialog = false },
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = dialogTextColor,
                        textContentColor = dialogTextColor,
                        title = { Text(stringResource(R.string.tournament_name_title), color = dialogTextColor) },
                        text = {
                            TextField(
                                value = tournamentNameInput,
                                onValueChange = { tournamentNameInput = it },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        confirmButton = {
                            Button(
                                enabled = canSave,
                                onClick = {
                                    val name = tournamentNameInput.trim()
                                    addTournament(context, name, teams, rounds, champion, groupStage = buildSavedGroupStage())
                                    showSaveTournamentDialog = false
                                }
                            ) {
                                Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.onPrimary)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSaveTournamentDialog = false }) {
                                Text(stringResource(R.string.cancel), color = dialogTextColor)
                            }
                        }
                    )
                }
        }
    } else if (!showGroupStageDialog) {
        BackHandler(onBack = onDismiss)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }

    if (showSaveTournamentDialog && !(setupDone && rounds.isNotEmpty())) {
        val canSave = tournamentNameInput.trim().isNotBlank()
        AlertDialog(
            onDismissRequest = { showSaveTournamentDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.tournament_name_title), color = dialogTextColor) },
            text = {
                TextField(
                    value = tournamentNameInput,
                    onValueChange = { tournamentNameInput = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    enabled = canSave,
                    onClick = {
                        val name = tournamentNameInput.trim()
                        addTournament(context, name, teams, rounds, champion, groupStage = buildSavedGroupStage())
                        showSaveTournamentDialog = false
                    }
                ) {
                    Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveTournamentDialog = false }) {
                    Text(stringResource(R.string.cancel), color = dialogTextColor)
                }
            }
        )
    }
}

@Composable
private fun TournamentMatchCard(
    match: TournamentMatch,
    onPickWinner: (SavedTeam) -> Unit
) {
    val cardColor = MaterialTheme.colorScheme.surface
    val cardTextColor = MaterialTheme.colorScheme.onSurface
    Surface(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp,
        color = cardColor
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            @Composable
            fun teamRow(team: SavedTeam?, isTop: Boolean) {
                val selected = team != null && match.winnerId == team.id
                val background = if (selected) GrassGreenDark else MaterialTheme.colorScheme.surfaceVariant
                val label = team?.name ?: "Pendiente"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background, RoundedCornerShape(if (isTop) 8.dp else 0.dp))
                        .clickable(enabled = team != null) {
                            if (team != null) onPickWinner(team)
                        }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = label,
                        color = cardTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 13.sp
                    )
                }
            }

            teamRow(match.teamA, true)
            Spacer(Modifier.height(4.dp))
            teamRow(match.teamB, false)
        }
    }
}

@Composable
private fun GoldenCupTrophy(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val gold = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFF5B1),
                Color(0xFFFFD95B),
                Color(0xFFF3B31D),
                Color(0xFFD88B00)
            )
        )
        val darkGold = Color(0xFF9D5E00)

        val handleStroke = Stroke(
            width = w * 0.07f,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        // Handles joined to the cup at top and lower side.
        val leftHandle = Path().apply {
            moveTo(w * 0.31f, h * 0.22f)
            cubicTo(w * 0.09f, h * 0.06f, w * 0.02f, h * 0.45f, w * 0.31f, h * 0.62f)
        }
        drawPath(path = leftHandle, brush = gold, style = handleStroke)

        val rightHandle = Path().apply {
            moveTo(w * 0.69f, h * 0.22f)
            cubicTo(w * 0.91f, h * 0.06f, w * 0.98f, h * 0.45f, w * 0.69f, h * 0.62f)
        }
        drawPath(path = rightHandle, brush = gold, style = handleStroke)

        // Rim
        drawRoundRect(
            brush = gold,
            topLeft = Offset(w * 0.21f, h * 0.17f),
            size = Size(w * 0.58f, h * 0.11f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
        )
        drawRoundRect(
            color = darkGold.copy(alpha = 0.35f),
            topLeft = Offset(w * 0.21f, h * 0.25f),
            size = Size(w * 0.58f, h * 0.022f),
            cornerRadius = CornerRadius(w * 0.02f, w * 0.02f)
        )

        // Cup body
        val body = Path().apply {
            moveTo(w * 0.31f, h * 0.27f)
            quadraticTo(w * 0.50f, h * 0.22f, w * 0.69f, h * 0.27f)
            quadraticTo(w * 0.67f, h * 0.55f, w * 0.56f, h * 0.70f)
            lineTo(w * 0.44f, h * 0.70f)
            quadraticTo(w * 0.33f, h * 0.55f, w * 0.31f, h * 0.27f)
            close()
        }
        drawPath(path = body, brush = gold)
        drawPath(path = body, color = darkGold.copy(alpha = 0.22f), style = Stroke(width = w * 0.015f))

        drawOval(
            color = Color.White.copy(alpha = 0.25f),
            topLeft = Offset(w * 0.36f, h * 0.30f),
            size = Size(w * 0.10f, h * 0.33f)
        )

        // Stem and base
        drawRoundRect(
            brush = gold,
            topLeft = Offset(w * 0.44f, h * 0.67f),
            size = Size(w * 0.12f, h * 0.12f),
            cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
        )
        drawOval(
            brush = gold,
            topLeft = Offset(w * 0.32f, h * 0.76f),
            size = Size(w * 0.36f, h * 0.10f)
        )
        drawOval(
            brush = gold,
            topLeft = Offset(w * 0.22f, h * 0.84f),
            size = Size(w * 0.56f, h * 0.12f)
        )
        drawOval(
            color = darkGold.copy(alpha = 0.30f),
            topLeft = Offset(w * 0.22f, h * 0.90f),
            size = Size(w * 0.56f, h * 0.04f)
        )
    }
}

@Composable
private fun CelebrationConfetti(
    modifier: Modifier = Modifier,
    triggerKey: Any? = Unit,
    pieceCount: Int = 70
) {
    val progress = remember { Animatable(0f) }
    val colors = remember {
        listOf(
            Color(0xFFFFF176),
            Color(0xFFFF8A65),
            Color(0xFF4FC3F7),
            Color(0xFFCE93D8),
            Color(0xFFA5D6A7),
            Color(0xFFFFD54F)
        )
    }

    LaunchedEffect(triggerKey) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2400, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier) {
        repeat(pieceCount) { index ->
            val spawn = ((index * 17) % 100) / 100f
            val localProgress = ((progress.value - spawn * 0.45f) / 0.55f).coerceIn(0f, 1f)
            if (localProgress <= 0f) return@repeat

            val xSeed = ((index * 73) % 100) / 100f
            val driftSeed = (((index * 29) % 100) / 100f) - 0.5f
            val sway = sin((localProgress * 6f + index * 0.7f).toDouble()).toFloat()

            val x = size.width * xSeed + (size.width * 0.18f * driftSeed * localProgress) + sway * size.width * 0.03f
            val y = -size.height * 0.12f + size.height * 1.25f * localProgress

            val pieceSize = size.minDimension * (0.010f + (((index * 11) % 7) / 1000f))
            val rotation = (index * 31f + localProgress * 540f) % 360f
            val fade = if (localProgress < 0.82f) 1f else (1f - (localProgress - 0.82f) / 0.18f).coerceIn(0f, 1f)

            rotate(degrees = rotation, pivot = Offset(x, y)) {
                drawRoundRect(
                    color = colors[index % colors.size].copy(alpha = fade),
                    topLeft = Offset(x - pieceSize * 0.8f, y - pieceSize * 0.5f),
                    size = Size(pieceSize * 1.6f, pieceSize),
                    cornerRadius = CornerRadius(pieceSize * 0.25f, pieceSize * 0.25f)
                )
            }
        }
    }
}

@Composable
private fun PenaltyShootoutDialog(
    onDismiss: () -> Unit
) {
    val maxAttempts = 5
    var attempts by remember { mutableStateOf(0) }
    var goals by remember { mutableStateOf(0) }
    var saves by remember { mutableStateOf(0) }
    var misses by remember { mutableStateOf(0) }
    var statusText by remember { mutableStateOf("Desliza el dedo desde el balon hacia el arco") }
    var isAnimatingShot by remember { mutableStateOf(false) }
    var isDraggingBall by remember { mutableStateOf(false) }
    var swipeStart by remember { mutableStateOf(Offset.Zero) }
    var swipeCurrent by remember { mutableStateOf(Offset.Zero) }
    var dragPower by remember { mutableStateOf(0f) }
    var lastShotPower by remember { mutableStateOf(0f) }
    var playfieldSize by remember { mutableStateOf(androidx.compose.ui.unit.IntSize.Zero) }
    var shotTargetX by remember { mutableStateOf(0f) }
    var shotHeight by remember { mutableStateOf(0.5f) }
    var keeperDiveDir by remember { mutableStateOf(0) } // -1 izquierda, 0 quieto, 1 derecha
    val keeperDiveProgress = remember { Animatable(0f) }
    val ballProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    fun resetGame() {
        attempts = 0
        goals = 0
        saves = 0
        misses = 0
        isDraggingBall = false
        swipeStart = Offset.Zero
        swipeCurrent = Offset.Zero
        dragPower = 0f
        lastShotPower = 0f
        isAnimatingShot = false
        statusText = "Desliza el dedo desde el balon hacia el arco"
        keeperDiveDir = 0
        scope.launch {
            ballProgress.snapTo(0f)
            keeperDiveProgress.snapTo(0f)
        }
    }

    fun shoot(aimXRaw: Float, powerRaw: Float) {
        if (isAnimatingShot || attempts >= maxAttempts) return

        val aimX = aimXRaw.coerceIn(-1f, 1f)
        val power = powerRaw.coerceIn(0.25f, 1f)

        shotTargetX = aimX * (0.38f + power * 0.14f)
        shotHeight = 0.30f + power * 0.55f

        // El gol ahora depende de que el remate termine dentro del arco.
        val goalLeftN = 0.14f
        val goalRightN = 0.86f
        val goalTopN = 0.10f
        val goalBottomN = 0.50f
        val endNormX = 0.5f + shotTargetX
        val endNormY = 0.40f - shotHeight * 0.18f
        val ballNormRadius = 0.03f
        val insideGoal =
            endNormX - ballNormRadius >= goalLeftN &&
                endNormX + ballNormRadius <= goalRightN &&
                endNormY - ballNormRadius >= goalTopN &&
                endNormY + ballNormRadius <= goalBottomN

        val shotZone = when {
            aimX <= -0.2f -> -1
            aimX >= 0.2f -> 1
            else -> 0
        }

        val decisionRoll = Random.nextFloat()
        val keeperDecision = when {
            decisionRoll < 0.55f -> shotZone
            decisionRoll < 0.78f -> 0
            shotZone == 0 -> if (Random.nextBoolean()) -1 else 1
            else -> -shotZone
        }
        keeperDiveDir = keeperDecision

        val missed = !insideGoal

        val saved = insideGoal && when (keeperDecision) {
            0 -> abs(aimX) < 0.22f && power < 0.95f
            -1 -> aimX < -0.12f && Random.nextFloat() < (0.72f - power * 0.22f).coerceAtLeast(0.25f)
            else -> aimX > 0.12f && Random.nextFloat() < (0.72f - power * 0.22f).coerceAtLeast(0.25f)
        }
        val goal = insideGoal && !saved

        attempts += 1
        if (goal) {
            goals += 1
        } else if (saved) {
            saves += 1
        } else {
            misses += 1
        }

        val keeperAction = when (keeperDecision) {
            -1 -> "Arquero se tira a la izquierda"
            1 -> "Arquero se tira a la derecha"
            else -> "Arquero espera al centro"
        }
        statusText = when {
            goal -> "GOL · $keeperAction"
            missed -> "FUERA · $keeperAction"
            else -> "ATAJADA · $keeperAction"
        }

        isAnimatingShot = true
        scope.launch {
            keeperDiveProgress.snapTo(0f)
            ballProgress.snapTo(0f)
            keeperDiveProgress.animateTo(1f, animationSpec = tween(durationMillis = 240, easing = LinearEasing))
            ballProgress.animateTo(1f, animationSpec = tween(durationMillis = 620, easing = LinearEasing))
            delay(240)
            keeperDiveProgress.animateTo(0f, animationSpec = tween(durationMillis = 340, easing = LinearEasing))
            delay(140)
            ballProgress.snapTo(0f)
            keeperDiveDir = 0
            isAnimatingShot = false

            statusText = if (attempts >= maxAttempts) {
                "Final: $goals/$maxAttempts goles"
            } else {
                "Intento ${attempts + 1}/$maxAttempts · Desliza el balon"
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = Icons.Filled.SportsEsports,
                            contentDescription = null,
                            tint = GrassGreen
                        )
                        Text(
                            text = "Penales - 1 jugador",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    TextButton(onClick = onDismiss) {
                        Text("Cerrar")
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF0A5B22))
                        .onSizeChanged { playfieldSize = it }
                        .pointerInput(isAnimatingShot, attempts, playfieldSize) {
                            detectDragGestures(
                                onDragStart = { start ->
                                    if (isAnimatingShot || attempts >= maxAttempts) return@detectDragGestures
                                    val w = playfieldSize.width.toFloat()
                                    val h = playfieldSize.height.toFloat()
                                    if (w <= 0f || h <= 0f) return@detectDragGestures

                                    val ballStart = Offset(w * 0.5f, h * 0.86f)
                                    val startDx = start.x - ballStart.x
                                    val startDy = start.y - ballStart.y
                                    val hitRadius = w.coerceAtMost(h) * 0.09f
                                    val insideBall = (startDx * startDx + startDy * startDy) <= (hitRadius * hitRadius)
                                    if (!insideBall) return@detectDragGestures

                                    isDraggingBall = true
                                    swipeStart = start
                                    swipeCurrent = start
                                    dragPower = 0f
                                },
                                onDrag = { change, _ ->
                                    if (!isDraggingBall) return@detectDragGestures
                                    swipeCurrent = change.position
                                    val h = playfieldSize.height.toFloat()
                                    if (h > 0f) {
                                        val dx = swipeCurrent.x - swipeStart.x
                                        val dy = swipeCurrent.y - swipeStart.y
                                        val swipeLen = sqrt(dx * dx + dy * dy)
                                        dragPower = (swipeLen / (h * 0.72f)).coerceIn(0f, 1f)
                                    }
                                },
                                onDragEnd = {
                                    if (!isDraggingBall) return@detectDragGestures
                                    val w = playfieldSize.width.toFloat()
                                    val h = playfieldSize.height.toFloat()
                                    val dx = swipeCurrent.x - swipeStart.x
                                    val dy = swipeCurrent.y - swipeStart.y
                                    val upDistance = -dy
                                    val swipeLen = sqrt(dx * dx + dy * dy)
                                    val powerByDistance = (swipeLen / (h * 0.72f)).coerceIn(0f, 1f)
                                    lastShotPower = powerByDistance

                                    if (upDistance > h * 0.12f && swipeLen > h * 0.14f) {
                                        val aimX = (dx / (w * 0.30f)).coerceIn(-1f, 1f)
                                        val power = powerByDistance
                                        shoot(aimX, power)
                                    } else {
                                        statusText = "Desliza hacia arriba desde el balon"
                                    }

                                    isDraggingBall = false
                                    dragPower = 0f
                                },
                                onDragCancel = {
                                    isDraggingBall = false
                                    dragPower = 0f
                                }
                            )
                        }
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF1C8A3A), Color(0xFF0D5A25))
                            )
                        )
                        drawRect(
                            color = Color(0xFF0B1F35),
                            topLeft = Offset(0f, 0f),
                            size = Size(size.width, size.height * 0.22f)
                        )
                        for (i in 0..9) {
                            val lx = size.width * (0.06f + i * 0.1f)
                            drawCircle(
                                color = Color(0xFFFFF3C4).copy(alpha = 0.22f),
                                radius = size.minDimension * 0.012f,
                                center = Offset(lx, size.height * 0.08f)
                            )
                        }

                        val goalTop = size.height * 0.10f
                        val goalBottom = size.height * 0.50f
                        val goalLeft = size.width * 0.14f
                        val goalRight = size.width * 0.86f
                        val goalWidth = goalRight - goalLeft
                        val goalHeight = goalBottom - goalTop

                        drawRect(
                            color = Color.White,
                            topLeft = Offset(goalLeft, goalTop),
                            size = Size(goalWidth, goalHeight * 0.06f)
                        )
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(goalLeft, goalTop),
                            size = Size(goalWidth * 0.02f, goalHeight)
                        )
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(goalRight - goalWidth * 0.02f, goalTop),
                            size = Size(goalWidth * 0.02f, goalHeight)
                        )

                        for (i in 1..5) {
                            val x = goalLeft + goalWidth * (i / 6f)
                            drawLine(
                                color = Color.White.copy(alpha = 0.35f),
                                start = Offset(x, goalTop),
                                end = Offset(x, goalBottom),
                                strokeWidth = 1.5f
                            )
                        }
                        for (i in 1..4) {
                            val y = goalTop + goalHeight * (i / 5f)
                            drawLine(
                                color = Color.White.copy(alpha = 0.35f),
                                start = Offset(goalLeft, y),
                                end = Offset(goalRight, y),
                                strokeWidth = 1.5f
                            )
                        }

                        val dive = keeperDiveProgress.value
                        val diveDir = keeperDiveDir.toFloat()
                        val keeperOffset = diveDir * dive * size.width * 0.24f
                        val keeperCenterX = size.width * 0.5f + keeperOffset
                        val keeperCenterY = goalTop + goalHeight * (0.62f + if (keeperDiveDir == 0) 0f else dive * 0.03f)
                        val torsoW = size.width * 0.095f
                        val torsoH = size.height * 0.19f

                        val skin = Color(0xFFFFC39A)
                        val hair = Color(0xFF5B3A22)
                        val shirt = Color(0xFFF4C233)
                        val sleeve = Color(0xFF2D3A4E)
                        val shortColor = Color(0xFF5E1A1A)
                        val sock = Color(0xFF37474F)
                        val glove = Color(0xFFE8ECEF)
                        val shoe = Color(0xFF7A2323)
                        val outline = Color(0xFF261E14)
                        val bodyAngle = -10f + diveDir * dive * 42f

                        rotate(degrees = bodyAngle, pivot = Offset(keeperCenterX, keeperCenterY)) {
                            val outlineW = torsoW * 0.05f

                            // Torso cartoon
                            drawRoundRect(
                                color = shirt,
                                topLeft = Offset(keeperCenterX - torsoW * 0.50f, keeperCenterY - torsoH * 0.44f),
                                size = Size(torsoW, torsoH * 0.88f),
                                cornerRadius = CornerRadius(torsoW * 0.20f, torsoW * 0.20f)
                            )
                            drawRoundRect(
                                color = outline,
                                topLeft = Offset(keeperCenterX - torsoW * 0.50f, keeperCenterY - torsoH * 0.44f),
                                size = Size(torsoW, torsoH * 0.88f),
                                cornerRadius = CornerRadius(torsoW * 0.20f, torsoW * 0.20f),
                                style = Stroke(width = outlineW)
                            )
                            drawRoundRect(
                                color = Color(0xFFFFDB60).copy(alpha = 0.70f),
                                topLeft = Offset(keeperCenterX - torsoW * 0.13f, keeperCenterY - torsoH * 0.38f),
                                size = Size(torsoW * 0.22f, torsoH * 0.72f),
                                cornerRadius = CornerRadius(torsoW * 0.08f, torsoW * 0.08f)
                            )
                            // Escudo
                            drawCircle(
                                color = Color(0xFF7A2323),
                                radius = torsoW * 0.09f,
                                center = Offset(keeperCenterX + torsoW * 0.22f, keeperCenterY - torsoH * 0.08f)
                            )
                            drawCircle(
                                color = outline,
                                radius = torsoW * 0.09f,
                                center = Offset(keeperCenterX + torsoW * 0.22f, keeperCenterY - torsoH * 0.08f),
                                style = Stroke(width = outlineW * 0.7f)
                            )

                            // Head cartoon
                            val headCenter = Offset(keeperCenterX, keeperCenterY - torsoH * 0.66f)
                            val headR = torsoW * 0.30f
                            drawCircle(color = skin, radius = headR, center = headCenter)
                            drawCircle(
                                color = outline,
                                radius = headR,
                                center = headCenter,
                                style = Stroke(width = outlineW * 0.7f)
                            )
                            drawArc(
                                color = hair,
                                startAngle = 185f,
                                sweepAngle = 170f,
                                useCenter = true,
                                topLeft = Offset(headCenter.x - headR, headCenter.y - headR),
                                size = Size(headR * 2f, headR * 1.42f)
                            )
                            drawCircle(color = Color.White, radius = headR * 0.095f, center = Offset(headCenter.x - headR * 0.22f, headCenter.y - headR * 0.03f))
                            drawCircle(color = Color.White, radius = headR * 0.095f, center = Offset(headCenter.x + headR * 0.22f, headCenter.y - headR * 0.03f))
                            drawCircle(color = Color(0xFF262626), radius = headR * 0.055f, center = Offset(headCenter.x - headR * 0.22f, headCenter.y - headR * 0.02f))
                            drawCircle(color = Color(0xFF262626), radius = headR * 0.055f, center = Offset(headCenter.x + headR * 0.22f, headCenter.y - headR * 0.02f))
                            drawRoundRect(
                                color = Color.White,
                                topLeft = Offset(headCenter.x - headR * 0.16f, headCenter.y + headR * 0.14f),
                                size = Size(headR * 0.32f, headR * 0.13f),
                                cornerRadius = CornerRadius(headR * 0.05f, headR * 0.05f)
                            )
                            drawRoundRect(
                                color = outline,
                                topLeft = Offset(headCenter.x - headR * 0.16f, headCenter.y + headR * 0.14f),
                                size = Size(headR * 0.32f, headR * 0.13f),
                                cornerRadius = CornerRadius(headR * 0.05f, headR * 0.05f),
                                style = Stroke(width = outlineW * 0.5f)
                            )

                            // Arms (single segment, shorter and straighter)
                            val shoulderY = keeperCenterY - torsoH * 0.20f
                            val armReach = torsoW * (0.74f + dive * 0.20f)
                            val armLift = torsoH * (0.42f + dive * 0.20f)
                            val bias = diveDir * torsoW * 0.08f
                            val leftShoulder = Offset(keeperCenterX - torsoW * 0.42f, shoulderY)
                            val rightShoulder = Offset(keeperCenterX + torsoW * 0.42f, shoulderY)
                            val leftHand = Offset(keeperCenterX - armReach + bias, shoulderY - armLift)
                            val rightHand = Offset(keeperCenterX + armReach + bias, shoulderY - armLift)

                            drawLine(color = outline, start = leftShoulder, end = leftHand, strokeWidth = torsoW * 0.19f, cap = StrokeCap.Round)
                            drawLine(color = outline, start = rightShoulder, end = rightHand, strokeWidth = torsoW * 0.19f, cap = StrokeCap.Round)
                            drawLine(color = sleeve, start = leftShoulder, end = leftHand, strokeWidth = torsoW * 0.15f, cap = StrokeCap.Round)
                            drawLine(color = sleeve, start = rightShoulder, end = rightHand, strokeWidth = torsoW * 0.15f, cap = StrokeCap.Round)
                            drawCircle(color = glove, radius = torsoW * 0.14f, center = leftHand)
                            drawCircle(color = glove, radius = torsoW * 0.14f, center = rightHand)
                            drawCircle(color = outline, radius = torsoW * 0.14f, center = leftHand, style = Stroke(width = outlineW * 0.6f))
                            drawCircle(color = outline, radius = torsoW * 0.14f, center = rightHand, style = Stroke(width = outlineW * 0.6f))

                            // Shorts
                            drawRoundRect(
                                color = shortColor,
                                topLeft = Offset(keeperCenterX - torsoW * 0.42f, keeperCenterY + torsoH * 0.24f),
                                size = Size(torsoW * 0.84f, torsoH * 0.32f),
                                cornerRadius = CornerRadius(torsoW * 0.12f, torsoW * 0.12f)
                            )
                            drawRoundRect(
                                color = outline,
                                topLeft = Offset(keeperCenterX - torsoW * 0.42f, keeperCenterY + torsoH * 0.24f),
                                size = Size(torsoW * 0.84f, torsoH * 0.32f),
                                cornerRadius = CornerRadius(torsoW * 0.12f, torsoW * 0.12f),
                                style = Stroke(width = outlineW * 0.7f)
                            )

                            // Legs (single segment)
                            val hipY = keeperCenterY + torsoH * 0.56f
                            val legSpread = torsoW * (0.26f + dive * 0.08f)
                            val legLen = torsoH * 0.82f
                            val leftFoot = Offset(keeperCenterX - legSpread, hipY + legLen)
                            val rightFoot = Offset(keeperCenterX + legSpread, hipY + legLen * 0.96f)

                            drawLine(color = outline, start = Offset(keeperCenterX - torsoW * 0.16f, hipY), end = leftFoot, strokeWidth = torsoW * 0.20f, cap = StrokeCap.Round)
                            drawLine(color = outline, start = Offset(keeperCenterX + torsoW * 0.16f, hipY), end = rightFoot, strokeWidth = torsoW * 0.20f, cap = StrokeCap.Round)
                            drawLine(color = sock, start = Offset(keeperCenterX - torsoW * 0.16f, hipY), end = leftFoot, strokeWidth = torsoW * 0.16f, cap = StrokeCap.Round)
                            drawLine(color = sock, start = Offset(keeperCenterX + torsoW * 0.16f, hipY), end = rightFoot, strokeWidth = torsoW * 0.16f, cap = StrokeCap.Round)
                            drawOval(color = shoe, topLeft = Offset(leftFoot.x - torsoW * 0.15f, leftFoot.y - torsoW * 0.06f), size = Size(torsoW * 0.30f, torsoW * 0.12f))
                            drawOval(color = shoe, topLeft = Offset(rightFoot.x - torsoW * 0.15f, rightFoot.y - torsoW * 0.06f), size = Size(torsoW * 0.30f, torsoW * 0.12f))
                            drawOval(color = outline, topLeft = Offset(leftFoot.x - torsoW * 0.15f, leftFoot.y - torsoW * 0.06f), size = Size(torsoW * 0.30f, torsoW * 0.12f), style = Stroke(width = outlineW * 0.7f))
                            drawOval(color = outline, topLeft = Offset(rightFoot.x - torsoW * 0.15f, rightFoot.y - torsoW * 0.06f), size = Size(torsoW * 0.30f, torsoW * 0.12f), style = Stroke(width = outlineW * 0.7f))
                        }

                        val t = ballProgress.value
                        val startBall = Offset(size.width * 0.5f, size.height * 0.86f)
                        val endBall = Offset(size.width * (0.5f + shotTargetX), size.height * (0.40f - shotHeight * 0.18f))
                        val ballX = startBall.x + (endBall.x - startBall.x) * t
                        val arcLift = size.height * 0.16f * shotHeight * sin((Math.PI * t).toFloat())
                        val ballY = startBall.y + (endBall.y - startBall.y) * t - arcLift
                        val ballR = size.minDimension * 0.035f

                        val ballCenter = if (isAnimatingShot) Offset(ballX, ballY) else startBall
                        drawCircle(color = Color.White, radius = ballR, center = ballCenter)
                        drawCircle(
                            color = Color(0xFF111111),
                            radius = ballR,
                            center = ballCenter,
                            style = Stroke(width = size.minDimension * 0.0052f)
                        )

                        fun pentagon(center: Offset, radius: Float, rotationDeg: Float = -90f): Path {
                            val p = Path()
                            for (i in 0 until 5) {
                                val deg = rotationDeg + i * 72f
                                val rad = Math.toRadians(deg.toDouble())
                                val px = center.x + cos(rad).toFloat() * radius
                                val py = center.y + sin(rad).toFloat() * radius
                                if (i == 0) p.moveTo(px, py) else p.lineTo(px, py)
                            }
                            p.close()
                            return p
                        }

                        drawPath(
                            path = pentagon(ballCenter, ballR * 0.32f),
                            color = Color(0xFF1A1A1A)
                        )
                        for (i in 0 until 5) {
                            val ang = Math.toRadians((-90.0 + i * 72.0))
                            val patchCenter = Offset(
                                ballCenter.x + cos(ang).toFloat() * ballR * 0.56f,
                                ballCenter.y + sin(ang).toFloat() * ballR * 0.56f
                            )
                            drawPath(
                                path = pentagon(patchCenter, ballR * 0.19f, rotationDeg = -54f),
                                color = Color(0xFF1A1A1A)
                            )
                        }
                        drawCircle(
                            color = Color.White.copy(alpha = 0.46f),
                            radius = ballR * 0.24f,
                            center = Offset(ballCenter.x - ballR * 0.26f, ballCenter.y - ballR * 0.28f)
                        )

                        drawCircle(
                            color = Color.White.copy(alpha = 0.8f),
                            radius = size.minDimension * 0.008f,
                            center = Offset(size.width * 0.5f, size.height * 0.86f)
                        )

                        if (isDraggingBall) {
                            drawLine(
                                color = Color.White.copy(alpha = 0.7f),
                                start = swipeStart,
                                end = swipeCurrent,
                                strokeWidth = size.minDimension * 0.01f
                            )
                        }
                    }

                    Text(
                        text = statusText,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Intentos: $attempts/$maxAttempts", color = MaterialTheme.colorScheme.onSurface)
                    Text("Goles: $goals  Atajadas: $saves  Fuera: $misses", color = MaterialTheme.colorScheme.onSurface)
                }

                val powerBarValue = if (isDraggingBall) dragPower else lastShotPower
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Potencia del tiro",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${(powerBarValue * 100f).roundToInt()}%",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xFF263238))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(powerBarValue.coerceIn(0f, 1f))
                                .clip(RoundedCornerShape(999.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFD50000),
                                            Color(0xFFFFAB00),
                                            Color(0xFF00C853)
                                        )
                                    )
                                )
                        )
                    }
                }

                Text(
                    text = "Control: toca y arrastra el balon hacia el arco para patear.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (attempts >= maxAttempts) {
                        Button(onClick = { resetGame() }) {
                            Icon(Icons.Filled.Refresh, contentDescription = null)
                            Spacer(Modifier.width(6.dp))
                            Text("Jugar otra ronda")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedTeamsScreen(
    players: List<Player>,
    onBack: () -> Unit,
    onStartTournament: (List<SavedTeam>) -> Unit,
    onOpenLeague: (List<SavedTeam>) -> Unit,
    onOpenSavedLeague: (SavedLeague) -> Unit
) {
    val context = LocalContext.current
    var proUnlocked by remember { mutableStateOf(isProUnlocked(context)) }
    val maxTeamsAllowed = if (proUnlocked) 64 else 20
    var showProUpsellDialog by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isCompactWidth = configuration.screenWidthDp < 360
    var teams by remember { mutableStateOf(loadTeams(context)) }
    var pendingDeleteTeam by remember { mutableStateOf<SavedTeam?>(null) }
    var tournamentMode by remember { mutableStateOf(false) }
    var selectedTournamentTeams by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var leagueMode by remember { mutableStateOf(false) }
    var selectedLeagueTeams by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var showTeamEditorDialog by remember { mutableStateOf(false) }
    var editingTeam by remember { mutableStateOf<SavedTeam?>(null) }
    var showTournamentHistory by remember { mutableStateOf(false) }
    var showLeagueHistory by remember { mutableStateOf(false) }
    var selectedTournament by remember { mutableStateOf<SavedTournament?>(null) }

    val screenTextColor = MaterialTheme.colorScheme.onSurface
    val screenSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val panelColor = MaterialTheme.colorScheme.surfaceVariant

    BackHandler(onBack = onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = screenTextColor
                )
            }
            Text(
                text = stringResource(R.string.saved_teams_title),
                color = screenTextColor,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { showTournamentHistory = true }) {
                Text(stringResource(R.string.history), color = screenTextColor)
            }
        }
        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(panelColor, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when {
                            tournamentMode -> stringResource(R.string.tournament_mode_select_teams)
                            leagueMode -> stringResource(R.string.league_mode_select_teams)
                            else -> stringResource(R.string.normal_mode)
                        },
                        color = screenTextColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.teams_count_format, teams.size),
                        color = screenTextColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(Modifier.height(4.dp))
                when {
                    tournamentMode -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { showTournamentHistory = true },
                                baseColor = GrassGreen,
                                border = null,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.History,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(R.string.saved_label))
                            }
                            OutlinedButton(
                                onClick = {
                                    tournamentMode = false
                                    selectedTournamentTeams = emptySet()
                                },
                                baseColor = GrassGreen,
                                border = null,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(R.string.exit))
                            }
                        }
                    }
                    leagueMode -> {
                        val selectedLeagueList = teams.filter { selectedLeagueTeams.contains(it.id) }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = {
                                    showLeagueHistory = true
                                },
                                baseColor = GrassGreen,
                                border = null,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.History,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(R.string.saved_label))
                            }
                            OutlinedButton(
                                onClick = {
                                    leagueMode = false
                                    selectedLeagueTeams = emptySet()
                                },
                                baseColor = GrassGreen,
                                border = null,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(R.string.exit))
                            }
                        }
                    }
                    else -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = {
                                    tournamentMode = true
                                    leagueMode = false
                                    selectedTournamentTeams = emptySet()
                                    selectedLeagueTeams = emptySet()
                                },
                                baseColor = GrassGreen,
                                border = null,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.EmojiEvents,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(R.string.tournament))
                            }
                            OutlinedButton(
                                onClick = {
                                    leagueMode = true
                                    tournamentMode = false
                                    selectedLeagueTeams = emptySet()
                                    selectedTournamentTeams = emptySet()
                                },
                                baseColor = GrassGreen,
                                border = null,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.List,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(R.string.league))
                            }
                        }
                    }
                }
            }

            if (teams.isEmpty()) {
                Text(stringResource(R.string.no_saved_teams), color = screenTextColor)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(teams) { t ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            var expanded by remember(t.id) { mutableStateOf(false) }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { expanded = !expanded },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(t.name, fontWeight = FontWeight.SemiBold, color = screenTextColor)
                                    Spacer(Modifier.height(2.dp))
                                    Text(
                                        text = stringResource(R.string.players_count_parentheses, t.players.size),
                                        color = screenSecondaryTextColor,
                                        fontSize = 12.sp
                                    )
                                }
                                if (tournamentMode) {
                                    Checkbox(
                                        checked = selectedTournamentTeams.contains(t.id),
                                        onCheckedChange = { checked ->
                                            if (checked && selectedTournamentTeams.size >= maxTeamsAllowed) {
                                                showProUpsellDialog = true
                                            } else {
                                                selectedTournamentTeams = if (checked) {
                                                    selectedTournamentTeams + t.id
                                                } else {
                                                    selectedTournamentTeams - t.id
                                                }
                                            }
                                        }
                                    )
                                } else if (leagueMode) {
                                    Checkbox(
                                        checked = selectedLeagueTeams.contains(t.id),
                                        onCheckedChange = { checked ->
                                            if (checked && selectedLeagueTeams.size >= maxTeamsAllowed) {
                                                showProUpsellDialog = true
                                            } else {
                                                selectedLeagueTeams = if (checked) {
                                                    selectedLeagueTeams + t.id
                                                } else {
                                                    selectedLeagueTeams - t.id
                                                }
                                            }
                                        }
                                    )
                                }
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    tint = screenTextColor
                                )
                            }
                            if (expanded) {
                                Spacer(Modifier.height(4.dp))
                                t.players.forEach { p ->
                                    Text(
                                        text = "\u0000b7 " + p.name,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        editingTeam = t
                                        showTeamEditorDialog = true
                                    },
                                    baseColor = GrassGreen,
                                    border = null,
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White
                                    )
                                ) {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = stringResource(R.string.edit),
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                }
                                OutlinedButton(
                                    onClick = { pendingDeleteTeam = t },
                                    baseColor = GrassGreen,
                                    border = null,
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White
                                    )
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = stringResource(R.string.delete),
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }

                if (tournamentMode || leagueMode) {
                    val allTeamIds = remember(teams) { teams.map { it.id }.toSet() }
                    val isAllSelected = when {
                        tournamentMode -> selectedTournamentTeams.size == teams.size && teams.isNotEmpty()
                        leagueMode -> selectedLeagueTeams.size == teams.size && teams.isNotEmpty()
                        else -> false
                    }
                    val selectedCount = when {
                        tournamentMode -> selectedTournamentTeams.size
                        leagueMode -> selectedLeagueTeams.size
                        else -> 0
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isAllSelected,
                            onCheckedChange = { checked ->
                                if (tournamentMode) {
                                    if (checked && allTeamIds.size > maxTeamsAllowed) {
                                        showProUpsellDialog = true
                                    } else {
                                        selectedTournamentTeams = if (checked) allTeamIds else emptySet()
                                    }
                                } else if (leagueMode) {
                                    if (checked && allTeamIds.size > maxTeamsAllowed) {
                                        showProUpsellDialog = true
                                    } else {
                                        selectedLeagueTeams = if (checked) allTeamIds else emptySet()
                                    }
                                }
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.select_all),
                            color = screenTextColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Seleccionados: $selectedCount / $maxTeamsAllowed",
                            color = screenSecondaryTextColor,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        val selectedTeamsList = teams.filter { selectedTournamentTeams.contains(it.id) }
        val size = selectedTeamsList.size
        val canStartTournament = tournamentMode && size in 2..maxTeamsAllowed
        val selectedLeagueList = teams.filter { selectedLeagueTeams.contains(it.id) }
        val canStartLeague = leagueMode && selectedLeagueList.size in 2..maxTeamsAllowed

        if (isCompactWidth) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        editingTeam = null
                        showTeamEditorDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.create_team), color = Color.White)
                }
                if (tournamentMode) {
                    Button(
                        enabled = canStartTournament,
                        onClick = {
                            if (canStartTournament) {
                                onStartTournament(selectedTeamsList)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.start_tournament), color = Color.White)
                    }
                }
                if (leagueMode) {
                    Button(
                        enabled = canStartLeague,
                        onClick = {
                            if (canStartLeague) {
                                onOpenLeague(selectedLeagueList)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.start_league), color = Color.White)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onBack) {
                        Text(stringResource(R.string.close), color = Color.White)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (tournamentMode) {
                        Button(
                            enabled = canStartTournament,
                            onClick = {
                                if (canStartTournament) {
                                    onStartTournament(selectedTeamsList)
                                }
                            }
                        ) {
                            Text(stringResource(R.string.start_tournament), color = Color.White)
                        }
                    }
                    if (leagueMode) {
                        Button(
                            enabled = canStartLeague,
                            onClick = {
                                if (canStartLeague) {
                                    onOpenLeague(selectedLeagueList)
                                }
                            }
                        ) {
                            Text(stringResource(R.string.start_league), color = Color.White)
                        }
                    }
                    Button(onClick = {
                        editingTeam = null
                        showTeamEditorDialog = true
                    }) {
                        Text(stringResource(R.string.create_team), color = Color.White)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onBack) {
                        Text(stringResource(R.string.close), color = Color.White)
                    }
                }
            }
        }

        if (pendingDeleteTeam != null) {
            val t = pendingDeleteTeam!!
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = { pendingDeleteTeam = null },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text(stringResource(R.string.delete_team_title), color = dialogTextColor) },
                text = { Text(stringResource(R.string.delete_team_confirm, t.name), color = dialogTextColor) },
                dismissButton = {
                    TextButton(onClick = { pendingDeleteTeam = null }) {
                        Text(stringResource(R.string.cancel), color = dialogTextColor)
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        deleteTeam(context, t.id)
                        teams = loadTeams(context)
                        pendingDeleteTeam = null
                    }) {
                        Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }

        if (showTeamEditorDialog) {
            val initial = editingTeam
            val initialName = initial?.name ?: ""
            val initialPlayers = initial?.players ?: emptyList()
            EditSavedTeamDialog(
                allPlayers = players,
                initialName = initialName,
                initialPlayers = initialPlayers,
                onSave = { name, selectedPlayers ->
                    if (initial == null) {
                        addTeam(context, name, selectedPlayers)
                    } else {
                        updateTeam(context, initial.copy(name = name, players = selectedPlayers))
                    }
                    teams = loadTeams(context)
                    showTeamEditorDialog = false
                },
                onDismiss = { showTeamEditorDialog = false }
            )
        }

        if (showTournamentHistory) {
            val context = LocalContext.current
            var tournaments by remember(showTournamentHistory) { mutableStateOf(loadTournaments(context)) }
            var deleteMode by remember(showTournamentHistory) { mutableStateOf(false) }
            var selectedTournamentsForDelete by remember(showTournamentHistory) { mutableStateOf<Set<Long>>(emptySet()) }
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

            AlertDialog(
                onDismissRequest = {
                    showTournamentHistory = false
                    deleteMode = false
                    selectedTournamentsForDelete = emptySet()
                },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.tournament_history_title), color = dialogTextColor)
                        IconButton(
                            onClick = {
                                if (!deleteMode) {
                                    // Primer toque: entrar en modo borrar
                                    deleteMode = true
                                    selectedTournamentsForDelete = emptySet()
                                } else {
                                    // Segundo toque: si hay torneo seleccionado, borrarlo
                                    val toDeleteIds = selectedTournamentsForDelete
                                    if (toDeleteIds.isNotEmpty()) {
                                        val updated = tournaments.filterNot { toDeleteIds.contains(it.id) }
                                        tournaments = updated
                                        saveTournaments(context, updated)
                                    }
                                    deleteMode = false
                                    selectedTournamentsForDelete = emptySet()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = if (deleteMode) Color(0xFFFF5252) else dialogTextColor
                            )
                        }
                    }
                },
                text = {
                    if (tournaments.isEmpty()) {
                        Text(stringResource(R.string.no_saved_tournaments), color = dialogTextColor)
                    } else {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyColumn(modifier = Modifier.heightIn(max = 360.dp)) {
                                items(tournaments) { t ->
                                    val isSelectedToDelete = deleteMode && selectedTournamentsForDelete.contains(t.id)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (isSelectedToDelete) Color(0xFF424242) else Color.Transparent,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                if (deleteMode) {
                                                    selectedTournamentsForDelete = if (isSelectedToDelete) {
                                                        selectedTournamentsForDelete - t.id
                                                    } else {
                                                        selectedTournamentsForDelete + t.id
                                                    }
                                                } else {
                                                    selectedTournament = t
                                                    showTournamentHistory = false
                                                }
                                            }
                                            .padding(vertical = 8.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(t.name, fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                            Spacer(Modifier.height(4.dp))
                                            val info = buildString {
                                                append(stringResource(R.string.teams_info_format, t.teams.size))
                                                if (t.championName != null && t.championName.isNotBlank()) {
                                                    append(" · ")
                                                    append(stringResource(R.string.champion_info_format, t.championName))
                                                }
                                            }
                                            Text(info, color = dialogSecondaryTextColor, fontSize = 12.sp)
                                        }
                                        if (deleteMode) {
                                            Checkbox(
                                                checked = isSelectedToDelete,
                                                onCheckedChange = { checked ->
                                                    selectedTournamentsForDelete = if (checked) {
                                                        selectedTournamentsForDelete + t.id
                                                    } else {
                                                        selectedTournamentsForDelete - t.id
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showTournamentHistory = false }) {
                        Text(stringResource(R.string.close), color = dialogTextColor)
                    }
                }
            )
        }

        if (showLeagueHistory) {
            val context = LocalContext.current
            var leagues by remember(showLeagueHistory) { mutableStateOf(loadLeagues(context)) }
            var deleteMode by remember(showLeagueHistory) { mutableStateOf(false) }
            var selectedLeaguesForDelete by remember(showLeagueHistory) { mutableStateOf<Set<Long>>(emptySet()) }
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

            AlertDialog(
                onDismissRequest = {
                    showLeagueHistory = false
                    deleteMode = false
                    selectedLeaguesForDelete = emptySet()
                },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.league_history_title), color = dialogTextColor)
                        IconButton(
                            onClick = {
                                if (!deleteMode) {
                                    deleteMode = true
                                    selectedLeaguesForDelete = emptySet()
                                } else {
                                    val toDeleteIds = selectedLeaguesForDelete
                                    if (toDeleteIds.isNotEmpty()) {
                                        val updated = leagues.filterNot { toDeleteIds.contains(it.id) }
                                        leagues = updated
                                        saveLeagues(context, updated)
                                    }
                                    deleteMode = false
                                    selectedLeaguesForDelete = emptySet()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = if (deleteMode) Color(0xFFFF5252) else dialogTextColor
                            )
                        }
                    }
                },
                text = {
                    if (leagues.isEmpty()) {
                        Text(stringResource(R.string.no_saved_leagues), color = dialogTextColor)
                    } else {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyColumn(modifier = Modifier.heightIn(max = 360.dp)) {
                                items(leagues) { l ->
                                    val isSelectedToDelete = deleteMode && selectedLeaguesForDelete.contains(l.id)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (isSelectedToDelete) Color(0xFF424242) else Color.Transparent,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                if (deleteMode) {
                                                    selectedLeaguesForDelete = if (isSelectedToDelete) {
                                                        selectedLeaguesForDelete - l.id
                                                    } else {
                                                        selectedLeaguesForDelete + l.id
                                                    }
                                                } else {
                                                    onOpenSavedLeague(l)
                                                    showLeagueHistory = false
                                                }
                                            }
                                            .padding(vertical = 8.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(l.name, fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                            Spacer(Modifier.height(4.dp))
                                            val info = stringResource(R.string.teams_info_format, l.teams.size)
                                            Text(info, color = dialogSecondaryTextColor, fontSize = 12.sp)
                                        }
                                        if (deleteMode) {
                                            Checkbox(
                                                checked = isSelectedToDelete,
                                                onCheckedChange = { checked ->
                                                    selectedLeaguesForDelete = if (checked) {
                                                        selectedLeaguesForDelete + l.id
                                                    } else {
                                                        selectedLeaguesForDelete - l.id
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showLeagueHistory = false }) {
                        Text(stringResource(R.string.close), color = dialogTextColor)
                    }
                }
            )
        }

        if (selectedTournament != null) {
            val t = selectedTournament!!
            Dialog(
                onDismissRequest = { selectedTournament = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TournamentBracketDialog(
                        teams = t.teams,
                        initialRounds = t.rounds,
                        initialGroupStage = t.groupStage,
                        onDismiss = { selectedTournament = null }
                    )
                }
            }
        }

        if (showProUpsellDialog) {
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = { showProUpsellDialog = false },
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text("Límite de equipos", color = dialogTextColor) },
                text = {
                    Text(
                        "El límite actual es $maxTeamsAllowed equipos. Para habilitar hasta 64 equipos necesitás el desbloqueo Pro (suscripción semestral de USD 1).",
                        color = dialogTextColor
                    )
                },
                confirmButton = {
                    Button(onClick = { showProUpsellDialog = false }) {
                        Text(stringResource(R.string.close), color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    }
}

@Composable
fun LeagueScreen(
    teams: List<SavedTeam>,
    onBack: () -> Unit,
    startWithHistory: Boolean = false,
    initialLeague: SavedLeague? = null
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isCompactWidth = configuration.screenWidthDp < 360

    val screenTextColor = MaterialTheme.colorScheme.onSurface
    val screenSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val panelColor = MaterialTheme.colorScheme.surfaceVariant
    val cardColor = MaterialTheme.colorScheme.surface

    var selectedTeamIds by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var matches by remember { mutableStateOf<List<LeagueMatch>>(emptyList()) }
    var editingMatch by remember { mutableStateOf<LeagueMatch?>(null) }
    var showCalendar by remember { mutableStateOf(true) }
    var showTable by remember { mutableStateOf(true) }
    var showLeagueHistory by remember { mutableStateOf(startWithHistory) }
    var activeLeague by remember(initialLeague) { mutableStateOf(initialLeague) }
    var winPoints by remember { mutableStateOf(initialLeague?.winPoints ?: 3) }
    var drawPoints by remember { mutableStateOf(initialLeague?.drawPoints ?: 1) }
    var lossPoints by remember { mutableStateOf(initialLeague?.lossPoints ?: 0) }
    var randomCalendar by remember { mutableStateOf(initialLeague?.randomCalendar ?: true) }
    var showLeagueSetupDialog by remember { mutableStateOf(initialLeague == null && !startWithHistory && teams.isNotEmpty()) }
    var setupDone by remember { mutableStateOf(initialLeague != null || startWithHistory || teams.isEmpty()) }
    var showLeagueCalendarPickerDialog by remember { mutableStateOf(false) }
    var draftRounds by remember { mutableStateOf<List<List<LeaguePair>>>(emptyList()) }
    var showLeagueNameDialog by remember { mutableStateOf(false) }
    var pendingLeagueName by remember { mutableStateOf("") }
    var showWinnerDialog by remember { mutableStateOf(false) }
    var lastAnnouncedWinnerId by remember { mutableStateOf<Long?>(null) }

    val maxTeams = if (isProUnlocked(context)) 64 else 20
    val selectedTeams = remember(teams, selectedTeamIds, activeLeague) {
        activeLeague?.teams ?: teams.filter { selectedTeamIds.contains(it.id) }
    }

    val table = remember(selectedTeams, matches, winPoints, drawPoints, lossPoints) {
        computeLeagueTable(selectedTeams, matches, winPoints, drawPoints, lossPoints)
    }

    val isCalendarFinished = remember(matches) {
        matches.isNotEmpty() && matches.all { it.homeGoals != null && it.awayGoals != null }
    }

    val currentWinnerTeamId = remember(isCalendarFinished, table) {
        if (!isCalendarFinished || table.isEmpty()) null else table.first().team.id
    }

    val currentWinnerName = remember(isCalendarFinished, table) {
        if (!isCalendarFinished || table.isEmpty()) null else table.first().team.name
    }

    LaunchedEffect(isCalendarFinished, currentWinnerTeamId) {
        if (isCalendarFinished && currentWinnerTeamId != null && currentWinnerTeamId != lastAnnouncedWinnerId) {
            lastAnnouncedWinnerId = currentWinnerTeamId
            showWinnerDialog = true
        }
    }

    LaunchedEffect(activeLeague) {
        val l = activeLeague
        if (l != null) {
            selectedTeamIds = l.teams.map { it.id }.toSet()
            matches = l.matches
            winPoints = l.winPoints
            drawPoints = l.drawPoints
            lossPoints = l.lossPoints
            randomCalendar = l.randomCalendar
            setupDone = true
            showLeagueSetupDialog = false
        }
    }

    LaunchedEffect(teams) {
        if (activeLeague == null && selectedTeamIds.isEmpty()) {
            selectedTeamIds = teams.map { it.id }.toSet()
        }
    }

    LaunchedEffect(selectedTeams, activeLeague, setupDone, randomCalendar) {
        if (activeLeague == null && setupDone && matches.isEmpty() && selectedTeams.size in 2..maxTeams) {
            matches = generateLeagueMatches(selectedTeams, randomCalendar)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = screenTextColor
                )
            }
            Text(
                text = stringResource(R.string.league),
                color = screenTextColor,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val title = activeLeague?.name ?: stringResource(R.string.league_current)
            Text(
                text = title,
                color = screenTextColor,
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedButton(
                onClick = onBack,
                baseColor = GrassGreen,
                border = null,
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(stringResource(R.string.exit))
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { showLeagueHistory = true },
                baseColor = GrassGreen,
                border = null,
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(stringResource(R.string.saved_label))
            }
        }

        Spacer(Modifier.height(8.dp))

        Spacer(Modifier.height(12.dp))

        if (showLeagueSetupDialog) {
            var winText by remember { mutableStateOf(winPoints.toString()) }
            var drawText by remember { mutableStateOf(drawPoints.toString()) }
            var lossText by remember { mutableStateOf(lossPoints.toString()) }
            var randomSelected by remember { mutableStateOf(randomCalendar) }

            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = {
                    showLeagueSetupDialog = false
                    onBack()
                },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text(stringResource(R.string.league_setup_title), color = dialogTextColor) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(stringResource(R.string.scoring_label), color = dialogTextColor, fontWeight = FontWeight.SemiBold)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = winText,
                                onValueChange = { winText = it.filter { c -> c.isDigit() } },
                                label = { Text(stringResource(R.string.win_label)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = drawText,
                                onValueChange = { drawText = it.filter { c -> c.isDigit() } },
                                label = { Text(stringResource(R.string.draw_label)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = lossText,
                                onValueChange = { lossText = it.filter { c -> c.isDigit() } },
                                label = { Text(stringResource(R.string.loss_label)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Text(stringResource(R.string.calendar_label), color = dialogTextColor, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { randomSelected = true },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = randomSelected, onClick = { randomSelected = true })
                            Spacer(Modifier.width(6.dp))
                            Text(stringResource(R.string.random_option), color = dialogTextColor)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { randomSelected = false },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = !randomSelected, onClick = { randomSelected = false })
                            Spacer(Modifier.width(6.dp))
                            Text(stringResource(R.string.manual_option), color = dialogTextColor)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            winPoints = winText.toIntOrNull() ?: 3
                            drawPoints = drawText.toIntOrNull() ?: 1
                            lossPoints = lossText.toIntOrNull() ?: 0
                            randomCalendar = randomSelected
                            if (randomSelected) {
                                setupDone = true
                                showLeagueSetupDialog = false
                            } else {
                                draftRounds = generateLeagueRounds(selectedTeams, randomCalendar = false)
                                showLeagueSetupDialog = false
                                showLeagueCalendarPickerDialog = true
                            }
                        }
                    ) {
                        Text(stringResource(R.string.continue_action), color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showLeagueSetupDialog = false
                            onBack()
                        }
                    ) {
                        Text(stringResource(R.string.cancel), color = dialogTextColor)
                    }
                }
            )
        }

        if (showLeagueCalendarPickerDialog) {
            var selectedPhaseIndex by remember { mutableStateOf(0) }
            var selectedSlotIndex by remember { mutableStateOf<Int?>(null) }
            var errorText by remember { mutableStateOf<String?>(null) }

            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface

            AlertDialog(
                onDismissRequest = {
                    showLeagueCalendarPickerDialog = false
                    showLeagueSetupDialog = true
                },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text(stringResource(R.string.manual_calendar_title), color = dialogTextColor) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        if (draftRounds.isEmpty()) {
                            Text(stringResource(R.string.no_phases_to_edit), color = dialogTextColor)
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                draftRounds.forEachIndexed { index, _ ->
                                    val selected = index == selectedPhaseIndex
                                    OutlinedButton(
                                        onClick = {
                                            selectedPhaseIndex = index
                                            selectedSlotIndex = null
                                            errorText = null
                                        },
                                        border = BorderStroke(1.dp, if (selected) GrassGreen else dialogTextColor)
                                    ) {
                                        Text(stringResource(R.string.phase_format, index + 1))
                                    }
                                }
                            }

                            val phase = draftRounds.getOrNull(selectedPhaseIndex).orEmpty()
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 360.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(phase) { pairIndex, p ->
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        tonalElevation = 2.dp,
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            val leftIndex = pairIndex * 2
                                            val rightIndex = pairIndex * 2 + 1
                                            val leftSelected = selectedSlotIndex == leftIndex
                                            val rightSelected = selectedSlotIndex == rightIndex

                                            val leftName = p.home?.name ?: stringResource(R.string.bye)
                                            val rightName = p.away?.name ?: stringResource(R.string.bye)

                                            OutlinedButton(
                                                onClick = {
                                                    errorText = null
                                                    selectedSlotIndex = if (selectedSlotIndex == null) {
                                                        leftIndex
                                                    } else {
                                                        val a = selectedSlotIndex!!
                                                        val b = leftIndex
                                                        val updatedPhase = swapRoundSlots(phase, a, b)
                                                        val newDraft = draftRounds.toMutableList().apply {
                                                            this[selectedPhaseIndex] = updatedPhase
                                                        }
                                                        draftRounds = newDraft
                                                        null
                                                    }
                                                },
                                                modifier = Modifier.weight(1f),
                                                border = BorderStroke(2.dp, if (leftSelected) GrassGreen else dialogTextColor)
                                            ) {
                                                Text(leftName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            }

                                            Text(stringResource(R.string.vs), color = dialogTextColor)

                                            OutlinedButton(
                                                onClick = {
                                                    errorText = null
                                                    selectedSlotIndex = if (selectedSlotIndex == null) {
                                                        rightIndex
                                                    } else {
                                                        val a = selectedSlotIndex!!
                                                        val b = rightIndex
                                                        val updatedPhase = swapRoundSlots(phase, a, b)
                                                        val newDraft = draftRounds.toMutableList().apply {
                                                            this[selectedPhaseIndex] = updatedPhase
                                                        }
                                                        draftRounds = newDraft
                                                        null
                                                    }
                                                },
                                                modifier = Modifier.weight(1f),
                                                border = BorderStroke(2.dp, if (rightSelected) GrassGreen else dialogTextColor)
                                            ) {
                                                Text(rightName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (errorText != null) {
                            Text(errorText!!, color = Color(0xFFFF5252))
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val error = validateLeagueRounds(selectedTeams, draftRounds)
                            if (error != null) {
                                errorText = error
                            } else {
                                matches = buildLeagueMatchesFromRounds(draftRounds)
                                setupDone = true
                                showLeagueCalendarPickerDialog = false
                            }
                        }
                    ) {
                        Text(stringResource(R.string.done), color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showLeagueCalendarPickerDialog = false
                            showLeagueSetupDialog = true
                        }
                    ) {
                        Text(stringResource(R.string.cancel), color = dialogTextColor)
                    }
                }
            )
        }

        val canStartLeague = selectedTeams.size in 2..maxTeams
        val canSaveLeague = selectedTeams.isNotEmpty() && matches.isNotEmpty()
        if (isCompactWidth) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        enabled = canStartLeague,
                        onClick = {
                            if (canStartLeague) {
                                showCalendar = !showCalendar
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (showCalendar) stringResource(R.string.hide_calendar) else stringResource(R.string.show_calendar),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Button(
                        enabled = selectedTeams.isNotEmpty(),
                        onClick = {
                            if (selectedTeams.isNotEmpty()) {
                                showTable = !showTable
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            if (showTable) stringResource(R.string.hide_table) else stringResource(R.string.show_table),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Button(
                    enabled = canSaveLeague,
                    onClick = {
                        if (canSaveLeague) {
                            val existing = activeLeague
                            if (existing == null) {
                                pendingLeagueName = ""
                                showLeagueNameDialog = true
                            } else {
                                val league = updateLeague(
                                    context,
                                    existing.copy(
                                        time = System.currentTimeMillis(),
                                        teams = selectedTeams,
                                        matches = matches,
                                        winPoints = winPoints,
                                        drawPoints = drawPoints,
                                        lossPoints = lossPoints,
                                        randomCalendar = randomCalendar
                                    )
                                )
                                activeLeague = league
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save_league), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    enabled = canStartLeague,
                    onClick = {
                        if (canStartLeague) {
                            showCalendar = !showCalendar
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        if (showCalendar) stringResource(R.string.hide_calendar) else stringResource(R.string.show_calendar),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Button(
                    enabled = selectedTeams.isNotEmpty(),
                    onClick = {
                        if (selectedTeams.isNotEmpty()) {
                            showTable = !showTable
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        if (showTable) stringResource(R.string.hide_table) else stringResource(R.string.show_table),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Button(
                    enabled = canSaveLeague,
                    onClick = {
                        if (canSaveLeague) {
                            val existing = activeLeague
                            if (existing == null) {
                                pendingLeagueName = ""
                                showLeagueNameDialog = true
                            } else {
                                val league = updateLeague(
                                    context,
                                    existing.copy(
                                        time = System.currentTimeMillis(),
                                        teams = selectedTeams,
                                        matches = matches,
                                        winPoints = winPoints,
                                        drawPoints = drawPoints,
                                        lossPoints = lossPoints,
                                        randomCalendar = randomCalendar
                                    )
                                )
                                activeLeague = league
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.save_league), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        if (showLeagueNameDialog) {
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = { showLeagueNameDialog = false },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = { Text(stringResource(R.string.league_name_title), color = dialogTextColor) },
                text = {
                    OutlinedTextField(
                        value = pendingLeagueName,
                        onValueChange = { pendingLeagueName = it },
                        label = { Text(stringResource(R.string.name)) },
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val league = addLeague(
                                context,
                                pendingLeagueName.trim(),
                                selectedTeams,
                                matches,
                                winPoints,
                                drawPoints,
                                lossPoints,
                                randomCalendar
                            )
                            activeLeague = league
                            showLeagueNameDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLeagueNameDialog = false }) {
                        Text(stringResource(R.string.cancel), color = dialogTextColor)
                    }
                }
            )
        }

        if (showWinnerDialog && currentWinnerName != null) {
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            AlertDialog(
                onDismissRequest = { showWinnerDialog = false },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                text = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 190.dp)
                            .background(Color(0xFF1B5E20), RoundedCornerShape(20.dp))
                            .padding(vertical = 22.dp, horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CelebrationConfetti(
                            modifier = Modifier.matchParentSize(),
                            triggerKey = currentWinnerName
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("🎉", fontSize = 24.sp)
                                GoldenCupTrophy(modifier = Modifier.size(68.dp))
                                Text("🎉", fontSize = 24.sp)
                            }
                            Text(
                                text = stringResource(R.string.champion_title),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Text(
                                text = currentWinnerName,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showWinnerDialog = false }) {
                        Text(stringResource(R.string.close), color = dialogTextColor)
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (showCalendar && matches.isNotEmpty()) {
                val matchesPerFecha = (selectedTeams.size / 2).coerceAtLeast(1)
                item {
                    Text(
                        text = stringResource(R.string.match_calendar_title),
                        color = screenTextColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                }
                itemsIndexed(matches) { index, m ->
                    val fecha = (index / matchesPerFecha) + 1
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        tonalElevation = 2.dp,
                        color = cardColor
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.matchday_format, fecha),
                                color = screenSecondaryTextColor,
                                fontSize = 12.sp
                            )
                            Text(
                                text = m.home.name + " vs " + m.away.name,
                                color = screenTextColor,
                                fontWeight = FontWeight.SemiBold
                            )
                            val goalsText = if (m.homeGoals != null && m.awayGoals != null) {
                                m.homeGoals.toString() + " - " + m.awayGoals.toString()
                            } else {
                                stringResource(R.string.no_result)
                            }
                            Text(
                                text = stringResource(R.string.result_with_value, goalsText),
                                color = screenSecondaryTextColor,
                                fontSize = 12.sp
                            )
                            if (m.homeYellows != 0 || m.awayYellows != 0 || m.homeReds != 0 || m.awayReds != 0) {
                                val yellow = stringResource(R.string.yellow_short)
                                val red = stringResource(R.string.red_short)
                                Text(
                                    text = stringResource(
                                        R.string.cards_format,
                                        yellow,
                                        m.homeYellows,
                                        m.awayYellows,
                                        red,
                                        m.homeReds,
                                        m.awayReds
                                    ),
                                    color = screenSecondaryTextColor,
                                    fontSize = 12.sp
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { editingMatch = m }) {
                                    Text(stringResource(R.string.edit), color = screenTextColor)
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(12.dp))
                }
            }

            if (showTable && selectedTeams.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.league_table_title),
                        color = screenTextColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = cardColor
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(stringResource(R.string.rank_short), modifier = Modifier.width(24.dp), color = screenSecondaryTextColor, fontSize = 12.sp)
                                Text(stringResource(R.string.team_label), modifier = Modifier.weight(1f), color = screenSecondaryTextColor, fontSize = 12.sp)
                                Text(stringResource(R.string.points_short), modifier = Modifier.width(40.dp), color = screenSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                Text(stringResource(R.string.played_short), modifier = Modifier.width(36.dp), color = screenSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                Text(stringResource(R.string.goals_for_short), modifier = Modifier.width(36.dp), color = screenSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                Text(stringResource(R.string.goals_against_short), modifier = Modifier.width(36.dp), color = screenSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                                Text(stringResource(R.string.goal_diff_short), modifier = Modifier.width(40.dp), color = screenSecondaryTextColor, fontSize = 12.sp, textAlign = TextAlign.End)
                            }
                            Spacer(Modifier.height(4.dp))
                            table.forEachIndexed { index, s ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = (index + 1).toString(),
                                        modifier = Modifier.width(24.dp),
                                        color = screenTextColor,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = s.team.name,
                                        modifier = Modifier.weight(1f),
                                        color = screenTextColor,
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = s.points.toString(),
                                        modifier = Modifier.width(40.dp),
                                        color = screenTextColor,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = s.played.toString(),
                                        modifier = Modifier.width(36.dp),
                                        color = screenTextColor,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = s.goalsFor.toString(),
                                        modifier = Modifier.width(36.dp),
                                        color = screenTextColor,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = s.goalsAgainst.toString(),
                                        modifier = Modifier.width(36.dp),
                                        color = screenTextColor,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = s.goalDiff.toString(),
                                        modifier = Modifier.width(40.dp),
                                        color = screenTextColor,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showLeagueHistory) {
            val context = LocalContext.current
            var leagues by remember(showLeagueHistory) { mutableStateOf(loadLeagues(context)) }
            var deleteMode by remember(showLeagueHistory) { mutableStateOf(false) }
            var selectedLeaguesForDelete by remember(showLeagueHistory) { mutableStateOf<Set<Long>>(emptySet()) }
            val dialogContainerColor = MaterialTheme.colorScheme.surface
            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

            AlertDialog(
                onDismissRequest = {
                    showLeagueHistory = false
                    deleteMode = false
                    selectedLeaguesForDelete = emptySet()
                },
                containerColor = dialogContainerColor,
                titleContentColor = dialogTextColor,
                textContentColor = dialogTextColor,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.league_history_title), color = dialogTextColor)
                        IconButton(
                            onClick = {
                                if (!deleteMode) {
                                    deleteMode = true
                                    selectedLeaguesForDelete = emptySet()
                                } else {
                                    val toDeleteIds = selectedLeaguesForDelete
                                    if (toDeleteIds.isNotEmpty()) {
                                        val updated = leagues.filterNot { toDeleteIds.contains(it.id) }
                                        leagues = updated
                                        saveLeagues(context, updated)
                                    }
                                    deleteMode = false
                                    selectedLeaguesForDelete = emptySet()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = if (deleteMode) Color(0xFFFF5252) else dialogTextColor
                            )
                        }
                    }
                },
                text = {
                    if (leagues.isEmpty()) {
                        Text(stringResource(R.string.no_saved_leagues), color = dialogTextColor)
                    } else {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LazyColumn(modifier = Modifier.heightIn(max = 360.dp)) {
                                items(leagues) { l ->
                                    val isSelectedToDelete = deleteMode && selectedLeaguesForDelete.contains(l.id)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (isSelectedToDelete) Color(0xFF424242) else Color.Transparent,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                if (deleteMode) {
                                                    selectedLeaguesForDelete = if (isSelectedToDelete) {
                                                        selectedLeaguesForDelete - l.id
                                                    } else {
                                                        selectedLeaguesForDelete + l.id
                                                    }
                                                } else {
                                                    activeLeague = l
                                                    showLeagueHistory = false
                                                }
                                            }
                                            .padding(vertical = 8.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(l.name, fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                            Spacer(Modifier.height(4.dp))
                                            val info = stringResource(R.string.teams_info_format, l.teams.size)
                                            Text(info, color = dialogSecondaryTextColor, fontSize = 12.sp)
                                        }
                                        if (deleteMode) {
                                            Checkbox(
                                                checked = isSelectedToDelete,
                                                onCheckedChange = { checked ->
                                                    selectedLeaguesForDelete = if (checked) {
                                                        selectedLeaguesForDelete + l.id
                                                    } else {
                                                        selectedLeaguesForDelete - l.id
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showLeagueHistory = false }) {
                        Text(stringResource(R.string.close), color = dialogTextColor)
                    }
                }
            )
        }

        if (editingMatch != null) {
            val m = editingMatch!!
            var homeGoalsText by remember(m.id) { mutableStateOf(m.homeGoals?.toString() ?: "") }
            var awayGoalsText by remember(m.id) { mutableStateOf(m.awayGoals?.toString() ?: "") }
            var homeYellowsText by remember(m.id) { mutableStateOf(m.homeYellows.toString()) }
            var awayYellowsText by remember(m.id) { mutableStateOf(m.awayYellows.toString()) }
            var homeRedsText by remember(m.id) { mutableStateOf(m.homeReds.toString()) }
            var awayRedsText by remember(m.id) { mutableStateOf(m.awayReds.toString()) }

            var homeGoalsCleared by remember(m.id) { mutableStateOf(false) }
            var awayGoalsCleared by remember(m.id) { mutableStateOf(false) }
            var homeYellowsCleared by remember(m.id) { mutableStateOf(false) }
            var awayYellowsCleared by remember(m.id) { mutableStateOf(false) }
            var homeRedsCleared by remember(m.id) { mutableStateOf(false) }
            var awayRedsCleared by remember(m.id) { mutableStateOf(false) }

            val dialogTextColor = MaterialTheme.colorScheme.onSurface
            val leagueEditFieldColors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = dialogTextColor,
                unfocusedTextColor = dialogTextColor,
                disabledTextColor = dialogTextColor,
                cursorColor = dialogTextColor,
                focusedLabelColor = dialogTextColor,
                unfocusedLabelColor = dialogTextColor,
                focusedBorderColor = dialogTextColor,
                unfocusedBorderColor = dialogTextColor
            )

            Dialog(onDismissRequest = { editingMatch = null }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = m.home.name + " vs " + m.away.name,
                            color = dialogTextColor,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(stringResource(R.string.goals), color = dialogTextColor)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = homeGoalsText,
                                onValueChange = { homeGoalsText = it.filter { c -> c.isDigit() } },
                                label = { Text(m.home.name) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                colors = leagueEditFieldColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && !homeGoalsCleared) {
                                            homeGoalsText = ""
                                            homeGoalsCleared = true
                                        }
                                    }
                            )
                            OutlinedTextField(
                                value = awayGoalsText,
                                onValueChange = { awayGoalsText = it.filter { c -> c.isDigit() } },
                                label = { Text(m.away.name) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                colors = leagueEditFieldColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && !awayGoalsCleared) {
                                            awayGoalsText = ""
                                            awayGoalsCleared = true
                                        }
                                    }
                            )
                        }
                        Text(stringResource(R.string.yellow_cards), color = dialogTextColor)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = homeYellowsText,
                                onValueChange = { homeYellowsText = it.filter { c -> c.isDigit() } },
                                label = { Text(m.home.name) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                colors = leagueEditFieldColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && !homeYellowsCleared) {
                                            homeYellowsText = ""
                                            homeYellowsCleared = true
                                        }
                                    }
                            )
                            OutlinedTextField(
                                value = awayYellowsText,
                                onValueChange = { awayYellowsText = it.filter { c -> c.isDigit() } },
                                label = { Text(m.away.name) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                colors = leagueEditFieldColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && !awayYellowsCleared) {
                                            awayYellowsText = ""
                                            awayYellowsCleared = true
                                        }
                                    }
                            )
                        }
                        Text(stringResource(R.string.red_cards), color = dialogTextColor)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = homeRedsText,
                                onValueChange = { homeRedsText = it.filter { c -> c.isDigit() } },
                                label = { Text(m.home.name) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                colors = leagueEditFieldColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && !homeRedsCleared) {
                                            homeRedsText = ""
                                            homeRedsCleared = true
                                        }
                                    }
                            )
                            OutlinedTextField(
                                value = awayRedsText,
                                onValueChange = { awayRedsText = it.filter { c -> c.isDigit() } },
                                label = { Text(m.away.name) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                colors = leagueEditFieldColors,
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { state ->
                                        if (state.isFocused && !awayRedsCleared) {
                                            awayRedsText = ""
                                            awayRedsCleared = true
                                        }
                                    }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { editingMatch = null }) {
                                Text(stringResource(R.string.cancel), color = Color.White)
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = {
                                val newHomeGoals = homeGoalsText.toIntOrNull()
                                val newAwayGoals = awayGoalsText.toIntOrNull()
                                val newHomeYellows = homeYellowsText.toIntOrNull() ?: 0
                                val newAwayYellows = awayYellowsText.toIntOrNull() ?: 0
                                val newHomeReds = homeRedsText.toIntOrNull() ?: 0
                                val newAwayReds = awayRedsText.toIntOrNull() ?: 0

                                val updated = m.copy(
                                    homeGoals = newHomeGoals,
                                    awayGoals = newAwayGoals,
                                    homeYellows = newHomeYellows,
                                    awayYellows = newAwayYellows,
                                    homeReds = newHomeReds,
                                    awayReds = newAwayReds
                                )
                                val newMatches = matches.map { if (it.id == m.id) updated else it }
                                matches = newMatches

                                val existing = activeLeague
                                if (existing != null) {
                                    val newLeague = updateLeague(
                                        context,
                                        existing.copy(
                                            time = System.currentTimeMillis(),
                                            matches = newMatches,
                                            winPoints = winPoints,
                                            drawPoints = drawPoints,
                                            lossPoints = lossPoints,
                                            randomCalendar = randomCalendar
                                        )
                                    )
                                    activeLeague = newLeague
                                }
                                editingMatch = null
                            }) {
                                Text(stringResource(R.string.save), color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayersApp(
    modifier: Modifier = Modifier,
    registerSecretOpener: (() -> Unit) -> Unit = {},
    pendingChatPostId: String? = null,
    pendingChatPeerName: String? = null,
    pendingChatIsGroup: Boolean = false,
    clearPendingChat: () -> Unit = {},
    onShowHistory: () -> Unit = {},
    isDarkTheme: Boolean = false
) {
    val context = LocalContext.current
    var players by remember {
        mutableStateOf(loadPlayers(context).toMutableList())
    }
    var accessToken by rememberSaveable { mutableStateOf<String?>(null) }
    var refreshToken by rememberSaveable { mutableStateOf<String?>(null) }
    var showAuthDialog by remember { mutableStateOf(false) }
    var showRegisterDialog by remember { mutableStateOf(false) }
    var showCommunity by remember { mutableStateOf(false) }
    var deepLinkPostId by rememberSaveable { mutableStateOf<String?>(null) }
    var deepLinkPeerName by rememberSaveable { mutableStateOf<String?>(null) }
    var deepLinkIsGroup by rememberSaveable { mutableStateOf(false) }
    var selected by remember { mutableStateOf(emptySet<String>()) }
    var teamA by remember { mutableStateOf<List<Player>>(emptyList()) }
    var teamB by remember { mutableStateOf<List<Player>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showCustomizeDialog by remember { mutableStateOf(false) }
    var showSavedTeamsDialog by remember { mutableStateOf(false) }
    var showTeamEditorDialog by remember { mutableStateOf(false) }
    var editingTeam by remember { mutableStateOf<SavedTeam?>(null) }
    var customTeamATitle by remember { mutableStateOf<String?>(null) }
    var customTeamBTitle by remember { mutableStateOf<String?>(null) }
    var showRenameATeamDialog by remember { mutableStateOf(false) }
    var showRenameBTeamDialog by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(true) }
    var showFullPitchDialog by remember { mutableStateOf(false) }
    var showTournamentDialog by remember { mutableStateOf(false) }
    var showPenaltyGame by remember { mutableStateOf(false) }
    var tournamentTeams by remember { mutableStateOf<List<SavedTeam>>(emptyList()) }
    var showLeagueScreen by remember { mutableStateOf(false) }
    var leagueTeams by remember { mutableStateOf<List<SavedTeam>>(emptyList()) }
    var initialLeague by remember { mutableStateOf<SavedLeague?>(null) }
    var selectTeamsForMatch by remember { mutableStateOf(false) }
    // Selector de deporte para creación de partidos
    val matchSports = remember {
        listOf(
            context.getString(R.string.sport_soccer),
            context.getString(R.string.sport_futbolito),
            context.getString(R.string.sport_baby_soccer),
            context.getString(R.string.sport_padel),
            context.getString(R.string.sport_tennis),
            context.getString(R.string.sport_volleyball),
            context.getString(R.string.sport_rugby)
        )
    }
    var matchSport by remember { mutableStateOf(context.getString(R.string.sport_futbolito)) }
    var matchSportExpanded by remember { mutableStateOf(false) }
    val teamColors = remember {
        listOf(
            context.getString(R.string.color_white),
            context.getString(R.string.color_black),
            context.getString(R.string.color_blue),
            context.getString(R.string.color_red),
            context.getString(R.string.color_yellow),
            context.getString(R.string.color_green),
            context.getString(R.string.color_purple)
        )
    }
    data class NationOption(val key: String, val label: String)
    val nationOptions = remember {
        listOf(
            NationOption("brazil", context.getString(R.string.nation_brazil)),
            NationOption("argentina", context.getString(R.string.nation_argentina)),
            NationOption("spain", context.getString(R.string.nation_spain)),
            NationOption("france", context.getString(R.string.nation_france)),
            NationOption("germany", context.getString(R.string.nation_germany)),
            NationOption("italy", context.getString(R.string.nation_italy)),
            NationOption("netherlands", context.getString(R.string.nation_netherlands)),
            NationOption("portugal", context.getString(R.string.nation_portugal)),
            NationOption("england", context.getString(R.string.nation_england)),
            NationOption("uruguay", context.getString(R.string.nation_uruguay)),
            NationOption("belgium", context.getString(R.string.nation_belgium)),
            NationOption("united_states", context.getString(R.string.nation_united_states))
        )
    }
    fun nationLabel(key: String): String = nationOptions.firstOrNull { it.key == key }?.label ?: key

    var teamAUseNations by remember { mutableStateOf(false) }
    var teamBUseNations by remember { mutableStateOf(false) }
    var teamANationKey by remember { mutableStateOf("brazil") }
    var teamBNationKey by remember { mutableStateOf("france") }
    var teamAColorName by remember { mutableStateOf(context.getString(R.string.color_yellow)) }
    var teamBColorName by remember { mutableStateOf(context.getString(R.string.color_white)) }
    var teamAColorExpanded by remember { mutableStateOf(false) }
    var teamBColorExpanded by remember { mutableStateOf(false) }
    var teamAClubsExpanded by remember { mutableStateOf(false) }
    var teamBClubsExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val mainListState = rememberLazyListState()
    var showPlayersList by remember { mutableStateOf(true) }

    fun scrollToResults() {
        scope.launch {
            delay(100)
            val index = players.size
            mainListState.animateScrollToItem(index.coerceAtLeast(0))
        }
    }

    val selectedPlayers = remember(selected, players) {
        players.filter { selected.contains(it.name) }
    }

    // No más menú secreto; las acciones ahora son visibles en la UI
    registerSecretOpener { /* sin-op */ }

    LaunchedEffect(pendingChatPostId, pendingChatPeerName, pendingChatIsGroup) {
        if (!pendingChatPostId.isNullOrBlank() || !pendingChatPeerName.isNullOrBlank()) {
            deepLinkPostId = pendingChatPostId
            deepLinkPeerName = pendingChatPeerName
            deepLinkIsGroup = pendingChatIsGroup
            showCommunity = true
            clearPendingChat()
        }
    }

    LaunchedEffect(Unit) {
        try {
            val (at, rt) = loadTokens(context)
            accessToken = at
            refreshToken = rt
            if (at != null) {
                val (remote, maybeAt) = withContext(Dispatchers.IO) { fetchPlayersRemote(context, at, rt) }
                if (maybeAt != at) {
                    accessToken = maybeAt
                    saveTokens(context, maybeAt, refreshToken)
                }
                if (remote != null) {
                    players = remote.toMutableList()
                    savePlayers(context, players)
                }
                startGlobalChatListener(context)
            }
        } catch (_: Exception) {
            // En caso de error de red u otro fallo en el arranque, seguimos con datos locales
        }
    }

    if (showCommunity) {
        CommunityScreen(
            onBack = { showCommunity = false },
            initialPostId = deepLinkPostId,
            initialPeerName = deepLinkPeerName,
            initialIsGroup = deepLinkIsGroup,
            isDarkTheme = isDarkTheme
        )
        return
    }

    if (showLeagueScreen) {
        val startWithHistory = leagueTeams.isEmpty()
        LeagueScreen(
            teams = leagueTeams,
            onBack = { showLeagueScreen = false },
            startWithHistory = startWithHistory,
            initialLeague = initialLeague
        )
        return
    }

    if (showTournamentDialog && tournamentTeams.isNotEmpty()) {
        TournamentBracketDialog(
            teams = tournamentTeams,
            onDismiss = {
                showTournamentDialog = false
                tournamentTeams = emptyList()
            }
        )
        return
    }

    if (showSavedTeamsDialog && !selectTeamsForMatch) {
        SavedTeamsScreen(
            players = players,
            onBack = { showSavedTeamsDialog = false },
            onStartTournament = { selectedTeams ->
                tournamentTeams = selectedTeams
                showTournamentDialog = true
                showSavedTeamsDialog = false
            },
            onOpenLeague = { allTeams ->
                leagueTeams = allTeams
                initialLeague = null
                showLeagueScreen = true
                showSavedTeamsDialog = false
            },
            onOpenSavedLeague = { league ->
                leagueTeams = league.teams
                initialLeague = league
                showLeagueScreen = true
                showSavedTeamsDialog = false
            }
        )
        return
    }

    if (showSavedTeamsDialog && selectTeamsForMatch) {
        val context = LocalContext.current
        var teams by remember(showSavedTeamsDialog) { mutableStateOf(loadTeams(context)) }
        var pendingDeleteTeam by remember(showSavedTeamsDialog) { mutableStateOf<SavedTeam?>(null) }
        var selectedTournamentTeams by remember(showSavedTeamsDialog) { mutableStateOf<Set<Long>>(emptySet()) }
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        val dialogPanelColor = MaterialTheme.colorScheme.surfaceVariant

        BackHandler {
            selectTeamsForMatch = false
            showSavedTeamsDialog = false
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = {
                        selectTeamsForMatch = false
                        showSavedTeamsDialog = false
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = dialogTextColor
                        )
                    }
                    Text(
                        text = stringResource(R.string.saved_teams_title),
                        color = dialogTextColor,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(Modifier.height(8.dp))

                if (teams.isEmpty()) {
                    Text(stringResource(R.string.no_saved_teams), color = dialogTextColor)
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(dialogPanelColor, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.select_two_teams_for_match),
                                color = dialogTextColor,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            items(teams) { t ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    var expanded by remember(t.id) { mutableStateOf(false) }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { expanded = !expanded },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(t.name, fontWeight = FontWeight.SemiBold, color = dialogTextColor)
                                            Spacer(Modifier.height(2.dp))
                                            Text(
                                                text = stringResource(R.string.players_count_parentheses, t.players.size),
                                                color = dialogSecondaryTextColor,
                                                fontSize = 12.sp
                                            )
                                        }
                                        Checkbox(
                                            checked = selectedTournamentTeams.contains(t.id),
                                            onCheckedChange = { checked ->
                                                selectedTournamentTeams = if (checked) {
                                                    selectedTournamentTeams + t.id
                                                } else {
                                                    selectedTournamentTeams - t.id
                                                }
                                            }
                                        )
                                        Icon(
                                            imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                            contentDescription = null,
                                            tint = dialogTextColor
                                        )
                                    }
                                    if (expanded) {
                                        Spacer(Modifier.height(4.dp))
                                        t.players.forEach { p ->
                                            Text(
                                                text = stringResource(R.string.bullet_player_name, p.name),
                                                color = dialogSecondaryTextColor,
                                                fontSize = 12.sp
                                            )
                                        }
                                        Spacer(Modifier.height(4.dp))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                editingTeam = t
                                                showTeamEditorDialog = true
                                            },
                                            baseColor = GrassGreen,
                                            border = null,
                                            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                                        ) {
                                            Icon(
                                                Icons.Filled.Edit,
                                                contentDescription = stringResource(R.string.edit),
                                                modifier = Modifier.size(20.dp),
                                                tint = Color.White
                                            )
                                        }
                                        OutlinedButton(
                                            onClick = { pendingDeleteTeam = t },
                                            baseColor = GrassGreen,
                                            border = null,
                                            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                                        ) {
                                            Icon(
                                                Icons.Filled.Delete,
                                                contentDescription = stringResource(R.string.delete),
                                                modifier = Modifier.size(20.dp),
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val selectedTeamsList = teams.filter { selectedTournamentTeams.contains(it.id) }
                    val canUseForMatch = selectedTeamsList.size == 2

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            enabled = canUseForMatch,
                            onClick = {
                                if (canUseForMatch) {
                                    val team1 = selectedTeamsList[0]
                                    val team2 = selectedTeamsList[1]
                                    teamA = team1.players
                                    teamB = team2.players
                                    customTeamATitle = team1.name
                                    customTeamBTitle = team2.name
                                    showResults = true
                                    scrollToResults()
                                    selectTeamsForMatch = false
                                    showSavedTeamsDialog = false
                                }
                            },
                            baseColor = GrassGreen,
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.create_match))
                        }

                        Button(
                            onClick = {
                                editingTeam = null
                                showTeamEditorDialog = true
                            },
                            baseColor = GrassGreen,
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.create_team))
                        }

                        Button(
                            onClick = {
                                selectTeamsForMatch = false
                                showSavedTeamsDialog = false
                            },
                            baseColor = GrassGreen,
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.close))
                        }
                    }
                }

                if (pendingDeleteTeam != null) {
                    val t = pendingDeleteTeam!!
                    val dialogContainerColor = MaterialTheme.colorScheme.surface
                    val dialogTextColor2 = MaterialTheme.colorScheme.onSurface
                    AlertDialog(
                        onDismissRequest = { pendingDeleteTeam = null },
                        containerColor = dialogContainerColor,
                        titleContentColor = dialogTextColor2,
                        textContentColor = dialogTextColor2,
                        title = { Text(stringResource(R.string.delete_team_title), color = dialogTextColor2) },
                        text = { Text(stringResource(R.string.delete_team_confirm, t.name), color = dialogTextColor2) },
                        dismissButton = {
                            TextButton(onClick = { pendingDeleteTeam = null }) {
                                Text(stringResource(R.string.cancel), color = dialogTextColor2)
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                deleteTeam(context, t.id)
                                teams = loadTeams(context)
                                pendingDeleteTeam = null
                            }) {
                                Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    )
                }

                if (showTeamEditorDialog) {
                    val initial = editingTeam
                    val initialName = initial?.name ?: ""
                    val initialPlayers = initial?.players ?: emptyList()
                    EditSavedTeamDialog(
                        allPlayers = players,
                        initialName = initialName,
                        initialPlayers = initialPlayers,
                        onSave = { name, selectedPlayers ->
                            if (initial == null) {
                                addTeam(context, name, selectedPlayers)
                            } else {
                                updateTeam(context, initial.copy(name = name, players = selectedPlayers))
                            }
                            teams = loadTeams(context)
                            showTeamEditorDialog = false
                        },
                        onDismiss = { showTeamEditorDialog = false }
                    )
                }
            }
        }
        return
    }

    if (showCustomizeDialog) {
        CustomizeTeamsDialog(
            players = players,
            initialA = teamA,
            initialB = teamB,
            onApply = { a, b ->
                teamA = a
                teamB = b
                showResults = true
                scrollToResults()
            },
            onOpenSavedTeams = {
                showCustomizeDialog = false
                selectTeamsForMatch = true
                showSavedTeamsDialog = true
            },
            onDismiss = { showCustomizeDialog = false }
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isLoggedIn = accessToken != null
                if (isLoggedIn) {
                    Button(
                        onClick = { showAuthDialog = true },
                        baseColor = LogoutRed,
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                    ) {
                        Text(stringResource(R.string.logout))
                    }
                } else {
                    OutlinedButton(
                        onClick = { showAuthDialog = true },
                        baseColor = MaterialTheme.colorScheme.primary,
                        border = null,
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text(stringResource(R.string.login))
                    }
                }
                if (accessToken != null) {
                    Button(
                        onClick = { showCommunity = true },
                        baseColor = Gold,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Text(stringResource(R.string.open_community))
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 64.dp),
                onClick = {
                    error = null
                    val n = selectedPlayers.size
                    if (n < 2) {
                        error = "Selecciona al menos 2 jugadores"
                        return@Button
                    }
                    
                    // Filtrar arqueros y jugadores de campo
                    val gksSel = selectedPlayers.filter { it.isGoalkeeper }
                    val fieldPlayers = selectedPlayers.filterNot { it.isGoalkeeper }
                    
                    // Manejar el caso de no tener suficientes arqueros
                    val mustInclude = when {
                        gksSel.size >= 2 -> gksSel.shuffled().take(2)
                        gksSel.size == 1 -> gksSel + fieldPlayers.shuffled().first()
                        else -> fieldPlayers.shuffled().take(2)
                    }
                    
                    // Tomar el resto de jugadores necesarios
                    val remainingPlayers = (selectedPlayers - mustInclude.toSet()).shuffled()
                    val restCount = (n - mustInclude.size).coerceAtLeast(0)
                    val chosen = mustInclude + remainingPlayers.take(restCount)
                    
                    // Generar equipos balanceados
                    val result = generateBalancedTeams(chosen)
                    teamA = result.first
                    teamB = result.second
                    
                    // Mostrar los equipos generados
                    showResults = true
                    scrollToResults()
                },
                baseColor = GrassGreen,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                // Ícono de balanza clásica para representar generación equilibrada de equipos
                Icon(
                    painter = painterResource(R.drawable.balance),
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Color.White
                )
            }

            // VS: personalizar equipos
            Button(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 64.dp),
                onClick = { showCustomizeDialog = true },
                baseColor = GrassGreen,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    stringResource(R.string.vs_upper),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            // Copa: equipos guardados
            Button(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 64.dp),
                onClick = {
                    // Al abrir el menú general de equipos guardados
                    // aseguramos que no esté activo el modo de selección para partido
                    selectTeamsForMatch = false
                    showSavedTeamsDialog = true
                },
                baseColor = GrassGreen,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                // Botón de Equipos guardados con icono de trofeo
                Icon(
                    imageVector = Icons.Filled.EmojiEvents,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Color.White
                )
            }

            // Deshacer: limpiar equipos A y B
            Button(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 64.dp),
                onClick = {
                    teamA = emptyList()
                    teamB = emptyList()
                },
                baseColor = GrassGreen,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                // Botón de deshacer solo con icono
                Icon(
                    Icons.AutoMirrored.Filled.Undo,
                    contentDescription = stringResource(R.string.undo),
                    modifier = Modifier.size(56.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            onClick = { showPenaltyGame = true },
            baseColor = GrassGreen,
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Icon(
                imageVector = Icons.Filled.SportsEsports,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = Color.White
            )
            Spacer(Modifier.width(8.dp))
            Text("Penales 1 jugador", color = Color.White, fontWeight = FontWeight.SemiBold)
        }

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = androidx.compose.material3.MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.select_players_and_count),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Jugadores: ${players.size}",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.enabled_count, selectedPlayers.size),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.height(4.dp))
        val allSelected = players.isNotEmpty() && selected.size == players.size
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = allSelected,
                    onCheckedChange = { checked ->
                        selected = if (checked) players.map { it.name }.toSet() else emptySet()
                    }
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    stringResource(R.string.select_all),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Button(
                onClick = { showEditDialog = true },
                baseColor = GrassGreen,
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonAdd,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(Modifier.width(6.dp))
                Text(stringResource(R.string.edit), color = Color.White)
            }
        }
        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { showPlayersList = !showPlayersList },
            baseColor = GrassGreen,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropUp,
                        contentDescription = null
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.players_tap_to_select),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f), state = mainListState) {
            if (showPlayersList) {
                items(players) { p ->
                    val checked = selected.contains(p.name)
                    PlayerRow(
                        player = p,
                        checked = checked,
                        onCheckedChange = { isChecked ->
                            selected = if (isChecked) selected + p.name else selected - p.name
                        }
                    )
                    HorizontalDivider()
                }
            }

            if (teamA.isNotEmpty() && teamB.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(12.dp))
                    val titleA = customTeamATitle ?: stringResource(R.string.team_a)
                    val titleB = customTeamBTitle ?: stringResource(R.string.team_b)
                    val teamAKitKey = if (teamAUseNations) teamANationKey else clubKitKeyFromSelection(teamAColorName)
                    val teamBKitKey = if (teamBUseNations) teamBNationKey else clubKitKeyFromSelection(teamBColorName)
                    val teamAVisualColor = if (teamAUseNations) nationPrimaryComposeColor(teamANationKey) else teamColorFromName(teamAColorName)
                    val teamBVisualColor = if (teamBUseNations) nationPrimaryComposeColor(teamBNationKey) else teamColorFromName(teamBColorName)
                    val teamAVisualAndroidColor = if (teamAUseNations) nationPrimaryAndroidColor(teamANationKey) else teamAndroidColorFromName(teamAColorName)
                    val teamBVisualAndroidColor = if (teamBUseNations) nationPrimaryAndroidColor(teamBNationKey) else teamAndroidColorFromName(teamBColorName)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showResults = !showResults }) {
                            Text(if (showResults) stringResource(R.string.hide_results) else stringResource(R.string.show_results))
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = matchSport,
                                onValueChange = { },
                                label = { Text(stringResource(R.string.sport_label)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { matchSportExpanded = true },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { matchSportExpanded = !matchSportExpanded }) {
                                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.onSurface,
                                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            DropdownMenu(
                                expanded = matchSportExpanded,
                                onDismissRequest = { matchSportExpanded = false },
                                containerColor = MaterialTheme.colorScheme.surface
                            ) {
                                matchSports.forEach { s ->
                                    DropdownMenuItem(
                                        text = { Text(s, color = MaterialTheme.colorScheme.onSurface) },
                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                        onClick = {
                                            matchSport = s
                                            matchSportExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(0.9f)) {
                            OutlinedTextField(
                                value = if (teamAUseNations) nationLabel(teamANationKey) else teamAColorName,
                                onValueChange = { },
                                label = { Text(stringResource(R.string.team_a_kit_label)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { teamAColorExpanded = true },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { teamAColorExpanded = !teamAColorExpanded }) {
                                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.onSurface,
                                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            DropdownMenu(
                                expanded = teamAColorExpanded,
                                onDismissRequest = {
                                    teamAColorExpanded = false
                                    teamAClubsExpanded = false
                                },
                                containerColor = MaterialTheme.colorScheme.surface
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            if (teamAUseNations) stringResource(R.string.switch_to_colors) else stringResource(R.string.switch_to_nations),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                    onClick = {
                                        teamAUseNations = !teamAUseNations
                                        teamAClubsExpanded = false
                                    }
                                )
                                HorizontalDivider()
                                if (teamAUseNations) {
                                    nationOptions.forEach { nation ->
                                        DropdownMenuItem(
                                            text = { Text(nation.label, color = MaterialTheme.colorScheme.onSurface) },
                                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                            onClick = {
                                                teamANationKey = nation.key
                                                teamAColorExpanded = false
                                            }
                                        )
                                    }
                                } else {
                                    teamColors.forEach { cName ->
                                        DropdownMenuItem(
                                            text = { Text(cName, color = MaterialTheme.colorScheme.onSurface) },
                                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                            onClick = {
                                                teamAColorName = cName
                                                teamAColorExpanded = false
                                            }
                                        )
                                    }
                                    HorizontalDivider()
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.clubs_menu), color = MaterialTheme.colorScheme.onSurface) },
                                        trailingIcon = {
                                            Icon(
                                                imageVector = if (teamAClubsExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        },
                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                        onClick = { teamAClubsExpanded = !teamAClubsExpanded }
                                    )
                                    if (teamAClubsExpanded) {
                                        HorizontalDivider()
                                        CLUB_KIT_OPTIONS.forEach { kit ->
                                            DropdownMenuItem(
                                                text = { Text(kit.label, color = MaterialTheme.colorScheme.onSurface) },
                                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                onClick = {
                                                    teamAColorName = kit.label
                                                    teamAClubsExpanded = false
                                                    teamAColorExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(0.9f)) {
                            OutlinedTextField(
                                value = if (teamBUseNations) nationLabel(teamBNationKey) else teamBColorName,
                                onValueChange = { },
                                label = { Text(stringResource(R.string.team_b_kit_label)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { teamBColorExpanded = true },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { teamBColorExpanded = !teamBColorExpanded }) {
                                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.onSurface,
                                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            DropdownMenu(
                                expanded = teamBColorExpanded,
                                onDismissRequest = {
                                    teamBColorExpanded = false
                                    teamBClubsExpanded = false
                                },
                                containerColor = MaterialTheme.colorScheme.surface
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            if (teamBUseNations) stringResource(R.string.switch_to_colors) else stringResource(R.string.switch_to_nations),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                    onClick = {
                                        teamBUseNations = !teamBUseNations
                                        teamBClubsExpanded = false
                                    }
                                )
                                HorizontalDivider()
                                if (teamBUseNations) {
                                    nationOptions.forEach { nation ->
                                        DropdownMenuItem(
                                            text = { Text(nation.label, color = MaterialTheme.colorScheme.onSurface) },
                                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                            onClick = {
                                                teamBNationKey = nation.key
                                                teamBColorExpanded = false
                                            }
                                        )
                                    }
                                } else {
                                    teamColors.forEach { cName ->
                                        DropdownMenuItem(
                                            text = { Text(cName, color = MaterialTheme.colorScheme.onSurface) },
                                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                            onClick = {
                                                teamBColorName = cName
                                                teamBColorExpanded = false
                                            }
                                        )
                                    }
                                    HorizontalDivider()
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.clubs_menu), color = MaterialTheme.colorScheme.onSurface) },
                                        trailingIcon = {
                                            Icon(
                                                imageVector = if (teamBClubsExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        },
                                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                        onClick = { teamBClubsExpanded = !teamBClubsExpanded }
                                    )
                                    if (teamBClubsExpanded) {
                                        HorizontalDivider()
                                        CLUB_KIT_OPTIONS.forEach { kit ->
                                            DropdownMenuItem(
                                                text = { Text(kit.label, color = MaterialTheme.colorScheme.onSurface) },
                                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.onSurface),
                                                onClick = {
                                                    teamBColorName = kit.label
                                                    teamBClubsExpanded = false
                                                    teamBColorExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    // Actions should be available regardless of results visibility
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)) {
                        Button(onClick = {
                            shareTeamsWithImage(
                                context,
                                titleA,
                                titleB,
                                teamA,
                                teamB,
                                matchSport,
                                teamAVisualAndroidColor,
                                teamBVisualAndroidColor,
                                teamANationKey = teamAKitKey,
                                teamBNationKey = teamBKitKey
                            )
                        }) { Text(stringResource(R.string.share)) }
                        Button(onClick = {
                            scope.launch {
                                if (accessToken.isNullOrBlank()) {
                                    addMatch(context, titleA, titleB, teamA, teamB)
                                } else {
                                    try {
                                        val newAt = withContext(Dispatchers.IO) {
                                            postMatchRemote(context, accessToken, refreshToken, titleA, titleB, teamA, teamB)
                                        }
                                        if (newAt != null && newAt != accessToken) {
                                            accessToken = newAt
                                            saveTokens(context, newAt, refreshToken)
                                        }
                                    } catch (_: Exception) {
                                        // Si falla el guardado remoto, igual guardamos localmente
                                    } finally {
                                        addMatch(context, titleA, titleB, teamA, teamB)
                                    }
                                }
                                onShowHistory()
                            }
                        }) { Text(stringResource(R.string.save_match)) }
                    }
                    if (showResults) {
                        PitchView(
                            teamA = teamA,
                            teamB = teamB,
                            sport = matchSport,
                            teamAColor = teamAVisualColor,
                            teamBColor = teamBVisualColor,
                            teamANationKey = teamAKitKey,
                            teamBNationKey = teamBKitKey
                        )
                        Spacer(Modifier.height(12.dp))
                        TeamsResult(
                            teamA = teamA,
                            teamB = teamB,
                            titleA = titleA,
                            titleB = titleB,
                            onRenameA = { showRenameATeamDialog = true },
                            onRenameB = { showRenameBTeamDialog = true }
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(onClick = { showFullPitchDialog = true }) {
                                Text(stringResource(R.string.view_large_pitch))
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

    }

    if (showPenaltyGame) {
        PenaltyShootoutDialog(onDismiss = { showPenaltyGame = false })
    }

    if (showFullPitchDialog && teamA.isNotEmpty() && teamB.isNotEmpty()) {
        Dialog(onDismissRequest = { showFullPitchDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                PitchView(
                    teamA = teamA,
                    teamB = teamB,
                    sport = matchSport,
                    teamAColor = if (teamAUseNations) nationPrimaryComposeColor(teamANationKey) else teamColorFromName(teamAColorName),
                    teamBColor = if (teamBUseNations) nationPrimaryComposeColor(teamBNationKey) else teamColorFromName(teamBColorName),
                    teamANationKey = if (teamAUseNations) teamANationKey else clubKitKeyFromSelection(teamAColorName),
                    teamBNationKey = if (teamBUseNations) teamBNationKey else clubKitKeyFromSelection(teamBColorName),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }


    if (showEditDialog) {
        EditPlayersDialog(
            players = players,
            onUpdatePlayer = { name, a, d, h ->
                players = players.map { if (it.name == name) it.copy(attack = a, defense = d, physical = h) else it }.toMutableList()
            },
            onAddPlayer = { name, a, d, h, isGoalkeeper ->
                if (players.none { it.name.equals(name, ignoreCase = true) }) {
                    players = (players + Player(name.trim(), a, d, h, isGoalkeeper)).toMutableList()
                    selected = selected + name.trim()
                    savePlayers(context, players)
                    if (!accessToken.isNullOrBlank()) {
                        scope.launch {
                            val newAt = withContext(Dispatchers.IO) { postPlayersBulkRemote(context, accessToken, refreshToken, players) }
                            if (newAt != null && newAt != accessToken) {
                                accessToken = newAt
                                saveTokens(context, newAt, refreshToken)
                            }
                        }
                    }
                }
            },
            onSave = {
                if (!accessToken.isNullOrBlank()) {
                    scope.launch {
                        val newAt = withContext(Dispatchers.IO) { postPlayersBulkRemote(context, accessToken, refreshToken, players) }
                        if (newAt != null && newAt != accessToken) {
                            accessToken = newAt
                            saveTokens(context, newAt, refreshToken)
                        }
                        if (newAt == null) savePlayers(context, players)
                    }
                } else {
                    savePlayers(context, players)
                }
            },
            onDismiss = { showEditDialog = false },
            onRenamePlayer = { oldName, newName ->
                val trimmed = newName.trim()
                if (trimmed.isNotEmpty() && (oldName.equals(trimmed, ignoreCase = true) || players.none { it.name.equals(trimmed, ignoreCase = true) })) {
                    players = players.map { if (it.name == oldName) it.copy(name = trimmed) else it }.toMutableList()
                    val updated = players.find { it.name == trimmed }
                    teamA = teamA.map { if (it.name == oldName) updated ?: it.copy(name = trimmed) else it }
                    teamB = teamB.map { if (it.name == oldName) updated ?: it.copy(name = trimmed) else it }
                    if (selected.contains(oldName)) {
                        selected = selected - oldName + trimmed
                    }
                }
            },
            onDeletePlayer = { name ->
                players = players.filter { it.name != name }.toMutableList()
                selected = selected - name
                teamA = teamA.filter { it.name != name }
                teamB = teamB.filter { it.name != name }
                // Guardar cambios localmente y sincronizar eliminación con backend
                savePlayers(context, players)
                if (!accessToken.isNullOrBlank()) {
                    scope.launch {
                        val newAt = withContext(Dispatchers.IO) {
                            postPlayersBulkRemote(context, accessToken, refreshToken, players)
                        }
                        if (newAt != null && newAt != accessToken) {
                            accessToken = newAt
                            saveTokens(context, newAt, refreshToken)
                        }
                    }
                }
            },
            onToggleGoalkeeper = { name, isGK ->
                players = players.map { if (it.name == name) it.copy(isGoalkeeper = isGK) else it }.toMutableList()
            }
        )
    }

    if (showRenameATeamDialog) {
        val current = customTeamATitle ?: stringResource(R.string.team_a)
        var text by remember(current, showRenameATeamDialog) { mutableStateOf(current) }
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        AlertDialog(
            onDismissRequest = { showRenameATeamDialog = false },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.edit_team_name), color = dialogTextColor) },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text(stringResource(R.string.team_name_label), color = dialogSecondaryTextColor) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = dialogTextColor,
                            unfocusedTextColor = dialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = dialogTextColor,
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = dialogSecondaryTextColor,
                            unfocusedLabelColor = dialogSecondaryTextColor,
                            focusedPlaceholderColor = dialogSecondaryTextColor,
                            unfocusedPlaceholderColor = dialogSecondaryTextColor
                        )
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val t = text.trim()
                    customTeamATitle = if (t.isEmpty()) null else t
                    showRenameATeamDialog = false
                }) { Text(stringResource(R.string.save)) }
            },
            dismissButton = { TextButton(onClick = { showRenameATeamDialog = false }) { Text(stringResource(R.string.cancel), color = dialogTextColor) } }
        )
    }

    if (showRenameBTeamDialog) {
        val current = customTeamBTitle ?: stringResource(R.string.team_b)
        var text by remember(current, showRenameBTeamDialog) { mutableStateOf(current) }
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        AlertDialog(
            onDismissRequest = { showRenameBTeamDialog = false },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.edit_team_name), color = dialogTextColor) },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text(stringResource(R.string.team_name_label), color = dialogSecondaryTextColor) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = dialogTextColor,
                            unfocusedTextColor = dialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = dialogTextColor,
                            focusedLabelColor = dialogSecondaryTextColor,
                            unfocusedLabelColor = dialogSecondaryTextColor
                        )
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val t = text.trim()
                    customTeamBTitle = if (t.isEmpty()) null else t
                    showRenameBTeamDialog = false
                }) { Text(stringResource(R.string.save)) }
            },
            dismissButton = { TextButton(onClick = { showRenameBTeamDialog = false }) { Text(stringResource(R.string.cancel), color = dialogTextColor) } }
        )
    }

    if (showAuthDialog) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var loginError by remember { mutableStateOf<String?>(null) }
        val currentUser = remember { userNameState.value }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var loginPasswordVisible by remember { mutableStateOf(false) }
        var isSubmittingLogin by remember { mutableStateOf(false) }
        val loginPrefs = remember { context.getSharedPreferences("login_prefs", 0) }
        var rememberMe by remember { mutableStateOf(loginPrefs.getBoolean("remember", false)) }
        var isRegister by remember { mutableStateOf(false) }
        var registerName by remember { mutableStateOf("") }
        var registerConfirm by remember { mutableStateOf("") }
        var registerError by remember { mutableStateOf<String?>(null) }
        var isSubmittingRegister by remember { mutableStateOf(false) }
        var canResendVerification by remember { mutableStateOf(false) }
        var resendDone by remember { mutableStateOf(false) }
        var isSubmittingGoogle by remember { mutableStateOf(false) }

        fun setAuthError(message: String) {
            if (isRegister) registerError = message else loginError = message
        }

        val googleClientId = stringResource(R.string.google_server_client_id).trim()
        LaunchedEffect(googleClientId) {
            val masked = when {
                googleClientId.length <= 12 -> googleClientId
                else -> googleClientId.take(6) + "..." + googleClientId.takeLast(6)
            }
            Log.i("Auth", "Google Sign-In configured with google_server_client_id=$masked")
        }
        val activity = context as? Activity
        val googleSignInClient = remember(googleClientId, activity) {
            if (activity == null) return@remember null
            val builder = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            if (googleClientId.isNotBlank() && googleClientId != "YOUR_GOOGLE_OAUTH_SERVER_CLIENT_ID") {
                builder.requestIdToken(googleClientId)
            }
            val opts = builder.build()
            GoogleSignIn.getClient(activity, opts)
        }

        val googleLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                setAuthError(context.getString(R.string.err_google_signin_failed))
                return@rememberLauncherForActivityResult
            }

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = try {
                task.getResult(ApiException::class.java)
            } catch (e: ApiException) {
                Log.e("Auth", "Google Sign-In ApiException statusCode=${e.statusCode}", e)
                val base = context.getString(R.string.err_google_signin_failed)
                setAuthError("$base (codigo: ${e.statusCode})")
                return@rememberLauncherForActivityResult
            } catch (e: Exception) {
                Log.e("Auth", "Google Sign-In failed", e)
                setAuthError(context.getString(R.string.err_google_signin_failed))
                return@rememberLauncherForActivityResult
            }

            val idToken = account?.idToken
            if (idToken.isNullOrBlank()) {
                setAuthError(context.getString(R.string.err_google_missing_token))
                return@rememberLauncherForActivityResult
            }

            isSubmittingGoogle = true
            scope.launch {
                try {
                    val body = JSONObject().put("idToken", idToken)
                    val (code, text) = postJsonWithRetry("/auth/google", body)

                    if (code in 200..299 && !text.isNullOrBlank()) {
                        val obj = JSONObject(text)
                        val at = obj.optString("accessToken").takeIf { it.isNotBlank() }
                        val rt = obj.optString("refreshToken").takeIf { it.isNotBlank() }
                        val name = obj.optJSONObject("user")?.optString("name")?.takeIf { it.isNotBlank() }

                        if (at != null && rt != null) {
                            accessToken = at
                            refreshToken = rt
                            saveTokens(context, at, rt)

                            if (!name.isNullOrBlank()) {
                                saveUserName(context, name)
                                userNameState.value = name
                            }

                            // Google Sign-In: no guardamos password local
                            loginPrefs.edit().remove("password").apply()

                            val (remotePlayers, maybeAt) = withContext(Dispatchers.IO) {
                                fetchPlayersRemote(context, at, rt)
                            }
                            if (maybeAt != at) {
                                accessToken = maybeAt
                                saveTokens(context, maybeAt, refreshToken)
                            }
                            if (remotePlayers != null) {
                                players = remotePlayers.toMutableList()
                                savePlayers(context, players)
                            }

                            startGlobalChatListener(context)

                            withContext(Dispatchers.Main) {
                                showAuthDialog = false
                            }
                        } else {
                            setAuthError(context.getString(R.string.err_server_response))
                        }
                    } else {
                        val backendError = try {
                            if (!text.isNullOrBlank()) JSONObject(text).optString("error").takeIf { it.isNotBlank() } else null
                        } catch (_: Exception) { null }
                        setAuthError(
                            when (backendError) {
                                "google_not_configured" -> context.getString(R.string.err_google_not_configured)
                                "invalid_token" -> context.getString(R.string.err_google_signin_failed)
                                else -> context.getString(R.string.err_google_signin_failed)
                            }
                        )
                    }
                } catch (e: Exception) {
                    setAuthError(context.getString(R.string.err_connection_with_detail, e.message ?: ""))
                } finally {
                    isSubmittingGoogle = false
                }
            }
        }
        LaunchedEffect(showAuthDialog) {
            if (showAuthDialog && currentUser == null && rememberMe) {
                email = loginPrefs.getString("email", "") ?: ""
                password = loginPrefs.getString("password", "") ?: ""
            }
        }
        
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

        AlertDialog(
            onDismissRequest = { showAuthDialog = false },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = {
                Text(
                    when {
                        currentUser != null -> stringResource(R.string.logout)
                        isRegister -> stringResource(R.string.register_title)
                        else -> stringResource(R.string.login)
                    },
                    color = dialogTextColor
                )
            },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    if (currentUser != null) {
                        // Pantalla de confirmación de cierre de sesión
                        Text(stringResource(R.string.logout_confirm, currentUser ?: ""), color = dialogTextColor)
                    } else {
                        // Pantalla de inicio de sesión / registro
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it; emailError = null; loginError = null },
                            label = { Text(stringResource(R.string.email), color = dialogSecondaryTextColor) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = dialogTextColor,
                                unfocusedTextColor = dialogTextColor,
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                cursorColor = dialogTextColor,
                                focusedLabelColor = dialogSecondaryTextColor,
                                unfocusedLabelColor = dialogSecondaryTextColor
                            )
                        )
                        emailError?.let { msg ->
                            Spacer(Modifier.height(4.dp))
                            Text(text = msg, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                        }
                        if (isRegister) {
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = registerName,
                                onValueChange = { registerName = it; registerError = null },
                                label = { Text(stringResource(R.string.name), color = dialogSecondaryTextColor) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = dialogTextColor,
                                    unfocusedTextColor = dialogTextColor,
                                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    cursorColor = dialogTextColor,
                                    focusedLabelColor = dialogSecondaryTextColor,
                                    unfocusedLabelColor = dialogSecondaryTextColor
                                )
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it; passwordError = null; loginError = null },
                            label = { Text(stringResource(R.string.password_label), color = dialogSecondaryTextColor) },
                            singleLine = true,
                            visualTransformation = if (loginPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { loginPasswordVisible = !loginPasswordVisible }) {
                                    Icon(if (loginPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, contentDescription = null)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = dialogTextColor,
                                unfocusedTextColor = dialogTextColor,
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                cursorColor = dialogTextColor,
                                focusedLabelColor = dialogSecondaryTextColor,
                                unfocusedLabelColor = dialogSecondaryTextColor
                            )
                        )
                        passwordError?.let { msg ->
                            Spacer(Modifier.height(4.dp))
                            Text(text = msg, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                        }
                        if (isRegister) {
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = registerConfirm,
                                onValueChange = { registerConfirm = it; registerError = null },
                                label = { Text(stringResource(R.string.confirm_password), color = dialogSecondaryTextColor) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = dialogTextColor,
                                    unfocusedTextColor = dialogTextColor,
                                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    cursorColor = dialogTextColor,
                                    focusedLabelColor = dialogSecondaryTextColor,
                                    unfocusedLabelColor = dialogSecondaryTextColor
                                )
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { checked ->
                                    rememberMe = checked
                                    val e = loginPrefs.edit()
                                    e.putBoolean("remember", checked)
                                    if (!checked) {
                                        e.remove("email"); e.remove("password")
                                    }
                                    e.apply()
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.remember_data), color = dialogTextColor)
                        }

                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(
                            enabled = !isSubmittingGoogle && !isSubmittingLogin && !isSubmittingRegister,
                            onClick = {
                                loginError = null
                                registerError = null
                                val client = googleSignInClient
                                if (client == null) {
                                    setAuthError(context.getString(R.string.err_google_signin_failed))
                                } else if (googleClientId.isBlank() || googleClientId == "YOUR_GOOGLE_OAUTH_SERVER_CLIENT_ID") {
                                    setAuthError(context.getString(R.string.err_google_client_id_missing))
                                } else {
                                    googleLauncher.launch(client.signInIntent)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            baseColor = MaterialTheme.colorScheme.surfaceVariant,
                            colors = ButtonDefaults.buttonColors(contentColor = dialogTextColor)
                        ) {
                            Text(stringResource(R.string.continue_with_google))
                        }
                        
                        loginError?.let { error ->
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (canResendVerification && isValidEmail(email)) {
                                Spacer(Modifier.height(8.dp))
                                TextButton(onClick = {
                                    scope.launch {
                                        try {
                                            postJsonWithRetry("/auth/send-verification", JSONObject().put("email", email.trim().lowercase()))
                                            resendDone = true
                                        } catch (_: Exception) { /* ignore */ }
                                    }
                                }) { Text(stringResource(R.string.resend_verification)) }
                            }
                            if (resendDone) {
                                Spacer(Modifier.height(4.dp))
                                Text(stringResource(R.string.verification_email_resent), style = MaterialTheme.typography.bodySmall, color = dialogSecondaryTextColor)
                            }
                        }
                        if (isRegister) {
                            registerError?.let { msg ->
                                Spacer(Modifier.height(8.dp))
                                Text(text = msg, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Spacer(Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = {
                                isRegister = !isRegister
                                // limpiar errores al cambiar
                                emailError = null; passwordError = null; loginError = null; registerError = null
                            }) {
                                Text(if (isRegister) stringResource(R.string.have_account_login) else stringResource(R.string.create_account), color = dialogTextColor)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                if (accessToken != null) {
                    Button(
                        onClick = {
                            // logout
                            saveTokens(context, null, null)
                            saveUserName(context, null)
                            userNameState.value = null
                            accessToken = null
                            refreshToken = null
                            // Limpiar completamente jugadores y equipos locales al salir de la sesión
                            players = mutableListOf()
                            selected = emptySet()
                            teamA = emptyList()
                            teamB = emptyList()
                            savePlayers(context, players)
                            stopGlobalChatListener()
                            showAuthDialog = false
                        },
                        baseColor = LogoutRed,
                        border = BorderStroke(2.dp, dialogTextColor),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text(stringResource(R.string.logout)) }
                } else {
                    if (isRegister) {
                        Button(
                            enabled = email.isNotBlank() && isValidEmail(email) && password.length >= 6 && registerConfirm == password && registerName.isNotBlank() && !isSubmittingRegister,
                            onClick = {
                                registerError = null
                                if (!isValidEmail(email)) { registerError = context.getString(R.string.err_email_invalid); return@Button }
                                if (password.length < 6) { registerError = context.getString(R.string.err_password_min, 6); return@Button }
                                if (password != registerConfirm) { registerError = context.getString(R.string.err_passwords_mismatch); return@Button }
                                isSubmittingRegister = true
                                scope.launch {
                                    try {
                                        val body = JSONObject().apply {
                                            put("email", email.trim().lowercase())
                                            put("password", password)
                                            put("name", registerName.trim())
                                        }
                                        val (code, _) = postJsonWithRetry("/auth/register", body)
                                        if (code in 200..299) {
                                            loginError = null
                                            canResendVerification = true
                                            resendDone = false
                                            // Mostrar indicación
                                            registerError = null
                                        } else {
                                            registerError = when (code) {
                                                409 -> context.getString(R.string.err_email_taken)
                                                400 -> context.getString(R.string.err_invalid_data)
                                                in 500..599 -> context.getString(R.string.err_server_try_again)
                                                else -> context.getString(R.string.err_register_generic_with_detail, "")
                                            }
                                        }
                                    } catch (e: Exception) {
                                        registerError = context.getString(R.string.err_connection_with_detail, e.message ?: "")
                                    } finally {
                                        isSubmittingRegister = false
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) { Text(stringResource(R.string.register_title)) }
                    } else {
                        Button(
                            enabled = email.isNotBlank() && password.isNotBlank() && !isSubmittingLogin,
                            onClick = {
                                emailError = if (email.isBlank()) context.getString(R.string.err_email_or_name_required) else null
                                passwordError = if (password.isBlank()) context.getString(R.string.err_password_required) else null
                                if (emailError != null || passwordError != null) return@Button
                                
                                isSubmittingLogin = true
                                scope.launch {
                                    try {
                                        val body = JSONObject().apply {
                                            put("email", email.trim().lowercase())
                                            put("password", password)
                                        }
                                        
                                        val (code, text) = postJsonWithRetry("/auth/login", body)
                                        
                                        if (code in 200..299 && !text.isNullOrBlank()) {
                                            val obj = JSONObject(text)
                                            val at = obj.optString("accessToken").takeIf { it.isNotBlank() }
                                            val rt = obj.optString("refreshToken").takeIf { it.isNotBlank() }
                                            val name = obj.optJSONObject("user")?.optString("name")?.takeIf { it.isNotBlank() }

                                            if (at != null && rt != null) {
                                                // Guardar tokens y actualizar estado
                                                accessToken = at
                                                refreshToken = rt
                                                saveTokens(context, at, rt)

                                                // Guardar nombre de usuario si está disponible
                                                if (!name.isNullOrBlank()) {
                                                    saveUserName(context, name)
                                                    userNameState.value = name
                                                }

                                                // Guardar o limpiar credenciales según preferencia
                                                if (rememberMe) {
                                                    loginPrefs.edit()
                                                        .putString("email", email.trim().lowercase())
                                                        .putString("password", password)
                                                        .apply()
                                                } else {
                                                    loginPrefs.edit()
                                                        .remove("email")
                                                        .remove("password")
                                                        .apply()
                                                }

                                                // Sincronizar jugadores desde backend inmediatamente después de login
                                                val (remotePlayers, maybeAt) = withContext(Dispatchers.IO) {
                                                    fetchPlayersRemote(context, at, rt)
                                                }
                                                if (maybeAt != at) {
                                                    accessToken = maybeAt
                                                    saveTokens(context, maybeAt, refreshToken)
                                                }
                                                if (remotePlayers != null) {
                                                    players = remotePlayers.toMutableList()
                                                    savePlayers(context, players)
                                                }

                                                // Iniciar listener global de notificaciones de chat
                                                startGlobalChatListener(context)

                                                withContext(Dispatchers.Main) {
                                                    showAuthDialog = false
                                                }
                                            } else {
                                                loginError = context.getString(R.string.err_server_response)
                                            }
                                        } else {
                                            canResendVerification = (code == 403)

                                            // Intentar leer un mensaje detallado desde la respuesta del backend
                                            val serverMessage = try {
                                                if (!text.isNullOrBlank()) {
                                                    JSONObject(text).optString("message").takeIf { it.isNotBlank() }
                                                } else null
                                            } catch (_: Exception) {
                                                null
                                            }

                                            loginError = when (code) {
                                                401 -> context.getString(R.string.err_invalid_credentials)
                                                400 -> context.getString(R.string.err_invalid_data)
                                                403 -> serverMessage
                                                    ?: "Tu correo aún no está verificado. Revisa tu bandeja de entrada y, si no ves el correo, revisa también la carpeta de Spam o Correos no deseados."
                                                in 500..599 -> context.getString(R.string.err_server_try_again)
                                                else -> context.getString(R.string.err_login_generic)
                                            }
                                        }
                                    } catch (e: Exception) {
                                        loginError = context.getString(R.string.err_connection_with_detail, e.message ?: "")
                                    } finally {
                                        isSubmittingLogin = false
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) { Text(stringResource(R.string.login)) }
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showAuthDialog = false }) { Text(stringResource(R.string.cancel), color = dialogTextColor) }
            }
        )
    }

    if (showRegisterDialog) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var submitError by remember { mutableStateOf<String?>(null) }
        var submitSuccess by remember { mutableStateOf(false) }
        var isSubmitting by remember { mutableStateOf(false) }
        var confirmPassword by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf<String?>(null) }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var confirmError by remember { mutableStateOf<String?>(null) }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmVisible by remember { mutableStateOf(false) }
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

        AlertDialog(
            onDismissRequest = { showRegisterDialog = false },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.register_title), color = dialogTextColor) },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { val t = it; name = t; nameError = if (t.trim().isBlank()) context.getString(R.string.err_name_required) else null },
                        label = { Text(stringResource(R.string.name), color = dialogSecondaryTextColor) },
                        singleLine = true,
                        isError = nameError != null,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = dialogTextColor,
                            unfocusedTextColor = dialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = dialogTextColor,
                            focusedLabelColor = dialogSecondaryTextColor,
                            unfocusedLabelColor = dialogSecondaryTextColor
                        )
                    )
                    nameError?.let { msg ->
                        Spacer(Modifier.height(4.dp))
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { val t = it; email = t; emailError = if (t.trim().isBlank()) context.getString(R.string.err_email_invalid) else if (!isValidEmail(t.trim())) context.getString(R.string.err_email_invalid) else null },
                        label = { Text(stringResource(R.string.email), color = dialogSecondaryTextColor) },
                        singleLine = true,
                        isError = emailError != null,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = dialogTextColor,
                            unfocusedTextColor = dialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = dialogTextColor,
                            focusedLabelColor = dialogSecondaryTextColor,
                            unfocusedLabelColor = dialogSecondaryTextColor
                        )
                    )
                    emailError?.let { msg ->
                        Spacer(Modifier.height(4.dp))
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { val t = it; password = t; passwordError = if (t.length < MIN_PASSWORD_LENGTH) context.getString(R.string.err_password_min, MIN_PASSWORD_LENGTH) else null; confirmError = if (confirmPassword != t) context.getString(R.string.err_passwords_mismatch) else null },
                        label = { Text(stringResource(R.string.password_min, MIN_PASSWORD_LENGTH), color = dialogSecondaryTextColor) },
                        singleLine = true,
                        isError = passwordError != null,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = dialogTextColor,
                            unfocusedTextColor = dialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = dialogTextColor,
                            focusedLabelColor = dialogSecondaryTextColor,
                            unfocusedLabelColor = dialogSecondaryTextColor
                        )
                    )
                    passwordError?.let { msg ->
                        Spacer(Modifier.height(4.dp))
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { val t = it; confirmPassword = t; confirmError = if (t != password) context.getString(R.string.err_passwords_mismatch) else null },
                        label = { Text(stringResource(R.string.confirm_password), color = dialogSecondaryTextColor) },
                        singleLine = true,
                        isError = confirmError != null,
                        visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmVisible = !confirmVisible }) {
                                Icon(if (confirmVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = dialogTextColor,
                            unfocusedTextColor = dialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = dialogTextColor,
                            focusedLabelColor = dialogSecondaryTextColor,
                            unfocusedLabelColor = dialogSecondaryTextColor
                        )
                    )
                    confirmError?.let { msg ->
                        Spacer(Modifier.height(4.dp))
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(Modifier.height(8.dp))
                    val strength = passwordStrength(password)
                    LinearProgressIndicator(progress = { strength / 4f }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = when (strength) {
                            0,1 -> stringResource(R.string.password_strength_weak)
                            2 -> stringResource(R.string.password_strength_medium)
                            3 -> stringResource(R.string.password_strength_good)
                            else -> stringResource(R.string.password_strength_strong)
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(8.dp))
                    if (isSubmitting) {
                        Text(stringResource(R.string.registering), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    submitError?.let { msg ->
                        Spacer(Modifier.height(4.dp))
                        Text(msg, color = MaterialTheme.colorScheme.error)
                    }
                    if (submitSuccess) {
                        Spacer(Modifier.height(4.dp))
                        Text(stringResource(R.string.registration_done), color = MaterialTheme.colorScheme.primary)
                    }
                }
            },
            confirmButton = {
                val canSubmit = name.trim().isNotEmpty() && isValidEmail(email.trim()) && password.length >= MIN_PASSWORD_LENGTH && confirmPassword == password
                Button(enabled = canSubmit && !isSubmitting, onClick = {
                    nameError = if (name.trim().isBlank()) context.getString(R.string.err_name_required) else null
                    emailError = if (!isValidEmail(email.trim())) context.getString(R.string.err_email_invalid) else null
                    passwordError = if (password.length < MIN_PASSWORD_LENGTH) context.getString(R.string.err_password_min, MIN_PASSWORD_LENGTH) else null
                    confirmError = if (confirmPassword != password) context.getString(R.string.err_passwords_mismatch) else null
                    if (nameError != null || emailError != null || passwordError != null || confirmError != null) return@Button
                    scope.launch {
                        isSubmitting = true
                        submitError = null
                        val body = JSONObject()
                            .put("name", name.trim())
                            .put("email", email.trim())
                            .put("password", password)
                        val (code, _) = postJsonWithRetry("/auth/register", body)
                        if (code in 200..299) {
                            // Registro aceptado. Se envió (o intentó enviar) verificación por correo.
                            submitSuccess = true
                        } else {
                            submitError = when (code) {
                                409 -> context.getString(R.string.err_email_taken)
                                400 -> context.getString(R.string.err_invalid_data)
                                in 500..599 -> context.getString(R.string.err_server_try_again)
                                else -> context.getString(R.string.err_register_generic_with_detail, "")
                            }
                        }
                        isSubmitting = false
                    }
                }) { Text(if (isSubmitting) stringResource(R.string.registering) else stringResource(R.string.register_title)) }
            },
            dismissButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { showRegisterDialog = false }) { Text(stringResource(R.string.cancel), color = dialogTextColor) }
                    TextButton(onClick = { showRegisterDialog = false; showAuthDialog = true }) { Text(stringResource(R.string.have_account_login), color = dialogTextColor) }
                }
            }
        )
    }

}

@Composable
fun PlayerRow(
    player: Player, 
    checked: Boolean, 
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (player.isGoalkeeper) {
                    Icon(Icons.Filled.BackHand, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(6.dp))
                } else {
                    Icon(painterResource(id = R.drawable.ic_tshirt), contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.width(6.dp))
                }
                Text(player.name, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            }
        }
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun PitchView(
    teamA: List<Player>,
    teamB: List<Player>,
    sport: String,
    teamAColor: Color = Color(0xFFFFEB3B),
    teamBColor: Color = Color(0xFF03A9F4),
    teamANationKey: String? = null,
    teamBNationKey: String? = null,
    modifier: Modifier = Modifier
) {
    if (teamA.isEmpty() && teamB.isEmpty()) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.6f)
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
        ) {
            val fieldColor = Color(0xFF43A047)
            val lineColor = Color.White
            val strokeWidth = size.minDimension * 0.01f

            val isTennis = sport.equals("Tenis", ignoreCase = true)
            val isPadel = sport.equals("Pádel", ignoreCase = true) || sport.equals("Padel", ignoreCase = true)
            val isVolleyball = sport.equals("Voleybol", ignoreCase = true) || sport.equals("Vóleibol", ignoreCase = true)
            val isBabyFootball = sport.equals("Baby Fútbol", ignoreCase = true) || sport.equals("Baby Futbol", ignoreCase = true) || sport.equals("Baby futbol", ignoreCase = true) || sport.equals("Baby fútbol", ignoreCase = true)
            val isRugby = sport.equals("Rugby", ignoreCase = true)
            // Colores específicos de Pádel según el SVG
            val padelCourtColor = Color(0xFF2EA4DA)   // Zona azul
            val padelWallColor = Color(0xFF082A40)    // Borde azul oscuro de la zona
            val volleyCourtColor = Color(0xFFFF9800)

            // Fondo general
            drawRect(color = fieldColor, size = size)

            // Borde exterior
            drawRect(
                color = lineColor,
                style = Stroke(width = strokeWidth),
                size = Size(width = size.width, height = size.height)
            )

            val boxWidth = size.width * 0.6f
            val boxHeight = size.height * 0.18f
            val boxLeft = (size.width - boxWidth) / 2f

            if (isTennis) {
                // Cancha de tenis basada en el nuevo SVG (800x1400), escalada al tamaño del Canvas
                val scaleX = size.width / 36f
                val scaleY = size.height / 78f

                fun sx(x: Float) = x * scaleX
                fun sy(y: Float) = y * scaleY

                // Fondo verde específico de tenis (#66CC66)
                drawRect(
                    color = Color(0xFF2F9E3A),
                    size = size
                )

                val baseStroke = strokeWidth
                val mainStroke = baseStroke * 0.75f   // líneas internas ~6px relativo
                val outerStroke = baseStroke          // borde exterior ~8px relativo
                val netStroke = mainStroke * 1.2f

                // Líneas verticales (x=120,400,680)
                drawLine(
                    lineColor,
                    Offset(sx(4.5f), sy(0f)),
                    Offset(sx(4.5f), sy(78f)),
                    mainStroke
                )
                drawLine(
                    lineColor,
                    Offset(sx(18f), sy(18f)),
                    Offset(sx(18f), sy(60f)),
                    mainStroke
                )
                drawLine(
                    lineColor,
                    Offset(sx(31.5f), sy(0f)),
                    Offset(sx(31.5f), sy(78f)),
                    mainStroke
                )

                // Líneas horizontales (y=356,696,1037)
                drawLine(
                    lineColor,
                    Offset(sx(4.5f), sy(18f)),
                    Offset(sx(31.5f), sy(18f)),
                    mainStroke
                )
                drawLine(
                    lineColor,
                    Offset(sx(0f), sy(39f)),
                    Offset(sx(36f), sy(39f)),
                    netStroke
                )
                drawLine(
                    lineColor,
                    Offset(sx(4.5f), sy(60f)),
                    Offset(sx(31.5f), sy(60f)),
                    mainStroke
                )
                // Baselines
                drawLine(
                    lineColor,
                    Offset(sx(0f), sy(0f)),
                    Offset(sx(36f), sy(0f)),
                    mainStroke
                )
                drawLine(
                    lineColor,
                    Offset(sx(0f), sy(78f)),
                    Offset(sx(36f), sy(78f)),
                    mainStroke
                )

                // Borde exterior (marco)
                drawRect(
                    color = lineColor,
                    style = Stroke(width = outerStroke),
                    topLeft = Offset(sx(0.075f), sy(0.075f)),
                    size = Size(sx(35.85f), sy(77.85f))
                )

                // Ticks centrales en extremos
                drawLine(
                    color = lineColor,
                    start = Offset(sx(18f), sy(-0.8f)),
                    end = Offset(sx(18f), sy(0.8f)),
                    strokeWidth = mainStroke * 0.8f
                )
                drawLine(
                    color = lineColor,
                    start = Offset(sx(18f), sy(77.2f)),
                    end = Offset(sx(18f), sy(78.8f)),
                    strokeWidth = mainStroke * 0.8f
                )
                // Postes de red (opcionales)
                drawRect(
                    color = lineColor,
                    topLeft = Offset(sx(0.075f), sy(38.6f)),
                    size = Size(sx(0.05f), sy(0.8f))
                )
                drawRect(
                    color = lineColor,
                    topLeft = Offset(sx(35.875f), sy(38.6f)),
                    size = Size(sx(0.05f), sy(0.8f))
                )
            } else if (isPadel) {
                // Cancha de pádel basada en el nuevo SVG (800x1400), escalada al tamaño actual del Canvas
                val scaleX = size.width / 800f
                val scaleY = size.height / 1400f

                fun sx(x: Float) = x * scaleX
                fun sy(y: Float) = y * scaleY

                // Refuerzos de grosor: central más gruesa, resto más delgadas
                val thickStroke = strokeWidth * 1.5f  // línea central ~12px
                val thinStroke = strokeWidth * 0.5f   // líneas laterales/vertical ~4px

                // Zona azul (cancha)
                drawRect(
                    color = padelCourtColor,
                    topLeft = Offset(sx(80f), sy(40f)),
                    size = Size(sx(640f), sy(1320f))
                )

                // Borde de la zona azul (marco)
                drawRect(
                    color = padelWallColor,
                    style = Stroke(width = strokeWidth * 1.5f),
                    topLeft = Offset(sx(80f), sy(40f)),
                    size = Size(sx(640f), sy(1320f))
                )

                // LÍNEA CENTRAL GRUESA
                drawLine(
                    lineColor,
                    Offset(sx(80f), sy(700f)),
                    Offset(sx(720f), sy(700f)),
                    thickStroke
                )

                // LÍNEAS DELGADAS LATERALES SUPERIOR / INFERIOR
                drawLine(
                    lineColor,
                    Offset(sx(80f), sy(360f)),
                    Offset(sx(720f), sy(360f)),
                    thinStroke
                )
                drawLine(
                    lineColor,
                    Offset(sx(80f), sy(1040f)),
                    Offset(sx(720f), sy(1040f)),
                    thinStroke
                )

                // LÍNEA VERTICAL DELGADA (solo entre las horizontales delgadas)
                drawLine(
                    lineColor,
                    Offset(sx(400f), sy(360f)),
                    Offset(sx(400f), sy(1040f)),
                    thinStroke
                )

                // Marcos exteriores azul oscuro arriba/abajo (#073150)
                val barColor = Color(0xFF073150)
                drawRect(
                    color = barColor,
                    topLeft = Offset(sx(70f), sy(30f)),
                    size = Size(sx(660f), sy(20f))
                )
                drawRect(
                    color = barColor,
                    topLeft = Offset(sx(70f), sy(1350f)),
                    size = Size(sx(660f), sy(20f))
                )
            } else if (isVolleyball) {
                // Escala basada en el SVG (viewBox 9x18)
                val scaleX = size.width / 9f
                val scaleY = size.height / 18f
                val unit = minOf(scaleX, scaleY)

                fun sx(x: Float) = x * scaleX
                fun sy(y: Float) = y * scaleY

                // Fondo verde exterior (#168548)
                drawRect(
                    color = Color(0xFF168548),
                    size = size
                )

                // Área de juego naranja con borde blanco (stroke 0.1 unidades)
                val innerTopLeft = Offset(sx(0.25f), sy(0.25f))
                val innerSize = Size(sx(8.5f), sy(17.5f))
                drawRect(
                    color = Color(0xFFF49A40),
                    topLeft = innerTopLeft,
                    size = innerSize
                )
                drawRect(
                    color = lineColor,
                    style = Stroke(width = unit * 0.1f),
                    topLeft = innerTopLeft,
                    size = innerSize
                )

                // Línea central y líneas de ataque
                val xStart = sx(0.25f)
                val xEnd = sx(8.75f)
                val lineW = unit * 0.1f
                drawLine(color = lineColor, start = Offset(xStart, sy(9f)), end = Offset(xEnd, sy(9f)), strokeWidth = lineW)
                drawLine(color = lineColor, start = Offset(xStart, sy(6f)), end = Offset(xEnd, sy(6f)), strokeWidth = lineW)
                drawLine(color = lineColor, start = Offset(xStart, sy(12f)), end = Offset(xEnd, sy(12f)), strokeWidth = lineW)

                // Marcas de zona de saque (rectángulos blancos)
                val tickW = sx(0.1f) - sx(0f)
                val tickH = sy(0.4f) - sy(0f)
                // Izquierda
                drawRect(color = lineColor, topLeft = Offset(sx(0.15f), sy(1f)), size = Size(tickW, tickH))
                drawRect(color = lineColor, topLeft = Offset(sx(0.15f), sy(17f)), size = Size(tickW, tickH))
                // Derecha
                drawRect(color = lineColor, topLeft = Offset(sx(8.75f), sy(1f)), size = Size(tickW, tickH))
                drawRect(color = lineColor, topLeft = Offset(sx(8.75f), sy(17f)), size = Size(tickW, tickH))

                // Líneas punteadas externas (lado izquierdo)
                val dotW = unit * 0.07f
                drawLine(color = lineColor, start = Offset(sx(0f), sy(4.5f)), end = Offset(sx(0.2f), sy(4.5f)), strokeWidth = dotW)
                drawLine(color = lineColor, start = Offset(sx(0f), sy(5f)), end = Offset(sx(0.2f), sy(5f)), strokeWidth = dotW)
                drawLine(color = lineColor, start = Offset(sx(0f), sy(5.5f)), end = Offset(sx(0.2f), sy(5.5f)), strokeWidth = dotW)
                drawLine(color = lineColor, start = Offset(sx(0f), sy(13.5f)), end = Offset(sx(0.2f), sy(13.5f)), strokeWidth = dotW)
                drawLine(color = lineColor, start = Offset(sx(0f), sy(14f)), end = Offset(sx(0.2f), sy(14f)), strokeWidth = dotW)
                drawLine(color = lineColor, start = Offset(sx(0f), sy(14.5f)), end = Offset(sx(0.2f), sy(14.5f)), strokeWidth = dotW)
            } else if (isRugby) {
                // Cancha de rugby: réplica visual en Compose para mantener coherencia con bitmap compartido.
                val scaleX = size.width / 70f
                val scaleY = size.height / 120f

                fun sx(x: Float) = x * scaleX
                fun sy(y: Float) = y * scaleY

                drawRect(
                    color = Color(0xFF39A94B),
                    size = size
                )

                val rugbyStroke = strokeWidth * 0.95f
                val rugbyDash = size.minDimension * 0.035f

                val left = 8f
                val right = 62f
                val top = 5f
                val bottom = 115f
                val goalTopY = 13f
                val goalBottomY = 107f
                val halfY = 60f
                val line22TopY = 33.68f
                val line22BottomY = 86.32f
                val line10TopY = 50.6f
                val line10BottomY = 69.4f
                val line5LeftX = 11.86f
                val line15LeftX = 19.57f
                val line15RightX = 50.43f
                val line5RightX = 58.14f

                // Marco e in-goals
                drawRect(
                    color = lineColor,
                    style = Stroke(width = rugbyStroke),
                    topLeft = Offset(sx(left), sy(top)),
                    size = Size(sx(right - left), sy(bottom - top))
                )
                drawLine(lineColor, Offset(sx(left), sy(goalTopY)), Offset(sx(right), sy(goalTopY)), rugbyStroke)
                drawLine(lineColor, Offset(sx(left), sy(goalBottomY)), Offset(sx(right), sy(goalBottomY)), rugbyStroke)

                // Mitad y 22m
                drawLine(lineColor, Offset(sx(left), sy(halfY)), Offset(sx(right), sy(halfY)), rugbyStroke)
                drawLine(lineColor, Offset(sx(left), sy(line22TopY)), Offset(sx(right), sy(line22TopY)), rugbyStroke)
                drawLine(lineColor, Offset(sx(left), sy(line22BottomY)), Offset(sx(right), sy(line22BottomY)), rugbyStroke)

                // 10m segmentadas
                val dashed = PathEffect.dashPathEffect(floatArrayOf(rugbyDash, rugbyDash * 0.75f), 0f)
                drawLine(
                    color = lineColor,
                    start = Offset(sx(left), sy(line10TopY)),
                    end = Offset(sx(right), sy(line10TopY)),
                    strokeWidth = rugbyStroke,
                    pathEffect = dashed
                )
                drawLine(
                    color = lineColor,
                    start = Offset(sx(left), sy(line10BottomY)),
                    end = Offset(sx(right), sy(line10BottomY)),
                    strokeWidth = rugbyStroke,
                    pathEffect = dashed
                )

                // 5m y 15m desde touch (segmentadas)
                drawLine(lineColor, Offset(sx(line5LeftX), sy(goalTopY)), Offset(sx(line5LeftX), sy(goalBottomY)), rugbyStroke, pathEffect = dashed)
                drawLine(lineColor, Offset(sx(line15LeftX), sy(goalTopY)), Offset(sx(line15LeftX), sy(goalBottomY)), rugbyStroke, pathEffect = dashed)
                drawLine(lineColor, Offset(sx(line15RightX), sy(goalTopY)), Offset(sx(line15RightX), sy(goalBottomY)), rugbyStroke, pathEffect = dashed)
                drawLine(lineColor, Offset(sx(line5RightX), sy(goalTopY)), Offset(sx(line5RightX), sy(goalBottomY)), rugbyStroke, pathEffect = dashed)

                // Marca central corta
                drawLine(
                    color = lineColor,
                    start = Offset(sx(35f), sy(58.8f)),
                    end = Offset(sx(35f), sy(61.2f)),
                    strokeWidth = rugbyStroke
                )

                // Porterías en H
                val postGap = 3.8f
                val topPostTop = 6.2f
                val topPostBottom = 11.2f
                val topCrossbarY = 9.2f
                val bottomPostTop = 108.8f
                val bottomPostBottom = 113.8f
                val bottomCrossbarY = 110.8f

                drawLine(lineColor, Offset(sx(35f - postGap / 2f), sy(topPostTop)), Offset(sx(35f - postGap / 2f), sy(topPostBottom)), rugbyStroke)
                drawLine(lineColor, Offset(sx(35f + postGap / 2f), sy(topPostTop)), Offset(sx(35f + postGap / 2f), sy(topPostBottom)), rugbyStroke)
                drawLine(lineColor, Offset(sx(35f - postGap / 2f), sy(topCrossbarY)), Offset(sx(35f + postGap / 2f), sy(topCrossbarY)), rugbyStroke)

                drawLine(lineColor, Offset(sx(35f - postGap / 2f), sy(bottomPostTop)), Offset(sx(35f - postGap / 2f), sy(bottomPostBottom)), rugbyStroke)
                drawLine(lineColor, Offset(sx(35f + postGap / 2f), sy(bottomPostTop)), Offset(sx(35f + postGap / 2f), sy(bottomPostBottom)), rugbyStroke)
                drawLine(lineColor, Offset(sx(35f - postGap / 2f), sy(bottomCrossbarY)), Offset(sx(35f + postGap / 2f), sy(bottomCrossbarY)), rugbyStroke)
            } else if (isBabyFootball) {
                // Baby fútbol: SVG (viewBox 20x40)
                val scaleX = size.width / 20f
                val scaleY = size.height / 40f
                val unit = minOf(scaleX, scaleY)

                fun sx(x: Float) = x * scaleX
                fun sy(y: Float) = y * scaleY

                // Fondo
                drawRect(
                    color = Color(0xFF279A49),
                    size = size
                )

                val lineW = unit * 0.12f
                val goalW = unit * 0.14f

                // Marco interior
                drawRect(
                    color = lineColor,
                    style = Stroke(width = lineW),
                    topLeft = Offset(sx(0.2f), sy(0.2f)),
                    size = Size(sx(19.6f), sy(39.6f))
                )

                // Línea central
                drawLine(
                    color = lineColor,
                    start = Offset(sx(0.2f), sy(20f)),
                    end = Offset(sx(19.8f), sy(20f)),
                    strokeWidth = lineW
                )

                // Círculo central
                drawCircle(
                    color = lineColor,
                    radius = 3f * unit,
                    center = Offset(sx(10f), sy(20f)),
                    style = Stroke(width = lineW)
                )

                // Líneas rectas reemplazando arcos
                drawLine(
                    color = lineColor,
                    start = Offset(sx(0.2f), sy(6.2f)),
                    end = Offset(sx(19.8f), sy(6.2f)),
                    strokeWidth = lineW
                )
                drawLine(
                    color = lineColor,
                    start = Offset(sx(0.2f), sy(33.8f)),
                    end = Offset(sx(19.8f), sy(33.8f)),
                    strokeWidth = lineW
                )

                // Porterías
                drawRect(
                    color = lineColor,
                    style = Stroke(width = goalW),
                    topLeft = Offset(sx(7.5f), sy(0.2f)),
                    size = Size(sx(5f), sy(1f))
                )
                drawRect(
                    color = lineColor,
                    style = Stroke(width = goalW),
                    topLeft = Offset(sx(7.5f), sy(38.8f)),
                    size = Size(sx(5f), sy(1f))
                )

                // Puntos de penal
                drawCircle(color = lineColor, radius = unit * 0.08f, center = Offset(sx(10f), sy(6.2f)))
                drawCircle(color = lineColor, radius = unit * 0.08f, center = Offset(sx(10f), sy(33.8f)))
            } else {
                // Fútbol Baby: 800x1400 base
                val scaleX = size.width / 800f
                val scaleY = size.height / 1400f
                val circleScale = minOf(scaleX, scaleY)

                fun sx(x: Float) = x * scaleX
                fun sy(y: Float) = y * scaleY

                //-----------------------------------------------------
                // BORDE EXTERIOR
                //-----------------------------------------------------
                drawRoundRect(
                    color = lineColor,
                    topLeft = Offset(sx(20f), sy(20f)),
                    size = Size(sx(760f), sy(1360f)),
                    cornerRadius = CornerRadius(6f * circleScale, 6f * circleScale),
                    style = Stroke(width = strokeWidth)
                )

                //-----------------------------------------------------
                // MEDIO CAMPO
                //-----------------------------------------------------
                drawLine(
                    color = lineColor,
                    start = Offset(sx(20f), sy(700f)),
                    end = Offset(sx(780f), sy(700f)),
                    strokeWidth = strokeWidth
                )

                drawCircle(
                    color = lineColor,
                    radius = 90f * circleScale,
                    center = Offset(sx(400f), sy(700f)),
                    style = Stroke(width = strokeWidth)
                )

                drawCircle(
                    color = lineColor,
                    radius = 6f * circleScale,
                    center = Offset(sx(400f), sy(700f))
                )


                //=====================================================
                // ÁREA PENAL SUPERIOR – AGRANDADA PROPORCIONALMENTE
                //=====================================================

                // Área grande (más ancha y profunda)
                drawRect(
                    color = lineColor,
                    topLeft = Offset(sx(250f), sy(20f)),          // antes 320   → ahora más ancha
                    size = Size(sx(300f), sy(180f)),              // antes 160x140 → ahora más grande
                    style = Stroke(width = strokeWidth)
                )

                // Área chica (más grande)
                drawRect(
                    color = lineColor,
                    topLeft = Offset(sx(310f), sy(20f)),
                    size = Size(sx(180f), sy(90f)),               // antes 90x70 → aumentado
                    style = Stroke(width = strokeWidth)
                )

                val arcPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    style = android.graphics.Paint.Style.STROKE
                    this.strokeWidth = strokeWidth
                    isAntiAlias = true
                }
                val rTop = 75f
                val topAreaBottomY = 20f + 180f
                val topCenterY = topAreaBottomY
                val topArcRect = android.graphics.RectF(
                    sx(400f - rTop), sy(topCenterY - rTop),
                    sx(400f + rTop), sy(topCenterY + rTop)
                )
                val nativeCanvas = drawContext.canvas.nativeCanvas
                nativeCanvas.save()
                val topClipY = sy(topAreaBottomY)
                nativeCanvas.clipRect(
                    0f,
                    topClipY,
                    size.width,
                    size.height
                )
                nativeCanvas.drawArc(topArcRect, 0f, 180f, false, arcPaint)
                nativeCanvas.restore()

                // Punto penal superior (ajustado)
                drawCircle(
                    color = lineColor,
                    radius = 5f * circleScale,
                    center = Offset(sx(400f), sy(170f))
                )


                //=====================================================
                // ÁREA PENAL INFERIOR – MISMAS PROPORCIONES
                //=====================================================

                // Área grande
                drawRect(
                    color = lineColor,
                    topLeft = Offset(sx(250f), sy(1200f)),
                    size = Size(sx(300f), sy(180f)),
                    style = Stroke(width = strokeWidth)
                )

                // Área chica
                drawRect(
                    color = lineColor,
                    topLeft = Offset(sx(310f), sy(1270f)),
                    size = Size(sx(180f), sy(90f)),
                    style = Stroke(width = strokeWidth)
                )

                val rBottom = 75f
                val bottomAreaTopY = 1200f
                val bottomCenterY = bottomAreaTopY
                val bottomArcRect = android.graphics.RectF(
                    sx(400f - rBottom), sy(bottomCenterY - rBottom),
                    sx(400f + rBottom), sy(bottomCenterY + rBottom)
                )
                nativeCanvas.save()
                val bottomClipY = sy(bottomAreaTopY)
                nativeCanvas.clipRect(
                    0f,
                    0f,
                    size.width,
                    bottomClipY
                )
                nativeCanvas.drawArc(bottomArcRect, 180f, 180f, false, arcPaint)
                nativeCanvas.restore()

                // Punto penal inferior
                drawCircle(
                    color = lineColor,
                    radius = 5f * circleScale,
                    center = Offset(sx(400f), sy(1230f))
                )
            }

            val radiusPlayer = size.minDimension * 0.018f
            val canvas = drawContext.canvas.nativeCanvas
            val textPaint = Paint().apply {
                color = AndroidColor.WHITE
                textAlign = Paint.Align.CENTER
                textSize = size.minDimension * 0.035f
                isAntiAlias = true
            }

            val gkA = teamA.firstOrNull { it.isGoalkeeper }
            val gkB = teamB.firstOrNull { it.isGoalkeeper }
            val fieldA = if (gkA != null) teamA.filterNot { it.isGoalkeeper } else teamA
            val fieldB = if (gkB != null) teamB.filterNot { it.isGoalkeeper } else teamB

            fun drawJersey(
                center: Offset,
                baseColor: Color,
                nationKey: String? = null,
                isGoalkeeper: Boolean = false
            ) {
                // Tamaño de la camiseta en la cancha (3x respecto al valor original)
                val iconSize = radiusPlayer * 6.6f
                val half = iconSize / 2f

                if (!nationKey.isNullOrBlank()) {
                    drawNationKitShirt(
                        canvas = drawContext.canvas.nativeCanvas,
                        left = center.x - half,
                        top = center.y - half,
                        size = iconSize,
                        nationKey = nationKey
                    )
                    return
                }
                drawSolidKitShirt(
                    canvas = drawContext.canvas.nativeCanvas,
                    left = center.x - half,
                    top = center.y - half,
                    size = iconSize,
                    baseColor = baseColor.toArgb()
                )
            }

            fun positionsHalf(teamSize: Int, hasGoalkeeper: Boolean, isTop: Boolean): List<Offset> {
                if (teamSize <= 0) return emptyList()

                // Caso especial: equipos de 5 (1 arquero + 4 de campo)
                // Colocamos 2 jugadores en línea defensiva y 2 en línea ofensiva.
                if (hasGoalkeeper && teamSize == 4) {
                    val halfHeight = size.height / 2f
                    val innerBand = halfHeight - boxHeight
                    val areaLine = if (isTop) boxHeight else size.height - boxHeight
                    val sign = if (isTop) 1f else -1f

                    val backY = areaLine + sign * innerBand * 0.15f
                    val frontY = areaLine + sign * innerBand * 0.6f

                    val xLeft = size.width * 0.33f
                    val xRight = size.width * 0.67f

                    return listOf(
                        Offset(xLeft, backY),
                        Offset(xRight, backY),
                        Offset(xLeft, frontY),
                        Offset(xRight, frontY)
                    )
                }

                // Caso especial: 5 jugadores de campo sin arquero -> 3 atrás y 2 adelante
                if (!hasGoalkeeper && teamSize == 5) {
                    val halfHeight = size.height / 2f
                    val innerBand = halfHeight - boxHeight
                    val areaLine = if (isTop) boxHeight else size.height - boxHeight
                    val sign = if (isTop) 1f else -1f

                    val backY = areaLine + sign * innerBand * 0.18f
                    val frontY = areaLine + sign * innerBand * 0.65f

                    val xBack1 = size.width * 0.25f
                    val xBack2 = size.width * 0.50f
                    val xBack3 = size.width * 0.75f
                    val xFront1 = size.width * 0.33f
                    val xFront2 = size.width * 0.67f

                    return listOf(
                        Offset(xBack1, backY),
                        Offset(xBack2, backY),
                        Offset(xBack3, backY),
                        Offset(xFront1, frontY),
                        Offset(xFront2, frontY)
                    )
                }

                // Distribución genérica para otros tamaños: rejilla simple por mitad
                // Siempre entre la línea del área y el medio campo, simétrico para ambos equipos.
                val cols = minOf(4, maxOf(1, teamSize))
                val rows = ((teamSize + cols - 1) / cols)
                val halfHeight = size.height / 2f
                val innerBand = halfHeight - boxHeight
                val areaLine = if (isTop) boxHeight else size.height - boxHeight
                val sign = if (isTop) 1f else -1f
                val cellW = size.width / (cols + 1)
                val cellH = innerBand / (rows + 1)

                val result = mutableListOf<Offset>()
                var idx = 0
                for (r in 0 until rows) {
                    for (c in 0 until cols) {
                        if (idx >= teamSize) break
                        val x = cellW * (c + 1)
                        val y = areaLine + sign * cellH * (r + 1)
                        result.add(Offset(x, y))
                        idx++
                    }
                }
                return result
            }

            val posA = positionsHalf(fieldA.size, hasGoalkeeper = gkA != null, isTop = true)
            val posB = positionsHalf(fieldB.size, hasGoalkeeper = gkB != null, isTop = false)

            fieldA.zip(posA).forEach { (player, pos) ->
                drawJersey(pos, teamAColor, nationKey = teamANationKey, isGoalkeeper = false)
                canvas.drawText(
                    player.name,
                    pos.x,
                    pos.y + radiusPlayer * 3.8f,
                    textPaint
                )
            }

            fieldB.zip(posB).forEach { (player, pos) ->
                drawJersey(pos, teamBColor, nationKey = teamBNationKey, isGoalkeeper = false)
                canvas.drawText(
                    player.name,
                    pos.x,
                    pos.y + radiusPlayer * 3.8f,
                    textPaint
                )
            }

            // Arqueros cerca de cada arco
            gkA?.let {
                val x = size.width / 2f
                val y = boxHeight * 0.5f
                drawJersey(Offset(x, y), teamAColor, nationKey = teamANationKey, isGoalkeeper = true)
                canvas.drawText(it.name, x, y + radiusPlayer * 3.8f, textPaint)
            }
            gkB?.let {
                val x = size.width / 2f
                val y = size.height - boxHeight * 0.5f
                drawJersey(Offset(x, y), teamBColor, nationKey = teamBNationKey, isGoalkeeper = true)
                canvas.drawText(it.name, x, y + radiusPlayer * 3.8f, textPaint)
            }
        }
    }
}

private data class ClubKitOption(
    val key: String,
    val label: String,
    val aliases: List<String> = emptyList()
)

private val CLUB_KIT_OPTIONS = listOf(
    ClubKitOption("club_real_madrid", "Real Madrid", listOf("real madrid", "madrid")),
    ClubKitOption("club_barcelona", "Barcelona", listOf("barcelona", "barca", "fc barcelona")),
    ClubKitOption("club_manchester_city", "Manchester City", listOf("man city", "manchester city", "city")),
    ClubKitOption("club_liverpool", "Liverpool", listOf("liverpool")),
    ClubKitOption("club_juventus", "Juventus", listOf("juventus", "juve")),
    ClubKitOption("club_ac_milan", "AC Milan", listOf("ac milan", "milan")),
    ClubKitOption("club_inter", "Inter", listOf("inter", "internazionale")),
    ClubKitOption("club_psg", "PSG", listOf("psg", "paris saint germain")),
    ClubKitOption("club_bayern", "Bayern", listOf("bayern", "bayern munich")),
    ClubKitOption("club_boca", "Boca Juniors", listOf("boca", "boca juniors")),
    ClubKitOption("club_river", "River Plate", listOf("river", "river plate")),
    ClubKitOption("club_racing", "Racing Club", listOf("racing", "racing club")),
    ClubKitOption("club_independiente", "Independiente", listOf("independiente")),
    ClubKitOption("club_flamengo", "Flamengo", listOf("flamengo")),
    ClubKitOption("club_palmeiras", "Palmeiras", listOf("palmeiras")),
    ClubKitOption("club_corinthians", "Corinthians", listOf("corinthians")),
    ClubKitOption("club_santos", "Santos", listOf("santos")),
    ClubKitOption("club_sao_paulo", "Sao Paulo", listOf("sao paulo", "sao-paulo")),
    ClubKitOption("club_gremio", "Gremio", listOf("gremio", "gremio porto alegre")),
    ClubKitOption("club_internacional", "Internacional", listOf("internacional", "inter porto alegre")),
    ClubKitOption("club_atletico_mineiro", "Atletico Mineiro", listOf("atletico mineiro", "galo")),
    ClubKitOption("club_fluminense", "Fluminense", listOf("fluminense")),
    ClubKitOption("club_colo_colo", "Colo-Colo", listOf("colo colo", "colo-colo")),
    ClubKitOption("club_u_de_chile", "Universidad de Chile", listOf("universidad de chile", "u de chile")),
    ClubKitOption("club_nacional", "Nacional", listOf("nacional uruguay", "nacional")),
    ClubKitOption("club_penarol", "Penarol", listOf("penarol")),
    ClubKitOption("club_olimpia", "Olimpia", listOf("olimpia", "olimpia asuncion")),
    ClubKitOption("club_atletico_nacional", "Atletico Nacional", listOf("atletico nacional", "nacional medellin"))
)

private fun clubKitKeyFromSelection(selection: String?): String? {
    val normalized = selection?.trim()?.lowercase() ?: return null
    return CLUB_KIT_OPTIONS.firstOrNull { option ->
        option.key.equals(normalized, ignoreCase = true) ||
            option.label.equals(normalized, ignoreCase = true) ||
            option.aliases.any { it.equals(normalized, ignoreCase = true) }
    }?.key
}

private enum class NationPattern {
    SOLID,
    VERTICAL_STRIPES,
    HORIZONTAL_STRIPES,
    CHEVRON,
    CENTER_STRIPE,
    HORIZONTAL_BAND
}

private data class NationKitStyle(
    val primary: Int,
    val secondary: Int,
    val trim: Int,
    val pattern: NationPattern
)

private fun nationKitStyleFromKey(nationKey: String?): NationKitStyle? {
    return when (nationKey?.lowercase()) {
        "brazil" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F4C20D"),
            secondary = AndroidColor.parseColor("#0B8E5E"),
            trim = AndroidColor.parseColor("#0B8E5E"),
            pattern = NationPattern.SOLID
        )
        "argentina" -> NationKitStyle(
            primary = AndroidColor.parseColor("#E8F3FA"),
            secondary = AndroidColor.parseColor("#6EA6D8"),
            trim = AndroidColor.parseColor("#0C4A64"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "spain" -> NationKitStyle(
            primary = AndroidColor.parseColor("#B51122"),
            secondary = AndroidColor.parseColor("#8F0C1A"),
            trim = AndroidColor.parseColor("#F0C36C"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "france" -> NationKitStyle(
            primary = AndroidColor.parseColor("#4B5C96"),
            secondary = AndroidColor.parseColor("#3E4D84"),
            trim = AndroidColor.parseColor("#DCE5EA"),
            pattern = NationPattern.SOLID
        )
        "germany" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F4F4F4"),
            secondary = AndroidColor.parseColor("#C8102E"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.CHEVRON
        )
        "italy" -> NationKitStyle(
            primary = AndroidColor.parseColor("#4A67C6"),
            secondary = AndroidColor.parseColor("#3A54AB"),
            trim = AndroidColor.parseColor("#E9F1F5"),
            pattern = NationPattern.SOLID
        )
        "netherlands" -> NationKitStyle(
            primary = AndroidColor.parseColor("#FF6A00"),
            secondary = AndroidColor.parseColor("#E65F00"),
            trim = AndroidColor.parseColor("#D95A00"),
            pattern = NationPattern.SOLID
        )
        "portugal" -> NationKitStyle(
            primary = AndroidColor.parseColor("#C61D4A"),
            secondary = AndroidColor.parseColor("#B51A43"),
            trim = AndroidColor.parseColor("#2A9A64"),
            pattern = NationPattern.SOLID
        )
        "england" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F3F3F3"),
            secondary = AndroidColor.parseColor("#E8E8E8"),
            trim = AndroidColor.parseColor("#C9102E"),
            pattern = NationPattern.SOLID
        )
        "uruguay" -> NationKitStyle(
            primary = AndroidColor.parseColor("#8CB1DE"),
            secondary = AndroidColor.parseColor("#B9D2EE"),
            trim = AndroidColor.parseColor("#E4EEF8"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "belgium" -> NationKitStyle(
            primary = AndroidColor.parseColor("#C9152A"),
            secondary = AndroidColor.parseColor("#AF1224"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.SOLID
        )
        "united_states" -> NationKitStyle(
            primary = AndroidColor.parseColor("#0E63C7"),
            secondary = AndroidColor.parseColor("#1C78DE"),
            trim = AndroidColor.parseColor("#CF1228"),
            pattern = NationPattern.HORIZONTAL_STRIPES
        )
        "club_real_madrid" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F6F6F6"),
            secondary = AndroidColor.parseColor("#EDEDED"),
            trim = AndroidColor.parseColor("#1E4FA3"),
            pattern = NationPattern.SOLID
        )
        "club_barcelona" -> NationKitStyle(
            primary = AndroidColor.parseColor("#A50044"),
            secondary = AndroidColor.parseColor("#004D98"),
            trim = AndroidColor.parseColor("#F6C200"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_manchester_city" -> NationKitStyle(
            primary = AndroidColor.parseColor("#6CABDD"),
            secondary = AndroidColor.parseColor("#8BC0EA"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.SOLID
        )
        "club_liverpool" -> NationKitStyle(
            primary = AndroidColor.parseColor("#C8102E"),
            secondary = AndroidColor.parseColor("#AE1028"),
            trim = AndroidColor.parseColor("#F5F5F5"),
            pattern = NationPattern.SOLID
        )
        "club_juventus" -> NationKitStyle(
            primary = AndroidColor.parseColor("#FFFFFF"),
            secondary = AndroidColor.parseColor("#141414"),
            trim = AndroidColor.parseColor("#0E0E0E"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_ac_milan" -> NationKitStyle(
            primary = AndroidColor.parseColor("#B20E22"),
            secondary = AndroidColor.parseColor("#111111"),
            trim = AndroidColor.parseColor("#F5F5F5"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_inter" -> NationKitStyle(
            primary = AndroidColor.parseColor("#0047AB"),
            secondary = AndroidColor.parseColor("#0F0F0F"),
            trim = AndroidColor.parseColor("#F5F5F5"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_psg" -> NationKitStyle(
            primary = AndroidColor.parseColor("#0B1D51"),
            secondary = AndroidColor.parseColor("#D60029"),
            trim = AndroidColor.parseColor("#F5F5F5"),
            pattern = NationPattern.CENTER_STRIPE
        )
        "club_bayern" -> NationKitStyle(
            primary = AndroidColor.parseColor("#D1022A"),
            secondary = AndroidColor.parseColor("#C4102E"),
            trim = AndroidColor.parseColor("#F5F5F5"),
            pattern = NationPattern.SOLID
        )
        "club_boca" -> NationKitStyle(
            primary = AndroidColor.parseColor("#003594"),
            secondary = AndroidColor.parseColor("#F6C700"),
            trim = AndroidColor.parseColor("#F6C700"),
            pattern = NationPattern.HORIZONTAL_BAND
        )
        "club_river" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#D91E2B"),
            trim = AndroidColor.parseColor("#1A1A1A"),
            pattern = NationPattern.CENTER_STRIPE
        )
        "club_racing" -> NationKitStyle(
            primary = AndroidColor.parseColor("#E8F4FF"),
            secondary = AndroidColor.parseColor("#69AEEB"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_independiente" -> NationKitStyle(
            primary = AndroidColor.parseColor("#D61F26"),
            secondary = AndroidColor.parseColor("#BE1A22"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.SOLID
        )
        "club_flamengo" -> NationKitStyle(
            primary = AndroidColor.parseColor("#111111"),
            secondary = AndroidColor.parseColor("#C8102E"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.HORIZONTAL_STRIPES
        )
        "club_palmeiras" -> NationKitStyle(
            primary = AndroidColor.parseColor("#0E7A3A"),
            secondary = AndroidColor.parseColor("#0A6230"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.SOLID
        )
        "club_corinthians" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#ECECEC"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.SOLID
        )
        "club_santos" -> NationKitStyle(
            primary = AndroidColor.parseColor("#FFFFFF"),
            secondary = AndroidColor.parseColor("#F0F0F0"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.SOLID
        )
        "club_sao_paulo" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#C8102E"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.CENTER_STRIPE
        )
        "club_gremio" -> NationKitStyle(
            primary = AndroidColor.parseColor("#57A8E6"),
            secondary = AndroidColor.parseColor("#111111"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_internacional" -> NationKitStyle(
            primary = AndroidColor.parseColor("#C8102E"),
            secondary = AndroidColor.parseColor("#B20E29"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.SOLID
        )
        "club_atletico_mineiro" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#111111"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_fluminense" -> NationKitStyle(
            primary = AndroidColor.parseColor("#7A0F2E"),
            secondary = AndroidColor.parseColor("#0F7B4B"),
            trim = AndroidColor.parseColor("#F7F7F7"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_colo_colo" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#ECECEC"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.SOLID
        )
        "club_u_de_chile" -> NationKitStyle(
            primary = AndroidColor.parseColor("#0B4FB3"),
            secondary = AndroidColor.parseColor("#0A4193"),
            trim = AndroidColor.parseColor("#E21F26"),
            pattern = NationPattern.SOLID
        )
        "club_nacional" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#0B3FA0"),
            trim = AndroidColor.parseColor("#C8102E"),
            pattern = NationPattern.CENTER_STRIPE
        )
        "club_penarol" -> NationKitStyle(
            primary = AndroidColor.parseColor("#111111"),
            secondary = AndroidColor.parseColor("#F6C700"),
            trim = AndroidColor.parseColor("#F6C700"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        "club_olimpia" -> NationKitStyle(
            primary = AndroidColor.parseColor("#F7F7F7"),
            secondary = AndroidColor.parseColor("#111111"),
            trim = AndroidColor.parseColor("#111111"),
            pattern = NationPattern.HORIZONTAL_BAND
        )
        "club_atletico_nacional" -> NationKitStyle(
            primary = AndroidColor.parseColor("#0F8A45"),
            secondary = AndroidColor.parseColor("#F7F7F7"),
            trim = AndroidColor.parseColor("#0F8A45"),
            pattern = NationPattern.VERTICAL_STRIPES
        )
        else -> null
    }
}

private fun nationPrimaryAndroidColor(nationKey: String?): Int {
    return nationKitStyleFromKey(nationKey)?.primary ?: AndroidColor.parseColor("#FFEB3B")
}

private fun nationPrimaryComposeColor(nationKey: String?): Color {
    return Color(nationPrimaryAndroidColor(nationKey))
}

private fun drawNationCrest(
    canvas: android.graphics.Canvas,
    left: Float,
    top: Float,
    size: Float,
    nationKey: String
) {
    val crestX = left + size * 0.46f
    val crestY = top + size * 0.34f
    val crestW = size * 0.162f
    val crestH = size * 0.1944f

    fun shieldPath(x: Float, y: Float, w: Float, h: Float): android.graphics.Path {
        return android.graphics.Path().apply {
            moveTo(x + w * 0.5f, y)
            lineTo(x + w, y + h * 0.18f)
            lineTo(x + w * 0.92f, y + h * 0.7f)
            lineTo(x + w * 0.5f, y + h)
            lineTo(x + w * 0.08f, y + h * 0.7f)
            lineTo(x, y + h * 0.18f)
            close()
        }
    }

    val border = Paint().apply {
        color = AndroidColor.WHITE
        this.style = Paint.Style.STROKE
        strokeWidth = size * 0.018f
        isAntiAlias = true
    }
    val fill = Paint().apply {
        this.style = Paint.Style.FILL
        isAntiAlias = true
    }

    when (nationKey.lowercase()) {
        "brazil" -> {
            fill.color = AndroidColor.parseColor("#1A4FA3")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            fill.color = AndroidColor.parseColor("#F4C20D")
            val diamond = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.5f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.82f, crestY + crestH * 0.52f)
                lineTo(crestX + crestW * 0.5f, crestY + crestH * 0.84f)
                lineTo(crestX + crestW * 0.18f, crestY + crestH * 0.52f)
                close()
            }
            canvas.drawPath(diamond, fill)
            fill.color = AndroidColor.parseColor("#1A4FA3")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.16f, fill)
        }
        "argentina" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#E8F3FA")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#6EA6D8")
            canvas.save()
            canvas.clipPath(shield)
            canvas.drawRect(crestX + crestW * 0.16f, crestY, crestX + crestW * 0.36f, crestY + crestH, fill)
            canvas.drawRect(crestX + crestW * 0.64f, crestY, crestX + crestW * 0.84f, crestY + crestH, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "spain" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#F0C36C")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#B51122")
            canvas.drawRect(crestX + crestW * 0.22f, crestY + crestH * 0.2f, crestX + crestW * 0.78f, crestY + crestH * 0.8f, fill)
            canvas.drawPath(shield, border)
        }
        "france" -> {
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawCircle(crestX + crestW * 0.56f, crestY + crestH * 0.5f, crestW * 0.15f, fill)
            canvas.drawCircle(crestX + crestW * 0.44f, crestY + crestH * 0.41f, crestW * 0.08f, fill)
            val body = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.35f, crestY + crestH * 0.72f)
                lineTo(crestX + crestW * 0.58f, crestY + crestH * 0.58f)
                lineTo(crestX + crestW * 0.72f, crestY + crestH * 0.8f)
                lineTo(crestX + crestW * 0.52f, crestY + crestH * 0.86f)
                close()
            }
            canvas.drawPath(body, fill)
            val legPaint = Paint().apply {
                color = AndroidColor.parseColor("#FFFFFF")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.014f
                isAntiAlias = true
            }
            canvas.drawLine(crestX + crestW * 0.48f, crestY + crestH * 0.86f, crestX + crestW * 0.46f, crestY + crestH * 0.96f, legPaint)
            canvas.drawLine(crestX + crestW * 0.56f, crestY + crestH * 0.84f, crestX + crestW * 0.58f, crestY + crestH * 0.95f, legPaint)
        }
        "germany" -> {
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.5f, crestW * 0.33f, fill)
            val ring = Paint().apply {
                color = AndroidColor.parseColor("#FFFFFF")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.014f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.5f, crestW * 0.23f, ring)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawRect(crestX + crestW * 0.2f, crestY + crestH * 0.5f, crestX + crestW * 0.8f, crestY + crestH * 0.67f, fill)
        }
        "italy" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#009246")
            canvas.drawRect(crestX, crestY, crestX + crestW * 0.33f, crestY + crestH, fill)
            fill.color = AndroidColor.parseColor("#CE2B37")
            canvas.drawRect(crestX + crestW * 0.67f, crestY, crestX + crestW, crestY + crestH, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "netherlands" -> {
            fill.color = AndroidColor.parseColor("#FFFFFF")
            val lion = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.42f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.58f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.66f, crestY + crestH * 0.38f)
                lineTo(crestX + crestW * 0.58f, crestY + crestH * 0.8f)
                lineTo(crestX + crestW * 0.42f, crestY + crestH * 0.8f)
                lineTo(crestX + crestW * 0.34f, crestY + crestH * 0.38f)
                close()
            }
            canvas.drawPath(lion, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.14f, crestW * 0.07f, fill)
        }
        "portugal" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#046A38")
            canvas.drawRect(crestX, crestY, crestX + crestW * 0.35f, crestY + crestH, fill)
            canvas.restore()
            fill.color = AndroidColor.parseColor("#F0C36C")
            canvas.drawCircle(crestX + crestW * 0.52f, crestY + crestH * 0.52f, crestW * 0.14f, fill)
            canvas.drawPath(shield, border)
        }
        "england" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawRect(crestX + crestW * 0.45f, crestY + crestH * 0.12f, crestX + crestW * 0.55f, crestY + crestH * 0.88f, fill)
            canvas.drawRect(crestX + crestW * 0.18f, crestY + crestH * 0.45f, crestX + crestW * 0.82f, crestY + crestH * 0.55f, fill)
            canvas.drawPath(shield, border)
        }
        "uruguay" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#EAF3FB")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#7EA7D8")
            canvas.save()
            canvas.clipPath(shield)
            var y = crestY + crestH * 0.2f
            val h = crestH * 0.12f
            while (y < crestY + crestH * 0.9f) {
                canvas.drawRect(crestX + crestW * 0.12f, y, crestX + crestW * 0.88f, y + h, fill)
                y += h * 1.8f
            }
            canvas.restore()
            fill.color = AndroidColor.parseColor("#F4C20D")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.15f, crestW * 0.08f, fill)
            canvas.drawPath(shield, border)
        }
        "belgium" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#000000")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#FFD90C")
            canvas.drawRect(crestX + crestW * 0.33f, crestY, crestX + crestW * 0.66f, crestY + crestH, fill)
            fill.color = AndroidColor.parseColor("#EF3340")
            canvas.drawRect(crestX + crestW * 0.66f, crestY, crestX + crestW, crestY + crestH, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "united_states" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#CE1126")
            val stripeH = crestH * 0.1f
            var y = crestY + crestH * 0.1f
            while (y < crestY + crestH * 0.92f) {
                canvas.drawRect(crestX + crestW * 0.08f, y, crestX + crestW * 0.92f, y + stripeH, fill)
                y += stripeH * 1.9f
            }
            fill.color = AndroidColor.parseColor("#002868")
            canvas.drawRect(crestX + crestW * 0.08f, crestY + crestH * 0.1f, crestX + crestW * 0.5f, crestY + crestH * 0.48f, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawCircle(crestX + crestW * 0.2f, crestY + crestH * 0.25f, crestW * 0.03f, fill)
            canvas.drawCircle(crestX + crestW * 0.3f, crestY + crestH * 0.3f, crestW * 0.03f, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_real_madrid" -> {
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            val ring = Paint().apply {
                color = AndroidColor.parseColor("#1E4FA3")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.013f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.24f, ring)
            fill.color = AndroidColor.parseColor("#D9A441")
            canvas.drawRect(crestX + crestW * 0.36f, crestY + crestH * 0.06f, crestX + crestW * 0.64f, crestY + crestH * 0.16f, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.03f, crestW * 0.06f, fill)
        }
        "club_barcelona" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#A50044")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#004D98")
            val stripeW = crestW * 0.2f
            var x = crestX + crestW * 0.1f
            while (x < crestX + crestW) {
                canvas.drawRect(x, crestY, x + stripeW, crestY + crestH, fill)
                x += stripeW * 2f
            }
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_manchester_city" -> {
            fill.color = AndroidColor.parseColor("#6CABDD")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            val ring = Paint().apply {
                color = AndroidColor.parseColor("#FFFFFF")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.013f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.26f, ring)
            fill.color = AndroidColor.parseColor("#1B3A6B")
            canvas.drawRect(crestX + crestW * 0.44f, crestY + crestH * 0.26f, crestX + crestW * 0.56f, crestY + crestH * 0.72f, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.26f, crestW * 0.07f, fill)
        }
        "club_liverpool" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            val bird = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.5f, crestY + crestH * 0.22f)
                lineTo(crestX + crestW * 0.62f, crestY + crestH * 0.45f)
                lineTo(crestX + crestW * 0.56f, crestY + crestH * 0.8f)
                lineTo(crestX + crestW * 0.46f, crestY + crestH * 0.8f)
                lineTo(crestX + crestW * 0.38f, crestY + crestH * 0.45f)
                close()
            }
            canvas.drawPath(bird, fill)
            canvas.drawPath(shield, border)
        }
        "club_juventus" -> {
            fill.color = AndroidColor.parseColor("#111111")
            val jMark = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.34f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.64f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.64f, crestY + crestH * 0.72f)
                cubicTo(
                    crestX + crestW * 0.64f, crestY + crestH * 0.9f,
                    crestX + crestW * 0.4f, crestY + crestH * 0.95f,
                    crestX + crestW * 0.3f, crestY + crestH * 0.78f
                )
                lineTo(crestX + crestW * 0.42f, crestY + crestH * 0.72f)
                cubicTo(
                    crestX + crestW * 0.47f, crestY + crestH * 0.8f,
                    crestX + crestW * 0.54f, crestY + crestH * 0.76f,
                    crestX + crestW * 0.54f, crestY + crestH * 0.66f
                )
                lineTo(crestX + crestW * 0.54f, crestY + crestH * 0.2f)
                close()
            }
            canvas.drawPath(jMark, fill)
        }
        "club_ac_milan" -> {
            fill.color = AndroidColor.parseColor("#B20E22")
            canvas.drawOval(
                android.graphics.RectF(
                    crestX + crestW * 0.16f, crestY + crestH * 0.08f,
                    crestX + crestW * 0.84f, crestY + crestH * 0.92f
                ),
                fill
            )
            val stripe = Paint().apply {
                color = AndroidColor.parseColor("#111111")
                this.style = Paint.Style.FILL
                isAntiAlias = true
            }
            val stripeW = crestW * 0.08f
            var x = crestX + crestW * 0.24f
            while (x < crestX + crestW * 0.8f) {
                canvas.drawRect(x, crestY + crestH * 0.12f, x + stripeW, crestY + crestH * 0.88f, stripe)
                x += stripeW * 2f
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.5f, crestW * 0.34f, border)
        }
        "club_inter" -> {
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            val ringBlue = Paint().apply {
                color = AndroidColor.parseColor("#0047AB")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.014f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.26f, ringBlue)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.16f, ringBlue)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawRect(crestX + crestW * 0.48f, crestY + crestH * 0.3f, crestX + crestW * 0.52f, crestY + crestH * 0.74f, fill)
        }
        "club_psg" -> {
            fill.color = AndroidColor.parseColor("#0B1D51")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            fill.color = AndroidColor.parseColor("#D60029")
            canvas.drawRect(crestX + crestW * 0.44f, crestY + crestH * 0.18f, crestX + crestW * 0.56f, crestY + crestH * 0.86f, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.07f, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, border)
        }
        "club_bayern" -> {
            fill.color = AndroidColor.parseColor("#D1022A")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            fill.color = AndroidColor.parseColor("#1C4FB6")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.23f, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            val d = crestW * 0.06f
            canvas.drawRect(crestX + crestW * 0.45f, crestY + crestH * 0.35f, crestX + crestW * 0.45f + d, crestY + crestH * 0.35f + d, fill)
            canvas.drawRect(crestX + crestW * 0.55f, crestY + crestH * 0.44f, crestX + crestW * 0.55f + d, crestY + crestH * 0.44f + d, fill)
            canvas.drawRect(crestX + crestW * 0.45f, crestY + crestH * 0.53f, crestX + crestW * 0.45f + d, crestY + crestH * 0.53f + d, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, border)
        }
        "club_boca" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#003594")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#F6C700")
            canvas.drawRect(crestX + crestW * 0.12f, crestY + crestH * 0.42f, crestX + crestW * 0.88f, crestY + crestH * 0.58f, fill)
            fill.color = AndroidColor.parseColor("#F6C700")
            for (i in 0..2) {
                canvas.drawCircle(crestX + crestW * (0.28f + i * 0.12f), crestY + crestH * 0.3f, crestW * 0.03f, fill)
                canvas.drawCircle(crestX + crestW * (0.34f + i * 0.12f), crestY + crestH * 0.7f, crestW * 0.03f, fill)
            }
            canvas.drawPath(shield, border)
        }
        "club_river" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#F7F7F7")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#D91E2B")
            val band = android.graphics.Path().apply {
                moveTo(crestX - crestW * 0.05f, crestY + crestH * 0.22f)
                lineTo(crestX + crestW * 0.18f, crestY)
                lineTo(crestX + crestW * 1.02f, crestY + crestH * 0.78f)
                lineTo(crestX + crestW * 0.78f, crestY + crestH)
                close()
            }
            canvas.drawPath(band, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_racing" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#69AEEB")
            val stripeW = crestW * 0.13f
            var x = crestX + crestW * 0.06f
            while (x < crestX + crestW) {
                canvas.drawRect(x, crestY, x + stripeW, crestY + crestH, fill)
                x += stripeW * 2f
            }
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_independiente" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#D61F26")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawRect(crestX + crestW * 0.45f, crestY + crestH * 0.15f, crestX + crestW * 0.55f, crestY + crestH * 0.85f, fill)
            canvas.drawPath(shield, border)
        }
        "club_flamengo" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#C8102E")
            val stripeH = crestH * 0.12f
            var y = crestY + crestH * 0.06f
            while (y < crestY + crestH * 0.94f) {
                canvas.drawRect(crestX, y, crestX + crestW, y + stripeH, fill)
                y += stripeH * 1.8f
            }
            canvas.restore()
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawRect(crestX + crestW * 0.12f, crestY + crestH * 0.12f, crestX + crestW * 0.18f, crestY + crestH * 0.34f, fill)
            canvas.drawRect(crestX + crestW * 0.18f, crestY + crestH * 0.28f, crestX + crestW * 0.34f, crestY + crestH * 0.34f, fill)
            canvas.drawPath(shield, border)
        }
        "club_palmeiras" -> {
            fill.color = AndroidColor.parseColor("#0E7A3A")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            val ring = Paint().apply {
                color = AndroidColor.parseColor("#FFFFFF")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.012f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.25f, ring)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawRect(crestX + crestW * 0.44f, crestY + crestH * 0.34f, crestX + crestW * 0.49f, crestY + crestH * 0.74f, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.38f, crestW * 0.08f, ring)
        }
        "club_corinthians" -> {
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            val ring = Paint().apply {
                color = AndroidColor.parseColor("#111111")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.012f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.26f, ring)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawRect(crestX + crestW * 0.48f, crestY + crestH * 0.18f, crestX + crestW * 0.52f, crestY + crestH * 0.86f, fill)
            canvas.drawRect(crestX + crestW * 0.18f, crestY + crestH * 0.5f, crestX + crestW * 0.82f, crestY + crestH * 0.54f, fill)
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.08f, fill)
        }
        "club_santos" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#111111")
            val stripeW = crestW * 0.1f
            var x = crestX + crestW * 0.08f
            while (x < crestX + crestW * 0.92f) {
                canvas.drawRect(x, crestY + crestH * 0.24f, x + stripeW, crestY + crestH, fill)
                x += stripeW * 2f
            }
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawRect(crestX + crestW * 0.08f, crestY + crestH * 0.1f, crestX + crestW * 0.92f, crestY + crestH * 0.22f, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_sao_paulo" -> {
            val tri = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.5f, crestY + crestH)
                lineTo(crestX + crestW * 0.08f, crestY + crestH * 0.1f)
                lineTo(crestX + crestW * 0.92f, crestY + crestH * 0.1f)
                close()
            }
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(tri, fill)
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawRect(crestX + crestW * 0.08f, crestY + crestH * 0.1f, crestX + crestW * 0.5f, crestY + crestH * 0.28f, fill)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawRect(crestX + crestW * 0.5f, crestY + crestH * 0.1f, crestX + crestW * 0.92f, crestY + crestH * 0.28f, fill)
            canvas.drawPath(tri, border)
        }
        "club_gremio" -> {
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            canvas.save()
            val clip = android.graphics.Path().apply { addCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.26f, android.graphics.Path.Direction.CW) }
            canvas.clipPath(clip)
            fill.color = AndroidColor.parseColor("#57A8E6")
            canvas.drawRect(crestX + crestW * 0.24f, crestY + crestH * 0.26f, crestX + crestW * 0.76f, crestY + crestH * 0.45f, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawRect(crestX + crestW * 0.24f, crestY + crestH * 0.45f, crestX + crestW * 0.76f, crestY + crestH * 0.59f, fill)
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawRect(crestX + crestW * 0.24f, crestY + crestH * 0.59f, crestX + crestW * 0.76f, crestY + crestH * 0.78f, fill)
            canvas.restore()
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, border)
        }
        "club_internacional" -> {
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, fill)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            val swirl = Paint().apply {
                color = AndroidColor.parseColor("#FFFFFF")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.014f
                isAntiAlias = true
            }
            val r = android.graphics.RectF(
                crestX + crestW * 0.3f,
                crestY + crestH * 0.32f,
                crestX + crestW * 0.7f,
                crestY + crestH * 0.74f
            )
            canvas.drawArc(r, 220f, 240f, false, swirl)
            canvas.drawCircle(crestX + crestW * 0.58f, crestY + crestH * 0.52f, crestW * 0.04f, fill)
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.52f, crestW * 0.34f, border)
        }
        "club_atletico_mineiro" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            val stripeW = crestW * 0.09f
            var x = crestX + crestW * 0.12f
            while (x < crestX + crestW * 0.88f) {
                canvas.drawRect(x, crestY + crestH * 0.3f, x + stripeW, crestY + crestH * 0.96f, fill)
                x += stripeW * 2f
            }
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawRect(crestX + crestW * 0.12f, crestY + crestH * 0.08f, crestX + crestW * 0.88f, crestY + crestH * 0.26f, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_fluminense" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#7A0F2E")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#0F7B4B")
            val yShape = android.graphics.Path().apply {
                moveTo(crestX + crestW * 0.12f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.38f, crestY + crestH * 0.48f)
                lineTo(crestX + crestW * 0.46f, crestY + crestH * 0.42f)
                lineTo(crestX + crestW * 0.3f, crestY + crestH * 0.9f)
                lineTo(crestX + crestW * 0.7f, crestY + crestH * 0.9f)
                lineTo(crestX + crestW * 0.54f, crestY + crestH * 0.42f)
                lineTo(crestX + crestW * 0.62f, crestY + crestH * 0.48f)
                lineTo(crestX + crestW * 0.88f, crestY + crestH * 0.2f)
                lineTo(crestX + crestW * 0.72f, crestY + crestH * 0.1f)
                lineTo(crestX + crestW * 0.5f, crestY + crestH * 0.34f)
                lineTo(crestX + crestW * 0.28f, crestY + crestH * 0.1f)
                close()
            }
            canvas.drawPath(yShape, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_colo_colo" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#1B3E8A")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawRect(crestX + crestW * 0.1f, crestY + crestH * 0.6f, crestX + crestW * 0.9f, crestY + crestH * 0.74f, fill)
            val profile = Paint().apply {
                color = AndroidColor.parseColor("#FFFFFF")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.01f
                isAntiAlias = true
            }
            canvas.drawCircle(crestX + crestW * 0.46f, crestY + crestH * 0.58f, crestW * 0.12f, profile)
            canvas.drawLine(crestX + crestW * 0.56f, crestY + crestH * 0.58f, crestX + crestW * 0.64f, crestY + crestH * 0.62f, profile)
            canvas.drawPath(shield, border)
        }
        "club_u_de_chile" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#0B4FB3")
            canvas.drawPath(shield, fill)
            val vPaint = Paint().apply {
                color = AndroidColor.parseColor("#E21F26")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.02f
                isAntiAlias = true
            }
            canvas.drawLine(
                crestX + crestW * 0.32f,
                crestY + crestH * 0.28f,
                crestX + crestW * 0.5f,
                crestY + crestH * 0.76f,
                vPaint
            )
            canvas.drawLine(
                crestX + crestW * 0.68f,
                crestY + crestH * 0.28f,
                crestX + crestW * 0.5f,
                crestY + crestH * 0.76f,
                vPaint
            )
            canvas.drawPath(shield, border)
        }
        "club_nacional" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#0B3FA0")
            canvas.drawRect(crestX + crestW * 0.08f, crestY, crestX + crestW * 0.33f, crestY + crestH, fill)
            fill.color = AndroidColor.parseColor("#C8102E")
            canvas.drawRect(crestX + crestW * 0.67f, crestY, crestX + crestW * 0.92f, crestY + crestH, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        "club_penarol" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#F6C700")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#111111")
            val stripeW = crestW * 0.1f
            var x = crestX + crestW * 0.1f
            while (x < crestX + crestW * 0.9f) {
                canvas.drawRect(x, crestY + crestH * 0.25f, x + stripeW, crestY + crestH, fill)
                x += stripeW * 2f
            }
            canvas.restore()
            fill.color = AndroidColor.parseColor("#111111")
            for (i in 0..4) {
                canvas.drawCircle(crestX + crestW * (0.2f + i * 0.14f), crestY + crestH * 0.14f, crestW * 0.03f, fill)
            }
            canvas.drawPath(shield, border)
        }
        "club_olimpia" -> {
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawOval(
                android.graphics.RectF(
                    crestX + crestW * 0.12f,
                    crestY + crestH * 0.12f,
                    crestX + crestW * 0.88f,
                    crestY + crestH * 0.88f
                ),
                fill
            )
            val ring = Paint().apply {
                color = AndroidColor.parseColor("#111111")
                this.style = Paint.Style.STROKE
                strokeWidth = size * 0.012f
                isAntiAlias = true
            }
            canvas.drawOval(
                android.graphics.RectF(
                    crestX + crestW * 0.2f,
                    crestY + crestH * 0.2f,
                    crestX + crestW * 0.8f,
                    crestY + crestH * 0.8f
                ),
                ring
            )
            fill.color = AndroidColor.parseColor("#111111")
            canvas.drawRect(crestX + crestW * 0.28f, crestY + crestH * 0.47f, crestX + crestW * 0.72f, crestY + crestH * 0.55f, fill)
        }
        "club_atletico_nacional" -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#FFFFFF")
            canvas.drawPath(shield, fill)
            canvas.save()
            canvas.clipPath(shield)
            fill.color = AndroidColor.parseColor("#0F8A45")
            val stripeW = crestW * 0.11f
            var x = crestX + crestW * 0.08f
            while (x < crestX + crestW * 0.92f) {
                canvas.drawRect(x, crestY + crestH * 0.2f, x + stripeW, crestY + crestH, fill)
                x += stripeW * 2f
            }
            fill.color = AndroidColor.parseColor("#0F8A45")
            canvas.drawRect(crestX + crestW * 0.2f, crestY + crestH * 0.06f, crestX + crestW * 0.8f, crestY + crestH * 0.2f, fill)
            canvas.restore()
            canvas.drawPath(shield, border)
        }
        else -> {
            val shield = shieldPath(crestX, crestY, crestW, crestH)
            fill.color = AndroidColor.parseColor("#EAEAEA")
            canvas.drawPath(shield, fill)
            fill.color = AndroidColor.parseColor("#3A3A3A")
            canvas.drawCircle(crestX + crestW * 0.5f, crestY + crestH * 0.5f, crestW * 0.12f, fill)
            canvas.drawPath(shield, border)
        }
    }
}

private fun blendColor(from: Int, to: Int, amount: Float): Int {
    val t = amount.coerceIn(0f, 1f)
    fun lerp(a: Int, b: Int): Int = (a + ((b - a) * t)).toInt().coerceIn(0, 255)
    return AndroidColor.argb(
        lerp(AndroidColor.alpha(from), AndroidColor.alpha(to)),
        lerp(AndroidColor.red(from), AndroidColor.red(to)),
        lerp(AndroidColor.green(from), AndroidColor.green(to)),
        lerp(AndroidColor.blue(from), AndroidColor.blue(to))
    )
}

private fun lightenColor(color: Int, amount: Float): Int = blendColor(color, AndroidColor.WHITE, amount)
private fun darkenColor(color: Int, amount: Float): Int = blendColor(color, AndroidColor.BLACK, amount)

private fun colorLuminance(color: Int): Float {
    val r = AndroidColor.red(color) / 255f
    val g = AndroidColor.green(color) / 255f
    val b = AndroidColor.blue(color) / 255f
    return 0.2126f * r + 0.7152f * g + 0.0722f * b
}

private fun adaptiveTrimColor(baseColor: Int): Int {
    return if (colorLuminance(baseColor) > 0.62f) AndroidColor.parseColor("#1C1C1C") else AndroidColor.WHITE
}

private fun createShirtPath(left: Float, top: Float, size: Float): android.graphics.Path {
    val scale = size / 24f
    return android.graphics.Path().apply {
        moveTo(left + 8f * scale, top + 4f * scale)
        lineTo(left + 10f * scale, top + 4f * scale)
        lineTo(left + 12f * scale, top + 6f * scale)
        lineTo(left + 14f * scale, top + 4f * scale)
        lineTo(left + 16f * scale, top + 4f * scale)
        lineTo(left + 20f * scale, top + 6f * scale)
        lineTo(left + 18f * scale, top + 10f * scale)
        lineTo(left + 16f * scale, top + 9f * scale)
        lineTo(left + 16f * scale, top + 20f * scale)
        lineTo(left + 8f * scale, top + 20f * scale)
        lineTo(left + 8f * scale, top + 9f * scale)
        lineTo(left + 6f * scale, top + 10f * scale)
        lineTo(left + 4f * scale, top + 6f * scale)
        close()
    }
}

private fun drawStylizedKitShirt(
    canvas: android.graphics.Canvas,
    left: Float,
    top: Float,
    size: Float,
    style: NationKitStyle,
    crestKey: String?
) {
    val shirtPath = createShirtPath(left, top, size)

    val dropShadowPath = android.graphics.Path(shirtPath).apply {
        offset(size * 0.035f, size * 0.055f)
    }
    val shadowPaint = Paint().apply {
        color = AndroidColor.argb(90, 0, 0, 0)
        this.style = Paint.Style.FILL
        isAntiAlias = true
    }
    canvas.drawPath(dropShadowPath, shadowPaint)

    val baseGradient = Paint().apply {
        shader = android.graphics.LinearGradient(
            left + size * 0.5f,
            top,
            left + size * 0.5f,
            top + size,
            lightenColor(style.primary, 0.18f),
            darkenColor(style.primary, 0.22f),
            android.graphics.Shader.TileMode.CLAMP
        )
        this.style = Paint.Style.FILL
        isAntiAlias = true
    }
    canvas.drawPath(shirtPath, baseGradient)

    canvas.save()
    canvas.clipPath(shirtPath)

    when (style.pattern) {
        NationPattern.SOLID -> {
            val sideShade = Paint().apply {
                color = darkenColor(style.secondary, 0.08f)
                this.style = Paint.Style.FILL
                isAntiAlias = true
                alpha = 60
            }
            canvas.drawRect(left + size * 0.12f, top + size * 0.25f, left + size * 0.3f, top + size, sideShade)
            canvas.drawRect(left + size * 0.7f, top + size * 0.25f, left + size * 0.88f, top + size, sideShade)
        }
        NationPattern.VERTICAL_STRIPES -> {
            val stripe = Paint().apply {
                color = style.secondary
                this.style = Paint.Style.FILL
                isAntiAlias = true
                alpha = 220
            }
            val stripeW = size * 0.15f
            var x = left + size * 0.15f
            while (x < left + size * 0.86f) {
                canvas.drawRect(x, top + size * 0.08f, x + stripeW, top + size * 0.98f, stripe)
                x += stripeW * 2f
            }
        }
        NationPattern.HORIZONTAL_STRIPES -> {
            val stripe = Paint().apply {
                color = style.secondary
                this.style = Paint.Style.FILL
                isAntiAlias = true
                alpha = 220
            }
            val stripeH = size * 0.09f
            var y = top + size * 0.12f
            while (y < top + size * 0.98f) {
                canvas.drawRect(left + size * 0.08f, y, left + size * 0.92f, y + stripeH, stripe)
                y += stripeH * 2f
            }
        }
        NationPattern.CHEVRON -> {
            val chevron = Paint().apply {
                color = style.secondary
                this.style = Paint.Style.FILL
                isAntiAlias = true
                alpha = 220
            }
            val y1 = top + size * 0.34f
            val y2 = top + size * 0.56f
            val xMid = left + size * 0.5f
            val outer = android.graphics.Path().apply {
                moveTo(left + size * 0.08f, y1)
                lineTo(xMid, y2)
                lineTo(left + size * 0.92f, y1)
                lineTo(left + size * 0.92f, y1 + size * 0.1f)
                lineTo(xMid, y2 + size * 0.1f)
                lineTo(left + size * 0.08f, y1 + size * 0.1f)
                close()
            }
            canvas.drawPath(outer, chevron)
        }
        NationPattern.CENTER_STRIPE -> {
            val stripe = Paint().apply {
                color = style.secondary
                this.style = Paint.Style.FILL
                isAntiAlias = true
                alpha = 228
            }
            val stripeW = size * 0.28f
            val x = left + (size - stripeW) * 0.5f
            canvas.drawRect(x, top + size * 0.08f, x + stripeW, top + size * 0.98f, stripe)
        }
        NationPattern.HORIZONTAL_BAND -> {
            val band = Paint().apply {
                color = style.secondary
                this.style = Paint.Style.FILL
                isAntiAlias = true
                alpha = 228
            }
            val yTop = top + size * 0.42f
            val yBottom = top + size * 0.62f
            canvas.drawRect(left + size * 0.08f, yTop, left + size * 0.92f, yBottom, band)
        }
    }

    val collarPaint = Paint().apply {
        color = darkenColor(style.primary, 0.35f)
        this.style = Paint.Style.STROKE
        strokeWidth = size * 0.032f
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }
    canvas.drawArc(
        android.graphics.RectF(
            left + size * 0.38f,
            top + size * 0.08f,
            left + size * 0.62f,
            top + size * 0.3f
        ),
        200f,
        140f,
        false,
        collarPaint
    )

    val foldPaint = Paint().apply {
        color = darkenColor(style.primary, 0.45f)
        this.style = Paint.Style.STROKE
        strokeWidth = size * 0.02f
        isAntiAlias = true
        alpha = 85
    }
    canvas.drawLine(left + size * 0.3f, top + size * 0.3f, left + size * 0.35f, top + size * 0.9f, foldPaint)
    canvas.drawLine(left + size * 0.7f, top + size * 0.3f, left + size * 0.65f, top + size * 0.9f, foldPaint)

    val gloss = Paint().apply {
        shader = android.graphics.RadialGradient(
            left + size * 0.34f,
            top + size * 0.22f,
            size * 0.55f,
            intArrayOf(AndroidColor.argb(110, 255, 255, 255), AndroidColor.TRANSPARENT),
            floatArrayOf(0f, 1f),
            android.graphics.Shader.TileMode.CLAMP
        )
        this.style = Paint.Style.FILL
        isAntiAlias = true
    }
    canvas.drawOval(
        android.graphics.RectF(
            left + size * 0.08f,
            top + size * 0.05f,
            left + size * 0.78f,
            top + size * 0.66f
        ),
        gloss
    )

    canvas.restore()

    val trim = Paint().apply {
        color = style.trim
        this.style = Paint.Style.STROKE
        strokeWidth = size * 0.034f
        isAntiAlias = true
    }
    canvas.drawPath(shirtPath, trim)

    val edgeHighlight = Paint().apply {
        color = AndroidColor.argb(80, 255, 255, 255)
        this.style = Paint.Style.STROKE
        strokeWidth = size * 0.012f
        isAntiAlias = true
    }
    canvas.drawPath(shirtPath, edgeHighlight)

    if (!crestKey.isNullOrBlank()) {
        drawNationCrest(
            canvas = canvas,
            left = left,
            top = top,
            size = size,
            nationKey = crestKey
        )
    }
}

private fun drawNationKitShirt(
    canvas: android.graphics.Canvas,
    left: Float,
    top: Float,
    size: Float,
    nationKey: String
) {
    val style = nationKitStyleFromKey(nationKey) ?: return
    drawStylizedKitShirt(canvas, left, top, size, style, nationKey)
}

private fun drawSolidKitShirt(
    canvas: android.graphics.Canvas,
    left: Float,
    top: Float,
    size: Float,
    baseColor: Int
) {
    val style = NationKitStyle(
        primary = baseColor,
        secondary = if (colorLuminance(baseColor) > 0.58f) darkenColor(baseColor, 0.2f) else lightenColor(baseColor, 0.2f),
        trim = adaptiveTrimColor(baseColor),
        pattern = NationPattern.SOLID
    )
    drawStylizedKitShirt(canvas, left, top, size, style, crestKey = null)
}

private fun teamColorFromName(name: String): Color {
    clubKitKeyFromSelection(name)?.let { kitKey ->
        nationKitStyleFromKey(kitKey)?.let { return Color(it.primary) }
    }
    return when (name.lowercase()) {
        "blanco", "white" -> Color.White
        "negro", "black" -> Color.Black
        "azul", "blue" -> Color(0xFF2196F3)
        "rojo", "red" -> Color(0xFFF44336)
        "amarillo", "yellow" -> Color(0xFFFFEB3B)
        "verde", "green" -> Color(0xFF4CAF50)
        "morado", "purple" -> Color(0xFF9C27B0)
        "rosado", "rosa", "pink" -> Color(0xFFE91E63)
        else -> Color(0xFFFFEB3B)
    }
}

private fun teamAndroidColorFromName(name: String): Int {
    clubKitKeyFromSelection(name)?.let { kitKey ->
        nationKitStyleFromKey(kitKey)?.let { return it.primary }
    }
    return when (name.lowercase()) {
        "blanco", "white" -> AndroidColor.WHITE
        "negro", "black" -> AndroidColor.BLACK
        "azul", "blue" -> AndroidColor.parseColor("#2196F3")
        "rojo", "red" -> AndroidColor.parseColor("#F44336")
        "amarillo", "yellow" -> AndroidColor.parseColor("#FFEB3B")
        "verde", "green" -> AndroidColor.parseColor("#4CAF50")
        "morado", "purple" -> AndroidColor.parseColor("#9C27B0")
        "rosado", "rosa", "pink" -> AndroidColor.parseColor("#E91E63")
        else -> AndroidColor.parseColor("#FFEB3B")
    }
}

@Composable
fun TeamsResult(
    teamA: List<Player>,
    teamB: List<Player>,
    titleA: String,
    titleB: String,
    onRenameA: () -> Unit,
    onRenameB: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (teamA.isEmpty() && teamB.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Group, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.empty_state),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    val displayA = remember(teamA) { teamA.shuffled() }
    val displayB = remember(teamB) { teamB.shuffled() }

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TeamCard(
            title = titleA,
            players = displayA,
            onRename = onRenameA,
            modifier = Modifier.weight(1f)
        )
        TeamCard(
            title = titleB,
            players = displayB,
            onRename = onRenameB,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TeamCard(
    title: String,
    players: List<Player>,
    onRename: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), color = Color.White)
                IconButton(onClick = onRename) {
                    Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                }
            }
            Spacer(Modifier.height(8.dp))
            players.forEach { p ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (p.isGoalkeeper) {
                        Icon(Icons.Filled.BackHand, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                        Spacer(Modifier.width(6.dp))
                    } else {
                        Icon(painterResource(id = R.drawable.ic_tshirt), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                        Spacer(Modifier.width(6.dp))
                    }
                    Text(stringResource(R.string.bullet_player_name, p.name))
                }
            }
        }
    }
}
fun generateBalancedTeams(players: List<Player>): Pair<List<Player>, List<Player>> {
    if (players.isEmpty()) return emptyList<Player>() to emptyList()
    val teamA = mutableListOf<Player>()
    val teamB = mutableListOf<Player>()
    var aA = 0.0; var dA = 0.0; var pA = 0.0; var rA = 0.0
    var aB = 0.0; var dB = 0.0; var pB = 0.0; var rB = 0.0

    fun objectiveAfter(addToA: Boolean, p: Player): Double {
        val naA = if (addToA) aA + p.attack else aA
        val ndA = if (addToA) dA + p.defense else dA
        val npA = if (addToA) pA + p.physical else pA
        val nrA = if (addToA) rA + p.rating else rA
        val naB = if (addToA) aB else aB + p.attack
        val ndB = if (addToA) dB else dB + p.defense
        val npB = if (addToA) pB else pB + p.physical
        val nrB = if (addToA) rB else rB + p.rating
        val da = naA - naB
        val dd = ndA - ndB
        val dp = npA - npB
        val dr = nrA - nrB
        return da * da + dd * dd + dp * dp + dr * dr
    }

    val goalkeepers = players.filter { it.isGoalkeeper }
    val assignedGK = if (goalkeepers.size >= 2) goalkeepers.sortedByDescending { it.rating }.take(2) else emptyList()
    if (assignedGK.size == 2) {
        val gkA = assignedGK[0]
        val gkB = assignedGK[1]
        teamA += gkA
        teamB += gkB
        aA += gkA.attack; dA += gkA.defense; pA += gkA.physical; rA += gkA.rating
        aB += gkB.attack; dB += gkB.defense; pB += gkB.physical; rB += gkB.rating
    }

    val remaining = players.filter { !assignedGK.contains(it) }.sortedByDescending { it.rating }
    for (p in remaining) {
        val toA = objectiveAfter(true, p)
        val toB = objectiveAfter(false, p)
        if (toA < toB) {
            teamA += p
            aA += p.attack; dA += p.defense; pA += p.physical; rA += p.rating
        } else {
            teamB += p
            aB += p.attack; dB += p.defense; pB += p.physical; rB += p.rating
        }
    }
    while (kotlin.math.abs(teamA.size - teamB.size) > 1) {
        if (teamA.size > teamB.size) {
            val moved = teamA.removeAt(teamA.lastIndex)
            teamB += moved
            aA -= moved.attack; dA -= moved.defense; pA -= moved.physical; rA -= moved.rating
            aB += moved.attack; dB += moved.defense; pB += moved.physical; rB += moved.rating
        } else {
            val moved = teamB.removeAt(teamB.lastIndex)
            teamA += moved
            aB -= moved.attack; dB -= moved.defense; pB -= moved.physical; rB -= moved.rating
            aA += moved.attack; dA += moved.defense; pA += moved.physical; rA += moved.rating
        }
    }
    return teamA to teamB
}

@Composable
private fun CustomizeTeamsDialog(
    players: List<Player>,
    initialA: List<Player>,
    initialB: List<Player>,
    onApply: (List<Player>, List<Player>) -> Unit,
    onOpenSavedTeams: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val namesA = initialA.map { it.name }.toSet()
    val namesB = initialB.map { it.name }.toSet()
    var assignments by remember(players, namesA, namesB) {
        mutableStateOf(
            players.associate { p ->
                val v = when {
                    namesA.contains(p.name) -> "A"
                    namesB.contains(p.name) -> "B"
                    else -> "N"
                }
                p.name to v
            }.toMutableMap()
        )
    }

    val dialogTextColor = MaterialTheme.colorScheme.onSurface
    val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    var showCreateTeamPicker by remember { mutableStateOf(false) }
    var showCreateTeamDialog by remember { mutableStateOf(false) }
    var createTeamInitialPlayers by remember { mutableStateOf<List<Player>>(emptyList()) }

    BackHandler(onBack = onDismiss)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = dialogTextColor
                    )
                }
                Text(
                    text = stringResource(R.string.create_teams_title),
                    color = dialogTextColor,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { showCreateTeamPicker = true },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(stringResource(R.string.create_team), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.assign_each_player),
                    fontWeight = FontWeight.SemiBold,
                    color = dialogTextColor,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onOpenSavedTeams,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(stringResource(R.string.teams), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(players) { p ->
                    val current = assignments[p.name] ?: "N"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            if (p.isGoalkeeper) {
                                Icon(Icons.Filled.BackHand, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(Modifier.width(6.dp))
                            }
                            Text(p.name, color = dialogTextColor)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = current == "A",
                                    onClick = {
                                        val m = assignments.toMutableMap()
                                        m[p.name] = "A"
                                        assignments = m
                                    }
                                )
                                Text(stringResource(R.string.assignment_a), color = dialogTextColor)
                            }
                            Spacer(Modifier.width(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = current == "B",
                                    onClick = {
                                        val m = assignments.toMutableMap()
                                        m[p.name] = "B"
                                        assignments = m
                                    }
                                )
                                Text(stringResource(R.string.assignment_b), color = dialogTextColor)
                            }
                            Spacer(Modifier.width(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = current == "N",
                                    onClick = {
                                        val m = assignments.toMutableMap()
                                        m[p.name] = "N"
                                        assignments = m
                                    }
                                )
                                Text(stringResource(R.string.assignment_none), color = dialogTextColor)
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onDismiss) { Text(stringResource(R.string.close)) }
                    Button(onClick = {
                        val a = players.filter { assignments[it.name] == "A" }
                        val b = players.filter { assignments[it.name] == "B" }
                        onApply(a, b)
                        onDismiss()
                    }) { Text(stringResource(R.string.save_changes)) }
                }
            }

            if (showCreateTeamPicker) {
                val a = players.filter { assignments[it.name] == "A" }
                val b = players.filter { assignments[it.name] == "B" }
                AlertDialog(
                    onDismissRequest = { showCreateTeamPicker = false },
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = dialogTextColor,
                    textContentColor = dialogTextColor,
                    title = { Text(stringResource(R.string.create_team), color = dialogTextColor) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(stringResource(R.string.select_two_teams_for_match), color = dialogSecondaryTextColor)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    enabled = a.isNotEmpty(),
                                    onClick = {
                                        createTeamInitialPlayers = a
                                        showCreateTeamPicker = false
                                        showCreateTeamDialog = true
                                    }
                                ) {
                                    Text(stringResource(R.string.team_a), color = MaterialTheme.colorScheme.onPrimary)
                                }
                                Button(
                                    enabled = b.isNotEmpty(),
                                    onClick = {
                                        createTeamInitialPlayers = b
                                        showCreateTeamPicker = false
                                        showCreateTeamDialog = true
                                    }
                                ) {
                                    Text(stringResource(R.string.team_b), color = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(onClick = { showCreateTeamPicker = false }) {
                            Text(stringResource(R.string.cancel), color = dialogTextColor)
                        }
                    }
                )
            }

            if (showCreateTeamDialog) {
                EditSavedTeamDialog(
                    allPlayers = players,
                    initialName = "",
                    initialPlayers = createTeamInitialPlayers,
                    onSave = { name, selectedPlayers ->
                        addTeam(context, name, selectedPlayers)
                        showCreateTeamDialog = false
                    },
                    onDismiss = { showCreateTeamDialog = false }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun EditPlayersDialog(
    players: List<Player>,
    onUpdatePlayer: (String, Double, Double, Double) -> Unit,
    onAddPlayer: (String, Double, Double, Double, Boolean) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    onRenamePlayer: (String, String) -> Unit,
    onDeletePlayer: (String) -> Unit,
    onToggleGoalkeeper: (String, Boolean) -> Unit
) {
    val dialogTextColor = MaterialTheme.colorScheme.onSurface
    val dialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    val coroutineScope = rememberCoroutineScope()

    val pendingEdits = remember { mutableStateOf(mutableMapOf<String, Triple<String, String, String>>()) }
    val pendingGK = remember { mutableStateOf(mutableMapOf<String, Boolean>()) }

    var newName by remember { mutableStateOf("") }
    var newAttackText by remember { mutableStateOf("") }
    var newDefenseText by remember { mutableStateOf("") }
    var newSkillText by remember { mutableStateOf("") }
    var newIsGoalkeeper by remember { mutableStateOf(false) }
    var addPlayerExpanded by remember { mutableStateOf(false) }

    var showEditPlayerDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var editTarget by remember { mutableStateOf<Player?>(null) }
    var editName by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val maxDialogHeight = configuration.screenHeightDp.dp * 0.8f
    val listScrollState = rememberScrollState()
    var lastPlayersSize by remember { mutableStateOf(players.size) }

    LaunchedEffect(players.size) {
        // Cuando cambia la cantidad de jugadores (por ejemplo, se agrega uno nuevo),
        // desplazar el scroll al final para que el nuevo jugador sea visible de inmediato.
        // Si la lista es corta, maxValue será 0 y no habrá movimiento perceptible.
        if (players.size > lastPlayersSize) {
            listScrollState.animateScrollTo(listScrollState.maxValue)
        }
        lastPlayersSize = players.size
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .statusBarsPadding()
                .padding(8.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .imePadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.edit_player_title),
                        color = GrassGreen,
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.close), tint = dialogTextColor)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .verticalScroll(listScrollState)
                ) {
                    players.forEach { p ->
                        var attackText by remember(p.name, p.attack) { mutableStateOf(p.attack.toString()) }
                        var defenseText by remember(p.name, p.defense) { mutableStateOf(p.defense.toString()) }
                        var skillText by remember(p.name, p.physical) { mutableStateOf(p.physical.toString()) }
                        val isGK = pendingGK.value[p.name] ?: p.isGoalkeeper

                        val bringIntoViewRequester = remember(p.name) { BringIntoViewRequester() }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .bringIntoViewRequester(bringIntoViewRequester)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(p.name, modifier = Modifier.weight(1f), color = dialogTextColor)
                                IconButton(onClick = {
                                    editTarget = p
                                    editName = p.name
                                    showEditPlayerDialog = true
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = isGK,
                                        onCheckedChange = { checked ->
                                            val map = pendingGK.value.toMutableMap()
                                            map[p.name] = checked
                                            pendingGK.value = map
                                        }
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(stringResource(R.string.goalkeeper), color = GrassGreen)
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = attackText,
                                    onValueChange = {
                                        val filtered = it.filter { ch -> ch.isDigit() || ch == '.' }
                                        attackText = filtered
                                        val curr = pendingEdits.value[p.name]?.copy(first = filtered) ?: Triple(filtered, defenseText, skillText)
                                        pendingEdits.value[p.name] = curr
                                    },
                                    label = { Text(stringResource(R.string.attack), color = GrassGreen, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .onFocusChanged { state ->
                                            if (state.isFocused) {
                                                coroutineScope.launch {
                                                    delay(80)
                                                    bringIntoViewRequester.bringIntoView()
                                                }
                                            }
                                        },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = dialogTextColor,
                                        unfocusedTextColor = dialogTextColor,
                                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        cursorColor = dialogTextColor
                                    )
                                )
                                OutlinedTextField(
                                    value = defenseText,
                                    onValueChange = {
                                        val filtered = it.filter { ch -> ch.isDigit() || ch == '.' }
                                        defenseText = filtered
                                        val curr = pendingEdits.value[p.name]?.copy(second = filtered) ?: Triple(attackText, filtered, skillText)
                                        pendingEdits.value[p.name] = curr
                                    },
                                    label = { Text(stringResource(R.string.defense), color = GrassGreen, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .onFocusChanged { state ->
                                            if (state.isFocused) {
                                                coroutineScope.launch {
                                                    delay(80)
                                                    bringIntoViewRequester.bringIntoView()
                                                }
                                            }
                                        },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = dialogTextColor,
                                        unfocusedTextColor = dialogTextColor,
                                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        cursorColor = dialogTextColor
                                    )
                                )
                                OutlinedTextField(
                                    value = skillText,
                                    onValueChange = {
                                        val filtered = it.filter { ch -> ch.isDigit() || ch == '.' }
                                        skillText = filtered
                                        val curr = pendingEdits.value[p.name]?.copy(third = filtered) ?: Triple(attackText, defenseText, filtered)
                                        pendingEdits.value[p.name] = curr
                                    },
                                    label = { Text(stringResource(R.string.physical), color = GrassGreen, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .onFocusChanged { state ->
                                            if (state.isFocused) {
                                                coroutineScope.launch {
                                                    delay(80)
                                                    bringIntoViewRequester.bringIntoView()
                                                }
                                            }
                                        },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = dialogTextColor,
                                        unfocusedTextColor = dialogTextColor,
                                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        cursorColor = dialogTextColor
                                    )
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }

                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { addPlayerExpanded = !addPlayerExpanded }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.add_new_player), fontWeight = FontWeight.SemiBold, color = GrassGreen)
                    Icon(
                        imageVector = if (addPlayerExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = dialogTextColor
                    )
                }

                if (addPlayerExpanded) {
                    Spacer(Modifier.height(8.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text(stringResource(R.string.name), color = GrassGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = dialogTextColor,
                                unfocusedTextColor = dialogTextColor,
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                cursorColor = dialogTextColor
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = newAttackText,
                                onValueChange = { newAttackText = it.filter { ch -> ch.isDigit() || ch == '.' } },
                                label = { Text(stringResource(R.string.attack), color = GrassGreen, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = dialogTextColor,
                                    unfocusedTextColor = dialogTextColor,
                                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    cursorColor = dialogTextColor
                                )
                            )
                            OutlinedTextField(
                                value = newDefenseText,
                                onValueChange = { newDefenseText = it.filter { ch -> ch.isDigit() || ch == '.' } },
                                label = { Text(stringResource(R.string.defense), color = GrassGreen, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = dialogTextColor,
                                    unfocusedTextColor = dialogTextColor,
                                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    cursorColor = dialogTextColor
                                )
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = newSkillText,
                                onValueChange = { newSkillText = it.filter { ch -> ch.isDigit() || ch == '.' } },
                                label = { Text(stringResource(R.string.physical), color = GrassGreen, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = dialogTextColor,
                                    unfocusedTextColor = dialogTextColor,
                                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    cursorColor = dialogTextColor
                                )
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = newIsGoalkeeper, onCheckedChange = { newIsGoalkeeper = it })
                                Text(stringResource(R.string.goalkeeper), color = GrassGreen)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                enabled = newName.trim().isNotEmpty() && newAttackText.isNotBlank() && newDefenseText.isNotBlank() && newSkillText.isNotBlank(),
                                onClick = {
                                    val name = newName.trim()
                                    val a = newAttackText.toDoubleOrNull()
                                    val d = newDefenseText.toDoubleOrNull()
                                    val h = newSkillText.toDoubleOrNull()
                                    if (name.isNotEmpty() && a != null && d != null && h != null &&
                                        a in 1.0..10.0 && d in 1.0..10.0 && h in 1.0..10.0
                                    ) {
                                        onAddPlayer(name, a, d, h, newIsGoalkeeper)
                                        newName = ""
                                        newAttackText = ""
                                        newDefenseText = ""
                                        newSkillText = ""
                                        newIsGoalkeeper = false
                                    }
                                }
                            ) { Text(stringResource(R.string.add)) }
                        }
                    }
                }

                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = onDismiss) { Text(stringResource(R.string.close)) }
                            Button(onClick = {
                                // aplicar ediciones pendientes
                                pendingEdits.value.forEach { (name, triple) ->
                                    val a = triple.first.toDoubleOrNull() ?: 0.0
                                    val d = triple.second.toDoubleOrNull() ?: 0.0
                                    val h = triple.third.toDoubleOrNull() ?: 0.0
                                    onUpdatePlayer(name, a, d, h)
                                }
                                pendingGK.value.forEach { (name, gk) ->
                                    onToggleGoalkeeper(name, gk)
                                }
                                onSave()
                                onDismiss()
                            }) { Text(stringResource(R.string.save_changes)) }
                        }
                    }
                }
            }
        }

    if (showEditPlayerDialog && editTarget != null) {
        Dialog(onDismissRequest = { showEditPlayerDialog = false }) {
            val nestedDialogTextColor = MaterialTheme.colorScheme.onSurface
            val nestedDialogSecondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .statusBarsPadding()
                    .padding(8.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .imePadding()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.edit_player_title),
                            color = nestedDialogTextColor,
                            style = MaterialTheme.typography.titleLarge
                        )
                        IconButton(onClick = { showEditPlayerDialog = false }) {
                            Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.close), tint = nestedDialogTextColor)
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text(stringResource(R.string.name), color = nestedDialogSecondaryTextColor) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = nestedDialogTextColor,
                            unfocusedTextColor = nestedDialogTextColor,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = nestedDialogTextColor,
                            focusedLabelColor = nestedDialogSecondaryTextColor,
                            unfocusedLabelColor = nestedDialogSecondaryTextColor
                        )
                    )

                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                val old = editTarget!!.name
                                val new = editName.trim()
                                if (new.isNotEmpty()) {
                                    onRenamePlayer(old, new)
                                    showEditPlayerDialog = false
                                }
                            }) { Text(stringResource(R.string.save)) }
                            Button(
                                onClick = {
                                    showEditPlayerDialog = false
                                    showDeleteConfirm = true
                                },
                                baseColor = GrassGreen,
                                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                            ) { Text(stringResource(R.string.delete)) }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteConfirm && editTarget != null) {
        val dialogContainerColor = MaterialTheme.colorScheme.surface
        val dialogTextColor = MaterialTheme.colorScheme.onSurface
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            containerColor = dialogContainerColor,
            titleContentColor = dialogTextColor,
            textContentColor = dialogTextColor,
            title = { Text(stringResource(R.string.delete_player_title), color = dialogTextColor) },
            text = { Text(stringResource(R.string.delete_confirm, editTarget!!.name)) },
            confirmButton = {
                Button(
                    onClick = {
                        onDeletePlayer(editTarget!!.name)
                        showDeleteConfirm = false
                    },
                    baseColor = GrassGreen,
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                ) { Text(stringResource(R.string.delete)) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text(stringResource(R.string.cancel), color = dialogTextColor) }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    EquiposTheme {
        AppScaffold()
    }
}

