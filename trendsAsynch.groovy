import twitter4j.Twitter
import twitter4j.Query
import groovyx.gpars.Asynchronizer

@Grab(group='net.homeip.yusuke', module='twitter4j', version='2.0.10')
@Grab(group='org.codehaus.gpars', module='gpars', version='0.9')
def recentTweets(api, query) {
  tweets = api.search(new Query(query)).tweets
  (1..3).each { i ->
    println "${Thread.currentThread().name} ${query} ${i}: ${tweets[i].text}"
  }
}

def api = new Twitter()
def trends = api.weeklyTrends[0].trends[0..4]

Asynchronizer.doParallel {
  def retrieveTweets = { query -> recentTweets(api, query) }

  trends.each {
    retrieveTweets.callAsync(it.query)
  }
}