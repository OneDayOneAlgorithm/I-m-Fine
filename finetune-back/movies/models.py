from django.db import models
from django.conf import settings

class Genre(models.Model):
    name = models.CharField(max_length=50)
    
class Movie(models.Model):
    like_users = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='like_movies')
    title = models.CharField(max_length=100)
    release_date = models.DateField(null=True, auto_now=False, auto_now_add=False)
    popularity = models.FloatField(null=True)
    vote_count = models.IntegerField(null=True)
    vote_average = models.FloatField(null=True)
    overview = models.TextField(null=True)
    poster_path = models.CharField(null=True, max_length=200)
    backdrop_path = models.CharField(null=True, max_length=200)
    key = models.CharField(null=True, max_length=200)
    genres = models.ManyToManyField(Genre)

class Quiz1(models.Model):
    question = models.CharField(max_length=255)
    options = models.JSONField()
    answer = models.CharField(max_length=255)

class Quiz2(models.Model):
    question = models.CharField(max_length=255)
    options = models.JSONField()
    answer = models.CharField(max_length=255)