package com.moon.bankspringbatch;


import com.moon.bankspringbatch.dao.BankTransaction;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired private ItemWriter<BankTransaction> bankTransactionItemWriter;
    //@Autowired private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;

        @Bean
        public Job bankJob(){
            Step step1 = stepBuilderFactory.get("STEP 1  load Data")
                    .<BankTransaction,BankTransaction>chunk(1000)
                    .reader(bankTransactionItemReader)
                    .processor(compositeItemProcessor()) // composition de plusieurs de processor ou pipeline , listeChaine de processor
                    .writer(bankTransactionItemWriter)
                    .build();

            return jobBuilderFactory.get("Chargement Job")
                    .start(step1)
                    .build();
        }

        @Bean
    public ItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor() {
        List<ItemProcessor<BankTransaction,BankTransaction>> itemProcessorList = new ArrayList<>();
            itemProcessorList.add(itemProcessor1());
            itemProcessorList.add(itemProcessor2());

            CompositeItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor = new CompositeItemProcessor<>();
            compositeItemProcessor.setDelegates(itemProcessorList);
            return compositeItemProcessor;
    }

    // on definit ici des bean pour instancier les objets et on commente l'annotaton @Component des classes
    // BankTransactionProcessor(); et BankTransactionItemAnalyticsProcessor();
    @Bean
    BankTransactionProcessor itemProcessor1(){
            return new BankTransactionProcessor();
    }

    @Bean
    BankTransactionItemAnalyticsProcessor itemProcessor2(){
            return new BankTransactionItemAnalyticsProcessor();
    }

    // Definition d'un bean de retourne un objet de type FlatFileItemReader
    @Bean
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource resource){
            FlatFileItemReader<BankTransaction> FileItemReader = new FlatFileItemReader<>();
            FileItemReader.setName("CSV-READER"); // type de fichier
            FileItemReader.setLinesToSkip(1); // ignore la premiere ligne
            FileItemReader.setResource(resource); // la ressource du fichier
            FileItemReader.setLineMapper(lineMapper());// methode
            return FileItemReader;
    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(","); // type de delimiter
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","accountID","strTransactionDate","transactionType","amount");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

}
