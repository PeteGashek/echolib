Echolib
=======
Send tweets and start programs from your Amazon echo.

-- comment --
I didn't like the usage of 'todo' items for identifying tasks.  It seems like 'todo' processing at amazon performs extra procession on the text to clean it up when it's a todo.

Instead I personally am using the 'simon says' feature.  This just tries to have echo repeat what you said.  Here's a snippet from my config file (I changed the todo configuration as well so that's captured here):

# todo configuration
todoPollingInterval=15
todoPollingItemCount=100
cancelledMinutesToLive=1440
todoListener.1=com.javasteam.amazon.echo.plugin.Builtin:shutdownTodoPoller key=halt echo
todoListener.2=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=Twitter
todoListener.3=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=Twit
todoListener.4=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=Tweet
todoListener.5=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=Notify twitter
todoListener.6=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=Send twitter

# activity configuration
activityPollingInterval=15
activityPollingItemCount=30
activityListener.1=com.javasteam.amazon.echo.plugin.Builtin:shutdownTodoPoller key=alexa simon says halt echo
activityListener.2=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=alexa simon says tweet
activityListener.3=com.javasteam.amazon.echo.plugin.Twitter:sendTwit key=alexa simon says twit

For activities you need to include your wakeup command (alexa or amazon) plus 'simon says'.

Echolib does not clean up your history like it does todos.  So when you restart, it will retry commands that is has already processed.  It keeps track by timestamp while running.  -- future feature.

Name change pending....

Echolib Deeper Dive
===================
Echolib is a tool for configuration driven automation of tasks driven by the Amazon Echo Todo list.
At it's core Echolib provides a java library for interacting the Echo that can be leveraged in other applications.  Interacting with the Echo account at amazon is based on a User Session.  The UserSession object is executable and supports executable task plugins that can be triggered by the content of Amazon Echo Todo lists.  A few plugins are provided so that Echolib is useful right out of the box.  This is all configurable and new plugins can be added simply and easily.  It is written in Java and can be run on Linux, windows or Mac OS X.


Special Thanks
==============
Specal thanks to noelportugal (AmazonEchoApi project on github) where I first saw the concept of using the echo todo list in this way.  Though I have re-factored it heavily, his code formed the initial basis of this effort.  


Running echolib
===============
Make sure you have java version 8 installed and that it is in your path.

Download the zip file from the zip directory of the application.

Unzip it into a location of your choosing.  This will create an echo directory.

Edit the file 'echo/config/echo.properties

The included version looks like this:

```
# key phrases in here are case sensitive in here.
# the actual 'key phrase' from the todo item is not, but other than that assume you have to use the same case
# as is in this sample. For instance 'todoListener.1=' is valid, while 'TODOLISTENER.1=' is not.
# put your credentials here
username=someuser
password=someuserspassword

# set how often we check for todos (in seconds)
pollingInterval=10

# set the number of items to pull for the request.  100 is probably over kill
pollingItemCount=100

# afer a todo is processed, it gets marked as complete.  After 'cancelledMinutesToLive' iit gets marked as deleted.
cancelledMinutesToLive=10

# Add todo listeners to perform tasks from todo events
# the format of these lines is:
# todoListener.#=<class> key=<key phrase>,command=<options>
# Replace the # with the sequence of listener.  This is not very smart it starts at one and increments the count until it does ot find a number.  It you leave a numerial
# gap, it won't load any listeners after the gap.
# The key phrase can be a string of words if you want.  The command is separated by a comma, so you can't have a comma in the key phrase.
# the command string is specific to the class you are using.  For the ExecuteExternalProecessPlugin it is the command line parameters
# for the command you want to execute, starting with the command itself.  If any element needs to have a spave in it then you have to enclose tha element in double quotes
# You can use the transposition key '%text%' which will be replacesd by everything in the todo item after the key phrase.
#
# This first one will exit the program when the key phrase 'stop poller' is at te beginning of a todo item.
# change the key phrase if you want.  The ShutdownTodoItemRetrievedPoller just exits the utility.
todoListener.1=com.javasteam.amazon.echo.plugin.ShutdownTodoItemRetrievedPoller key=stop poller

# here is an example of calling cygwmin bash.exe to run a bask script passing in all of the text ater the keyword (script) as the $1 argument to the script
# this one will not be useful o you, but, you can do the same thing with an editor, an mp3 player, or whatever.
todoListener.2=com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin key=script,command=c:/cygwin64/bin/bash.exe c:/scripts/script1.sh %text%

# Another example using the %text% transposition in an editor.
todoListener.3=com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin key=edit,command="c:/Program\ Files\ \(x86\)/Vim/vim74/gvim.exe" %text%

# THis one launches the game 'skyrim' installed from steam with the phrase 'Alexa add play skyrim to my todo list'
# yeah nerdy, but cool.  In a nerdy way.
todoListener.4=com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin key=play skyrim,command="c:/Program\ Files\ \(x86\)/Steam/SteamApps/common/Skyrim/skse_loader.exe"

# This is an example of what I call todo chaining. CreateTodoPlugin will create one or more new todos.  These todos can cause other events to occur
# on subsequent todo list processing.
# this example isn't very useful since it starts skyrim and a text editor
todoListener.5=com.javasteam.amazon.echo.plugin.CreateTodoPlugin key=create plug in,command="play skyrim" "edit c:/someFile.txt"

# You can have as many processes handle a single todo item as you want.  This one starts notepad editng the script we started in todoListener.2
# note that we are using the same key phrase as in todoListener.2 (script).  So if you add a todo item that begins with "script', foth todoListener 2 and 6
# will get run for that todo item.
todoListener.6=com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin key=script,command=c:/WINDOWS/System32/notepad.exe c:/scripts/script1.sh
```  

