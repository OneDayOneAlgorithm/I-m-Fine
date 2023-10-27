from rest_framework import serializers
from .models import User
# from movies.models import Movie

class FollowSerializer(serializers.ModelSerializer):
    followings_count = serializers.SerializerMethodField()
    followers_count = serializers.SerializerMethodField()
    followings_name = serializers.SerializerMethodField()
    followers_name = serializers.SerializerMethodField()
    is_follow = serializers.SerializerMethodField()

    def get_is_follow(self, obj):
        request = self.context.get('request')
        if request and request.user.is_authenticated:
            return request.user in obj.followers.all()
        return False
    
    def get_followings_count(self, obj):
        return obj.followings.count()

    def get_followers_count(self, obj):
        return obj.followers.count()
    
    def get_followings_name(self, obj):
        return [user.username for user in obj.followings.all()]

    def get_followers_name(self, obj):
        return [user.username for user in obj.followers.all()]
    
    class Meta:
        model = User
        fields = ('followings_count', 'followers_count', 
                  'followings', 'followers', 
                  'followings_name', 'followers_name',
                  'is_follow',)
        

class CustomUserSerializer(serializers.ModelSerializer):
    followings_count = serializers.SerializerMethodField()
    followers_count = serializers.SerializerMethodField()
    followings_name = serializers.SerializerMethodField()
    followers_name = serializers.SerializerMethodField()
    idealmovie_name = serializers.SerializerMethodField()
    like_movies_name = serializers.SerializerMethodField()
    
    is_follow = serializers.SerializerMethodField()

    def get_is_follow(self, obj):
        request = self.context.get('request')
        if request and request.user.is_authenticated:
            return request.user in obj.followers.all()
        return False
    
    
    def get_followings_count(self, obj):
        return obj.followings.count()

    def get_followers_count(self, obj):
        return obj.followers.count()
    
    def get_followings_name(self, obj):
        return [user.username for user in obj.followings.all()]

    def get_followers_name(self, obj):
        return [user.username for user in obj.followers.all()]
    
    def get_idealmovie_name(self, obj):
        return [movie.title for movie in obj.idealmovie.all()]

    def get_like_movies_name(self, obj):
        return [movie.title for movie in obj.like_movies.all()]
    
    class Meta:
        model = User
        fields = ('is_follow', 'id', 'password', 'last_login', 'is_superuser', 
                  'username', 'first_name', 'last_name', 'email', 
                  'is_staff', 'is_active', 'date_joined', 
                  'exp', 'point', 'grade',
                  'groups', 'user_permissions',
                  'followings', 'followers', 'followings_name', 'followers_name',
                  'followings_count', 'followers_count',
                  'idealmovie','idealmovie_name',
                  'like_movies', 'like_movies_name',)