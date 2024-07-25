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
import com.example.lokalappassignment.models.SavedJobResult



class JobsAdapter2(private var jobs: List<SavedJobResult>, private val context: Context) :
    RecyclerView.Adapter<JobsAdapter2.JobViewHolder>() {

    class JobViewHolder(val binding: JobItemviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        with(holder.binding) {

            jobTitle.text = job.title
            location.text = job.location
            salary.text = job.salary
            phone.text = job.phone

        }
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context, JobDetailsActivity::class.java).putExtra("id",job.id).putExtra("status","2"))
        }
    }

    override fun getItemCount() = jobs.size

    fun updateJobs(newJobs: List<SavedJobResult>) {
        jobs = newJobs
        notifyDataSetChanged()
    }
}
