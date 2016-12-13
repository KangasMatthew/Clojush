# "Evolving a program"
### UMM ECAI Fall 2016
### Henry Fellows, Matthew Kangas, Joseph Thelen

## Our choosen problem
We are attempting to evolve a program which recognizes
strings in which the second half of the string is
identical to the first half  of the string. For example,
the string "abcabc" would result in *true* whereas the
string "bleepbloop" would result in *false*.

## Our approach
Initially, we gave the program every string operation and let it
go with 1000 generations. In the end, every solution it found was
worse than simply guessing *true* or *false*. In an attempt to improve
our results we narrowed down the number of operators our solution can
use, and gave it some specifics that might help it along. (see a093236).
Despite these changes, it still thinks the best possible program is no
program at all.

In 4df66de2fa we added the boolean and char stacks, which finally led
to the creation of some non-empty programs. Unfortunately, these programs are
still awful - consisting primarily of the "boolean_empty" operation repeated
between 1 and 3 times; effectivley just guessing true or false... 

## Final Results
We're never done.

