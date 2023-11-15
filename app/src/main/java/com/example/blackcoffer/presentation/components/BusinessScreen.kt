package com.example.blackcoffer.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcoffer.presentation.MainActivity
import com.example.blackcoffer.presentation.RefineActivity
import com.example.blackcoffer.presentation.effect.ShimmerListItem
import com.example.blackcoffer.ui.theme.primary
import kotlinx.coroutines.delay


@Composable
fun BusinessScreen() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        var isLoading by remember {
            mutableStateOf(true)
        }
        //iT IS ONLY FOR DEMO MOTIVE
        LaunchedEffect(key1 = true) {
            delay(1500)
            isLoading = false
        }

        if(isLoading) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(20) {
                    ShimmerListItem(
                        isLoading = isLoading,
                        contentAfterLoading = {
                              //List of content
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }else {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(text = "Your explore list is ",color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Text(text = "EMPTY", color = Color(0xFFCFA300), fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Sorry, we could not find any user near you.", color = Color.Gray)
                Text(text = "Try increasing your search radius.", color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 65.dp, end = 65.dp),
                    onClick = { context.startActivity(Intent(context,RefineActivity::class.java)) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = primary
                    )) {
                    Text(text = "INCREASE RADIUS")
                }
            }
        }
    }
}