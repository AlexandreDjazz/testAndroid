package com.bankingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bankingapp.ui.screens.auth.LoginScreen
import com.bankingapp.ui.screens.auth.SignupScreen
import com.bankingapp.ui.screens.budget.BudgetScreen
import com.bankingapp.ui.screens.help.HelpSupportScreen
import com.bankingapp.ui.screens.home.HomeScreen
import com.bankingapp.ui.screens.map.ATMMapScreen
import com.bankingapp.ui.screens.notifications.NotificationsScreen
import com.bankingapp.ui.screens.payment.PaymentScreen
import com.bankingapp.ui.screens.pin.PinScreen
import com.bankingapp.ui.screens.security.SecurityScreen
import com.bankingapp.ui.screens.settings.SettingsScreen
import com.bankingapp.ui.screens.MainViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Pin : Screen("pin")
    object Home : Screen("home")
    object Payment : Screen("payment")
    object Budget : Screen("budget")
    object ATMMap : Screen("atm_map")
    object Settings : Screen("settings")
    object Security : Screen("security")
    object Notifications : Screen("notifications")
    object HelpSupport : Screen("help_support")
}

@Composable
fun BankingNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = false)
    val hasPin by viewModel.hasPin.collectAsState(initial = false)

    val startDestination = when {
        !isLoggedIn -> Screen.Login.route
        !hasPin -> Screen.Pin.route
        else -> Screen.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Pin.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Pin.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Pin.route) {
            PinScreen(
                onPinVerified = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Pin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPayment = {
                    navController.navigate(Screen.Payment.route)
                },
                onNavigateToBudget = {
                    navController.navigate(Screen.Budget.route)
                },
                onNavigateToATMMap = {
                    navController.navigate(Screen.ATMMap.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                onPaymentComplete = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Budget.route) {
            BudgetScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ATMMap.route) {
            ATMMapScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToSecurity = {
                    navController.navigate(Screen.Security.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                },
                onNavigateToHelp = {
                    navController.navigate(Screen.HelpSupport.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Security.route) {
            SecurityScreen(
                onBack = {
                    navController.popBackStack()
                },
                onChangePIN = {
                    navController.navigate(Screen.Pin.route)
                }
            )
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.HelpSupport.route) {
            HelpSupportScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
