package com.example.lokalappassignment.activities

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lokalappassignment.applications.JobApplication
import com.example.lokalappassignment.databinding.ActivityJobDetailsBinding
import com.example.lokalappassignment.repository.JobRepository
import com.example.lokalappassignment.viewmodels.JobsViewModel
import com.example.lokalappassignment.viewmodels.JobsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class JobDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailsBinding
    private lateinit var viewModel: JobsViewModel
    private var jobId: Int? = null
    private lateinit var repository: JobRepository
    private var phone="Not Available"
    private lateinit var status: String
    private var id by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        jobId = intent.getIntExtra("id", -1)
        status= intent.getStringExtra("status").toString()
        if(status=="1"){
            binding.floatingActionButtonAddJob.visibility = View.VISIBLE
            binding.floatingActionButtonRemoveJob.visibility = View.GONE
        }
        else{
            binding.floatingActionButtonRemoveJob.visibility = View.VISIBLE
            binding.floatingActionButtonAddJob.visibility = View.GONE
        }

        if (jobId == -1) {
            Log.e("JobDetailsActivity", "No Job ID provided")
            return
        }

        val application = application as JobApplication
        repository = application.repository
        val factory = JobsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[JobsViewModel::class.java]
        viewModel.jobAddStatus.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.jobDetail.observe(this) { jobDetail ->


            Log.d("JobDetailsCheck","enter")
            if (jobDetail != null) {
                // Bind job details
                binding.textViewTitle.text = jobDetail.title
                binding.textViewCompanyName.text = jobDetail.company_name
                binding.textViewJobType.text = jobDetail.primary_details.Job_Type
                binding.textViewSalary.text = jobDetail.primary_details.Salary
                binding.textViewExperience.text = jobDetail.primary_details.Experience
                binding.textViewQualification.text = jobDetail.primary_details.Qualification
                binding.textViewLocation.text = jobDetail.primary_details.Place

                phone= jobDetail.whatsapp_no
                id=jobDetail.id


                Glide.with(this)
                    .load(jobDetail.creatives.firstOrNull()?.file)
                    .into(binding.imageViewJob)


                binding.buttonCallHR.setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:${jobDetail.custom_link.replace("tel:", "")}")
                    startActivity(callIntent)
                }

                binding.buttonWhatsApp.setOnClickListener {
                    val whatsappIntent = Intent(Intent.ACTION_VIEW)
                    whatsappIntent.data = Uri.parse(jobDetail.contact_preference.whatsapp_link)
                    startActivity(whatsappIntent)
                }
            } else {
                Log.e("JobDetailsActivity", "JobDetail is null")
            }
        }

        Log.d("JobDetailsActivity", "Calling fetchJobById for jobId: $jobId")
        jobId?.let {
            viewModel.fetchJobById(it)
        }
    }
    fun onSaveJob(view: View) {
        viewModel.addJob(id, binding.textViewTitle.text.toString(),binding.textViewLocation.text.toString(),binding.textViewSalary.text.toString(),phone)
    }

    fun onRemoveJob(view: View) {
        viewModel.deleteJob(id)
        Toast.makeText(this,"Job Removed Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

}

