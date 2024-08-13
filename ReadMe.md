### 데이터 베이스 DDL - 분리 TODO :: 

### 구현 내용 요약

### 설정 가이드 

### 실행 가이드

### 테스트 가이드 

### 개발 버전
- JDK 17 (Amazon Corretto 17.0.10)
- Spring Framework 6.1.3
- Spring Batch 5.1.2
- mysql 8.0.32
- gradle 8.8


### Processing Time
1. 1차 시도 : 전체 데이터 1000개 Chunk 단일 테이블 Insert
   - Start Time : 2024-08-13 00:07:14.774968
   - End Time : 2024-08-13 00:27:16.926867
   - 소요시간 : 약 20m
   - Skip Count : 3 (2,157,824 insert)
2. 2차 시도 : 1차 시도 + JPA Batch Insert 200개
   - Start Time : 2024-08-13 00:54:14.660754
   - End Time : 2024-08-13 01:12:16.216284
   - 소요시간 : 약 18m
   - Skip Count : 3 (2,157,824 insert)
3. 3차 시도 : 2차 시도 + 병렬 처리
   - Start Time : 2024-08-13 01:22:25.440823
   - End Time : 2024-08-13 01:31:09.707325
   - 소요시간 : 약 8m
   - Skip Count : 3 (2,157,824 insert)
   - 이슈 스레드 세이프


