package mess.crawler

import org.jsoup.nodes.Element

class ActionItem {

    Long id
    String name
    String url
    String imgUrl

    ActionItem(Element element) {
        id = System.currentTimeMillis()
        name = element.select("div.name").first().text()
        url = element.select("a").first().attr("href")
        imgUrl = element.select("div.img").first().attr("data-background")
    }

    static constraints = {
        id unique: true, blank: false, nullable: false
        name blank: false, nullable: false
    }

    static belongsTo = [ part: Part ]
}
