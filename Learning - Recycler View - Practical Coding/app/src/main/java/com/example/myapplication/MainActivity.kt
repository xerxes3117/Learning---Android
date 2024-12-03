package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val aminoAcidList = ArrayList<AminoAcidModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Create dataset to be used in the RecyclerView
        setupAminoAcidModels()

        with(binding) {
            mRecyclerView.adapter = AARecyclerViewAdapter(aminoAcidList)
            mRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupAminoAcidModels() {
        val aminoAcidNames = resources.getStringArray(R.array.amino_acids)
        val aminoAcidAbs = resources.getStringArray(R.array.animo_acids_word_abbreviation)
        val aminoAcidAbsSmall = resources.getStringArray(R.array.animo_acids_abbreviation)

        for (i in aminoAcidNames.indices) {
            val aminoAcid = AminoAcidModel(
                aminoAcidNames[i],
                aminoAcidAbs[i],
                aminoAcidAbsSmall[i],
                R.drawable.baseline_10k_24
            )
            aminoAcidList.add(aminoAcid)
        }
    }
}