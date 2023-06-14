package com.example.advweek4.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.advweek4.R
import com.example.advweek4.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit


class StudentListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)
        val recView = view?.findViewById<RecyclerView>(R.id.recView)
        val txtError = view?.findViewById<TextView>(R.id.txtError)
        val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recView?.layoutManager = LinearLayoutManager(context)
        recView?.adapter = studentListAdapter

        refreshLayout?.setOnRefreshListener {
            recView?.visibility = View.GONE
            txtError?.visibility = View.GONE
            progressLoad?.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    @SuppressLint("CheckResult")
    fun observeViewModel(){

        val recView = view?.findViewById<RecyclerView>(R.id.recView)
        val txtError = view?.findViewById<TextView>(R.id.txtError)
        val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)
        var btnDetail = view?.findViewById<Button>(R.id.btnDetail)

        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
            var student = it
//            btnDetail?.setOnClickListener {
//                Observable.timer(5, TimeUnit.SECONDS)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        Log.d("Messages", "five seconds")
//                        MainActivity.showNotification(
//                            view?.findViewById<TextView>(R.id.txtName).toString(),
//                            "A new notifidcation created",
//                            R.drawable.baseline_circle_24
//                        )
//                    }
//                }
            })

        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                txtError?.visibility = View.VISIBLE
            } else {
                txtError?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                recView?.visibility = View.GONE
                progressLoad?.visibility = View.VISIBLE
            } else {
                recView?.visibility = View.VISIBLE
                progressLoad?.visibility = View.GONE
            }
        })

    }
}