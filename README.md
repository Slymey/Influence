# Influenced Library

A Java library for temporarily modifying the state of a variable using the `Influenced<T>` class.

## Overview

`Influenced<T>` allows you to create a managed variable whose value can be temporarily influenced and reverted using `Influencer<T>` objects. This is useful in scenarios where changes to a variable's state must be controlled and rolled back.

### Key Features:
- Temporary value modification.
- Automatic rollback using `Influencer#reset()`.
- Support for nested influences.

## Installation

To include the `Influenced` library in your project:

1. Clone this repository:
   ```sh
   git clone https://github.com/Slymey/Influence.git
   ```
2. Add the `Influenced.java` file to your project.

## Usage

### Creating an Influenced Variable
```java
Influenced<Integer> influencedValue = new Influenced<>(10);
System.out.println("Initial value: " + influencedValue.get()); // Output: 10
```

### Applying Influence
```java
Influenced.Influencer<Integer> influencer = influencedValue.influence(20);
System.out.println("After influence: " + influencedValue.get()); // Output: 20
```

### Resetting Influence
```java
influencer.reset();
System.out.println("After reset: " + influencedValue.get()); // Output: 10
```

### Managing Multiple Influences
```java
Influenced.Influencer<Integer> inf1 = influencedValue.influence(30);
Influenced.Influencer<Integer> inf2 = influencedValue.influence(40);
System.out.println("Latest value: " + influencedValue.get()); // Output: 40

inf2.reset();
System.out.println("After resetting last influence: " + influencedValue.get()); // Output: 30
```

## Documentation
For detailed API documentation, refer to the JavaDoc comments within the source code.


## License

This project is licensed under the MIT License. See `LICENSE` for more details.

---

**Author:** Slymey_


