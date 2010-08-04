import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.SortDirection.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*
import com.google.appengine.api.datastore.*
import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat

def isoTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
def gists = memcache[params.extension ?: params.q]
if (gists == null) {
    System.out.println "Missing from memcache. [${params.extension ?: params.q}]"
    gists = memcache[params.extension ?: params.q] = datastore.prepare(new Query('gist')
        .addSort('dateCreated', DESCENDING)
        .addFilter(params.extension ? 'extensions' : 'files', EQUAL, params.extension ?: params.q)).asList(withLimit(20))
}
def dateUpdated = (gists.max { it.dateCreated } ?: [dateCreated:new Date()]).dateCreated

response.contentType = "application/atom+xml;charset=utf-8"

new MarkupBuilder(out).feed(xmlns: "http://www.w3.org/2005/Atom") {
    title "Gist Feed - ${params.extension ?: params.q}"
    link href: (params.extension ? "http://gistfeed.appspot.com/gist/${params.extension}/list" : "http://gistfeed.appspot.com/gist/list?q=${params.q}"), rel: "self"
    updated isoTime.format(dateUpdated)
    author {
        name "Kiyotaka Oku"
    }
    generator(uri: "http://gaelyk.appspot.com", version: "0.3.2", "Gaelyk lightweight Groovy toolkit for Google App Engine")
    gists.each { gist ->
        entry {
            id gist.gistNo
            title gist.gistNo
            link href: "http://gist.github.com/${gist.gistNo}"
            updated isoTime.format(gist.dateCreated)
            summary gist.files.join(', ')
            author {
                name gist.author
            }
        }
    }
}
