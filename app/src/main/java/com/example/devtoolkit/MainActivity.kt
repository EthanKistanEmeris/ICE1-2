package com.example.devtoolkit

import android.content.Context
import android.os.Bundle
import android.os.Build //added this so we can  see the build information
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.trimmedLength
import com.example.devtoolkit.ui.theme.DevToolKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
    DeveloperNotesScreen()
    DeveloperLinksScreen()
    DeviceInfoScreen()
}
        }
    }
}

@Composable
fun DeviceInfoScreen() {
//Device information
    val userDeviceInfo = """
        Manufacturer: ${Build.MANUFACTURER}
        Device Model:${Build.MODEL}
        Android version:${Build.VERSION.RELEASE}
        SDK Level: ${Build.VERSION.SDK_INT}
        """. trimIndent()

    //Display the info through compose
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Device Information",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = userDeviceInfo,
            fontSize = 18.sp

        )
    }
}

    @Composable
    fun  DeveloperNotesScreen() {
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        var text by remember { mutableStateOf("") }
        var savedDisplay by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
           val loadedText = sharedPreferences.getString("saved_text", "") ?: ""
text = loadedText
savedDisplay = loadedText



            }


        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter your notes") },
                modifier = Modifier.fillMaxWidth()

            )



            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    sharedPreferences.edit()
                        .putString("saved_text", text)
                        .apply()

                    savedDisplay = text
                }
            )
            {
                Text("Save Notes")
            }


            OutlinedTextField(
                value = savedDisplay,
                onValueChange = {},
                label = { Text("Stored Notes") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    sharedPreferences.edit()
                        .remove("saved_text")
                        .apply()
                    savedDisplay = ""
                    text = ""
                }
            ){
                Text("Delete Notes")
            }

        }
    }
@Composable
fun DeveloperLinksScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("DevLinkPrefs", Context.MODE_PRIVATE)

    var title by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    val categories = listOf("Documentation","Tutorials","Tools",)
    var selectedCategory by remember { mutableStateOf("") }

    var displayTitle by remember { mutableStateOf("") }
    var displayLink by remember { mutableStateOf("") }
    var displayCategory by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        displayTitle = sharedPreferences.getString("title", "") ?: ""
        displayLink = sharedPreferences.getString("link", "") ?: ""
        displayCategory = sharedPreferences.getString("category", "") ?: ""
    }

    Column(
        modifier = Modifier.padding(16.dp)
    )
    {
        OutlinedTextField(
            label = { Text("Title") },
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Save Link:", fontSize = 20.sp)
        OutlinedTextField(
            label={Text("link")},
            value = link,
            onValueChange = { link = it},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier= Modifier.height(16.dp))

        Text("Select Category:", fontSize = 14.sp)
        androidx.compose.foundation.layout.Row {
            categories.forEach { categoryName ->
                androidx.compose.material3.FilterChip(
                    modifier = Modifier.padding(end = 8.dp),
                    selected = (selectedCategory == categoryName),
                    onClick = { selectedCategory = categoryName },
                    label = { Text(categoryName) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                sharedPreferences.edit()
                    .putString("title", title)
                    .putString("link", link)
                    .putString("category",selectedCategory)
                    .apply()

                displayTitle = title
                displayLink = link
                displayCategory = selectedCategory
            }
        ) {
            Text("Save Link Information")


            }

        Text("Saved Link:", fontSize = 20.sp)
        Text("Title:", fontSize = 10.sp)
        Text(displayTitle, fontSize = 16.sp)
        Text("Link:", fontSize = 20.sp)
        Text(displayLink, fontSize = 16.sp)
        Text("Category:", fontSize = 20.sp)
        Text(displayCategory, fontSize = 16.sp)






    }



}












