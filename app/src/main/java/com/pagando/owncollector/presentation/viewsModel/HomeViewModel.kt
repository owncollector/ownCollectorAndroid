package com.pagando.owncollector.presentation.viewsModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.pagando.owncollector.data.models.LoginResponseModel
import com.pagando.owncollector.data.models.TrashResponse
import com.pagando.owncollector.data.remote.api.ApiService
import com.pagando.owncollector.data.remote.api.OkHttpClientSingleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DecimalFormat

class HomeViewModel : ViewModel(){
    private val apiService = ApiService(OkHttpClientSingleton.provideClient())
    private val _amountData = MutableStateFlow<TrashResponse?>(null)
    val amountData: StateFlow<TrashResponse?> get() = _amountData
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

    fun getTrashData(id: String){
        apiService.fetchTrashData(id) { response, error ->
            if (error != null) {
                println("Error: ${error.message}")
            } else {
                response?.let {
                _amountData.value = it
                }
            }
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