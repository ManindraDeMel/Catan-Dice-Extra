## Code Review

Reviewed by: Stephen Burg, u7146285

Reviewing code written by: Manindra de Mel u7156805

Component: The method isActionValid and all attached helper functions

### Comments 
- Best Features
The complex code is decomposed into many simple helper functions, allowing for drastically easier identification of errors as each could be tested individually. This type of decomposition also allows for greater moluarity and changes to the code, as if the effect of one helper function needs to be changed, it can be done so more easily than if its fucntion had simpley been intregrated into a larger body of code.
- Documentation
The code is documented but this is the weakest aspect of an otherwsie excellent code. Single line comments explaining the reasons and purpose of various lines are helpful, but with so many helper functions, larger comments explaining exactly the input, purpose and output of each helper function would make following the code much easier.
Decomposition
- As mentioned, the decomposition is exremely strong. With the addition of many of the fucntions surrounding our own implementation of classes, some helpfer functions could likely be simplified, but this is not fault of the code as I believe it was written before these were implemented.
Style and conventions
-The style and conventions are consistent with Java and remain consistent throughout. The only thing worth noting was that 'startsWith' and 'subString' (for example, lines 556-559) could have been used in some places, rather than more complex but functionally identical implementations of the same thing. This makes no functional change and would onyl serve to shorten the length of the code.
Errors
-No potential errors were identified, the code functions well.
<write your comments here>


