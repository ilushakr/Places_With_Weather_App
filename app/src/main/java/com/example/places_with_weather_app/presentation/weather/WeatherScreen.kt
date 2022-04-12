package com.example.places_with_weather_app.presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.common.Result
import com.example.places_with_weather_app.R
import com.example.places_with_weather_app.presentation.utils.UiEvent
import com.example.places_with_weather_app.ui.theme.AppBaseTheme
import com.example.places_with_weather_app.utils.GsonUtils.fromArgs
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun WeatherInfoScreen(
    viewModel: WeatherViewModel,
    weatherArgs: String,
    onNavigate: (UiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        val args = fromArgs<WeatherArgs>(weatherArgs)
        viewModel.getData(args.lat, args.lng)
    }

    val weather = viewModel.weatherState.collectAsState().value
    val mode = viewModel.modeState.collectAsState().value

    val keyboardController = LocalSoftwareKeyboardController.current

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        // Do smthg
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onNavigate(event)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WeatherInfoTopBar(
                mode = mode,
                onBack = {
                    keyboardController?.hide()
                    viewModel.onEvent(WeatherScreenEvent.OnBackClick)
                },
                onAction = { weatherScreenEvent ->
                    keyboardController?.hide()
                    viewModel.onEvent(weatherScreenEvent)
                }
            )
        },
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            when (mode) {
                WeatherScreenMode.Exist -> {
                    item {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = viewModel.title,
                                style = MaterialTheme.typography.h5
                            )

                            Spacer(modifier = Modifier.height(AppBaseTheme.dimens.baseMedium))
                        }
                    }
                    showWeather(weather, this@LazyColumn)
                }
                WeatherScreenMode.NotExist -> {
                    showWeather(weather, this@LazyColumn)
                }
                WeatherScreenMode.Edit -> {
                    item {

                        var editTitle by remember { mutableStateOf(viewModel.title) }

                        Column(modifier = Modifier.fillMaxSize()) {

                            TextField(
                                value = editTitle,
                                modifier = Modifier.fillMaxWidth(),
                                onValueChange = { title ->
                                    editTitle = title
                                    viewModel.updateTitle(title)
                                }
                            )

                            Spacer(modifier = Modifier.height(AppBaseTheme.dimens.baseMedium))
                        }
                    }
                    showWeather(weather, this@LazyColumn)
                }
                WeatherScreenMode.Error -> {
                    item {
                        Text(
                            text = "Oops... Something went wrong",
                            modifier = Modifier.fillParentMaxSize()
                        )
                    }
                }
                WeatherScreenMode.Pending -> Unit
            }
        }
    }
}

private fun showWeather(weather: Result<List<String>>, scope: LazyListScope) {
    when (weather) {
        is Result.Success -> {
            scope.items(weather.data) { item ->
                Text(text = item, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
        is Result.Fail -> scope.item {
            Text(
                text = weather.error.message ?: weather.error.localizedMessage,
                modifier = Modifier.fillParentMaxSize()
            )
        }
        is Result.Pending -> Unit
    }
}

@Composable
fun WeatherInfoTopBar(
    mode: WeatherScreenMode,
    onBack: () -> Unit,
    onAction: (WeatherScreenEvent) -> Unit
) {
    val saveMarkerMessage = stringResource(id = R.string.marker_saving)
    val emptyTitleMessage = stringResource(id = R.string.empty_title)

    TopAppBar(
        title = {
            Text(text = "Place weather")
        },
        actions = {
            when (mode) {
                is WeatherScreenMode.Edit -> {
                    Row {
                        IconButton(
                            onClick = {
                                onAction(
                                    WeatherScreenEvent.SavePlace(
                                        message = saveMarkerMessage,
                                        emptyTitleMessage = emptyTitleMessage
                                    )
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Save place"
                            )
                        }
                        IconButton(
                            onClick = {
                                onAction(WeatherScreenEvent.Cancel)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Cancel editing"
                            )
                        }
                    }
                }
                is WeatherScreenMode.Exist -> {
                    IconButton(
                        onClick = {
                            onAction(WeatherScreenEvent.ChangeMode(WeatherScreenMode.Edit))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit place"
                        )
                    }
                }
                is WeatherScreenMode.NotExist -> {
                    IconButton(
                        onClick = {
                            onAction(WeatherScreenEvent.ChangeMode(WeatherScreenMode.Edit))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add place"
                        )
                    }
                }
                else -> Unit
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBack()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back arrow"
                )
            }
        },
        contentColor = MaterialTheme.colors.primaryVariant,
        elevation = 2.dp
    )
}