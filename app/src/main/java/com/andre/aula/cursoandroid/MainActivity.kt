package com.andre.aula.cursoandroid

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.andre.aula.cursoandroid.databinding.ActivityMainBinding
import com.andre.aula.cursoandroid.fragments.StocksFragment
import com.andre.aula.cursoandroid.fragments.BrickFiisFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var darkModeEnable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

            AdapterView.OnItemClickListener { parent, _, position, _ ->

                val selectedItem = parent.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "Ações" -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(binding.fragmentContainerView.id, StocksFragment())
                            addToBackStack(null)
                            commit()
                        }
                    }
                    "FIIs de Tijolo" -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(binding.fragmentContainerView.id, BrickFiisFragment())
                            addToBackStack(null)
                            commit()
                        }
                    }
                }
            }

        changeFragment(StocksFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it) {
                R.id.stockItem -> {
                   changeFragment(StocksFragment())
                }
                R.id.brickItem -> {
                    changeFragment(BrickFiisFragment())
                }
                R.id.paperItem -> {
                   changeFragment(StocksFragment())
                }
            }
        }

        binding.darkModeButton.setOnClickListener{

            if (darkModeEnable) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            darkModeEnable = !darkModeEnable
        }
    }

    fun showResultDialog(ceilPrice: Double, currentPrice: Double, typeOfAsset: String) {

        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_ceilprice, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val buyingOpportunity  = (currentPrice <= ceilPrice)
        println("ceilPrice: $ceilPrice, currentPrice: $currentPrice, buyingOpportunity: $buyingOpportunity")

        val color = if (buyingOpportunity) ContextCompat.getColor(
            this,
            R.color.my_green
        ) else ContextCompat.getColor(this, R.color.my_red)

        val message = if (buyingOpportunity) "$typeOfAsset descontada, Compre!" else "$typeOfAsset acima do valor Teto, Não compre!"
        val resultMessage = "Preço Teto : R$ %.2f".format(ceilPrice) + "\n" + "Preço Atual: R$ %.2f".format(currentPrice)

        myDialog.findViewById<TextView>(R.id.dialogtitleTextView).text = "Preço Teto $typeOfAsset"
        myDialog.findViewById<TextView>(R.id.ceilPriceTV).text = resultMessage
        myDialog.findViewById<View>(R.id.topView).setBackgroundColor(color)
        myDialog.findViewById<View>(R.id.bottomView).setBackgroundColor(color)
        myDialog.findViewById<TextView>(R.id.resultMessageTextView).text = message

        myDialog.show()
    }

    private fun changeFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainerView.id, fragment)
            addToBackStack(null)
            commit()
        }
    }


}