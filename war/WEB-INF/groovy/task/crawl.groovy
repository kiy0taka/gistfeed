import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static com.google.appengine.api.datastore.Query.SortDirection.*
import com.google.appengine.api.datastore.*
import org.cyberneko.html.parsers.SAXParser

def query = new Query('gist').addSort('gistNo', DESCENDING)
def to = (params.to ?: (((datastore.prepare(query).asList(withLimit(1))) ?: [[gistNo:'-1']])[0]).gistNo).toLong()

def url = new URL('http://gist.github.com/gists' + (params.page ? "?page=${params.page}" : ""))
def c = url.openConnection()
c.setRequestProperty('User-Agent', 'GistFeedBot/beta1(http://gistfeed.appspot.com/)')

def html = new XmlParser(new SAXParser()).parseText(c.inputStream.getText('UTF-8'))
def gists = html.'**'.DIV.findAll { it.'@class' == 'file public' }.collect {
    [gistNo: it.DIV.DIV.SPAN.A.'@href'[0][1..-1].toLong(),
        dateCreated: it.DIV.DIV[1].ABBR.'@title'[0]]
}

def count = 1
gists.eachWithIndex { gist, i ->
    def gistNo = gist.gistNo
    if (((params.from == null || params.from.toLong() > gistNo)) && to < gistNo) {
        println gistNo
        defaultQueue << [
            countdownMillis:(count++ * 3000), url:'/task/fetch.groovy',
            taskName:"fetch-${gistNo}-${new Date().format('yyyyMMddHHmmssSSS')}",
            params:[gistNo: gistNo, dateCreated: gist.dateCreated]
        ]
    }
}

if (to >= 0 && gists[0].gistNo > to) {
    def nextPage = (params.page ?: '1').toLong() + 1
    defaultQueue << [
        countdownMillis:(count+1) * 3000, url:'/task/crawl.groovy',
        taskName:"crawl-$nextPage-${gists[-1].gistNo}-$to-${new Date().format('yyyyMMddHHmmssSSS')}",
        params:params.from ? [from:params.from, to:to, page:nextPage] : [to:to, page:nextPage]
    ]
}
