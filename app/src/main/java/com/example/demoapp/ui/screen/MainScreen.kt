package com.example.demoapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.demoapp.data.models.User
import com.example.demoapp.ui.state.MainScreenEvent
import com.example.demoapp.ui.state.MainScreenState
import com.example.demoapp.ui.theme.DemoAppTheme
import com.example.demoapp.ui.views.ListItem
import com.example.demoapp.ui.views.TopAppbar
import kotlinx.coroutines.flow.flowOf


@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val userPaging = viewModel.translateUser.collectAsLazyPagingItems()
    val uistate by viewModel.uiState.collectAsStateWithLifecycle()
    UI(
        uistate,
        userPaging,
        viewModel::onEvent
    )
}


@Composable
private fun UI(
    state: MainScreenState,
    users: LazyPagingItems<User>,
    onEvent: (MainScreenEvent) -> Unit,
) {


    val listState = rememberLazyListState()

    var lastScrollPosition = 0
    var isScrollUP by remember {
        mutableStateOf(false)
    }
    val positionchange by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex != lastScrollPosition
        }
    }
    LaunchedEffect(positionchange) {
        if (listState.firstVisibleItemIndex > lastScrollPosition) {
            lastScrollPosition = listState.firstVisibleItemIndex
            isScrollUP = true

        } else {
            isScrollUP = false

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 450.dp),
            state = listState
        ) {
            items(count = users.itemCount,
                key = { index -> users[index]?.id ?: -1 }
            ) { index ->
                val user = users[index]
                if (user != null) {
                    ListItem(item = user, modifier = Modifier.fillMaxWidth(0.95f))
                }
            }

        }

        if (state.isLoading) {
            Dialog(onDismissRequest = { }) {
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)

                    )
                    Text(text = "Loading..")

                }
            }
        }
        TopAppbar(isScrollUP = isScrollUP,
            title = state.title,
            modifier = Modifier,
            onLanguageChange = { onEvent(MainScreenEvent.OnLanguageCnage(it)) }
        )


    }


}


@Preview
@Composable
private fun MainScreen_Preview() {
    DemoAppTheme {
        Surface {
            UI(
                state = MainScreenState.init().copy(isError = false, isLoading = true),
                users = flowOf(
                    PagingData.from(
                        listOf(
                            User(
                                id = "1",
                                name = "Debashis",
                                email = "test@gmail.com",
                                address = "kolkata",
                                createdAt = "today"
                            )
                        )
                    )
                ).collectAsLazyPagingItems()
            ) {}
        }
    }
}