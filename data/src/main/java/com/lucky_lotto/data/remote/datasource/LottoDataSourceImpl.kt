package com.lucky_lotto.data.remote.datasource

import com.lucky_lotto.data.remote.model.LottoResponse
import com.lucky_lotto.domain.util.APIResponseState
import com.lucky_lotto.domain.util.ExceptionType
import com.lucky_lotto.data.util.apiErrorHandler
import javax.inject.Inject

class LottoDataSourceImpl @Inject constructor(
    private val lottoRetrofit: LottoDataSource
) {
    suspend fun requestLottoData(round: String): APIResponseState<List<LottoResponse>> {
        return when (val result = lottoRetrofit.requestLottoData(
            searchMethod = "center",
            round = round
        ).apiErrorHandler()) {
            is APIResponseState.Success -> {
                val item = result.body.data?.list
                if (item != null) {
                    APIResponseState.Success(
                        code = result.code,
                        message = result.message,
                        body = item
                    )
                } else {
                    APIResponseState.Exception(type = ExceptionType.NonDataException)
                }
            }
            is APIResponseState.Failure -> APIResponseState.Failure(result.code, result.message, result.body)
            is APIResponseState.Exception -> APIResponseState.Exception(result.type)
        }
    }
}
