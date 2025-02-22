package com.abcollection.calculatorlogic

enum class MATHOPERATION(val index: Int) {
    NONE(0),
    DIVIDE(1),
    MULTIPLY(2),
    ADD(3),
    SUBTRACT(4),
    MODULO(5),
    PERCENT(6);
}

class CalcLogic {
    private var mathOperator = MATHOPERATION.NONE
    private var calcVal = 0.0
    private var memVal = 0.0


    /************************************************
     * NUM BTN PRESSED
     ***********************************************/
    fun numBtnPressed(btnValue: String, displayVal: String): String {
        // if the display value is 0 the new number is displayed,
        // if display is 0. we need to keep treat '.' || "0." as numButton
        val dblDisplayVal = try {
            displayVal.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        if (displayVal != "0." && dblDisplayVal == 0.0) {
            return btnValue
        } else {
            val newVal = displayVal + btnValue
            return newVal
        }
    }


    /************************************************
     * MATH BUTTON PRESSED
     ***********************************************/
    fun mathBtnPressed(btnValue: String, displayVal:String):String {

        val currentVal = displayVal.toDoubleOrNull()
        if (currentVal != null) {
            calcVal = currentVal
        }
        when (btnValue) {
            "÷" -> mathOperator = MATHOPERATION.DIVIDE
            "x" -> mathOperator = MATHOPERATION.MULTIPLY
            "+" -> mathOperator = MATHOPERATION.ADD
            "-" -> mathOperator = MATHOPERATION.SUBTRACT
            "mod" -> mathOperator = MATHOPERATION.MODULO
            "%" -> mathOperator = MATHOPERATION.PERCENT
        }
        return btnValue
    }


   /************************************************
    * EQUALS BUTTON PRESSED
    ***********************************************/
    fun equalsBtnPressed(displayVal:String):String {
        val currentVal = displayVal.toDoubleOrNull()
        var solution = 0.0
        if (currentVal != null) {
            solution = when (mathOperator) {
                MATHOPERATION.NONE -> currentVal
                MATHOPERATION.DIVIDE -> calcVal / currentVal
                MATHOPERATION.MULTIPLY -> calcVal * currentVal
                MATHOPERATION.ADD -> calcVal + currentVal
                MATHOPERATION.SUBTRACT -> calcVal - currentVal
                MATHOPERATION.MODULO -> calcVal % currentVal
                MATHOPERATION.PERCENT -> calcVal * (currentVal / 100)
            }
        }
        return formatSolution(solution)
    }


    /************************************************
     * CHANGE SIGN BUTTON PRESSED
     ***********************************************/
    fun changeSignBtnPressed(displayVal: String):String{
        val currentVal = displayVal.toDoubleOrNull()

        if (currentVal != null) {
            val changedVal = (currentVal * -1)
            return(formatSolution(changedVal))
        } else {
            return displayVal
        }
    }


    /************************************************
     * MEM ADD BUTTON PRESSED
     * *********************************************/
    fun memAddBtnPressed(displayVal: String):String{
        var dblDisplayVal = displayVal.toDoubleOrNull()
        if (dblDisplayVal == null) {
            dblDisplayVal = 0.0
        }
        memVal += dblDisplayVal
        return formatSolution(memVal)
    }


    /************************************************
     * MEM GET BUTTON PRESSED
     ***********************************************/
    fun memGetBtnPressed():String {
        return formatSolution(memVal)
    }


    /************************************************
     * MEM SUB BUTTON PRESSED
     ***********************************************/
    fun memSubBtnPressed(displayVal: String):String {
        var dblDisplayVal = displayVal.toDoubleOrNull()
        if (dblDisplayVal == null) {
            dblDisplayVal = 0.0
        }
        memVal -= dblDisplayVal
        return formatSolution(memVal)
    }


    /************************************************
     * CLEAR MEM BUTTON PRESSED
     ***********************************************/
    fun memClearBtnPressed(displayVal: String):String{
        memVal = 0.0
        return displayVal
    }


    /************************************************
     * CLEAR ALL BUTTON PRESSED
     ***********************************************/
    fun clearAllBtnPressed():String{
        calcVal = 0.0
        mathOperator = MATHOPERATION.NONE
        return "" // clear the display
    }


    /************************************************
     * DELETE BUTTON PRESSED
     ***********************************************/
    fun deleteBtnPressed(displayVal:String):String{
        val newVal = (displayVal.dropLast(1))
        return newVal
    }


    /************************************************
     * DECIMAL BUTTON PRESSED
     ***********************************************/
    fun decimalBtnPressed(displayVal: String):String{
        val dblDisplayVal = try {
            displayVal.toDouble()
        } catch (e: NumberFormatException) { 0.0 }

        if(dblDisplayVal == 0.0){
            return "0."
        } else {
            return "$displayVal."
        }
    }

    /************************************************
     * FORMAT SOLUTION
     ***********************************************/
    fun formatSolution(solution: Double):String {
        return if (solution % 1.0 == 0.0) {
            solution.toLong().toString()
        } else {
            solution.toString()
        }
    }
}
