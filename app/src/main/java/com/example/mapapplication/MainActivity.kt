package com.example.mapapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.services.core.ServiceSettings
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //获取高德地图的许可
        ServiceSettings.updatePrivacyShow(this, true, true);
        ServiceSettings.updatePrivacyAgree(this, true);
        val geocodeSearch = GeocodeSearch(this)

        //获取上下文
        val context = this
        //根据本地的Json文件 获取地名 存放到list
        val list = getSightList(context, "SZSight.json")

        //存放每一次搜索的结果
        val builder = StringBuilder()
        //计数器
        var num = 0
        //定义查询体
        var query = GeocodeQuery(list[num], "深圳")
        //发起请求
        geocodeSearch.getFromLocationNameAsyn(query)

        geocodeSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
            override fun onRegeocodeSearched(result: RegeocodeResult?, p1: Int) {

            }

            override fun onGeocodeSearched(result: GeocodeResult?, resultCode: Int) {
                //请求的返回值 resultCode=1000 则为成功
                if (resultCode == 1000) {
                    if (result != null) {
                        runBlocking {
                            launch {
                                //获取到地址的经纬度 随后拼接到builder里
                                val address = result.geocodeAddressList[0].latLonPoint
                                builder.append(result.geocodeQuery.locationName).append(",")
                                    .append(address.longitude.toString()).append(",")
                                    .append(address.latitude.toString()).append("\n")
                            }
                        }
                        num++
                        //计数器+1 达到list的末尾则停止发送请求 把结果写进文件里
                        if (num < list.size) {
                            query = GeocodeQuery(list[num], "深圳")
                            geocodeSearch.getFromLocationNameAsyn(query)
                        } else {
                            //把结果写到文件里
                            writeToFile(context, "example.txt", builder.toString())
                        }
                    } else {
                        num++
                        if (num < list.size) {
                            query = GeocodeQuery(list[num], "深圳")
                            geocodeSearch.getFromLocationNameAsyn(query)
                        } else {
                            writeToFile(context, "example.txt", builder.toString())
                        }
                    }
                } else {
                    num++
                    if (num < list.size) {
                        query = GeocodeQuery(list[num], "深圳")
                        geocodeSearch.getFromLocationNameAsyn(query)
                    } else {
                        writeToFile(context, "example.txt", builder.toString())
                    }
                }

            }


        })
    }


    private fun writeToFile(context: Context, fileName: String, content: String) {
        // 获取应用的内部存储目录
        val fileDir = context.filesDir
        // 创建文件对象
        val file = File(fileDir, fileName)

        try {
            // 打开文件输出流
            val fos = FileOutputStream(file)
            // 将字符串转换为字节数组并写入文件
            fos.write(content.toByteArray())
            // 关闭文件输出流
            fos.close()
            Log.d("aaa", "文件写入成功：${file.absolutePath}")
        } catch (e: IOException) {
            Log.e("aaa", e.message.toString(), e)
        }
    }


    fun trans(string: String): List<String> {
        val result = ArrayList<String>()
        var left = 0
        var right = 0
        for (char in string) {
            right++
            if (char == ' ') {
                result.add(string.substring(left, right))
                left = right
                continue
            }
        }
        return result.toList()
    }

    private fun getSightList(context: Context, fileName: String): List<String> {
        // 获取应用的内部存储目录
        val fileDir = context.filesDir
        // 创建文件对象
        val jsonFile = File(fileDir, fileName)
        val reader = FileReader(jsonFile)

        val gson = Gson()
        val data = gson.fromJson(reader, JsonData::class.java)

        // 遍历verticalData列表获取displayName字段
        val verticalDataList = data.verticalData
        val nameList = ArrayList<String>()


        for (verticalData in verticalDataList) {
            nameList.add(verticalData.displayName)
        }

        reader.close()

        return nameList.toList()
    }
}


