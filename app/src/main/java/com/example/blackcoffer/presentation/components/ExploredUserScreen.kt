package com.example.blackcoffer.presentation.components

import android.graphics.ColorMatrixColorFilter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.blackcoffer.domain.explore.UserInformation
import com.example.blackcoffer.presentation.ExploreUiState
import com.example.blackcoffer.ui.theme.lightGray
import com.example.blackcoffer.utils.FakeDataSource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExploredUserScreen(
    state: ExploreUiState,
    onRefreshScreen: () -> Unit,
    onUserCardClick:(UserInformation) -> Unit
){
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabRowItems = listOf(
        TabRowItem("Personal", Icons.Filled.Person, screen = {
            PersonalScreen(state,
                onRefreshScreen = {
                    onRefreshScreen()
                }, onClick = { userInformation ->
                    onUserCardClick(userInformation)
                })
        }),
        TabRowItem("Business", Icons.Filled.Person, screen = { BusinessScreen() }),
        TabRowItem("Merchant", Icons.Filled.Person, screen = { MerchantScreen() })
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage,
            indicator = { tabPosition->
                TabRowDefaults.Indicator(
                    height = 3.dp,
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState,tabPosition),
                    color = Color.White
                )
            }, backgroundColor = com.example.blackcoffer.ui.theme.secondary
            ) {
            tabRowItems.forEachIndexed { index, tabRowItem -> 
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                     text = {
                        Text(text = tabRowItem.title,
                            color = (
                                    if(pagerState.currentPage == index){
                                        Color.White
                                    }else lightGray),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                    }
                )
            }
        }
        
        HorizontalPager(
            count = tabRowItems.size,
            state = pagerState
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}


data class TabRowItem(
    val title: String,
    val icon: ImageVector,
    val screen: @Composable ()-> Unit
)

//@Composable
//fun TabScreen(
//    title: String
//){
//    Box(modifier = Modifier.fillMaxSize()){
//        Text(text = title,
//            fontWeight = FontWeight.Medium)
//    }
//}
