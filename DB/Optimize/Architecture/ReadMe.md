# 목차

* 테이블을 가능한 작게 만들어라.
* 인덱스를 활용하라.

# 테이블을 가능한 작게 만들어라.

> 테이블을 작게 만드는 것은 가장 기본적인 최적화 방법 중 하나이다.

인덱싱 또한 작은 컬럼에서 이루어진다면 자원을 덜 차지한다.

## 최소한의 테이블을 만드는 기법

1. MEDIUMINT 타입이 더 좋을 수 있다.

int 보다 medium int가 25% 작은 공간을 차지하기 때문에 가끔은 더 좋은 선택이 될 수 있다.

2. NOT NULL을 적극 도입하라.

NOT NULL로 선언하면 컬럼 당 1바이트의 공간을 절약할 수 있다.

3. 테이블의 주 인덱스는 가능한 한 작게 만든다.

4. 필요한 경우에만 인덱스를 사용한다.

인덱스는 추출 시에는 좋다. 하지만 데이터를 저장할 때에는 좋지 않다.

# 인덱스를 활용하라.

인덱스 공부 후 업데이트 예정


