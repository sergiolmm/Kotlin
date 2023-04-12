package br.com.slmm.lista2

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.slmm.lista2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
// cria a variavel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inicia a variavel
        binding = ActivityMainBinding.inflate(layoutInflater)
        // seleciona o layout inteiro para ser mostrado
        setContentView(binding.root)

        val cor: Cores = Cores("a","b")
        var lista = mutableListOf(cor,cor,cor)
        lista.add(cor)
        var adapter = CoresAdapter(this,android.R.layout.simple_list_item_2,lista)
        binding.lista.setAdapter(adapter)

    }

}
// cria a classe adapter
class CoresAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    cores: List<Cores>
): ArrayAdapter<Cores>(mContext, mLayoutResourceId, cores){
    private val cor: MutableList<Cores> = ArrayList(cores)

    // sobrescreve algumas funções
    override fun getCount(): Int {
        return cor.size
    }

    override fun getItem(position: Int): Cores {
        return cor[position]
    }

    // função principal
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null){
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent,false)
        }
        try{
            val cor : Cores = getItem(position)
            val txt1 = convertView!!.findViewById<View>(android.R.id.text1) as TextView
            val txt2 = convertView!!.findViewById<View>(android.R.id.text2) as TextView
            txt1.text = cor.nome
            txt2.text = cor.valor
        } catch (e: Exception){
            e.printStackTrace()
        }
        return convertView!!
    }

}
