package com.abcollection.calculator
// Compose Foundation and Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

// Compose Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Material3 Components
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

// Compose UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ConstraintLayout for Compose
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

// Lifecycle and ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

//Library Imports
import com.abcollection.calculatorlogic.CalcLogic
import com.abcollection.calculatorlogic.MATHOPERATION


enum class BTNFUNCTION( val index:Int) {
    NONE (0),
    MATH (1),
    NUM (2),
    EQUALS (3),
    CHANGESIGN (4),
    MEMADD (5),
    MEMGET (6),
    MEMSUB (7),
    MEMCLR (8),
    CLRALL (9),
    DELETE (10),
    DECIMAL(11);
}



//Define the buttons for the calculator to be used in the UI grid and control flow
data class Btn ( val name:String, val value:String,  val btnFunction:String, val colSpan:Int, val rowSpan:Int)
val memAddBtn     = Btn("memAdd",     "M+",  "memAddBtn",     1, 1);
val memClearBnt   = Btn("memClear",   "MC",  "memClearBtn",   1, 1);
val moduloBtn     = Btn("modulo",     "mod", "mathBtn",       1, 1);
val changeSignBtn = Btn("changeSign", "±",   "changeSignBtn", 1, 1);
val clearAllBtn   = Btn("clearAll",   "AC",  "clearAllBtn",   1, 1);
val memSubBtn     = Btn("memSub",     "M-",  "memSubBtn",     1, 1);
val memGetBtn     = Btn("memGet",     "MR",  "memGetBtn",     1, 1);
val percentBtn    = Btn("percent",    "%",   "mathBtn",       1, 1);
val multiplyBtn   = Btn("multiply",   "x",   "mathBtn",       1, 1);
val deleteBtn     = Btn("delete",     "⌫",   "deleteBtn",     1, 1);
val sevenBtn      = Btn("seven",      "7",   "numBtn",        1, 1);
val eightBtn      = Btn("eight",      "8",   "numBtn",        1, 1);
val nineBtn       = Btn("nine",       "9",   "numBtn",        1, 1);
val divideBtn     = Btn("divide",     "÷",   "mathBtn",       1, 1);
val equalsBtn     = Btn("equals",     "=",   "equalsBtn",     1, 3);
val fourBtn       = Btn("four",       "4",   "numBtn",        1, 1);
val fiveBtn       = Btn("five",       "5",   "numBtn",        1, 1);
val sixBtn        = Btn("six",        "6",   "numBtn",        1, 1);
val addBtn        = Btn("add",        "+",   "mathBtn",       1, 1);
val oneBtn        = Btn("one",        "1",   "numBtn",        1, 1);
val twoBtn        = Btn("two",        "2",   "numBtn",        1, 1);
val threeBtn      = Btn("three",      "3",   "numBtn",        1, 1);
val subtractBtn   = Btn("subtract",   "-",   "mathBtn",       1, 1);
val zeroBtn       = Btn("zero",       "0",   "numBtn",        2, 1);
val decimalBtn    = Btn("decimal",    ".",   "decimalBtn",    1, 1);

/************************************************
 * CLASS: CALCULATOR VIEW MODEL:
 * Top-Level View Model is passed down the Composable tree
 * Allows the business logic to interact with the UI
 ***********************************************/

class CalculatorViewModel : ViewModel() {
   private val calcLogic = CalcLogic()
    var displayVal by mutableStateOf("0")
    private var calcVal = " "
    private var memVal = 0.0
    private var mathOperator = MATHOPERATION.NONE
    private var btnFunction = BTNFUNCTION.NONE

    /************************************************
     * UPDATE DISPLAY
     ***********************************************/
    fun updateDisplay(btnFunction: BTNFUNCTION, btnVal: String = "" ) {

            var solution = when (btnFunction) {
                BTNFUNCTION.NONE -> ""
                BTNFUNCTION.NUM ->        calcLogic.numBtnPressed(btnVal, displayVal)
                BTNFUNCTION.MATH ->       calcLogic.mathBtnPressed(btnVal, displayVal)
                BTNFUNCTION.EQUALS ->     calcLogic.equalsBtnPressed(displayVal)
                BTNFUNCTION.CHANGESIGN -> calcLogic.changeSignBtnPressed(displayVal)
                BTNFUNCTION.MEMADD ->     calcLogic.memAddBtnPressed(displayVal)
                BTNFUNCTION.MEMGET ->     calcLogic.memGetBtnPressed()
                BTNFUNCTION.MEMSUB ->     calcLogic.memSubBtnPressed(displayVal)
                BTNFUNCTION.MEMCLR ->     calcLogic.memClearBtnPressed(displayVal)
                BTNFUNCTION.CLRALL ->     calcLogic.clearAllBtnPressed()
                BTNFUNCTION.DELETE ->     calcLogic.deleteBtnPressed(displayVal)
                BTNFUNCTION.DECIMAL ->    calcLogic.decimalBtnPressed(displayVal)

            }
            displayVal = solution
      }
    }


