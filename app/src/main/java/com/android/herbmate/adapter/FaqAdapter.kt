//package com.android.herbmate.adapter
//
//import android.R
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.android.herbmate.data.response.FaqResponse
//import com.android.herbmate.databinding.ItemFaqBinding
//
//class FaqAdapter(private val onItemSelected: (String, String) -> Unit) : ListAdapter<FaqResponse, FaqAdapter.ListViewHolder>(DIFF_CALLBACK) {
//
//
//    private var listTanaman: List<String> = emptyList()
//    class ListViewHolder(private val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(faq: FaqResponse, onItemSelected: (String, String) -> Unit) {
//            binding.tvFaq.text = faq.message
//
//            val tanamanList: List<String> = faq.listTanaman?.filterNotNull() ?: emptyList()
//            // Set up the Spinner for selecting questions
//            val spinnerAdapter = ArrayAdapter(binding.root.context, R.layout.simple_spinner_item, tanamanList)
//            binding.spinnerDropdown.adapter = spinnerAdapter
//
//            binding.spinnerDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                    val selectedQuestion = faq.listTanaman?.get(position)
//                    // Load parameters for the selected question
//                    if (selectedQuestion != null) {
//                        loadParameters(selectedQuestion, binding.rvList, onItemSelected)
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {}
//            }
//        }
//
//        private fun loadParameters(selectedQuestion: String, recyclerView: RecyclerView, onItemSelected: (String, String) -> Unit) {
//            // Load parameters based on the selected question and set up the nested RecyclerView
//            val parameters = getParametersForQuestion(selectedQuestion) // Implement this method to get parameters
//            val parameterAdapter = ParameterAdapter { selectedParameter ->
//                // Handle parameter selection
//                onItemSelected(selectedQuestion, selectedParameter)
//            }
//            recyclerView.adapter = parameterAdapter
//            recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
//            parameterAdapter.submitList(parameters)
//            recyclerView.visibility = View.VISIBLE // Show the RecyclerView for parameters
//        }
//
//        private fun getParametersForQuestion(question: String): List<String> {
//            // Return a list of parameters based on the question
//            // This is a placeholder; implement your logic to fetch parameters
//            return listOf("Parameter 1", "Parameter 2", "Parameter 3") // Example parameters
//        }
//    }
//
//    fun updateListTanaman(newList: List<String>?) {
//        if (newList != null) {
//            listTanaman = newList
//            notifyDataSetChanged() // Notify the adapter to refresh the UI
//        } else {
//            // Handle the case where newList is null
//            listTanaman = emptyList() // or any default behavior
//            notifyDataSetChanged()
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
//        val binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val faq = getItem(position)
//        holder.bind(faq, onItemSelected) // Pass the onItemSelected callback here
//    }
//
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FaqResponse>() {
//            override fun areItemsTheSame(oldItem: FaqResponse, newItem: FaqResponse): Boolean {
//                return oldItem.kategori3a == newItem.kategori3a // Adjust based on your unique identifier
//            }
//
//            override fun areContentsTheSame(oldItem: FaqResponse, newItem: FaqResponse): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}