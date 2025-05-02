package com.example.demo.filters

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SignatureFilter :
    GlobalFilter,
    Ordered {
    override fun filter(
        exchange: ServerWebExchange,
        chain: GatewayFilterChain,
    ): Mono<Void> {
        if (exchange.request.headers["X-Signature"] == null) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            return exchange.response.setComplete()
        }

        return chain.filter(exchange)
    }

    override fun getOrder(): Int = Ordered.LOWEST_PRECEDENCE
}
