package com.sampleapp.functions.network.internal.okhttp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class OkhttpViewModel : ViewModel() {

    private val apiService: ApiService by Network.getService()
    private val responseData = MutableLiveData<Pair<String, Any?>>()

    fun get() {
        val label = "GET call"
        viewModelScope.launch {
            val auth = enqueue { apiService.get() }
            responseData.postValue(
                when (auth) {
                    is ResponseWrapper.Success -> Pair(label, auth.body)
                    is ResponseWrapper.Failure -> Pair(label, auth.error)
                }
            )
        }
    }

    fun graphqlQuery() {
        viewModelScope.launch {
            enqueue {
                apiService.graphql(
                    mapOf(
                        "query" to "query Launches(\$limit: Int){launches(limit: \$limit){mission_name}}",
                        "variables" to mapOf("limit" to 3),
                    )
                )
            }
        }
    }

    fun graphqlQueryError() {
        viewModelScope.launch {
            enqueue {
                apiService.graphql(
                    mapOf(
                        "query" to "query Launches(\$limit: Int){launches(limit: \$limit){mission_name}}",
                        "variables" to mapOf("limit" to -1111),
                    )
                )
            }
        }
    }

    fun graphqlMutation() {
        viewModelScope.launch {
            enqueue {
                apiService.graphql(
                    mapOf(
                        "query" to "mutation Insert_users(\$objects: [users_insert_input!]!) {insert_users(objects: \$objects) {affected_rows}}",
                        "variables" to mapOf("objects" to emptyList<Any>()),
                    )
                )
            }
        }
    }

    fun graphqlMutationError() {
        viewModelScope.launch {
            enqueue {
                apiService.graphql(
                    mapOf(
                        "query" to "mutation Insert_users(\$objects: [users_insert_input!]!) {insert_users112231321(objects: \$objects) {affected_rows}}",
                        "variables" to mapOf("objects" to emptyList<Any>()),
                    )
                )
            }
        }
    }

    fun post() {
        val label = "POST call"
        viewModelScope.launch {
            val auth = enqueue {
                apiService.post(
                    hashMapOf(
                        "user" to "John Smith",
                        "email" to "john.smith@gmail.com"
                    )
                )
            }
            responseData.postValue(
                when (auth) {
                    is ResponseWrapper.Success -> Pair(label, auth.body)
                    is ResponseWrapper.Failure -> Pair(label, auth.error)
                }
            )
        }
    }

    fun xml() {
        val label = "XML Response call"
        val requestBodyText =
            """<html><head>bye</head><body>hello</body></html>""".trimMargin()
        val requestBody = requestBodyText.toRequestBody("text/xml".toMediaTypeOrNull())

        viewModelScope.launch {
            val auth = enqueue {
                apiService.xml(requestBody)
            }

            responseData.postValue(
                when (auth) {
                    is ResponseWrapper.Success -> Pair(label, auth.body)
                    is ResponseWrapper.Failure -> Pair(label, auth.error)
                }
            )
        }
    }

    fun form() {
        val label = "Form URL Encoded call"
        viewModelScope.launch {
            val auth = enqueue {
                apiService.form(title = "sample title", diff = "sample diff")
            }
            responseData.postValue(
                when (auth) {
                    is ResponseWrapper.Success -> Pair(label, auth.body)
                    is ResponseWrapper.Failure -> Pair(label, auth.error)
                }
            )
        }
    }
}
