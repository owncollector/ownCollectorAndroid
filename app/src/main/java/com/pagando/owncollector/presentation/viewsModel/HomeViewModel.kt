package com.pagando.owncollector.presentation.viewsModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.text.DecimalFormat

class HomeViewModel : ViewModel(){
    fun generateQRCode(content: String, width: Int = 500, height: Int = 500): Bitmap? {
        val writer = QRCodeWriter()
        return try {
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    fun formattedAmount(amount: String): String {
        val numericAmount = amount.toDoubleOrNull() ?: 0.00
        val decimalFormat = DecimalFormat("0.00")
        val formattedAmount = decimalFormat.format(numericAmount)
        return formattedAmount
    }

    fun refreshData() {
            Log.d("refresh", "refreshing")
    }
}