### The Toolbox Project
This is a project for putting utility code - things for statistics, random numbers, graphs, trees, etc.  I started this project mainly because I wanted to do some statistical and numerical work in Java, similar to what you can do in R, but did not know of any Java libraries that I could use.  I also wanted to implement some graph algorithms and this was a good place to put that, but it may need to be moved to a different project in the future.

One recent addition I have been working on is random number generation.  The standard Java system has the ability to generate uniformly distributed random numbers between 0 and 1, but nothing more that I am aware of.  This project contains implementations of binomial, normal, and exponential random numbers.  They all use Java's Math.random(), but use it in a way that generates the correct distribution.  For the normal and exponential distributions, it uses the inverse of the integral of the probability mass function as a shortcut (instead of say, simulating a series of binomials to generate an exponential, which is much slower).

I have been focusing on the stats stuff lately so the other stuff (graphs and trees) is still woefully incomplete.

I hope it can be useful for my projects, and maybe others' as well.  It can also be a way of showing code I have written.