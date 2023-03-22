package br.unicamp.duastelas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TelaValores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_valores)

        val btnRetorno = findViewById(R.id.btnVoltar) as Button
        btnRetorno.setOnClickListener {
            val txt = findViewById(R.id.txt1) as TextView
            var msg = txt.text

            Intent().apply {
                putExtra("Resultado",msg)
                setResult(RESULT_OK, this)
            }

            finish()

        }

    }
}