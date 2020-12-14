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
>       * [service](#service)
>       * [apachepoi](#apachepoi)
>       * [utils](#utils)
>       * [enums](#enums)

## Details

### Libraries

| LIBRARY | USAGE | 
| ------- | ----- | 
| jsoup | fetching HTML document |
| apache poi | parsing stylesheet |
| postgresql | database driver |

### Packages

##### service
  To automate the update job used ScheduledExecutorService in combination with the observer design pattern

    private final List<Observer> observers;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ...
    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update();
        }
    }
    ...
    public void enable() {
        scheduler.scheduleWithFixedDelay(track, 0, 15, TimeUnit.MINUTES);
    }
    

##### apachepoi

