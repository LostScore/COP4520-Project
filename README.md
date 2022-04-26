# COP4520-Project
Analyzing and attempting to approve BFS by implementing concurrency in the algorithm.

# Compile
to compile the code type in your command line
```
javac run.java
```
to run it type
```
java run.java *integer **integer

* means that it is optional to add.
*Integer Flag, to change the number of threads tested.
**Intger Flag, to change the index number to do the BFS from 0 - 99;
```
the output of the file is the time in ms for a sequential BFS on the first line and the second is the time in ms for the parralel BFS.

# Experimenntal Data

All test will be done with the index at 0 but increaseing and decreating the number of threads
5 Threads
```
Testing with 5 threads starting at index 0
Test Case
Sequential Time:  1350 microseconds
Parralel Time:    4283 microseconds
Worst Case
Sequential Time:  19 microseconds
Parralel Time:    645 microseconds
```
10 Threads
```
Testing with 10 threads starting at index 0
Test Case
Sequential Time:  1288 microseconds
Parralel Time:    5456 microseconds
Worst Case
Sequential Time:  23 microseconds
Parralel Time:    962 microseconds
```
20 Threads
```
Testing with 20 threads starting at index 0
Test Case
Sequential Time:  1631 microseconds
Parralel Time:    4787 microseconds
Worst Case
Sequential Time:  18 microseconds
Parralel Time:    1775 microseconds
```
50 Threads
```
Testing with 50 threads starting at index 0
Test Case
Sequential Time:  1487 microseconds
Parralel Time:    7900 microseconds
Worst Case
Sequential Time:  228 microseconds
Parralel Time:    4626 microseconds
```
100 Threads
```
Testing with 100 threads starting at index 0
Test Case
Sequential Time:  1700 microseconds
Parralel Time:    13045 microseconds
Worst Case
Sequential Time:  16 microseconds
Parralel Time:    10410 microseconds
```