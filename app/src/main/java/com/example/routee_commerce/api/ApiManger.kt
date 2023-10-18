package com.example.routee_commerce.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManger {

    companion object {
        private var retrofit: Retrofit? = null

        private fun getInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://ecommerce.routemisr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        fun getApis(): WebServices {
            return getInstance().create(WebServices::class.java)
        }
    }
}
