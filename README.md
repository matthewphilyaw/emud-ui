# emud-ui

A command line interface for emud. It's written in clojure, and my goal is to ultimately create a more eloborate ui base on ncurses.

Leiningen 1.7.1 has been used to handle the project, no promises on other versions.

Also if using leiningen I'd recommend either running

run -h or --help for more info on parameters.

``` bash
lein trampoline run [options]
```

or 

``` bash
lein uberjar
java -jar [insert jar that was generated] [options]
```

simply doing

``` bash
lein run
```

swallows the input stream, so when the server replies back the code that is blocking waiting for the read to happen never exits. Those two options resolve that issue. 

