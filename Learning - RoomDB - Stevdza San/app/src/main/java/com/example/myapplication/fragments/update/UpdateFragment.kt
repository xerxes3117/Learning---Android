package com.example.myapplication.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUpdateBinding
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.UserViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        _binding!!.etFirstUpdate.setText(args.currentUser.firstName)
        _binding!!.etLastUpdate.setText(args.currentUser.lastName)
        _binding!!.etAgeUpdate.setText(args.currentUser.age.toString())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        _binding!!.buttonUpdate.setOnClickListener {
            updateUser()
        }

        return _binding!!.root
    }

    private fun updateUser(){
        val fName = _binding?.etFirstUpdate?.text.toString()
        val lName = _binding?.etLastUpdate?.text.toString()
        val age = Integer.parseInt(_binding?.etAgeUpdate?.text.toString())

        if(inputCheck(fName, lName, _binding?.etAgeUpdate?.text)){
            val updatedUser = User(args.currentUser.id, fName, lName, age)
            // Update user in db
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(fName: String, lName: String, age: Editable?): Boolean {
        if (age != null) {
            return !(TextUtils.isEmpty(fName) && TextUtils.isEmpty(lName) && age.isEmpty())
        } else {
            return false
        }
    }
}