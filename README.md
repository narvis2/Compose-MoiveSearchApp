# 🍀 Naver Open API 를 이용한 영화 찾기 앱 [Jetpack Compose] 🍀

## ☘️ 프로젝트 구성 ☘️
``` sh
buildSrc
├─ ..
├─ src
│ └─ main
│   └─ kotlin
│     ├─ ConfigData.kt
│     ├─ Dependencies.kt
│     └─ Versions.kt
└─ build.gradle.kts
app
├─ ..
├─ src
│ ├─ main  
│ │ ├─ java
│ │ │ ├─ com.example.moviesearchapp  
│ │ │ │ ├─ di  
│ │ │ │ ├─ ui.theme  
│ │ │ │ ├─ view
│ │ │ │ │  ├─ component
│ │ │ │ │  ├─ navigation
│ │ │ │ │  ├─ network
│ │ │ │ │  ├─ screen
│ │ │ │ │  ├─ utils
│ │ │ │ │  ├─ widgets
│ │ │ │ │  ├─ MainActivity.kt
│ │ │ │ │  └─ MainViewModel.kt
│ │ │ │ └─ MovieApplication.kt
└─└─└─└─ res  
data
├─ ..
├─ src
│ ├─ main
│ │ ├─ java  
│ │ │ ├─ com.example.data
│ │ │ │ ├─ api
│ │ │ │ ├─ mapper  
│ │ │ │ ├─ model  
└─└─└─└─└─ repository
domain
├─ ..
├─ src
│ ├─ main
│ │ ├─ java  
│ │ │ ├─ com.example.domain
│ │ │ │ ├─ model
│ │ │ │ ├─ repository
└─└─└─└─└─ usecase   
```

## ☘️ 주요 사용 기술 ☘️
- Kotlin
- MVC
- MVVM
- Clean Archtecture
- Multi-Module Project
- Kotlin DSL + BuildSrc
- Jetpack-Compose
- Kotlin Coroutine
- Dagger-Hilt
- Paging3
- Retrofit
- Okhttp
- Compose-Coil
- Compose-Navigation
- Swipe-Refresh
- Compose-ViewModel
- Timber
- Joda
- Room
- Compose-Ratingbar
- Material Icon
- Material Design
