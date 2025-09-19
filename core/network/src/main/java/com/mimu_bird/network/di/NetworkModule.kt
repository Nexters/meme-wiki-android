package com.mimu_bird.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mimu_bird.network.api.CategoryService
import com.mimu_bird.network.api.SearchService
import com.mimu_bird.network.api.TopRatedMemeService
import com.mimu_bird.network.api.SharedMemeService
import com.mimu_bird.network.api.MemeDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // 검색 서비스
    @Provides
    @Singleton
    fun provideSearchService(
        retrofit: Retrofit
    ): SearchService = retrofit.create(SearchService::class.java)

    // 카테고리 서비스
    @Provides
    @Singleton
    fun provideCategoryService(
        retrofit: Retrofit
    ): CategoryService = retrofit.create(CategoryService::class.java)

    // Top Rated 밈 서비스
    @Provides
    @Singleton
    fun provideTopRatedMemeService(
        retrofit: Retrofit
    ): TopRatedMemeService = retrofit.create(TopRatedMemeService::class.java)

    // Shared 밈 서비스
    @Provides
    @Singleton
    fun provideSharedMemeService(
        retrofit: Retrofit
    ): SharedMemeService = retrofit.create(SharedMemeService::class.java)

    // 밈 상세 정보 서비스
    @Provides
    @Singleton
    fun provideMemeDetailService(
        retrofit: Retrofit
    ): MemeDetailService = retrofit.create(MemeDetailService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        converter: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.meme-wiki.net/")
            .client(okHttpClient)
            .addConverterFactory(converter)
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ConverterFactoryModule {
    @Provides
    @Singleton
    fun provideJsonConverter(): Converter.Factory {
        return Json {
            coerceInputValues = true // null 값으로 들어오면 default value로 처리
            ignoreUnknownKeys = true // 프로퍼티로 선언되지 않은 key는 무시
        }.asConverterFactory("application/json".toMediaType())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // 호스트네임도 무조건 통과
            .build()
    }
}