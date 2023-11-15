package com.example.blackcoffer.presentation.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcoffer.domain.explore.UserInformation
import com.example.blackcoffer.presentation.ExploreUiState
import com.example.blackcoffer.ui.theme.primary
import com.example.blackcoffer.ui.theme.secondary
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(
    state: ExploreUiState,
    onRefreshScreen: () -> Unit,
    onClick: (UserInformation) -> Unit,
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val fabVisibility by derivedStateOf {
        listState.firstVisibleItemIndex == 0
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isRequesting -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading")
                }
            }

            state.isResponse -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(state.isRefreshing),
                    onRefresh = { onRefreshScreen() },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        CustomSearchBox(
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    null,
                                    tint = LocalContentColor.current.copy(alpha = 0.3f)
                                )
                            },
                            trailingIcon = null,
                            modifier = Modifier
                                .background(
                                    Color.White,
                                    RoundedCornerShape(percent = 50)
                                )
                                .padding(start = 20.dp, end = 20.dp)
                                .height(28.dp),
                            fontSize = 14.sp,
                            placeholderText = "Search"
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 5.dp),
                            state = listState,
                            contentPadding = PaddingValues(4.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.userInformationList) { userInfo ->
                                UserCard(userInfo = userInfo,Modifier.clickable { onClick(userInfo) })
                            }
                        }
                    }

                }
            }

            else -> {

            }

        }
        HideableFab(
            modifier = Modifier
                .padding(bottom = 30.dp, end = 20.dp)
                .align(Alignment.BottomEnd),
            isVisibleBecauseOfScrolling = fabVisibility
        )
    }
}

@Composable
private fun CustomSearchBox(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = 14.sp,
) {
    var text by rememberSaveable { mutableStateOf("") }
    BasicTextField(modifier = modifier
        .background(
            Color.Black,
            MaterialTheme.shapes.large,
        )
        .fillMaxWidth()
        .padding(1.dp),
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(primary),
        textStyle = LocalTextStyle.current.copy(
            color = Color.Black,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = Color.Gray,
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}

@Composable
private fun HideableFab(
    modifier: Modifier,
    isVisibleBecauseOfScrolling: Boolean,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = slideInVertically {
            with(density) { 60.dp.roundToPx() }
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 120
            }
        )
    ) {
        FloatingActionButton(
            onClick = {
                Toast.makeText(
                    context,
                    "Here I have to show various option for User",
                    Toast.LENGTH_SHORT
                ).show()
            },
            containerColor = secondary,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }
}