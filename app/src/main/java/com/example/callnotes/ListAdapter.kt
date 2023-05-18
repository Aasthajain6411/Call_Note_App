package com.example.callnotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date
import java.text.SimpleDateFormat


class ListAdapter( private val listener: MainActivity): RecyclerView.Adapter<CallViewHolder>() {
    private val items:ArrayList<Call> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_call,parent,false)
        val viewHolder= CallViewHolder(view)
        //what will happen when we click the view (this should be responsibility of the activity not of the adapter)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val currentItem = items[position]
        holder.name.text =currentItem.name
        holder.phoneNumber.text=currentItem.number
        holder.duration.text=currentItem.duration +'s'
        holder.type.text= currentItem.type
        val dateString=currentItem.id.toLong()
        val formatter = SimpleDateFormat("dd-MM-yy HH:mm")
        val dateStringFinal: String = formatter.format(Date(dateString))
        holder.date.text=dateStringFinal
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateNews(updatedNews: ArrayList<Call>){
        items.clear()
        items.addAll(updatedNews)
        // will call all three above functions again
        //first getitemcount then create then onbind
        notifyDataSetChanged()
    }

}
class CallViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val name:TextView=itemView.findViewById(R.id.Name)
    val phoneNumber:TextView =itemView.findViewById(R.id.PhoneNumber)
    val duration: TextView =itemView.findViewById(R.id.Duration)
    val type: TextView=itemView.findViewById(R.id.Type)
    val date: TextView=itemView.findViewById(R.id.timeStamp)
}
interface ItemClicked {
    fun onItemClicked(item :Call)

}