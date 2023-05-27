package com.lewis.boot.batch.config;

import com.lewis.boot.batch.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Autowired
    DataSource dataSource;


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job job(){
        return new JobBuilder("job-1", jobRepository).flow(step()).end().build();
    }

    @Bean
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder("step-1", jobRepository);
        return stepBuilder.<Product,Product>chunk(4, transactionManager)
                .reader(reader()).processor(processor())
                .writer((writer()))
                .build();
    }

    @Bean
    public ItemReader<Product> reader(){

        //Responsible for reading the flat file and converting into an object of type product
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
        //Configured a resource, i.e. products.csv
        reader.setResource(new ClassPathResource("com/lewis/boot/batch/products.csv"));

        //Responsible for mapping each line into a product object
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();

        //Responsible for reading each token separated by a comma, then set these fields into Java dynamically
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "name", "description", "price");

        //Responsible for taking each variable and setting into object of product type
        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Product.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        //Setting lineMapper on reader
        reader.setLineMapper(lineMapper);

        return null;
    }

    //See whether can impose checking conditions on processor
    @Bean
    public ItemProcessor<Product, Product> processor(){

        return (p)->{
            p.setPrice(p.getPrice()-p.getPrice()*0.1);
            return p;
        };
    }

    @Bean
    public ItemWriter<Product> writer(){
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
        writer.setSql("INSERT INTO PRODUCT (ID,NAME,DESCRIPTION,PRICE) VALUES (:id,:name,:description,:price)");
        return writer;
    }
}
