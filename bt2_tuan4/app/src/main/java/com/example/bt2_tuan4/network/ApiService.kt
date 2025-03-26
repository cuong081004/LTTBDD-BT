package com.example.bt2_tuan4.model

import com.example.bt2_tuan4.model.TaskResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("tasks")
    suspend fun getTasks(): Response<TaskResponse>
}