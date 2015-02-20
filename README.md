# echolib
Java library for using amazon echo

Build with maven
run with java 8 (make sure hava 8 is in your path).

Download echoDeploy.zip from the zip directory for a quick start.  This will just run and process your echo todo
list.

unzip echoDeploy.zip somewhere.  It will create an 'echo' directory.  cd into that directory.

First you will need to edit the conf/echo.properties file.

Read the comments in the file and adjust accordingly.

Most of the settings are straight forward, and there are not many.

There is a collection of 'todoListener' properties in there.  Other than the username and password, the todoListener properties
are what you will be most concerned with.


I'd delete all of them and start with this:
  todoListener.1=com.javasteam.amazon.echo.plugin.ShutdownTodoItemRetrievedPoller key=stop poller
  todoListener.2=com.javasteam.amazon.echo.plugin.CreateTodoPlugin key=new item,command="new item 1" "new item 2"

then run the run.sh (or run.bat on windows).

Give this command into your echo:
   'alexa add new item to my todo list'

Wait a few seconds and check your todo list (via the echo, phone app, or web).  You should see that a 'new item' todo was created
by your direct voice tommand to the echo.  When echo lib detected the todo item, it should mark that todo as completed, and 
add two new items.

After 10 minutes of running, the 'new item' todo should delete.

You can either kill echolib with ctrl-c, or you can say this to your echo:
  'alexa add stop poller to my todo list'

todoListener.1 will detect that todo item, mark it complete and shut the app down.

The most powerful plugin is com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin.  It will run an external application.  You can
do a lot with this.  Plus new plugins are easy to write, so additional features can be added by anyone that knows java.

Look at the examples in the original echo.properties file.  More docs to come.

--Dave
