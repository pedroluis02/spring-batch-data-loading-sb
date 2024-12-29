package com.github.pedroluis02.batchdataloading

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
class BatchJobConfig {

    private companion object {
        const val JOB_NAME = "load-customers-job"
        const val ITEM_READER_NAME = "customers-reader"
        const val STEP_NAME = "write-customers-step"
    }

    private val columns = listOf(
        "id",
        "code",
        "name",
        "last_name",
        "company",
        "city",
        "country",
        "phone",
        "phone2",
        "email",
        "subscription_date",
        "website"
    )

    private val names = listOf(
        "id",
        "code",
        "name",
        "lastName",
        "company",
        "city",
        "country",
        "phone",
        "phone2",
        "email",
        "subscriptionDate",
        "website"
    )

    private val parameters: String = List(columns.count()) { _ -> "?" }
        .joinToString(",")

    @Bean
    fun reader(): FlatFileItemReader<Customer> {
        return FlatFileItemReaderBuilder<Customer>()
            .name(ITEM_READER_NAME)
            .resource(ClassPathResource("customers.csv"))
            .delimited()
            .names(*names.toTypedArray())
            .targetType(Customer::class.java)
            .build()
    }

    @Bean
    fun writer(dataSource: DataSource): JdbcBatchItemWriter<Customer> {
        return JdbcBatchItemWriterBuilder<Customer>()
            .sql("INSERT INTO customer (${columns.joinToString(",")}) VALUES ($parameters)")
            .dataSource(dataSource)
            .itemPreparedStatementSetter(CustomerItemSetter())
            .build()
    }

    @Bean
    fun loadCustomersJob(jobRepository: JobRepository, step1: Step, listener: JobNotificationListener): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .listener(listener)
            .start(step1)
            .build()
    }

    @Bean
    fun step1(
        jobRepository: JobRepository,
        transactionManager: DataSourceTransactionManager,
        reader: FlatFileItemReader<Customer>,
        writer: JdbcBatchItemWriter<Customer>
    ): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .chunk<Customer, Customer>(3, transactionManager)
            .reader(reader)
            .processor(CustomerItemProcessor())
            .writer(writer)
            .build()
    }
}