/************************************************
 * @COMPOSABLE
 * CALCULATOR SCREEN
 ***********************************************/
@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
) {
    Calculator(
        displayVal = viewModel.displayVal,
        modifier = modifier,
        viewModel = viewModel
    )
}


/************************************************
 * @COMPOSABLE
 * CALCULATOR
 ***********************************************/
@Composable
fun Calculator(
    displayVal: String,
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel
) {
    Box(modifier = modifier) { // container for the entire calculator
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff1a1817)),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = displayVal,
                style = TextStyle(
                    fontSize = 60.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(10.dp))
            CalcLayout(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                viewModel = viewModel
            )
        }
    }
}


/************************************************
 * @COMPOSABLE
 * CALC LAYOUT:
 * The meat of the UI. The constraint layout is
 * necessary because the 0 button spans cols and
 * the = button spans row. Had to build the grid
 * by hand
 ***********************************************/
@Composable
fun CalcLayout(modifier: Modifier = Modifier, viewModel: CalculatorViewModel ) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(Color(0xff1a1817))
    ) {
        // In a 5x5 grid = size 25, make coordinate references for each cell
        val cellRefs = List(25) { createRef() }

        // Create guidelines for columns
        val c0 = createGuidelineFromStart(0f)
        val c1 = createGuidelineFromStart(1/5f)
        val c2 = createGuidelineFromStart(2/5f)
        val c3 = createGuidelineFromStart(3/5f)
        val c4 = createGuidelineFromStart(4/5f)
        val c5 = createGuidelineFromStart(1f)

        // Create guidelines for rows
        val r0 = createGuidelineFromTop(0f)
        val r1 = createGuidelineFromTop(1/12f)
        val r2 = createGuidelineFromTop(2/12f)
        val r3 = createGuidelineFromTop(3/12f)
        val r4 = createGuidelineFromTop(4/12f)
        val r5 = createGuidelineFromTop(5/12f)
        val r6 = createGuidelineFromTop(6/12f)

        // Helper to place a generic Box at [row, col], optionally spanning multiple rows/columns
        // rowSpan or colSpan > 1 merges those cells.
        var cellIndex = 0
        @Composable
        fun placeCell(row: Int, col: Int, rowSpan: Int = 1, colSpan: Int = 1, btn: Btn? = null) {
            if (cellIndex >= cellRefs.size) return
            val ref = cellRefs[cellIndex++]

            // Compute start/end guidelines in each dimension
            val topGuide = when (row) {
                0 -> r0; 1 -> r1; 2 -> r2; 3 -> r3; 4 -> r4; 5 -> r5 else -> r0
            }
            val bottomGuide = when (row + rowSpan) {
                1 -> r1; 2 -> r2; 3 -> r3; 4 -> r4; 5 -> r5; 6 -> r6 else -> r6
            }
            val startGuide = when (col) {
                0 -> c0; 1 -> c1; 2 -> c2; 3 -> c3; 4 -> c4; else -> c0
            }
            val endGuide = when (col + colSpan) {
                1 -> c1; 2 -> c2; 3 -> c3; 4 -> c4; 5 -> c5; else -> c5
            }

            Box(
                modifier = Modifier
                    .constrainAs(ref) {
                        top.linkTo(topGuide)
                        bottom.linkTo(bottomGuide)
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                        width = Dimension.wrapContent // child dimension enabled
                        height = Dimension.wrapContent // child dimension enabled
                    },
                contentAlignment = Alignment.Center
            ) {
                btn?.let { CalcBtn(btn = it, viewModel = viewModel) } // have to pass down the view model
            }
        }

        val btnRow0 =  listOf(memAddBtn, memClearBnt, moduloBtn,  changeSignBtn, clearAllBtn  )
        val btnRow1 = listOf(memSubBtn,  memGetBtn,   percentBtn, multiplyBtn,   deleteBtn    )
        val btnRow2 = listOf(sevenBtn,   eightBtn,    nineBtn,    divideBtn,     equalsBtn    )
        val btnRow3 = listOf(fourBtn,    fiveBtn,     sixBtn,     addBtn     /*EqualsSpanRow*/)
        val btnRow4 = listOf(oneBtn,     twoBtn,      threeBtn,  subtractBtn /*EqualsSpanRow*/)
        // Both buttons on Row five had to be added manually. Thought the row may be useful in the future
       // val btnRow5 = listOf(zeroBtn,/*ZeroSpanCol*/  decimalBtn /*No btn*/  /*EqualsSpanRow*/)


        // Row 0: 5 columns [0..4]
        for (col in 0..4){
            placeCell(row = 0, col = col, btn = btnRow0.getOrNull(col))
        }

        // Row 1: 5 columns [0..4]
        for (col in 0..4){
            placeCell(row = 1, col = col, btn = btnRow1.getOrNull(col))
        }

        // Row 2: 5 columns [0..4]
        for (col in 0..3){
            placeCell(row = 2, col = col, btn = btnRow2.getOrNull(col))
        }
        //Row 2: column 4, manually add the equals to span the rows merges rows 2-5 on col 4
        placeCell(row = 2, col = 4, rowSpan = 4, btn = equalsBtn)

        // Row 3: 4 columns [3,0..3], then one cell that might span into Row 4
        for (col in 0..3){
            placeCell(row = 3, col = col, btn = btnRow3.getOrNull(col))
        }

        // Row 4: 4 columns [3,0..3], then one cell that might span into Row 4
        for (col in 0..3){
            placeCell(row = 4, col = col, btn = btnRow4.getOrNull(col))
        }

        // Row 5: merges columns 0 and 1
        placeCell(row = 5, col = 0, colSpan = 2, btn = zeroBtn)
        // Row 5: col 2, manually add the decimal after the "0" btn col span
        placeCell(row = 5, col = 2, btn = decimalBtn)
        // col 3 is skipped no button
        // col 4 was used by equals button (spanned from row=2)
    }
}


