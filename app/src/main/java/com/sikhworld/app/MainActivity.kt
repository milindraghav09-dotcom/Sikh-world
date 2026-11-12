package com.sikhworld.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF090909)
private val Panel = Color(0xFF171512)
private val Gold = Color(0xFFE0B653)
private val Cream = Color(0xFFFFF1D0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SikhWorldApp(
                openKirtan = { openUrl("https://new.sgpc.net/live-kirtan-sri-harmandir-sahib/") },
                shareStatus = { shareStatus() },
                openWhatsApp = { openUrl("https://wa.me/") }
            )
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun shareStatus() {
        val send = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Waheguru Ji Ka Khalsa, Waheguru Ji Ki Fateh 🙏\nShared from Sikh World")
        }
        startActivity(Intent.createChooser(send, "Share Sikh status"))
    }
}

private data class Tile(val title: String, val subtitle: String, val symbol: String, val kind: String)

private val wallpaperTiles = listOf(
    Tile("Darbar Sahib", "Golden Temple collection", "ੴ", "wallpaper"),
    Tile("Khanda Sahib", "Sikh symbols & designs", "☬", "wallpaper"),
    Tile("Waheguru", "Naam-inspired artwork", "ਵਾਹਿਗੁਰੂ", "wallpaper"),
    Tile("Gurbani", "Peaceful devotional designs", "ਸਤਿਨਾਮ", "wallpaper")
)
private val threeDTiles = listOf(
    Tile("3D Khanda", "Gold metallic artwork", "☬", "3d"),
    Tile("3D Waheguru", "Cinematic devotional art", "ੴ", "3d"),
    Tile("Golden Darbar", "3D temple-inspired art", "ੴ", "3d"),
    Tile("3D Gurbani", "Premium depth effects", "ਸਤਿਨਾਮ", "3d")
)
private val statusTiles = listOf(
    Tile("Waheguru Status", "Vertical 3D status video", "▶", "status"),
    Tile("Gurbani Status", "Devotional status video", "▶", "status"),
    Tile("Khalsa Status", "Sikh pride status video", "▶", "status"),
    Tile("Darbar Sahib", "Golden Temple status video", "▶", "status")
)

