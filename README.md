# Sthlm new apartments

A program to show only newly added apartment projects between visits to [Bostad Stockholm Nyproduktion](https://bostad.stockholm.se/nyproduktion/).

This program have been developed since that type of functionality is not available on the site at the moment.

## Build project and run tests

Start a local server on port 8000 in a terminal to prepare for the tests:

<code>
cd path/to/project/test-url

python3 -m http.server [PORT_NUMBER]
</code>

Then build project in another terminal window:

<code>
cd path/to/project

ant
</code>

Close the local server when build has completed.

[More information on starting a local server](https://developer.mozilla.org/en-US/docs/Learn/Common_questions/set_up_a_local_testing_server).

## Start the program

Double click the jar file or run in terminal: 

<code>ant run</code>

or

<code>java -jar sthlm-new-apartments.jar</code>

An assets directory with a data file will be created and used to find updated projects between runs.
Store the jar file and assets directory in a dedicated directory to keep them together.