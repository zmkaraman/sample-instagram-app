package com.example.sampleapp.ui.master

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.AppUtil
import com.example.sampleapp.R
import com.example.sampleapp.databinding.FragmentMasterBinding

class MasterFragment : Fragment() {

    lateinit var binding : FragmentMasterBinding

    private val graphViewModel: InsGraphViewModel by lazy {
        ViewModelProvider(this).get(InsGraphViewModel::class.java)
    }

    private var viewModelAdapter: MediaDataListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_master, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = graphViewModel

        setHasOptionsMenu(true)

        //Add binding values
        viewModelAdapter = MediaDataListAdapter(MediaDataListAdapter.MediaDataListener { mediaData ->
            view?.let {
                Navigation.findNavController(it).navigate(MasterFragmentDirections.actionMasterFragmentToDetailFragment(mediaData))
            }
        })


        binding.recyclerViewMedia.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMedia.adapter = viewModelAdapter


        graphViewModel.getUserProfile()
        graphViewModel.getUserMediaData()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        graphViewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                //TODO MERVE aksiyon ?
            }
        })


        graphViewModel.mediaData.observe(viewLifecycleOwner, { mediaData ->
            mediaData?.apply {
                viewModelAdapter?.submitList(this)
                viewModelAdapter?.notifyDataSetChanged()
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
               // TODO MERVE instagram logout servisi
                AppUtil.resetLoginResponse()
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
}