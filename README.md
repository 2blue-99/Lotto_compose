
![행운 로또 추첨](https://dl.dropbox.com/scl/fi/kc4q3ooec0vfkc69n9yqr/lotto_read_me_image.png?rlkey=dfsmbjnpo3qteqeh7h0vxq324&st=6edzpa64&dl=0 "행운 로또 추첨")

<a href="https://play.google.com/store/apps/details?id=com.lucky_lotto.mvi_test"><img src="https://play.google.com/intl/ko_kr/badges/static/images/badges/ko_badge_web_generic.png" height="70"></a> <a>&nbsp;&nbsp;</a>

<br>

# 행운 로또 추첨
> 💡 **키워드 로또 추첨과 통계 로또 추첨을 제공하는 행운 로또 추첨기**

### Feature
#### 키워드 추첨
- 사용자가 입력한 키워드를 유니코드 값으로 변경한 후, 랜덤 시드값에 포함시켜 추첨 진행
#### 통계 추첨
- 선택한 범위에 해당하는 회차 정보 통계 데이터를 제공하고 숫자 고정을 통해 추첨 진행
#### 추첨 기록 저장
- 각 추첨의 결과를 복사, 공유, 저장 기능 제공
#### QR 스캔
- 로또 추첨지의 QR 이미지를 인식하여 웹 추첨 결과 연결


### ScreenShots
| <img src="https://dl.dropbox.com/scl/fi/khhr502iynvgkgx6dgr7q/all_1.png?rlkey=1dem2jtcl1wfg9wqvqexnwuik&st=8nbqfebl&dl=0" height="360"> | <img src="https://dl.dropbox.com/scl/fi/3y3l4orrsp29hbs3lyiet/all_2.png?rlkey=bue5dmtf3y9dhupbubvs4zlgp&st=k5snlfyq&dl=0" height="360"> | <img src="https://dl.dropbox.com/scl/fi/r7bk8tfkvue6ss88x7jk4/all_3.png?rlkey=4dsg08dp2akep4lgm2xfs0zpr&st=v9w3v2nn&dl=0" height="360"> |
|:--:|:--:|:--:|
| <img src="https://dl.dropbox.com/scl/fi/afgqk2ukeeisjo0a8orzc/all_4.png?rlkey=9jct514i3caam3pma8pn68d8v&st=65gybugk&dl=0" height="360"> | <img src="https://dl.dropbox.com/scl/fi/u3w2gs1p61a1s8dfoc1uf/all_5.png?rlkey=83f0oqtjlroeateme0pm24d2f&st=4ggbj8wu&dl=0" height="360"> | <img src="https://dl.dropbox.com/scl/fi/ue85i8lxjwb6o7472j7kl/all_6.png?rlkey=lpmgorn8pkhe3iyd9oxmbaqlt&st=hp8t6h9a&dl=0" height="360"> |

<br>

# Module

```mermaid
graph TD;
    data:::data --> domain:::domain
    presentation:::app --> domain
    presentation:::app --> data

classDef app stroke:#00E489,stroke-width:2px
classDef data stroke:#4FC3F7,stroke-width:2px
classDef domain stroke:#9575CD,stroke-width:2px
```
<br>

# Architecture
**행운 로또 추첨**은 [Android Architecture Guide](https://developer.android.com/topic/architecture) 를 준수합니다.

### Overview

- Data, Domain, UI 총 세 개의 Layer로 구성되어 있습니다.
- [Unidirectional Data Flow](https://developer.android.com/topic/architecture/ui-layer#udf) 를 준수합니다.

    - 상위 Layer는 하위 Layer의 변화에 반응합니다. 
    - Event는 상위에서 하위 Layer로 이동합니다. 
    - Data는 하위에서 상위 Layer로 이동합니다.


- 데이터 흐름은 streams 통해 표현하며 Kotlin Flow를 사용합니다.


<br>

# Development
### Required
| Name | Version |
| --- | --- |
| IDE |   *```Android Studio Ladybug```* | 
| Kotlin |   *```2.0.0```* | 
| MinSdk  |   *```24```* | 
| TargetSdk  |   *```35```* | 


### Libraries
| Name | Version |
| --- | --- |
| Coroutines | *```1.6.4```* |
| Dagger-Hilt | *```2.51.1```* |
| Room | *```2.7.0```* |
| DataStore  | *```1.1.7```* |
| Timber | *```5.0.1```* |


> [!NOTE]
> 사용한 라이브러리 세부정보는 [libs.versions.toml](https://github.com/2blue-99/Lotto_compose/blob/master/gradle/libs.versions.toml) 를 참고해 주세요.
