package rk.first.saathi.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rk.first.saathi.R
import rk.first.saathi.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController){
    Scaffold(
        bottomBar = {
            Footer()
        },
        floatingActionButton = {
            Help()
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
            Display(navController)
            myButtons()
        }
    }
}

@Composable
fun Header(){
    val gradient = Brush.linearGradient(
        listOf(Color(0XFFFEE990),Color(0xFFF2D660))
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(74.dp)
        .background(gradient))
}

@Composable
fun myButtons(){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    )
    {
        LargeFloatingActionButton(
            onClick = {},
            shape = CircleShape,
            containerColor = Color.White,
        ) {
            Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
                , modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun Footer(){
    BottomAppBar(
        containerColor = Color(0xFFC3B36F),
        contentColor = Color(0xFFFEE990),
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFFEE990)),
                modifier = Modifier
                    .wrapContentSize(),
                onClick = {}
            ) {
                Text(
                    text = "Welcome",
                    color = Color(0xFF2B0E48),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun Display(navController:NavController){
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
                .height(256.dp)
                .width(311.dp)
        )

        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier
                .width(193.dp)
                .height(60.dp),
            onClick = {navController.navigate(Screen.Home.route)}
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2B0E48),
                    textAlign = TextAlign.Center,
                )
            )
        }

        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier
                .padding(top = 20.dp)
                .width(281.dp)
                .height(60.dp),
            onClick = {}
        ) {
            Text(
                text = "Register",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2B0E48),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun Help(){
    var presses by remember {mutableIntStateOf(0)}
    LargeFloatingActionButton(
        containerColor = Color(0xFF2B0E48),
        contentColor = Color(0xFFFEE990),
        onClick = { presses++ },
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(id = R.drawable.questionmark),
            contentDescription = "Help",
            modifier = Modifier.height(50.dp))

    }
}

