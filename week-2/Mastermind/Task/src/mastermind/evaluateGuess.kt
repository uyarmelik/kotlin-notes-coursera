package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    var rightPosition = 0
    var wrongPosition = 0
    val secretChars = secret.toCharArray()
    val guessChars = guess.toCharArray()

    for (i in secret.indices) {
        if (secret[i]==guess[i]) {
            secretChars[i] = 'X'
            guessChars[i] = 'Y'
            rightPosition++
        }
    }

    for (i in secret.indices) {
        for (j in secret.indices) {
            if (secretChars[i]==guessChars[j]) {
                secretChars[i] = 'X'
                guessChars[j] = 'Y'
                wrongPosition++
            }
        }
    }

    return Evaluation(rightPosition, wrongPosition)

}