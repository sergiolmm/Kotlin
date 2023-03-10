package br.unicamp.tela1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var txtLabel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLabel = findViewById(R.id.txtBase)
    }

    fun onBtn1Click(v: View?){

        if (txtLabel.text == "Mudei")
        {
          txtLabel.text = "Texto 1"
        }
        else{
            txtLabel.text = "Mudei"
        }



    }
}