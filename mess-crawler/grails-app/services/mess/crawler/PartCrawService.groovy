package mess.crawler

import grails.gorm.transactions.Transactional

@Transactional
class PartCrawService {

    JsoupService jsoupService

    @Transactional(readOnly = false)
    def getPartList() {
        def parts = []
        String urlPrefix = Contants.KEEP_HOST << Contants.TRAINING_PATH
        def doc = jsoupService.getDocument(urlPrefix)
        def elements = doc.select("ul.training-point li")
        elements.each {
            def part = new Part(it)
            part.save()
            parts << part
        }
        return parts
    }

    @Transactional(readOnly = false)
    def getActionLists() {
        def parts = Part.list()
        def actionItems = []
        parts.each { part ->
            def partUrl = part.url
            def doc = jsoupService.getDocument(Contants.KEEP_HOST + partUrl)
            def nextPageUrl = Contants.KEEP_HOST + partUrl + "&scrollId=" + doc.select("div.keep-loading-more").first().attr("data-url").split("&scrollId=")[1]
            // First page
            def elements = doc.select("ul.exercises li")
            elements.each {
                def item = new ActionItem(it)
                item.part = part
                item.save()
                actionItems << item
            }
            // Other page
            while (true) {
                def currentDoc = jsoupService.getDocumentIgnoreHtmlStyle(nextPageUrl)
                if (currentDoc == null) {
                    break
                }
                def currentPageCode = currentDoc.html()
                currentPageCode = currentPageCode.replace("\\&quot;", "")
                currentPageCode = currentPageCode.replace("\n", "")
                def currentPageItems = []
                elements = jsoupService.getDocumentByCode(currentPageCode).select("li")
                elements.each {
                    def item = new ActionItem(it)
                    item.part = part
                    item.save()
                    currentPageItems << item
                }
                if (currentPageItems.isEmpty()) {
                    break
                }
                actionItems += currentPageItems
            }
        }
        return actionItems
    }

    @Transactional(readOnly = false)
    def getActions() {
        def actionItems = ActionItem.list()
        def actions = []
        actionItems.each { actionItem ->
            def url = Contants.KEEP_HOST + actionItem.getUrl()
            def doc = jsoupService.getDocument(url)
            try {
                def action = new Action(doc)
                action.part = actionItem.part
                action.save()
                actions << action
            } catch (Exception e) {
            }
        }
        return actions
    }
}
