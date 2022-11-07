object BuildPlugins {
    val DAGGER_HILT by lazy { "com.google.dagger.hilt.android" }
    val HILT_PLUGIN by lazy { "dagger.hilt.android.plugin" }
    val ANDROID_APPLICATION by lazy { "com.android.application" }
    val ANDROID_LIBRARY by lazy { "com.android.library" }
    val KOTLIN_ANDROID by lazy { "org.jetbrains.kotlin.android" }
    val BUILD_SRC_UPDATE_VERSION by lazy { "com.github.ben-manes.versions" }
    val KOTLIN_PARCELIZE by lazy { "kotlin-parcelize" }
    val KOTLIN_KAPT by lazy { "kotlin-kapt" }
}

object Dependencies {
    val hilt = Hilt
    val androidX = AndroidX
    val image = Image
    val retrofit = Retrofit()
    val okHttp = OkHttp()
    val coroutine = Coroutine
    val swipe_refresh by lazy { "com.google.accompanist:accompanist-swiperefresh:${Versions.COMPOSE_SWIPE_REFRESH}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.TIMBER}" }
    val joda by lazy { "net.danlew:android.joda:${Versions.JODA}" }
    val ratingBar by lazy { "io.github.a914-gowtham:compose-ratingbar:${Versions.COMPOSE_RATING_BAR}" }

    object Hilt {
        val hilt by lazy { "com.google.dagger:hilt-android:${Versions.HILT}" }
        val compiler by lazy { "com.google.dagger:hilt-compiler:${Versions.HILT}" }
        val compose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.COMPOSE_HILT_NAVIGATION}" }
    }

    object AndroidX {
        val core by lazy { "androidx.core:core-ktx:${Versions.CORE_KTX}" }
        val compose_activity by lazy { "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}" }

        val compose_ui = ComposeUi
        object ComposeUi {
            val ui by lazy { "androidx.compose.ui:ui:${Versions.COMPOSE_UI}" }
            val preview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_UI}" }
            val junit4 by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE_UI}" }
            val tooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_UI}" }
            val manifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE_UI}" }
        }

        val room = Room
        object Room {
            val runtime by lazy { "androidx.room:room-runtime:${Versions.ROOM}" }
            val ktx by lazy { "androidx.room:room-ktx:${Versions.ROOM}" }
            val compiler by lazy { "androidx.room:room-compiler:${Versions.ROOM}" }
        }

        val paging = Paging
        object Paging {
            val runtime by lazy { "androidx.paging:paging-runtime-ktx:${Versions.PAGING}" }
            val compose by lazy { "androidx.paging:paging-compose:${Versions.COMPOSE_PAGING}" }
            val common by lazy { "androidx.paging:paging-common:${Versions.PAGING}" }
        }

        val navigation = Navigation
        object Navigation {
            val navigation by lazy { "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}" }
            val bottomNavigation by lazy { "com.google.accompanist:accompanist-navigation-material:${Versions.COMPOSE_BOTTOM_NAVIGATION}" }
        }

        val material_icon = MaterialIcon
        object MaterialIcon {
            val extended by lazy { "androidx.compose.material:material-icons-extended:${Versions.MATERIAL_ICON}" }
            val core by lazy { "androidx.compose.material:material-icons-core:${Versions.MATERIAL_ICON}" }
        }

        val material = Material
        object Material {
            val compose_material by lazy { "androidx.compose.material:material:${Versions.COMPOSE_MATERIAL}" }
            val material by lazy { "com.google.android.material:material:${Versions.MATERIAL}" }
        }

        val lifecycle = Lifecycle
        object Lifecycle {
            val viewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.COMPOSE_VIEW_MODEL}" }
            val runtime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}" }
        }

        val test = Test
        object Test {
            val junit by lazy { "junit:junit:${Versions.JUNIT}" }
            val ext_junit by lazy { "androidx.test.ext:junit:${Versions.TEST_JUNIT}" }
            val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}" }
        }
    }

    object Image {
        val glide by lazy { "com.github.bumptech.glide:glide:${Versions.GLIDE}" }
        val compiler by lazy { "com.github.bumptech.glide:compiler:${Versions.GLIDE}" }
        val coil by lazy { "io.coil-kt:coil-compose:${Versions.COMPOSE_COIL}" }
    }

    class Retrofit(
        private val name: String = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    ) : CharSequence by name {
        val gsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}" }

        override fun toString(): String = name
    }

    class OkHttp(
        private val name: String = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    ) : CharSequence by name {
        val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}" }
        override fun toString(): String = name
    }

    object Coroutine {
        val core by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}" }
        val android by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}" }
    }
}