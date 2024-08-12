package com.example.restaurant.batch.item;

import com.example.restaurant.entity.RestaurantInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@RequiredArgsConstructor
public class CsvReader {

    @Bean
    public FlatFileItemReader<RestaurantInfo> csvContentReader() {//RestaurantInfoDto
        //"번호","개방서비스명","개방서비스아이디","개방자치단체코드","관리번호","인허가일자","인허가취소일자","영업상태구분코드","영업상태명","상세영업상태코드","상세영업상태명","폐업일자","휴업시작일자","휴업종료일자","재개업일자","소재지전화","소재지면적","소재지우편번호","소재지전체주소","도로명전체주소","도로명우편번호","사업장명","최종수정시점","데이터갱신구분","데이터갱신일자","업태구분명","좌표정보(x)","좌표정보(y)","위생업태명","남성종사자수","여성종사자수","영업장주변구분명","등급구분명","급수시설구분명","총직원수","본사직원수","공장사무직직원수","공장판매직직원수","공장생산직직원수","건물소유구분명","보증액","월세액","다중이용업소여부","시설총규모","전통업소지정번호","전통업소주된음식","홈페이지"
        FlatFileItemReader<RestaurantInfo> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/fulldata_07_24_04_P_일반음식점.csv"));
        flatFileItemReader.setEncoding("EUC-KR");


        // delimitedLineTokenizer : csv 파일에서 구분자 지정하고 구분한 데이터 setNames를 통해 각 이름 설정
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("번호","개방서비스명","개방서비스아이디","개방자치단체코드","관리번호","인허가일자","인허가취소일자","영업상태구분코드","영업상태명","상세영업상태코드","상세영업상태명","폐업일자","휴업시작일자","휴업종료일자","재개업일자","소재지전화","소재지면적","소재지우편번호","소재지전체주소","도로명전체주소","도로명우편번호","사업장명","최종수정시점","데이터갱신구분","데이터갱신일자","업태구분명","좌표정보(x)","좌표정보(y)","위생업태명","남성종사자수","여성종사자수","영업장주변구분명","등급구분명","급수시설구분명","총직원수","본사직원수","공장사무직직원수","공장판매직직원수","공장생산직직원수","건물소유구분명","보증액","월세액","다중이용업소여부","시설총규모","전통업소지정번호","전통업소주된음식","홈페이지","lasttest"); //행으로 읽은 데이터 매칭할 데이터 각 이름


        // defaultLineMapper: 읽으려는 데이터 LineMapper을 통해 Dto로 매핑
        DefaultLineMapper<RestaurantInfo> defaultLineMapper = new DefaultLineMapper<>(); //TODO :: Dto통으로 변경 이후 프로세서에서 엔티티로 쪼개기
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        // beanWrapperFieldSetMapper: 매칭할 class 타입 지정
        BeanWrapperFieldSetMapper<RestaurantInfo> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(RestaurantInfo.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);
        flatFileItemReader.setLinesToSkip(1); // 헤더패스

        return flatFileItemReader;

    }

}
