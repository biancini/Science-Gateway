Science Gateway
===============
This project contains applicatons developed in the context of the CHAIN european project.

It consists of two different development areas:
  * A portlet for the Science Gateway on Liferay that permits to interface a Grid application
  * A JSAGA DRMAA adaptor to interact with GARUDA network

Chain Project
-------------

The CHAIN project aims to coordinate and leverage recent efforts and results with a vision of
a harmonised and optimised interaction model for e-Infrastructure and specifically Grid interfaces
between Europe and the rest of the world.
The project will elaborate a strategy and define the instruments in order to ensure coordination
and interoperation of the European Grid Infrastructures with other external e-Infrastructures.

The CHAIN consortium, consisting of leading organisations in all the regions addressed by the project,
will ensure global coverage, European leadership, and most efficient leveraging of results with respect
to preceding regional initiatives. First, the project will define a coherent operational and
organisational model, where a number of EU countries/regions will possibly act, in collaboration
with EGI.eu, as bridges/gateways to other Regions/Continents. Further, the project will validate this
model by supporting the extension and consolidation of worldwide virtual communities, which
increasingly require distributed facilities (large instruments, distributed data and databases,
digital repositories, etc.) across the regions for trans-continental research.
Finally, the project will act as a worldwide policy-watch and coordination instrument, by exploring
and proposing concrete steps for the coordination with other initiatives and studying the evolution
of e-Infrastructures.


Portlet
-------

By definition, a Science Gateway is a community-developed set of tools, applications, and data that is
integrated via a portal or a suite of applications, usually in a graphical user interface, that is further
customized to meet the needs of a specific community.

The GILDA framework for Science Gateways is fully web-based and adopts official worldwide standards and protocols,
through their most common implementations. These are:

  * The JSR 168 and JSR 286 standards (also known as "portlet 1.0" and "portlet 2.0" standards);
  * The OASIS Security Assertion Markup Language (SAML) standard and its Shibboleth and SimpleSAMLphp implementations;
  * The Lightweight Direct Access Protocol, and its OpenLDAP implementation;
  * The Cryptographic Token Interface Standard (PKCS#11) standard and its Cryptoki implementation;
  * The Open Grid Forum (OGF) Simple API for Grid Applications (SAGA) standard and its JSAGA implementation.

The GILDA Science Gateways are built using the Liferay portal framework.
  [http://www.liferay.com/]


JSAGA DRMAA
-----------
Implementation of a JSAGA Adaptor for DRMAA.
Copyright, 2012-2012, Istituto Nazionale Fisica Nucleare (INFN)


About
-----

This JSAGA Adaptor permits to exploit the DRMAA implementation packaged with GridWay to submit
jobs to grids environments. The current implementation complies with the  DRMAA specification
version 1.0.
 
The idea is to access GridWay through a SAGA adaptor to DRMAA (SAGA->DRMAA->GridWay).
This piece of code is part of the EU funded Chain project:

  [http://www.chain-project.eu/]
  
  

Gridway
-------

GridWay is composed of several modules:

  * At first we have the GridWay daemon, which is the core that coordinates the whole job life
  cycle process by means of a state machine.
  * This daemon uses a set of Middleware Access Drivers (MADs), basically independent processes
  that talk with the daemon using the standard I/O streams.
  These processes are used to perform execution (for job submission),
  transfer (for data staging) and information (for resource discovery and monitoring) tasks.
  Several MADs of each type can be loaded and used simultaneously.
  * The last component of Gridway is the scheduler, which also lives in a separate process, 
  communicating with the daemon also through the standard streams, and is in charge of the job 
  allocation in the resources. 

GridWay exposes two ways of interfacing with the core. One is a powerful command line interface 
(CLI), featuring commands to manage jobs and  computing resources. But GridWay can be used 
programmatically with the Distributed Resource Management Application API (DRMAA), which is 
an OGF standard. In this way, applications can be built abstracted from the heterogeneous 
resources where it can be executed.


DRMAA API
---------

As described in [GridWay DRMAA 1.0 Implementation – Experience Report](http://www.ogf.org/documents/GFD.104.pdf), 
GridWay  implements  the DRMAA API  (C and JAVA bindings) OGF standard, assuring compatibility of applications
with LRM systems that implement the standard, such as SGE, Condor or Torque.

The GridWay DRMAA distribution includes: 
  * A DRMAA include file, drmaa.h, placed in $GW_LOCATION/include 
  * A dynamic implementation library, libdrmaa.so, placed in $GW_LOCATION/lib 
  * A JAR package with the JAVA implementation of the DRMAA API, drmaa.jar, placed in $GW_LOCATION/lib 
  * Documentation at www.gridway.org
  
The DRMAA GridWay  implementation is fully usable, as its suitability to express distributed computations on a
Grid has  been demonstrated on several research papers. Minor functionality  issues required by the DRMAA cannot
be  implemented on Globus Grids (core, and  running  from suspended).
In addition, the  DRMAA  Test  Suite  has  proven  to  be  a  valuable  tool  to  test  and  develop DRMAA
implementations, and a valuable source to interpret the DRMAA specifications. 


Role of the JSAGA Adaptor
-------------------------

This implementation assumes that job submission, monitoring and control requests can be received and then processed
by the DRMAA interface of GridWay.

SAGA is closely related to other OGF standards - in particular, DRMAA and SAGA have overlapping scope.
To describe briefly the differences between the two standards we may say that SAGA is user-facing while DRMAA is middleware-facing. 
