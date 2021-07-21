package com.ebookfrenzy.roomdemo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ebookfrenzy.roomdemo.Product
import com.ebookfrenzy.roomdemo.R

class ProductListAdapter(private val productItemLayout: Int) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var productList: List<Product>? = null

    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val item = holder.item
        productList.let {
            item.text = it!![listPosition].productName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            productItemLayout, parent, false)
        return ViewHolder(view)
    }

    fun setProductList(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: TextView = itemView.findViewById(R.id.product_row)
    }
}