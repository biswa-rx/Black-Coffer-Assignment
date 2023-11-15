package com.example.blackcoffer.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.blackcoffer.domain.explore.Purpose
import com.example.blackcoffer.domain.explore.Sex
import com.example.blackcoffer.domain.explore.UserInformation
import com.example.blackcoffer.ui.theme.BlackcofferTheme
import com.example.blackcoffer.ui.theme.blueGray
import com.example.blackcoffer.ui.theme.lightGray
import com.example.blackcoffer.ui.theme.primary
import java.util.Locale

@Composable
fun UserCard(
    userInfo: UserInformation,
    modifier: Modifier
) {

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ){
        UserInfoCard(userInfo = userInfo,modifier)
        Card(elevation = CardDefaults.cardElevation(12.dp), modifier = modifier
            .padding(top = 45.dp, start = 12.dp)
            .size(68.dp),
            colors = CardDefaults.cardColors(containerColor = blueGray),) {

            if(userInfo.profilePicUrl != null){
                AsyncImage(modifier = modifier.fillMaxSize(),
                    model = userInfo.profilePicUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }else {
                Text(
                    text = getInitials(userInfo.name),
                    modifier = modifier.padding(14.dp),
                    color = primary,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

@Composable
fun UserInfoCard(
    userInfo: UserInformation,
    modifier: Modifier
){
    var isInvited by rememberSaveable { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(20.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.Yellow),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(3.dp)
            .padding(start = 25.dp, end = 8.dp)
            .clip(
                RoundedCornerShape(25.dp)
            )
    ) {
        Column(
            modifier = modifier
                .padding(start = 16.dp, end = 12.dp, bottom = 25.dp, top = 5.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(0.dp),
                horizontalAlignment = Alignment.End) {
                TextButton(onClick = { isInvited = !isInvited }) {
                    Text(text = (if(isInvited){
                        "PENDING"
                    }else "+ INVITE"),
                        fontSize = 14.sp,
                        color = (if(isInvited){
                            Color.Gray
                        }else primary)
                    )
                }
            }
            Column(
                modifier = modifier.padding(start = 45.dp, top = 0.dp)
            ) {
                Text(text = userInfo.name,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Row {
                    Text(text = userInfo.location, fontSize = 14.sp)
                    Text(text = " | " + userInfo.profession, fontSize = 14.sp)
                }
                Text(text = "Within "+ userInfo.distance + " KM",
                    color = Color.Black,
                    fontSize = 16.sp,
                    )

            }

            Column(modifier = modifier.padding(top = 30.dp)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(userInfo.purposeList) { index, item ->
                        if(index > 0){
                            Text(text = " | $item",
                                color = Color.Black,
                                fontSize = 16.sp,
                            )
                        } else {
                            Text(text = "$item",
                                color = Color.Black,
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
                Text(text = userInfo.about,
                    fontSize = 14.sp)
            }
        }
    }
}

fun getInitials(name: String): String {
    val nameParts = name.trim().split(" ")

    if (nameParts.isEmpty()) {
        return ""
    }

    val firstInitial = nameParts.firstOrNull()?.firstOrNull() ?: ' '
    val lastInitial = nameParts.lastOrNull()?.firstOrNull() ?: ' '

    return "$firstInitial$lastInitial".uppercase(Locale.ROOT)
}

//@Preview
//@Composable
//fun ElevationCardPreview() {
//    BlackcofferTheme {
//        UserCard(UserInformation(
//            1,
//            "Biswaranjan Behera",
//            "Bhawanipatna",
//            4.6f,
//             sex = Sex.Male,
//            "Android Developer",
//            purposeList = listOf(Purpose.Business,Purpose.Coffee,Purpose.Friendship),
//            "biswa@gmail.com",
//            "I have no about...\n But I am happy that you have ask that.")
//        )
//    }
//}

