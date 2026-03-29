package com.lucky_lotto.mvi_test.util

import android.app.Activity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

sealed class BillingState {
    object Idle : BillingState()
    object Loading : BillingState()
    object Success : BillingState()
    data class Error(val message: String) : BillingState()
}

class BillingManager(private val activity: Activity) {

    private val _state = MutableStateFlow<BillingState>(BillingState.Idle)
    val state: StateFlow<BillingState> = _state.asStateFlow()

    private var billingClient: BillingClient? = null

    companion object {
        const val PRODUCT_ID = "com.lucky_lotto.mvi_test.coffee"
    }

    fun startPurchase() {
        _state.value = BillingState.Loading
        connectAndPurchase()
    }

    fun resetState() {
        _state.value = BillingState.Idle
    }

    private fun connectAndPurchase() {
        billingClient = BillingClient.newBuilder(activity)
            .setListener { billingResult, purchases ->
                handlePurchaseResult(billingResult, purchases)
            }
            .enablePendingPurchases(
                PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()
            )
            .build()

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryAndLaunchBillingFlow()
                } else {
                    Timber.e("Billing setup failed: ${billingResult.debugMessage}")
                    _state.value = BillingState.Error("결제 서비스 연결 실패")
                }
            }

            override fun onBillingServiceDisconnected() {
                Timber.e("Billing service disconnected")
                _state.value = BillingState.Idle
            }
        })
    }

    private fun queryAndLaunchBillingFlow() {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRODUCT_ID)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient?.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK
                && productDetailsList.isNotEmpty()
            ) {
                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetailsList[0])
                        .build()
                )
                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()
                billingClient?.launchBillingFlow(activity, billingFlowParams)
            } else {
                Timber.e("Product query failed: ${billingResult.debugMessage}")
                _state.value = BillingState.Error("상품 정보를 불러올 수 없습니다")
            }
        }
    }

    private fun handlePurchaseResult(billingResult: BillingResult, purchases: List<Purchase>?) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.forEach { purchase ->
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        consumePurchase(purchase)
                    }
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                _state.value = BillingState.Idle
            }
            else -> {
                Timber.e("Purchase failed: ${billingResult.debugMessage}")
                _state.value = BillingState.Error("결제에 실패했습니다")
            }
        }
    }

    private fun consumePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient?.consumeAsync(consumeParams) { billingResult, _ ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                _state.value = BillingState.Success
            } else {
                Timber.e("Consume failed: ${billingResult.debugMessage}")
                _state.value = BillingState.Error("결제 처리 중 오류가 발생했습니다")
            }
        }
    }

    fun endConnection() {
        billingClient?.endConnection()
        billingClient = null
    }
}
