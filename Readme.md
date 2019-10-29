# Nebula dependencies comparison library

This library allows to compare dependencies between two project resolution states across configurations.

It allows to find out:
* What is the version change between two states assuming all configurations having the module have the same old and new state
* What is a newly added dependency
* What is a removed dependency
* What are inconsistent dependencies having different versions in different configuration and how they change