package com.andre.aula.cursoandroid.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.andre.aula.cursoandroid.MainActivity
import com.andre.aula.cursoandroid.R
import com.andre.aula.cursoandroid.databinding.FragmentStocksBinding


class StocksFragment : Fragment() {
    private lateinit var binding: FragmentStocksBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentStocksBinding.inflate(inflater, container, false)
        binding.calcButton.setOnClickListener {

            if (checkFilledEdits()){

                val dividendMean = binding.dividendTextInputEditText.text.toString().toDouble()
                val rentability = binding.rentTextInputEditText.text.toString().toDouble().div(100)
                val currentPrice = binding.currentPriceTextEdit.text.toString().toDouble()

                val ceilPrice = dividendMean / rentability
                (activity as MainActivity).showResultDialog(ceilPrice, currentPrice, "Ação")
            }else {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.infoParamstextView.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.custom_popup_acoes, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            myDialog.findViewById<ImageView>(R.id.eqn_imageView).setImageResource(R.drawable.eqn_acoes)
            myDialog.findViewById<ImageView>(R.id.paramsexplaineqn_imageView).setImageResource(R.drawable.paramsexplaineqn)


            myDialog.show()

        }
        return binding.root
    }

    private fun  checkFilledEdits(): Boolean {
        return binding.dividendTextInputEditText.text?.isNotEmpty() == true &&
                binding.rentTextInputEditText.text?.isNotEmpty() == true &&
                binding.currentPriceTextEdit.text?.isNotEmpty() == true
    }
}