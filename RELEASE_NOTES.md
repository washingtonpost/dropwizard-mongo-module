# dropwizard-mongo Release Notes

## 4.0.6 Release Date 2019/02/04

* Increase Mongo Driver version to 3.12.7 in order to support mongo 4.4 version

## 4.0.4 Release Date 2019/02/04

* Increase Mongo Driver version to 3.6.4

## 4.0.3 Release Date 2016/11/30

* Increase Mongo Driver version to 3.3

## 4.0.2 Release Date 2016/06/21

* Fixes issue where guicing a MongoDatabase in disabled mode throws a NullPointerException

## 4.0.1 Release Date 2016/03/22

* Adding support for Mongo Driver 3.0 MongoDatabase interface

## 4.0.0 Release Date 2015/11/30

* Upgrading libraries to Dropwizard-0.9.1 compatible versions,
   specifically assertj 2.0.0 -> 2.2.0 and
   dropwizard-guice 0.8.1.0 -> 0.8.4.0

## 3.0.2 Release Date 2015/11/24

* Fixing harmless exceptions that happen on startup

## 3.0.1 Release Date 2015/10/20

* Masked password in MongoFactory.toString

## 3.0.0 Release Date 2015/10/14

* Upgraded mongo driver from 2.13.0 to 3.1.0
* Added the MongoFactory.buildDB(String) method

## 2.0.0 Release Date 2015/08/24

* Opensourced (moved parent to wp-oss-parent-pom & repo to github.com/washingtonpost)

## 1.1.0 Release Date 2015/08/24

* Adds a "disabled" flag

## 1.0.0 Release Date 2015/07/10

* First release with a guice-wired module to connect to a Mongo DB cluster
