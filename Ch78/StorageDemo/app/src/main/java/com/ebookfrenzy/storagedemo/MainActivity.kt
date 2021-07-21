package com.ebookfrenzy.storagedemo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.storagedemo.databinding.ActivityMainBinding
import android.content.Intent
import android.view.View
import android.net.Uri
import java.io.FileOutputStream
import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val CREATE_REQUEST_CODE = 40
    private val OPEN_REQUEST_CODE = 41
    private val SAVE_REQUEST_CODE = 42

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun newFile(view: View) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt")
        startActivityForResult(intent, CREATE_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int,
                                         resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        var currentUri: Uri? = null

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {
                if (resultData != null) {
                    binding.fileText.setText("")
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {
                resultData?.let {
                    currentUri = it.data
                    currentUri?.let {
                        writeFileContent(it)
                    }
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {
                resultData?.let {
                    currentUri = it.data
                    currentUri?.let {
                        try {
                            val content = readFileContent(it)
                            binding.fileText.setText(content)
                        } catch (e: IOException) {
                            // 에러 처리 코드
                        }
                    }
                }
            }
        }
    }

    fun saveFile(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        startActivityForResult(intent, SAVE_REQUEST_CODE)
    }

    private fun writeFileContent(uri: Uri) {
        try {
            val pfd = contentResolver.openFileDescriptor(uri, "w")
            val fileOutputStream = FileOutputStream(
                pfd?.fileDescriptor)
            val textContent = binding.fileText.text.toString()
            fileOutputStream.write(textContent.toByteArray())
            fileOutputStream.close()
            pfd?.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun openFile(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        startActivityForResult(intent, OPEN_REQUEST_CODE)
    }

    private fun readFileContent(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(
            inputStream))
        val stringBuilder = StringBuilder()
        var currentline = reader.readLine()
        while (currentline != null) {
            stringBuilder.append(currentline + "\n")
            currentline = reader.readLine()
        }
        inputStream?.close()
        return stringBuilder.toString()
    }
}