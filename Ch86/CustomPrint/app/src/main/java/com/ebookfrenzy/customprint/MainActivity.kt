package com.ebookfrenzy.customprint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.os.CancellationSignal
import android.content.Context
import android.print.PrintDocumentInfo
import android.print.pdf.PrintedPdfDocument
import android.graphics.pdf.PdfDocument
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.Color
import android.graphics.Paint
import android.print.PrintManager
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    inner class MyPrintDocumentAdapter(private var context: Context)
        : PrintDocumentAdapter() {

        private var pageHeight: Int = 0
        private var pageWidth: Int = 0
        private var myPdfDocument: PdfDocument? = null
        private var totalpages = 4

        override fun onLayout(oldAttributes: PrintAttributes,
                              newAttributes: PrintAttributes,
                              cancellationSignal: CancellationSignal?,
                              callback: LayoutResultCallback?,
                              metadata: Bundle?) {
            myPdfDocument = PrintedPdfDocument(context, newAttributes)

            val height = newAttributes.mediaSize?.heightMils
            val width = newAttributes.mediaSize?.heightMils

            height?.let {
                pageHeight = it / 1000 * 72
            }

            width?.let {
                pageWidth = it / 1000 * 72
            }

            cancellationSignal?.let {
                if (it.isCanceled) {
                    callback?.onLayoutCancelled()
                    return
                }
            }

            if (totalpages > 0) {
                val builder =
                    PrintDocumentInfo.Builder("print_output.pdf").setContentType(
                        PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages)

                val info = builder.build()
                callback?.onLayoutFinished(info, true)
            } else {
                callback?.onLayoutFailed("Page count is zero.")
            }
        }

        override fun onWrite(pageRanges: Array<out PageRange>?,
                             destination: ParcelFileDescriptor?,
                             cancellationSignal: android.os.CancellationSignal?,
                             callback: WriteResultCallback?) {
            for (i in 0 until totalpages) {
                if (pageInRange(pageRanges, i)) {
                    val newPage = PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create()

                    val page = myPdfDocument?.startPage(newPage)

                    cancellationSignal?.let {
                        if (it.isCanceled) {
                            callback?.onWriteCancelled()
                            myPdfDocument?.close()
                            myPdfDocument = null
                            return
                        }
                    }

                    page?.let {
                        drawPage(it, i)
                    }

                    myPdfDocument?.finishPage(page)
                }
            }

            try {
                myPdfDocument?.writeTo(FileOutputStream(
                    destination?.fileDescriptor))
            } catch (e: IOException) {
                callback?.onWriteFailed(e.toString())
                return
            } finally {
                myPdfDocument?.close()
                myPdfDocument = null
            }

            callback?.onWriteFinished(pageRanges)
        }

        private fun pageInRange(pageRanges: Array<out PageRange>?, page: Int):
                Boolean {

            pageRanges?.let {
                for (i in it.indices) {
                    if (page >= it[i].start && page <= it[i].end)
                        return true
                }
            }
            return false
        }

        private fun drawPage(page: PdfDocument.Page,
                             pagenumber: Int) {
            var pagenum = pagenumber
            val canvas = page.canvas

            pagenum++ // 페이지 번호가 1부터 시작하게 한다

            val titleBaseLine = 72
            val leftMargin = 54

            val paint = Paint()
            paint.color = Color.BLACK
            paint.textSize = 40f
            canvas.drawText(
                "Test Print Document Page " + pagenum,
                leftMargin.toFloat(),
                titleBaseLine.toFloat(),
                paint)

            paint.textSize = 14f
            canvas.drawText(
                "This is some test content to verify that custom document printing works",
                leftMargin.toFloat(), (titleBaseLine + 35).toFloat(),
                paint)

            if (pagenum % 2 == 0)
                paint.color = Color.RED
            else
                paint.color = Color.GREEN

            val pageInfo = page.info

            canvas.drawCircle((pageInfo.pageWidth / 2).toFloat(),
                (pageInfo.pageHeight / 2).toFloat(),
                150f,
                paint)
        }
    }

    fun printDocument(view: View) {
        val printManager = this
            .getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = this.getString(R.string.app_name) + " Document"
        printManager.print(jobName, MyPrintDocumentAdapter(this), null)
    }
}