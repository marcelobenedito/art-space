package com.github.marcelobenedito.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.marcelobenedito.artspace.model.Artwork
import com.github.marcelobenedito.artspace.ui.theme.ArtSpaceTheme
import com.github.marcelobenedito.artspace.util.Data

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    val artworkList = Data.images
    var currentArtwork by remember { mutableStateOf(artworkList[0]) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        ArtworkWall(
            image = currentArtwork.image,
            modifier = Modifier.weight(8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ArtworkDescriptor(
            title = currentArtwork.title,
            artist = currentArtwork.artist,
            year = currentArtwork.year,
            modifier = Modifier.weight(2f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        DisplayController(
            onPreviousArtwork = {
                val currentIndex = artworkList.indexOf(currentArtwork)
                val previousIndex = currentIndex - 1
                currentArtwork = if (previousIndex >= 0) {
                    artworkList[previousIndex]
                } else {
                    artworkList[artworkList.size - 1]
                }
            },
            onNextArtwork = {
                val currentIndex = artworkList.indexOf(currentArtwork)
                val nextIndex = currentIndex + 1
                currentArtwork = if (nextIndex < artworkList.size) {
                    artworkList[nextIndex]
                } else {
                    artworkList[0]
                }
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .shadow(elevation = 13.dp)
                .background(Color.White)
                .padding(30.dp)
                .width(300.dp)
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ArtworkDescriptor(
    title: String,
    artist: String,
    year: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentHeight()
                .background(Color(0xFFecebf4))
                .padding(16.dp)
                .width(330.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Light,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = buildAnnotatedString {
                    val spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                    withStyle(style = spanStyle) {
                        append(artist)
                    }

                    append(" ($year)")
                },
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun DisplayController(
    onPreviousArtwork: () -> Unit,
    onNextArtwork: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onPreviousArtwork,
            modifier = Modifier
                .width(180.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = onNextArtwork,
            modifier = Modifier
                .width(180.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Next")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}