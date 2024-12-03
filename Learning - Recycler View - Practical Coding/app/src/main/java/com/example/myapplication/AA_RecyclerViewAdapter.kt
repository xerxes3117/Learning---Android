package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerViewRowBinding

class AARecyclerViewAdapter(
    private val aminoAcidList: ArrayList<AminoAcidModel>
) : RecyclerView.Adapter<AARecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        val view = RecyclerViewRowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.aminoAcidName.text = aminoAcidList[position].name
        holder.aminoAcidAbbreviation.text = aminoAcidList[position].abbreviation
        holder.aminoAcidSymbol.text = aminoAcidList[position].symbol
        holder.aminoAcidImage.setImageResource(aminoAcidList[position].image)
    }

    override fun getItemCount(): Int {
        return aminoAcidList.size
    }

    class ViewHolder(private val binding: RecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val aminoAcidName: TextView = binding.aminoAcidTextView
        val aminoAcidAbbreviation: TextView = binding.aminoAcidAbbView
        val aminoAcidSymbol: TextView = binding.aminoAcidAbbSmallView
        val aminoAcidImage: ImageView = binding.aminoAcidImageView
    }

}