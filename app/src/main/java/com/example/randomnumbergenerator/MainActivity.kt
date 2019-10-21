package com.example.randomnumbergenerator

import android.content.Context
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val n1 = findViewById<EditText>(R.id.num1)
        val n2 = findViewById<EditText>(R.id.num2)
        val replacement = findViewById<CheckBox>(R.id.checkBox)
        val numberText = findViewById<TextView>(R.id.numberText)
        val titleText = findViewById<TextView>(R.id.title)
        val button = findViewById<Button>(R.id.button)
        var usedVals  = mutableListOf<Int>()
        var withReplacement :Boolean = replacement.isChecked

        n1.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    usedVals.clear()
            }

        })

        n2.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                usedVals.clear()
            }

        })

        button.setOnClickListener {
            Toast.makeText(this, getString(R.string.buttonclicked), Toast.LENGTH_SHORT).show()
            if(n1.text.length > 0 && n2.text.length > 0)
            update(numberText, n1, n2, withReplacement, usedVals, titleText)
        }

        replacement.setOnClickListener{
            val checked = replacement.isChecked
            if(checked != withReplacement){
                //the check value has changed
                n1.text.clear()
                n2.text.clear()
                numberText.text =  ""
                n1.hideKeyboard()
                withReplacement = checked
                if(withReplacement){
                    usedVals.clear()
                }

            }
        }



    }

    fun update(textView: TextView, n1:EditText, n2:EditText, replacement: Boolean, vals: MutableList<Int>, title: TextView){
        var a = n1.text.toString().toInt()
        var b = n2.text.toString().toInt()
        //keep working if the values are switched. not listed in requirements. but should still work.
        if(a > b){
            val t = a
            a = b
            b = t
        }
        b +=1 //inclusive fix.

        val nums : Int = getNumber(a, b)
        if(replacement){
            val used : Boolean = vals.contains(nums)
            //if it contains the value, get a new one.
            //if not. use it. store it.
            if(vals.size >= b-a) {
                textView.text = ""
                Toast.makeText(this, getString(R.string.replacement_error), Toast.LENGTH_SHORT).show()

            }
            else if(used) update(textView, n1, n2, replacement, vals, title)
            else {
                vals.add(nums)
                textView.text = nums.toString()
               // title.text = vals.toString()  //used for debugging only.
            }

        } else textView.text = getNumber(a, b).toString()


    }
    fun getNumber(low: Int, high: Int): Int{

        val randomValues = List(high-low) { Random.nextInt(low, high) }
        val num = randomValues.get(0)

        return num

    }


//    https://stackoverflow.com/questions/41790357/close-hide-the-android-soft-keyboard-with-kotlin
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
