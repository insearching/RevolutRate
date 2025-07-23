package com.insearching.revolutrate.fake

import com.insearching.revolutrate.data.api.RevolutApi
import com.insearching.revolutrate.data.model.DigitalRates
import retrofit2.Response

class FakeRevolutApi : RevolutApi {
    override suspend fun latestRates(base: String): Response<DigitalRates> {
        val rates = mapOf(
            "EUR" to 0.9,
            "GBP" to 0.8
        )
        return Response.success(DigitalRates(baseCurrency = base, rates = rates))
    }
}
