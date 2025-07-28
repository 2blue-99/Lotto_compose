package com.example.mvi_test

import com.example.data.local.entity.LottoRecodeEntity
import com.example.data.util.Utils.makeRecodeGroup
import com.example.domain.model.LottoItem
import com.google.common.truth.Truth
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `if 1 group recodeEntity reutrn group list`() {
        // given
        val input = listOf(test(), test(), test())

        // when
        val result = input.makeRecodeGroup()

        // then
        result.forEach {
            println(it)
        }
        Truth.assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun `if 2 group recodeEntity reutrn group list`() {
        // given
        val input = listOf(
            test(),
            test(),
            test().copy(saveDate = "2025.07.08 00:00:01", drwtNo1 = "1"),
            test().copy(saveDate = "2025.07.08 00:00:01", drwtNo1 = "2")
        )

        // when
        val result = input.makeRecodeGroup()

        // then
        result.forEach {
            println(it)
        }
        Truth.assertThat(result.size).isEqualTo(2)

    }

    @Test
    fun `if 3 group recodeEntity reutrn group list`() {
        // given
        val input = listOf(
            test(),
            test(),
            test().copy(saveDate = "2025.07.08 00:00:01", drwtNo1 = "1"),
            test().copy(saveDate = "2025.07.08 00:00:01", drwtNo1 = "2"),
            test().copy(saveDate = "2025.07.08 00:00:02", drwtNo1 = "2"),
            test().copy(saveDate = "2025.07.08 00:00:02", drwtNo1 = "2"),
        )

        // when
        val result = input.makeRecodeGroup()

        // then
        result.forEach {
            println(it)
        }
        Truth.assertThat(result.size).isEqualTo(3)
    }

    @Test
    fun `if empty recodeEntity reutrn group list`() {
        // given
        val input = listOf<LottoRecodeEntity>()

        // when
        val result = input.makeRecodeGroup()

        // then
        result.forEach {
            println(it)
        }
        Truth.assertThat(result.size).isEqualTo(0)
    }
}

fun test(): LottoRecodeEntity =
    LottoRecodeEntity(
        id = 0,
        drawType = "",
        drawData = "",
        saveDate = "2025.07.08 00:00:00",
        sequence = "A",
        sum = "100",
        oddEndEvent = "3:3",
        highEndLow = "3:3",
        drwtNo1 = "7",
        drwtNo2 = "7",
        drwtNo3 = "7",
        drwtNo4 = "7",
        drwtNo5 = "7",
        drwtNo6 = "7",
    )