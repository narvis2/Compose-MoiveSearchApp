# Compose-MovieSearchApp

## 🍎 프로젝트 구성
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