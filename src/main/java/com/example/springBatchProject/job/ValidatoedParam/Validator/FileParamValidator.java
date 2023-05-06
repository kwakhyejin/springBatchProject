package com.example.springBatchProject.job.ValidatoedParam.Validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class FileParamValidator implements JobParametersValidator {


    // 파일 네임이 유효한지 유효하지 않은지 검증
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("fileName");

        if(!StringUtils.endsWithIgnoreCase(fileName,"csv")){
            throw new JobParametersInvalidException("This is not csv file!!!");
        }
    }
}
