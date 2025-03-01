package com.koco.carrentalbooking.presentation

sealed class Screen(val route: String) {
    object CRSearchScreen: Screen("cr_search_screen")
}