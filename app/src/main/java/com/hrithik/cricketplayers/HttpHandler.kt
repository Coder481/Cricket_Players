package com.hrithik.cricketplayers

import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

class HttpHandler constructor() {

    companion object {
        val TAG = (HttpHandler::class.java).simpleName as String
    }

    public fun makeServiceCall(reqUrl : String) : String?{
        var response : String?  = null

        try {

            val url = URL(reqUrl)

            val conn : HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            // Read the response
            val inp : InputStream = BufferedInputStream(conn.inputStream)

            response = convertToResponse(inp)

        }catch (e : MalformedURLException){
            Log.e(TAG, "makeServiceCall1: ${e.localizedMessage}")
        }catch (e : ProtocolException){
            Log.e(TAG, "makeServiceCall2: ${e.localizedMessage}")
        }catch (e : IOException){
            Log.e(TAG, "makeServiceCall3: ${e.localizedMessage}")
        }catch (e : Exception){
            Log.e(TAG, "makeServiceCall4: ${e.localizedMessage}")
        }

        return response
    }

    private fun convertToResponse(inp: InputStream): String? {
        val reader = BufferedReader(InputStreamReader(inp))
        val sb : StringBuilder = StringBuilder()

        var line : String = reader.readLine()
        try {
            while ((line) != null){
                sb.append(line).append("\n")
                line = reader.readLine()
                Log.i(TAG,"lineFromInputStream: $line")
            }
        }catch (e : IOException){
            Log.e(TAG, "convertToResponse1: ${e.localizedMessage}")
        }finally {
            try {

            }catch (e : IOException){
                Log.e(TAG, "convertToResponse2: ${e.localizedMessage}")
            }
        }

        return sb.toString()
    }
}