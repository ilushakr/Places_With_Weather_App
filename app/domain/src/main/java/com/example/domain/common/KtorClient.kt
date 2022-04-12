package com.example.domain.common

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorClient(
    val client: HttpClient
) {

    suspend inline fun <reified T> request(
        url: Url,
        requestBody: Any? = null,
        queryParams: Map<String, String> = mapOf(),
        method: HttpMethod = HttpMethod.Get
    ): Result<T> {
        return try {
            Result.Success(
                data = client.request(url.toString()) {

                    this.method = method

                    for ((key, value) in queryParams) {
                        parameter(key, value)
                    }

                    requestBody?.let {
                        contentType(ContentType.Application.Json)
                        body = requestBody
                    }

                }
            )
        } catch (e: Exception) {
            Result.Fail(error = e)
        }
    }

    suspend inline fun <reified T> get(
        url: Url,
        queryParams: Map<String, String> = mapOf(),
    ) = request<T>(
        url = url,
        queryParams = queryParams,
        method = HttpMethod.Get
    )

    suspend inline fun <reified T> post(
        url: Url,
        requestBody: Any,
    ) = request<T>(
        url = url,
        requestBody = requestBody,
        method = HttpMethod.Post
    )
}