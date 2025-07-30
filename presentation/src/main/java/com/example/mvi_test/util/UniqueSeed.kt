package com.example.mvi_test.util

import java.util.concurrent.atomic.AtomicLong

/**
 * 로또 Seed 제작
 */
object UniqueSeed {
    private val seedUniquifier = AtomicLong(8682522807148012L)

    fun makeUniqueSeed(codeSum: Long): Long {
        val uniquifier = nextSeedUniquifier()
        val time = System.nanoTime()
        return uniquifier xor time xor codeSum
    }

    private fun nextSeedUniquifier(): Long {
        while(true){
            val current: Long = seedUniquifier.get()
            val next = current * 1181783497276652981L
            if(seedUniquifier.compareAndSet(current, next)){
                return next
            }
        }
    }
}