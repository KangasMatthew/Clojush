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
use, and gave it some specifics that might help it along. (see [a093236](https://github.com/KangasMatthew/Clojush/commit/a093236)).
Despite these changes, it still thinks the best possible program is no
program at all.

In [4df66de2fa](https://github.com/KangasMatthew/Clojush/commit/4df66de2fa72777a6ce4e1e015c56969df00224b) we added the boolean and char stacks, which finally led
to the creation of some non-empty programs. Unfortunately, these programs are
still awful - consisting primarily of the "boolean_empty" operation repeated
between 1 and 3 times; effectivley just guessing true or false. The Lexicase
Best Program here in the final generation was: (boolean_empty, boolean_xor).

In [8007392befe88c](https://github.com/KangasMatthew/Clojush/commit/a093236) we removed our registration for the boolean and char
stacks, adding "string_take", "string_length", and "string_contains"
specifically. Interestingly enough, documentation for the Clojush project
lists "string_contained" as a string instruction and not "string_contains",
despite the fact that "string_contained", apparently, doesn't exist. The
results of this run were pretty dissapointing, with the best program
it could come up with being an empty one.

In [c4a7d03c582b8](https://github.com/KangasMatthew/Clojush/commit/a093236) we added back the boolean stack, which gives us a new
"Lexicase Best Program" of (string_empty boolean_empty). Still not correct,
by any definition of the word, but more exciting than an empty program.

## Final Results
Despite several attempts, we could not seem to evolve a program that even
came slightly close to being an actual solution to our chosen problem. This
is likely due, for the most part, to our lack of familiarity with the
available instructions and operations in Clojush. Additionally, the problem
we choose is difficult to evolve a program for because it has no gradient
of success - that is, a program either succeeds or fails with no indication
of "how badly" it failed.


