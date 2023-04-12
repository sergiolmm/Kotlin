package br.com.slmm.minhafoto

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission_group.CAMERA
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    val CAMERA_PERMISSION_CODE = 1000
    val CAMERA_CAPTURE_CODE    = 1001
    val WRITE_PERMISSION_CODE = 1002
    private var imageUri: Uri? = null
    private var imagem : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagem = findViewById(R.id.picture)
        imageUri = initTempUri()

        findViewById<Button>(R.id.takepicture).setOnClickListener {
            //request permittion

            val permissionGranted = requestCameraPermissions()
            if(permissionGranted) {
                val permissionGranted1 = requestWritePermissions()
                if (permissionGranted1) {
                    opemCameraInterface()
                }
            }
        }



        val luncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                imagem?.setImageURI(imageUri)
            }
        }

        findViewById<Button>(R.id.takepicture2).setOnClickListener {
            //request permittion

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                luncher.launch(intent)

        }

    }
    private fun initTempUri(): Uri {
        //gets the temp_images dir
        val tempImagesDir = File(
            applicationContext.filesDir, //this function gets the external cache dir
            getString(R.string.temp_images_dir)) //gets the directory for the temporary images dir

        tempImagesDir.mkdir() //Create the temp_images dir

        //Creates the temp_image.jpg file
        val tempImage = File(
            tempImagesDir, //prefix the new abstract path with the temporary images dir path
            getString(R.string.temp_image)) //gets the abstract temp_image file name

        //Returns the Uri object to be used with ActivityResultLauncher
        return FileProvider.getUriForFile(
            applicationContext,
            getString(R.string.authorities),
            tempImage)
    }

    private fun requestCameraPermissions(): Boolean {
        var permissionGranted = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val cameraPermissionNotGranted = checkSelfPermission(Manifest.permission.CAMERA)
            if (cameraPermissionNotGranted  == PackageManager.PERMISSION_DENIED){
                var permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

                requestPermissions(permission, CAMERA_PERMISSION_CODE)
            }
            else{
                permissionGranted = true
            }

        }else{
            permissionGranted = true
        }
        return permissionGranted
    }

    private fun requestWritePermissions(): Boolean {
        var permissionGranted = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val cameraPermissionNotGranted = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (cameraPermissionNotGranted  == PackageManager.PERMISSION_DENIED){
                var permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                requestPermissions(permission, WRITE_PERMISSION_CODE)
            }
            else{
                permissionGranted = true
            }

        }else{
            permissionGranted = true
        }
        return permissionGranted
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode === CAMERA_PERMISSION_CODE){
            if (grantResults.size === 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    requestWritePermissions()
            }
            else{
                showAlert("permissão negada para a camera")
            }
        }
        if (requestCode === WRITE_PERMISSION_CODE){
            if (grantResults.size === 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                opemCameraInterface()

            }
            else{
                showAlert("permissão negada para a camera")
            }
        }

    }

    private fun showAlert(message: String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("Ok",null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun opemCameraInterface(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Tirar foto")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Foto teste")
        imageUri = this?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        // cria o intent da camera
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        // lança a intent
        startActivityForResult(intent,CAMERA_CAPTURE_CODE)
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            imagem?.setImageURI(imageUri)
        }
        else{
            showAlert("Falha ao tirar a foto")
        }
    }
*/

}