package rk.first.saathi.ui.presentation

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rk.first.saathi.R

@Composable
fun Home(navController: NavController,viewModel: SaathiViewModel,uiState: State) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    HandleScreens(uiState,navController)
    viewModel.updatePageState(navController.currentDestination?.route?.lowercase())

    Scaffold(
        bottomBar = {
            HomeFooter2(navController = navController,viewModel)
        },
        floatingActionButton = {
            HomeHelp()
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
            HomeDisplay()
            HomeButtons(isPressed,interactionSource,viewModel)
            // the color will change depending on the presence of a hover
        }
    }
    //viewModel.changeScreenSpeak("Welcome to home Screen")
}

@Composable
fun HandleScreens(uiState: State,navController: NavController){
    if(uiState.gotoScreen != "home") {
        navController.navigate(uiState.gotoScreen)
    }
}

@Composable
fun HomeButtons(isPressed: Boolean,interactionSource:MutableInteractionSource,viewModel: SaathiViewModel){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    )
    {
        LargeFloatingActionButton(
            onClick = {viewModel.logOut()},
            shape = CircleShape,
            containerColor = Color(0xFF2B0E48),
            contentColor = Color(0xFFFEE990),
        ) {
            Icon(painter = painterResource(id = R.drawable.home), "Home "
                , modifier = Modifier.height(50.dp))
        }

        LargeFloatingActionButton(
            interactionSource = interactionSource,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .padding(start = 30.dp)
                .clearAndSetSemantics {
                    contentDescription = "Mic Button. Double tap and hold to Speak"
                },
            onClick = {}
        ) {
            if(isPressed){
                val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.click)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
                Log.d("Voice","Listening")
                Icon(painter = painterResource(id = R.drawable.mic), "Mic"
                    , modifier = Modifier.height(50.dp))
                viewModel.homeListen()
            }else{
                Icon(painter = painterResource(id = R.drawable.mic), "Mic"
                    , modifier = Modifier.height(50.dp))
                viewModel.speechRecognizer.stopListening()
            }
//            Icon(painter = painterResource(id = R.drawable.mic), "Large floating action button"
//                , modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun BottomNavigation(itemList:List<BottomNavItem>,navController: NavController,viewModel: SaathiViewModel) {
    NavigationBar(
        containerColor = Color(0xFFC3B36F),
        contentColor = Color(0xFFFEE990)
    ){
        itemList.forEach { item ->
            AddItem(
                screen = item,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    navController: NavController,
    screen: BottomNavItem,
    viewModel: SaathiViewModel
) {
    NavigationBarItem(
        // The icon resource
        icon = {
            Text(
                text = screen.title,
                color = Color(0xFF2B0E48),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        },

        // Display if the icon it is select or not
        selected = (screen.title.lowercase() == navController.currentDestination?.route?.lowercase()),

        // Click listener for the icon
        onClick = {
            viewModel.changeScreenSpeak(screen.title)
            viewModel.updateScreen(screen.title.lowercase())
            if (screen.title.lowercase() != navController.currentDestination?.route?.lowercase()) {
                navController.navigate(screen.title.lowercase())
            }
        },
        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFFEE990))
    )
}

@Composable
fun HomeFooter(itemList:List<BottomNavItem>,navController: NavController,viewModel: SaathiViewModel){
    BottomAppBar(
        containerColor = Color(0xFFC3B36F),
        contentColor = Color(0xFFFEE990),
    ) {
        BottomNavigation(itemList = itemList, navController = navController, viewModel)
    }
}

@Composable
fun HomeFooter2(navController: NavController,viewModel: SaathiViewModel){
    val itemsList = listOf(
        BottomNavItem.Learn,
        BottomNavItem.LOOK,
        BottomNavItem.Read,
        BottomNavItem.Find,
    )

    BottomAppBar(
        containerColor = Color(0xFFC3B36F),
        contentColor = Color(0xFFFEE990),
    ) {
        NavigationBar(
            containerColor = Color(0xFFC3B36F),
            contentColor = Color(0xFFFEE990),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ){
            itemsList.forEach { item ->
                NavigationBarItem(
                    // The icon resource
                    icon = {
                        Image(painter = painterResource(id = item.icon), contentDescription = item.title
                            , modifier = Modifier.height(30.dp))
                    },

                    // Display if the icon it is select or not
                    selected = (item.title.lowercase() == navController.currentDestination?.route?.lowercase()),

                    // Click listener for the icon
                    onClick = {
                        viewModel.changeScreenSpeak(item.title)
                        viewModel.updateScreen(item.title.lowercase())
                        if (item.title.lowercase() != navController.currentDestination?.route?.lowercase()) {
                            navController.navigate(item.title.lowercase())
                        }
                    },
                    // Control all the colors of the icon
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFFEE990))
                )
            }
        }
    }
}

@Composable
fun HomeHelp(){
    var presses by remember { mutableIntStateOf(0) }
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

@Composable
fun HomeDisplay(){
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
            contentDescription = null,
            modifier = Modifier
                .height(256.dp)
                .width(311.dp)
        )
    }
}