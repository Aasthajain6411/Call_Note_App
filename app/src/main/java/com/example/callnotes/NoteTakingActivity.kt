package com.example.callnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_note_taking.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class NoteTakingActivity : AppCompatActivity() {
    lateinit var reasonOfCalling: EditText
    lateinit var discussion:EditText
    lateinit var addUpdateBtn: Button
    lateinit var viewModel: NoteViewModel
    lateinit var addUpdate:String
    companion object{
        const val Id="id"
    }
    private val scope = CoroutineScope(newSingleThreadContext("name"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_taking)

        reasonOfCalling = findViewById(R.id.reason_of_calling)
        discussion= findViewById(R.id.discussion_details)
        addUpdateBtn=findViewById(R.id.submitButton)

        val id= intent.getStringExtra(Id)

        viewModel=ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
//        viewModel.allNotes.observe(this, Observer {
//
//        })
        addUpdate="Save"
        var noted:Note?
        scope.launch{noted= id?.let { viewModel.retrieveNote(it) }
            //This will only run when the variable is not null. If we want for when it is null then we remove ?
            noted?.let {
                if(it.text?.isNotEmpty() == true){
                reasonOfCalling.setText(it.text)
                discussion.setText(it.discuss)
                addUpdate="Edit"
                }
            }
        }
    }

    fun submitData(view: View) {

        val noteText= reason_of_calling.text.toString()
        val discussText=discussion_details.text.toString()
        val id= intent.getStringExtra(Id)
        //&& discussText.isNotEmpty()
        if(noteText.isNotEmpty() ){
            if(addUpdate=="Save"){
                id?.let {
                    viewModel.insertNote(Note(noteText,discussText,id))
                    Toast.makeText(this, "Note Saved",Toast.LENGTH_LONG).show()
                }
            }
            else{
                id?.let {
                    viewModel.updateNote(Note(noteText,discussText,id))
                    Toast.makeText(this, "Note Updated",Toast.LENGTH_LONG).show()
                }
            }

        }
        else{
            Toast.makeText(this, "Please fill the required fields!",Toast.LENGTH_LONG).show()
        }
    }
}