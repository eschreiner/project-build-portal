# Project Build Portal (PBP)

### Overview

The Project Build Portal (PBP) is a web service that receives build notifications from [CI] servers and sends out customized emails to project stakeholders. PBP is useful if you have to maintain several versions of a [COTS] software product that are rolled out to a small number of customers who each run their own installation, and various support teams need to get notified about:

- new builds of 'their' version
- new features added
- bug alerts
- bug fixes

### Technology stack

A predecessor of PBP is used at my day job, but I decided to re-implement its basic ideas using a different tool set to showcase this particular technology stack.

##### Backend

The PBP backend is implemented using following tools and libraries:

* written in [Scala] in version 2.11.1
* using the [Play!] framework, in version 2.3.1
* and [Squeryl], an [ORM] and database query [DSL], in version 0.9.5-6

##### Frontend

The web [GUI] is implemented using following libraries:

* the Twitter [Bootstrap] CSS library implemented using [LESS], in version 2.1.1
* [JQuery]
* [KnockoutJS]

##### REST API

The [CI] servers publish any new builds to PBP using a simple [REST][] [API].

[API]:  http://en.wikipedia.org/wiki/Api
[CI]:   http://en.wikipedia.org/wiki/Continuous_integration
[COTS]: http://en.wikipedia.org/wiki/Commercial_off-the-shelf
[DSL]:  http://en.wikipedia.org/wiki/Domain-specific_language
[GUI]:  http://en.wikipedia.org/wiki/GUI
[ORM]:  http://en.wikipedia.org/wiki/Object-relational_mapping
[REST]: http://en.wikipedia.org/wiki/Representational_state_transfer

[Bootstrap]: http://getbootstrap.com/
[JQuery]:  http://jquery.com/
[KnockoutJS]: http://knockoutjs.com/
[LESS]:    http://lesscss.org/
[Play!]:   http://www.playframework.com
[Scala]:   http://www.scala-lang.org
[Squeryl]: http://squeryl.org
