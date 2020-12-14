# Schedule Updater

## About / Synopsis

* Service application dealing with updating the plan. It does a simple sequence every specified time (30 minutes). It downloads the timetable in .xls format from the HTML document and uses the Apache POI library to parse the obtained data into PostgreSQL. It uses the DDD approach to define database objects.
* Project status: working/prototype

## Table of contents

> * [Schedule Updater](#schedule-updater)
>   * [About / Synopsis](#about--synopsis)
>   * [Table of contents](#table-of-contents)
>   * [Details](#details)
>     * [Libraries](#libraries)
>     * [Packages](#packages)
>       * [apachepoi](#apachepoi)
>       * [service](#service)
>       * [utils](#utils)
>       * [enums](#enums)

## Details

### Libraries

| LIBRARY | USAGE | 
| ------- | ----- | 
| lombok v1.18.18 | boiler plate code reduction |
| hateoas | data representation |
| postgresql | database driver |
| junit | unit tests |
| mockito | unit layer and integration tests |
| h2 v1.4.2 | JPQL queries tests | 

### Packages

