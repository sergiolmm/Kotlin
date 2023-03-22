package br.unicamp.duastelas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val btnNext = findViewById(R.id.btnGetValor) as Button
        btnNext.setOnClickListener{
            val intent = Intent(this, TelaValores::class.java)
            launcher.launch(intent)
        }

    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result : ActivityResult ->
        Log.d("Teste","Retorno")
        Log.d("Teste", result.resultCode.toString())
        if (result.resultCode == Activity.RESULT_OK){
            var m : String = ""
            result.data?.let{
                if (it.hasExtra("Resultado")){
                    Log.i("DFF","Callback result ${it.getStringExtra("Resultado")}")
                    m = it.extras?.get("Resultado").toString()
                    it.extras.let { it2 ->
                        //m = "Callback result ${it2.toString()}"
                        m = it2?.get(/* key = */ "Resultado").toString()

                    }
                }
            }

            val ss:String = result.data!!.getStringExtra("ActivityResult").toString()

            val data: Intent? = result.data
            var ms = result.data?.extras




            var resp = ms!!.getString("ActivityResult").toString()
            val returnString = data!!.extras!!.getString("ActivityResult").toString()

            var txt = findViewById(R.id.textView) as TextView
            var msg = returnString//data?.getStringExtra("ActivityResult").toString()

            //getStringExtra("ActivityResult")
            txt.setText(m)
        }

    }
}