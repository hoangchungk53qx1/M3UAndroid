package com.m3u.features.live

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m3u.core.util.toast
import com.m3u.ui.components.LivePlayer
import com.m3u.ui.model.AppAction
import com.m3u.ui.util.EventHandler
import com.m3u.ui.util.LifecycleEffect

@Composable
internal fun LiveRoute(
    modifier: Modifier = Modifier,
    viewModel: LiveViewModel = hiltViewModel(),
    setAppActions: (List<AppAction>) -> Unit,
    id: Int
) {
    val context = LocalContext.current
    val state: LiveState by viewModel.readable.collectAsStateWithLifecycle()

    val setAppActionsUpdated by rememberUpdatedState(setAppActions)
    LifecycleEffect { event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                val actions = listOf<AppAction>()
                setAppActionsUpdated(actions)
            }
            Lifecycle.Event.ON_PAUSE -> {
                setAppActionsUpdated(emptyList())
            }
            else -> {}
        }
    }

    EventHandler(state.message) {
        context.toast(it)
    }

    LaunchedEffect(id) {
        viewModel.onEvent(LiveEvent.Init(id))
    }
    LiveScreen(
        modifier = modifier,
        url = state.live?.url
    )
}

@Composable
private fun LiveScreen(
    modifier: Modifier = Modifier,
    url: String?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("features:live")
    ) {
        LivePlayer(
            url = url,
            useController = false,
            modifier = Modifier.fillMaxSize()
        )
    }
}