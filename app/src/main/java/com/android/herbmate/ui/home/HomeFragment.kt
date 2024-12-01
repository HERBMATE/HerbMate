package com.android.herbmate.ui.home

import com.android.herbmate.databinding.FragmentHomeBinding
import com.android.herbmate.ui.home.HomeViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_CANCELED
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.Plant
import com.android.herbmate.PlantAdapter
import com.android.herbmate.PlantAdapterHome
import com.android.herbmate.R
import com.android.herbmate.ui.detail.DetailActivity
import java.io.File
import com.yalantis.ucrop.UCrop

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapterHome
    private var listPlant = ArrayList<Plant>()

    // Declare ViewModel as a property
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
            adapter = PlantAdapterHome(requireContext(), listPlant)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
