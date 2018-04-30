#Final Project - Task Manager

*Author Mayson Green, Alex Mussell
*Class CS410 [DataBases] Spring 2018

## Overview:
 This project is a task manager that can be used to keep track of all the things you need
 to get done in your busy life so you don't miss a thing.

## Manifest

    included files:
      Credentials.java          - A class to store credentials needed for SSH and the Database connection
      Database.java             - Handles all of the database transactions
      SampleDataGenerator.java  - Parses a .csv file and creates the needed SQL commands for inserting data
      Task.java                 - A class used to represent a Task
      TaskManager.java          - A class used to keep an interactive shell running and parse command line arguments
      ToDoListManager.java      - Parses command line arguments needed for SSH and Database connection and starts interactive shell
      example-data.sql          - SQL insert statments used to insert sample data into the Database
      schema.sql                - DDL used to create the Database and tables

## Building the project
 A Jar file has been provided and can be run using the following command

 ```
 java -jar ToDoListManager.jar <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>
 ```
## Features and usage

 Commands:
 ```
  TaskManager> active                        (view currently active tasks)
  TaskManager> add <label>                   (Create a new task with the given label)
  TaskManager> due <taskID> <yyyy-MM-dd>     (Adds a due date to the task matching the given taskID)
  TaskManager> tag <taskID> <tag> [tag]      (Tags a the task matching the given taskID with the given tags)
  TaskManager> finish <taskID>               (Marks the task matching the given taskID as finished)
  TaskManager> cancel <taskID>               (Marks the task matching the given taskID as canceled; Deletes task from the Database)
  TaskManager> show <taskID>                 (Shows details of the task matching the given taskID)
  TaskManager> active <tag>                  (Shows active tasks matching the given tag)
  TaskManager> completed <tag>               (Shows completed tasks matching the given tag)
  TaskManager> overdue                       (Shows tasks that are overdue)
  TaskManager> due [<today>|<soon>]          (Shows tasks that are due today or soon)
  TaskManager> rename <taskID> <NewLabel>    (Renames the task matching the given taskID to the given label)
  TaskManager> search <keyword>              (Searches for tasks by by the given keyword)
 ```

## Testing

 Manual happy path testing was performed on this project.

## Known Bugs

 No known bugs.
