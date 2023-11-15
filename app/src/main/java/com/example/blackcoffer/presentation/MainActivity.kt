@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.blackcoffer.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Engineering
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.GroupWork
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blackcoffer.R
import com.example.blackcoffer.presentation.components.ExploredUserScreen
import com.example.blackcoffer.ui.theme.BlackcofferTheme
import com.example.blackcoffer.ui.theme.primary
import com.example.blackcoffer.ui.theme.secondary
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

enum class Screen {
    Explore, Network, Chat, Contacts, Groups
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            val navController = rememberNavController()
            BlackcofferTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val state by viewModel.state.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    ModalNavigationDrawer(
                        drawerContent = {
                            DrawerContent(scope,drawerState)
                        },
                        drawerState = drawerState
                    ) {

                        Scaffold(
                            bottomBar = {
                                BottomNavBar(navController)
                            },
                            // Animated FloatingActionButton is implemented on PersonalScreen.kt
//                            floatingActionButton = {
//                                FloatingActionButton(
//                                    onClick = { Toast.makeText(this, "Here I have to show various option for User",Toast.LENGTH_SHORT).show() },
//                                    containerColor = secondary,
//                                    elevation = FloatingActionButtonDefaults.elevation(4.dp),
//                                    shape = CircleShape
//                                ) {
//                                  Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
//                                } },
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Column {
                                            Text(text = "Howdy Biswaranjan Behera !!", fontSize = 15.sp, color = Color.White)
                                            Row {
                                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location icon", tint = Color.White, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(text = "Bhubaneswar, Odisha", fontSize = 11.sp, color = Color.White)
                                            }
                                        }
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = { scope.launch {
                                            drawerState.open()
                                        } }) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                contentDescription = "Open Drawer",
                                                tint = Color.White
                                            )
                                        }
                                    },
                                    actions = {
                                        IconButton(
                                            onClick = { startActivity(Intent(this@MainActivity,
                                            RefineActivity::class.java)) }) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    imageVector = Icons.Default.Interests,
                                                    contentDescription = "Refine",
                                                    Modifier.size(22.dp),
                                                    tint = Color.White
                                                )
                                                Text(text = "Refine", fontSize = 10.sp, color = Color.White)
                                            }
                                        }
                                    },
                                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = com.example.blackcoffer.ui.theme.primary),
                                    scrollBehavior = scrollBehavior,
                                )
                            }
                        ){paddingValues ->
                            NavHost(
                                navController = navController,
                                startDestination = Screen.Explore.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                composable(Screen.Explore.name) {
                                    ExploredUserScreen(
                                        state,
                                        onRefreshScreen = viewModel::refreshUserList,
                                        onUserCardClick = { userInfo ->
                                            //Here we have to implement code which run on a single card click for Example ViewModel any function call
                                            //But for simplify I will show only Toast here
                                            Toast.makeText(this@MainActivity,"Hello!! " + userInfo.name,Toast.LENGTH_SHORT).show()
                                        }
                                    ) }
                                composable(Screen.Network.name) { Text(text = "UNDER CONSTRUCTION NETWORK") }
                                composable(Screen.Chat.name) { Text(text = "UNDER CONSTRUCTION CHAT") }
                                composable(Screen.Contacts.name) { Text(text = "UNDER CONSTRUCTION CONTACTS") }
                                composable(Screen.Groups.name) { Text(text = "UNDER CONSTRUCTION GROUPS") }
                            }
                        }

                    }
                }

            }
        }
    }
    data class BottomNavigationItem(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val hasNews: Boolean,
        val badgeCount: Int? = null
    )

    @Composable
    fun BottomNavBar(navController: NavHostController) {
        val items = listOf(
            BottomNavigationItem(
                title = "Explore",
                selectedIcon = Icons.Filled.RemoveRedEye,
                unselectedIcon = Icons.Outlined.RemoveRedEye,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Network",
                selectedIcon = Icons.Filled.GroupWork,
                unselectedIcon = Icons.Outlined.GroupWork,
                hasNews = false,
                badgeCount = 45
            ),
            BottomNavigationItem(
                title = "Chat",
                selectedIcon = Icons.Filled.Chat,
                unselectedIcon = Icons.Outlined.Chat,
                hasNews = true,
                badgeCount = 5
            ),
            BottomNavigationItem(
                title = "Contacts",
                selectedIcon = Icons.Filled.Contacts,
                unselectedIcon = Icons.Outlined.Contacts,
                hasNews = true,
            ),
            BottomNavigationItem(
                title = "Groups",
                selectedIcon = Icons.Filled.Groups,
                unselectedIcon = Icons.Outlined.Groups,
                hasNews = false,
            ),
        )
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                         navController.navigate(item.title)
                    },
                    label = {
                        Text(text = item.title)
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if(item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if(item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun DrawerContent(scope: CoroutineScope, drawerState: DrawerState) {
        val items = listOf(
            NavigationItem(
                title = "Edit Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
            ),
            NavigationItem(
                title = "My Network",
                selectedIcon = Icons.Filled.Group,
                unselectedIcon = Icons.Outlined.Group,
                badgeCount = 45
            ),
            NavigationItem(
                title = "Switch to Business",
                selectedIcon = Icons.Filled.Business,
                unselectedIcon = Icons.Outlined.Business,
            ),
            NavigationItem(
                title = "Switch to Merchant",
                selectedIcon = Icons.Filled.Shop,
                unselectedIcon = Icons.Outlined.Shop,
            ),
            NavigationItem(
                title = "Dating",
                selectedIcon = Icons.Filled.HeartBroken,
                unselectedIcon = Icons.Outlined.HeartBroken,
            ),
            NavigationItem(
                title = "Matrimony",
                selectedIcon = Icons.Filled.Engineering,
                unselectedIcon = Icons.Outlined.Engineering,
            ),NavigationItem(
                title = "Buy-Sell-Rent",
                selectedIcon = Icons.Filled.ShoppingCart,
                unselectedIcon = Icons.Outlined.ShoppingCart,
            ),
            NavigationItem(
                title = "Jobs",
                selectedIcon = Icons.Filled.Work,
                unselectedIcon = Icons.Outlined.WorkOutline,
            ),
            NavigationItem(
                title = "Business Card",
                selectedIcon = Icons.Filled.CreditCard,
                unselectedIcon = Icons.Outlined.CreditCard,
            ),
            NavigationItem(
                title = "Netclan Group",
                selectedIcon = Icons.Filled.Groups,
                unselectedIcon = Icons.Outlined.Groups,
            ),
            NavigationItem(
                title = "Notes",
                selectedIcon = Icons.Filled.EditNote,
                unselectedIcon = Icons.Outlined.EditNote,
            ),
            NavigationItem(
                title = "Live Location",
                selectedIcon = Icons.Filled.LocationOn,
                unselectedIcon = Icons.Outlined.LocationOn,
            ),

        )
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        ModalDrawerSheet {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.drawer_header_ic),
                        contentDescription = "Navigation header",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth() // Adjust the size as needed
                            .clip(RoundedCornerShape(2))
                            .padding(start = 0.dp, end = 0.dp, top = 0.dp)
                    )
                    Text(text = "BISWARANJAN BEHERA",
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 46.dp)
                    )
                    Icon(imageVector = Icons.Filled.Settings,
                        contentDescription = "Setting",
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 14.dp,top = 14.dp)
                            .clickable {
                                Toast.makeText(this@MainActivity,"Setting Activity",Toast.LENGTH_LONG).show()
                            }
                    )
                    
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
//                                            navController.navigate(item.route)
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }

        }
    }


}





