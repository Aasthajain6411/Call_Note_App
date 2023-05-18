package com.example.callnotes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager=LinearLayoutManager(this)
        mAdapter= ListAdapter(this)
        recyclerView.adapter=mAdapter
        //have to check if we have the permission to access call logs. If not then ask permission by requesting code
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG),111)
        }
        //if permission already granted then run the program
        else{
            fetchdata()
        }
    }
    //if permission granted then check and run the program
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
            fetchdata()
        }
    }
    @SuppressLint("Range")
    private fun fetchdata(){
        val callsArray= ArrayList<Call>()

        val allCalls: Uri = Uri.parse("content://call_log/calls")
        val c: Cursor = managedQuery(allCalls, null, null, null, null)
        while(c.moveToNext() ){
        val num: String = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)) // for  number

        var name: String? = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME)) // for name

        val duration: String = c.getString(c.getColumnIndex(CallLog.Calls.DURATION)) // for duration
        val date: String = c.getString(c.getColumnIndex(CallLog.Calls.DATE)) // for date

        val type: Int = c.getString(c.getColumnIndex(CallLog.Calls.TYPE))
            .toInt() // for call type, Incoming or out going.
            val Stype:String = if(type==1){
                    "Incoming"
                } else{
                    "Outgoing"
                }
        if(name==null){
            name="UNKNOWN"
        }

        val call= Call(
            name,num,duration,Stype,date
        )
        callsArray.add(call)
        }

        mAdapter.updateNews(callsArray)
    }
    fun onItemClicked(item: Call){
        val date= item.id
        val intent= Intent(this,NoteTakingActivity::class.java)
        intent.putExtra(NoteTakingActivity.Id,date)
        startActivity(intent)
    }
}