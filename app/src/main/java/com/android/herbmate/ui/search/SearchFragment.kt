package com.android.herbmate.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.R
import com.android.herbmate.databinding.FragmentSearchBinding
import com.android.herbmate.Plant
import com.android.herbmate.PlantAdapter
import com.android.herbmate.ui.search.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapter
    private var listPlant = ArrayList<Plant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val plantNames = resources.getStringArray(R.array.name_plant)
        val plantImages = resources.obtainTypedArray(R.array.image_plant)

        listPlant.clear()

        for (i in plantNames.indices) {
            listPlant.add(
                Plant(
                    name = plantNames[i],
                    image = plantImages.getResourceId(i, -1)
                )
            )
        }
        plantImages.recycle()


//        binding.recyclerViewFilters.apply  {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            adapter = PlantAdapter(requireContext(), listPlant)
//        }
//
//        binding.recyclerViewOther.apply  {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            adapter = PlantAdapter(requireContext(), listPlant)
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
