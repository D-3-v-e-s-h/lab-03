package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit, // Added my onUpdateCity parameter to pass onto my CityListScreen
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember {mutableStateOf(false)}
    // As per compose hint from the lab, I will use a state to track which city is being edited
    var selectedCity by remember {mutableStateOf<City?>(null)}

    Column(modifier = modifier.fillMaxSize()) {
        Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }
        }
        if (showAddCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = {Text("City")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = {newProvinceName = it},
                    label = {Text("Province")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
// Lines 85-88, 100-101, & 105 were generated / modified using Google Gemini, model 3.1 Pro Extended
// Prompt: "How can I have an if-else statement that updates my current selected city and if that city is not selected then do else add city?" 2026-Sep-17
                            val currentCity = selectedCity // Here I created a variable to keep track of my current selected city
                            if (currentCity != null) { // If my currently selected city is not null, then I will pass onUpdateCity
                                val updatedCity = City(name = newCityName, province = newProvinceName)
                                onUpdateCity(currentCity, updatedCity) // This function will update my existing city
                            } else { //
                                onAddCity(
                                    City(
                                        name = newCityName,
                                        province = newProvinceName
                                    )
                                )
                            }

                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false // After updating, this will hide add city fields!
                            selectedCity = null // This will ensure that when I PRESS plus button again after updating, I can get add city button!
                        }
                    }
                ) {
                    Text(if (selectedCity != null) "Update" else "Add City")
                    // If the user has selected a city, then display text "Update", else I will display "Add City"
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
// Lines 117-122 were generated / modified using Google Gemini, model 3.1 Pro Extended
// Prompt: "How can I make it such that when I click on selected city, the text for that city name and province name shows up in the input field?" 2026-Sep-17
                    onClick = {
                        selectedCity = city
                        newCityName = city.name // This line will fill the city name
                        newProvinceName = city.province // And this line will fill the province name
                        showAddCityFields = true // I show the input fields when my city name and province name is filled
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    onClick: () -> Unit // Here I pass onClick constructor to perform some action
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable {onClick()} // I added my missing clickable functionality for onClick
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = {oldCity, updatedCity -> } // Here I pass the input arguments when the function is triggered
        )
    }
}