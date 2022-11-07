package com.example.data.mapper

import com.example.data.db.entity.MovieEntity
import com.example.data.model.MovieInfo
import com.example.domain.model.MovieInfoModel

object ObjectMapper {
    fun List<MovieInfo>.toMovieInfoModelList(): List<MovieInfoModel> = map {
        it.toMovieInfoModel()
    }

    private fun MovieInfo.toMovieInfoModel(): MovieInfoModel = MovieInfoModel(
        title = this.title,
        link = this.link,
        image = this.image,
        subtitle = this.subtitle,
        pubDate = this.pubDate,
        director = this.director,
        actor = this.actor,
        userRating = this.userRating.toFloat()
    )

    fun List<MovieEntity>.entityToMovieInfoModelList(): List<MovieInfoModel> = map {
        it.toMovieInfoModel()
    }

    fun MovieEntity.toMovieInfoModel(): MovieInfoModel = MovieInfoModel(
        id = this.id,
        title = this.title,
        link = this.link,
        image = this.image,
        subtitle = this.subtitle,
        pubDate = this.pubDate,
        director = this.director,
        actor = this.actor,
        userRating = this.rating,
        insertDateTime = this.insertDate,
        updateTime = this.updateDate
    )

    fun MovieInfoModel.toMovieEntity(): MovieEntity = MovieEntity(
        id = this.id,
        title = this.title,
        link = this.link,
        image = this.image,
        subtitle = this.subtitle,
        pubDate = this.pubDate,
        director = this.director,
        actor = this.actor,
        rating = this.rating,
        insertDate = this.insertDateTime,
        updateDate = this.updateTime
    )
}