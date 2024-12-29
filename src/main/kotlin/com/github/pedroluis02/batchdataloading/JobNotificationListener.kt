package com.github.pedroluis02.batchdataloading

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JobNotificationListener(private val jdbcTemplate: JdbcTemplate) : JobExecutionListener {

    private companion object {
        val log: Logger = LoggerFactory.getLogger(JobNotificationListener::class.java)
    }

    override fun beforeJob(jobExecution: JobExecution) {
        log.info("before job: {}", jobExecution.jobId)
    }

    override fun afterJob(jobExecution: JobExecution) {
        log.info("after job: {}", jobExecution.jobId)

        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info("job completed")
            jdbcTemplate.query("select * from customer", DataClassRowMapper(Customer::class.java))
                .forEach { log.info("{}", it) }
        }
    }
}
