package com.example.allinoneapp.utilClass

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.allinoneapp.databinding.InnerItemBinding
import com.example.allinoneapp.model.Language

class InAdapter(
    context: Context,
    private var languageList: List<Language>
) : RecyclerView.Adapter<InAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: InnerItemBinding) : RecyclerView.ViewHolder(binding.root)
        var symbolsJ:Boolean=false
    var symbolsK:Boolean=false
    var symbolsP:Boolean=false
    var symbolsC:Boolean=false
    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    // bind the items with each item of the list languageList which than will be
    // shown in recycler view
    // to keep it simple we are not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(languageList[position]){
                // set name of the language from the list
                binding.tvSymbol.text = this.symbol
                // set description to the text
                // since this is inside "expandedView" its visibility will be gone initially
                // after click on the item we will make the visibility of the "expandedView" visible
                // which will also make the visibility of desc also visible
                Log.e("symbol",this.symbol)
                binding.tvName.text=this.name
                binding.tvDescription.text = this.description
                // check if boolean property "extend" is true or false
                // if it is true make the "extendedView" Visible
                if (this.symbol=="J") {
                    binding.expandedViewName.visibility = if (symbolsJ) View.VISIBLE else View.GONE
                }
                else if (this.symbol=="K") {
                    binding.expandedViewName.visibility = if (symbolsK) View.VISIBLE else View.GONE
                }
                else if (this.symbol=="P") {
                    binding.expandedViewName.visibility = if (symbolsP) View.VISIBLE else View.GONE
                }
                else if (this.symbol=="C") {
                    binding.expandedViewName.visibility = if (symbolsC) View.VISIBLE else View.GONE
                }
                binding.expandedViewDescription.visibility = if (this.expand_symbol) View.VISIBLE else View.GONE
                // on Click of the item take parent card view in our case
                // revert the boolean "expand"
                binding.tvSymbol.setOnClickListener {
                    if (this.symbol=="J") {
                        symbolsJ = !symbolsJ
//                        this.expand_symbol = false
                        notifyDataSetChanged()
//                    this.expand_name = !this.expand_name
                    }
                    else if (this.symbol=="K") {
                        symbolsK=!symbolsK
//                        this.expand_symbol = false
                        notifyDataSetChanged()
                    }
                    else if (this.symbol=="P") {
                        symbolsP=!symbolsP
//                        this.expand_symbol = false
                        notifyDataSetChanged()
                    }
                    else if (this.symbol=="C") {
                        symbolsC=!symbolsC
//                        this.expand_symbol = false
                        notifyDataSetChanged()
                    }
                }
                binding.expandedViewName.setOnClickListener {
                    this.expand_symbol = !this.expand_symbol
                    notifyDataSetChanged()
                }
            }
        }

    }
    // return the size of languageList
    override fun getItemCount(): Int {
        return languageList.size
    }

}