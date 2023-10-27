from rest_framework import serializers
from .models import Movie, Genre, Quiz1, Quiz2
# from .models import Ideal
from accounts.models import User
import json

# 영화 리뷰 게시판
# 영화 리뷰 목록
class MovieListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Movie
        fields = ('id', 'title', 'overview','backdrop_path','poster_path', 'key')
        
class MovieSerializer(serializers.ModelSerializer):
    genres_name = serializers.SerializerMethodField()

    def get_genres_name(self, movie):
        return [genre.name for genre in movie.genres.all()]
    
    class Meta:
        model = Movie
        fields = '__all__'
        read_only_fields = ('review',)


class GenreSerializer(serializers.ModelSerializer):
    class Meta:
        model = Genre
        fields = '__all__'
        

###################################################################
# 영화 이상형 월드컵을 위한 serializer

class IdealMovieSerializer(serializers.ModelSerializer):
    class Meta:
        model = Movie
        fields = ('id', 'title', 'poster_path',)

# 퀴즈를 위한 serializer
class Quiz1Serializer(serializers.ModelSerializer):

    class Meta:
        model = Quiz1
        fields = '__all__'


class Quiz2Serializer(serializers.ModelSerializer):
    class Meta:
        model = Quiz2
        fields = '__all__'