import groovy.time.TimeCategory
import groovyx.gpars.Parallelizer
import groovyx.gpars.ParallelArrayUtil
import static groovyx.gpars.Parallelizer.*

@Grab(group='org.codehaus.gpars', module='gpars', version='0.9')
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

doParallel {
	def data = ParallelArrayUtil.getParallel(createOrders()).filter(isLate).map(daysOverdue)

	println("# overdue = " + data.size())
	println("avg overdue by = " + (data.sum() / data.size()))
}