/************************************************
 * @COMPOSABLE
 * CALC BTN
 ***********************************************/
@Composable
fun CalcBtn(btn:Btn, viewModel: CalculatorViewModel, modifier: Modifier = Modifier){
var containerColor = Color(0xff1f2b36)
var height = (40.dp)
var width =  (70.dp)
    when (btn.btnFunction){
        "mathBtn" -> { containerColor = Color(0xff2f2f2f) }
        "numBtn" -> { containerColor = Color(0xff4b4b4b)
            when(btn.name){ "zero" -> width = (140.dp)
            }
        }
        "equalsBtn" -> { containerColor = Color(0xff2f2f2f)
            height = (240.dp)
        }
        "changeSignBtn" -> { containerColor = Color(0xff2f2f2f) }
        "clearAllBtn"   -> { containerColor = Color(0xff5e040c) }
        "deleteBtn"     -> { containerColor = Color(0xffda7a1f) }
        "decimalBtn"    -> { containerColor = Color(0xff4b4b4b) }
    }
    Button(
        modifier = modifier
            .padding(0.dp)
            .width(width)
            .height(height),

        onClick = {
            when (btn.btnFunction) {
                "mathBtn"       -> viewModel.updateDisplay(BTNFUNCTION.MATH, btn.value)
                "numBtn"        -> viewModel.updateDisplay(BTNFUNCTION.NUM, btn.value )
                "equalsBtn"     -> viewModel.updateDisplay(BTNFUNCTION.EQUALS)
                "changeSignBtn" -> viewModel.updateDisplay(BTNFUNCTION.CHANGESIGN)
                "memAddBtn"     -> viewModel.updateDisplay(BTNFUNCTION.MEMADD)
                "memGetBtn"     -> viewModel.updateDisplay(BTNFUNCTION.MEMGET)
                "memSubBtn"     -> viewModel.updateDisplay(BTNFUNCTION.MEMSUB)
                "memClearBtn"   -> viewModel.updateDisplay(BTNFUNCTION.MEMCLR)
                "clearAllBtn"   -> viewModel.updateDisplay(BTNFUNCTION.CLRALL)
                "deleteBtn"     -> viewModel.updateDisplay(BTNFUNCTION.DELETE)
                "decimalBtn"    -> viewModel.updateDisplay(BTNFUNCTION.DECIMAL)
            }
        },
        shape = RoundedCornerShape(20),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) { Text(text = btn.value) }
}