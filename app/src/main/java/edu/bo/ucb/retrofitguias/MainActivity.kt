package edu.bo.ucb.retrofitguias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val restApiAdapter = RestApiAdapter()
        val endPoint = restApiAdapter.connectionApi()
        val bookResponseCall = endPoint.getAllPost()
        bookResponseCall.enqueue( object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val posts = response?.body()
                Log.d("RESP POST", Gson().toJson(posts))
                posts?.forEach {
                    Log.d("RESP POST", it.body)
                }
            }
        })
    }
    class ConstantsRestApi {
        companion object {
            const val URL_BASE = "https://jsonplaceholder.typicode.com"
            const val POSTS ="/posts"
        }
    }

    interface EndPointApi {
        @GET(ConstantsRestApi.POSTS)
        fun getAllPost(): Call<List<Post>>
    }
    class RestApiAdapter {
        fun connectionApi(): EndPointApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConstantsRestApi.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(EndPointApi::class.java)
        }
    }
}