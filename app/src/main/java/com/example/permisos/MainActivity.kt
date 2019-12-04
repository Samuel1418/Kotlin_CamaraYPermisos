package com.example.permisos


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1
val REQUEST_IMAGE_CAPTURE = 2


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnComprobar.setOnClickListener {
            comprobarPermisosCamara()
        }

        btnCamara.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun comprobarPermisosCamara() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            toast("No has dado permisos a la camara")
            //LLAMAR A REQUESTPERMISSIONS PARA DAR LOS PERMISOS
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            toast("La camara tiene permisos")

        }

    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView2.setImageBitmap(imageBitmap)
        }
    }
}
