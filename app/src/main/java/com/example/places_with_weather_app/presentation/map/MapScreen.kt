package com.example.places_with_weather_app.presentation.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.places_with_weather_app.R
import com.example.places_with_weather_app.presentation.utils.UiEvent
import com.example.places_with_weather_app.ui.theme.AppBaseTheme
import com.example.places_with_weather_app.utils.Keyboard
import com.example.places_with_weather_app.utils.keyboardAsState
import com.example.places_with_weather_app.wigets.BottomSheetList
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    onNavigate: (UiEvent) -> Unit
) {

    val deleteMarkerMessage = stringResource(id = R.string.marker_deletion)

    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val selectedLocation = remember { mutableStateOf<LatLng?>(null) }
    val typeOfBottomSheet = remember { mutableStateOf<BottomSheetType>(BottomSheetType.MapBottomSheet) }
    val isKeyboardOpen by keyboardAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.getPlaces()

        scope.launch {
            modalBottomSheetState.hide()
        }
    }

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
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

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                type = typeOfBottomSheet.value,
                scope = scope,
                modalBottomSheetState = modalBottomSheetState,
                onOptionSelected = { option ->
                    when (option) {
                        is MapBottomSheetOptions.CreateMarker -> {
                            selectedLocation.value?.let { latLng: LatLng ->
                                viewModel.onEvent(
                                    MapScreenEvent.CreateMarker(
                                        latLng = latLng
                                    ),
                                )
                            }
                        }
                        is MapBottomSheetOptions.ShowWeather -> {
                            viewModel.onEvent(MapScreenEvent.OpenWeatherScreen(selectedLocation.value))
                        }
                        is MapBottomSheetOptions.DeleteMarker -> {
                            viewModel.onEvent(
                                MapScreenEvent.DeleteMarker(
                                    marker = option.marker,
                                    message = deleteMarkerMessage
                                )
                            )
                        }
                    }
                }
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = AppBaseTheme.shapes.bottomSheet,
        sheetBackgroundColor = Color.White,
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
        ) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp),
                onMapLongClick = { latLng ->
                    viewModel.onEvent(
                        MapScreenEvent.CreateMarker(
                            latLng = latLng
                        ),
                    )
                },
                onMapClick = { latLng ->
                    when (isKeyboardOpen) {
                        Keyboard.Closed -> {
                            selectedLocation.value = latLng
                            typeOfBottomSheet.value = BottomSheetType.MapBottomSheet
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                        Keyboard.Opened -> keyboardController?.hide()
                    }
                }
            ) {

                viewModel.markerState.collectAsState().value.forEach { mapMarkerUi ->
                    Marker(
                        position = mapMarkerUi.latLng,
                        title = mapMarkerUi.title,
                        snippet = "weather marker",
                        onClick = { marker ->
                            selectedLocation.value = LatLng(marker.position.latitude, marker.position.longitude)
                            typeOfBottomSheet.value = BottomSheetType.MarkerBottomSheet(marker)
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                            true
                        }
                    )
                }
            }

        }

    }

}

@ExperimentalMaterialApi
@Composable
fun BottomSheetContent(
    type: BottomSheetType,
    scope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    onOptionSelected: (MapBottomSheetOptions) -> Unit
) {
    val items = when (type) {
        is BottomSheetType.MapBottomSheet -> listOf(
            MapBottomSheetOptions.ShowWeather(),
            MapBottomSheetOptions.CreateMarker()
        )
        is BottomSheetType.MarkerBottomSheet -> listOf(
            MapBottomSheetOptions.ShowWeather(),
            MapBottomSheetOptions.DeleteMarker(type.marker)
        )
    }

    val titleView: (@Composable () -> Unit)? = if (type is BottomSheetType.MarkerBottomSheet) {
        {
            BottomSheetTitleView(type.marker.title ?: type.marker.position.toString())
        }
    } else {
        null
    }

    BottomSheetList(
        items = items,
        titleView = titleView
    ) { option: MapBottomSheetOptions ->

        scope.launch {
            modalBottomSheetState.hide()
        }

        onOptionSelected(option)
    }
}

@Composable
fun BottomSheetTitleView(title: String) {
    Text(text = title)
}
