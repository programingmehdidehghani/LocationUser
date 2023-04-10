package com.example.locationservice.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.locationservice.data.Location
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun LocationDisplay(
    location: Location,
    modifier: Modifier = Modifier,
    textColor : Color = Color.White
){


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${location.longitude}",
            color = Color.LightGray
        )
        Text(
            text = "${location.latitude}",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${location.timeStamp}",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }

}