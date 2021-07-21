package com.ebookfrenzy.applinking

import android.net.Uri
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.activity_app_linking.*

class AppLinkingActivity : AppCompatActivity() {

    private var dbHandler: MyDBHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_linking)

        dbHandler = MyDBHandler(this, null, null, 1)
    }

    fun findLandmark(view: View) {

        if (idText?.text.toString() != "") {
            val landmark = dbHandler?.findLandmark(idText?.text.toString())

            if (landmark != null) {
                val uri = Uri.parse("http://<your_domain>/landmarks/" + landmark.id)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else {
                titleText?.setText("No Match")
            }

            /* if (landmark != null) {

                val intent = Intent(this, LandmarkActivity::class.java)
                val landmarkid = idText?.text.toString()
                intent.putExtra(LANDMARK_ID, landmarkid)
                startActivity(intent)

                /*
                val uri = Uri.parse("http://example.com/landmarks/" + landmark.id);
                val intent = Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                */
            } else {
                titleText?.setText("No Match")
            }*/
        }
    }

    fun addLandmark(view: View) {

        val landmark = Landmark(idText?.text.toString(), titleText?.text.toString(),
                descriptionText?.text.toString(), 1)

        dbHandler?.addLandmark(landmark)
        idText?.setText("")
        titleText?.setText("")
        descriptionText?.setText("")

    }

    companion object {

        val LANDMARK_ID = "landmarkID"
        private val TAG = "AppIndexActivity"
        private val PERSONAL = 1
        private val PUBLIC = 0
    }
}
