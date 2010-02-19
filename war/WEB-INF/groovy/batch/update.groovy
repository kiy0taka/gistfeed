import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.SortDirection.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import com.google.appengine.api.datastore.*

def query = new Query('gist').addSort('gistNo', DESCENDING)
if (params.from) {
    query.addFilter('gistNo', LESS_THAN, params.from.toLong())
}

def gists = datastore.prepare(query).asList(withLimit(10))
gists.each {
    it.dateCreated.hours -= 9
    System.out.println it.gistNo
    it.save()
}

if (gists) {
    def from = gists[-1].gistNo
    defaultQueue << [
        countdownMillis:1000, url:'/batch/update.groovy',
        taskName:"update-$from-${new Date().format('yyyyMMddHHmmssSSS')}",
        params:[from:from]
    ]
}
