# RealmPerformanceIssue

Here is a sample project that reproduces performance issue when upgrading realm from v5.x.x to v10.x.x. We tested it in on a simulator, and take approximatively 2 minutes to run.

version of realm-java can be changed in top level build.gradle.

# How it works?

The test to be run is `ExampleInstrumentedTest`. It creates a fresh new realm db containing 150k rows of a fake model filled with random data (`Model0`), and then measure the average duration of X query.

# Results

Here is the output logs. It appears to be 5 times slower than previously. We can see that for 1000 queries, it takes now more than 10 seconds with the latest realm versions, versus 2.2 seconds with the old one.

v5.15.1
-------

```
For 25 queries: 73ms (average)
For 50 queries: 136ms (average)
For 100 queries: 241ms (average)
For 200 queries: 506ms (average)
For 400 queries: 1009ms (average)
For 600 queries: 1677ms (average)
For 800 queries: 1813ms (average)
For 1000 queries: 2227ms (average)
```

v10.8.0
-------

```
For 25 queries: 271ms (average)
For 50 queries: 529ms (average)
For 100 queries: 1057ms (average)
For 200 queries: 2085ms (average)
For 400 queries: 4329ms (average)
For 600 queries: 6193ms (average)
For 800 queries: 8185ms (average)
For 1000 queries: 10492ms (average)
```
