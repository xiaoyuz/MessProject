package mess.crawler

class CrawController {
	static responseFormats = ['json', 'xml']

    PartCrawService partCrawService

    def crawPart() {
        respond partCrawService.getPartList()
    }

    def crawActionItem() {
        respond partCrawService.getActionLists()
    }

    def crawAction() {
        respond partCrawService.getActions()
    }

    def list() {
        def parts = Part.list()
        respond parts
    }
}
