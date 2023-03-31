package eu.ahmed.navigationApp.data.remote

import eu.ahmed.navigationApp.domain.abstraction.ResourceProvider
import navigationApp.R
import okhttp3.*
import javax.inject.Inject


class MockClientInterceptor @Inject constructor(private val resourceProvider: ResourceProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url()
        return when (url.encodedPath()) {
             "/getCity" -> {
                val response: String = readJsonFromFile()
                return Response.Builder()
                    .code(200)
                    .message(response)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(
                        ResponseBody.create(
                            MediaType.parse("application/json"),
                            response.toByteArray()
                        )
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            }
            else -> Response.Builder()
                .code(404)
                .build()
        }
    }

    private fun readJsonFromFile(): String {
        return resourceProvider.getRawResource(R.raw.cities)
    }
}