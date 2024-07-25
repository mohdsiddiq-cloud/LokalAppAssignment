package com.example.lokalappassignment.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokalappassignment.R
import com.example.lokalappassignment.adapters.JobsAdapter
import com.example.lokalappassignment.adapters.JobsAdapter2
import com.example.lokalappassignment.applications.JobApplication
import com.example.lokalappassignment.databinding.FragmentBookmarksBinding
import com.example.lokalappassignment.databinding.FragmentJobsBinding
import com.example.lokalappassignment.viewmodels.JobsViewModel
import com.example.lokalappassignment.viewmodels.JobsViewModelFactory

class BookmarksFragment : Fragment() {


    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobsAdapter: JobsAdapter2
    private lateinit var viewModel: JobsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE
        binding.text.visibility = View.GONE

        val application = requireActivity().application as JobApplication
        val repository = application.repository
        val factory = JobsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(JobsViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsAdapter = JobsAdapter2(emptyList(),requireContext()) // Initialize with empty list
        binding.recyclerView.adapter = jobsAdapter

        viewModel.savedJobs.observe(viewLifecycleOwner, Observer { jobResponse ->
            if (jobResponse != null) {
                Log.d("checkHere2", "JobResponse received: ${jobResponse.size} items")
                if(jobResponse.size==0){
                    binding.text.visibility = View.VISIBLE
                }
                else{
                    binding.text.visibility = View.GONE
                }
                jobsAdapter.updateJobs(jobResponse)
                Log.d("checkHere2", "Data received and adapter updated")
            } else {
                Log.d("checkHere2", "No data received")
            }
            binding.progressBar.visibility = View.GONE
        })
        viewModel.getSavedJobs()
        Log.d("checkHere2", "getSavedJobs called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}