## Code Review

Reviewed by: Arjun Raj, u7526852

Reviewing code written by: Stephen Burg, u7526852

Component: Task 3 - ```isBoardStateWellFormed``` method (along with helper methods: ```isPlayerScoreWellFormed``` and ```isPlayerBoardStateWellFormed```) in ```CatanDiceExtra``` class

### Comments

#### Best features of this code

- The code implements the requirements in an efficient way! The program goes through each character and checks if it is valid. If not, ```false``` is returned. For instance, if a ```boardState``` is not valid (because of the invalid third character), the code immediately returns ```false``` without running the rest of the code and without checking rest of the characters.
- This ensures that processing power and time is saved. Great design!

####  Program decomposition

- The program is well decomposed. Creating ```isPlayerBoardStateWellFormed``` and ```isPlayerScoreWellFormed``` helper methods makes the main method (```isBoardStateWellFormed ```) look neater and easy to navigate. Moreover, the two helper methods can be reused elsewhere.

#### Code documentation

- Mostly, good documentation! The javadoc clearly explains what each method does.
- The convention/syntax for the javadoc could have included ```@param``` and ```@return``` in the helper methods (like how it did in ``isBoardStateWellFormed``) for better readability. Otherwise, the explanation is precise and accurate.
- The code inside the methods could have included brief single-line comments of their purpose. It can help in debugging, for instance.

#### Java code conventions

- The naming conventions for identifiers are followed everywhere and the identifiers have meaningful names. The method names all follow the java conventions and programming style is consistent throughout.
- Good work!


#### Error in the code

- If the input parameter (```boardState```) is ```null```, the whole program halts/fails due to ```NullPointerException```. ```boardState.charAt(0)``` (third line) can't be invoked on a ```null``` string.
    - Solution 1: Check if ```boardState``` is ```null``` at the start before executing any other code. eg -> ```if(boardState == null) return false;```
    - Solution 2: Add try catch block around the entire body of code. This is a bit tedious and may not be necessary. Solution 1 is most suitable and works fine!

#### Overall

- Fantastic job! The tests pass successfully and the program works well for current requirements!