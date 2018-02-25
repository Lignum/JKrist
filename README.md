# JKrist ![](https://travis-ci.org/Lignum/JKrist.svg?branch=master)
A Java library for interfacing with the Krist API.

## Usage
### Creating a Krist instance

```
Krist krist = new Krist();
```

This will use the default Krist node (http://krist.ceriat.net), but you can also specify your own by passing it as a constructor parameter.

### Getting address information
Information about one specific address:
```
Address addr = krist.getAddress("kre3w0i79j");
long balance = addr.getBalance();
long totalIn = addr.getTotalIn();
// ...
```

Get a list of addresses:
```
int maxAddresses = 100; // Get at most 50 addresses
Address[] addr = krist.getAddresses(maxAddresses); // Returns a list of existing addresses
Address[] richest = krist.getTopAddresses(maxAddresses); // Returns a list of the richest addresses
```
