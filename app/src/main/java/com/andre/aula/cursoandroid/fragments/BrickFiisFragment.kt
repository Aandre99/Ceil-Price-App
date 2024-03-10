
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
import com.andre.aula.cursoandroid.MainActivity
import com.andre.aula.cursoandroid.R
import com.andre.aula.cursoandroid.databinding.FragmentBrickFiisBinding

class BrickFiisFragment : Fragment() {

    private lateinit var binding: FragmentBrickFiisBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBrickFiisBinding.inflate(inflater, container, false)
        binding.calcFiiButton.setOnClickListener {
            if (checkFilledEdits()) {

                val dividend = binding.dividendTextInputEditText1.text.toString().toDouble()
                val k = binding.KTextInputEditText.text.toString().toDouble() / 100.0
                val g = binding.GTextInputEditText.text.toString().toDouble() / 100.0
                val currentPrice = binding.currentCotationextInputEditText.text.toString().toDouble()

                val d1 = dividend * (1 + g)
                val result = d1 / (k - g)

                (activity as MainActivity).showResultDialog(result, currentPrice, "FII de Tijolo")
            }
            else {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.paramsInfotextView.setOnClickListener {

            val dialogBinding = layoutInflater.inflate(R.layout.custom_popup_acoes, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            myDialog.findViewById<TextView>(R.id.gordon_formulasTxt).text = "Formula de Gordon para FIIs de Tijolo"
            myDialog.findViewById<ImageView>(R.id.eqn_imageView).setImageResource(R.drawable.fiiseqn)
            myDialog.findViewById<ImageView>(R.id.paramsexplaineqn_imageView).setImageResource(R.drawable.fiisparamsexplaineqn)

            myDialog.show()
        }
        return binding.root
    }

    private fun checkFilledEdits(): Boolean {
        return listOf(
            binding.dividendTextInputEditText1,
            binding.KTextInputEditText,
            binding.GTextInputEditText,
            binding.currentCotationextInputEditText
        ).all { it.text?.isNotEmpty() == true }
    }

}