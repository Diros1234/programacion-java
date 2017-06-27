# Explain of programming strategies using syncronized.

# Rol of objects in syncronized systems.

The most important object is the wish object.

The wish object in the example of philosophers,is that all philosophers want forks, in the fork object we create a syncronized functions and strategies for solved the problems.

-Syncronized function is for that only one thread or object can execute at the same time.

“block is equal to syncronized function.Also is possible use syncronized for a piece of code, but good practices say that syncronized is better to use it,in functions, in our case ever use good practices”

# Brief explanation of a synchronized system

First step

The object asks, if there is any object in the block.if that question is true the object can’t enter in the block,if is false the object can enter in the block.

At this moment,there could be two cases.

-1 None have entered the block yet.

-2 Yes,have object in the block but this states are “waiting”.

In two cases the object can enter in the block.

Second step

In the second step we should ask the object, if it fulfills what we are looking for.
If the object don’t fulfills, this object should enter in the wait();

Tree Step

When object leave of wait,he can execute the rest of code, It is recommended that when we change some sensitive variable, let's say notifyAll(); , at the end of the function.

If have questions, don’t doubt for asking me.
