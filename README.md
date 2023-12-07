# Influence
For easily temporarily modifying state of a variable

## Definitions
### Java
Beging by instantiating the entire class
```java
inf = new Influences()
```

Then you can instantiate an variable that can be influenced
```java
inf.new Influenced<T>()

inf.new Influenced<T>(T value)
```

After which you can instantiate an influence on that variable with a new value
```java
inf.new Influence(Influenced<T> target,T value)
```
#### Methods of the Influenced object
Returns the current value
```java
get()
```

Sets the value immediately. It will be changed when you reset an influence
```java
set(T v)
```

Returns most recent influence on the value
```java
getInfluence()
```

Removes all influences on the value presurving current state
```java
removeInfluences()
```

Sets the innitial value that will remain when all influences are reset
```java
setInnitialValue(T innit)
```

Gets the innitial value that remains when you reset all influences
```java
getInnitialValue()
```

Returns the current value in string format
```java
toString()
```

#### Methods of the Influence object
Returns the object it is influencing
```java
getInfluenced()
```

Returns the previous value before this influence was applied
```java
getPreviousValue()
```

Resets this influence by setting the value of the Influenced to the value before this Influence. 
If it is not the last influence in the chain it propagates the last value. Returns false if Infleunced is null
```java
reset()
```

Repurposes this Influence to influence another value with the same type
```java
reInfluence(Influenced<T> target,T value)
```
