
  
![UML 318](https://github.com/user-attachments/assets/3f1f54b3-9880-47f3-82bc-4be5f48d0632)

<div align="center">
  
# Daily Music Log

 </div>
 
## 기간 및 인원
- 2024.08.19 ~ 2024.10.10 (2개월)  /  AOS 1명(개인 프로젝트)


## 소개
- 오늘의 노래를 검색하고 간단한 메모와 함께 저장할 수 있는 어플



-----

## (1) 검색 페이지 / 선택 트랙 페이지 / 로컬 DB 저장 트랙 로드 페이지

<div align="center">
  
![image](https://github.com/user-attachments/assets/91c9ec23-62e7-43b0-9d16-873f4c0d0428)

 </div>

<div align="center">
  
### 트러블 슈팅(1)

#### < 상황 및 과제 >
![image](https://github.com/user-attachments/assets/3a8cd49f-41ea-481d-9157-87157f9e1a70)
  
 
 
①-[track.search] API의 { Track명 / Artist명 / 포스터URL } 의 실시간 데이터 값을 UI에 업데이트 했을 때,

**앨범 포스터 URL이 모두 기본 이미지로 로드되는 문제**

(이미지 URL이 모두 기본 이미지)

</div>

- ②-[track.getInfo] API에서 실제 포터스터 URL이 나오는 것을 발견
- Track / Artist 객체를 어떻게 담을 수 있을지 고민하다 기존 ① 요청 결과로 두 메서드의 값을 알 수 있으니 API①의 결과값으로, API② 데이터를 요청 받는 것으로 구상
- 먼저 두 API를 활용하여 데이터가 불러와지는 것을 확인하고 싶었기에
**"클릭한 데이터" 기준의 URL을 가져오기로 결정**
  
<div align="center">
  
#### 해결 및 과제

**[데이터 클래스 설계 오류]**

"하나의 검색어에 여러 앨범 데이터가 반환된다"는 가정하에 요청 받은 앨범 포스터 URL 반환 값을 List<Album> 으로 설정
▷ 두 APi를 호출하는 과정에서 반환 값이 틀리는 에러

- **(T)**
API 반환 구조와 실제 데이터의 관계에 따라 정확한 데이터 반환하도록 수정

- **(A)**
받을 Album 데이터 "들" 이라고 생각해 List로 표현했던 문제
하나의 검색어에 대한 여러 개의 앨범 정보를 담는 것이 아니라,
한 데이터당 한 앨범의 고유 정보를 담아
각 데이터들의 List<Track>이 만들어 진다는 것을 발견
2번째 API(포스터앨범)을 반환 받기 위해 List<Track2> 로 변경  

- **(A)-1**
mapNotNull 을 활용하여 유효한 데이터 리스트만 담으려 했지만,
mapNotNull은 결과를 List로 묶기에 반환 값이 List<List<Track2>>가 되는 문제로 flatten으로 평탄화 해주려 함

다만, 앨범 포스터 URL 하나만 가져오면 되는데, List로 가져오는 게 비효율적이라는 생각이 듦
필요 이상의 많은 데이터를 수집하게 됨으로 코드 변경

- **(A)-2**
특정 트랙의 앨범 포스터 URL만 가져오는 것에 집중하여 구조 최적화
"선택한 트랙 정보" 하나에 집중하여,
단일 API 호출로 해당 트랙의 앨범 이미지 URL만 가져오는 방식으로 변경

-----------------------------------------------------------

![image](https://github.com/user-attachments/assets/2c80def8-a250-4b73-b56b-79d5de7f5bac)

-----------------------------------------------------------

- **(R)**
불필요한 데이터 리스트가 아닌 필요한 앨범 포스터 URL만 가져오는 구조로 최적화하여 필요한 
데이터를 즉시 가져와 UI 업데이트 원활하게 이루어지도록 개선

---

#### **Coil과 Glide 비교**
**Glide가 메모리 사용량이 더 낮은 이유**
▶ 다층 캐싱 구조로 로드 전 메모리와 디스크 캐시를 확인하여 이미 로드한 이미지가 있으면 즉시 반환하여 네트워크 호출 및 메모리 사용량을 효율적으로 관리
▶ 뷰의 크기에 맞춰 이미지 리사이징

**Coil 선택 이유**
(1) 데이터가 많아질수록 더 빠르게 이미지 로드
▶ 코루틴을 사용하여 비동기 작업 효율적으로 처리
▶ Okhttp 등을 활용하여 네트워크 요청과 이미지 처리를 최적화

(2) Jetpack Compose에 최적화
▶ 'AsyncImage' 컴포저블을 사용하여 이미지를 쉽게 로드 가능하며,
빈번히 재구성되는 환경에서 생명주기가 호환되게 설계되어
컴포넌트가 화면에서 사라질 때 자동으로 취소되어
리소스도 자동으로 해제된다는 점과 메모리 누수 위험이 덜 발생



 </div>. 

## 기술스택
1. Compose
2. Clean Architecture
3. MVVM
4. Coil
5. REST API, Retrofit2, Okhttp3
6. Room
7. Coroutines / Flow
8. Dagger hilt

##### UI (User Interface) : Compose , Coil
##### 아키텍처 (Architecture) : Clean Architecture, MVVM (Model-View-ViewModel)
##### 네트워킹 (Networking) : REST API, Retrofit2, OkHttp3
##### 데이터 관리 (Data Management) : Room
##### 비동기 처리 및 데이터 흐름 (Asynchronous Processing and Data Flow) :Coroutines/Flow
##### 의존성 주입 (Dependency Injection) :Dagger Hilt



