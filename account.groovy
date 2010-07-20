import groovyx.gpars.agent.Agent

/**
 * Define a BankAccount as an Agent managing a field of type Long holding the value 
 * in dollars.
 */
@Grab(group='org.codehaus.gpars', module='gpars', version='0.10')
class BankAccount extends Agent<Long> {
  def BankAccount() { super(0) }
  private def deposit(long deposit) { data += deposit }
  private def withdraw(long deposit) { data -= deposit }
}

final BankAccount acct = new BankAccount()

final Thread atm2 = Thread.start { 
  acct << { withdraw 200 }
}

final Thread atm1 = Thread.start { 
  acct << { deposit 500 }
}

[atm1,atm2]*.join()
println "Final balance: ${ acct.val }"
