package br.com.slmm.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.slmm.retrofit2.databinding.ActivityMainBinding
import com.google.gson.annotations.SerializedName
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
            val src: String = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAA2ADkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9U68w+Ln7QXhr4SqLS5dtT16UAw6TaMPMwf4nJOEX3PJ7Anisb9qb4+QfAn4fSXVu8b+IL/dBp0MnIDY+aVh3Vcj0ySBnmvzB074gXmueKLnUtTu5Lu8upTLLPM25pGJ5JP8AnAAHQV42YZgsIuSGsn+B+kcKcJvO74rFNxop203k+y7Lu/ku6+/I/jN43+IVwvk6tF4YsicrDpMKSSn2aWZWBH+6in3rqY9B1aeD7RJ4j115Dzu/tKVfx2ghf0rxT4Vab4kn8LjxBFo90+jRrvN3tAUqOrAE5YDByQCBivrHwoLLUvhpBqBiRjJaPKZMfNwCev4VzYSpVxKvUbva/Y9TPsNg8pko4SnFx5uV2s2n2b1fyZ4xe/EXxv4Fummg8RtrNsD81lrFvG6geiSRqjg+7F/pXefC79pzw9481IaLqSjw/wCIC22O2nkDRXPvFJxk/wCywDcHgjmvFfiNZ+I28LN4g/sW7OkMu8XKpkBcffKg7gv+0Rj3r4u8deNHk1JWgmaORHDq6MQVIOQQexBxzXHLM62GqJSV4+f6M+io8I5fnGElKLUKi0vG1k+0ktPyZ+0NFfL/AOxN+0w/xg8OTeHNeuVl8UaTGCJWPzXdv08wj+8pIVvXKnvX1BX1NKrGtBVIPRn4jmGBr5biZ4TEK0ov/hmvJrVH5Sf8FGPibLr3x0vNFV2FrocEVmqE8b2RZXYfXzFH/Aa4P9jX4UR/HT43aZpF/wCZ/YljG2oX4j4LxpjEeewZyin2JxziqX/BQLw5f6T+1H4skbzI4bqWG4iJPDo0EfP0yGH4V6n/AMEvddt9D+OGo2N4ypNqujywWxOBudZI5Cv12oxwPSvjqkI1Mw/e9Zfgtv0P6Do4ipg+F74NWcaSaa7tJyfybb+R9M/thftMQ/CdLb4c+G4YILy5sh9skVBstLVgUWJAOAzAH/dXHHzAi38Nf2nfAWn/ALNqrfeILeDWLKwmtpNNdv8ASXlwwTYnVg2V+YcDPJGDjwP9vz4b6rpXxqfxNJA0uka3bwiG4XJVJI41Ro29DhQw9Q3GcHHzf/ZDbP8AVmniMdWoYqpdeSXkc+U8NZdmWSYZKT1anJq13LVNPfbVeVvU+5v2Uv2w/wDhKvEmn/DvxSYpDcR+Rpl+RgsUTiGXsSQMK3BJGOSRXzl/wUE+B9p8GPiRZazocC2/h3xEjyx2saFY7WdCPMjXsFO5WAHTLAAACqn7LXwu1bxx8ePCh0+Blt9JvodUvLkg7IooXD4J9WKhR7n0Br2L/gq/4ktJrXwN4fidZNRja4vpIwwzHGQqKSOo3ENj12n0qlJ4jL5SrfZej+4zlSp5VxVTpZfoqsW6kVts2nbpsv6Z8zfsd/EK58KftGeB3hchb7UodOkVT95J3ERB9suD/wABr9oq/Bz9luG+vP2mvhpDFGZAviKwkZVGcKtzGzH8FBP4V+8derlCcaMo9LnxPH1SFbGUaqVpcrT+T0/NnxD/AMFHPgLN4r03TfHunQCSTT4/seoKq/MIi2Y5PorMQfZgexr4f8DLe+GtastQsJ5LLUbKZZ4ZoyVZHUggg/UV+299ZW+pWc1rdQpcW0yGOSKRQyupGCCD1BFfDHxu/YpvvDOpS6x4Kt5NV0ZtzvYL81zanOQFH/LROT/tDA+9nI580y+dSX1iiteq/VHs8GcUYejQ/srMJKMdeWT2s94vt5N6a27HrHwx+P3hn44eDRoXxA0qGO6kTyrjzYi1rOegdSOY279tp5B9L7/sk/CC6ElxE0iW7EsBHqIKKPQE5OPqa8W+C+lwRbYpFxJE3lyRsMPGwxlWU8qR3BGRX1Np+l6WdHLNtD7fSqw0niof7RBSa7rU485pRyXENZTWnThJ3tF3j8v6Zw3iL4lfD79m3wfdaf4M0eO6uwGdLezyyvIckNLOxJYc+rEAYGABX5p/GTxRrHxM8W6j4i164+06leNlsDCooGFRB2UDgCvt/wCNtlZw2twxaONAOWchR+ZrzX4S/sZ678T9ai1HXIZtB8M7t5lnjKXE6/3Y42GVz/eYY9Ac8ediY4nGVFQpr3V0WiX9f8MfX5NPKciwksyxNR+1nvKTvOXklv8A1qzI/wCCcP7PU+qfECf4iajDs03R90NnuX/W3LLjIPoik592HpX6Y1keE/Cml+CPD1jomi2iWOm2cYihhj7AdyepJ6knkmtevq8Lh1haSpLX/M/Ec8zaedY6eLkrLaK7Jbf5vzYUUUV1HgGDrngPw74mkMmqaLZXkxGPOkhHmY9Nw5/WstvhH4ZaQMLe/Qf8849Wu0j/AO+BKF/SiilZG0a1SKtGTS9S9pXw38L6Lci5s9Ds47kNuE7Rh5AcYzubJz+NdH06UUUzOUpTd5O7FooooJP/2Q=="

            mApiService3 = RestClient2.client.create(APIService3::class.java)


            dadosI = DadosI(ra = "987",lat= "1",log="1",img = src)


            val call = mApiService3!!.sendDados(dadosI!!)
            call!!.enqueue(object : Callback<Resposta> {

                override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {
                    Log.d("RETTO", "TOTAL " + response.body()!!)
                    var dados: Resposta = response.body()!!

                }

                override fun onFailure(call: Call<Resposta>, t: Throwable) {
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

    data class DadosI(
        @SerializedName("ra") val ra: String? = null,
        @SerializedName("lat") val lat: String?= null,
        @SerializedName("log") val log: String?= null,
        @SerializedName("img") val img: String?= null
    )

class Resposta{
    val status: String? = null
}




interface APIService3 {
    @Headers("Content-Type: application/json")
    @POST("/CTC/dsApiIns.php")
    fun sendDados(@Body dadosInfo: DadosI): Call<Resposta>
}
