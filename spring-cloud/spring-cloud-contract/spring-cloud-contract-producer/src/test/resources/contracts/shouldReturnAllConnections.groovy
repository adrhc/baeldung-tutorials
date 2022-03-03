package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return all connections"
	request {
		method GET()
		url("/connections") {
			queryParameters {
//				parameter("offset", 0)
				parameter("offset", $(c(matching("\\d+")), p(0)))
				parameter("limit", $(c(anInteger()), p(5)))
//				parameter("offset", $(c(anyInteger()), p(0)))       -> bad generated wiremock stub
//				parameter("limit", $(c(anyPositiveInt()), p(5)))    -> bad generated wiremock stub
				parameter("sort", $(c(regex("[\\+-]?[a-zA-Z0-9]+")), p("-startDate")))
				parameter("sort", $(c(regex("[\\+-]?[a-zA-Z0-9]+")), p("+type")))
				parameter("sort", $(c(regex("[\\+-]?[a-zA-Z0-9]+")), p("state")))
			}
		}
	}
	response {
		body([offset: 0, limit: 5])
		bodyMatchers {
			jsonPath('$.offset', byRegex("\\d+"))
			jsonPath('$.limit', byRegex(positiveInt()))
			jsonPath('$.sort[0]', byRegex("[\\+-]?[a-zA-Z0-9]+"))
			jsonPath('$.sort[1]', byRegex("[\\+-]?[a-zA-Z0-9]+"))
			jsonPath('$.sort[2]', byRegex("[\\+-]?[a-zA-Z0-9]+"))
		}
		headers {
			contentType(applicationJson())
		}
		status OK()
	}
}