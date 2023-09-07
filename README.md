# Kotlin-Dutch
<a href="https://play.google.com/store/apps/details?id=com.dutch2019">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
  </a>  
   

#### 더치는 입력한 위치들의 중간지점을 찾아주고 중간지점 근처의 대중교통, 문화시설, 음식점, 카페를 찾아 카카오톡으로 공유할 수 있는 서비스입니다.
![Frame 50](https://github.com/dev210202/Kotlin-Dutch/assets/32587845/34d9fd1c-4440-4089-a616-a320c3e1bbe5)

#### 사용기술
Java, Kotlin, MVVM, Coroutine, Retrofit, Room, DataBinding, LiveData, Hilt, TMap API, Kakao API

#### 설계 특징
가독성을 높이기 위해 서비스의 동작방식과 유사하게 코드를 작성하고자 했습니다.
추가와 삭제가 용이한 List를 사용하여 위치 정보를 저장합니다.
불변객체를 사용하고, getter/setter로 데이터를 접근하게하여 데이터를 안전하게 사용할 수 있도록 합니다.
가독성을 높이기 위해 Util 패키지를 사용하여 전역적으로 사용되는 함수, 여러 클래스에서 공통적으로 사용되는 함수, 의존성을  가지지않는 함수, 확장함수, Enum class 등을 묶어서 관리합니다.

#### 업데이트 내역 
1. 첫 출시 - 2019.10
2. 1차 업데이트 - 2020.07
3. 2차 업데이트 - 2020.10
4. 3차 업데이트 - 2021.11
5. 4차 업데이트 - 2023.07
