package com.jglognemra.practica1.model

import android.content.Context
import com.jglognemra.practica1.R
import kotlin.math.pow

data class Equation(val name: String, val img: Int, val variables: ArrayList<String>, val cal: () -> Double)

class EquationBrain(context: Context) {

    private var x = 0.0
    private var y = 0.0
    private var z = 1.0
    private var equations = ArrayList<Equation>()

    init {
        val names = context.resources.getStringArray(R.array.equations_array)

        // constants
        val gConstant = 6.67430 * 10.0.pow(-11.0)

        equations.add(
            Equation(names[0], R.drawable.grav_ecuation, arrayListOf("m1", "m2", "d")) {
                return@Equation gConstant * (x * y) / z
            }
        )
        equations.add(
            Equation(names[1],R.drawable.ohms_law, arrayListOf("I", "R")) {
                return@Equation x * y
            }
        )
        equations.add(
            Equation(names[2], R.drawable.parallel_resistors, arrayListOf("R1", "R2")) {
                @Suppress("DIVISION_BY_ZERO")
                return@Equation if (x + y != 0.0) (x * y)/(x + y) else 0.0
            }
        )
    }

    fun setValues(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun getNameAt(pos: Int): String {
        return equations[pos].name
    }

    fun getImageAt(pos: Int): Int {
        return equations[pos].img
    }

    fun getNumberOfVariablesAt(pos: Int): Int {
        return equations[pos].variables.size
    }

    fun getVariablesNamesAt(pos: Int) : ArrayList<String> {
        return equations[pos].variables
    }

    fun solveEquationAt(pos: Int): Double {
        return equations[pos].cal.invoke()
    }

}