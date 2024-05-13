package com.example.demoapp.ui.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.demoapp.R
import com.example.demoapp.util.Language


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppbar(
    modifier: Modifier = Modifier,
    isScrollUP: Boolean,
    title: String,
    onLanguageChange: (Language) -> Unit
) {
    val topBarPosition by animateFloatAsState(
        targetValue = if (isScrollUP) -380f else 0f,
        label = "position",
        animationSpec = tween()
    )
    var isDropdownOpen by remember {
        mutableStateOf(false)
    }
    var selectedLanguage by rememberSaveable {
        mutableStateOf(Language.ENGLISH.name)
    }
    var searchText by rememberSaveable { mutableStateOf("") }
    Surface(modifier = modifier.graphicsLayer {
        translationY = topBarPosition
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {

            CenterAlignedTopAppBar(
                title = {
                    Text(text = title)
                },
//                    scrollBehavior = scrollBehavior
            )
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                prefix = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                },
                placeholder = { Text("Search for services") }
            )
            Spacer(modifier = Modifier.height(30.dp))
            ImageSlider(
                images = listOf(
                    R.drawable.image_1, R.drawable.image_2
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = "This is a test app",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                ExposedDropdownMenuBox(
                    expanded = isDropdownOpen,
                    onExpandedChange = { isDropdownOpen = it })
                {
                    OutlinedTextField(
                        value = selectedLanguage,
                        onValueChange = {},
                        label = { Text(text = "Select Language") },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownOpen)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.8f),
                        shape = RoundedCornerShape(10.dp)

                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownOpen,
                        onDismissRequest = { isDropdownOpen = false })
                    {
                        Language.entries.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(text = language.name) },
                                onClick = {
                                    onLanguageChange(language)
                                    selectedLanguage = language.name
                                    isDropdownOpen = false
                                })

                        }
                    }


                }


            }

        }
    }
}