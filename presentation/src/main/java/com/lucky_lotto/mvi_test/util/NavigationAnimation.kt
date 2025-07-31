package com.lucky_lotto.mvi_test.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

object NavigationAnimation {
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToRightEnter(): EnterTransition {
        return slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(400)
        )
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToLeftEnter(): EnterTransition {
        return slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(400)
        )
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToRightExit(): ExitTransition {
        return slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.End,
            tween(400)
        )
    }
    fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToLeftExit(): ExitTransition {
        return slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start,
            tween(400)
        )
    }
}