import groovyx.gpars.actor.Safe

@Grab(group='org.codehaus.gpars', module='gpars', version='0.9')
def stuff = new Safe<List>([])

// thread 1
stuff.send( {it.add("pizza")} )

// thread 2 
stuff.send( {it.add("nachos")} )

println stuff.val