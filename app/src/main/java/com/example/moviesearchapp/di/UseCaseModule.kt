package com.example.moviesearchapp.di

import com.example.domain.repository.NaverRepository
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideRequestMovieListUseCase(
        naverRepository: NaverRepository
    ): RequestMovieListUseCase = RequestMovieListUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestLocalMovieListUseCase(
        naverRepository: NaverRepository
    ): RequestLocalMovieListUseCase = RequestLocalMovieListUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestLocalMovieUseCase(
        naverRepository: NaverRepository
    ): RequestLocalMovieUseCase = RequestLocalMovieUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestLocalMovieByTitleUseCase(
        naverRepository: NaverRepository
    ): RequestLocalMovieByTitleUseCase = RequestLocalMovieByTitleUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestInsertMovieUseCase(
        naverRepository: NaverRepository
    ): RequestInsertMovieUseCase = RequestInsertMovieUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestLocalDeleteMovieAllUseCase(
        naverRepository: NaverRepository
    ): RequestLocalDeleteMovieAllUseCase = RequestLocalDeleteMovieAllUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestLocalDeleteMovieUseCase(
        naverRepository: NaverRepository
    ): RequestLocalDeleteMovieUseCase = RequestLocalDeleteMovieUseCase(naverRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestDeleteMovieListUseCase(
        naverRepository: NaverRepository
    ): RequestDeleteMovieListUseCase = RequestDeleteMovieListUseCase(naverRepository)
}