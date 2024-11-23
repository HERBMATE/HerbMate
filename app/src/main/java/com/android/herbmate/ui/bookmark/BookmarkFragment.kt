package com.android.herbmate.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.Plant
import com.android.herbmate.PlantAdapter
import com.android.herbmate.R
import com.android.herbmate.databinding.FragmentBookmarkBinding
import com.android.myapplication.herbmate.ui.bookmark.BookmarkViewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var adapter: PlantAdapter
    private var listPlant = ArrayList<Plant>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookmarkViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
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

        binding.recyclerViewFilters.apply  {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = PlantAdapter(requireContext(), listPlant)
        }

        binding.recyclerViewOther.apply  {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = PlantAdapter(requireContext(), listPlant)
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}