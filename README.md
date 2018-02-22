# Packing Markup Calculator

Runtime: Java 8 

Project was created with IntelliJ, the IDE specific files are included in the branch.
Gradle is used as the Build Tool, the build script is provided with `build.gradle`.
The only dependency beside the runtime is the `JUnit` Testing Framework.

Running the gradle build as `./gradlew test` will run all tests with the build
and the build will fail if any of the tests fail.


## Task Description

NuPack is responsible for taking existing products and repackaging them for sale at electronic stores like Best Buy. 
Companies will phone up NuPack, explain the process and NuPack needs to quickly give them an estimate of how much it will cost. 
Different markups to the job:

- Without exception, there is a __flat markup on all jobs of 5%__
- For __each person__ that needs to work on the job, there is __a markup of 1.2%__

Markups are also added depending on the types of materials involved:

- __If pharmaceuticals are involved__, there is an immediate 7.5% markup
- For __food__, there is a 13% markup
- __Electronics__ require a 2% markup
- Everything else, there is no markup


Another system calculates the base price depending on how many products need to be repackaged. 
As such, __the markup calculator should accept the initial base price along with the different categories of markups 
and calculate a final cost for a project.__

__The flat markup is calculated first and then all other markups are calculated on top of the base price plus flat markup.__


## Questions / Answers

__Q__: Can there be jobs with 0 people?\
__A__: Yes

__Q__: Can a job have multiple material categories?\
__A__: No

__Q__: What is the input type for the material category? Is there a finite amount of inputs (like a code or enumeration) or can it be any String in which case should there be a mapping of material description -> markup category. (example 'radios' -> electronics, 'books' -> other, 'something' -> other, 'cabbage' -> food)?\
__A__: Please do whatever makes the most sense to you.

__Q__: What rounding method is applied after the markup calculation? From the example it appears we round to nearest neighbour, what about x.xx5 values? Nearest Even, Up or Down?\
__A__: Use your best judgement on this one and be prepared to justify your decision as part of the code review process.


## Components and Design

- It is assumed that it is the rule that monetary values are represented as `BigDecimal` instances in the overall system, 
which encapsulates representation of a non-integer number as an integer number with an arbitrary, fixed precision. 
This is a widely agreed on practice for computations with money, as opposed to representation as a floating point value, 
which bears inherent error due to the nature of representation of the number in memory and additional rounding errors during computation. 

- I did choose not to implement an abstraction for Money or Currency, instead the assumption is made that all monetary values
are in USD. 

- `MaterialMarkup` Simple enumeration encapsulating the four different material category based markups. I believe that in a production environment the
  "material description" string or whatever it might be that is emitted by the NuPack system would undergo a lookup from a database 
  or similar mechanism to be resolved into either a simple markup value from a table join or a value of this enumeration or similar,
  which would ideally happen in a persistence abstraction and not be concern of the markup calculation domain, which should be able
  to be modeled in a stateless fashion. 

- `MoneyCalculator` is an interface to add the programs support and specifications for calculations on monetary values as a mixin.
It provides constants for used rounding method and precisions, as well as default methods for common operations on `BigDecimal` and for the purpose of this program. 
Rounding to the nearest even number is used as the rounding method for computation involving fractions and for truncation of decimal places when dealing with .5 values, 
otherwise we round to the nearest neighbour. This practice is commonly known as 'Bankers Rounding' and has no bias towards any direction 
when rounding, presuming an even distribution of even and odd numbers, which is supposed to result in average accumulated rounding error of around 0.

- `Markup` encapsulates a markup percent value and the markup calculation. It implements the `Function` interface and applying a price 
to the function returns a new price including the markup. The class also contains functions to change the percent value of the markup
in an immutable fashion, allowing to add and multiply with another markup or integer value. This construct mostly
serves the purpose to hold the markup value and for allow defining the markup calculation and values in a declarative fashion.


- `PackingMarkupCalculator` implements the actual packing markup calculation as described in the assignment. 
As for interpretation of the line "__the markup calculator should accept the initial base price along with the different 
categories of markups and calculate a final cost for a project.__" the class constructor accepts 
two instances of `Markup`, the "flat Markup" and a "base personnell Markup" as the base markup value for every Person on the job.
The class instances then provide a method `calculate` that accepts the base price as a `BigDecimal`, the number of people required to work
on the job and a value of `MaterialMarkup` enum and returns the final price, again as a `BigDecimal`. 
Since Java does not feature unsigned Integer values we have to use a signed integer value for the number of people, 
and since a negative amount of personnel makes no sense an `IllegalArgumentException` is thrown from the `calculate` method.
Unfortunately that means that the `calculate` is not defined for all of its possible arguments, the choice of throwing
a RuntimeException results mostly from the lack of alternatives, an unchecked exception is still a better choice than
a checked exception from a software design standpoint, yet it also means that the caller can not know about the error unless
documentation provided. An alternative to an exception would be a NullObject Pattern like approach that would return an invalid price,
but this would require abstraction over the return type. The ideal choice for this would be in my personal opinion
a `Try` style Monad to be used to signal the chance of failur to the caller, without the need for checked exception, while
still allowing to preserve the original error, and being equally robust as a NullObject. Unfortunately Java does not feature any 
useful Monads, use of a third party library or custom implementation of such would have been required, which I felt was not
within the scope of the given Task.



