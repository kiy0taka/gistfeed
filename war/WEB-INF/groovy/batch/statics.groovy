import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.SortDirection.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import com.google.appengine.api.datastore.*

def query = new Query('gist').addSort('gistNo', DESCENDING)
if (params.from) {
    query.addFilter('gistNo', LESS_THAN, params.from.toLong())
}

def gists = datastore.prepare(query).asList(withLimit(20))
gists.each {
    def dayKey = 'd' + it.dateCreated.day
    def hoursKey = 'h' + it.dateCreated.hours
    params[dayKey] = ((params[dayKey]?:'0').toInteger()+1).toString()
    params[hoursKey] = ((params[hoursKey]?:'0').toInteger()+1).toString()
}

if (gists) {
    def from = gists[-1].gistNo
    params.from = from
    defaultQueue << [
        countdownMillis:1000, url:'/batch/statics.groovy',
        taskName:"statics-$from-${new Date().format('yyyyMMddHHmmssSSS')}",
        params:params
    ]
} else {
    def statics = new Entity('statics')
    statics.date = new Date()
    (0..23).each {
        statics['h'+it] = (params['h'+it]?:'0').toInteger()
    }
    (0..6).each {
        statics['d'+it] = (params['d'+it]?:'0').toInteger()
    }
    statics.save()
}
