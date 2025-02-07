package com.example.walkthroughcreatingnavigation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.walkthroughcreatingnavigation.ui.theme.WalkthroughCreatingNavigationTheme
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.walkthroughcreatingnavigation.ui.appbars.BottomBar
import com.example.walkthroughcreatingnavigation.ui.appbars.DrawerBar
import com.example.walkthroughcreatingnavigation.ui.appbars.TopBar
import com.google.rpc.Help
import kotlinx.coroutines.launch



data class TabItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalkthroughCreatingNavigationTheme {
                ScaffoldApp()
            }
        }
    }
}

@Composable
fun ScaffoldApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    Log.d("TAG", "Hello, this is a debug message!")
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerBar(navController)
        },
        gesturesEnabled = true
    ) {

        Scaffold(
            topBar = { TopBar(navController,title = "Walkthrough. Scaffold", onMenuClick = { drawerState.apply {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }}) },
            bottomBar = { BottomBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = "home") { MainScreen(navController) }
                composable(route = "info") { InfoScreen(navController) }
                composable(route = "settings") { SettingsScreen(navController) }
            }
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {
    Text(
        text = "Home Screen",
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun InfoScreen(navController: NavController) {
    Text(
        text = "Info Screen",
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun SettingsScreen(navController: NavController) {
    Text(
        text = "Settings Screen",
        modifier = Modifier.padding(8.dp)
    )
}


@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = modifier,
            text = "This is home screen"
        )
        OutlinedTextField(
            value = text,
            onValueChange = {text = it}
        )
        Button(
            onClick = { navController.navigate("second/$text") },
            enabled = text.isNotEmpty()
        ) {
            Text("To second")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController, modifier: Modifier = Modifier, parameter: String?) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "This is second screen",
            modifier = modifier
        )
        Text (
            text = "Parameter from home is $parameter"
        )
        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text("Back to home")
        }
    }
}

@Composable
fun NavigationExampleApp(modifier:Modifier) {
    val navController = rememberNavController()
    NavHost (
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(navController, modifier)
        }
        composable(route = "second/{parameter}",
            arguments = listOf(
                navArgument("parameter") {
                    type = NavType.StringType
                }
            )
        ) {
            SecondScreen(navController, modifier, it.arguments?.getString("parameter"))
        }
    }
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
    WalkthroughCreatingNavigationTheme {
        Greeting("Android")
    }
}