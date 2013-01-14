Project Build Portal (PBP)
==========================

Overview
--------

The Project Build Portal (PBP) is a web service that receives build notifications from CI services and sends out customized emails to project stakeholders.

A predecessor of PBP is used at my day job, but I decided to re-implement its basic ideas using a different tool set to showcase this particular technology stack.

PBP is implemented in Scala and based on following tools and libraries:

* the [Play! framework](www.playframework.org), currently in version 2.0.4
* [Squeryl](squeryl.org), a Scala ORM and DSL
