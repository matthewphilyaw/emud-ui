# emud-ui

A curses based UI for EMUD

Leiningen 1.7.1 has been used to handle the project, no promises on other versions.

to run it from command line through lein

```bash
lein run [options]
```

You can also package it up in a jar like

```bash
lein uberjar
```

That will create a jar file with all the dependencies tucked inside. In fact the downloads section contains this far file.

Couple of notes, when the UI launches you can exit at anytime by pressing Esacape and if the emud server is not running you will get an error and be prompted to close.
