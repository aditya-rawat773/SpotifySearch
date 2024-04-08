package com.example.spotifysearch.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.spotifysearch.R
import com.example.spotifysearch.data.models.TokenResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val gson = Gson()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)

        refreshToken("AQBKGTKIL_Ne2KDdoRg3eJ1hsrMN4uL2XD2d8WO7rPAjfPC-ttV17-IyfESO7U5k82ILcNuhXJPwpg5tFgNm__BbLSt5mXBykittxetOdUH8w8jg9DXqchPeCBdJlm1SCz0")
    }

    private fun refreshToken(refreshToken: String) {
        val clientId = "7a63c5761cea4ab48ee617206436dd41"
        val clientSecret = "1ce5669758d6462cb90eee6adf294105"

        val requestBody = FormBody.Builder()
            .add("grant_type", "refresh_token")
            .add("refresh_token", refreshToken)
            .build()

        val request = Request.Builder()
            .url("https://accounts.spotify.com/api/token")
            .addHeader("Authorization", Credentials.basic(clientId, clientSecret))
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Failed to refresh token", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val tokenResponse = gson.fromJson(responseBody, TokenResponse::class.java)

                val editor = sharedPreferences.edit()
                editor.putString("access_token", tokenResponse.access_token)
                editor.putString("refresh_token", tokenResponse.refresh_token)
                // Update expiration time if included in response
                // editor.putLong("token_expires_at", calculateExpirationTime(tokenResponse.expires_in))
                editor.apply()
            }
        })
    }
}
