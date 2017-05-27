package mess.crawler

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/craw/part"(controller: 'craw', action: 'crawPart')
        "/craw/action_item"(controller: 'craw', action: 'crawActionItem')
        "/craw/action"(controller: 'craw', action: 'crawAction')
        "/craw/list"(controller: 'craw', action: 'list')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
