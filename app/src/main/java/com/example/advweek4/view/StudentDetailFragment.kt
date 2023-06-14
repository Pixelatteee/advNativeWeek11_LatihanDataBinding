package com.example.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.advweek4.R
import com.example.advweek4.databinding.FragmentStudentDetailBinding
import com.example.advweek4.databinding.StudentListItemBinding
import com.example.advweek4.model.Student
import com.example.advweek4.util.loadImage
import com.example.advweek4.viewmodel.DetailViewModel
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class StudentDetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentStudentDetailBinding

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
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_detail, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = ""
        arguments.let{
            val studentID = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId
            id = "$studentID"
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(id)

        observeViewModel()
    }

    fun observeViewModel(){

        val ID = view?.findViewById<TextInputEditText>(R.id.txtID)
        val studentName = view?.findViewById<TextInputEditText>(R.id.txtName)
        val studentBOD = view?.findViewById<TextInputEditText>(R.id.txtBod)
        val studentPhone = view?.findViewById<TextInputEditText>(R.id.txtPhone)
        val photoURl = view?.findViewById<ImageView>(R.id.imageView3)

        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
//            ID?.setText(viewModel.studentLD.value?.id)
//            studentName?.setText(viewModel.studentLD.value?.name)
//            studentBOD?.setText(viewModel.studentLD.value?.dob)
//            studentPhone?.setText(viewModel.studentLD.value?.phone)
//            photoURl?.loadImage(viewModel.studentLD.value?.photoUrl)

            dataBinding.student = it
        })
    }
}