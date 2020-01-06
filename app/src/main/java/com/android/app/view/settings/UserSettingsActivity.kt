package com.android.app.view.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.app.R
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.utils.FileUtils
import com.android.app.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_user_settings.*
import kotlinx.android.synthetic.main.activity_user_settings.imgUser
import kotlinx.android.synthetic.main.activity_user_settings.progressBar
import java.io.File

class UserSettingsActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        showUserData(UserSingleton.instance)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "rquestCode $requestCode")
        Log.d(TAG, "resultCode $resultCode")
        Log.d(TAG, "data ${data.toString()}")
        if (requestCode == 1  && resultCode != 0) {

        }
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.layoutChangePhoto -> {
                val name = UserSingleton.instance.uID
                searchImageFromGalery(name)
            }
        }
    }

    private fun searchImageFromGalery(fileName: String) {
        //Intent para galeria
        val intentGalery = Intent(Intent.ACTION_GET_CONTENT)
        intentGalery.setType("image/*")//Filtrar apneas imagens

        //Intent para camera
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //Salvar foto tirada
        val imageName = fileName.plus(".png")//Nome da foto
        val path = FileUtils(this).getPathRoot()//Diretorio para salvar foto
        val imageSaved = File(path.plus(imageName))//Arquivo que sera salvo apos tirar a foto
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageSaved))//Nao precisa de permissoes para salvar imagem

        //Intent para escolher
        val titleIntent = "Tire uma foto ou escolha na galeria"
        val intentChooser = Intent.createChooser(intentGalery, titleIntent)
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf<Intent>(intentCamera))
        startActivityForResult(intentChooser, 1)
    }

    private fun showUserData(user: BaseUser) {
        edtName.setText(user.name)
        edtEmail.setText(user.email)
        ImageUtils(this).picassoImageUser(user.uID, imgUser, "asdfasdf", progressBar)
    }

    fun changePassword(view: View) {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    fun salvar(view: View) {
        btnSalvar.visibility = View.INVISIBLE
    }

}
