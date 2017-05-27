package mess.crawler

import org.jsoup.Jsoup

class JsoupService {

    def getDocument(String url) {
        try {
            Jsoup.connect(url)
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; U;" +
                    "Intel Mac OS X 10.4; en-US; rv:1.9.2.2)" +
                    " Gecko/20100316 Firefox/3.6.2").timeout(10000)
                    .get()
        } catch (IOException e) {
            null
        }
    }

    def getDocumentIgnoreHtmlStyle(String url) {
        try {
            Jsoup.connect(url).ignoreContentType(true)
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; U;" +
                    "Intel Mac OS X 10.4; en-US; rv:1.9.2.2)" +
                    " Gecko/20100316 Firefox/3.6.2").timeout(10000)
                    .get()
        } catch (IOException e) {
            null
        }
    }

    def getDocumentByCode(String code) {
        def doc = Jsoup.parse(code)
        return doc
    }
}
