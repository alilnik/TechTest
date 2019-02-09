# Tech Test Project for Android

The test consists of 2 parts. First a code review, then a practical implementation part.

## 1. Code review of `MainActivity`

Estimated time to complete: 30 minutes

`MainActivity` has a `Button` and a `ListView`.
When user press the `Button`, a JSON with an array of objects is fetched on the background.
After this background fetch has finished, the data retrieved will be displayed in the `ListView`.

However, the implementation has several issues. Please review the code, and write down suggestions
of what can be improvements as comments in the code.

Deliverable: MainActivity.java (single file)

## 2. Re-implementation of the app

Estimated time to complete: no more than 5 hours

Please re-implement the app reflecting all the suggestions made during the code review.

* You're not limited to a single class or use of any third-party library for the re-implementation.

Deliverable: the whole source code of the app in a compressed file format (preferred: zip).

## 3. Submitting your code

Send it back to the interviewer at dev+android-tt@drivemode.com.

Make sure the package is of a reasonable size to be sent as an attachment. Please avoid packaging
unnecessary binary or intermediate files (build caches, etc..), but make sure it contains all
necessary code for us to open on Android studio and run. 
