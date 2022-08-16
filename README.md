# COMP1110 Assignment 2

## Academic Honesty and Integrity

Honesty and integrity are of utmost importance. These goals are *not* at odds
with being resourceful and working collaboratively. You *should* be
resourceful, you should collaborate within your team, and you should discuss
the assignment and other aspects of the course with others taking the class.
However, *you must never misrepresent the work of others as your own*. If you
have taken ideas from elsewhere or used code sourced from elsewhere, you must
say so with *utmost clarity*. At each stage of the assignment you will be asked
to submit a statement of originality, either as a group or as individuals. This
statement is the place for you to declare which ideas or code contained in your
submission were sourced from elsewhere.

Please read the ANU's [official position](http://academichonesty.anu.edu.au/)
on academic honesty. If you have any questions, please ask me.

Carefully review the statements of originality in the [admin](admin) folder
which you must complete at each stage.  Edit the relevant statement and update
it as you complete each stage of the assignment, ensuring that when you
complete each stage, a truthful statement is committed and pushed to your repo.

## Purpose

In this assignment you will *work as a group* to master a number of major
themes of this course, including software design and implementation, group
work, using development tools such as Git and IntelliJ, and using JavaFX to
build a user interface.  **Above all, this assignment will emphasize group
work**; while you will receive an individual mark for your work based on your
contributions to the assignment, **you can only succeed if all members
contribute to your group's success**.

## Assignment Deliverables

The assignment is worth 30% of your total assessment, and it will be
marked out of 30.  So each mark in the assignment corresponds to a
mark in your final assessment for the course.  Note that for some
stages of the assignment, you will get a _group_ mark, and for others
you will be _individually_ marked.  The mark breakdown and the due
dates are described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
page.

Your tutor will mark your work via GitLab, so it is essential that you carefully follow instructions for setting up and maintaining your group repository.
You will be marked according to whatever is committed to your repository at the time of the deadline.
You will be assessed on how effectively you use Git as a development tool.

## Problem Description

Your task is to implement in Java, using JavaFX, a board game called the
[Catan Dice Game "Extra"](https://www.catan.com/sites/default/files/2021-07/catan-dice-game-extra.pdf). (The link lets you download the game rules booklet
as a PDF file.)

Catan Dice Game Extra is a spin-off (one of many) of the "Settlers of Catan"
game series. There are even several Catan dice games. If you search for
information or answers about the game on-line, make sure what you read
actually refers to the **Catan Dice Game "Extra"** (sometimes also known as
"XXL"), not one of the many other games in the series.

