package com.example.allinoneapp.utilClass

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allinoneapp.databinding.SingleItemBinding
import com.example.allinoneapp.model.Language

class RvAdapter(
    private var languageList: List<Language>,
    private var context: Context
) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)
    lateinit var inAdapter: InAdapter
    var expand_main_symbolsJ=true
    var expand_main_symbolsK=true
    var expand_main_symbolsP=true
    var expand_main_symbolsC=true
    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // bind the items with each item of the list languageList which than will be
    // shown in recycler view
    // to keep it simple we are not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(languageList[position]){

                binding.tvSymbol.text = this.symbol
//               val layoutManager = LinearLayoutManager(
//                    context,
//                    LinearLayoutManager.HORIZONTAL, false
//                )
//                binding.innerRecyclerview.layoutManager = layoutManager
                binding.innerRecyclerview.layoutManager = LinearLayoutManager(context)
                inAdapter= InAdapter(context,languageList)
                binding.innerRecyclerview.adapter = inAdapter
//                binding.innerRecyclerview.setHasFixedSize(true)
//
//                binding.tvName.text=this.name
//                binding.tvDescription.text = this.description
                // check if boolean property "extend" is true or false
                // if it is true make the "extendedView" Visible
//                binding.expandedViewName.visibility = if (this.expand_main_symbol) View.VISIBLE else View.GONE
//                binding.expandedViewDescription.visibility = if (this.expand_symbol) View.VISIBLE else View.GONE
                // on Click of the item take parent card view in our case
                // revert the boolean "expand"


                    binding.tvSymbol.setOnClickListener {
                        if(this.symbol=="J") {
                            binding.expandedViewName.visibility = if (expand_main_symbolsJ) View.VISIBLE else View.GONE
                            expand_main_symbolsJ = !expand_main_symbolsJ
//                            this.expand_name = false
//                            this.expand_symbol = false
                            notifyDataSetChanged()

                        }
                        else if (this.symbol=="K") {
                            binding.expandedViewName.visibility = if (expand_main_symbolsK) View.VISIBLE else View.GONE
                            expand_main_symbolsK = !expand_main_symbolsK
//                            this.expand_name = false
//                            this.expand_symbol = false
                            notifyDataSetChanged()
//                            Log.e("adaptor", inAdapter.toString())

                        }
                        else if (this.symbol=="P") {
                            binding.expandedViewName.visibility = if (expand_main_symbolsP) View.VISIBLE else View.GONE
                            expand_main_symbolsP = !expand_main_symbolsP
                            this.expand_name = false
                            this.expand_symbol = false
                            notifyDataSetChanged()

                        }
                        else if (this.symbol=="C") {
                            binding.expandedViewName.visibility = if (expand_main_symbolsC) View.VISIBLE else View.GONE
                            expand_main_symbolsC = !expand_main_symbolsC
                            this.expand_name = false
                            this.expand_symbol = false
                            notifyDataSetChanged()

                        }

                    }
//                binding.expandedViewName.setOnClickListener {
//                    this.expand_symbol = !this.expand_symbol
//                    notifyDataSetChanged()
//                }

            }

        }

    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return languageList.size
    }
}