package com.example.mvi_test.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun CommonAdBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 8f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
    ) {
        Surface(
            elevation = 2.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(16),
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
        ) {
//        AdView(context).apply {
//            adSize = AdSize.BANNER
//            adUnitId = ""
//            loadAd(AdRequest.Builder().build())
//        }
        }
    }
}

@Preview
@Composable
private fun CommonAdBannerPreview() {
    CommonAdBanner()
}

fun main() = runBlocking {

    (1..4).shuffled()

    val timeComponent = System.currentTimeMillis().toString().toList().shuffled().joinToString("")
    val stringComponent = "Lucky".sumOf { it.code }  // ASCII 합
    val seed = timeComponent + stringComponent
    val random = Random(seed)
    val number = random.nextInt(1, 46)


//    val random1 = Random(100)
//    val random2 = Random(101)
//
//    val array1 = Array(46){0}
//    val array2 = Array(46){0}
//
//    var count = 0
//
//    while(count < 1000000){
//        val number = random1.nextInt(1,46)
//        array1[number]+=1
//        count++
//    }
//
//    count = 0
//
//    while(count < 1000000){
//        val number = random1.nextInt(1,46)
//        array2[number]+=1
//        count++
//    }

//    println(array1.contentToString())
//    println(array2.contentToString())





//    val input = "Lucky".map { it.code }.sum()
//
//    val array = Array(46){0}
//    var count = 0
//
//    while(count < 100000000){
//        val random = Random(System.currentTimeMillis() + input)
//        val number = random.nextInt(1,46)
//        array[number] += 1
//        count++
//        println(count)
//    }
//
//    println(array.contentToString())


//    println(random.nextInt(1..45))
//    println(random.nextInt(1..45))
//    println(random.nextInt(1..45))
//    println(random.nextInt(1..45))
//    println((1..45).shuffled(random).take(6).sorted())
//    println((1..45).shuffled(random).take(6).sorted())
//    println((1..45).shuffled(random).take(6).sorted())
//    println((1..45).shuffled(random).take(6).sorted())
//    println((1..45).shuffled(random).take(6).sorted())
}