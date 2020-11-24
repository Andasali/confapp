package kz.kolesateam.confapp.events.presentation.view.branchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.EventClickListener

class BranchAdapter(
    private val eventClickListener: EventClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val branchApiDataList: MutableList<UpcomingEventListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            1 -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_layout,
                    parent,
                    false
                )
            )
            else -> BranchViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.branch_item,
                    parent,
                    false
                ),
                eventClickListener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder){
            holder.bind(branchApiDataList[position].data as String)
        }

        if(holder is BranchViewHolder){
            holder.bind(branchApiDataList[position].data as BranchApiData)
        }
    }

    override fun getItemCount(): Int = branchApiDataList.size

    override fun getItemViewType(position: Int): Int {
        return branchApiDataList[position].type
    }

    fun setList(branchApiDataList: List<UpcomingEventListItem>){
        this.branchApiDataList.clear()
        this.branchApiDataList.addAll(branchApiDataList)
        notifyDataSetChanged()
    }
}