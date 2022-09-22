# Calculator
A calculator made in java using __Reverse Polish Notation__ (Shunting yard algorithm) and __REGEX__. Its purpose was to help me learn and understand better **OOP** concepts. The GUI is made with JavaFX.

* [Demo](#demo)
* [How does it work](#how-does-it-work)
  * [REGEX](#regex)
  * [Reverse Polish Notation](#reverse-polish-notation)
  * [Evaluation of RPN](#evaluation-of-rpn)
* [Installation & Libraries](#installation-and-libraries)

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
* This convertor is an **improved and detailed implementation** of [Shunting Yard algorithm](https://en.wikipedia.org/wiki/Shunting_yard_algorithm#:~:text=In%20computer%20science%2C%20the%20shunting,abstract%20syntax%20tree%20(AST).)
* Every operator and symbol has either common or special logic for processing the queue.
* Details about operators and symbols for processing can be found in `MathSymbol`. [PEMDAS rule](https://en.wikipedia.org/wiki/Order_of_operations#:~:text=It%20stands%20for%20Parentheses%2C%20Exponents,%2FMultiplication%2C%20Addition%2FSubtraction)

## Evaluation of RPN
The way each operation is implemented can be found in the `Calculations` class. Every number is perceived as a `ComplexNumber` and the operations are written for complex numbers. Decimal numbers are also accepted (or both at the same time).
* Operators are saved in Binary/UnaryOperatorEnum, where each operator is associated a sign, an operation from `Calculations` and a regex form for the first mentioned convertor.
* `CalculatorService` calculates the [postfix expression](https://www.tutorialspoint.com/what-is-postfix-notation#:~:text=In%20postfix%20notation%2C%20the%20operator,%2B%20c)

# Installation and Libraries
- [Java](https://phoenixnap.com/kb/install-java-windows)
- [JavaFX](https://www.youtube.com/watch?v=Ope4icw6bVk)
- [Scene Builder](https://www.youtube.com/watch?v=-Obxf6NjnbQ&t=76s)
- [How to export JAR](https://www.youtube.com/watch?v=EyYb0GmtEX4)
