# dropwizard-mongo Release Notes

## 4.0.0 Release Date TBD

* Upgrading libraries to Dropwizard-0.9.1 compatible versions,
   specifically assertj 2.0.0 -> 2.2.0 and 
   dropwizard-guice 0.8.1.0 -> 0.8.3.0

## 3.0.2 Release Date TBD

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
