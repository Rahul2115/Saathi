package rk.first.saathi.ui.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import rk.first.saathi.R
import rk.first.saathi.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController,viewModel: SaathiViewModel,logState: LoginState){
    Scaffold(
        bottomBar = {
            Footer("Login")
        },
        floatingActionButton = {
            LoginHelp()
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
            LoginDisplay(navController = navController, viewModel = viewModel,logState)
            LoginMic(viewModel = viewModel)
        }
    }
}

@Composable
fun LoginMic(viewModel:SaathiViewModel){
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

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
            interactionSource = interactionSource,
            shape = CircleShape,
            containerColor = Color.White,
        ) {
            if(isPressed){
                Log.d("Voice","Listening")
                Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
                    , modifier = Modifier.height(50.dp))

                viewModel.loginListen()
            }else{
                Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
                    , modifier = Modifier.height(50.dp))

                viewModel.speechRecognizer.stopListening()
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginDisplay(navController:NavController,viewModel: SaathiViewModel,logState: LoginState){

    var textName by remember { mutableStateOf("") }
    var textCountry by remember { mutableStateOf("") }
    var textNumber by remember { mutableStateOf("")}

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

        if (viewModel.checkState(1) && viewModel.checkState(2) && viewModel.checkState(3)&& viewModel.checkState(4) && viewModel.checkState(5)&& viewModel.checkState(6)) {
            OutlinedTextField(
                value = logState.name,
                onValueChange = {
                },
                label = { Text("Name") }
            )

            if(logState.name == "") {
                viewModel.speak("Speak your Name by pressing the mic button")
            } else{
                viewModel.speak("Say Confirm if you Name is ${logState.name}")
            }

        }else if(!viewModel.checkState(1) && viewModel.checkState(4)&& viewModel.checkState(5)&& viewModel.checkState(6) && viewModel.checkState(2) && viewModel.checkState(3) ){
            OutlinedTextField(
                value = logState.yearOB.toString(),
                onValueChange = {},
                label = { Text("Year") }
            )
            if(logState.yearOB == 0) {
                viewModel.speak("Speak your birth year by pressing the mic button")
            } else{
                var phone = logState.yearOB.toString()
                //phone = phone.replace(".".toRegex(), "$0 ")
                //phone = phone.replace("(.{1})".toRegex(), " ")
                Log.d("Spaced Number", phone)
                viewModel.speak("Say Confirm if your birth year is $phone")
            }

        }else if(!viewModel.checkState(1) && !viewModel.checkState(4)&& viewModel.checkState(5)&& viewModel.checkState(6) && viewModel.checkState(2) && viewModel.checkState(3) ){
            OutlinedTextField(
                value = logState.monthOB.toString(),
                onValueChange = {},
                label = { Text("month") }
            )
            if(logState.monthOB == 0) {
                viewModel.speak("Speak your birth month by pressing the mic button")
            } else{
                var phone = logState.monthOB.toString()
                //phone = phone.replace(".".toRegex(), "$0 ")
                //phone = phone.replace("(.{1})".toRegex(), " ")
                Log.d("Spaced Number", phone)
                viewModel.speak("Say Confirm if your birth month is $phone")
            }

        }else if(!viewModel.checkState(1) && !viewModel.checkState(4)&& !viewModel.checkState(5)&& viewModel.checkState(6) && viewModel.checkState(2) && viewModel.checkState(3) ){
            OutlinedTextField(
                value = logState.dateOB.toString(),
                onValueChange = {},
                label = { Text("Date") }
            )
            if(logState.dateOB == 0) {
                viewModel.speak("Speak your birth date by pressing the mic button")
            } else{
                var phone = logState.dateOB.toString()
                //phone = phone.replace(".".toRegex(), "$0 ")
                //phone = phone.replace("(.{1})".toRegex(), " ")
                Log.d("Spaced Number", phone)
                viewModel.speak("Say Confirm if your birth date is $phone")
            }

        }else if (!viewModel.checkState(1) && !viewModel.checkState(4)&& !viewModel.checkState(5)&& !viewModel.checkState(6) && viewModel.checkState(2) && viewModel.checkState(3)) {
            OutlinedTextField(
                value = logState.country,
                onValueChange = {},
                label = { Text("Country") }
            )

            if(logState.country == "") {
                viewModel.speak("Speak your Country by pressing the mic button")
            } else{
                viewModel.speak("Say Confirm if your Country is ${logState.country}")
            }
        }else if(!viewModel.checkState(1) && !viewModel.checkState(4)&& !viewModel.checkState(5)&& !viewModel.checkState(6) && !viewModel.checkState(2) && viewModel.checkState(3)){
            OutlinedTextField(
                value = logState.number.toString(),
                onValueChange = {},
                label = { Text("Number") }
            )

            if(logState.number == 0) {
                viewModel.speak("Speak your Phone number by pressing the mic button")
            } else{
                var phone = logState.number.toString()
                phone = phone.replace(".".toRegex(), "$0 ")
                //phone = phone.replace("(.{1})".toRegex(), " ")
                Log.d("Spaced Number", phone)
                viewModel.speak("Say Confirm if your number is $phone")
            }
        }else{
            navController.navigate(Screen.Home.route)
        }
    }
}

@Composable
fun LoginHelp(){
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
