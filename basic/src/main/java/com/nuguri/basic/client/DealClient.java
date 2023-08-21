package com.nuguri.basic.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "deal-service")
public interface DealClient {


}
