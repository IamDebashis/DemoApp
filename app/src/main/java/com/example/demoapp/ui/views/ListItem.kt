package com.example.demoapp.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.demoapp.data.models.User


@Composable
fun ListItem(item: User, modifier: Modifier = Modifier) {

    OutlinedCard(modifier = modifier.padding(5.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "id: ${item.id}")
            Text(text = "name: ${item.name}")
            Text(text = "email: ${item.email}")
            Text(text = "address: ${item.address}")
            Text(text = "createdAt: ${item.createdAt}")
        }
    }


}


@Preview
@Composable
private fun ListItem_Preview() {
    ListItem(
        User(
            id = "1",
            name = "Demo name",
            email = "Demo@gmail.com",
            address = "kolkata",
            createdAt = "today"
        )
    )
}