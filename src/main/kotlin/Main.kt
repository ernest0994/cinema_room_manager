package org.example

var ticketsSold = 0
var currentIncome = 0

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    val cinema = mutableListOf<MutableList<String>>()

    for (i in 1..rows) {
        cinema.add(MutableList(seats) { "S" })
    }

    while (true) {
        when (showMenu()) {
            1 -> showSeats(cinema)
            2 -> buyTicket(cinema, totalRows = rows, totalSeats = rows * seats)
            3 -> statistics(rows = rows, seats = seats)
            0 -> break
        }
    }
}

fun showMenu(): Int {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readln().toInt()
}

fun showSeats(cinema: MutableList<MutableList<String>>) {
    println()
    println("Cinema:")
    for (i in 0..cinema.first().size) {
        if (i == 0) {
            print(" ")
        } else {
            print(" $i")
        }
    }
    println()
    for ((row, seats) in cinema.withIndex()) {
        print(row + 1)
        for (seatIndex in seats.indices) {
            print(" ${seats[seatIndex]}")
        }
        println()
    }
}

fun buyTicket(
    cinema: MutableList<MutableList<String>>,
    totalRows: Int,
    totalSeats: Int
) {

    while (true) {
        println()
        println("Enter a row number:")
        val row = readln().toInt()
        println("Enter a seat number in that row:")
        val seat = readln().toInt()
        val ticketPrice = calculateTicketPrice(
            totalSeats = totalSeats,
            totalRows = totalRows,
            rowNumber = row
        )
        println()
        println("Ticket price: $$ticketPrice")

        try {
            if (cinema[row - 1][seat - 1] == "B") {
                println()
                println("That ticket has already been purchased!")
            } else {
                cinema[row - 1][seat - 1] = "B"
                ticketsSold++
                currentIncome +=ticketPrice
                break
            }
        } catch (ex: IndexOutOfBoundsException) {
            println("Wrong input!")
        }
    }
}

fun calculateTicketPrice(totalSeats: Int, totalRows: Int, rowNumber: Int): Int {
    return if (rowNumber <= totalRows / 2) {
        10
    } else {
        if (totalSeats < 60) {
            10
        } else 8
    }
}

fun statistics(rows: Int, seats: Int) {
    val percentage = (ticketsSold * 100.0) / (rows * seats)
    val formatPercentage = "%.2f".format(percentage)

    println()
    println("Number of purchased tickets: $ticketsSold")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $${calculateTotalIncome(rows, seats)}")
}

fun calculateTotalIncome(rows: Int, seats: Int): Int {
    val frontRows = rows / 2
    val totalSeats = rows * seats
    return if (totalSeats > 60) {
        (frontRows * seats * 10 + ((rows - frontRows) * seats * 8))
    } else {
        totalSeats * 10
    }
}