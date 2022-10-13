package com.example.data.mapper

import com.example.data.model.MovieInfo
import com.example.domain.model.MovieInfoModel

object ObjectMapper {
    fun List<MovieInfo>.toMovieInfoModelList(): List<MovieInfoModel> = map {
        it.toMovieInfoModel()
    }

    fun List<MovieInfoModel>.toMovieInfoList(): List<MovieInfo> = map {
        it.toMovieInfo()
    }

    fun MovieInfo.toMovieInfoModel(): MovieInfoModel = MovieInfoModel(
        title = this.title,
        link = this.link,
        image = this.image,
        subtitle = this.subtitle,
        pubDate = this.pubDate,
        director = this.director,
        actor = this.actor,
        userRating = this.userRating
    )

    fun MovieInfoModel.toMovieInfo(): MovieInfo = MovieInfo(
        title = this.title,
        link = this.link,
        image = this.image,
        subtitle = this.subtitle,
        pubDate = this.pubDate,
        director = this.director,
        actor = this.actor,
        userRating = this.userRating
    )
}