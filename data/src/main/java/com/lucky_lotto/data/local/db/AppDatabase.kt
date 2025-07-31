package com.lucky_lotto.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucky_lotto.data.local.dao.KeywordDao
import com.lucky_lotto.data.local.dao.LottoRecodeDao
import com.lucky_lotto.data.local.dao.LottoRoundDao
import com.lucky_lotto.data.local.entity.KeywordEntity
import com.lucky_lotto.data.local.entity.LottoRecodeEntity
import com.lucky_lotto.data.local.entity.LottoRoundEntity

@Database(
    entities = [LottoRoundEntity::class, KeywordEntity::class, LottoRecodeEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lottoRoundDao(): LottoRoundDao
    abstract fun lottoRecodeDao(): LottoRecodeDao
    abstract fun keywordDao(): KeywordDao
}