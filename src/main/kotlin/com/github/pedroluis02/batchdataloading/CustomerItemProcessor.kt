package com.github.pedroluis02.batchdataloading

import org.springframework.batch.item.ItemProcessor

class CustomerItemProcessor : ItemProcessor<Customer, Customer> {

    override fun process(item: Customer): Customer {
        return item
    }
}