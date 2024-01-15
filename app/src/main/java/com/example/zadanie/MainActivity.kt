package com.example.zadanie

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.zadanie.ui.theme.ZadanieTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

var Studenci by mutableStateOf(mutableListOf<Student>())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZadanieTheme {
                if (Studenci.isEmpty()) {
                    Studenci = readStudents(applicationContext)
                }
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {

                    composable(route = "home") {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            numOfStudents()
                            Button(onClick = {
                                navController.navigate("colum")
                            }) {
                                Text(text = "Show all students in colum")
                            }
                            Button(onClick = {
                                navController.navigate("row")
                            }) {
                                Text(text = "Show all students in row")
                            }
                            Button(onClick = { onDelete() }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }
                    composable(route = "colum") {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            StudentsColum()
                        }
                    }
                    composable(route = "row") {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            StudentsRow()
                        }
                    }


                }

            }
        }
    }
    fun onDelete() {
        val randomIndex = Studenci.indices.randomOrNull() ?: return
        Studenci = Studenci.toMutableList().apply { removeAt(randomIndex) }
    }

}

@Composable
fun StudentsRow() {
    LazyRow {
        Studenci?.let {
            items(it.toList()) {
                Text("Name: ${it.name}, Age: ${it.age}, GPA: ${it.gpa}")
            }
        }
    }
}

@Composable
fun StudentsColum() {
    LazyColumn {
        Studenci?.let {
            items(it.toList()) {
                Text("Name: ${it.name}, Age: ${it.age}, GPA: ${it.gpa}")
            }
        }
    }
}

fun readStudents(context: Context): MutableList<Student> {
    if (Studenci.isEmpty()) {
        val inputStream = context.assets.open("students.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()
        val studentType = object : TypeToken<MutableList<Student>>() {}.type
        Studenci = gson.fromJson(json, studentType) ?: mutableListOf()
    }
    return Studenci
}


@Composable
fun numOfStudents() {
    Text(text = "Liczba studentów: ${Studenci.size}")
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZadanieTheme {
        Greeting("Android")
    }
}