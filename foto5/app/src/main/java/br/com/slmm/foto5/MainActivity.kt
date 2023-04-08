package br.com.slmm.foto5

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import br.com.slmm.foto5.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {
    private val CAMERA_CODE = 1000
    private val GPS_CODE = 1001

    private lateinit var binding: ActivityMainBinding
    private var imagem : ImageView? = null
    private var imageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    private val requestMultiplePermissions =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
           permissions.entries.forEach {
             Log.e("DEBUG", "${it.key} = ${it.value}")
             if (!it.value) {
                 Log.i("Permission: ", "${it.key} permisson denied")
             }
          }
       }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(binding.root)


        binding.btnCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, CAMERA) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(CAMERA)
            }
        }

        binding.btnGps.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
        }

        binding.btnVarias.setOnClickListener {
            if ((ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, CAMERA) !=
                        PackageManager.PERMISSION_GRANTED)
            ) {
                requestMultiplePermissions.launch(
                    arrayOf(CAMERA, ACCESS_FINE_LOCATION)
                )
            }
        }

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            binding.btnGravar.isEnabled = false
        }

        binding.btnGravar.setOnClickListener {
            myExternalFile = File(getExternalFilesDir(filepath), fileName)
            Log.e("DEBUG",getExternalFilesDir(filepath).toString())
                try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(binding.edtText.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(applicationContext,"data save",Toast.LENGTH_SHORT).show()
        }

        binding.btnLer.setOnClickListener {
            myExternalFile = File(getExternalFilesDir(filepath), fileName)
            Log.e("DEBUG",getExternalFilesDir(filepath).toString())
            val filename = fileName

            if(filename!=null && filename.trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnFoto.setOnClickListener {
            imagem = binding.imageView
            imageUri = initFileUri()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            luncher.launch(intent)
        }

    }



    val fileName = "JPEG_${System.currentTimeMillis()}.txt"

    private val filepath = "MyFileStorage"
    internal var myExternalFile: File?=null

    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            true
        } else {
            false
        }
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            true
        } else{
            false
        }
    }

    val luncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imagem?.setImageURI(imageUri)
        }
    }

    private fun initFileUri(): Uri {
        val filepath = "JpgFileStorage"
        val fileName = "JPEG_${System.currentTimeMillis()}.jpg"
        //Creates the jpg file on directory above
        val jpgImage = File(
            getExternalFilesDir(filepath),
            fileName)

        //Returns the Uri object to be used with ActivityResultLauncher
        return FileProvider.getUriForFile(
            applicationContext,
            "br.com.slmm.fileprovide1",
            jpgImage)
    }
}