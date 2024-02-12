package rk.first.saathi.ui.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Box(modifier = Modifier
            .padding(start = 25.dp, bottom = 20.dp, end = 25.dp)
            .background(Color.White)
            .fillMaxWidth()
            .height(180.dp))
        {
            Text(modifier = Modifier
                .padding(start = 30.dp, top = 20.dp),
                text = "Sign in",
                color = Color(0xFF2B0E48),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            if (logState.loginSuccess == 0 && viewModel.checkState(2) && viewModel.checkState(3)) {
                OutlinedTextField(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 40.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    value = logState.country,
                    onValueChange = {},
                    label = {Text("Country")},
                )

                if (logState.country == "" && logState.invalidInput == 0) {
                    viewModel.speak("Speak your Country by pressing the mic button")
                } else if (logState.country == "" && logState.invalidInput == 1) {

                } else {
                    viewModel.speak("Say Confirm if your Country is ${logState.country}")
                }
            } else if (logState.loginSuccess == 0 && logState.codeSent == 0 && !viewModel.checkState(
                    2
                ) && viewModel.checkState(3)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 40.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    value = if(logState.number == 0L){""}else{logState.number.toString()},
                    onValueChange = {},
                    label = { Text("Mobile Number") }
                )

                if (logState.number == 0L  && logState.invalidInput == 0) {
                    viewModel.speak("Speak your Phone number by pressing the mic button")
                } else if (logState.number == 0L && logState.invalidInput == 1) {

                } else {
                    var phone = logState.number.toString()
                    phone = phone.replace(".".toRegex(), "$0 ")
                    //phone = phone.replace("(.{1})".toRegex(), " ")
                    Log.d("Spaced Number", phone)
                    viewModel.speak("Say Confirm if your number is $phone")
                }
            } else if (logState.loginSuccess == 0 && logState.codeSent == 1 && !viewModel.checkState(
                    2
                ) && !viewModel.checkState(3)
            ) {
                Log.d(
                    "Values in otp",
                    "${logState.loginSuccess},${logState.codeSent},${!viewModel.checkState(3)}"
                )
                OutlinedTextField(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 40.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    value = logState.otp,
                    onValueChange = {},
                    label = { Text("OTP") }
                )

                if (logState.otp == "") {
                    viewModel.speak("Speak your Otp by pressing the mic button")
                } else if (logState.otp == "" && logState.invalidInput == 1) {

                } else if(logState.otpConfirmed == 0) {
                    var Otp = logState.otp
                    Otp = Otp.replace(".".toRegex(), "$0 ")
                    //phone = phone.replace("(.{1})".toRegex(), " ")
                    Log.d("Spaced Number", Otp)
                    viewModel.speak("Say Confirm if your Otp is $Otp")
                }
            } else {
                Log.d(
                    "Values in last else",
                    "${logState.loginSuccess},${logState.codeSent},${!viewModel.checkState(3)}"
                )
                navController.navigate(Screen.Home.route)
            }
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
