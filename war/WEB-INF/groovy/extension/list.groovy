import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*

def page = (params.page ?: '1').toInteger()
def query = new Query('extension')
if (params.index) {
    query.addFilter('index', EQUAL, params.index)
}
def extensions = datastore.prepare(query).asList(withOffset((page - 1) * 20).limit(21))
def hasNext = extensions.size() == 21
request.extensions = hasNext ? extensions[0..-2] : extensions
request.hasPrev = !!(page - 1)
request.hasNext = hasNext
request.page = page
forward '/extension/list.gtpl'
