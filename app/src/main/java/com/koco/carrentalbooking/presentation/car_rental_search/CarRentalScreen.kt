package com.koco.carrentalbooking.presentation.car_rental_search

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.koco.carrentalbooking.util.Utils
import java.time.LocalDate


@Composable
fun CarRentalScreen(
    viewModel: MyViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val pickUpLocation by viewModel.pickUpLocation.collectAsState()
    val dropOffLocation by viewModel.dropOffLocation.collectAsState()
    val pickUpDate by viewModel.pickUpDate.collectAsState()
    val dropOffDate by viewModel.dropOffDate.collectAsState()
    val url = viewModel.kayakUrl.collectAsState().value


    val date = remember {
        mutableLongStateOf(0L)
    }
    val dateDialogVisibility = remember {
        mutableStateOf(false)
    }
    var isPickUpDate = remember {
        mutableStateOf(false)
    }
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = pickUpLocation,
                onValueChange = { newLocation -> viewModel.pickUpLocationChanged(newLocation) },
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom = 30.dp)
                    .focusRequester(focusRequester),
                label = {
                    Text(text = "Pickup Location (City, State/Country)")
                },
                maxLines = 1,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "locIcon"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = dropOffLocation,
                onValueChange = { newLocation -> viewModel.dropOffLocationChanged(newLocation) },
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom = 30.dp)
                    .focusRequester(focusRequester),
                label = {
                    Text(text = "Drop-Off Location (City, State/Country)")
                },
                maxLines = 1,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "locIcon"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = pickUpDate.toString(),
                onValueChange = { },
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom = 30.dp)
                    .clickable {
                        dateDialogVisibility.value = true
                        isPickUpDate.value = true
                    },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Black, disabledTextColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                ),
                singleLine = true,
                label = {
                    Text(text = "PickUp Date")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "datIcon"
                    )

                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            OutlinedTextField(
                value = dropOffDate.toString(),
                onValueChange = { },
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom = 60.dp)
                    .clickable {
                        dateDialogVisibility.value = true
                        isPickUpDate.value = false
                    },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Black, disabledTextColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                ),
                singleLine = true,
                label = {
                    Text(text = "DropOff Date")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "datIcon"
                    )

                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Button(onClick = {

                viewModel.generateKayakLink()

                if (url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    Log.d("CheckURI", "${Uri.parse(url)}")
                    context.startActivity(intent)
                }
            }) {
                Text("Search on Kayak")
            }

        }

    }
    if (dateDialogVisibility.value) {
        DatePickDialog(onDateSelected = {
            date.longValue = it
            if (!isPickUpDate.value) {
                viewModel.dropOffChanged(Utils.formatDateToHumanReadableForm(date.longValue))
            } else {
                viewModel.pickUpChanged(Utils.formatDateToHumanReadableForm(date.longValue))
            }
            dateDialogVisibility.value = false
        }, onDismiss = {
            dateDialogVisibility.value = false
        })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickDialog(
    onDateSelected: (date: Long) -> Unit, onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Cancel")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}
