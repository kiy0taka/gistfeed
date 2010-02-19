import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.SortDirection.*
import com.google.appengine.api.datastore.*

if (params.delete != null) {
    new Entity(KeyFactory.createKey('error', params.gistNo.toLong())).delete()
    redirect 'errors.groovy'
} else if (params.retry != null) {
    def gistNo = params.gistNo.toLong()
    new Entity(KeyFactory.createKey('error', gistNo)).delete()
    defaultQueue << [
        countdownMillis:3000, url:'/task/fetch.groovy',
        taskName:"fetchPage-${gistNo}-${new Date().format('yyyyMMddHHmmssSSS')}",
        params:[gistNo: gistNo]
    ]
    redirect 'errors.groovy'
} else {
    def errors = datastore.prepare(new Query('error')).asList(withLimit(20))
    html.html {
        head { title 'Errors' }
        body {
            table {
                errors.each { error ->
                    tr {
                        td {
                            a error.key.id, target: "_blank", href: "http://gist.github.com/${error.key.id}"
                        }
                        td error.message
                        td {
                            a 'Delete', href: "errors.groovy?delete&gistNo=${error.key.id}"
                        }
                        td {
                            a 'Retry', href: "errors.groovy?retry&gistNo=${error.key.id}"
                        }
                    }
                }
            }
        }
    }
}

