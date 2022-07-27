package jp.co.yumemi.android.codeCheck.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import jp.co.yumemi.android.codeCheck.R

class ItemListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<Item, ItemListViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    interface OnItemClickListener {
        fun itemClick(item: Item)
    }

    /**
     * View を生成する
     * 生成した View は ViewHolder に格納する
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ItemListViewHolder(view)
    }

    /**
     * その位置の Item を取得し、ViewHolder を通じて View に Item情報をセット
     */
    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text = item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}

/**
 * 生成した View を保持する
 */
class ItemListViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

}

/**
 * Item の差分確認
 */
val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
    // 渡されたデータが同じ項目であるか確認する
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }

    // 渡されたデータが同じ値であるか確認する
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
