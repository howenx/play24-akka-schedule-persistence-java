#Akka Schedule Persistence
&nbsp;&nbsp;&nbsp;&nbsp;It's a solution for akka scheduler persistence because akka schedule persistence is not supported for akka official.
##Solution Analysis
&nbsp;&nbsp;&nbsp;&nbsp;Akka's scheduler is convenient,of cause we can use like Quartz util to start a schedule,but they are not lightly for develement,so I look for a method to solve it.

	persist entity ----> akka actor path and scheduler created time,delay, Cancellable of actor returned.
	leveldb ---> Save message for key,Persist Object for value.
	Cancellable HashMap ---> Save Persist Object for cancel had scheduled scheduler.
	Delete Actor HashMap ---> Save message and remove persistent schedule Persist Object from Cancellable HashMap and leveldb.

	when a schedule started, first save it to leveldb and hashmap.
	Why use HashMap save Persist Object? Because the akka scheduler's returned object(Cancellable) can't be serializable.
	Then start a schedule delete the persistent object from hashmap and leveldb,5 seconds after the schedule excute completed.
	Finally,sending the first http request for execute the iterate of leveldb when the application is on start.

##Contributions

Any helpful feedback is more than welcome. This includes feature requests, bug reports, pull requests, constructive feedback, etc.

##Copyright & License

Licensed under the terms of the Apache License, Version 2.0.
