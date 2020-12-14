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

## Details

### Libraries

| LIBRARY | USAGE | 
| ------- | ----- | 
| jsoup | fetching HTML document |
| apache poi | parsing stylesheet |
| postgresql | database driver |

### Packages

#### service

  To automate the update job used ScheduledExecutorService in combination with the observer design pattern:

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
    

Defined contracts within service package:

    public interface ScheduleUpdateHandler {
        Optional<Schedule> handle();
    }
    
    public interface EntityFactory<T, S, V> {
        Optional<T> create(S selfReference, V argument);
    }
    
    public interface EntityCollectionFactory<T, S, V> {
        List<T> createCollection(S selfReference, List<V> collection);
    }


#### apachepoi

  The package deals with parsing stylesheet file by mentioned contracts implementations:
 
    public class ApacheScheduleUpdateHandler implements ScheduleUpdateHandler {
        private final EntityFactory<Schedule, ScheduleVersion, URL> scheduleFactory;

        ...
        
        
        @Override
        public Optional<Schedule> handle() {
            ...
            ScheduleVersion scheduleVersion = new ScheduleVersion();
            Optional<Schedule> schedule = scheduleFactory.create(scheduleVersion, url.get());
            if (schedule.isPresent()) {
                ...
                return schedule;
                }
              }
              return Optional.empty();
            }
            
        public class ScheduleFactory implements EntityFactory<Schedule, ScheduleVersion, URL> {
            private final EntityCollectionFactory<GroupedDailySchedule, Schedule, List<String>> factory;

            @Override
            public Optional<Schedule> create(ScheduleVersion scheduleVersion, URL url) {
              Optional<List<List<String>>> preparedCollections = fetchAndPrepareData(url);
                if (preparedCollections.isPresent()) {
                  ...
                  schedule.setDailySchedule(factory.createCollection(schedule, preparedCollections.get()));
                  return Optional.of(schedule);
                }
              return Optional.empty();
            }     
  
 
 #### utils

  The class that represents the in-memory file is noteworthy in this package
  
    public final class TemporaryFile {
      private TemporaryFile() {
          throw new AssertionError();
      }

      public static Optional<File> writeInputStreamToFile(InputStream inputStream) {
          if (Objects.isNull(inputStream)) {
              throw new NullPointerException("InputStream cannot be null value.");
          }

          File file = newTemporaryFile()
                  .orElseThrow(IllegalStateException::new);

          try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
              byte[] buffer = new byte[1024];
              int lenght = 0;
              while ((lenght = inputStream.read(buffer)) != -1) {
                  fileOutputStream.write(buffer, 0, lenght);
              }
          } catch (IOException e) {
              e.printStackTrace();
              return Optional.empty();
         }
          return Optional.of(file);
      }

      private static Optional<File> newTemporaryFile() {
          try {
              File file = File.createTempFile("temp", null);
              file.deleteOnExit();
              return Optional.of(file);
          } catch (IOException e) {
              e.printStackTrace();
              return Optional.empty();
          }
       }
    }
    
 As we can se above the only was way to get TemporaryFile is to call method with InputStream as param.
 TemporaryFile is destroyed after calling exit method on him.
