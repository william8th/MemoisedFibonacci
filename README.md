MemoisedFibonacci
=================

The result of each fibonacci function call is memoised (cached, in this case) so that the calculations don't have to be done again. Memoisation can only be done for functions that are invariant. Fibonacci sequence is invariant as the results never change.

Gains from using a cache might not be obvious when calculating fibonacci terms that are small as the overhead of connecting to Redis to get/set numbers is much bigger than a controlled amount of recursive function call. The advantage here is once the calculation has been done previously, no calculation is ever needed in the future. All there is to do is to get the results from Redis. Gains will be apparent once fibonacci terms that need to be calculated get larger.

## Dependencies ##
Redis is used as the caching mechanism. Visit [Redis](https://github.com/antirez/redis) to learn how to install and launch Redis server.