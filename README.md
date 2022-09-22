# Calculator
A calculator made in java using __Reverse Polish Notation__ (using Shunting yard algorithm) and __REGEX__. Its purpose was to help me learn and understand better **OOP** concepts. The GUI is made with JavaFX.

# Demo
https://user-images.githubusercontent.com/53339016/191843600-022d52fa-c0a9-42a1-9c82-be7f41e4b66a.mp4

# How does it work
The calculator takes the input (which is the mathematical expression) from the user, converts the string to a list using **regex** and then converts the list into a queue that contains the RPN form of the expression.

## REGEX
`ConvertorExpressionToList` - used for converting the string into a list using regex.
* The regex used for delimitating operands: `(\d+(\.\d+)?i?)`
  * `\d+` -> matches a digit between one and unlimited times. This is the whole part
  * `(\.\d+)?` -> This is the fractional part that can exist or not
  * `i?` -> matches letter "i" (imaginary unit) zero or one time.
* The regex used for delimitating operators: `(\+|-|\*|/|max|min|\^|%|sqrt|ln|exp|\(|\)|,|i)` (built at runtime)
  * matches one of the available operations

## Reverse Polish Notation
`ConvertorInfixToPostfix` - used for converting the infix form(a list) into a postfix form (a queue)
* Every operator and symbol has either common or special logic for processing the queue.
* Details about operators and symbols for processing can be found in `MathSymbol`. [PEMDAS
https://en.wikipedia.org/wiki/Order_of_operations#:~:text=It%20stands%20for%20Parentheses%2C%20Exponents,%2FMultiplication%2C%20Addition%2FSubtraction)] rule]


# Installation & Libraries
- java: https://phoenixnap.com/kb/install-java-windows
- javafx: https://www.youtube.com/watch?v=Ope4icw6bVk
- scene builder: https://www.youtube.com/watch?v=-Obxf6NjnbQ&t=76s
- how to export jar: https://www.youtube.com/watch?v=EyYb0GmtEX4
