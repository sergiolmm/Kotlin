package br.com.slmm.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.slmm.retrofit2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var mApiService2: APIService2? = null
    lateinit var DS403List: ArrayList<DS403>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {

            mApiService2 = RestClient2.client.create(APIService2::class.java)

            val call = mApiService2!!.fetchDS403()
            call!!.enqueue(object : Callback<ArrayList<DS403>?> {

                override fun onResponse(call: Call<ArrayList<DS403>?>, response: Response<ArrayList<DS403>?>){
                    Log.d("RETTO", "TOTAL " + response.body()!!)
                    DS403List = response.body()!!
                }
                override fun onFailure(call: Call<ArrayList<DS403>?>, t: Throwable) {
                    Log.e("TAG", "Got error : " + t.localizedMessage)
                }


            })


        }
    }
}

object RestClient {

    private val BASE_URL = "https://api.stackexchange.com"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.mRetrofit!!
        }
}
object RestClient2 {

    private val BASE_URL = "https://www.slmm.com.br"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.mRetrofit!!
        }
}
//mapes os objetos json (chaves) para um objeto
class Question{
    val title: String? = null
    val link:  String? = null
}

class DS403{
    val id: String? = null
    val nome:  String? = null
}


class QuestionList {
    val items: List<Question>? = null
    val has_more: Boolean? =null
    val quota_max: Number? = null
    val quota_remaining: Number? = null
}
// interface para acessar end point (retrofit)
interface APIService {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    fun fetchQuestions(@Query("tagged") tags: String): Call<QuestionList>
}

interface APIService2 {
    @GET("/CTC/getLista.php")
    fun fetchDS403(): Call<ArrayList<DS403>?>?
}
