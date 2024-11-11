package contracts.rest

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'PUT'
        url ('/fees/1/pay')
        headers {
            contentType(applicationJsonUtf8())
        }
        body(
                id: 1,
                applicationNumber: A001,
                charge: 100,
                userId: user001,
        )
    }
    response {
        status 200
        body(
                id: 1,
                applicationNumber: A001,
                charge: 100,
        )
        bodyMatchers {
            jsonPath('$.id', byRegex(nonEmpty()).asLong())
            jsonPath('$.applicationnumber', byRegex(nonEmpty()).asString())
            jsonPath('$.charge', byRegex(nonEmpty()).asLong())
        }
        headers {
            contentType(applicationJsonUtf8())
        }
    }
}

