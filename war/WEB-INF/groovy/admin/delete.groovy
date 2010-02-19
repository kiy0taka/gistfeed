import com.google.appengine.api.datastore.*
datastore.get(KeyFactory.createKey('extension', params.extension)).delete()
redirect "/${params.page?'?page='+params.page:''}"
