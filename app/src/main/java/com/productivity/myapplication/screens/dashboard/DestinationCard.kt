package com.productivity.myapplication.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun DestinationCard(destinations: String, onClick: () -> Unit){

    Card(shape = RoundedCornerShape(15.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = destinations)
            IconButton(onClick = {
                onClick()
            }) {
                Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = "right arrow")
            }
        }
    }
}