package br.com.slmm.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.slmm.retrofit2.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Body


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var mApiService2: APIService2? = null
    lateinit var DS403List: ArrayList<DS403>

    private var mApiService3: APIService3? = null

    var dadosI : DadosI? = null

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

        binding.btn2.setOnClickListener {
            val src: String = "data:image/jpeg;base64,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"

            mApiService3 = RestClient2.client.create(APIService3::class.java)


            dadosI = DadosI(ra = "987",lat= "1",lon="1",img = src)

            var gson = Gson().newBuilder().disableHtmlEscaping().create()
            var str = gson.toJson(dadosI)
//            Log.d("POST", " "+ str)
          val call = mApiService3!!.sendDados(str)
                call!!.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Log.d("RETTO", "TOTAL " + response.body().toString())
                       // var dados: Resposta = response.body()!!
                       // Log.d("POST", " " + dados!!)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
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
 /*
    private var mGsonConverter: GsonConverterFactory? = null

    val gsonConverter: GsonConverterFactory
        get() {
            if(mGsonConverter == null){
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                        .setLenient()
                        .disableHtmlEscaping()
                        .create())
            }
            return mGsonConverter!!
        }
*/
    val client: Retrofit
        get() {
            if (mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(gsonConverter) //GsonConverterFactory.create())
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

    data class DadosI(
        @SerializedName("ra") val ra: String? = null,
        @SerializedName("lat") val lat: String?= null,
        @SerializedName("lon") val lon: String?= null,
        @SerializedName("img") val img: String?= null
    )

class Resposta{
    val status: String? = null
}




interface APIService3 {
    @Headers("Content-Type: application/json")
    @POST("/DS/dsApiIns.php")
    fun sendDados(@Body req: String): Call<ResponseBody>  //dadosI: DadosI
}
