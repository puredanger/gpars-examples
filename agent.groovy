import groovyx.gpars.agent.Agent

@Grab(group='org.codehaus.gpars', module='gpars', version='0.10')
def stuff = new Agent<List>([])

// thread 1
stuff.send( {it.add("pizza")} )

// thread 2 
stuff.send( {it.add("nachos")} )

println stuff.val
