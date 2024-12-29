package com.github.pedroluis02.batchdataloading

import org.springframework.batch.item.database.ItemPreparedStatementSetter
import java.sql.PreparedStatement

class CustomerItemSetter : ItemPreparedStatementSetter<Customer> {

    override fun setValues(item: Customer, ps: PreparedStatement) {
        val values = listOf(
            item.id,
            item.code,
            item.name,
            item.lastName,
            item.company,
            item.city,
            item.country,
            item.phone,
            item.phone2,
            item.email,
            item.subscriptionDate,
            item.website
        )
        values.forEachIndexed { index, value ->
            ps.setObject(index + 1, value)
        }
    }
}
