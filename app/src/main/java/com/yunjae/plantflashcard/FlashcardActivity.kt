@file:JvmName("FlashcardActivity")
package com.yunjae.plantflashcard

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.Toolbar
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.yunjae.plantflashcard.dto.Photo
import com.yunjae.plantflashcard.dto.Plant
import com.yunjae.plantflashcard.service.PlantService
import java.lang.Exception




class FlashcardActivity : AppCompatActivity() {


    private var imageView: ImageView? = null

    companion object {
        private const val CAMERA_ACTIVITY_REQUEST: Int = 10
        private const val PICK_PHOTO_FOR_AVATAR = 0
        private var ins: FlashcardActivity? = null

        fun getInstance(): FlashcardActivity? {
            return ins
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar as Toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action" , Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        imageView = findViewById(R.id.imageView)

        ins = this
    }


    fun updateTheTextView(newText: String) {
        this.runOnUiThread {
            run {
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = newText
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        return if (id == R.id.action_settings) true else super.onOptionsItemSelected(item)
    }

    inner class GetPlantsActivity : AsyncTask<String, Int, List<Plant>>() {
        /**
         * OPen a connection to a data feed to retrieve data over a network
         * @param search Array<out String?>
         * @return List<Plant>
         */
        override fun doInBackground(vararg search: String?): List<Plant>? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            // we are collection the first string passed
            var difficulty = search[0]

            val plantService = PlantService()

            // invoke our parse plants function
            val allPlants = plantService.parsePlantFromJsonData(difficulty)

            // return the collection
            return allPlants
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_ACTIVITY_REQUEST) {
                val image = data?.extras?.get("data") as? Bitmap
                imageView?.setImageBitmap(image)
            }
            if (requestCode == PICK_PHOTO_FOR_AVATAR) {
                data?.let {
                    val uri = data.data
                    try {
                        val image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri)
                        imageView?.setImageBitmap(image)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }



                /*val image = data?.extras?.get("data") as? Bitmap
                imageView?.setImageBitmap(image)*/
            }
        }
    }

    fun onBtn5Click(view: View) {
       val imageGalleryActivity =  Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        imageGalleryActivity.setType("image/*")
        startActivityForResult(imageGalleryActivity, PICK_PHOTO_FOR_AVATAR)
    }

    fun onBtn4Click(view: View) {
//        Toast.makeText(this, "You clicked me!", Toast.LENGTH_LONG).show()

        // ceate an implicit intent to invoke the camera
        val cameraActivity = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraActivity, CAMERA_ACTIVITY_REQUEST)

    }

    fun onBtn3Click(view: View) {
        val getPlantsActivity = GetPlantsActivity()
        // execute will create a new thread, and invoke doInBackground
        getPlantsActivity.execute("1")
    }

    fun onBtn1Click(view: View) {
        var food: String? = null
        val size = food?.length?: 0
        Toast.makeText(this, "You clicked button1 result : $size", Toast.LENGTH_LONG).show()
    }

    fun onBtn2Click(view: View) {
        val redbud = Plant(83, "Cercis", "candadensis", "", "Eastern Redbud")
        val pawpaw = Plant(100, "Asimina", "triloba", "Alleghany", "Alleghany Pawpaw", 10)

        val plants = ArrayList<Plant>()
        plants.add(redbud)
        plants.add(pawpaw)

        val photo = Photo("", "")
    }
















}
