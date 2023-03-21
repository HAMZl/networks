#!/bin/bash

# compile the Java program
javac Client.java

# running test from example provided in coursework
noItems=$(java Client show)
if ["$noItems" == "There are currently no items in this auction." ]; then
    echo "Test for no items: PASSED"
    echo "EXPECTED: There are currently no items in this auction."
    echo "GOT: $noItems"
else
    echo "Test for no items: FAILED"
    echo "EXPECTED: There are currently no items in this auction."
    echo "GOT: $noItems"
fi