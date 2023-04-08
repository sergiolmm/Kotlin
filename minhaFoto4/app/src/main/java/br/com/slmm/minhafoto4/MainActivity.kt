package br.com.slmm.minhafoto4

import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    val ALL_PERMISSON = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TESTE", "iniciou")
        if (hasNoPermissions()){
            requestPermission()
        }else{
            Log.d("TESTE", "passou")
        }
    }

    override fun onStart() {
        super.onStart()


    }

    val permissions = arrayOf(android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)

    private fun hasNoPermissions(): Boolean{

        var finePermisson = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        var coarsePermisson = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        var writePermission=ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        var cameraPermission=ContextCompat.checkSelfPermission(this, CAMERA)
        return (( finePermisson != PackageManager.PERMISSION_GRANTED && coarsePermisson != PackageManager.PERMISSION_GRANTED)||
            writePermission != PackageManager.PERMISSION_GRANTED ||
            cameraPermission != PackageManager.PERMISSION_GRANTED  )

        /*
        return ContextCompat.checkSelfPermission(this,
            READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
               ContextCompat.checkSelfPermission(this,
            WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
            CAMERA) != PackageManager.PERMISSION_GRANTED
         */
    }

    fun requestPermission(){
        Log.d("TESTE", "request permission")
        requestPermissions(permissions,ALL_PERMISSON)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("Teste", requestCode.toString())
        if (requestCode === ALL_PERMISSON)
        {
            var i = 0
            permissions.forEach {
                if (it == android.Manifest.permission.CAMERA) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    }
                    Log.d("Teste", "CAMERA")
                }
                if (it == android.Manifest.permission.ACCESS_FINE_LOCATION) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    }
                    Log.d("Teste", "ACCESS_FINE_LOCATION")
                }
                if (it == android.Manifest.permission.ACCESS_COARSE_LOCATION) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    }
                    Log.d("Teste", "ACCESS_COARSE_LOCATION")
                }
                i++
            }
        }
    }
}