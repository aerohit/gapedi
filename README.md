# gapedi

## TODO

### Targeted api:

#### Client side:

+ It would be very nice to have a concept of independent repositories.

+ Data Structure:

        {
          "users": {
            "mchen": {
              "friends": { "brinchen": true },
              "name": "Mary Chen",
              // our child node appears in the existing JSON tree
              "widgets": { "one": true, "three": true }
            },
            "brinchen": { ... },
            "hmadi": { ... }
          }
        }

  *widgets* is a child of *users/mchen*

+ similarly __name__ should be accessible through *users/mchen/name*

+ should be able to access through the parent as well: rootRef.child('users/mchen/name');

+ arrays aren't directly supported because of concurrency issues.

+ Writing data:
    1. set(): write/replace data to a defined path.
    2. update(): only updates the passed keys, leaving others unaffected.
    3. push(): appends an object to a list.

+ Retrieving data:
    Data is read asynchronously, calling a provided callback. The clients
    can choose the events to listen to.

    1. *value*: to read a snapshot of data. Triggered once with initial data
       and then everytime the data at this path changes.
    2. *child_added*: used for list type of data. Called once of each item
       in the list and the everytime a child is added.
    3. *child_changed*: triggered when a child node (including it's decendants)
       are modified.
    4. *child_removed*: triggered when an immediate child is removed.
    5. *child_moved*: ...

+ Detaching Callbacks

+ Reading data once

+ Queries


#### Server side:
Would have to basically entertain what the client wants.
