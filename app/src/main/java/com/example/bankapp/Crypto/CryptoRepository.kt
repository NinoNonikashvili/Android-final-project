package com.example.bankapp.Crypto

import com.example.bankapp.api.CryptoApi
import com.example.bankapp.model.CryptoData
import com.example.bankapp.util.Resource
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val api:CryptoApi
) {
    suspend fun getData() :Resource<CryptoData>{
        return try {
            val response = api.getCryptoData()
            val body = response.body()
            if(response.isSuccessful&&body!=null){
                Resource.Success(body)
            }else{
                Resource.Error(response.message())
            }
        }catch (e:Throwable){
            Resource.Error(e.message ?:"error occurred")
        }

    }
}