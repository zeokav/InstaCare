package edu.gatech.instacareplus.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.R


class SuppliesAdapter (val supplyList: ArrayList<SupplyItem>):
    RecyclerView.Adapter<SuppliesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.cards_supply, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return supplyList.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.name?.text = supplyList[p1].itemName
        p0.desc?.text = supplyList[p1].itemDesc
        p0.price?.text = supplyList[p1].itemPrice
        p0.qty?.text = supplyList[p1].itemQty
        p0.deleteButton.setOnClickListener(View.OnClickListener { view ->
            supplyList.removeAt(p1)
            notifyItemRemoved(p1)
            notifyItemRangeChanged(p1, supplyList.size)
        })

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.itemName)
        val desc = itemView.findViewById<TextView>(R.id.desc)
        val price = itemView.findViewById<TextView>(R.id.available)
        val qty = itemView.findViewById<TextView>(R.id.available)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
    }
}