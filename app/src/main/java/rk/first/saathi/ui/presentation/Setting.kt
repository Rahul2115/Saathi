package rk.first.saathi.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rk.first.saathi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(navController: NavController, viewModel: SaathiViewModel, uiState: State) {
    viewModel.updatePageState(navController.currentDestination?.route?.lowercase())
    Scaffold(
        bottomBar = {
            val itemList = listOf(
                BottomNavItem.Read,
                BottomNavItem.Setting,
                BottomNavItem.Empty,
            )
            HomeFooter(itemslist = itemList,navController = navController,viewModel)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFFEE990)),
        )
        {
            Header()
            SettingDisplay(viewModel,uiState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingDisplay(viewModel: SaathiViewModel,uiState: State){
    var text2 by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.file),
            contentDescription = "Saathi Logo",
            modifier = Modifier
                .height(226.dp)
                .width(311.dp)
        )


        OutlinedTextField(
            value = text2,
            onValueChange ={
                text2= it
            },
            label = { Text(text ="OCR Read Values")}
        )

        Button(onClick = {viewModel.addKeywords(text2)}) {
            Text("Add Values")
        }
    }
}