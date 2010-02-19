import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import static com.google.appengine.api.datastore.Query.SortDirection.*

def query = new Query('gist')
if (params.extension) {
    query.addFilter('extensions', EQUAL, params.extension)
}
if (params.next) {
    query.addFilter('gistNo', LESS_THAN_OR_EQUAL, params.next.toLong() - 1)
    query.addSort('gistNo', DESCENDING)
} else if (params.prev) {
    query.addFilter('gistNo', GREATER_THAN_OR_EQUAL, params.prev.toLong() + 1)
    query.addSort('gistNo', ASCENDING)
} else {
    query.addSort('gistNo', DESCENDING)
}

def gists = datastore.prepare(query).asList(withLimit(21))
request.gists = (gists.size() == 21 ? gists[0..-2] : gists).sort { l,r -> r.gistNo - l.gistNo }
if (params.next) {
    request.hasNext = gists.size() == 21
    request.hasPrev = (params.next != null)
} else if (params.prev) {
    request.hasNext = (params.prev != null)
    request.hasPrev = gists.size() == 21
} else {
    request.hasNext = gists.size() == 21
}

forward '/gist/list.gtpl'
