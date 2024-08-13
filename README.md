### 데이터 베이스 DDL - 분리 TODO :: 

### 프로젝트 개요
- Spring Batch를 활용하여 CSV 파일을 읽고 데이터베이스(MySQL)에 CSV 데이터를 저장한다.

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
   - 큰 개선은 없음
3. 3차 시도 : 2차 시도 + 병렬 처리
   - 상세 : 쓰레드풀 활용 여러 쓰레드가 step을 병렬로 처리하며 작업(TaskExecutor)
   - Start Time : 2024-08-13 01:22:25.440823
   - End Time : 2024-08-13 01:31:09.707325
   - 소요시간 : 약 8m
   - Skip Count : 3 (2,157,824 insert)
   - 이슈 : FlatFileItemReader 스레드 세이프
4. 4차 시도 : 2차 시도 + Partitioning


### Processing Time 
| 시도 | 설명 | Start Time | End Time | 소요시간 | Skip Count | 비고 |
|------|------|------------|----------|----------|------------|------|
| 1차 시도 | 전체 데이터 1000개 Chunk 단일 테이블 Insert | 2024-08-13 00:07:14.774968 | 2024-08-13 00:27:16.926867 | 약 20m | 3 (2,157,824 insert) | |
| 2차 시도 | 1차 시도 + JPA Batch Insert 200개 | 2024-08-13 00:54:14.660754 | 2024-08-13 01:12:16.216284 | 약 18m | 3 (2,157,824 insert) | 큰 개선은 없음 |
| 3차 시도 | 2차 시도 + 병렬 처리 (TaskExecutor 사용) | 2024-08-13 01:22:25.440823 | 2024-08-13 01:31:09.707325 | 약 8m | 3 (2,157,824 insert) | FlatFileItemReader 스레드 세이프 문제 |


