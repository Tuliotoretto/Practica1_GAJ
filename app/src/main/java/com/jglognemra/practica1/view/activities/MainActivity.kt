package com.jglognemra.practica1.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.jglognemra.practica1.R
import com.jglognemra.practica1.databinding.ActivityMainBinding
import com.jglognemra.practica1.model.EquationBrain

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var equationBrain: EquationBrain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        equationBrain = EquationBrain(this)

        // spinner config
        ArrayAdapter.createFromResource(
            this,
            R.array.equations_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerEquations.adapter = adapter
            binding.spinnerEquations.onItemSelectedListener = this
        }

        // input config
        with(binding) {
            btnCalculate.setOnClickListener {
                var flag = true
                if (etVar1.text.isEmpty()) {
                    etVar1.requestFocus()
                    etVar1.error = getString(R.string.invalid_input)
                    flag = false
                }
                if (etVar2.text.isEmpty()) {
                    etVar2.requestFocus()
                    etVar2.error = getString(R.string.invalid_input)
                    flag = false
                }
                if (etVar3.text.isEmpty() and llVal3.isVisible) {
                    etVar3.requestFocus()
                    etVar3.error = getString(R.string.invalid_input)
                    flag = false
                }
                if (flag) {
                    equationBrain.setValues(
                        etVar1.text.toString().toDouble(),
                        etVar2.text.toString().toDouble(),
                        if (llVal3.isVisible) etVar3.text.toString().toDouble() else 0.0
                    )
                    val result = equationBrain.solveEquationAt(spinnerEquations.selectedItemPosition)
                    //Log.d("DEBUGGING", result.toString())

                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra(getString(R.string.intent_result), result)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        //Log.d("DEBUGGING", parent?.getItemAtPosition(pos).toString())

        // change image
        val img : Int = equationBrain.getImageAt(pos)

        // input texts
        val vNames = equationBrain.getVariablesNamesAt(pos)

        with(binding) {
            imgEquation.setBackgroundResource(img)

            etVar1.setText("")
            etVar2.setText("")
            etVar3.setText("")
            etVar1.error = null
            etVar2.error = null
            etVar3.error = null

            tvVar1.text = vNames[0]
            tvVar2.text = vNames[1]

            if (vNames.count() > 2) {
                tvVar3.text = vNames[2]
                llVal3.visibility = View.VISIBLE
            } else {
                llVal3.visibility = View.INVISIBLE
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Log.d("DEBUGGING", "Nothing selected")
    }


}
