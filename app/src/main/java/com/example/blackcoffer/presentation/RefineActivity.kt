@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)

package com.example.blackcoffer.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcoffer.domain.explore.Purpose
import com.example.blackcoffer.ui.theme.BlackcofferTheme
import com.example.blackcoffer.ui.theme.primary
import com.example.blackcoffer.ui.theme.secondary
import com.example.blackcoffer.ui.theme.ternary
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class RefineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackcofferTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val scope = rememberCoroutineScope()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Refine", fontWeight = FontWeight.SemiBold, color = Color.White)
                                },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch {
                                        finish()
                                    } }) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowLeft,
                                            contentDescription = "Back",
                                            tint = Color.White
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = secondary),
                                scrollBehavior = scrollBehavior,
                            )
                        }
                    ) { paddingValues ->
                        var selectedAvailability by remember { mutableStateOf("Available | Hay Let Us Connect") }
                        var userStatus by remember { mutableStateOf("Hi community! | I am open to new connection \"😊\"") }
                        var sliderPosition by remember { mutableStateOf(0f) }

                        Column(modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                           ) {

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "Select Your Availability", modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            EditableDropdownField(
                                items = listOf("Available | Hay Let Us Connect", "Away | Stay Discrete And Watch", "Busy | Do Not Disturb | Will Catch Up Letter", "SOS | Emergency! Need Assistance! HELP"),
                                selectedAvailability = selectedAvailability,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                onItemSelected = { item->
                                    selectedAvailability = item
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Add Your Status", modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = userStatus,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                textStyle = TextStyle(fontSize = 14.sp),
                                onValueChange = { text->
                                    userStatus = text
                                })
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Select Hyper Local Distance", modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Slider(
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                                value = sliderPosition,
                                onValueChange = { sliderPosition = it },
                                valueRange = 1f..100f,
                                onValueChangeFinished = {
                                    //Update final value here
                                },
                                steps = 100,
                                colors = SliderDefaults.colors(
                                    thumbColor = secondary,
                                    activeTrackColor = ternary
                                ),
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Select Purpose", modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))


                            var list by remember {
                                mutableStateOf(
                                    listOf(
                                        Purpose.Coffee.name,
                                        Purpose.Business.name,
                                        Purpose.Hobbies.name,
                                        Purpose.Friendship.name,
                                        Purpose.Movies.name,
                                        Purpose.Dinning.name,
                                        Purpose.Dating.name,
                                        Purpose.Matrimony.name,
                                    )
                                )
                            }

                            val tempList: Set<Int> = emptySet()

                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                                ChipEachRow(list = list, tempList = tempList)
                            }

                            Button(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 100.dp, end = 100.dp),
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    containerColor = primary)) {
                                Text(text = "Save & Explore")
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ChipEachRow(
        list: List<String>,
        tempList: Set<Int>
    ) {

        var multipleChecked by rememberSaveable { mutableStateOf(tempList) }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp
        ) {
            list.forEachIndexed { index, s ->
                FilterChip(
                    selected = multipleChecked.contains(index), onClick = {
                        multipleChecked = if (multipleChecked.contains(index))
                            multipleChecked.minus(index)
                        else
                            multipleChecked.plus(index)
                    },
                    label = {
                        Text(text = s)
                    },
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (!multipleChecked.contains(index)) primary else Color.Transparent,
                        borderWidth = if (multipleChecked.contains(index)) 0.dp else 1.dp
                    ),
                    colors = FilterChipDefaults.filterChipColors(
                        disabledContainerColor = Color.Transparent,
                        disabledLabelColor = Color.Black,
                        selectedContainerColor = secondary,
                        selectedLabelColor = Color.White,
                    ),
                    shape = RoundedCornerShape(20.dp),
                )
            }
        }


    }



    @Composable
    fun EditableDropdownField(
        items: List<String>,
        modifier: Modifier,
        selectedAvailability: String,
        onItemSelected: (String) -> Unit
    ) {
        var isDropdownExpanded by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current

        Column {
            OutlinedTextField(
                value = selectedAvailability,
                onValueChange = {text->
                    onItemSelected(text)
                    keyboardController?.hide()
                },
                textStyle = TextStyle(fontSize = 14.sp),
                readOnly = true,
                modifier = modifier.onFocusChanged { focusState ->
                    if(focusState.isFocused)
                        isDropdownExpanded = true
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = "arrow down")
                }
            )

            DropdownMenu(modifier = modifier,
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {onItemSelected(item)
                            keyboardController?.hide() // Hide the keyboard when an item is selected
                            isDropdownExpanded = false},
                        text = {Text(text = item)}
                    )
                }
            }
        }
    }
}
