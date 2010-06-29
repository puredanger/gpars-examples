import groovy.time.TimeCategory
import groovyx.gpars.GParsPool

@Grab(group='org.codehaus.gpars', module='gpars', version='0.10')
class Order {
	def Date dueDate	
	def Integer daysOverdue() {
		use(TimeCategory) {
			return (dueDate - new Date()).days
		}
	}
}

def createOrders() {
	def data = new ArrayList()
	
	use(TimeCategory) {
		def date = new Date()
	
		(0..99).each {
			order = new Order(dueDate: date)
			data.add(order)
			date = date + 1.day
		}
	}
	return data
}

def isLate = { order -> order.dueDate > new Date() }
def daysOverdue = { order -> order.daysOverdue() }

GParsPool.withPool {
	def data = createOrders().parallel.filter(isLate).map(daysOverdue)

	println("# overdue = " + data.size())
	println("avg overdue by = " + (data.sum() / data.size()))
}