Assignment 2 for COMP1110 is built on the basic Catan Dice Game. You can
find (and may want to refer to) the rules for this game too on
[the Catan web site](https://www.catan.com/). However, the description here
is be self-contained; you should not have any need to look at the COMP1110
assignment.

## Game Overview

Catan Dice Game Extra can be played by two or more players. Players take
turns rolling dice to obtain resources and using those resources to build
structures on a shared map.
All things built contribute to a player's score, and the player with the
highest score at the end of the game is the winner.
The network of structures built by each player must be connected, and each
structure can be built by only one player. Thus, there can be a race to
each valuable areas of the map, and to avoid being "cornered" by other players.

An overview of the game rules is given below. Use this, in addition to the
resources linked above. If anything is unclear, please consult Piazza for
clarification.

### The Game Board

![The Game Map](assets/catan-dice-extra-map.png)

The game board (or map) has five types of buildable structures: Roads
(rectangles, on the edges between the hexagonal tiles), Settlements
(the small house-like shapes at tile corners), Cities (the larger house
shapes, found next to a Settlement at some tile corners), Knights
(the figures near the centre of each tile), and the Castles (four larges
house-shapes at the corners, off the map).
As a player builds structures, they mark them off on their map by filling
them in; each player will have to use a different colour to identify the
things they have built.
Settlements are worth 1 point each, while Cities and Castels are worth 2
points each.

### Resources

There is six different types of resources: Ore, Grain, Wool (represented
by a sheep), Timber, Bricks and Gold. Each structure has a build cost, which
is the resources a player must have available to build it. For example,
building a Road consumes 1 Brick and 1 Timber. The build costs are summarised
at the top and bottom of the map. Gold is not used to build anything, but can
be traded for other resources (see "Trades and Swaps" below).

### Player Turn

There are some special rules for the first turn(s); these follow in the
section [Start of the Game](#start-of-the-game) below.

On their turn, a player first rolls the dice (there are six of them). Each
die is marked with the six different resources in the game.

After rolling, the player may select a number of dice to re-roll, and after
the first re-roll may again select a number of dice and roll a third time.

Next, the player can trade and/or swap resources (using Knights), and use
resources to build. A player can build more than one structure on the same
turn, as long as they have enough resources to build all of them. Building
actions are done in sequence, so that conditions required to build a
structure (see "Building Constraints" below) do not necessarily have to
be satisfied at the start of the turn. For example, a player may build a
Road to reach a Settlement, and then build that Settlement, on the same
turn. Also note that building and swapping can be interleaved: a Knight
can be used to swap a resource on the same turn that the knight was built
(but only after it has been built, of course).

When a player cannot take any more actions, or chooses not to, the turn
passes to the next player.

### Building Constraints

In addition to having the resources available, there are certain constraints
on the order in which structures can be built:

*   Roads, Settlements and Cities must form a connected network, starting
    from the initial Road. To reach a Knight, a player must have built at
    least one Road that borders the Knight's hex, or one Settlement at one
    of the corners of the Knight's hex.

*   To build a City, a player must first have built the Settlement in the
    same location. (That is, a City is an "upgrade" of a Settlement.)

*   As explained in the game rules, you cannot "skip" a Settlement; that is,
    when determining which parts of the map a player can reach, you cannot
    trace a path through an unbuilt Settlement (to build a Road on the other
    side). However, if the Settlement is upgradeable to a City, you can
    continue building through the Settlement without upgrading it.

*   Structures built by other players block the path.

### Trades and Swaps

A player can change their resources in two ways: trading Gold for other
resources, at a rate of 2:1, or swapping resources using Knights.

To trade, the player simply exchanges two Gold for one resource of their
choice.

A Knight, once built, can be used once per game to swap a resource. Knights
on hexes marked with a resource allow the player to swap one available
resource of their choice for that resource. The two Knights in the dessert
hex at the centre of the board are wildcards: it can be used to swap one
available resource of the player's choice for one of any other resource
(including Gold).

### Longest Road and Largest Army

In addition to the points gained from building Settlements, Cities and
Castles, players can gain points for having the "longest road" and the
"largest army": these are worth 1 and 2 points, respectively.
The longest road points go to the player who currently has the longest
contiguous sequence of Roads built on the board - branches do not count
- once that sequence is at least 5 Roads long. The largest army points
go to the player who has the most Knights built on the board, as long as
they are at least 3. For the purpose of counting the size of the army,
it does not matter if the Knight's resource-swap function has been used
or not.

The title of having the longest road or largest army (and the associated
points) belongs to one player at a time, and can change hands during the
game, if another player builds a (strictly) longer road or larger army.
For example, the first player to build a Road sequence of length 5 gains
the longest road title; if another player also builds a sequence of length
5, the title stays with the player who did it first. But if another player
builds a sequence of 6 or more Roads, they then become the holder of the
longest road, and the associated 1 point is moved to them. This means
a player's score can decrease between turns (but only increase, or remain
unchanged, on their own turn).

### Start of the Game

At the start of the game, each player gets to build (i.e., mark) one of
the coastal Roads for free. This road will be the starting point of their
network. Players choose their starting Road in sequence, with the
constraint that the chosen Road must be **at least 5 steps aways from the
Roads chosen by all previous players**.

To compensate for the "first-mover advantage" of the player who picks their
starting Road first, this player only rolls only **three** dice for resources
on their first turn. Each subsequent player adds one more die, until the
number is six, and from that point on all players continue rolling six dice
on their turn. This means that in a four-player game, all players will be
rolling six dice from turn 2, but in a two-player game, it will take two
turns to ramp up to the full set of dice.

### Game End

The game ends as soon as one player reaches 10 victory points. This can
occur either through building a final Settlement, City or Castle, or
through capturing the longest road or largest army title by building a
Road or Knight.

## Encoding Game State

*More details of the `CatanDiceExtra` class and the string encoding used
 for interfacing with tests will be included here after D2B is complete.*

## Evaluation Criteria

It is essential that you refer to the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
to check that you understand each of the deadlines and what is
required.  Your assignment will be marked via tests run through git's
continuous integration (CI) framework, so all submittable materials
will need to be in git and in the *correct* locations, as prescribed
by the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/).

**The mark breakdown is described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
page.**

### Part One

In the first part of the assignment you will:
* Set up your assignment (Task #1).
* Create a design skeleton (Task #2).
* Implement parts of the text interface to the game (Tasks #3 and #4).
* Implement basic dice rolling (Task #5).
* Implement a simple viewer that allows you to visualize game states (Task #6).
* Implement basic checks for action validity (Task #7).
* Create functions to assist calculating the longest road and largest army (Task #8).

An indicative grade level for each task for the [completion of part one](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2C) is as follows:

**Pass**
* Tasks #1, #2, #3 and #4.

**Credit**
* Task #5 and #6 *(in addition to all tasks required for Pass)*

**Distinction**
* Task #7 and #8 *(in addition to all tasks required for Credit)*

### Part Two

Create a fully working game, using JavaFX to implement a playable
graphical version of the game in a 1200x700 window.

Notice that aside from the window size, the details of exactly how the
game looks etc, are **intentionally** left up to you. You may choose to
closely follow the look of the original board game, or you may choose to
present the game in totally different manner.

The only **firm** requirements are that:

* You must use Java 17 and JavaFX 17.
* Your implementation must respect the specification of the game rules
  given here.
* Your game must be easy to play.
* Your game must runs in a 1200x700 window.
* Your game must be executable on a standard lab machine from a jar
  file called `game.jar`,

Your game must successfully run from `game.jar` from within another
user's (i.e.  your tutor's) account on a standard lab machine. In
other words, your game must not depend on any features not self-contained
within that jar file and the Java 17 runtime.

An indicative grade level for each task for the [completion of part
two](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2F)
is as follows:

**Pass**
* Correctly implements all of the <b>Part One</b> criteria.
* Appropriate use of git (as demonstrated by the history of your repo).
* Completion of Task #9 and #10.
* Executable on a standard lab computer from a runnable jar file,
  game.jar, which resides in the root level of your group repo.

**Credit**
* _All of the Pass-level criteria, plus the following..._
* Task #11.

**Distinction**
* _All of the Credit-level criteria, plus the following..._
* Tasks #12 and #13.

**High Distinction**
* _All of the Distinction-level criteria, plus the following..._
* Tasks #14, #15 and #16.