@Composable
private fun SikhWorldApp(openKirtan: () -> Unit, shareStatus: () -> Unit, openWhatsApp: () -> Unit) {
    var tab by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf<Tile?>(null) }
    val tabs = listOf("Home", "Wallpapers", "3D Art", "Status", "Kirtan")
    MaterialTheme(colorScheme = darkColorScheme(
        background = Ink, surface = Panel, primary = Gold, onPrimary = Ink,
        onBackground = Cream, onSurface = Cream, secondary = Gold
    )) {
        Scaffold(
            containerColor = Ink,
            bottomBar = {
                NavigationBar(containerColor = Panel, contentColor = Gold) {
                    val icons = listOf(Icons.Default.Home, Icons.Default.Wallpaper, Icons.Default.AutoAwesome, Icons.Default.PlayCircle, Icons.Default.MusicNote)
                    tabs.forEachIndexed { index, label ->
                        NavigationBarItem(
                            selected = tab == index,
                            onClick = { tab = index; selected = null },
                            icon = { Icon(icons[index], contentDescription = label) },
                            label = { Text(label, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Gold, selectedTextColor = Gold,
                                indicatorColor = Color(0xFF342914), unselectedIconColor = Cream.copy(alpha=.65f),
                                unselectedTextColor = Cream.copy(alpha=.65f)
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding).background(Ink)) {
                Header()
                when (tab) {
                    0 -> HomeScreen(
                        onWallpapers = { tab = 1 }, on3D = { tab = 2 },
                        onStatus = { tab = 3 }, onKirtan = { tab = 4 },
                        openKirtan = openKirtan
                    )
                    1 -> CollectionScreen("Sikh Wallpapers", "HD devotional wallpaper collection", wallpaperTiles) { selected = it }
                    2 -> CollectionScreen("3D Sikh Wallpapers", "Premium 3D-inspired Sikh artwork", threeDTiles) { selected = it }
                    3 -> StatusScreen(shareStatus, openWhatsApp)
                    4 -> KirtanScreen(openKirtan)
                }
            }
            selected?.let { tile ->
                AlertDialog(
                    onDismissRequest = { selected = null },
                    containerColor = Panel,
                    title = { Text(tile.title, color = Gold) },
                    text = { Text("This is a starter preview tile. Add your own licensed image/video file to the app's media library to make this item downloadable.", color = Cream) },
                    confirmButton = { TextButton(onClick = { selected = null }) { Text("OK", color = Gold) } }
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(46.dp).background(
                    Brush.linearGradient(listOf(Color(0xFF6E4B12), Gold, Color(0xFF6E4B12))),
                    RoundedCornerShape(14.dp)
                ),
                contentAlignment = Alignment.Center
            ) { Text("☬", color = Ink, fontSize = 28.sp, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.width(12.dp))
            Column {
                Text("SIKH WORLD", color = Gold, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                Text("Waheguru Ji Ka Khalsa • Waheguru Ji Ki Fateh", color = Cream.copy(alpha=.7f), fontSize = 10.sp)
            }
        }
        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = Gold.copy(alpha=.25f))
    }
}

@Composable
private fun HomeScreen(onWallpapers: () -> Unit, on3D: () -> Unit, onStatus: () -> Unit, onKirtan: () -> Unit, openKirtan: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        Box(
            Modifier.fillMaxWidth().height(174.dp)
                .background(Brush.linearGradient(listOf(Color(0xFF49320F), Color(0xFF17130C), Color(0xFF090909))), RoundedCornerShape(22.dp))
                .padding(20.dp)
        ) {
            Column(Modifier.align(Alignment.CenterStart)) {
                Text("ੴ", color = Gold, fontSize = 42.sp)
                Text("Stay Connected
With Gurbani", color = Cream, fontSize = 23.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
                Text("Art • Status • Kirtan", color = Gold, fontSize = 12.sp)
            }
            Text("☬", Modifier.align(Alignment.CenterEnd), color = Gold.copy(alpha=.28f), fontSize = 104.sp)
        }
        Spacer(Modifier.height(18.dp))
        Text("EXPLORE", color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(Modifier.height(10.dp))
        val cards = listOf(
            Triple("Sikh Wallpapers", "HD devotional collection", onWallpapers),
            Triple("3D Sikh Wallpapers", "Premium 3D-inspired art", on3D),
            Triple("3D WhatsApp Status", "Share devotional status", onStatus),
            Triple("Live Kirtan", "Listen from Harmandir Sahib", onKirtan)
        )
        cards.forEachIndexed { i, item ->
            Row(
                Modifier.fillMaxWidth().padding(bottom = 10.dp)
                    .background(Panel, RoundedCornerShape(16.dp))
                    .clickable { item.third() }.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(listOf("ੴ", "☬", "▶", "♫")[i], color = Gold, fontSize = 30.sp, modifier = Modifier.width(52.dp), textAlign = TextAlign.Center)
                Column(Modifier.weight(1f)) {
                    Text(item.first, color = Cream, fontWeight = FontWeight.SemiBold)
                    Text(item.second, color = Cream.copy(alpha=.6f), fontSize = 11.sp)
                }
                Icon(Icons.Default.ChevronRight, null, tint = Gold)
            }
        }
        Spacer(Modifier.height(4.dp))
        Button(onClick = openKirtan, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink)) {
            Icon(Icons.Default.PlayArrow, null)
            Spacer(Modifier.width(8.dp))
            Text("LISTEN TO LIVE KIRTAN", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun CollectionScreen(title: String, subtitle: String, tiles: List<Tile>, onSelect: (Tile) -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        Text(title, color = Cream, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Gold.copy(alpha=.8f), fontSize = 12.sp)
        Spacer(Modifier.height(14.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(tiles) { tile ->
                Column(Modifier.fillMaxWidth().clickable { onSelect(tile) }) {
                    Box(
                        Modifier.fillMaxWidth().height(164.dp)
                            .background(Brush.linearGradient(listOf(Color(0xFF5C421A), Color(0xFF201A10), Color(0xFF111111))), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tile.symbol, color = Gold, fontSize = if (tile.symbol.length > 3) 22.sp else 58.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text("PREVIEW", Modifier.align(Alignment.BottomEnd).padding(8.dp), color = Cream.copy(alpha=.55f), fontSize = 8.sp, letterSpacing = 1.sp)
                    }
                    Spacer(Modifier.height(7.dp))
                    Text(tile.title, color = Cream, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text(tile.subtitle, color = Cream.copy(alpha=.55f), fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
private fun StatusScreen(shareStatus: () -> Unit, openWhatsApp: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("3D WhatsApp Status", color = Cream, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Vertical devotional status collection", color = Gold, fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        statusTiles.forEach { tile ->
            Row(Modifier.fillMaxWidth().padding(bottom = 10.dp).background(Panel, RoundedCornerShape(16.dp)).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(52.dp).background(Color(0xFF3A2A12), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                    Text(tile.symbol, color = Gold, fontSize = 25.sp)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(tile.title, color = Cream, fontWeight = FontWeight.SemiBold)
                    Text("Video asset to be added", color = Cream.copy(alpha=.55f), fontSize = 10.sp)
                }
                Icon(Icons.Default.Lock, null, tint = Gold.copy(alpha=.7f), modifier = Modifier.size(18.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = shareStatus, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink)) {
            Icon(Icons.Default.Share, null); Spacer(Modifier.width(8.dp)); Text("SHARE WAHEGURU MESSAGE")
        }
        OutlinedButton(onClick = openWhatsApp, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = Gold)) {
            Text("OPEN WHATSAPP")
        }
    }
}

@Composable
private fun KirtanScreen(openKirtan: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(18.dp))
        Box(Modifier.size(190.dp).background(Brush.radialGradient(listOf(Color(0xFF70501D), Color(0xFF241A0B), Ink)), RoundedCornerShape(100.dp)), contentAlignment = Alignment.Center) {
            Text("ੴ", color = Gold, fontSize = 86.sp)
        }
        Spacer(Modifier.height(24.dp))
        Text("LIVE GURBANI KIRTAN", color = Gold, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        Spacer(Modifier.height(8.dp))
        Text("Sachkhand Sri Harmandir Sahib Sri Amritsar", color = Cream, fontSize = 15.sp, textAlign = TextAlign.Center, lineHeight = 22.sp)

        Spacer(Modifier.height(12.dp))
        Text("The live broadcast opens on the official SGPC website. Playback and availability are controlled by SGPC.", color = Cream.copy(alpha=.65f), fontSize = 12.sp, textAlign = TextAlign.Center)
        Spacer(Modifier.height(22.dp))
        Button(onClick = openKirtan, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink)) {
            Icon(Icons.Default.PlayArrow, null); Spacer(Modifier.width(8.dp)); Text("PLAY OFFICIAL LIVE KIRTAN")
        }
        Spacer(Modifier.height(12.dp))
        Text("Source: Shiromani Gurdwara Parbandhak Committee (SGPC)", color = Cream.copy(alpha=.45f), fontSize = 10.sp, textAlign = TextAlign.Center)
    }
}
