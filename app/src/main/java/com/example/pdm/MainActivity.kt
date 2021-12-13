package com.example.pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var currentPlayer: Int = 1
    var currentPlayerChar: Char = 'X'
    var gameOver: Boolean = false
    lateinit var board: IntArray
    lateinit var buttons: Array<Button>
    lateinit var etCurrentPlayer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCurrentPlayer = findViewById<Button>(R.id.et_current_player)

        findViewById<Button>(R.id.btn_reset).setOnClickListener { onReset() }

        board = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

        buttons = arrayOf(
            findViewById(R.id.btn_0),
            findViewById(R.id.btn_1),
            findViewById(R.id.btn_2),
            findViewById(R.id.btn_3),
            findViewById(R.id.btn_4),
            findViewById(R.id.btn_5),
            findViewById(R.id.btn_6),
            findViewById(R.id.btn_7),
            findViewById(R.id.btn_8)
        )

        for (i in 0..8)
            buttons[i].setOnClickListener {onClick(i)}

        onReset()
    }

    fun onReset() {
        Toast.makeText(this, "Resetting the board...", Toast.LENGTH_SHORT).show()

        gameOver = false
        currentPlayer = 1
        currentPlayerChar = 'X'
        etCurrentPlayer.text = "Current Player: $currentPlayerChar"

        for (i in 0..8) {
            board[i] = 0
            buttons[i].text = ""
        }
    }

    fun onClick(idx: Int) {
        if(gameOver) {
            Toast.makeText(this, "Tap reset to play again!", Toast.LENGTH_SHORT).show()
            return;
        }
        if(board[idx] != 0) {
            Toast.makeText(this, "Select an empty slot", Toast.LENGTH_SHORT).show()
            return;
        }

        board[idx] = currentPlayer
        currentPlayer = 1 + (currentPlayer % 2)

        buttons[idx].text = "$currentPlayerChar"
        currentPlayerChar = if(currentPlayerChar == 'X') 'O' else 'X'
        etCurrentPlayer.text = "Current Player: $currentPlayerChar"

        if(checkPlayerWon()) {
            etCurrentPlayer.text = "Game Over"
            Toast.makeText(this, "Player ${1 + (currentPlayer % 2)} won!", Toast.LENGTH_SHORT).show()
            gameOver = true
        }
    }

    fun checkPlayerWon(): Boolean {
        for (i in 0..2) {
            // Horizontal check
            if(board[i] == board[i+1] && board[i] == board[i+2]
                && board[i] != 0 && board[i + 1] != 0 && board[i+2] != 0)
                return true

            // Vertical check
            if(board[0 + i] == board[3 + i] && board[0 + i] == board[6 + i]
                && board[0 + i] != 0 && board[3 + i] != 0 && board[6 + i] != 0)
                return true
        }

        // Diagonal checks
        if(board[0] == board[4] && board[0] == board[8]
            && board[0] != 0 && board[4] != 0 && board[8] != 0)
            return true
        if(board[2] == board[4] && board[2] == board[6]
            && board[2] != 0 && board[4] != 0 && board[6] != 0)
            return true

        return false;
    }
}