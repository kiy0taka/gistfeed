import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.FilterOperator.*

datastore.prepare(new Query('extension')).asList(withOffset(100)).each {
    it.index = it.key.name[0].toLowerCase()
    it.save()
}
