# Clean the unclean code

In this exercise you have to apply changes on the code, make it readable, 
concise and safe for potential modification.

You can rename variables, restructure code, or remove unnecessary lines.
Make sure that the method still passes the tests. 
If not, take a step back and choose another approach.

## The function

`Application#filter` method returns all the unique elements of the list getting as an argument.

```
[1, 1, 2, 1, 3, 3] -> [2]
```
- `1`: not included in the result list, since it appears 3 times in the input list 
- `2`: included in the result list, since it appears exactly once in the input list
- `3`: not included in the result list, since it appears twice in the input list

Some more examples:
```
[1, 3, 4, 2, 2, 4, 5, 4, 3, 7, 4] -> [1, 5, 7]
[4, 3, 4, 5, 3, 6, 4] -> [5, 6]
```