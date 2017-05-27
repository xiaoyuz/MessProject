package mess.admin

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TripController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Trip.list(params), model:[tripCount: Trip.count()]
    }

    def show(Trip trip) {
        respond trip
    }

    def create() {
        respond new Trip(params)
    }

    @Transactional
    def save(Trip trip) {
        if (trip == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (trip.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond trip.errors, view:'create'
            return
        }

        trip.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'trip.label', default: 'Trip'), trip.id])
                redirect trip
            }
            '*' { respond trip, [status: CREATED] }
        }
    }

    def edit(Trip trip) {
        respond trip
    }

    @Transactional
    def update(Trip trip) {
        if (trip == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (trip.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond trip.errors, view:'edit'
            return
        }

        trip.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'trip.label', default: 'Trip'), trip.id])
                redirect trip
            }
            '*'{ respond trip, [status: OK] }
        }
    }

    @Transactional
    def delete(Trip trip) {

        if (trip == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        trip.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'trip.label', default: 'Trip'), trip.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'trip.label', default: 'Trip'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
