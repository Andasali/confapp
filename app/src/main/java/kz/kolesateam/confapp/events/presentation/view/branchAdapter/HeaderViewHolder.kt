package kz.kolesateam.confapp.events.presentation.view.branchAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val userNameTextView: TextView = itemView.findViewById(R.id.header_layout_username)

    fun bind(userName: String){
        userNameTextView.text = userName
    }
}