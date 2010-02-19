import com.google.appengine.api.datastore.*
import org.cyberneko.html.parsers.SAXParser

def gistNo = params.gistNo.toLong()

try {
    def url = new URL("http://gist.github.com/$gistNo")
    def c = url.openConnection()
    c.setRequestProperty('User-Agent', 'GistFeedBot/beta1(http://gistfeed.appspot.com/)')
    def html = new XmlParser(new SAXParser()).parseText(c.inputStream.getText('UTF-8'))

    def gist = new Entity(KeyFactory.createKey('gist', "$gistNo"))
    gist.gistNo = gistNo
    gist.files = []
    gist.extensions = [] as Set

    if (params.dateCreated) {
        gist.dateCreated = Date.parse('yyyy-MM-dd HH:mm:ss', params.dateCreated)
    } else {
        gist.dateCreated = Date.parse('yyyy-MM-dd HH:mm:ss', html.'**'.LI.find { it.'@class' == 'current' }.'**'.ABBR.'@title'[0])
    }
    gist.dateCreated.hours += 8

    def div = html.'**'.DIV.find { it.'@class' == 'name' }
    gist.author = (div.A ?: div).text()

    html.'**'.SPAN.findAll { it.'@class' == 'code' }.each {
        def fileName = it.text()
        gist.files << fileName
        def m = (it.A.'@href'[0] =~ /.*\.([a-zA-Z][-a-zA-Z0-9_]*$)/)
        if (m) {
            def ext = m[0][1].trim()
            def entity = new Entity(KeyFactory.createKey('extension', ext))
            entity.updated = true
            entity.index = ext[0].toLowerCase()
            entity.save()
            gist.extensions << ext
        }
    }

    gist.save()
} catch (e) {
    def error = new Entity(KeyFactory.createKey('error', gistNo))
    error.message = e.message
    error.dateCreated = new Date()
    error.save()
}
