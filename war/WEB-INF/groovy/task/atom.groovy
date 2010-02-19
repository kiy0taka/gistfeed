import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.SortDirection.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import com.google.appengine.api.datastore.*

if (params.type == null) {
    def query = new Query('extension').addFilter('updated', EQUAL, true)
    datastore.prepare(query).asList(withOffset(0)).eachWithIndex { ext, i ->
        println ext.key.name
        queues.atom << [
            countdownMillis: (i+1) * 1000, url: '/task/atom.groovy',
            taskName: "atom-$i-${new Date().format('yyyyMMddHHmmss')}",
            params: [type: ext.key.name]
        ]
    }
} else {
    memcache[params.type] = datastore.prepare(new Query('gist')
        .addSort('dateCreated', DESCENDING)
        .addFilter('extensions', EQUAL, params.type)).asList(withLimit(20))
    def entity = datastore.get(KeyFactory.createKey('extension', params.type))
    entity.updated = false
    entity.save()
}