Update the username and password fields with your amazon echo account credentials and save ithe file.
The echo directory has to be your working directory as all of the paths are relative to that.  So from a command prompt (bash shell in unix, CMD.exe in Windows)ruen either the run.sh (unix) or run.bat (windows).  You should see something like this:

```
$ ./run.bat

E:\packageEcho\echo>set CLASSPATH=./config;./lib/commons-codec-1.6.jar;./lib/commons-exec-1.3.jar;./lib/commons-logging-1.2.jar;./lib/echolib-0.0.1-SNAPSHOT.jar;./lib/hamcrest-core-1.1.jar;./lib/httpclient-4.3.6.jar;./lib/httpcore-4.4.jar;./lib/jackson-annotations-2.5.1.jar;./lib/jackson-core-2.5.1.jar;./lib/jackson-databind-2.5.1.jar;./lib/json-simple-1.1.1.jar;./lib/jsoup-1.7.3.jar;./lib/junit-4.10.jar;./lib/log4j-1.2.17.jar

E:\packageEcho\echo>java -cp ./config;./lib/commons-codec-1.6.jar;./lib/commons-exec-1.3.jar;./lib/commons-logging-1.2.jar;./lib/echolib-0.0.1-SNAPSHOT.jar;./lib/hamcrest-core-1.1.jar;./lib/httpclient-4.3.6.jar;./lib/httpcore-4.4.jar;./lib/jackson-annotations-2.5.1.jar;./lib/jackson-core-2.5.1.jar;./lib/jackson-databind-2.5.1.jar;./lib/json-simple-1.1.1.jar;./lib/jsoup-1.7.3.jar;./lib/junit-4.10.jar;./lib/log4j-1.2.17.jar com.javasteam.amazon.echo.EchoUserSession
2015-02-20 12:42:11 INFO  EchoUserSession:341: (main) - Added com.javasteam.amazon.echo.plugin.ShutdownTodoItemRetrievedPoller as a listener for key: stop poller
2015-02-20 12:42:11 INFO  EchoUserSession:341: (main) - Added com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin as a listener for key: script
2015-02-20 12:42:11 INFO  EchoUserSession:341: (main) - Added com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin as a listener for key: edit
2015-02-20 12:42:11 INFO  EchoUserSession:341: (main) - Added com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin as a listener for key: play skyrim
2015-02-20 12:42:11 INFO  EchoUserSession:341: (main) - Added com.javasteam.amazon.echo.plugin.CreateTodoPlugin as a listener for key: create plug in
2015-02-20 12:42:11 INFO  EchoUserSession:341: (main) - Added com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin as a listener for key: script
2015-02-20 12:42:11 INFO  EchoBase:340: (Thread-1) - user is logged in....false

```
The 'user is logged in....false' at the end is just logging prior to logging you into Amazon.

If your credentials fail, you will see a lot of crud scroll by after that.  Otherwise it will sit there like that polling for todos that it can handle.  If all looks good, give this command to your echo:
```
 'alexa add stop poller to my todo list'
```

Within a few seconds echolib should exit back to the prompt.

There are currently three plugins available:
1) com.javasteam.amazon.echo.plugin.ShutdownTodoItemRetrievedPoller -- this plugin you just used to exit echolib
2) com.javasteam.amazon.echo.plugin.CreateTodoPlugin -- this plugin creates new todos in the amazon echos todo list
3) com.javasteam.amazon.echo.plugin.ExecuteExternalProcessPlugin -- this plugin executes a program using the command line



Additional info
===============

Build with maven
run with java 8 (make sure hava 8 is in your path).

Most of the settings in the config are straight forward, and there are not many in there anyway. Comments start the line with '#' and 
are just there to be informational.

There is a collection of 'todoListener' properties near the end.  Other than the username and password, the todoListener properties
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
