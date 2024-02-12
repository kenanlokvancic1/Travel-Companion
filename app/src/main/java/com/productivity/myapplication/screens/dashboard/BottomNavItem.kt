package com.productivity.myapplication.screens.dashboard

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val screen: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)