package com.pierodibello.connectfour

import com.pierodibello.connectfour.Player.Cross
import com.pierodibello.connectfour.Player.Nought

fun main() {
    listOf(
            Column.a, Column.c,
            Column.e, Column.a,
            Column.f, Column.f)
            .fold(Board()) { board, choices -> board.placeChipIn(choices) }
            .show()
}

enum class Column { a, b, c, d, e, f, g }
enum class Row { l1, l2, l3, l4, l5, l6 }

fun Row.render(moves: List<Move>): String {
    val rowChars = Column.values().map { lane ->
        val pile = moves.filter { lane == it.column }
        if (pile.size > ordinal)
            pile[ordinal].player.sign
        else
            ' '
    }
    return rowChars.joinToString(prefix = "|", separator = "|", postfix = "|")
}

enum class Player(val sign: Char) {
    Cross('X'), Nought('O')
}

private fun Player.opponent() =
        when (this) {
            Cross -> Nought
            Nought -> Cross
        }


data class Move(val player: Player, val column: Column)
class Board(private val moves: List<Move> = emptyList()) {

    private val player = if (moves.isEmpty()) Cross
    else moves.last().player.opponent()

    fun show() {
        Row.values().reversed().forEach {
            println(it.render(moves))
        }
        println("|-+-+-+-+-+-+-|")
        println(Column.values().joinToString("+", prefix = "|", postfix = "|") { it.name })
    }

    fun placeChipIn(column: Column): Board {
        return Board(moves + Move(player, column))
    }
}
