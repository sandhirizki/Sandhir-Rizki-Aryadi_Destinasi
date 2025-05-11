package com.example.destinasi.ui.theme.screen

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Detail : Screen("detail_screen") {
        fun withArgs(destinasiId: Long?): String {
            return if (destinasiId != null) {
                "detail_screen?destinasiId=$destinasiId"
            } else {
                "detail_screen"
            }
        }
    }
}
