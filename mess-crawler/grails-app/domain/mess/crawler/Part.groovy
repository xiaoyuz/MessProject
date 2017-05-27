package mess.crawler

import org.jsoup.nodes.Element

class Part {

    Long id
    String name
    String img
    String url

    Part(Element element) {
        id = System.currentTimeMillis()
        url = element.select("a").first().attr("href")
        img = element.select("div.img img").first().attr("src")
        name = element.select("div.name").first().text()
    }

    static constraints = {
        id unique: true, blank: false, nullable: false
        name blank: false, nullable: false
    }

    static hasMany = [ actionItems : ActionItem, action: Action]
}
