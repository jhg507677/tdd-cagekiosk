# Practical Testing: 실용적인 테스트 가이드(인프런)
인프런 강의 **「Practical Testing: 실용적인 테스트 가이드」** 를 수강하며 테스트 코드 작성 역량 강화를 위해 진행한 실습 프로젝트

## 프로젝트 목적
4년간 실무에서 JPA와 TDD를 사용하지 않는 환경에서 개발을 진행해왔고, 
JPA, 테스트 주도 개발과 테스트 설계 역량을 체계적으로 보완하기 위해 해당 강의를 수강하며 실습 코드를 정리

## 프로젝트 설명
### 기술 스택
Java, Spring Boot, JUnit 5, AssertJ, JPA (Hibernate)

### 도메인: 주문(Order) / 음료(Beverage)
본 프로젝트는 카페 주문 도메인을 기반으로 테스트를 설계합니다.
기본 요구사항 : 사용자는 카페 키오스크를 이용해 음료를 주문할 수 있다.

🧪 테스트 설계 방향
1. DisplayName을 섬세하게 작성 (CH_14)
- 단순한 명사 나열 ❌
- 행위 + 결과가 드러나는 문장형 DisplayName 지향
- 테스트 대상이 아닌 도메인 관점의 언어 사용
  ex) @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다.")
  “특정 시간 이전에 주문을 생성하면 실패한다” ❌
  → 도메인 규칙 중심으로 표현 ✔
2. BDD 스타일 테스트 (CH_15)
함수 단위 검증보다 시나리오 중심 테스트
개발자가 아닌 사람도 이해 가능한 추상화 수준 지향
Given / When / Then 구조
Given: 어떤 상황이 주어졌을 때
When: 어떤 행동을 하면
Then: 어떤 결과가 발생한다

3. 통합 테스트 중심 사고
단위 테스트만으로는 기능 전체의 신뢰성을 보장하기 어려움
여러 도메인과 모듈이 협력하는 흐름을 검증
실제 사용 시나리오에 가까운 테스트 작성 연습

#### 트러블 슈팅
- JUnit 버전 충돌 이슈  
Exception in thread "main"
java.lang.NoSuchMethodError:
'java.lang.String org.junit.platform.engine.discovery.MethodSelector.getMethodParameterTypes()'

원인  
기존 Spring Test 4.x 환경에서 JUnit5와 충돌 발생

해결  
JUnit Platform 버전 3.2.5로 변경,  
spring-test-webmvc → spring-test-web 변경