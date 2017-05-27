package mess.crawler

import org.jsoup.nodes.Element

class Action {

    Long id
    String name
    String description
    String img
    List<String> detailImgs
    List<String> details
    String videoUrl

    Action(Element element) {
        id = System.currentTimeMillis()
        name = element.select("div.content h2.name").first().text()
        def h4Elements = element.select("pre").first().select("h4")
        def ulElements = element.select("pre").first().select("ul")
        description = ""
        ulElements.eachWithIndex { item, index ->
            description += "${index + 1}.${h4Elements.get(index).text()}\n"
            def liElements = item.select("li")
            liElements.each {
                description += "${it.text()}\n"
            }
        }
        img = element.select("div.video-cover").first().attr("style").split("'")[1]
        videoUrl = element.select("div.video-wrapper").first().attr("data-src")

        detailImgs = []
        details = []
        def detailElments = element.select("div.detail")
        detailElments.each {
            detailImgs << it.select("div.img img").first().attr("src")
            def liElements = it.select("ol li")
            def detailStr = ""
            liElements.eachWithIndex { item, index ->
                detailStr += "${index + 1}.${item.text()}\n"
            }
            details << detailStr
        }
    }

    static constraints = {
        id unique: true, blank: false, nullable: false
        name blank: false, nullable: false
    }

    static mapping = {
        description type: 'text'
    }

    static belongsTo = [ part: Part ]
}
