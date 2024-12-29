package com.github.pedroluis02.batchdataloading

data class Customer(
    var id: Long = 0L,
    var code: String = "",
    var name: String = "",
    var lastName: String = "",
    var company: String = "",
    var city: String = "",
    var country: String = "",
    var phone: String = "",
    var phone2: String = "",
    var email: String = "",
    var subscriptionDate: String = "",
    var website: String = ""
)
