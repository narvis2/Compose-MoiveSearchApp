plugins {
    id (BuildPlugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id (BuildPlugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id (BuildPlugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id (BuildPlugins.DAGGER_HILT) version Versions.HILT apply false
    id (BuildPlugins.BUILD_SRC_UPDATE_VERSION) version Versions.BEN_MANES
    id (BuildPlugins.GOOGLE_GMS) version Versions.GOOGLE_GMS apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

// 네트워크에서 아티팩트 정보를 받아온 뒤 안정버전인지 여부를 판정하는 함수를 추가
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

/**
 * 디펜던시 버전을 체크하는 태스크를 정의
 * ✅ 실행
 * 1️⃣ mac 기준 control 키 2번 클릭
 * 2️⃣ ./gradlew dependencyUpdate 입력 및 엔터
 * 3️⃣ build > dependencyUpdates 하위에 json 파일 생김
 */
tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates")
    .configure {
        // rejectVersionInf -> 제한 사항, isStable 에 해당되지 않는 모든 버전을 거부
        rejectVersionIf {
            isNonStable(candidate.version)
        }

        // optional parameters
        checkForGradleUpdate = true
        outputFormatter = "json"
        outputDir = "build/dependencyUpdates"
        reportfileName = "report" // build > dependencyUpdates 하위에 report.json 파일 생성됨
    }