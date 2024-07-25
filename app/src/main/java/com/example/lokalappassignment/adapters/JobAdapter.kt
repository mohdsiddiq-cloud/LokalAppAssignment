package com.example.lokalappassignment.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalappassignment.activities.JobDetailsActivity
import com.example.lokalappassignment.databinding.JobItemviewBinding
import com.example.lokalappassignment.models.Result


class JobsAdapter(private var jobs: List<Result>, private val context: Context) :
    RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    init {

        jobs = jobs.filter { job ->
            !job.title.isNullOrEmpty() &&
                    !job.primary_details?.Place.isNullOrEmpty() &&
                    !job.primary_details?.Salary.isNullOrEmpty() &&
                    !job.whatsapp_no.isNullOrEmpty()
        }
    }

    class JobViewHolder(val binding: JobItemviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        with(holder.binding) {

            jobTitle.text = job.title
            jobTitle.visibility = if (job.title.isNullOrEmpty()) View.GONE else View.VISIBLE


            if (!job.primary_details?.Place.isNullOrEmpty()) {
                location.text = job.primary_details?.Place
                location.visibility = View.VISIBLE
            } else {
                location.visibility = View.GONE
            }


            if (!job.primary_details?.Salary.isNullOrEmpty()) {
                salary.text = job.primary_details?.Salary
                salary.visibility = View.VISIBLE
            } else {
                salary.visibility = View.GONE
            }


            if (!job.whatsapp_no.isNullOrEmpty()) {
                phone.text = job.whatsapp_no
                phone.visibility = View.VISIBLE
            } else {
                phone.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, JobDetailsActivity::class.java).putExtra("id", job.id).putExtra("status","1"))
        }
    }

    override fun getItemCount() = jobs.size

    fun updateJobs(newJobs: List<Result>) {
        jobs = newJobs.filter { job ->
            !job.title.isNullOrEmpty() &&
                    !job.primary_details?.Place.isNullOrEmpty() &&
                    !job.primary_details?.Salary.isNullOrEmpty() &&
                    !job.whatsapp_no.isNullOrEmpty()
        }
        notifyDataSetChanged()
    }
}
