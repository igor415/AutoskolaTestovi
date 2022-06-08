package com.varivoda.igor.autokola_testovi2019.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.databinding.TestItemBinding

class HomeAdapter(private val homeClickListener: HomeClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(TestEntityDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    fun addHeaderAndSubmitList(list: List<TestEntity>?) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> list.map { DataItem.TestEntityItem(it) }
        }
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> TestEntityViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.TestEntityItem -> ITEM_VIEW_TYPE_ITEM
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TestEntityViewHolder -> {
                val nightItem = getItem(position) as DataItem.TestEntityItem
                holder.bind(nightItem.testEntity, homeClickListener)
            }
        }
        //holder.bind(getItem(position), homeClickListener)
    }

    class TestEntityViewHolder private constructor(val binding: TestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestEntity, homeClickListener: HomeClickListener){
            binding.test = item
            binding.clickListener = homeClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TestEntityViewHolder {
                val binding = TestItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
                return TestEntityViewHolder(binding)
            }
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_text, parent, false)
                return TextViewHolder(view)
            }
        }
    }
}

class TestEntityDiffCallback() : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}